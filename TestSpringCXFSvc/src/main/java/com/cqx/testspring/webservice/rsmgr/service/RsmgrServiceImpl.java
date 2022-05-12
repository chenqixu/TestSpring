package com.cqx.testspring.webservice.rsmgr.service;

import com.cqx.testspring.webservice.rsmgr.bean.RsmgrReqObject;
import com.cqx.testspring.webservice.rsmgr.bean.RsmgrRespObject;
import com.cqx.testspring.webservice.rsmgr.manage.RsmgrServiceMgtImpl;

import javax.annotation.Resource;

/**
 * 资源服务化接口的实现
 *
 * @author chenqixu
 */
public class RsmgrServiceImpl implements RsmgrServiceInf {
    @Resource(name = "com.cqx.testspring.webservice.rsmgr.manage.RsmgrServiceMgtImpl")
    private RsmgrServiceMgtImpl rsmgrServiceMgt;

    @Override
    public RsmgrRespObject qryApplyList(RsmgrReqObject reqObject) {
        return rsmgrServiceMgt.interfaceCall();
    }
}
