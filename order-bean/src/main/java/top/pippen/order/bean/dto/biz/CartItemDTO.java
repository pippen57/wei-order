package top.pippen.order.bean.dto.biz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.pippen.order.common.validator.group.AddGroup;
import top.pippen.order.common.validator.group.UpdateGroup;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 购物车
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-22
 */
@Data
@ApiModel(value = "购物车")
public class CartItemDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	@NotNull(groups = {UpdateGroup.class})
	private Long id;

	@ApiModelProperty(value = "店铺ID")
	@NotNull(message = "参数异常",groups = {AddGroup.class,UpdateGroup.class})
	private Long shopId;

	@ApiModelProperty(value = "产品ID")
	@NotNull(message = "请选择产品",groups = {AddGroup.class,UpdateGroup.class})
	private Long productId;

	@ApiModelProperty(value = "SKUID")
	private Long skuId;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "购买数量")
	@Max(value = 99,message = "购买数量超限")
	@Min(value = 1,message = "参数不正确" ,groups = {AddGroup.class})
	private Integer quantity;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;


	private SkuDTO sku;

	private ProductDTO product;

	private BigDecimal totalPrice;


}
