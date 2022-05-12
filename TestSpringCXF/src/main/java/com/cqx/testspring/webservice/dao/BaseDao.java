package com.cqx.testspring.webservice.dao;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.ContextLoader;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository("com.cqx.testspring.webservice.dao.BaseDao")
public class BaseDao implements IBaseDao {
    private static final Logger logger = Logger.getLogger(BaseDao.class.getName());
    @Resource(
            name = "jdbcTemplate"
    )
    public JdbcTemplate jdbcTemplate;
    @Resource(
            name = "namedParameterJdbcTemplate"
    )
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BaseDao() {
    }

    public void setDefaultJDBC(String templateName) {
        if (templateName != null) {
            this.jdbcTemplate = (JdbcTemplate) ContextLoader.getCurrentWebApplicationContext().getBean(templateName + "JdbcTemplate");
            this.namedParameterJdbcTemplate = (NamedParameterJdbcTemplate) ContextLoader.getCurrentWebApplicationContext().getBean(templateName + "NamedParameterJdbcTemplate");
        } else {
            this.jdbcTemplate = (JdbcTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("jdbcTemplate");
            this.namedParameterJdbcTemplate = (NamedParameterJdbcTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("namedParameterJdbcTemplate");
        }

    }

    public void reSetDefaultJDBC() {
        this.setDefaultJDBC((String) null);
    }

    public String getKey(String seqName) throws Exception {
        return this.getKey(seqName, "");
    }

    public String getKey(String seqName, String prefixName) throws Exception {
        try {
            long key = (Long) this.jdbcTemplate.queryForObject("select " + seqName + ".nextval as nextval from dual", Long.class);
            return prefixName + key;
        } catch (Exception var5) {
            logger.error(var5);
            throw new SQLException("后台服务异常，请联系管理员");
        }
    }

    public void dropTable(String tableName) throws Exception {
        try {
            String sql = "drop table " + tableName + " purge";
            this.jdbcTemplate.execute(sql);
        } catch (Exception var3) {
            logger.error(var3);
            throw new SQLException("后台服务异常，请联系管理员");
        }
    }

    public int batchUpdate(String sql, List<?> paramList) {
        Object[] paramObj = (Object[]) null;
        if (CollectionUtils.isEmpty(paramList)) {
            paramObj = new Object[0];
        } else {
            paramObj = new Object[paramList.size()];
            paramObj = paramList.toArray();
        }

        try {
            SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(paramObj);
            int[] arrResult = this.namedParameterJdbcTemplate.batchUpdate(sql, params);
            return ArrayUtils.getLength(arrResult);
        } catch (Exception var6) {
            logger.error(var6);
            var6.printStackTrace();
            return -1;
        }
    }

    public DBResult pageQuery(String sql, Map<String, ?> paramMap, Class<?> T, int startPage, int limit) {
        return this.pageQuery(sql, paramMap, T, startPage, limit, false);
    }

    public DBResult pageQuery(String sql, Map<String, ?> paramMap, Class<?> T, int startPage, int limit, boolean isReturnCount) {
        DBResult rs = new DBResult();

        try {
            if (isReturnCount) {
                StringBuilder countSql = new StringBuilder();
                countSql.append("select count(1) from ( ").append(sql).append(" )");
                int cnt = this.namedParameterJdbcTemplate.queryForObject(countSql.toString(), paramMap, Integer.class);
                rs.iTotalNum = cnt;
            }

            rs.result = this.query(sql, paramMap, T, startPage, limit);
        } catch (Exception var11) {
            rs.sErrorDescriptor = var11.getMessage();
            rs.iErrorCode = -1;
            rs.sErrorStrack = this.getExceptionInfo(var11);
            logger.error("分页查询错误", var11);

            try {
                throw new SQLException("后台服务异常，请联系管理员");
            } catch (SQLException var10) {
                var10.printStackTrace();
            }
        }

        return rs;
    }

    public DBResult pageQuery(String sql, Object paramBean, Class<?> T, int startPage, int limit) {
        return this.pageQuery(sql, paramBean, T, startPage, limit, false);
    }

    public DBResult pageQuery(String sql, Object paramBean, Class<?> T, int startPage, int limit, boolean isReturnCount) {
        DBResult rs = new DBResult();

        try {
            BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
            if (isReturnCount) {
                StringBuilder countSql = new StringBuilder();
                countSql.append("select count(1) from ( ").append(sql).append(" )");
                int cnt = this.namedParameterJdbcTemplate.queryForObject(countSql.toString(), bp, Integer.class);
                rs.iTotalNum = cnt;
            }

            rs.result = this.query(sql, paramBean, T, startPage, limit);
        } catch (Exception var12) {
            rs.sErrorDescriptor = var12.getMessage();
            rs.iErrorCode = -1;
            rs.sErrorStrack = this.getExceptionInfo(var12);
            logger.error("分页查询错误", var12);

            try {
                throw new SQLException("后台服务异常，请联系管理员");
            } catch (SQLException var11) {
                var11.printStackTrace();
            }
        }

        return rs;
    }

    public DBResult getFailDBResult(String sErrorDescriptor) {
        DBResult rs = new DBResult();
        rs.sErrorDescriptor = sErrorDescriptor;
        rs.iErrorCode = -1;
        logger.error(sErrorDescriptor);
        return rs;
    }

    public DBResult getSuccessDBResult(Object obj) {
        DBResult rs = new DBResult();
        rs.result = obj;
        return rs;
    }

    private String getExceptionInfo(Throwable e) {
        StackTraceElement[] astacktraceelement = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString());
        sb.append("\n");

        for (int i = 0; i < astacktraceelement.length; ++i) {
            sb.append("\tat ").append(astacktraceelement[i]).toString();
            sb.append("\n");
        }

        Throwable throwable = e.getCause();
        if (throwable != null) {
            this.getCauseInfo(sb, throwable);
        }

        return sb.toString();
    }

    private StringBuilder getCauseInfo(StringBuilder sb, Throwable throwable) {
        sb.append("Caused by: " + throwable.toString());
        sb.append("\n");
        StackTraceElement[] astacktraceelement = throwable.getStackTrace();

        for (int i = 0; i < astacktraceelement.length; ++i) {
            sb.append("\tat ").append(astacktraceelement[i]).toString();
            sb.append("\n");
        }

        return sb;
    }

    public List<?> query(String sql, Class<?> T) {
        return this.query(sql, (Map) null, T);
    }

    public List<?> query(String sql, Map<String, ?> paramMap, Class<?> T) {
        try {
            return !ClassUtils.isPrimitiveWrapper(T) && !T.equals(String.class) ? this.namedParameterJdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(T)) : this.namedParameterJdbcTemplate.queryForList(sql, paramMap, T);
        } catch (Exception var7) {
            logger.error(var7);

            try {
                throw new SQLException("后台服务异常，请联系管理员");
            } catch (SQLException var6) {
                var6.printStackTrace();
                return null;
            }
        }
    }

