package com.cqx.testspringboot.activiti.dao;

import java.sql.SQLException;

/**
 * AbstractBaseDao
 *
 * @author chenqixu
 */
public abstract class AbstractBaseDao implements IBaseDao {
    public AbstractBaseDao() {
    }

    public String getKey(String seqName) throws SQLException {
        return "";
    }

    public String getKey(String seqName, String prefixName) throws SQLException {
        return "";
    }

    protected String buildPageQuerySQL(String sql, int startPage, int limit) {
        return "";
    }

    public String buildDeleteTabSQL(String schemaName, String tabName) throws SQLException {
        return "";
    }
}
