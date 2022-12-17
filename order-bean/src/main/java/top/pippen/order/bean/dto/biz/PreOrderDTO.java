package top.pippen.order.bean.dto.biz;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Pippen
 * @since 2022/11/01 11:07
 */
@Data
public class PreOrderDTO {

    /**
     * 商品列表
     */
    private List<CartItemDTO> prodList;

    /**
     * 用户联系电话
     */
    private String userPhone;


    /**
     * 总件数
     */
    private Integer totalCount;

    /**
     * 总金额
     */
    private BigDecimal totalMoney;


    /**
     * 商家信息
     */
    private ShopDTO shop;

     private String preId;



}
