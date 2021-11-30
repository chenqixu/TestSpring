package com.cqx.testweb.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 *
 * @author chenqixu
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    public static final String mysqlDataSource = "mysqlDataSource";
    public static final String derbynetDataSource = "derbynetDataSource";
    // 本地线程，获取当前正在执行的currentThread
    public static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setCustomerType(String customerType) {
        contextHolder.set(customerType);
    }

    public static String getCustomerType() {
        return contextHolder.get();
    }

    public static void clearCustomerType() {
        contextHolder.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getCustomerType();
    }
}
