package com.liuzhanhui.test.mybatis.controller;

import com.liuzhanhui.test.mybatis.service.IRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 执行脚本
 */
@RestController
public class RunController {

    @Autowired
    private IRunService runService;

    @GetMapping("/run/{scriptId}")
    public void runScriptController (@PathVariable Long scriptId) {
        runService.runScript(scriptId);
    }
}
