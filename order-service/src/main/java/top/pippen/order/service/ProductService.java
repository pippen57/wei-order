package top.pippen.order.service;

import top.pippen.order.bean.dto.biz.APIProductDTO;
import top.pippen.order.bean.dto.biz.ProductDTO;
import top.pippen.order.bean.model.biz.ProductEntity;
import top.pippen.order.common.service.CrudService;

import java.util.List;

/**
 * 商品
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
public interface ProductService extends CrudService<ProductEntity, ProductDTO> {


    /**
     * 保存
     *
     * @param productDTO /
     */
    void saveProduct(ProductDTO productDTO);

    /**
     * 修改
     *
     * @param productDTO /
     */
    void updateProduct(ProductDTO productDTO);

    ProductDTO get(Long id, Long shopId);

    /**
     * 删除
     *
     * @param id     /
     * @param shopId /
     */
    void delProduct(Long[] id, Long shopId);

    /**
     * 获取商品列表,用于客户端的展示
     *
     * @param shopId
     * @param categoryId
     * @return
     */
    List<ProductDTO> getProduct(Long shopId,String categoryId);

     List<APIProductDTO> getProduct(Long shopId);

}
