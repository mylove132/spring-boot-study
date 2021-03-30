package com.liuzhanhui.test.mybatis.mapper;

import com.liuzhanhui.test.mybatis.dto.Script;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ScriptMapper {

    Script queryScriptById (Long scriptId);

    List<Script> queryScripts ();

    void delScriptById (Long scriptId);

    void addScript (Script script);
}
