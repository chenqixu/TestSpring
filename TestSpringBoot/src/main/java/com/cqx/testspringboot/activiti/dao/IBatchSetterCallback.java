package com.cqx.testspringboot.activiti.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * IBatchSetterCallback
 *
 * @author chenqixu
 */
public interface IBatchSetterCallback {
    void execute(PreparedStatement var1, Object var2) throws SQLException;
}
