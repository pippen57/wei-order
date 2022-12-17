package top.pippen.order.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Pippen
 * @since 2022/11/23 14:47
 */
@Data
public class ShopListParamDTO {

    private String shopName;

    /**
     * 经度
     */
    @NotBlank(message = "参数异常")
    private String longitude;
    /**
     * 纬度
     */
    @NotBlank(message = "参数异常")
    private String latitude;
}
