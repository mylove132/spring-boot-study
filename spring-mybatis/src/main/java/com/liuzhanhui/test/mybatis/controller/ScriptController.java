package com.liuzhanhui.test.mybatis.controller;

import com.liuzhanhui.test.mybatis.dto.Script;
import com.liuzhanhui.test.mybatis.service.IScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ScriptController {

    @Autowired
    private IScriptService scriptService;

    @GetMapping("/script/{scriptId}")
    public Map<String, Object> queryScriptByIdController (@PathVariable Long scriptId) {
        Script script = scriptService.queryScriptByIdService(scriptId);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 10000);
        result.put("data", script);
        result.put("msg", "OK");
        return result;
    }
}
