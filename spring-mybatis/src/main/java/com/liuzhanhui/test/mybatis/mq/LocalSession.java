package com.liuzhanhui.test.mybatis.mq;

import com.alibaba.fastjson.JSONObject;
import com.liuzhanhui.test.mybatis.dto.JmeterMessage;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class LocalSession {

    public static Map<String, Session> localSession = new ConcurrentHashMap<>();

    public static void addSession (String userId, Session session) {
        localSession.put(userId, session);
    }

    public static void delSession (Session session) throws IOException {
        for (String userId : localSession.keySet()) {
            if (!"".equals(userId) && null != userId) {
                if (localSession.get(userId) != null) {
                    localSession.get(userId).close();
                    localSession.remove(userId);
                }
            }
        }
    }

    public static void sendMessage (Session session, JmeterMessage message) {
        try {
            session.getBasicRemote().sendText(message.getContents());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageToAll (String message) {
        log.info("发送的消息： {}",  message);
        JmeterMessage jmeterMessage = JSONObject.parseObject(message, JmeterMessage.class);
        localSession.forEach(
                (userId, session) -> {
                    sendMessage(session, jmeterMessage);
                }
        );
    }
}
