package top.pippen.order.miniapp.service;

import top.pippen.order.bean.dto.biz.OrderDTO;
import top.pippen.order.bean.dto.biz.ShopDTO;
import top.pippen.order.bean.model.biz.UserEntity;

/**
 * @author Pippen
 * @since 2022/12/08 09:21
 */
public interface MsgService {

    /**
     * 发送下单成功通知
     *
     * @param order /
     * @param shop  /
     * @param user  /
     */
    void sendOrderSuccessMsg(OrderDTO order, ShopDTO shop, UserEntity user, String templateId);

    /**
     * 下发订单取消通知
     *
     * @param order /
     * @param shop  /
     * @param user  /
     */
    void sendOrderCancelMsg(OrderDTO order, ShopDTO shop, UserEntity user, String templateId);
}
