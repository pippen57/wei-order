package top.pippen.order.bean.model.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单项
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@Data
@TableName("biz_order_item")
public class OrderItemEntity {

    /**
     * 订单项ID
     */
	private Long id;
    /**
     * 店铺id
     */
	private Long shopId;
    /**
     * 订单ID

     */
	private Long orderId;
    /**
     * 产品ID
     */
	private Long productId;
    /**
     * 产品SkuID
     */
	private Long skuId;
    /**
     * 产品个数
     */
	private Integer productCount;
    /**
     * 产品名称
     */
	private String productName;
    /**
     * sku名称
     */
	private String skuName;
    /**
     * 产品主图片路径
     */
	private String pic;
    /**
     * 产品价格
     */
	private BigDecimal price;
    /**
     * 用户Id
     */
	private Long userId;
    /**
     * 商品总金额
     */
	private BigDecimal productTotalAmount;
    /**
     * 加入购物车时间
     */
	private Date basketDate;
}
