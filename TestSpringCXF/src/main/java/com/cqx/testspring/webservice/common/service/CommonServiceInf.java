package com.cqx.testspring.webservice.common.service;

import com.cqx.testspring.webservice.common.model.CommonReqObject;
import com.cqx.testspring.webservice.common.model.CommonRespObject;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * CommonServiceInf
 *
 * @author chenqixu
 */
@WebService
public interface CommonServiceInf {
    public @WebResult(name = "CommonRespObject")
    CommonRespObject call(@WebParam(name = "CommonReqObject") CommonReqObject reqObject);
}
