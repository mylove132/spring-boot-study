package com.liuzhanhui.test.mybatis.mq;

import com.alibaba.fastjson.JSONObject;
import com.liuzhanhui.test.mybatis.constant.Constants;
import com.liuzhanhui.test.mybatis.dto.JmeterMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
@Slf4j
public class RabbitProvider implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init () {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        log.info("消息发送成功:" + correlationData);
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        log.error("消息发送失败:" + new String(message.getBody()));
    }

    public void send (JmeterMessage jmeterMessage) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(jmeterMessage.getExchange(), jmeterMessage.getRoutingKey(), JSONObject.toJSON(jmeterMessage).toString(), correlationId);
    }
}
