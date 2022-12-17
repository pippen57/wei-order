/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.job.dao;

import org.apache.ibatis.annotations.Mapper;
import top.pippen.order.common.dao.BaseDao;
import top.pippen.order.job.entity.ScheduleJobLogEntity;

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {

}
