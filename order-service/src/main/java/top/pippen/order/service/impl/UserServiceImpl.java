/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package top.pippen.order.service.impl;

import org.springframework.stereotype.Service;
import top.pippen.order.dao.biz.UserDao;
import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.common.service.impl.BaseServiceImpl;
import top.pippen.order.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public UserEntity getByOpenId(String openId) {
        return baseDao.getUserByOpenId(openId);
    }

    @Override
    public UserEntity getUserByUserId(Long userId) {
        return baseDao.getUserByUserId(userId);
    }

    @Override
    public UserDao getDao() {
        return baseDao;
    }
}
