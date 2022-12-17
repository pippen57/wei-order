package top.pippen.order.bean.model.biz;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.pippen.order.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * SKU
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-11
 */
@Data
@TableName("biz_sku")
@EqualsAndHashCode(callSuper = false)
public class SkuEntity extends BaseEntity {

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;
    /**
     * 销售属性组合字符串 格式是p1:v1;p2:v2
     */
    private String properties;
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 销量
     */
    private Integer sale;
    /**
     * 库存
     */
    private Integer stocks;
    /**
     * sku图片
     */
    private String pic;

    private String skuName;

    /**
     * 默认是1，表示正常状态, -1表示删除, 0下架
     */
    private Integer status;
    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
}
