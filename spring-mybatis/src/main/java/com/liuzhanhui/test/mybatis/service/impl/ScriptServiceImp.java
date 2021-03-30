package com.liuzhanhui.test.mybatis.service.impl;

import com.liuzhanhui.test.mybatis.dto.Script;
import com.liuzhanhui.test.mybatis.mapper.ScriptMapper;
import com.liuzhanhui.test.mybatis.service.IScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptServiceImp implements IScriptService {

    @Autowired
    private ScriptMapper scriptMapper;

    @Override
    public Script queryScriptByIdService(Long scriptId) {
        return scriptMapper.queryScriptById(scriptId);
    }

    @Override
    public List<Script> queryScriptsService() {
        return scriptMapper.queryScripts();
    }

    @Override
    public void delScriptByIdService(Long scriptId) {
        scriptMapper.delScriptById(scriptId);
    }

    @Override
    public void addScriptService(Script script) {
        scriptMapper.addScript(script);
    }
}
