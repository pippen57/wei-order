package top.pippen.order.dao.biz;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.pippen.order.bean.model.biz.ShopEntity;
import top.pippen.order.common.dao.BaseDao;

/**
 * 店铺
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
@Mapper
public interface ShopDao extends BaseDao<ShopEntity> {
    /**
     * 更改店铺状态
     * @param shopStatus
     * @param shopId
     * @return
     */
    int updateShopStatus(@Param("shopStatus") Integer shopStatus,@Param("shopId") Long shopId);
}
