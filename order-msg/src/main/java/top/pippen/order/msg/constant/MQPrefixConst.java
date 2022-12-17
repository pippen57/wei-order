package top.pippen.order.msg.constant;

/**
 * mqprefix常量
 * mq常量
 *
 * @author pippen
 */
public class MQPrefixConst {


    /**
     * 订单成功交换机
     */
    public static final String ORDER_SUBMIT_EXCHANGE = "order_submit_exchange";

    /**
     * 订单成功队列
     */
    public static final String ORDER_SUBMIT_QUEUE = "order_submit_queue";


    /**
     * 订单状态交换机
     */
    public static final String ORDER_STATUS_EXCHANGE = "order_status_exchange";

    /**
     * 订单状态队列
     */
    public static final String ORDER_STATUS_QUEUE = "order_status_queue";

}
