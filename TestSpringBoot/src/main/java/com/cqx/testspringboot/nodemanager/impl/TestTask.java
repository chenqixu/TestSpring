package com.cqx.testspringboot.nodemanager.impl;

import com.cqx.common.utils.system.SleepUtil;
import com.cqx.testspringboot.nodemanager.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 测试任务
 *
 * @author chenqixu
 */
@Repository("com.cqx.testspringboot.nodemanager.impl.TestTask")
public class TestTask implements ITask {
    private static final Logger logger = LoggerFactory.getLogger(TestTask.class);
    private String name = "测试任务";

    @Override
    public void init(Map<String, String> param) throws Throwable {
        logger.info("[{}] 模拟任务初始化, 参数v1={}", name, param.get("v1"));
    }

    @Override
    public void run() throws Throwable {
        logger.info("[{}] 模拟任务执行", name);
        SleepUtil.sleepMilliSecond(1500L);
    }

    @Override
    public void release() {
        logger.info("[{}] 资源释放", name);
    }
}
