package com.cqx.testspring.webservice.rsmgr.utils;

import com.cqx.common.utils.system.SleepUtil;
import com.cqx.common.utils.system.TimeCostUtil;
import com.cqx.testspring.webservice.dao.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 模拟接口工具
 *
 * @author chenqixu
 */
@Repository("IntfHuaWeiUtil")
public class IntfHuaWeiUtil {
    private static final Logger logger = LoggerFactory.getLogger(IntfHuaWeiUtil.class);
    public final Object dbLock = new Object();
    @Resource(name = "com.cqx.testspring.webservice.dao.BaseDao")
    private BaseDao baseDao;
    private int id;

    /**
     * 数据库参数初始化<br>
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次<br>
     * PostConstruct在构造函数之后执行，init方法之前执行
     */
    @PostConstruct
    public void paramsInit() {
        // 参数定时初始化，5毫秒后执行第一次，之后每隔半小时执行一次
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dbInfoInit();
            }
        }, 5, 1000 * 60 * 1);
    }

    /**
     * 模拟参数初始化，从数据库配置表获取
     */
    private void dbInfoInit() {
        logger.info("========参数定时初始化：开始========");
        // 需要加锁，避免下游在调用的时候，数据库发生了变化
        synchronized (dbLock) {
            logger.info("========参数定时初始化：抢到锁========");
            TimeCostUtil tc = new TimeCostUtil();
            tc.start();
            List<Integer> rets = (List<Integer>) baseDao.query("select id from cqx_test5", Integer.class);
            for (int ret : rets) {
                logger.info("【dbInfoInit】查询到的ret：{}", ret);
                this.id = ret;
            }
            logger.info("【dbInfoInit】初始化三态环境");
            SleepUtil.sleepSecond(3);
            logger.info("【dbInfoInit】初始化公共接口Api");
            SleepUtil.sleepSecond(3);
            logger.info("【dbInfoInit】获取阿里HIVE数据库生产态");
            SleepUtil.sleepSecond(3);
            logger.info("【dbInfoInit】获取阿里HIVE数据库开发态");
            SleepUtil.sleepSecond(3);
            logger.info(String.format("========参数定时初始化：完成，耗时：%s========", tc.stopAndGet()));
        }
    }

    public int getId() {
        return id;
    }
}
