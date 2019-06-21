package com.cqx.testspringboot.activiti.dao;

import com.cqx.testspringboot.activiti.model.ProcessQuery;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 原生SQL历史查询
 *
 * @author chenqixu
 */
@Repository("nativeHistoricProcessInstanceCommonDao")
public class NativeHistoricProcessInstanceCommonDao implements INativeHistoricProcessInstanceDao {

    private static final Logger logger = LoggerFactory.getLogger(NativeHistoricProcessInstanceCommonDao.class);

    @Resource(name = "com.cqx.testspringboot.activiti.dao.CommonBaseDao")
    protected CommonBaseDao dao;

    @Resource
    protected HistoryService historyService;

    @Resource
    protected ManagementService managementService;

    @Override
    public List<HistoricProcessInstance> queryHistoricInstances(ProcessQuery processQuery) {
        return null;
    }

    @Override
    public int queryHistoricInstanceCount(ProcessQuery processQuery) {
        return 0;
    }
}
