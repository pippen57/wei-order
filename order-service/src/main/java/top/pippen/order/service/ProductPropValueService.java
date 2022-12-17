package top.pippen.order.service;

import top.pippen.order.bean.dto.biz.ProductPropValueDTO;
import top.pippen.order.bean.model.biz.ProductPropValueEntity;
import top.pippen.order.common.service.CrudService;

import java.util.List;

/**
 * 规格值
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
public interface ProductPropValueService extends CrudService<ProductPropValueEntity, ProductPropValueDTO> {

    /**
     * 保存规格值
     * @param propValue /
     * @param propId 规格ID
     */
    void savePropValue(List<ProductPropValueDTO> propValue, Long  propId);

    /**
     * 根据规格ID删除所有的值
     * @param propId 规格ID
     */
    void deletePropValue(Long propId);
}
