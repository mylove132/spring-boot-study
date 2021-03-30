package com.liuzhanhui.test.mybatis.service.impl;

import com.liuzhanhui.test.mybatis.dto.Script;
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

    @Override
    public void runScript(Long scriptId) {
        Script script = scriptService.queryScriptByIdService(scriptId);
        long value = System.currentTimeMillis() + script.getStressTime() * 60;
        if (!redisLock.lock(String.valueOf(scriptId), String.valueOf(value))){
            throw new RuntimeException("脚本正在执行，请稍后重试");
        }
        try {
            // 执行脚本
            Thread.sleep(5000);
        }catch (Exception e) {

        }
        redisLock.unlock(String.valueOf(scriptId), String.valueOf(value));

    }
}
