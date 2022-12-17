package top.pippen.order.bean.dto.biz;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Pippen
 * @since 2022/11/12 16:38
 */
@Data
public class OrderBalanceDTO {

    private BigDecimal total;
    private BigDecimal totalBalance;
    private Boolean refund;
}
