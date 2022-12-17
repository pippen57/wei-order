package top.pippen.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.pippen.order.bean.dto.biz.OrderItemDTO;
import top.pippen.order.bean.model.biz.OrderItemEntity;
import top.pippen.order.common.service.impl.CrudServiceImpl;
import top.pippen.order.dao.biz.OrderItemDao;
import top.pippen.order.service.OrderItemService;

import java.util.List;
import java.util.Map;

/**
 * 订单项
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@Service
public class OrderItemServiceImpl extends CrudServiceImpl<OrderItemDao, OrderItemEntity, OrderItemDTO> implements OrderItemService {

    @Override
    public QueryWrapper<OrderItemEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String shopId = (String) params.get("shopId");
        String orderId = (String) params.get("orderId");

        QueryWrapper<OrderItemEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.eq(StringUtils.isNotBlank(shopId), "shop_id", shopId);
        wrapper.eq(StringUtils.isNotBlank(orderId), "order_id", orderId);

        return wrapper;
    }

    @Override
    public void saveList(List<OrderItemDTO> orderItemDTOList) {
        for (OrderItemDTO orderItemDTO : orderItemDTOList) {
            save(orderItemDTO);
        }
    }
}