    public List<?> query(String sql, Object paramBean, Class<?> T) {
        try {
            BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
            return !ClassUtils.isPrimitiveWrapper(T) && !T.equals(String.class) ? this.namedParameterJdbcTemplate.query(sql, bp, BeanPropertyRowMapper.newInstance(T)) : this.namedParameterJdbcTemplate.queryForList(sql, bp, T);
        } catch (Exception var7) {
            logger.error(var7);

            try {
                throw new SQLException("后台服务异常，请联系管理员");
            } catch (SQLException var6) {
                var6.printStackTrace();
                return null;
            }
        }
    }

    public List<?> query(String sql, Map<String, ?> paramMap, Class<?> T, int startPage, int limit) {
        int startRow = (startPage - 1) * limit + 1;
        int endRow = startRow + limit - 1;
        StringBuffer execSql = new StringBuffer(500);
        execSql.append("select * from (select rownum as r_n, b.* from (");
        execSql.append(sql);
        execSql.append(") b where rownum <= " + endRow + ") c where c.r_n >= " + startRow);
        execSql.trimToSize();
        logger.info("分页查询的sql:" + execSql);
        return this.query(execSql.toString(), paramMap, T);
    }

    public List<?> query(String sql, Object paramBean, Class<?> T, int startPage, int limit) {
        int startRow = (startPage - 1) * limit + 1;
        int endRow = startRow + limit - 1;
        StringBuffer execSql = new StringBuffer(500);
        execSql.append("select * from (select rownum as r_n, b.* from (");
        execSql.append(sql);
        execSql.append(") b where rownum <= " + endRow + ") c where c.r_n >= " + startRow);
        execSql.trimToSize();
        logger.info("分页查询的sql:" + execSql);
        return this.query(execSql.toString(), paramBean, T);
    }

