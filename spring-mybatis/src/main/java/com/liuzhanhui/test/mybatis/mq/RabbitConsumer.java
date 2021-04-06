package com.liuzhanhui.test.mybatis.mq;

import com.alibaba.fastjson.JSONObject;
import com.liuzhanhui.test.mybatis.constant.Constants;
import com.liuzhanhui.test.mybatis.dto.JmeterMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.io.IOException;


@Slf4j
@Configuration
@EnableRabbit
public class RabbitConsumer implements RabbitListenerConfigurer {

    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private WebSocketServerEndpoint webSocketServerEndpoint;

    @Bean
    public DefaultMessageHandlerMethodFactory handlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }

    @Bean
    public SimpleMessageListenerContainer messageContainer(@Qualifier("jmeterQueue") Queue jmeterQueue, @Qualifier("finishQueue") Queue finishQueue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(jmeterQueue, finishQueue);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                log.info("receive msg : " + new String(body));
                JmeterMessage jmeterMessage = JSONObject.parseObject(new String(body), JmeterMessage.class);
                switch (jmeterMessage.getExchange()) {
                    case Constants.FINISH_EXCHANGE_NAME:
                        try {
                            // todo 需求结合登录接口获取登录userId，单独发送
                            LocalSession.sendMessage(LocalSession.localSession.get("1"), jmeterMessage);
                            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//确认消息成功消费
                        } catch (IOException e) {
                            log.error("消息推送前台出错：" + e.getMessage() + "/r/n重新发送");
                            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); //重新发送
                        }
                        break;
                    case Constants.JMETER_EXCHANGE_NAME:
                        try {
                            // 订阅群发
                            LocalSession.sendMessageToAll(new String(body));
                            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//确认消息成功消费
                        } catch (IOException e) {
                            log.error("消息推送前台出错：" + e.getMessage() + "/r/n重新发送");
                            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); //重新发送
                        }
                        break;
                }



            }
        });
        return container;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(handlerMethodFactory());
    }
}
