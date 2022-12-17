package top.pippen.order.service;

import top.pippen.order.bean.dto.biz.SkuDTO;
import top.pippen.order.bean.model.biz.SkuEntity;
import top.pippen.order.common.service.CrudService;

import java.util.List;

/**
 * SKU
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-11
 */
public interface SkuService extends CrudService<SkuEntity, SkuDTO> {
    /**
     * 保存SKU
     * @param skuList /
     * @param productId 商品ID
     */
    void saveSku(List<SkuDTO> skuList,Long productId);

    /**
     * 删除所有SKU
     * @param productIds /
     */
    void deleteSku(Long[] productIds);


    /**
     * 获取商品SKU 列表
     * @param productId 商品ID
     * @return /
     */
    List<SkuDTO> listByProdId(Long productId);
}
