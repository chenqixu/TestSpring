package com.cqx.testspring.webservice.common.util.xml;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * BeanUtil
 *
 * @author chenqixu
 */
public class BeanUtil {

    public BeanUtil() {
    }

    public static void setBeanProptyValue(Object beanObj, int j, String value) {
        try {
            Field[] rowAllFields = beanObj.getClass().getDeclaredFields();
            String fieldName = rowAllFields[j].getName();
            if (fieldName.equals("serialVersionUID")) {
                return;
            }

            BeanUtils.setProperty(beanObj, fieldName, value);
        } catch (Exception var5) {
        }

    }

    public static Map<String, Class<?>> getClassFields(Class<?> clazz, boolean includeParentClass) {
        Map<String, Class<?>> map = new HashMap();
        Field[] fields = clazz.getDeclaredFields();
        Field[] var7 = fields;
        int var6 = fields.length;

        for (int var5 = 0; var5 < var6; ++var5) {
            Field field = var7[var5];
            map.put(field.getName(), field.getType());
        }

        if (includeParentClass) {
            getParentClassFields(map, clazz.getSuperclass());
        }

        return map;
    }

    private static Map<String, Class<?>> getParentClassFields(Map<String, Class<?>> map, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Field[] var6 = fields;
        int var5 = fields.length;

        for (int var4 = 0; var4 < var5; ++var4) {
            Field field = var6[var4];
            if (!map.containsKey(field.getName())) {
                map.put(field.getName(), field.getType());
            }
        }

        if (clazz.getSuperclass() == null) {
            return map;
        } else {
            getParentClassFields(map, clazz.getSuperclass());
            return map;
        }
    }
}
