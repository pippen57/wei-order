package top.pippen.order.bean.excel.biz;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * SKU
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-11
 */
@Data
public class SkuExcel {
    @Excel(name = "单品ID")
    private Long skuId;
    @Excel(name = "商品ID")
    private Long productId;
    @Excel(name = "销售属性组合字符串 格式是p1:v1;p2:v2")
    private String properties;
    @Excel(name = "原价")
    private BigDecimal originalPrice;
    @Excel(name = "价格")
    private BigDecimal price;
    @Excel(name = "销量")
    private Integer sale;
    @Excel(name = "sku图片")
    private String pic;
    @Excel(name = "sku名称")
    private String skuName;
    @Excel(name = "商品名称")
    private String prodName;
    @Excel(name = "默认是1，表示正常状态, -1表示删除, 0下架")
    private Integer status;
    @Excel(name = "创建者")
    private Long creator;
    @Excel(name = "创建时间")
    private Date createDate;
    @Excel(name = "更新者")
    private Long updater;
    @Excel(name = "更新时间")
    private Date updateDate;

}
