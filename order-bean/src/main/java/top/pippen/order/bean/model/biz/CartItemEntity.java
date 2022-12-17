package top.pippen.order.bean.model.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 购物车
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-22
 */
@Data
@TableName("biz_cart_item")
public class CartItemEntity {

    /**
     * 主键
     */
	private Long id;
    /**
     * 店铺ID
     */
	private Long shopId;
    /**
     * 产品ID
     */
	private Long productId;
    /**
     * SKUID
     */
	private Long skuId;
    /**
     * 用户ID
     */
	private Long userId;
    /**
     * 购买数量
     */
	private Integer quantity;
    /**
     * 创建时间
     */
	private Date createDate;
}
