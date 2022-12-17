package top.pippen.order.bean.model.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付清算
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@Data
@TableName("biz_order_statement")
public class OrderStatementEntity {

    /**
     * 支付结算单据ID
     */
	private Long id;
    /**
     * 支付单号
     */
	private String payNo;
    /**
     * order表中的订单号
     */
	private String orderNumber;
    /**
     * 支付方式 1 微信支付 2 支付宝
     */
	private Integer payType;
    /**
     * 支付方式名称
     */
	private String payTypeName;
    /**
     * 支付金额
     */
	private BigDecimal payAmount;
    /**
     * 是否清算 0:否 1:是
     */
	private Integer isClearing;
    /**
     * 用户ID
     */
	private Long userId;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 清算时间
     */
	private Date clearingTime;
    /**
     * 支付状态 1-支付成功
     */
	private Integer payStatus;
}
