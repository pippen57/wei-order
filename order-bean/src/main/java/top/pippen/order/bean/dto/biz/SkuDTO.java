package top.pippen.order.bean.dto.biz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import java.math.BigDecimal;

/**
 * SKU
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-11
 */
@Data
@ApiModel(value = "SKU")
public class SkuDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "单品ID")
	private Long id;

	@ApiModelProperty(value = "商品ID")
	private Long productId;

	@ApiModelProperty(value = "销售属性组合字符串 格式是p1:v1;p2:v2")
	private String properties;

	@ApiModelProperty(value = "原价")
	private BigDecimal originalPrice;

	@ApiModelProperty(value = "价格")
	private BigDecimal price;

	@ApiModelProperty(value = "销量")
	private Integer sale;

	@ApiModelProperty(value = "库存")
	private Integer stocks;

	@ApiModelProperty(value = "sku图片")
	private String pic;

	@ApiModelProperty(value = "sku名称")
	private String skuName;

	@ApiModelProperty(value = "商品名称")
	private String productName;

	@ApiModelProperty(value = "默认是1，表示正常状态, -1表示删除, 0下架")
	private Integer status;

	@ApiModelProperty(value = "创建者")
	private Long creator;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "更新者")
	private Long updater;

	@ApiModelProperty(value = "更新时间")
	private Date updateDate;


}
