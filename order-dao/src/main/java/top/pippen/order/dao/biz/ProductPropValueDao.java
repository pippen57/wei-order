package top.pippen.order.dao.biz;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.pippen.order.bean.model.biz.ProductPropValueEntity;
import top.pippen.order.common.dao.BaseDao;

/**
 * 规格值
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Mapper
public interface ProductPropValueDao extends BaseDao<ProductPropValueEntity> {

    /**
     * 根据规格Id删除
     * @param propId 规格ID
     */
    void deleteByPropId(@Param("propId") Long propId);
}
