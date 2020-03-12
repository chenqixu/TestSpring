package com.cqx.testspring.webservice.operhi.client;

import com.alibaba.fastjson.JSON;
import com.cqx.testspring.webservice.common.BaseClient;
import com.cqx.testspring.webservice.operhis.model.OperhisReqObject;
import com.cqx.testspring.webservice.operhis.model.OperhisRespObject;
import com.cqx.testspring.webservice.operhis.service.OperhisServiceInf;

/**
 * OperhisClient
 *
 * @author chenqixu
 */
public class OperhisClient extends BaseClient {

//    static {
//        System.setProperty("javax.net.ssl.trustStore", "d:\\Soft\\Apache\\tomcat\\apache-tomcat-6.0.44\\conf\\tomcat.keystore"); //key路径
//        System.setProperty("javax.net.ssl.trustStorePassword", "123456");//密码
//        System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
//        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//    }

    public <T, E> E exec(String mgt_name, String func_name, T t) {
        Object resp_obj = null;
        try {
            OperhisServiceInf operhisServiceInf = super.getService(OperhisServiceInf.class);
            OperhisReqObject operhisReqObject = new OperhisReqObject();
            operhisReqObject.setReq_class(t.getClass().getName());
            operhisReqObject.setReq_content(JSON.toJSONString(t));
            operhisReqObject.setMgt_name(mgt_name);
            operhisReqObject.setFunc_name(func_name);
            OperhisRespObject operhisRespObject = operhisServiceInf.addOperHistory(operhisReqObject);
            Class cls = Class.forName(operhisRespObject.getResq_class());
            resp_obj = JSON.parseObject(operhisRespObject.getResp_conent(), cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (E) resp_obj;
    }

//    public <T> T newInstance(Class<T> cls) {
//        try {
//            return cls.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public <T, E> E addOperHistory(T t) {
//        Object resp_obj = null;
//        try {
//            OperhisServiceInf operhisServiceInf = super.getService(OperhisServiceInf.class);
//            OperhisReqObject operhisReqObject = new OperhisReqObject();
//            operhisReqObject.setReq_class(t.getClass().getName());
//            operhisReqObject.setReq_content(JSON.toJSONString(t));
//            OperhisRespObject operhisRespObject = operhisServiceInf.addOperHistory(operhisReqObject);
//            Class cls = Class.forName(operhisRespObject.getResq_class());
//            resp_obj = JSON.parseObject(operhisRespObject.getResp_conent(), cls);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return (E) resp_obj;
//    }
}
