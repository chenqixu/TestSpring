package com.cqx.testspring.webservice.login.dao;

import org.springframework.stereotype.Repository;

/**
 * LoginServiceDaoImpl
 *
 * @author chenqixu
 */
@Repository("com.cqx.testspring.webservice.login.dao.LoginServiceDaoImpl")
public class LoginServiceDaoImpl {
    public void queryLogin() {
        System.out.println("queryLogin……");
    }
}
