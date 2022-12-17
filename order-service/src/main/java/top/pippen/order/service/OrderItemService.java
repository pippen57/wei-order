package top.pippen.order.service;


import top.pippen.order.bean.dto.biz.OrderItemDTO;
import top.pippen.order.bean.model.biz.OrderItemEntity;
import top.pippen.order.common.service.CrudService;

import java.util.List;

/**
 * 订单项
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
public interface OrderItemService extends CrudService<OrderItemEntity, OrderItemDTO> {

    /**
     * 批量保存
     * @param orderItemDTOList /
     */
    void saveList(List<OrderItemDTO> orderItemDTOList);
}
