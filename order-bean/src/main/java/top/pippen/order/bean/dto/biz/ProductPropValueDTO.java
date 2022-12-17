package top.pippen.order.bean.dto.biz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * 规格值
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Data
@ApiModel(value = "规格值")
public class ProductPropValueDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "规格值ID")
	private Long id;

	@ApiModelProperty(value = "规格值名称")
	@NotBlank(message = "规格值不能为空")
	private String propValue;

	@ApiModelProperty(value = "规格ID")
	private Long propId;


}
