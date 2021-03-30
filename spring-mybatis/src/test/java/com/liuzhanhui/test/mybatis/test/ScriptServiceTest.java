package com.liuzhanhui.test.mybatis.test;

import com.liuzhanhui.test.mybatis.dto.Script;
import com.liuzhanhui.test.mybatis.service.IScriptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScriptServiceTest {

    @Autowired
    private IScriptService scriptService;

    @Test
    public void queryScript () {
        Script script = scriptService.queryScriptByIdService(1000000l);
        System.out.println(script);
    }
}
