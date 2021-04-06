package com.liuzhanhui.test.mybatis.service.impl;
import com.liuzhanhui.test.mybatis.constant.Constants;
import com.liuzhanhui.test.mybatis.dto.JmeterMessage;
import com.liuzhanhui.test.mybatis.dto.Script;
import com.liuzhanhui.test.mybatis.mq.RabbitProvider;
import com.liuzhanhui.test.mybatis.service.IRunService;
import com.liuzhanhui.test.mybatis.service.IScriptService;
import com.liuzhanhui.test.mybatis.utils.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunServiceImp implements IRunService {

    @Autowired
    private IScriptService scriptService;

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private RabbitProvider rabbitProvider;

    @Override
    public void runScript(Long scriptId) {
        Script script = scriptService.queryScriptByIdService(scriptId);
        long value = System.currentTimeMillis() + script.getStressTime() * 60 * 1000;
        // 加锁去执行脚本，防止脚本重复执行
        if (!redisLock.lock(String.valueOf(scriptId), String.valueOf(value))) {
            throw new RuntimeException("脚本正在执行，请稍后重试");
        }
        // 开线程去执行脚本，执行完成，mq事件通知
        new Thread(
                () -> {
                    try {
                        // 执行脚本 模拟脚本的返回结果 每次返回内容都通过mq进行转发
                        for (int i = 0; i < 100; i++) {
                            JmeterMessage jmeterMessage = new JmeterMessage();
                            jmeterMessage.setTitle("-----rok------"+i);
                            jmeterMessage.setContents("------测试消息----------"+i);
                            jmeterMessage.setRoutingKey(Constants.RK_JMETER);
                            jmeterMessage.setExchange(Constants.JMETER_EXCHANGE_NAME);
                            rabbitProvider.send(jmeterMessage);
                        }

                        // 脚本执行完成发事件通知
                        JmeterMessage jmeterMessage = new JmeterMessage();
                        jmeterMessage.setTitle("-----执行完成------");
                        jmeterMessage.setContents("------执行完成----------");
                        jmeterMessage.setRoutingKey(Constants.RK_FINISH);
                        jmeterMessage.setExchange(Constants.FINISH_EXCHANGE_NAME);
                        rabbitProvider.send(jmeterMessage);
                    } catch (Exception e) {

                    }
                }
        ).start();
        redisLock.unlock(String.valueOf(scriptId), String.valueOf(value));

    }
}
