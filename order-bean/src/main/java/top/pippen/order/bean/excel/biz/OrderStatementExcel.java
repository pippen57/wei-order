package top.pippen.order.bean.excel.biz;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
public class OrderStatementExcel {
    @Excel(name = "支付结算单据ID")
    private Long id;
    @Excel(name = "支付单号")
    private String payNo;
    @Excel(name = "order表中的订单号")
    private String orderNumber;
    @Excel(name = "支付方式 1 微信支付 2 支付宝")
    private Integer payType;
    @Excel(name = "支付方式名称")
    private String payTypeName;
    @Excel(name = "支付金额")
    private BigDecimal payAmount;
    @Excel(name = "是否清算 0:否 1:是")
    private Integer isClearing;
    @Excel(name = "用户ID")
    private String userId;
    @Excel(name = "创建时间")
    private Date createTime;
    @Excel(name = "清算时间")
    private Date clearingTime;
    @Excel(name = "支付状态 1-支付成功")
    private Integer payStatus;

}
