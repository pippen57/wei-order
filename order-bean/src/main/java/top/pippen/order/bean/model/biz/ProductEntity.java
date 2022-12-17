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
 * 商品
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Data
@TableName("biz_product")
@EqualsAndHashCode(callSuper=false)
public class ProductEntity extends BaseEntity {

    /**
     * 商品名称
     */
	private String productName;
    /**
     * 店铺id
     */
	private Long shopId;
    /**
     * 原价
     */
	private BigDecimal originalPrice;
    /**
     * 现价
     */
	private BigDecimal price;
    /**
     * 卖点等
     */
	private String point;
    /**
     * 详细描述
     */
	private String content;
    /**
     * 商品主图
     */
	private String pic;
    /**
     * 商品图片，以,分割
     */
	private String imgs;
    /**
     * 商品分类
     */
	private Long categoryId;
    /**
     * 销量
     */
	private Integer soldNum;
    /**
     * 总库存
     */
	private Integer totalStocks;
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
