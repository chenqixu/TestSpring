package com.cqx.testspringboot.activiti.dao;

import com.cqx.testspringboot.activiti.model.ProcessQuery;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 原生ORACLE SQL任务查询
 *
 * @author chenqixu
 */
@Repository
public class NativeTaskOracleDao extends NativeTaskCommonDao {

    private static final Logger logger = LoggerFactory.getLogger(NativeTaskOracleDao.class);

    public List<Task> queryUserTasks(ProcessQuery processQuery) {
        StringBuffer sql = new StringBuffer("select distinct T.* from " + managementService.getTableName(Task.class) + " T " +
                " LEFT JOIN act_ru_identitylink a on T.ID_ = a.TASK_ID_ " +
                " LEFT JOIN act_re_procdef b on T.PROC_DEF_ID_ = b.ID_ " +
                " where (a.USER_ID_ like #{userId} or T.ASSIGNEE_ LIKE #{userId} ");
        if (StringUtils.isNotEmpty(processQuery.getUserRole())) {
            sql.append(" or a.GROUP_ID_ LIKE #{userRole} )");
        } else {
            sql.append(" )");
        }
        if (StringUtils.isNotEmpty(processQuery.getProcessKey())) {
            sql.append(" and b.KEY_ = #{processKey}");
        }
        if (StringUtils.isNotEmpty(processQuery.getStartTime())) {
            sql.append(" and T.CREATE_TIME_ > to_date(#{startTime},'yyyy-mm-dd hh24:mi:ss')");
        }
        if (StringUtils.isNotEmpty(processQuery.getEndTime())) {
            sql.append(" and T.CREATE_TIME_ <= to_date(#{endTime},'yyyy-mm-dd hh24:mi:ss')");
        }
        sql.append(" order by T.CREATE_TIME_ desc");
        NativeTaskQuery nativeTaskQuery = taskService.createNativeTaskQuery().sql(sql.toString())
                .parameter("userId", "%" + processQuery.getUserId() + "%")
                .parameter("userRole", processQuery.getUserRole())
                .parameter("processKey", processQuery.getProcessKey())
                .parameter("startTime", processQuery.getStartTime())
                .parameter("endTime", processQuery.getEndTime());

        logger.info("原生SQL ORACLE 任务查询:{},参数:{}", sql.toString(), processQuery.toString());

        List<Task> taskList = new ArrayList<>();
        if (processQuery.getNowPage() > 0 && processQuery.getPageSize() > 0) {
            int firstResult = processQuery.getPageSize() * (processQuery.getNowPage() - 1);
            taskList = nativeTaskQuery.listPage(firstResult, processQuery.getPageSize());
        } else {
            taskList = nativeTaskQuery.listPage(0, 10);
        }

        return taskList;
    }

    public int queryUserTaskCount(ProcessQuery processQuery) {
        StringBuffer sql = new StringBuffer("select count(*) from (select distinct T.* from " + managementService.getTableName(Task.class) + " T " +
                " LEFT JOIN act_ru_identitylink a on T.ID_ = a.TASK_ID_ " +
                " LEFT JOIN act_re_procdef b on T.PROC_DEF_ID_ = b.ID_ " +
                " where (a.USER_ID_ like #{userId} or T.ASSIGNEE_ LIKE #{userId} ");
        if (StringUtils.isNotEmpty(processQuery.getUserRole())) {
            sql.append(" or a.GROUP_ID_ LIKE #{userRole} )");
        } else {
            sql.append(" )");
        }
        if (StringUtils.isNotEmpty(processQuery.getProcessKey())) {
            sql.append(" and b.KEY_ = #{processKey}");
        }
        if (StringUtils.isNotEmpty(processQuery.getStartTime())) {
            sql.append(" and T.CREATE_TIME_ > to_date(#{startTime},'yyyy-mm-dd hh24:mi:ss')");
        }
        if (StringUtils.isNotEmpty(processQuery.getEndTime())) {
            sql.append(" and T.CREATE_TIME_ <= to_date(#{endTime},'yyyy-mm-dd hh24:mi:ss')");
        }
        sql.append(" ) xx");
        NativeTaskQuery nativeTaskQuery = taskService.createNativeTaskQuery().sql(sql.toString())
                .parameter("userId", "%" + processQuery.getUserId() + "%")
                .parameter("userRole", processQuery.getUserRole())
                .parameter("processKey", processQuery.getProcessKey())
                .parameter("startTime", processQuery.getStartTime())
                .parameter("endTime", processQuery.getEndTime());
        logger.info("原生SQL ORACLE 任务查询统计总数:{},参数:{}", sql.toString(), processQuery.toString());

        Long totalCount = nativeTaskQuery.count();

        return totalCount.intValue();
    }

}
