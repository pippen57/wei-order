package top.pippen.order.bean.dto.biz;

import lombok.Data;

import java.util.List;

/**
 * @author Pippen
 * @since 2022/11/07 14:18
 */
@Data
public class OrderInfoDTO {

    private OrderDTO order;

    private List<OrderItemDTO> orderItem;

    private OrderBalanceDTO balance;

    private ShopDTO shop;
    public OrderInfoDTO() {
    }

    public OrderInfoDTO(OrderDTO orderDTO, List<OrderItemDTO> items) {
        this.order = orderDTO;
        this.orderItem = items;
    }
}
