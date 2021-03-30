package com.liuzhanhui.test.mybatis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysLog implements Serializable {

    private static final long serialVersionUID = -6309732882044872298L;

    private Integer sysId;

    private String username;

    private String operation;

    private Integer time;

    private String method;

    private String params;

    private String ip;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;
}
