package com.cqx.testspring.webservice.operhis.service;

import com.cqx.testspring.webservice.operhis.model.javabean.LoginBean;
import com.cqx.testspring.webservice.operhis.model.javabean.NullResultBean;
import org.springframework.stereotype.Component;

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

    public NullResultBean login(LoginBean loginBean) {
        System.out.println("[login]" + loginBean);
        return new NullResultBean();
    }
}
