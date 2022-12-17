package top.pippen.order.bean.dto.biz;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单项
 *
 * @author Pippen lipenghxgg@163.com
 * @since 1.0.0 2022-10-31
 */
@Data
@ApiModel(value = "订单项")
public class OrderItemDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单项ID")
    private Long id;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "产品SkuID")
    private Long skuId;

    @ApiModelProperty(value = "产品个数")
    private Integer productCount;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "sku名称")
    private String skuName;

    @ApiModelProperty(value = "产品主图片路径")
    private String pic;

    @ApiModelProperty(value = "产品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "商品总金额")
    private BigDecimal productTotalAmount;

    @ApiModelProperty(value = "加入购物车时间")
    private Date basketDate;

    public OrderItemDTO(CartItemDTO item, Long shopId, Long userId, Long orderId,ProductDTO productDTO) {
        SkuDTO sku = item.getSku();
        BigDecimal price;
        if (ObjectUtil.isNull(sku)){
            price = productDTO.getPrice();
            this.skuName = "";
            this.pic = item.getProduct().getPic() ;
        }else {
            price = sku.getPrice();
            this.skuName = sku.getSkuName();
            this.pic = StringUtils.isBlank(sku.getPic()) ? item.getProduct().getPic() : sku.getPic();
        }
        Integer quantity = item.getQuantity();
        this.shopId = shopId;
        this.orderId = orderId;
        this.productId = item.getProductId();
        this.skuId = item.getSkuId();
        this.productCount = quantity;
        this.productName = item.getProduct().getProductName();

        this.price = price;
        this.userId = userId;
        this.productTotalAmount = NumberUtil.mul(price, quantity);
        this.basketDate = item.getCreateDate();
    }

    public OrderItemDTO() {
    }
}
