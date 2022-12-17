package top.pippen.order.service;

import top.pippen.order.bean.dto.biz.ProductPropDTO;
import top.pippen.order.bean.model.biz.ProductPropEntity;
import top.pippen.order.common.service.CrudService;

import java.util.List;

/**
 * 规格
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
public interface ProductPropService extends CrudService<ProductPropEntity, ProductPropDTO> {

    /**
     * 获取全部数据 不分页
     * @return
     */
    List<ProductPropDTO> getPropAll(Long shopId);

    /**
     * 保存规格与规格值
     * @param propDTO /
     */
    void savePropAndValues(ProductPropDTO propDTO);


    ProductPropDTO get(Long id, Long shopId);
    /**
     * 更新规格
     * @param propDTO
     */
    void updatePropAndValues(ProductPropDTO propDTO);

    /**
     * 删除规格
     * @param propId 规格ID
     * @param shopId 店铺ID
     */
    void deleteProdPropAndValues(Long propId, Long shopId);
}
