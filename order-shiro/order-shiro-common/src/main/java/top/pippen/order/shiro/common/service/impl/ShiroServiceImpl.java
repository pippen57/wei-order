/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.shiro.common.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.pippen.order.bean.enums.SuperAdminEnum;
import top.pippen.order.bean.model.sys.SysUserEntity;
import top.pippen.order.dao.sys.SysMenuDao;
import top.pippen.order.dao.sys.SysUserDao;
import top.pippen.order.shiro.common.dao.SysUserTokenDao;
import top.pippen.order.shiro.common.model.SysUserTokenEntity;
import top.pippen.order.shiro.common.service.ShiroService;
import top.pippen.order.shiro.admin.user.UserDetail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;

    @Override
    public Set<String> getUserPermissions(UserDetail user) {
        //系统管理员，拥有最高权限
        List<String> permissionsList;
        if(user.getSuperAdmin() == SuperAdminEnum.YES.value()) {
            permissionsList = sysMenuDao.getPermissionsList();
        }else{
            permissionsList = sysMenuDao.getUserPermissionsList(user.getId());
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String permissions : permissionsList){
            if(StringUtils.isBlank(permissions)){
                continue;
            }
            permsSet.addAll(Arrays.asList(permissions.trim().split(",")));
        }

        return permsSet;
    }

    @Override
    public SysUserTokenEntity getByToken(String token) {
        return sysUserTokenDao.getByToken(token);
    }

    @Override
    public SysUserEntity getUser(Long userId) {
        return sysUserDao.selectById(userId);
    }
}
