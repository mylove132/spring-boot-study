package com.liuzhanhui.test.mybatis.mapper;

import com.liuzhanhui.test.mybatis.dto.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface SysLogMapper {

    void saveSysLog (SysLog sysLog);
}
