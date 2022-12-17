package top.pippen.order.bean.excel.biz;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Data
public class ProductExcel {
    @Excel(name = "产品ID")
    private Long id;
    @Excel(name = "商品名称")
    private String productName;
    @Excel(name = "店铺id")
    private Long shopId;
    @Excel(name = "原价")
    private BigDecimal originalPrice;
    @Excel(name = "现价")
    private BigDecimal price;
    @Excel(name = "简要描述,卖点等")
    private String point;
    @Excel(name = "详细描述")
    private String content;
    @Excel(name = "商品主图")
    private String pic;
    @Excel(name = "商品图片，以,分割")
    private String imgs;
    @Excel(name = "商品分类")
    private Long categoryId;
    @Excel(name = "销量")
    private Integer soldNum;
    @Excel(name = "总库存")
    private Integer totalStocks;
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
