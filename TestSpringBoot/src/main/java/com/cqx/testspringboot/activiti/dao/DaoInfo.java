package com.cqx.testspringboot.activiti.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DaoInfo
 *
 * @author chenqixu
 */
public class DaoInfo {
    private static final Logger logger = LoggerFactory.getLogger(DaoInfo.class);
    private DaoInfo.DB_TYPE dbType;
    private String dataSourceId;

    public DaoInfo() {
    }

    public DaoInfo(String dataSourceId, DaoInfo.DB_TYPE dbType) {
        this.dbType = dbType;
        this.dataSourceId = dataSourceId;
    }

    public DaoInfo.DB_TYPE getDbType() {
        return this.dbType;
    }

    public void setDbType(DaoInfo.DB_TYPE dbType) {
        this.dbType = dbType;
    }

    public String getDataSourceId() {
        return this.dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String toString() {
        return "DaoInfo{dbType=" + this.dbType + ", dataSourceId='" + this.dataSourceId + '\'' + '}';
    }

    public static enum DB_TYPE {
        ORACLE,
        MYSQL,
        SQLSERVER,
        DB2,
        POSTGRESQL,
        TERADATA;

        private DB_TYPE() {
        }
    }
}
