package com.cqx.testspringboot.nodemanager;

import java.util.Map;

/**
 * 任务接口
 *
 * @author chenqixu
 */
public interface ITask {
    void init(Map<String, String> param) throws Throwable;

    void run() throws Throwable;

    void release();
}
