package com.cqx.testspringboot.activiti.holder;

import com.cqx.testspringboot.activiti.dao.DaoInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * DynamicDataSourceContextHolder
 *
 * @author chenqixu
 */
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal();
    public static Map<String, DaoInfo> mapDataSource = new HashMap();

    public DynamicDataSourceContextHolder() {
    }

    public static void setDataSourceType(String dataSourceId) {
        contextHolder.set(dataSourceId);
    }

    public static String getDataSourceType() {
        return (String) contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    public static boolean containsDataSource(String dataSourceId) {
        DaoInfo daoInfo = (DaoInfo) mapDataSource.get(dataSourceId);
        return daoInfo != null;
    }
}
