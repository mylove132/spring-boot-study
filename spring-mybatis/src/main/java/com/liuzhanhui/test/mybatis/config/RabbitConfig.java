package com.liuzhanhui.test.mybatis.config;

import com.liuzhanhui.test.mybatis.constant.Constants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mq 队列，交换机,路由配置
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue jmeterQueue() {
        return new Queue(Constants.JMETER_QUEUE_NAME, true);
    }

    @Bean
    public Queue finishQueue() {
        return new Queue(Constants.FINISH_QUEUE_NAME, true);
    }

    @Bean
    DirectExchange jmeterDirectExchange() {
        return new DirectExchange(Constants.JMETER_EXCHANGE_NAME, true, false);
    }

    @Bean
    DirectExchange finishDirectExchange() {
        return new DirectExchange(Constants.FINISH_EXCHANGE_NAME, true, false);
    }

    //绑定jmeter路由
    @Bean
    Binding bindingJmeterDirect() {
        return BindingBuilder.bind(jmeterQueue()).to(jmeterDirectExchange()).with(Constants.RK_JMETER);
    }

    //绑定finish路由
    @Bean
    Binding bindingFinishDirect() {
        return BindingBuilder.bind(finishQueue()).to(finishDirectExchange()).with(Constants.RK_FINISH);
    }

}
