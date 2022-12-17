package top.pippen.order.bean.model.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 规格值
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-09
 */
@Data
@TableName("biz_product_prop_value")
public class ProductPropValueEntity {

    /**
     * 属性值ID
     */
	private Long id;
    /**
     * 属性值名称
     */
	private String propValue;
    /**
     * 属性ID
     */
	private Long propId;
}
