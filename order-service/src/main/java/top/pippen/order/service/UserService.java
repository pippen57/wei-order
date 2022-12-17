/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.service;

import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.common.service.BaseService;
import top.pippen.order.dao.biz.UserDao;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends BaseService<UserEntity> {

	UserEntity getByOpenId(String openId);

	UserEntity getUserByUserId(Long userId);

	UserDao getDao();


}
