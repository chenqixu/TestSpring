package com.cqx.testspring.webservice.common.service;

import com.cqx.testspring.webservice.common.manage.CommonMgtImpl;
import com.cqx.testspring.webservice.common.model.CommonReqObject;
import com.cqx.testspring.webservice.common.model.CommonRespObject;

/**
 * CommonServiceImpl
 *
 * @author chenqixu
 */
public class CommonServiceImpl implements CommonServiceInf {

    @Override
    public CommonRespObject call(CommonReqObject reqObject) {
        return CommonMgtImpl.exec(reqObject);
    }

}
