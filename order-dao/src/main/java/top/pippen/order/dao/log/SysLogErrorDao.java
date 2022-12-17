/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.dao.log;

import org.apache.ibatis.annotations.Mapper;
import top.pippen.order.bean.model.log.SysLogErrorEntity;
import top.pippen.order.common.dao.BaseDao;

/**
 * 异常日志
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@Mapper
public interface SysLogErrorDao extends BaseDao<SysLogErrorEntity> {

}
