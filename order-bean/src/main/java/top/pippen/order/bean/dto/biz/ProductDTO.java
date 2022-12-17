package top.pippen.order.bean.dto.biz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.pippen.order.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Data
@ApiModel(value = "商品")
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "产品ID")
	@NotNull(message = "参数异常",groups = {UpdateGroup.class})
	private Long id;

	@NotBlank(message = "商品名称不能为空")
	@Size(max = 200, message = "商品名称长度应该小于{max}")
	@ApiModelProperty(value = "商品名称")
	private String productName;

	@ApiModelProperty(value = "店铺id")
	private Long shopId;

	@NotNull(message = "请输入商品原价")
	@ApiModelProperty(value = "原价")
	private BigDecimal originalPrice;

	@NotNull(message = "请输入商品价格")
	@ApiModelProperty(value = "现价")
	private BigDecimal price;

	@Size(max = 500, message = "商品卖点长度应该小于{max}")
	@ApiModelProperty(value = "简要描述,卖点等")
	private String point;

	@ApiModelProperty(value = "详细描述")
	private String content;

	@NotBlank(message = "请选择图片上传")
	@ApiModelProperty(value = "商品主图")
	private String pic;

	@NotBlank(message = "请选择图片上传")
	@ApiModelProperty(value = "商品图片，以,分割")
	private String imgs;

	@NotNull(message = "请选择商品分类")
	@ApiModelProperty(value = "商品分类")
	private Long categoryId;

	private String categoryName;


	@ApiModelProperty(value = "销量")
	private Integer soldNum;

	@NotNull(message = "请输入商品库存")
	@ApiModelProperty(value = "总库存")
	private Integer totalStocks;

	@ApiModelProperty(value = "默认是1，表示正常状态, -1表示删除, 0下架")
	private Integer status;

	@ApiModelProperty(value = "创建者")
	private Long creator;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	private List<SkuDTO> skuList;

	private Integer badge = 0;

}
