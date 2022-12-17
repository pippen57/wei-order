package top.pippen.order.msg.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static top.pippen.order.msg.constant.MQPrefixConst.*;


/**
 * Rabbitmq配置类
 *
 * @author pippen
 */
@Configuration
public class RabbitMQConfig {


    @Bean
    public Queue orderSubmitQueue() {
        return new Queue(ORDER_SUBMIT_QUEUE, true);
    }

    @Bean
    public FanoutExchange orderSubmitExchange() {
        return new FanoutExchange(ORDER_SUBMIT_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingOrderSubmitDirect() {
        return BindingBuilder.bind(orderSubmitQueue()).to(orderSubmitExchange());
    }


    @Bean
    public Queue orderStatusQueue() {
        return new Queue(ORDER_STATUS_QUEUE, true);
    }

    @Bean
    public FanoutExchange orderStatusExchange() {
        return new FanoutExchange(ORDER_STATUS_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingOrderStatusDirect() {
        return BindingBuilder.bind(orderStatusQueue()).to(orderStatusExchange());
    }
}
