package com.cqx.testspring.webservice.operhis.service;

import com.cqx.testspring.webservice.operhis.model.javabean.LoginBean;
import com.cqx.testspring.webservice.operhis.model.javabean.NullResultBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * LoginServiceImpl
 *
 * @author chenqixu
 */
@Component
public class LoginServiceImpl {

    static {
        BaseServiceMgt.register(LoginServiceImpl.class);
    }

    @Resource(name = "com.cqx.testspring.webservice.operhis.service.LoginServiceDaoImpl")
    private LoginServiceDaoImpl loginServiceDao;

    public NullResultBean login(LoginBean loginBean) {
        System.out.println("[login]" + loginBean);
        loginServiceDao.queryLogin();
        return new NullResultBean();
    }
}
