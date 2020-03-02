package com.cqx.testspring.webservice.common.manage;

import com.cqx.testspring.webservice.common.model.ServiceBean;
import com.cqx.testspring.webservice.common.model.ServiceListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * WebServiceMgtImpl
 *
 * @author chenqixu
 */
public class WebServiceMgtImpl implements WebServiceMgt {
//    private WebServiceDao webServiceDao = new WebServiceDaoImpl();

    public WebServiceMgtImpl() {
    }

    public List<ServiceBean> queryService(ServiceBean serviceBean) throws Exception {
        List<ServiceBean> serviceBeanList = new ArrayList();
//        DBResult ret = this.webServiceDao.queryService(serviceBean);
//        if (ret.iErrorCode == 0 && ret.iSum > 0) {
//            ServiceBean tempServiceBean = null;
//
//            for (int i = 0; i < ret.iSum; ++i) {
//                tempServiceBean = new ServiceBean();
//
//                for (int j = 0; j < ret.iFieldCount; ++j) {
//                    BeanUtil.setBeanProptyValue(tempServiceBean, j, ret.aaOut[i][j]);
//                }
//
//                serviceBeanList.add(tempServiceBean);
//            }
//        }

        ServiceBean tempServiceBean = new ServiceBean();
        tempServiceBean.setServiceImplClass("com.cqx.testspring.webservice.operhis.service.OperhisServiceImpl");
        tempServiceBean.setServiceName("OperhisServiceInf");
        serviceBeanList.add(tempServiceBean);

        return serviceBeanList;
    }

    @Deprecated
    public ServiceListBean queryServiceListBean(ServiceBean serviceBean, String start, String pageSize) throws Exception {
        ServiceListBean serviceListBean = new ServiceListBean();
        List<ServiceBean> serviceBeanList = new ArrayList();
//        DBResult ret = this.webServiceDao.queryService(serviceBean, Integer.valueOf(start), Integer.valueOf(pageSize));
//        if (ret.iErrorCode == 0 && ret.iSum > 0) {
//            ServiceBean tempServiceBean = null;
//
//            for (int i = 0; i < ret.iSum; ++i) {
//                tempServiceBean = new ServiceBean();
//
//                for (int j = 0; j < ret.iFieldCount - 1; ++j) {
//                    BeanUtil.setBeanProptyValue(tempServiceBean, j, ret.aaOut[i][j]);
//                }
//
//                serviceBeanList.add(tempServiceBean);
//            }
//        }

        serviceListBean.setServiceBeanList(serviceBeanList);
        serviceListBean.setStart(start);
        serviceListBean.setPageSize(pageSize);
//        serviceListBean.setTotalCount(String.valueOf(ret.iTotalNum));
        return serviceListBean;
    }
}
