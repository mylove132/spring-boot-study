package com.liuzhanhui.test.mybatis.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Script implements Serializable {

    private Long scriptId;

    private String scriptName;

    // 线程数
    private Integer threadNum;

    // 压测时长 (分钟)
    private Integer stressTime;

}
