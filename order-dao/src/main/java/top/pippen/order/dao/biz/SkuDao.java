package top.pippen.order.dao.biz;

import org.apache.ibatis.annotations.Mapper;
import top.pippen.order.bean.model.biz.SkuEntity;
import top.pippen.order.common.dao.BaseDao;

/**
 * SKU
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-11
 */
@Mapper
public interface SkuDao extends BaseDao<SkuEntity> {

}
