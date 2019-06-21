package com.cqx.testspringboot.activiti.dao;

import com.cqx.testspringboot.activiti.util.MutilDataSourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * FacadeDao
 *
 * @author chenqixu
 */
public class FacadeDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(FacadeDao.class);

    public FacadeDao() {
    }

    protected synchronized T getBusDao(Map<DaoInfo.DB_TYPE, T> mapBaseDao) {
        DaoInfo daoInfo = MutilDataSourceUtils.getCurrentDaoInfo();
        return mapBaseDao.get(daoInfo.getDbType());
    }
}
