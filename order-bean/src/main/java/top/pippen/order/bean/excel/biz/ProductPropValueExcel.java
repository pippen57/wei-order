package top.pippen.order.bean.excel.biz;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 规格值
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Data
public class ProductPropValueExcel {
    @Excel(name = "属性值ID")
    private Long id;
    @Excel(name = "属性值名称")
    private String propValue;
    @Excel(name = "属性ID")
    private Long propId;
    @Excel(name = "创建者")
    private Long creator;
    @Excel(name = "创建时间")
    private Date createDate;
    @Excel(name = "更新者")
    private Long updater;
    @Excel(name = "更新时间")
    private Date updateDate;

}
