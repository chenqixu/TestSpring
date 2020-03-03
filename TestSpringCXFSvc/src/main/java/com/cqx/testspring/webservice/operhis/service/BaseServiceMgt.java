package com.cqx.testspring.webservice.operhis.service;

import com.alibaba.fastjson.JSON;
import com.cqx.testspring.webservice.common.util.session.SpringBeanFactory;
import com.cqx.testspring.webservice.operhis.model.OperhisReqObject;
import com.cqx.testspring.webservice.operhis.model.OperhisRespObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * BaseServiceMgt
 *
 * @author chenqixu
 */
public class BaseServiceMgt {
    private static final Logger logger = Logger.getLogger(BaseServiceMgt.class);
    private static final ConcurrentMap<String, Class<?>> services = new ConcurrentHashMap<>();
//    private static WebApplicationContext wac;

    public static void register(Class<?> cls) {
        logger.info("[register.services]" + cls.getSimpleName().toLowerCase());
        services.put(cls.getSimpleName().toLowerCase(), cls);
    }

//    public static synchronized void getWac() {
//        if (wac == null) wac = ContextLoader.getCurrentWebApplicationContext();
//    }

    public static OperhisRespObject exec(OperhisReqObject reqObject) {
        OperhisRespObject operhisRespObject = null;
        try {
            String req_class = reqObject.getReq_class();
            String req_content = reqObject.getReq_content();
            Class cls = Class.forName(req_class);
            Object req_obj = JSON.parseObject(req_content, cls);
            logger.info(String.format("req_obj：%s", req_obj));

            String serviceName = reqObject.getMgt_name().toLowerCase();
            String funcName = reqObject.getFunc_name().toLowerCase();
            Class<?> serviceCls = services.get(serviceName);
            if (serviceCls != null) {
                Object serviceObj = serviceCls.newInstance();
//                getWac();
//                Object serviceObj = wac.getBean(serviceCls);
                SpringBeanFactory.getSpringConfigBeanFactory().registerBeanDefinition(serviceCls.getName(),
                        BeanDefinitionBuilder.genericBeanDefinition(serviceObj.getClass()).getRawBeanDefinition());
                serviceObj = SpringBeanFactory.getBean(serviceCls.getName());
                for (Method method : serviceCls.getMethods()) {
                    String _methodName = method.getName().toLowerCase();
                    if (funcName.equals(_methodName)) {
                        Object resp_obj = method.invoke(serviceObj, req_obj);
                        operhisRespObject = new OperhisRespObject();
                        operhisRespObject.setResp_conent(JSON.toJSONString(resp_obj));
                        operhisRespObject.setResq_class(resp_obj.getClass().getName());
                        break;
                    }
                }
            } else {
                logger.error("找不到类");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operhisRespObject;
    }

}
