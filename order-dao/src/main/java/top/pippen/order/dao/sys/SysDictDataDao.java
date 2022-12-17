/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.dao.sys;

import org.apache.ibatis.annotations.Mapper;
import top.pippen.order.bean.model.sys.DictData;
import top.pippen.order.bean.model.sys.SysDictDataEntity;
import top.pippen.order.common.dao.BaseDao;

import java.util.List;

/**
 * 字典数据
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysDictDataDao extends BaseDao<SysDictDataEntity> {

    /**
     * 字典数据列表
     */
    List<DictData> getDictDataList();
}
