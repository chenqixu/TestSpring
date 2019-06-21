package com.cqx.testspringboot.activiti.dao;

import com.cqx.testspringboot.activiti.model.ProcessQuery;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * 原生sql审核任务查询
 *
 * @author chenqixu
 */
public interface INativeTaskDao {

    public List<Task> queryUserTasks(ProcessQuery processQuery);

    public int queryUserTaskCount(ProcessQuery processQuery);
}
