package com.liuzhanhui.test.mybatis.service.impl;

import com.liuzhanhui.test.mybatis.dto.SysLog;
import com.liuzhanhui.test.mybatis.mapper.SysLogMapper;
import com.liuzhanhui.test.mybatis.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImp implements ISysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void saveSysLogService(SysLog sysLog) {
        sysLogMapper.saveSysLog(sysLog);
    }
}
