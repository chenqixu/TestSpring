package com.cqx.testspringboot.activiti.util;

import com.cqx.testspringboot.activiti.exception.ProcessException;
import com.cqx.testspringboot.activiti.exception.ProcessExceptionCode;
import com.cqx.testspringboot.activiti.model.ProcessInfo;
import com.cqx.testspringboot.activiti.model.ProcessQuery;
import com.cqx.testspringboot.activiti.model.ProcessTask;
import org.apache.commons.lang3.StringUtils;

/**
 * @author chenqixu
 */
public class ValidateParamUtil {

    /**
     * 验证流程查询传递过来的查询参数
     *
     * @param processQuery
     */
    public static void ValidateProcessQuery(ProcessQuery processQuery) {
        if (processQuery == null) {
            throw new ProcessException("参数不能为空", ProcessExceptionCode.NON_PARAM);
        }
        if (StringUtils.isEmpty(processQuery.getUserId())) {
            throw new ProcessException("当前用户不能为空", ProcessExceptionCode.NON_PROCESS_CURRENT_USER);
        }
        if (StringUtils.isNotEmpty(processQuery.getStartTime()) &&
                !ValidateUtil.isDateFormat(processQuery.getStartTime())) {
            throw new ProcessException("日期格式不合法:yyyy-MM-dd HH:mm:ss", ProcessExceptionCode.UN_VALID_DATE);
        }
        if (StringUtils.isNotEmpty(processQuery.getEndTime()) &&
                !ValidateUtil.isDateFormat(processQuery.getEndTime())) {
            throw new ProcessException("日期格式不合法:yyyy-MM-dd HH:mm:ss", ProcessExceptionCode.UN_VALID_DATE);
        }
    }

    /**
     * 验证处理流程任务传递过来的参数
     *
     * @param processTask
     */
    public static void ValidateProcessTask(ProcessTask processTask) {
        if (processTask == null) {
            throw new ProcessException("参数不能为空", ProcessExceptionCode.NON_PARAM);
        }
        if (StringUtils.isEmpty(processTask.getCurrentUserId())) {
            throw new ProcessException("当前用户不能为空", ProcessExceptionCode.NON_PROCESS_CURRENT_USER);
        }

        if (StringUtils.isEmpty(processTask.getCurrentTaskId())) {
            throw new ProcessException("任务ID不能为空", ProcessExceptionCode.NON_PROCESS_CURRENT_TASK);
        }
    }

    /**
     * 验证发起流程实例传递过来的参数
     *
     * @param processInfo
     */
    public static void ValidateProcessInfo(ProcessInfo processInfo) {
        if (processInfo == null) {
            throw new ProcessException("参数不能为空", ProcessExceptionCode.NON_PARAM);
        }
        if (StringUtils.isEmpty(processInfo.getProcessKey())) {
            throw new ProcessException("流程KEY不能为空", ProcessExceptionCode.NON_PROCESS_KEY);
        }
        if (StringUtils.isEmpty(processInfo.getStartUser())) {
            throw new ProcessException("流程发起人不能为空", ProcessExceptionCode.NON_PROCESS_START_USER);
        }
    }
}
