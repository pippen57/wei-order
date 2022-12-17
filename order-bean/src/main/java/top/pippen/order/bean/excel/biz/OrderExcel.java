package top.pippen.order.bean.excel.biz;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
public class OrderExcel {
    @Excel(name = "订单ID")
    private Long id;
    @Excel(name = "店铺id")
    private Long shopId;
    @Excel(name = "产品名称,多个产品将会以逗号隔开")
    private String prodName;
    @Excel(name = "用户ID")
    private String userId;
    @Excel(name = "订购流水号，biz系统生成")
    private String orderNumber;
    @Excel(name = "订单总金额")
    private BigDecimal total;
    @Excel(name = "支付方式 0 手动代付 1 微信支付 2 支付宝")
    private Integer payType;
    @Excel(name = "订单备注")
    private String remarks;
    @Excel(name = "订单状态 1:待付款  2:待评价 3:成功 4:失败")
    private Integer status;
    @Excel(name = "订单商品总数")
    private Integer productNums;
    @Excel(name = "订购时间")
    private Date createTime;
    @Excel(name = "订单更新时间")
    private Date updateTime;
    @Excel(name = "付款时间")
    private Date payTime;
    @Excel(name = "发货时间")
    private Date deliverTime;
    @Excel(name = "完成时间")
    private Date finallyTime;
    @Excel(name = "取消时间")
    private Date cancelTime;
    @Excel(name = "是否已经支付，1：已经支付过，0：，没有支付过")
    private Integer isPayed;
    @Excel(name = "用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除")
    private Integer deleteStatus;
    @Excel(name = "0:默认,1:在处理,2:处理完成")
    private Integer refundStatus;
    @Excel(name = "订单关闭原因 1-超时未支付 2-退款关闭 4-买家取消")
    private Integer closeType;
    @Excel(name = "订单来源 1：微信小程序 2： 微信公众号 3：支付宝小程序")
    private Integer orderSource;
    @Excel(name = "取餐时间")
    private Date mealTime;

}
