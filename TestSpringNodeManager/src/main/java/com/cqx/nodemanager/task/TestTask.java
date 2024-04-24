package com.cqx.nodemanager.task;

import com.cqx.common.utils.system.SleepUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 测试任务
 *
 * @author chenqixu
 */
public class TestTask implements ITask {
    private static final Logger logger = LoggerFactory.getLogger(TestTask.class);

    @Override
    public void init(Map<String, String> param) throws Throwable {
        String test1 = param.get("test1");
        logger.info("[参数]test1={}", test1);
    }

    @Override
    public void run() throws Throwable {
        logger.info("模拟运行开始");
        int i = 10;
        while (i > 0) {
            logger.info("模拟运行, i={}", i);
            SleepUtil.sleepMilliSecond(500);
            i--;
        }
        logger.info("模拟运行完成");
    }

    @Override
    public void release() {
        logger.info("资源释放");
    }
}
