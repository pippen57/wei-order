package top.pippen.order.bean.excel.biz;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 购物车
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-22
 */
@Data
public class CartItemExcel {
    @Excel(name = "主键")
    private Long id;
    @Excel(name = "店铺ID")
    private Long shopId;
    @Excel(name = "产品ID")
    private Long productId;
    @Excel(name = "SKUID")
    private Long skuId;
    @Excel(name = "用户ID")
    private String userId;
    @Excel(name = "购买数量")
    private Integer quantity;
    @Excel(name = "创建时间")
    private Date createDate;

}
