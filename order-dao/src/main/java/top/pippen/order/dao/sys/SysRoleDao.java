/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.dao.sys;

import org.apache.ibatis.annotations.Mapper;
import top.pippen.order.bean.model.sys.SysRoleEntity;
import top.pippen.order.common.dao.BaseDao;

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysRoleDao extends BaseDao<SysRoleEntity> {


}
