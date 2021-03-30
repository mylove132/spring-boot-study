package com.liuzhanhui.test.mybatis.aop;

import com.liuzhanhui.test.mybatis.db.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Order(value = 1)
@Component
public class DataSourceContextAop {

    @Pointcut("!@annotation(com.liuzhanhui.test.mybatis.annotation.Master) " +
            "&& (execution(* com.liuzhanhui.test.mybatis.service..*.select*(..)) " +
            "|| execution(* com.liuzhanhui.test.mybatis.service..*.get*(..)))" +
            "|| execution(* com.liuzhanhui.test.mybatis.service..*.query*(..)))"
      )
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.liuzhanhui.test.mybatis.annotation.Master) " +
            "|| execution(* com.liuzhanhui.test.mybatis.service..*.insert*(..)) " +
            "|| execution(* com.liuzhanhui.test.mybatis.service..*.add*(..)) " +
            "|| execution(* com.liuzhanhui.test.mybatis.service..*.update*(..)) " +
            "|| execution(* com.liuzhanhui.test.mybatis.service..*.edit*(..)) " +
            "|| execution(* com.liuzhanhui.test.mybatis.service..*.delete*(..)) " +
            "|| execution(* com.liuzhanhui.test.mybatis.service..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DataSourceContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DataSourceContextHolder.master();
    }
}
