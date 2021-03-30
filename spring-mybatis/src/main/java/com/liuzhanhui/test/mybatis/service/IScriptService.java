package com.liuzhanhui.test.mybatis.service;

import com.liuzhanhui.test.mybatis.dto.Script;

import java.util.List;

public interface IScriptService {

    Script queryScriptByIdService (Long scriptId);

    List<Script> queryScriptsService ();

    void delScriptByIdService (Long scriptId);

    void addScriptService (Script script);
}
