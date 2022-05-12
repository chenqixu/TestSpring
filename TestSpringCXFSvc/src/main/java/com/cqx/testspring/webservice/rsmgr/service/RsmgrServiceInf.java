package com.cqx.testspring.webservice.rsmgr.service;

import com.cqx.testspring.webservice.rsmgr.bean.RsmgrReqObject;
import com.cqx.testspring.webservice.rsmgr.bean.RsmgrRespObject;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * 资源服务化的接口
 *
 * @author chenqixu
 */
@WebService
public interface RsmgrServiceInf {

    /**
     * 查询申请列表
     *
     * @param reqObject
     * @return
     */
    @WebResult(name = "Message")
    RsmgrRespObject qryApplyList(@WebParam(name = "Message") RsmgrReqObject reqObject);
}
