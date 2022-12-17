package top.pippen.order.bean.excel.biz;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 商品分类
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
@Data
public class CategoryExcel {
    @Excel(name = "类目ID")
    private Long id;
    @Excel(name = "店铺ID")
    private Long shopId;
    @Excel(name = "父节点")
    private Long parentId;
    @Excel(name = "产品类目名称")
    private String categoryName;
    @Excel(name = "类目图标")
    private String icon;
    @Excel(name = "类目的显示图片")
    private String pic;
    @Excel(name = "排序")
    private Integer seq;
    @Excel(name = "状态  0：停用   1：正常")
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
