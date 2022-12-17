package top.pippen.order.bean.dto.biz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 商品分类
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-09-29
 */
@Data
@ApiModel(value = "商品分类")
public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "类目ID")
	private Long id;

	@ApiModelProperty(value = "店铺ID")
	private Long shopId;

	@ApiModelProperty(value = "父节点")
	private Long pid;

	@ApiModelProperty(value = "父节点名称")
	private String parentName;

	@ApiModelProperty(value = "产品类目名称")
	private String categoryName;

	@ApiModelProperty(value = "类目图标")
	private String icon;

	@ApiModelProperty(value = "类目的显示图片")
	private String pic;

	@ApiModelProperty(value = "排序")
	private Integer seq;

	@ApiModelProperty(value = "状态  0：停用   1：正常")
	private Integer status;

	@ApiModelProperty(value = "创建者")
	private Long creator;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "更新者")
	private Long updater;

	@ApiModelProperty(value = "更新时间")
	private Date updateDate;

	private Integer badge = 0;


}
