package top.pippen.order.bean.dto.biz;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pippen
 * @since 2022/10/24 10:11
 */
@Data
public class APIProductDTO {

    private Long categoryId;


    private String categoryName;

    private List<ProductBean> productList = new ArrayList<>();


    @Data
    public static class ProductBean {
        private Long id;
        /**
         * 商品名
         */
        private String productName;
        /**
         * 商品价格
         */
        private BigDecimal price;
        /**
         * 商品结束
         */
        private String point;
        /**
         * 商品照片
         */
        private String pic;

        /**
         * 商品销量
         */
        private Integer soldNum;
        /**
         * sku
         */
        private List<SkuDTO> skuList = new ArrayList<>();


    }
}
