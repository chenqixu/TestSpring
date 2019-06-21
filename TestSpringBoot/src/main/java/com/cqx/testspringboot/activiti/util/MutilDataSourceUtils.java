package com.cqx.testspringboot.activiti.util;

import com.cqx.testspringboot.activiti.dao.DaoInfo;
import com.cqx.testspringboot.activiti.holder.DynamicDataSourceContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MutilDataSourceUtils
 *
 * @author chenqixu
 */
public class MutilDataSourceUtils {
    private static final Logger logger = LoggerFactory.getLogger(MutilDataSourceUtils.class);
    private static final String ORACLE_DRIVER1 = "oracle.jdbc.driver.OracleDriver";
    private static final String ORACLE_DRIVER2 = "oracle.jdbc.OracleDriver";
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String SQLSERVER_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    private static final String DB2_DRIVER = "com.ibm.db2.jcc.DB2Driver";
    private static final String POSTGRE_DRIVER = "org.postgresql.Driver";
    private static final String TERADATA_DRIVER = "com.teradata.jdbc.TeraDriver";

    public MutilDataSourceUtils() {
    }

    public static synchronized DaoInfo getCurrentDaoInfo() {
        String dataSourceId = (String) StringUtils.defaultIfEmpty(DynamicDataSourceContextHolder.getDataSourceType(), "dataSource");
        DaoInfo daoInfo = (DaoInfo)DynamicDataSourceContextHolder.mapDataSource.get(dataSourceId);
        if (daoInfo == null) {
            logger.error("★★★ daoInfo = {}", daoInfo);
            logger.error("★★★ dataSourceId = {}", dataSourceId);
            throw new RuntimeException("Invalid dataSourceId!");
        } else {
            return daoInfo;
        }
    }
}
