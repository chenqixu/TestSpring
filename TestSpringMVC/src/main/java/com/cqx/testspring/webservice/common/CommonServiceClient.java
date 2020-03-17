package com.cqx.testspring.webservice.common;

import com.alibaba.fastjson.JSON;
import com.cqx.testspring.webservice.common.model.CommonReqObject;
import com.cqx.testspring.webservice.common.model.CommonRespObject;
import com.cqx.testspring.webservice.common.service.CommonServiceInf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CommonServiceClient
 *
 * @author chenqixu
 */
public class CommonServiceClient extends BaseClient {

    private static final Logger logger = LoggerFactory.getLogger(CommonServiceClient.class);

    public <T, E> E exec(String mgt_name, String func_name, T t) {
        Object resp_obj = null;
        try {
            CommonServiceInf commonServiceInf = super.getService(CommonServiceInf.class);
            CommonReqObject reqObject = new CommonReqObject();
            reqObject.setReq_class(t.getClass().getName());
            reqObject.setReq_content(JSON.toJSONString(t));
            reqObject.setMgt_name(mgt_name);
            reqObject.setFunc_name(func_name);
            CommonRespObject respObject = commonServiceInf.call(reqObject);
            Class cls = Class.forName(respObject.getResq_class());
            resp_obj = JSON.parseObject(respObject.getResp_conent(), cls);
        } catch (Exception e) {
            logger.error(String.format("CommonServiceClient调用异常，具体信息：%s", e.getMessage()), e);
        }
        return (E) resp_obj;
    }
}
