package com.cqx.testspring.webservice.operhis.service;

import com.cqx.testspring.webservice.operhis.model.OperhisReqObject;
import com.cqx.testspring.webservice.operhis.model.OperhisRespObject;
import org.apache.log4j.Logger;

/**
 * OperhisServiceImpl
 *
 * @author chenqixu
 */
public class OperhisServiceImpl implements OperhisServiceInf {
    private static final Logger logger = Logger.getLogger(OperhisServiceImpl.class);

    @Override
    public OperhisRespObject addOperHistory(OperhisReqObject reqObject) {
        OperhisRespObject operhisRespObject = BaseServiceMgt.exec(reqObject);
        return operhisRespObject;
    }
}
