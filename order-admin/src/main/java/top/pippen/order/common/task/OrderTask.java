package top.pippen.order.common.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.github.binarywang.wxpay.exception.WxPayException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.pippen.order.bean.model.biz.OrderEntity;
import top.pippen.order.common.constant.Constant;
import top.pippen.order.common.redis.RedisKeys;
import top.pippen.order.common.redis.RedisUtils;
import top.pippen.order.job.task.ITask;
import top.pippen.order.msg.constant.MQPrefixConst;
import top.pippen.order.pay.service.PayService;
import top.pippen.order.service.OrderService;
import top.pippen.order.service.SysParamsService;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * @author Pippen
 * @since 2022/11/02 16:56
 */
@Component("orderTask")
public class OrderTask implements ITask {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;
    @Autowired
    private SysParamsService sysParamsService;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public void run(String params) {

        Date now = new Date();
        String value = sysParamsService.getValue(Constant.ORDER_CANCEL_KEY);

        int time = Integer.parseInt(StringUtils.isBlank(value) ? "5" : value);
        DateTime dateTime = DateUtil.offsetMinute(now, -time);

        List<OrderEntity> obligationOrder = orderService.getObligationOrder(dateTime);
        if (CollectionUtil.isNotEmpty(obligationOrder)){
            for (OrderEntity orderEntity : obligationOrder) {
                try {
                    payService.closeOrder(orderEntity.getOrderNumber());
                    redisUtils.delete(RedisKeys.getOrderWxPayPrepayIdKey(orderEntity.getOrderNumber()));
                    orderService.obligationOrder(orderEntity);
                    rabbitTemplate.convertAndSend(MQPrefixConst.ORDER_STATUS_EXCHANGE, "*",
                            new Message(JSONUtil.toJsonStr(orderEntity).getBytes(StandardCharsets.UTF_8), new MessageProperties()));

                } catch (WxPayException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
