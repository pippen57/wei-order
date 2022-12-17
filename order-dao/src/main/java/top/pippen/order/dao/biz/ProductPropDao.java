package top.pippen.order.dao.biz;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.pippen.order.bean.model.biz.ProductPropEntity;
import top.pippen.order.common.dao.BaseDao;

/**
 * 规格
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Mapper
public interface ProductPropDao extends BaseDao<ProductPropEntity> {
    /**
     * 根据propName和ShopId查询规格
     * @param propName 规格名称
     * @param shopId 门店ID
     * @return  /
     */
    ProductPropEntity getByPropNameAndShopId(@Param("propName") String propName, @Param("shopId") Long shopId);

    /**
     * 删除
     * @param propId /
     * @param shopId /
     * @return /
     */
    int deleteByPropId(@Param("propId") Long propId, @Param("shopId") Long shopId);

}
