package top.pippen.order.bean.dto.biz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.pippen.order.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 规格
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Data
@ApiModel(value = "规格")
public class ProductPropDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "属性id")
	@NotNull(message = "参数有误",groups = UpdateGroup.class)
	private Long id;

	@ApiModelProperty(value = "属性名称")
	@NotBlank(message = "规格名称不能为空")
	private String propName;

	@ApiModelProperty(value = "店铺id")
	private Long shopId;


	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "规格值")
	private List<ProductPropValueDTO> propValue;


}
