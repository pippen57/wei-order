package top.pippen.order.bean.vo;

import lombok.Data;
import top.pippen.order.bean.dto.biz.OrderDTO;
import top.pippen.order.bean.dto.biz.ShopDTO;
import top.pippen.order.bean.model.biz.UserEntity;

import java.util.Map;

/**
 * @author Pippen
 * @since 2022/12/12 11:26
 */
@Data
public class MqOrderMsgVo {


    private ShopDTO shop;

    private OrderDTO order;

    private UserEntity user;

    private Map<String,Object> config;
}
