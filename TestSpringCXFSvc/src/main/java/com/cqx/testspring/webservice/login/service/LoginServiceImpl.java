package com.cqx.testspring.webservice.login.service;

import com.cqx.testspring.webservice.common.manage.CommonMgtImpl;
import com.cqx.testspring.webservice.login.dao.LoginServiceDaoImpl;
import com.cqx.testspring.webservice.login.LoginBean;
import com.cqx.testspring.webservice.login.NullResultBean;
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
        CommonMgtImpl.register(LoginServiceImpl.class);
    }

    @Resource(name = "com.cqx.testspring.webservice.login.dao.LoginServiceDaoImpl")
    private LoginServiceDaoImpl loginServiceDao;

    public NullResultBean login(LoginBean loginBean) {
        System.out.println("[login]" + loginBean);
        loginServiceDao.queryLogin();
        return new NullResultBean();
    }
}
