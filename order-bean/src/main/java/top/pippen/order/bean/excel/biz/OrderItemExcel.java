package top.pippen.order.bean.excel.biz;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单项
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@Data
public class OrderItemExcel {
    @Excel(name = "订单项ID")
    private Long id;
    @Excel(name = "店铺id")
    private Long shopId;
    @Excel(name = "订单ID ")
    private Long orderId;
    @Excel(name = "产品ID")
    private Long productId;
    @Excel(name = "产品SkuID")
    private Long skuId;
    @Excel(name = "产品个数")
    private Integer productCount;
    @Excel(name = "产品名称")
    private String productName;
    @Excel(name = "sku名称")
    private String skuName;
    @Excel(name = "产品主图片路径")
    private String pic;
    @Excel(name = "产品价格")
    private BigDecimal price;
    @Excel(name = "用户Id")
    private String userId;
    @Excel(name = "商品总金额")
    private BigDecimal productTotalAmount;
    @Excel(name = "加入购物车时间")
    private Date basketDate;

}
