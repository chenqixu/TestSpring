package com.cqx.testspring.webservice.rsmgr.utils;

import com.cqx.common.utils.system.SleepUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 模拟租户创建流程
 *
 * @author chenqixu
 */
@Repository("IntfHuaWeiTenantProcess")
public class IntfHuaWeiTenantProcess {
    private static final Logger logger = LoggerFactory.getLogger(IntfHuaWeiTenantProcess.class);
    @Autowired
    private IntfHuaWeiUtil intfHuaWeiUtil;

    /**
     * 模拟租户创建过程
     */
    public void createTenant() {
        synchronized (intfHuaWeiUtil.dbLock) {
            logger.info("【createTenant】id：{}", intfHuaWeiUtil.getId());
            logger.info("【createTenant】创建租户");
            SleepUtil.sleepSecond(1);
            logger.info("【createTenant】创建用户");
            SleepUtil.sleepSecond(1);
            logger.info("【createTenant】创建HIVE库");
            SleepUtil.sleepSecond(1);
            logger.info("【createTenant】HDFS授权");
            SleepUtil.sleepSecond(1);
            logger.info("【createTenant】YARN授权");
            SleepUtil.sleepSecond(1);
            logger.info("【createTenant】HIVE授权");
            SleepUtil.sleepSecond(1);
            logger.info("【createTenant】阿里HIVE授权");
            SleepUtil.sleepSecond(1);
            logger.info("【createTenant】阿里HIVE对应HDFS授权操作");
            SleepUtil.sleepSecond(1);
        }
    }
}
