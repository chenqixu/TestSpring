package com.cqx.testspringboot.activiti.dao;

import com.cqx.testspringboot.activiti.model.ProcessQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.NativeHistoricProcessInstanceQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 原生SQL Oracle 历史查询
 *
 * @author chenqixu
 */
@Repository
public class NativeHistoricProcessInstanceOracleDao extends NativeHistoricProcessInstanceCommonDao {

    private static final Logger logger = LoggerFactory.getLogger(NativeHistoricProcessInstanceOracleDao.class);

    public List<HistoricProcessInstance> queryHistoricInstances(ProcessQuery processQuery) {
        StringBuffer sql = new StringBuffer("select distinct T.*,b.KEY_ processDefinitionKey,b.NAME_ processDefinitionName  from " + managementService.getTableName(HistoricProcessInstance.class) + " T" +
                "  LEFT JOIN act_hi_taskinst a on T.ID_ = a.PROC_INST_ID_" +
                "  LEFT JOIN act_re_procdef b on T.PROC_DEF_ID_ = b.ID_" +
                "  LEFT JOIN act_hi_identitylink c on a.ID_ = c.TASK_ID_" +
                " where (T.START_USER_ID_ like #{userId} or a.ASSIGNEE_ LIKE #{userId} or c.USER_ID_ LIKE #{userId} ");
        if (StringUtils.isNotEmpty(processQuery.getUserRole())) {
            sql.append(" or c.GROUP_ID_ LIKE #{userRole} )");
        } else {
            sql.append(" )");
        }
        if (StringUtils.isNotEmpty(processQuery.getProcessKey())) {
            sql.append(" and b.KEY_ = #{processKey}");
        }
        if (StringUtils.isNotEmpty(processQuery.getStartTime())) {
            sql.append(" and T.START_TIME_ > to_date(#{startTime},'yyyy-mm-dd hh24:mi:ss')");
        }
        if (StringUtils.isNotEmpty(processQuery.getEndTime())) {
            sql.append(" and T.START_TIME_ <= to_date(#{endTime},'yyyy-mm-dd hh24:mi:ss')");
        }
        if (StringUtils.equals("1", processQuery.getIsEnded())) {
            sql.append(" and T.END_TIME_ is not null");
        } else if (StringUtils.equals("0", processQuery.getIsEnded())) {
            sql.append(" and T.END_TIME_ is null");
        }
        sql.append(" and a.end_time_ is not null ");
        sql.append(" order by T.START_TIME_ desc");
        NativeHistoricProcessInstanceQuery nativeQuery = historyService.createNativeHistoricProcessInstanceQuery().sql(sql.toString())
                .parameter("userId", "%" + processQuery.getUserId() + "%")
                .parameter("userRole", processQuery.getUserRole())
                .parameter("processKey", processQuery.getProcessKey())
                .parameter("startTime", processQuery.getStartTime())
                .parameter("endTime", processQuery.getEndTime());

        logger.info("原生SQL ORACLE 历史查询:{},参数:{}", sql.toString(), processQuery.toString());
        List<HistoricProcessInstance> historicProcessInstanceList = new ArrayList<>();
        if (processQuery.getNowPage() > 0 && processQuery.getPageSize() > 0) {
            int firstResult = processQuery.getPageSize() * (processQuery.getNowPage() - 1);
            historicProcessInstanceList = nativeQuery.listPage(firstResult, processQuery.getPageSize());
        } else {
            historicProcessInstanceList = nativeQuery.listPage(0, 10);
        }

        return historicProcessInstanceList;
    }

    public int queryHistoricInstanceCount(ProcessQuery processQuery) {
        StringBuffer sql = new StringBuffer("select count(*) from (select distinct T.*,b.KEY_ processDefinitionKey,b.NAME_ processDefinitionName from " + managementService.getTableName(HistoricProcessInstance.class) + " T" +
                "  LEFT JOIN act_hi_taskinst a on T.ID_ = a.PROC_INST_ID_" +
                "  LEFT JOIN act_re_procdef b on T.PROC_DEF_ID_ = b.ID_" +
                "  LEFT JOIN act_hi_identitylink c on a.ID_ = c.TASK_ID_" +
                " where (T.START_USER_ID_ like #{userId} or a.ASSIGNEE_ LIKE #{userId} or c.USER_ID_ LIKE #{userId} ");
        if (StringUtils.isNotEmpty(processQuery.getUserRole())) {
            sql.append(" or c.GROUP_ID_ LIKE #{userRole} )");
        } else {
            sql.append(" )");
        }
        if (StringUtils.isNotEmpty(processQuery.getProcessKey())) {
            sql.append(" and b.KEY_ = #{processKey}");
        }
        if (StringUtils.isNotEmpty(processQuery.getStartTime())) {
            sql.append(" and T.START_TIME_ > to_date(#{startTime},'yyyy-mm-dd hh24:mi:ss')");
        }
        if (StringUtils.isNotEmpty(processQuery.getEndTime())) {
            sql.append(" and T.START_TIME_ <= to_date(#{endTime},'yyyy-mm-dd hh24:mi:ss')");
        }
        if (StringUtils.equals("1", processQuery.getIsEnded())) {
            sql.append(" and T.END_TIME_ is not null");
        } else if (StringUtils.equals("0", processQuery.getIsEnded())) {
            sql.append(" and T.END_TIME_ is null");
        }
        sql.append(" and a.end_time_ is not null ");
        sql.append(" order by T.START_TIME_ desc");
        sql.append(" ) xx");

        NativeHistoricProcessInstanceQuery nativeQuery = historyService.createNativeHistoricProcessInstanceQuery().sql(sql.toString())
                .parameter("userId", "%" + processQuery.getUserId() + "%")
                .parameter("userRole", processQuery.getUserRole())
                .parameter("processKey", processQuery.getProcessKey())
                .parameter("startTime", processQuery.getStartTime())
                .parameter("endTime", processQuery.getEndTime());

        logger.info("原生SQL ORACLE 历史查询统计总数:{},参数:{}", sql.toString(), processQuery.toString());
        Long totalCount = nativeQuery.count();

        return totalCount.intValue();
    }
}
