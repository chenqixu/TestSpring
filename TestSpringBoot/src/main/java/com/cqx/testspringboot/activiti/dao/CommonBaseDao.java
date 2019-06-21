package com.cqx.testspringboot.activiti.dao;

import com.cqx.testspringboot.activiti.dao.DaoInfo.DB_TYPE;
import com.cqx.testspringboot.activiti.util.MutilDataSourceUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CommonBaseDao
 *
 * @author chenqixu
 */
@Repository("com.cqx.testspringboot.activiti.dao.CommonBaseDao")
public class CommonBaseDao extends AbstractBaseDao {
    private static final Logger logger = LoggerFactory.getLogger(CommonBaseDao.class);
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Resource(name = "namedParameterJdbcTemplate")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//    @Resource(name = "LobHandler")
//    private LobHandler lobHandler;

    public CommonBaseDao() {
    }

    public int getRecordCount(String sql, Object paramBean) {
        BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
        StringBuffer countSql = new StringBuffer();
        countSql.append("select count(1) from ( ").append(sql).append(" ) tt");
        return (Integer) this.namedParameterJdbcTemplate.queryForObject(countSql.toString(), bp, Integer.class);
    }

    public int getRecordCount(String sql, Map<String, ?> paramMap) {
        StringBuffer countSql = new StringBuffer(200);
        countSql.append("select count(1) from ( ").append(sql).append(" ) tt");
        return (Integer) this.namedParameterJdbcTemplate.queryForObject(countSql.toString(), paramMap, Integer.class);
    }

    public void insert(String sql, Object paramBean) throws SQLException {
        BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
        this.namedParameterJdbcTemplate.update(sql, bp);
    }

    public void insert(String sql, Map<String, ?> paramMap) throws SQLException {
        this.namedParameterJdbcTemplate.update(sql, paramMap);
    }

    public int update(String sql, Object paramBean) throws SQLException {
        BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
        return this.namedParameterJdbcTemplate.update(sql, bp);
    }

    public int update(String sql, Map<String, ?> paramMap) throws SQLException {
        return this.namedParameterJdbcTemplate.update(sql, paramMap);
    }

