package top.pippen.order.dao.biz;

import org.apache.ibatis.annotations.Mapper;
import top.pippen.order.bean.model.biz.ProductEntity;
import top.pippen.order.common.dao.BaseDao;

/**
 * 商品
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Mapper
public interface ProductDao extends BaseDao<ProductEntity> {

}
