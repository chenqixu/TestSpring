package com.cqx.testspring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassUtil
 *
 * @author chenqixu
 */
public class ClassUtil {
    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);
    protected Map<String, Class<?>> classPool = Collections.synchronizedMap(new HashMap());

    public ClassUtil() {
    }

    public Object getClassInstance(String className) {
        Object classObj = null;

        try {
            synchronized (this) {
                Class objClass;
                if (this.classPool.containsKey(className)) {
                    objClass = (Class) this.classPool.get(className);
                    classObj = objClass.newInstance();
                    return classObj;
                }

                objClass = this.getClass(className);
                this.classPool.put(className, objClass);
                classObj = objClass.newInstance();
            }
        } catch (Exception var7) {
            logger.error("创建实例失败", var7);
        }

        return classObj;
    }

    public Class<?> getClass(String className) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = this.getClass().getClassLoader();
        }

        try {
            return classLoader.loadClass(className);
        } catch (Exception var4) {
            logger.error("加载类失败！", var4);
            return null;
        }
    }

    public Method getMethod(Class<?> mClass, String methodName, Class<?>[] argsClass) {
        try {
            return mClass.getMethod(methodName, argsClass);
        } catch (SecurityException var5) {
            logger.error("获取方法时安全异常", var5);
            return null;
        } catch (NoSuchMethodException var6) {
            logger.error("未找到该方法", var6);
            return null;
        }
    }

    public Object runMethod(Object classObj, String methodName, Class<?>[] argsClass, Object[] argsValue) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Method method = this.getMethod(classObj.getClass(), methodName, argsClass);
        return method.invoke(classObj, argsValue);
    }
}
