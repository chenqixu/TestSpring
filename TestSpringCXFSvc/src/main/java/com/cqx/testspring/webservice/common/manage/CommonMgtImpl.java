package com.cqx.testspring.webservice.common.manage;

import com.alibaba.fastjson.JSON;
import com.cqx.testspring.webservice.common.model.CommonReqObject;
import com.cqx.testspring.webservice.common.model.CommonRespObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * CommonMgtImpl
 *
 * @author chenqixu
 */
public class CommonMgtImpl {

    private static final Logger logger = LoggerFactory.getLogger(CommonMgtImpl.class);
    private static final ConcurrentMap<String, Class<?>> services = new ConcurrentHashMap<>();
    private static ApplicationContext wac;

    public static void register(Class<?> cls) {
        logger.info("[register.services]" + cls.getSimpleName().toLowerCase());
        services.put(cls.getSimpleName().toLowerCase(), cls);
    }

    public static synchronized void initWac(ApplicationContext context) {
        logger.info("[initWac start]，context：{}", context);
        if (wac == null) {
            wac = ContextLoader.getCurrentWebApplicationContext();
            if (wac == null && context != null) {
                wac = context;
            }
        }
        logger.info("[initWac end]，wac：{}", wac);
    }

    public static CommonRespObject exec(CommonReqObject reqObject) {
        CommonRespObject respObject = null;
        String req_class = reqObject.getReq_class();
        String req_content = reqObject.getReq_content();
        String serviceName = reqObject.getMgt_name().toLowerCase();
        String funcName = reqObject.getFunc_name().toLowerCase();
        try {
            Class cls = Class.forName(req_class);
            Object req_obj = JSON.parseObject(req_content, cls);
            logger.info(String.format("[%s . %s]调用对象：%s", serviceName, funcName, req_obj));
            Class<?> serviceCls = services.get(serviceName);
            if (serviceCls != null) {
                Object serviceObj = wac.getBean(serviceCls);
                for (Method method : serviceCls.getMethods()) {
                    String _methodName = method.getName().toLowerCase();
                    if (funcName.equals(_methodName)) {
                        Object resp_obj = method.invoke(serviceObj, req_obj);
                        respObject = new CommonRespObject();
                        respObject.setResp_conent(JSON.toJSONString(resp_obj));
                        respObject.setResq_class(resp_obj.getClass().getName());
                        break;
                    }
                }
            } else {
                throw new ClassNotFoundException(serviceName);
            }
        } catch (Exception e) {
            logger.error(String.format("[%s . %s]调用异常，信息：%s", serviceName, funcName, e.getMessage()), e);
        }
        return respObject;
    }
}
