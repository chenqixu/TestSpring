package com.cqx.testspringboot.activiti.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ISetterCallback
 *
 * @author chenqixu
 */
public interface ISetterCallback {
    void execute(PreparedStatement var1) throws SQLException;
}
