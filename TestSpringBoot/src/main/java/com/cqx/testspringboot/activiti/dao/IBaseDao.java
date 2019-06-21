package com.cqx.testspringboot.activiti.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * IBaseDao
 *
 * @author chenqixu
 */
public interface IBaseDao {
    String getKey(String var1) throws SQLException;

    String getKey(String var1, String var2) throws SQLException;

    int getRecordCount(String var1, Object var2);

    int getRecordCount(String var1, Map<String, ?> var2);

    void insert(String var1, Object var2) throws SQLException;

    void insert(String var1, Map<String, ?> var2) throws SQLException;

    void insert(String var1, ISetterCallback var2) throws SQLException;

    <T extends Number> T insertByPK(String var1, Object var2) throws SQLException;

    <T extends Number> T insertByPK(String var1, ISetterCallback var2) throws SQLException;

    <T> int insertAll(String var1, List<T> var2) throws SQLException;

    <T> int insertAll(String var1, List<T> var2, IBatchSetterCallback var3) throws SQLException;

    int update(String var1, Object var2) throws SQLException;

    int update(String var1, Map<String, ?> var2) throws SQLException;

    int batchUpdate(String var1, Object[] var2) throws SQLException;

    <T> int batchUpdate(String var1, List<T> var2) throws SQLException;

    int delete(String var1, Object var2) throws SQLException;

    int delete(String var1, Map<String, ?> var2) throws SQLException;

    int deleteTab(String var1, String var2) throws SQLException;

    <T> List<T> query(String var1, Class<T> var2);

    <T> List<T> query(String var1, Map<String, ?> var2, Class<T> var3);

    <T> List<T> query(String var1, Object var2, Class<T> var3);

    <T> List<T> query(String var1, Map var2, IGetterCallback var3);

    <T> T queryForObject(String var1, Class<T> var2);

    <T> T queryForObject(String var1, Map<String, ?> var2, Class<T> var3);

    <T> T queryForObject(String var1, Object var2, Class<T> var3);

    Map<String, Object> queryForMap(String var1, Map<String, ?> var2);

    Map<String, Object> queryForMap(String var1, Object var2);

    String[][] queryForArray(String var1);

    String[][] queryForArray(String var1, Map<String, ?> var2);

    String[][] queryForArray(String var1, Object var2);

    <T> List<T> pageQuery(String var1, Map<String, ?> var2, Class<T> var3, int var4, int var5);

    <T> List<T> pageQuery(String var1, Object var2, Class<T> var3, int var4, int var5);

    String[][] pageQueryForArray(String var1, Map<String, ?> var2, int var3, int var4);

    String[][] pageQueryForArray(String var1, Object var2, int var3, int var4);
}
