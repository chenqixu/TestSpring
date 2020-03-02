package com.cqx.testspring.webservice.common.manage;

import com.cqx.testspring.webservice.common.model.ServiceBean;
import com.cqx.testspring.webservice.common.model.ServiceListBean;

import java.util.List;

/**
 * WebServiceMgt
 *
 * @author chenqixu
 */
public interface WebServiceMgt {
    List<ServiceBean> queryService(ServiceBean var1) throws Exception;

    @Deprecated
    ServiceListBean queryServiceListBean(ServiceBean var1, String var2, String var3) throws Exception;
}
