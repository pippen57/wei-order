package top.pippen.order.bean.dto.biz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.pippen.order.common.validator.group.AddGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 支付退款
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-11-11
 */
@Data
@ApiModel(value = "支付退款")
public class OrderRefundDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "记录ID")
	private Long id;

	@ApiModelProperty(value = "店铺ID")
	@NotNull(message = "请选择店铺",groups = AddGroup.class)
	private Long shopId;

	@ApiModelProperty(value = "订单ID")
	@NotNull(message = "参数异常",groups = AddGroup.class)
	private Long orderId;

	@ApiModelProperty(value = "订单流水号")
	private String orderNumber;

	@ApiModelProperty(value = "订单总金额")
	private BigDecimal orderAmount;

	@ApiModelProperty(value = "订单项ID 全部退款是0")
	private String orderItemId;

	@ApiModelProperty(value = "退款编号")
	private String refundNumber;

	@ApiModelProperty(value = "订单支付流水号")
	private String payNo;

	@ApiModelProperty(value = "第三方退款单号(微信退款单号)")
	private String refundId;

	@ApiModelProperty(value = "订单支付方式 1 微信支付 2 支付宝")
	private Integer payType;

	@ApiModelProperty(value = "订单支付名称")
	private String payTypeName;

	@ApiModelProperty(value = "买家ID")
	private Long userId;

	@ApiModelProperty(value = "退货数量")
	private Integer goodsNum;

	@ApiModelProperty(value = "退款金额")
	@NotNull(message = "退款金额不能为空",groups = AddGroup.class)
	private BigDecimal refundAmount;

	@ApiModelProperty(value = "处理退款状态: 0:退款处理中 1:退款成功 -1:退款失败")
	private Integer returnMoneySts;

	@ApiModelProperty(value = "申请时间")
	private Date createTime;

	@ApiModelProperty(value = "退款时间")
	private Date refundTime;

	@ApiModelProperty(value = "申请原因")
	private String buyerMsg;

	@ApiModelProperty(value = "退款所使用资金对应的资金账户类型")
	private String fundsAccount;

	@ApiModelProperty(value = "退款入账账户")
	private String userReceivedAccount;

	/**
	 * 订单项ID
	 */
	private List<String> orderItems;
	/**
	 * 用户信息
	 */
	private String username;
	private String mobile;
	private String avatar;


}
