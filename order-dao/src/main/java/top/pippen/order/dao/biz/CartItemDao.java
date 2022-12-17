package top.pippen.order.dao.biz;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.pippen.order.bean.model.biz.CartItemEntity;
import top.pippen.order.common.dao.BaseDao;

/**
 * 购物车
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-22
 */
@Mapper
public interface CartItemDao extends BaseDao<CartItemEntity> {


    CartItemEntity findBySkuIdAndPropId(@Param("skuId") Long skuId,@Param("prodId") Long prodId,@Param("userId") Long userId);
    CartItemEntity findByProdId(@Param("prodId") Long prodId,@Param("userId") Long userId);
}
