/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package top.pippen.order.dao.sys;

import org.apache.ibatis.annotations.Mapper;
import top.pippen.order.bean.model.sys.DictType;
import top.pippen.order.bean.model.sys.SysDictTypeEntity;
import top.pippen.order.common.dao.BaseDao;

import java.util.List;

/**
 * 字典类型
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysDictTypeDao extends BaseDao<SysDictTypeEntity> {

    /**
     * 字典类型列表
     */
    List<DictType> getDictTypeList();

}
