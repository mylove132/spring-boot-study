package com.liuzhanhui.test.mybatis.service;

import com.liuzhanhui.test.mybatis.dto.SysLog;

public interface ISysLogService {

    /**
     * 保存操作信息
     * @param sysLog
     */
    void saveSysLogService (SysLog sysLog);
}
