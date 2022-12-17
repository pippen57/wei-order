package top.pippen.order.bean.excel.biz;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 规格
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Data
public class ProductPropExcel {
    @Excel(name = "属性id")
    private Long id;
    @Excel(name = "属性名称")
    private String propName;
    @Excel(name = "店铺id")
    private Long shopId;
    @Excel(name = "创建者")
    private Long creator;
    @Excel(name = "创建时间")
    private Date createDate;
    @Excel(name = "更新者")
    private Long updater;
    @Excel(name = "更新时间")
    private Date updateDate;

}
