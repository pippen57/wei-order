package top.pippen.order.bean.model.biz;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.pippen.order.common.entity.BaseEntity;

import java.util.Date;

/**
 * 商品分类
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
@Data
@TableName("biz_category")
@EqualsAndHashCode(callSuper=false)
public class CategoryEntity extends BaseEntity {

    /**
     * 店铺ID
     */
	private Long shopId;
    /**
     * 父节点
     */
    @TableField(value = "parent_id")
	private Long pid;
    /**
     * 产品类目名称
     */
	private String categoryName;
    /**
     * 类目图标
     */
	private String icon;
    /**
     * 类目的显示图片
     */
	private String pic;
    /**
     * 排序
     */
	private Integer seq;
    /**
     * 状态  0：停用   1：正常
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
