package com.cqx.testspringboot.activiti.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * IGetterCallback
 *
 * @author chenqixu
 */
public interface IGetterCallback {
    <T> T execute(ResultSet var1) throws SQLException;
}
