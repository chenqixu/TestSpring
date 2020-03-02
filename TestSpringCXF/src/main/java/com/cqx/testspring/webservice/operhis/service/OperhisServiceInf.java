package com.cqx.testspring.webservice.operhis.service;

import com.cqx.testspring.webservice.operhis.model.OperhisReqObject;
import com.cqx.testspring.webservice.operhis.model.OperhisRespObject;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * OperhisServiceInf
 *
 * @author chenqixu
 */
@WebService
public interface OperhisServiceInf {
    public @WebResult(name = "OperhisRespObject")
    OperhisRespObject addOperHistory(@WebParam(name = "OperhisReqObject") OperhisReqObject reqObject);
}
