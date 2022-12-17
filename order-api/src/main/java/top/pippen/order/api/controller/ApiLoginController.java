/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package top.pippen.order.api.controller;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.pippen.order.api.annotation.Login;
import top.pippen.order.api.dto.LoginDTO;
import top.pippen.order.api.dto.UserInfoDTO;
import top.pippen.order.api.entity.TokenEntity;
import top.pippen.order.api.service.TokenService;
import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.ValidatorUtils;
import top.pippen.order.service.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/api")
@Api(tags = "登录接口")
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private WxMaService wxService;

    @PostMapping("login")
    @ApiOperation("登录")
    public Result<Map<String, Object>> login(LoginDTO dto) {
        //表单校验
        ValidatorUtils.validateEntity(dto);
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(dto.getCode());
            String openid = session.getOpenid();
            UserEntity user = userService.getByOpenId(openid);
            if (user == null) {
                user = new UserEntity();
                user.setOpenId(openid);
                user.setCreateDate(new Date());
                userService.insert(user);
            }
            //获取登录token
            TokenEntity tokenEntity = tokenService.createToken(user.getId());

            Map<String, Object> map = new HashMap<>(2);
            map.put("token", tokenEntity.getToken());
            map.put("user", user);
            map.put("expire", tokenEntity.getExpireDate().getTime() - System.currentTimeMillis());
            return new Result<Map<String, Object>>().ok(map);
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }

    @Login
    @PostMapping("/user_info")
    public Result<UserEntity> userInfo(@RequestBody UserInfoDTO userInfo, @RequestAttribute("userId") Long userId) {
        boolean allBlank = StrUtil.isAllBlank(userInfo.getAvatarUrl(),userInfo.getPhone());
        if (allBlank){
            return new Result<UserEntity>().error("参数异常");
        }

        UserEntity user = new UserEntity();
        user.setAvatar(userInfo.getAvatarUrl());
        user.setMobile(userInfo.getPhone());
        user.setUsername(userInfo.getName());
        user.setId(userId);
        int res = userService.getDao().updateUserInfo(user);
        if (res >= 1) {
            return new Result<UserEntity>().ok(user);
        }  else {
            return new Result<UserEntity>().error();
        }

    }

    @GetMapping("wx/phone")
    @ApiOperation("登录")
    public Result<WxMaPhoneNumberInfo> getWxPhone(@RequestParam String code) {
        if (StringUtils.isBlank(code)) {
            throw new RenException("参数异常");
        }
        try {
            return new Result<WxMaPhoneNumberInfo>().ok(wxService.getUserService().getPhoneNoInfo(code));
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }

    @Login
    @PostMapping("logout")
    @ApiOperation("退出")
    public Result logout(@ApiIgnore @RequestAttribute("userId") Long userId) {
        tokenService.expireToken(userId);
        return new Result();
    }

}
