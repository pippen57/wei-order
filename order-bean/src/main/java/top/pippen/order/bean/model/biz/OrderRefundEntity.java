package top.pippen.order.bean.model.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付退款
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-11-11
 */
@Data
@TableName("biz_order_refund")
public class OrderRefundEntity {

    /**
     * 记录ID
     */
	private Long id;
    /**
     * 店铺ID
     */
	private Long shopId;
    /**
     * 订单ID
     */
	private Long orderId;
    /**
     * 订单流水号
     */
	private String orderNumber;
    /**
     * 订单总金额
     */
	private BigDecimal orderAmount;
    /**
     * 订单项ID 全部退款是0
     */
	private String orderItemId;
    /**
     * 退款编号
     */
	private String refundNumber;
    /**
     * 订单支付流水号
     */
	private String payNo;
    /**
     * 微信退款单号
     */
	private String refundId;
    /**
     * 订单支付方式 1 微信支付 2 支付宝
     */
	private Integer payType;
    /**
     * 订单支付名称
     */
	private String payTypeName;
    /**
     * 买家ID
     */
	private Long userId;
    /**
     * 退货数量
     */
	private Integer goodsNum;
    /**
     * 退款金额
     */
	private BigDecimal refundAmount;
    /**
     * 处理退款状态: 0:退款处理中 1:退款成功 -1:退款失败
     */
	private Integer returnMoneySts;
    /**
     * 申请时间
     */
	private Date createTime;
    /**
     * 退款时间
     */
	private Date refundTime;
    /**
     * 申请原因
     */
	private String buyerMsg;
    /**
     * 退款所使用资金对应的资金账户类型
        枚举值：
        UNSETTLED : 未结算资金
        AVAILABLE : 可用余额
        UNAVAILABLE : 不可用余额
        OPERATION : 运营户
        BASIC : 基本账户（含可用余额和不可用余额）
     */
	private String fundsAccount;
    /**
     * 退款入账账户
     */
	private String userReceivedAccount;
}
