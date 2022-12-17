package top.pippen.order.bean.model.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@Data
@TableName("biz_order")
public class OrderEntity {

    /**
     * 订单ID
     */
	private Long id;
    /**
     * 店铺id
     */
	private Long shopId;
    /**
     * 产品名称,多个产品将会以逗号隔开
     */
	private String prodName;
    /**
     * 用户ID
     */
	private Long userId;
    /**
     * 订购流水号，biz系统生成
     */
	private String orderNumber;
    /**
     * 订单总金额
     */
	private BigDecimal total;
    /**
     * 支付方式 0 手动代付 1 微信支付 2 支付宝
     */
	private Integer payType;
    /**
     * 订单备注
     */
	private String remarks;
    /**
     * 订单状态 1:待付款  3:成功 4:失败
     */
	private Integer status;
    /**
     * 订单商品总数
     */
	private Integer productNums;
    /**
     * 订购时间
     */
	private Date createTime;
    /**
     * 订单更新时间
     */
	private Date updateTime;
    /**
     * 付款时间
     */
	private Date payTime;

    /**
     * 完成时间
     */
	private Date finallyTime;
    /**
     * 取消时间
     */
	private Date cancelTime;
    /**
     * 是否已经支付，1：已经支付过，0：，没有支付过
     */
	private Integer isPayed;
    /**
     * 用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除
     */
	private Integer deleteStatus;
    /**
     * 0:默认,1:在处理,2:处理完成
     */
	private Integer refundStatus;
    /**
     * 订单关闭原因 1-超时未支付 2-退款关闭 4-买家取消
     */
	private Integer closeType;
    /**
     * 订单来源 1：微信小程序 2： 微信公众号 3：支付宝小程序
     */
	private Integer orderSource;
    /**
     * 取餐时间
     */
	private String mealTime;

    /**
     * 取餐号
     */
    private String mealNumber;

    /**
     * 就餐方式
     */
    private Integer mealType;
}