    public int delete(String sql, Object paramBean) throws Exception {
        try {
            BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
            return this.namedParameterJdbcTemplate.update(sql, bp);
        } catch (Exception var4) {
            logger.error(var4);
            throw new SQLException("后台服务异常，请联系管理员");
        }
    }

    public Object queryForObject(String sql, Map<String, ?> paramMap, Class<?> T) {
        try {
            return !ClassUtils.isPrimitiveWrapper(T) && !T.equals(String.class) ? this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(T)) : this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, T);
        } catch (EmptyResultDataAccessException var7) {
            logger.debug("未找到任何记录:" + sql);
            return null;
        } catch (IncorrectResultSizeDataAccessException var8) {
            logger.debug("查询结果超过一条记录:" + sql);
            return null;
        } catch (Exception var9) {
            logger.error(var9);

            try {
                throw new SQLException("后台服务异常，请联系管理员");
            } catch (SQLException var6) {
                var6.printStackTrace();
                return null;
            }
        }
    }

    public Object queryForObject(String sql, Object paramBean, Class<?> T) {
        try {
            BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
            return !ClassUtils.isPrimitiveWrapper(T) && !T.equals(String.class) ? this.namedParameterJdbcTemplate.queryForObject(sql, bp, BeanPropertyRowMapper.newInstance(T)) : this.namedParameterJdbcTemplate.queryForObject(sql, bp, T);
        } catch (EmptyResultDataAccessException var7) {
            logger.debug("未找到任何记录:" + sql);
            return null;
        } catch (IncorrectResultSizeDataAccessException var8) {
            logger.debug("查询结果超过一条记录:" + sql);
            return null;
        } catch (Exception var9) {
            logger.error(var9);

            try {
                throw new SQLException("后台服务异常，请联系管理员");
            } catch (SQLException var6) {
                var6.printStackTrace();
                return null;
            }
        }
    }

    public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) {
        try {
            return this.namedParameterJdbcTemplate.queryForMap(sql, paramMap);
        } catch (EmptyResultDataAccessException var6) {
            logger.debug("未找到任何记录:" + sql);
            return null;
        } catch (IncorrectResultSizeDataAccessException var7) {
            logger.debug("查询结果超过一条记录:" + sql);
            return null;
        } catch (Exception var8) {
            logger.error(var8);

            try {
                throw new SQLException("后台服务异常，请联系管理员");
            } catch (SQLException var5) {
                var5.printStackTrace();
                return null;
            }
        }
    }

    public Map<String, Object> queryForMap(String sql, Object paramBean) {
        try {
            BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
            return this.namedParameterJdbcTemplate.queryForMap(sql, bp);
        } catch (EmptyResultDataAccessException var6) {
            logger.debug("未找到任何记录:" + sql);
            return null;
        } catch (IncorrectResultSizeDataAccessException var7) {
            logger.debug("查询结果超过一条记录:" + sql);
            return null;
        } catch (Exception var8) {
            logger.error(var8);

            try {
                throw new SQLException("后台服务异常，请联系管理员");
            } catch (SQLException var5) {
                var5.printStackTrace();
                return null;
            }
        }
    }

    public Object queryForObject(String sql, Class<?> T) {
        return this.queryForObject(sql, (Map) null, T);
    }

    public String[][] queryForArray(String sql) {
        try {
            SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql);
            int iRowCount = this.getRecordCount(sql, (Map) null);
            logger.info("查询的记录数:" + iRowCount);
            return this.getQueryArray(rs, iRowCount, false);
        } catch (Exception var5) {
            logger.error(var5);

            try {
                throw new SQLException("后台服务异常，请联系管理员");
            } catch (SQLException var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }

    private String[][] queryForArray(String sql, Map<String, ?> paramMap, boolean isCut) {
        try {
            SqlRowSet rs = this.namedParameterJdbcTemplate.queryForRowSet(sql, paramMap);
            int iRowCount = this.getRecordCount(sql, paramMap);
            logger.info("查询的记录数:" + iRowCount);
            return this.getQueryArray(rs, iRowCount, isCut);
        } catch (Exception var7) {
            logger.error(var7);

            try {
                throw new SQLException("后台服务异常，请联系管理员");
            } catch (SQLException var6) {
                var6.printStackTrace();
                return null;
            }
        }
    }

    public String[][] queryForArray(String sql, Map<String, ?> paramMap) {
        return this.queryForArray(sql, paramMap, false);
    }

    private String[][] queryForArray(String sql, Object paramBean, boolean isCut) {
        try {
            BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
            int iRowCount = this.getRecordCount(sql, paramBean);
            logger.info("查询的记录数:" + iRowCount);
            SqlRowSet rs = this.namedParameterJdbcTemplate.queryForRowSet(sql, bp);
            return this.getQueryArray(rs, iRowCount, isCut);
        } catch (Exception var8) {
            logger.error(var8);

            try {
                throw new SQLException("后台服务异常，请联系管理员");
            } catch (SQLException var7) {
                var7.printStackTrace();
                return null;
            }
        }
    }

    public String[][] queryForArray(String sql, Object paramBean) {
        return this.queryForArray(sql, paramBean, false);
    }

    private String[][] getQueryArray(SqlRowSet rs, int iRowCount, boolean isCut) {
        SqlRowSetMetaData rsmd = rs.getMetaData();
        int iColCount = isCut ? rsmd.getColumnNames().length - 1 : rsmd.getColumnNames().length;
        logger.info("查询字段数:" + iColCount);
        String[][] arr2Data = new String[iRowCount][iColCount];
        if (rs.first()) {
            for (int i = 0; !rs.isAfterLast(); ++i) {
                for (int j = 0; j < iColCount; ++j) {
                    arr2Data[i][j] = ObjectUtils.toString(rs.getObject(j + 1), "");
                }

                rs.next();
            }
        }

        return arr2Data;
    }

    private int getRecordCount(String sql, Object paramBean) throws SQLException {
        try {
            BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
            StringBuilder countSql = new StringBuilder();
            countSql.append("select count(1) from ( ").append(sql).append(" )");
            return this.namedParameterJdbcTemplate.queryForObject(countSql.toString(), bp, Integer.class);
        } catch (Exception var5) {
            logger.error(var5);
            throw new SQLException("后台服务异常，请联系管理员");
        }
    }

    private int getRecordCount(String sql, Map<String, ?> paramMap) throws SQLException {
        try {
            StringBuilder countSql = new StringBuilder();
            countSql.append("select count(1) from ( ").append(sql).append(" )");
            return this.namedParameterJdbcTemplate.queryForObject(countSql.toString(), paramMap, Integer.class);
        } catch (Exception var4) {
            logger.error(var4);
            throw new SQLException("后台服务异常，请联系管理员");
        }
    }

    public DBResult pageQueryForArray(String sql, Map<String, ?> paramMap, int startPage, int limit) {
        return this.pageQueryForArray(sql, paramMap, startPage, limit, false);
    }

    public DBResult pageQueryForArray(String sql, Map<String, ?> paramMap, int startPage, int limit, boolean isReturnCount) {
        DBResult rs = new DBResult();

        try {
            if (isReturnCount) {
                StringBuilder countSql = new StringBuilder();
                countSql.append("select count(1) from ( ").append(sql).append(" )");
                int cnt = this.namedParameterJdbcTemplate.queryForObject(countSql.toString(), paramMap, Integer.class);
                rs.iTotalNum = cnt;
            }

            rs.result = this.queryForPartArray(sql, paramMap, startPage, limit);
        } catch (Exception var10) {
            rs.sErrorDescriptor = var10.getMessage();
            rs.iErrorCode = -1;
            rs.sErrorStrack = this.getExceptionInfo(var10);
            logger.error("分页查询错误", var10);

            try {
                throw new SQLException("后台服务异常，请联系管理员");
            } catch (SQLException var9) {
                var9.printStackTrace();
            }
        }

        return rs;
    }

    public DBResult pageQueryForArray(String sql, int startPage, int limit) {
        return this.pageQueryForArray(sql, (Map) null, startPage, limit, false);
    }

    public DBResult pageQueryForArray(String sql, Object paramBean, int startPage, int limit) {
        return this.pageQueryForArray(sql, paramBean, startPage, limit, false);
    }

    public DBResult pageQueryForArray(String sql, Object paramBean, int startPage, int limit, boolean isReturnCount) {
        DBResult rs = new DBResult();

        try {
            BeanPropertySqlParameterSource bp = new BeanPropertySqlParameterSource(paramBean);
            if (isReturnCount) {
                StringBuilder countSql = new StringBuilder();
                countSql.append("select count(1) from ( ").append(sql).append(" )");
                int cnt = this.namedParameterJdbcTemplate.queryForObject(countSql.toString(), bp, Integer.class);
                rs.iTotalNum = cnt;
            }

            rs.result = this.queryForPartArray(sql, paramBean, startPage, limit);
        } catch (Exception var11) {
            rs.sErrorDescriptor = var11.getMessage();
            rs.iErrorCode = -1;
            rs.sErrorStrack = this.getExceptionInfo(var11);
            logger.error("分页查询错误", var11);

            try {
                throw new SQLException("后台服务异常，请联系管理员");
            } catch (SQLException var10) {
                var10.printStackTrace();
            }
        }

        return rs;
    }

    private String[][] queryForPartArray(String sql, Object paramBean, int startPage, int limit) throws SQLException {
        int startRow = (startPage - 1) * limit + 1;
        int endRow = startRow + limit - 1;
        StringBuffer execSql = new StringBuffer(500);
        execSql.append("select * from (select b.*, rownum as r_n from (");
        execSql.append(sql);
        execSql.append(") b where rownum <= " + endRow + ") c where c.r_n >= " + startRow);
        execSql.trimToSize();
        logger.info("分页查询的sql:" + execSql);
        return this.queryForArray(execSql.toString(), paramBean, true);
    }

    private String[][] queryForPartArray(String sql, Map<String, ?> paramMap, int startPage, int limit) throws SQLException {
        int startRow = (startPage - 1) * limit + 1;
        int endRow = startRow + limit - 1;
        StringBuffer execSql = new StringBuffer(500);
        execSql.append("select * from (select b.*, rownum as r_n from (");
        execSql.append(sql);
        execSql.append(") b where rownum <= " + endRow + ") c where c.r_n >= " + startRow);
        execSql.trimToSize();
        logger.info("分页查询的sql:" + execSql);
        return this.queryForArray(execSql.toString(), paramMap, true);
    }
}

