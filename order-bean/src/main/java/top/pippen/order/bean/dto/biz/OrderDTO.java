package top.pippen.order.bean.dto.biz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.pippen.order.common.validator.group.AddGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@Data
@ApiModel(value = "订单")
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@NotNull(message = "超时,请重新下单",groups = {AddGroup.class})
	private String preId;

	@ApiModelProperty(value = "订单ID")
	private Long id;

	@ApiModelProperty(value = "店铺id")
	@NotNull(message = "请选择店铺",groups = {AddGroup.class})
	private Long shopId;

	@ApiModelProperty(value = "产品名称,多个产品将会以逗号隔开")
	private String prodName;

	@ApiModelProperty(value = "用户ID")
	@NotNull(message = "参数异常",groups = {AddGroup.class})
	private Long userId;

	@ApiModelProperty(value = "订购流水号，biz系统生成")
	private String orderNumber;

	@ApiModelProperty(value = "订单总金额")
	private BigDecimal total;

	@ApiModelProperty(value = "支付方式 0 手动代付 1 微信支付 2 支付宝")
	private Integer payType;

	@ApiModelProperty(value = "订单备注")
	private String remarks;

	@ApiModelProperty(value = "订单状态 1:待付款 2:成功 3:失败")
	private Integer status;

	@ApiModelProperty(value = "订单商品总数")
	private Integer productNums;

	@ApiModelProperty(value = "订购时间")
	private Date createTime;

	@ApiModelProperty(value = "订单更新时间")
	private Date updateTime;

	@ApiModelProperty(value = "付款时间")
	private Date payTime;

	@ApiModelProperty(value = "完成时间")
	private Date finallyTime;

	@ApiModelProperty(value = "取消时间")
	private Date cancelTime;

	@ApiModelProperty(value = "是否已经支付，1：已经支付过，0：，没有支付过")
	private Integer isPayed;

	@ApiModelProperty(value = "用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除")
	private Integer deleteStatus;

	@ApiModelProperty(value = "0:默认,1:在处理,2:处理完成")
	private Integer refundStatus;

	@ApiModelProperty(value = "订单关闭原因 1-超时未支付 2-退款关闭 4-买家取消")
	private Integer closeType;

	@ApiModelProperty(value = "订单来源 1：微信小程序 2： 微信公众号 3：支付宝小程序")
	private Integer orderSource;

	@ApiModelProperty(value = "取餐时间")
	private String mealTime;


	/**
	 * 取餐号
	 */
	@ApiModelProperty(value = "取餐号")
	private String mealNumber;

	/**
	 * 就餐方式
	 */
	@ApiModelProperty(value = "就餐方式")
	private Integer mealType;


}
