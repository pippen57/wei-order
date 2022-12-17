package top.pippen.order.service;

import top.pippen.order.bean.dto.biz.OrderBalanceDTO;
import top.pippen.order.bean.dto.biz.OrderRefundDTO;
import top.pippen.order.bean.model.biz.OrderEntity;
import top.pippen.order.bean.model.biz.OrderRefundEntity;
import top.pippen.order.bean.vo.PayRefundNotifyResultVO;
import top.pippen.order.common.service.CrudService;

/**
 * 支付退款
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-11-11
 */
public interface OrderRefundService extends CrudService<OrderRefundEntity, OrderRefundDTO> {

    /**
     * 发起退款
     *
     * @param dto /
     * @param orderEntity
     * @return OrderRefundDTO
     */
    OrderRefundDTO refund(OrderRefundDTO dto, OrderEntity orderEntity);

    /**
     * 计算余额
     * 余额=付款-退款
     *
     * @param orderId 订单ID
     * @param shopId
     * @return
     */
    OrderBalanceDTO balance(Long orderId, Long shopId);
    /**
     * 微信退款回调通知
     *  @param result
     *
     */
    void parseRefundNotifyResult(PayRefundNotifyResultVO result);
}
