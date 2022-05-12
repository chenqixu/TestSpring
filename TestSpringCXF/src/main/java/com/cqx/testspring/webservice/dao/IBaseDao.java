package com.cqx.testspring.webservice.dao;

import java.util.List;
import java.util.Map;

/**
 * IBaseDao
 */
public interface IBaseDao {
    String getKey(String var1) throws Exception;

    String getKey(String var1, String var2) throws Exception;

    void dropTable(String var1) throws Exception;

    int batchUpdate(String var1, List<?> var2) throws Exception;

    int delete(String var1, Object var2) throws Exception;

    DBResult pageQuery(String var1, Map<String, ?> var2, Class<?> var3, int var4, int var5);

    DBResult pageQuery(String var1, Map<String, ?> var2, Class<?> var3, int var4, int var5, boolean var6);

    DBResult pageQuery(String var1, Object var2, Class<?> var3, int var4, int var5);

    DBResult pageQuery(String var1, Object var2, Class<?> var3, int var4, int var5, boolean var6);

    DBResult pageQueryForArray(String var1, Map<String, ?> var2, int var3, int var4);

    DBResult pageQueryForArray(String var1, Map<String, ?> var2, int var3, int var4, boolean var5);

    DBResult pageQueryForArray(String var1, int var2, int var3);

    DBResult pageQueryForArray(String var1, Object var2, int var3, int var4);

    DBResult pageQueryForArray(String var1, Object var2, int var3, int var4, boolean var5);

    List<?> query(String var1, Class<?> var2);

    List<?> query(String var1, Map<String, ?> var2, Class<?> var3);

    List<?> query(String var1, Object var2, Class<?> var3);

    List<?> query(String var1, Map<String, ?> var2, Class<?> var3, int var4, int var5);

    List<?> query(String var1, Object var2, Class<?> var3, int var4, int var5);

    Object queryForObject(String var1, Class<?> var2);

    Object queryForObject(String var1, Map<String, ?> var2, Class<?> var3);

    Object queryForObject(String var1, Object var2, Class<?> var3);

    Map<String, Object> queryForMap(String var1, Map<String, ?> var2);

    Map<String, Object> queryForMap(String var1, Object var2);

    String[][] queryForArray(String var1);

    String[][] queryForArray(String var1, Map<String, ?> var2);

    String[][] queryForArray(String var1, Object var2);
}
