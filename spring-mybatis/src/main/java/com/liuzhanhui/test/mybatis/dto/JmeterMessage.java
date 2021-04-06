package com.liuzhanhui.test.mybatis.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JmeterMessage implements Serializable {

    private String routingKey;
    private String exchange;
    private String contents;
    private String title;
}
