package com.cqx.testspringboot.activiti.dao;

import com.cqx.testspringboot.activiti.model.ProcessQuery;
import org.activiti.engine.history.HistoricProcessInstance;

import java.util.List;

/**
 * 原生SQL历史查询接口
 *
 * @author chenqixu
 */
public interface INativeHistoricProcessInstanceDao {

    /**
     * 根据条件查询流程历史(我参与的流程)
     *
     * @param processQuery
     * @return
     */
    public List<HistoricProcessInstance> queryHistoricInstances(ProcessQuery processQuery);

    /**
     * 根据条件查询流程历史数量(我参与的流程)
     *
     * @param processQuery
     * @return
     */
    public int queryHistoricInstanceCount(ProcessQuery processQuery);
}
