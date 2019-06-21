package com.cqx.testspringboot.activiti.util;

import org.activiti.engine.history.HistoricVariableInstance;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * activiti中使用得到的工具方法
 *
 * @author chenqixu
 */
public class ActivitiUtil {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiUtil.class);

    /**
     * 将历史参数列表设置到实体中去
     *
     * @param entity          实体
     * @param varInstanceList 历史参数列表
     */
    public static <T> void setVars(T entity, List<HistoricVariableInstance> varInstanceList) {
        Class<?> tClass = entity.getClass();
        try {
            Field[] fields = tClass.getDeclaredFields();
            for (HistoricVariableInstance varInstance : varInstanceList) {
                if (!isContainField(varInstance.getVariableName(), fields)) {
                    continue;
                }
                Field field = tClass.getDeclaredField(varInstance.getVariableName());
                if (field == null) {
                    continue;
                }
                field.setAccessible(true);
                field.set(entity, varInstance.getValue());
            }
        } catch (Exception e) {
            logger.error("获取历史参数异常:{}", e.getMessage());
        }
    }

    public static Map<String, Object> getVarMap(List<HistoricVariableInstance> varInstanceList) {
        Map<String, Object> varMap = new HashMap<>();
        for (HistoricVariableInstance varInstance : varInstanceList) {
            varMap.put(varInstance.getVariableName(), varInstance.getValue());
        }
        return varMap;
    }

    private static boolean isContainField(String variableName, Field[] fields) {
        if (fields != null && fields.length > 0 && StringUtils.isNotEmpty(variableName)) {
            for (int i = 0; i < fields.length; i++) {
                if (StringUtils.equals(variableName, fields[i].getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