    public void insert(final String sql, final ISetterCallback setterCallback) throws SQLException {
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, 1);
                setterCallback.execute(ps);
                return ps;
            }
        });
    }

    public <T extends Number> T insertByPK(String sql, Object paramBean) throws SQLException {
        BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.namedParameterJdbcTemplate.update(sql, bp, keyHolder);
        return (T) keyHolder.getKey();
    }

    public <T extends Number> T insertByPK(final String sql, final ISetterCallback setterCallback) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, 1);
                setterCallback.execute(ps);
                return ps;
            }
        }, keyHolder);
        return (T) keyHolder.getKey();
    }

    public <T> int insertAll(String sql, List<T> lstParam) throws SQLException {
        Object[] objParam;
        if (CollectionUtils.isEmpty(lstParam)) {
            objParam = new Object[0];
        } else {
            objParam = new Object[lstParam.size()];
            objParam = lstParam.toArray();
        }

        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(objParam);
        int[] arrResult = this.namedParameterJdbcTemplate.batchUpdate(sql, params);
        return ArrayUtils.getLength(arrResult);
    }

    public <T> int insertAll(String sql, final List<T> lstParam, final IBatchSetterCallback batchSetterCallback) throws SQLException {
        int[] arrResult = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                T t = lstParam.get(i);
                batchSetterCallback.execute(ps, t);
            }

            public int getBatchSize() {
                return lstParam.size();
            }
        });
        return ArrayUtils.getLength(arrResult);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public int batchUpdate(String sql, Object[] arrParam) throws SQLException {
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(arrParam);
        int[] arrResult = this.namedParameterJdbcTemplate.batchUpdate(sql, params);
        return ArrayUtils.getLength(arrResult);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public <T> int batchUpdate(String sql, List<T> lstParam) throws SQLException {
        return this.insertAll(sql, lstParam);
    }

    public int delete(String sql, Map<String, ?> paramMap) throws SQLException {
        return this.namedParameterJdbcTemplate.update(sql, paramMap);
    }

    public int delete(String sql, Object paramBean) throws SQLException {
        BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
        return this.namedParameterJdbcTemplate.update(sql, bp);
    }

    public int deleteTab(String schemaName, String tabName) throws SQLException {
        String sql = this.buildDeleteTabSQL(schemaName, tabName);
        return this.jdbcTemplate.update(sql);
    }

    public <T> List<T> query(String sql, Class<T> tclass) {
        return this.query(sql, (Map) null, (Class) tclass);
    }

    public <T> List<T> query(String sql, Map<String, ?> paramMap, Class<T> tClass) {
        return !ClassUtils.isPrimitiveWrapper(tClass) && !tClass.equals(String.class) ? this.namedParameterJdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(tClass)) : this.namedParameterJdbcTemplate.queryForList(sql, paramMap, tClass);
    }

    public <T> List<T> query(String sql, Object paramBean, Class<T> tClass) {
        BeanPropertySqlParameterSource bp = null;
        if (paramBean != null) {
            bp = new BeanPropertySqlParameterSource(paramBean);
        }

        return !ClassUtils.isPrimitiveWrapper(tClass) && !tClass.equals(String.class) ? this.namedParameterJdbcTemplate.query(sql, bp, BeanPropertyRowMapper.newInstance(tClass)) : this.namedParameterJdbcTemplate.queryForList(sql, bp, tClass);
    }

    public <T> List<T> query(String sql, Map paramMap, final IGetterCallback getterCallback) {
        List<T> lstData = this.namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<T>() {
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                return getterCallback.execute(rs);
            }
        });
        return lstData != null && lstData.size() > 0 ? lstData : null;
    }

    public <T> T queryForObject(String sql, Class<T> tClass) {
        return (T) this.queryForObject(sql, (Map) null, tClass);
    }

    public <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> tClass) {
        try {
            return !ClassUtils.isPrimitiveWrapper(tClass) && !tClass.equals(String.class) ? this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(tClass)) : this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, tClass);
        } catch (EmptyResultDataAccessException var5) {
            logger.error("未找到任何记录:" + sql);
            return null;
        } catch (IncorrectResultSizeDataAccessException var6) {
            logger.error("查询结果超过一条记录:" + sql);
            return null;
        } catch (Exception var7) {
            logger.error(var7.getMessage(), var7);
            return null;
        }
    }

    public <T> T queryForObject(String sql, Object paramBean, Class<T> tClass) {
        try {
            BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
            return !ClassUtils.isPrimitiveWrapper(tClass) && !tClass.equals(String.class) ? this.namedParameterJdbcTemplate.queryForObject(sql, bp, BeanPropertyRowMapper.newInstance(tClass)) : this.namedParameterJdbcTemplate.queryForObject(sql, bp, tClass);
        } catch (EmptyResultDataAccessException var5) {
            logger.error("未找到任何记录:" + sql);
            return null;
        } catch (IncorrectResultSizeDataAccessException var6) {
            logger.error("查询结果超过一条记录:" + sql);
            return null;
        } catch (Exception var7) {
            logger.error(var7.getMessage(), var7);
            return null;
        }
    }

    public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) {
        try {
            return this.namedParameterJdbcTemplate.queryForMap(sql, paramMap);
        } catch (EmptyResultDataAccessException var4) {
            logger.error("未找到任何记录:" + sql);
            return null;
        } catch (IncorrectResultSizeDataAccessException var5) {
            logger.error("查询结果超过一条记录:" + sql);
            return null;
        } catch (Exception var6) {
            logger.error(var6.getMessage(), var6);
            return null;
        }
    }

    public Map<String, Object> queryForMap(String sql, Object paramBean) {
        try {
            BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
            return this.namedParameterJdbcTemplate.queryForMap(sql, bp);
        } catch (EmptyResultDataAccessException var4) {
            logger.error("未找到任何记录:" + sql);
            return null;
        } catch (IncorrectResultSizeDataAccessException var5) {
            logger.error("查询结果超过一条记录:" + sql);
            return null;
        } catch (Exception var6) {
            logger.error(var6.getMessage(), var6);
            return null;
        }
    }

    public String[][] queryForArray(String sql) {
        return this.queryForArray(sql, false);
    }

    public String[][] queryForArray(String sql, Map<String, ?> paramMap) {
        return this.queryForArray(sql, paramMap, false);
    }

    public String[][] queryForArray(String sql, Object paramBean) {
        return this.queryForArray(sql, paramBean, false);
    }

    public <T> List<T> pageQuery(String sql, Map<String, ?> paramMap, Class<T> tClass, int startPage, int limit) {
        return this.query(sql, paramMap, tClass, startPage, limit);
    }

    public <T> List<T> pageQuery(String sql, Object paramBean, Class<T> tClass, int startPage, int limit) {
        return this.query(sql, paramBean, tClass, startPage, limit);
    }

    public String[][] pageQueryForArray(String sql, Map<String, ?> paramMap, int startPage, int limit) {
        String pageSQL = this.buildPageQuerySQL(sql, startPage, limit);
        return this.queryForArray(pageSQL, paramMap, this.isCutFirstColByDbType());
    }

    public String[][] pageQueryForArray(String sql, Object paramBean, int startPage, int limit) {
        String pageSQL = this.buildPageQuerySQL(sql, startPage, limit);
        return this.queryForArray(pageSQL, paramBean, this.isCutFirstColByDbType());
    }

    private <T> List<T> query(String sql, Map<String, ?> paramMap, Class<T> tClass, int startPage, int limit) {
        return this.query(this.buildPageQuerySQL(sql, startPage, limit), paramMap, tClass);
    }

    private <T> List<T> query(String sql, Object paramBean, Class<T> tClass, int startPage, int limit) {
        return this.query(this.buildPageQuerySQL(sql, startPage, limit), paramBean, tClass);
    }

    private String[][] queryForArray(String sql, boolean isCutFirstCol) {
        SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql);
        int iRowCount = this.getRecordCount(sql, (Map) null);
        logger.info("查询的记录数: {}", iRowCount);
        return this.queryForArrayBySqlRowSet(rs, iRowCount, isCutFirstCol);
    }

    private String[][] queryForArray(String sql, Map<String, ?> paramMap, boolean isCutFirstCol) {
        SqlRowSet rs = this.namedParameterJdbcTemplate.queryForRowSet(sql, paramMap);
        rs.last();
        int iRowCount = rs.getRow();
        logger.info("查询的记录数: {}", iRowCount);
        return this.queryForArrayBySqlRowSet(rs, iRowCount, isCutFirstCol);
    }

    private String[][] queryForArray(String sql, Object paramBean, boolean isCutFirstCol) {
        BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
        SqlRowSet rs = this.namedParameterJdbcTemplate.queryForRowSet(sql, bp);
        rs.last();
        int iRowCount = rs.getRow();
        logger.info("查询的记录数: {}", iRowCount);
        return this.queryForArrayBySqlRowSet(rs, iRowCount, isCutFirstCol);
    }

    private String[][] queryForArrayBySqlRowSet(SqlRowSet rs, int iRowCount, boolean isCutFirstCol) {
        SqlRowSetMetaData rsmd = rs.getMetaData();
        int iColCount = isCutFirstCol ? rsmd.getColumnCount() - 1 : rsmd.getColumnCount();
        logger.info("查询字段数: {}", iColCount);
        String[][] arr2Data = new String[iRowCount][iColCount];
        if (rs.first()) {
            int i = 0;

            for (int jindex = isCutFirstCol ? 2 : 1; !rs.isAfterLast(); ++i) {
                for (int j = 0; j < iColCount; ++j) {
                    String strData = ObjectUtils.defaultIfNull(rs.getObject(j + jindex), "").toString();
                    arr2Data[i][j] = strData;
                }

                rs.next();
            }
        }

        return arr2Data;
    }

    private String[][] queryForArrayBySqlRowSet(SqlRowSet rs, int iRowCount) {
        return this.queryForArrayBySqlRowSet(rs, iRowCount, false);
    }

    private boolean isCutFirstColByDbType() {
        DaoInfo daoInfo = MutilDataSourceUtils.getCurrentDaoInfo();
        DB_TYPE dbType = daoInfo.getDbType();
        if (DB_TYPE.ORACLE.equals(dbType)) {
            return true;
        } else if (DB_TYPE.SQLSERVER.equals(dbType)) {
            return true;
        } else {
            return DB_TYPE.MYSQL.equals(dbType) ? false : false;
        }
    }

    protected String getIncrementerKey(String tabName) {
        DaoInfo daoInfo = MutilDataSourceUtils.getCurrentDaoInfo();
        DB_TYPE dbType = daoInfo.getDbType();
        String sql;
        if (DB_TYPE.MYSQL.equals(dbType)) {
            sql = "select auto_increment from information_schema.`TABLES` where TABLE_NAME=:table_name";
        } else {
            if (!DB_TYPE.SQLSERVER.equals(dbType)) {
                return "";
            }

            sql = "select ident_current(:table_name) + 1";
        }

        Map<String, String> map = new HashMap();
        map.put("table_name", tabName);
        Long ivalue = (Long) this.queryForObject(sql, (Map) map, Long.class);
        return ivalue.toString();
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return this.namedParameterJdbcTemplate;
    }

//    public LobHandler getLobHandler() {
//        return this.lobHandler;
//    }
}
