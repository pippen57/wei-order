package top.pippen.order.common.consumer;

import cn.hutool.json.JSONUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.pippen.order.bean.dto.biz.OrderDTO;
import top.pippen.order.bean.dto.biz.ShopDTO;
import top.pippen.order.bean.model.biz.UserEntity;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.miniapp.service.MsgService;
import top.pippen.order.msg.constant.MQPrefixConst;
import top.pippen.order.service.ShopService;
import top.pippen.order.service.SysParamsService;
import top.pippen.order.service.UserService;

/**
 * @author Pippen
 * @since 2022/12/12 15:10
 */
@Component
@RabbitListener(queues = MQPrefixConst.ORDER_SUBMIT_QUEUE)
public class OrderSuccessConsumer {

    private final ShopService shopService;
    private final UserService userService;
    private final SysParamsService sysParamsService;
    private final MsgService msgService;

    public OrderSuccessConsumer(ShopService shopService, UserService userService, SysParamsService sysParamsService, MsgService msgService) {
        this.shopService = shopService;
        this.userService = userService;
        this.sysParamsService = sysParamsService;
        this.msgService = msgService;
    }

    @RabbitHandler
    public void process(byte[] data){
        OrderDTO orderDTO = JSONUtil.toBean(new String(data), OrderDTO.class);
        UserEntity user = userService.getUserByUserId(orderDTO.getUserId());
        ShopDTO shop = shopService.get(orderDTO.getShopId());
        String tempId = sysParamsService.getValue(Constant.ORDER_SUCCESS_NOTICE_TEMPID);
        msgService.sendOrderSuccessMsg(orderDTO,shop,user,tempId);
    }

}
