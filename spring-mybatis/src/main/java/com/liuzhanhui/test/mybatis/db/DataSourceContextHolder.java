package com.liuzhanhui.test.mybatis.db;

public class DataSourceContextHolder {
    private static final ThreadLocal<DynamicDataSourceEnum> contextHolder = new ThreadLocal<>();

    public static void set(DynamicDataSourceEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DynamicDataSourceEnum get() {
        return contextHolder.get();
    }

    public static void master() {
        set(DynamicDataSourceEnum.MASTER);
    }

    public static void slave() {
       set(DynamicDataSourceEnum.SLAVE);
    }
}
