package com.cqx.testspringboot.activiti.dao;

import com.cqx.testspringboot.activiti.model.ProcessQuery;
import org.activiti.engine.ManagementService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 原生SQL任务查询
 *
 * @author chenqixu
 */
@Repository("nativeTaskCommonDao")
public class NativeTaskCommonDao implements INativeTaskDao {

    private static final Logger logger = LoggerFactory.getLogger(NativeTaskCommonDao.class);

    @Resource(name = "com.cqx.testspringboot.activiti.dao.CommonBaseDao")
    protected CommonBaseDao dao;

    @Resource
    protected TaskService taskService;

    @Resource
    protected ManagementService managementService;

    @Override
    public List<Task> queryUserTasks(ProcessQuery processQuery) {
        return null;
    }

    @Override
    public int queryUserTaskCount(ProcessQuery processQuery) {
        return 0;
    }
}
