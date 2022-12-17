package top.pippen.order.service;


import top.pippen.order.bean.dto.biz.CartItemDTO;
import top.pippen.order.bean.model.biz.CartItemEntity;
import top.pippen.order.common.service.CrudService;

import java.util.List;

/**
 * 购物车
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-22
 */
public interface CartItemService extends CrudService<CartItemEntity, CartItemDTO> {


    /**
     * 购物车列表
     *
     * @param userId 用户ID
     * @param shopId
     * @return /
     */
    List<CartItemDTO> listCart(Long userId,Long shopId);

    /**
     * 添加购物车
     * @param dto
     */
    void saveItem(CartItemDTO dto);
    /**
     * 商品数量变化
     */
    void itemNumChange(Long userId, CartItemDTO dto);

    /**
     * 清空购物车
     *
     * @param userId 用户ID
     * @param shopId
     */
    void clearCartItem(Long userId,Long shopId);
}
