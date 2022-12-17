package top.pippen.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pippen.order.bean.dto.biz.CartItemDTO;
import top.pippen.order.bean.dto.biz.ProductDTO;
import top.pippen.order.bean.dto.biz.SkuDTO;
import top.pippen.order.bean.model.biz.CartItemEntity;
import top.pippen.order.common.exception.ErrorCode;
import top.pippen.order.common.exception.RenException;
import top.pippen.order.common.service.impl.CrudServiceImpl;
import top.pippen.order.dao.biz.CartItemDao;
import top.pippen.order.service.CartItemService;
import top.pippen.order.service.ProductService;
import top.pippen.order.service.SkuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-22
 */
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl extends CrudServiceImpl<CartItemDao, CartItemEntity, CartItemDTO> implements CartItemService {

    private final SkuService skuService;
    private final ProductService productService;

    @Override
    public QueryWrapper<CartItemEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String userId = (String) params.get("userId");
        String shopId = (String) params.get("shopId");

        QueryWrapper<CartItemEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.eq(StringUtils.isNotBlank(userId), "user_id", userId);
        wrapper.eq(StringUtils.isNotBlank(shopId), "shop_id", shopId);

        return wrapper;
    }

    @Override
    public void saveItem(CartItemDTO dto) {
        Long skuId = dto.getSkuId();
        CartItemEntity cartItem;
        if (skuId != null) {
            cartItem = baseDao.findBySkuIdAndPropId(skuId, dto.getProductId(), dto.getUserId());
        } else {
            cartItem = baseDao.findByProdId(dto.getProductId(), dto.getUserId());
        }
        if (cartItem != null) {
            Integer quantity = cartItem.getQuantity();
            Integer dtoQuantity = dto.getQuantity();
            cartItem.setQuantity(dtoQuantity > 1 ? dtoQuantity : quantity + dtoQuantity);
            updateById(cartItem);
        } else {
            save(dto);
        }

    }

    @Override
    public List<CartItemDTO> listCart(Long userId, Long shopId) {
        if (userId == null) {
            throw new RenException(ErrorCode.TOKEN_INVALID);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId + "");
        params.put("shopId", shopId + "");
        List<CartItemDTO> cartItem = list(params);

        for (CartItemDTO itemDTO : cartItem) {

            Long skuId = itemDTO.getSkuId();
            Long productId = itemDTO.getProductId();
            if (skuId != null) {
                SkuDTO skuDTO = skuService.get(skuId);
                itemDTO.setSku(skuDTO);
            }
            ProductDTO productDTO = productService.get(productId);
            itemDTO.setProduct(productDTO);
        }
        return cartItem;
    }

    @Override
    public void itemNumChange(Long userId, CartItemDTO dto) {
        Long id = dto.getId();
        CartItemEntity cartItemEntity = baseDao.selectOne(Wrappers.<CartItemEntity>lambdaUpdate().eq(CartItemEntity::getId, id).eq(CartItemEntity::getUserId, userId));
        if (cartItemEntity == null) {
            throw new RenException("参数错误");
        }
        Integer quantity = dto.getQuantity();
        if (quantity <= 0) {
            // 删除
            deleteById(id);
        } else {
            // 改变数量
            cartItemEntity.setQuantity(quantity);
            updateById(cartItemEntity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearCartItem(Long userId, Long shopId) {
        baseDao.delete(Wrappers.<CartItemEntity>lambdaUpdate().eq(CartItemEntity::getUserId, userId).eq(CartItemEntity::getShopId, shopId));
    }
}
