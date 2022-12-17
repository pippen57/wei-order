package top.pippen.order.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.pippen.order.api.annotation.Login;
import top.pippen.order.api.annotation.LoginUser;
import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.api.interceptor.AuthorizationInterceptor;
import top.pippen.order.bean.dto.biz.CartItemDTO;
import top.pippen.order.common.utils.Result;
import top.pippen.order.common.validator.ValidatorUtils;
import top.pippen.order.common.validator.group.AddGroup;
import top.pippen.order.common.validator.group.UpdateGroup;
import top.pippen.order.service.CartItemService;

import java.util.Date;
import java.util.List;


/**
 * 购物车
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-22
 */
@RestController
@RequestMapping("/api/cart")
@Api(tags = "购物车")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;


    @Login
    @GetMapping("/{shopId}")
    @ApiOperation("获取购物车列表")
    public Result<List<CartItemDTO>> get(@RequestAttribute(AuthorizationInterceptor.USER_KEY) Long userId, @PathVariable Long shopId) {
        List<CartItemDTO> data = cartItemService.listCart(userId, shopId);

        return new Result<List<CartItemDTO>>().ok(data);
    }

    @Login
    @PostMapping
    @ApiOperation("保存")
    public Result save(@RequestBody CartItemDTO dto, @LoginUser UserEntity user) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class);
        dto.setUserId(user.getId());
        dto.setCreateDate(new Date());
        cartItemService.saveItem(dto);

        return new Result();
    }

    @Login
    @PutMapping
    @ApiOperation("商品数量增减")
    public Result update(@RequestBody CartItemDTO dto, @RequestAttribute(AuthorizationInterceptor.USER_KEY) Long userId) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class);

        cartItemService.itemNumChange(userId, dto);

        return new Result();
    }

    @Login
    @DeleteMapping("/{shopId}")
    @ApiOperation("清空购物车")
    public Result delete(@LoginUser UserEntity user, @PathVariable Long shopId) {

        cartItemService.clearCartItem(user.getId(), shopId);

        return new Result();
    }


}
