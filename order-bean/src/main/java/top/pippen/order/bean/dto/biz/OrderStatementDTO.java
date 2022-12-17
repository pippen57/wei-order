package top.pippen.order.bean.dto.biz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import java.math.BigDecimal;

/**
 * 支付清算
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@Data
@ApiModel(value = "支付清算")
public class OrderStatementDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "支付结算单据ID")
	private Long id;

	@ApiModelProperty(value = "支付单号")
	private String payNo;

	@ApiModelProperty(value = "order表中的订单号")
	private String orderNumber;

	@ApiModelProperty(value = "支付方式 1 微信支付 2 支付宝")
	private Integer payType;

	@ApiModelProperty(value = "支付方式名称")
	private String payTypeName;

	@ApiModelProperty(value = "支付金额")
	private BigDecimal payAmount;

	@ApiModelProperty(value = "是否清算 0:否 1:是")
	private Integer isClearing;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "清算时间")
	private Date clearingTime;

	@ApiModelProperty(value = "支付状态 1-支付成功")
	private Integer payStatus;


}
