package com.cqx.testspring.webservice.dao;

/**
 * DBResult
 *
 * @author chenqixu
 */
public class DBResult {
    public int iErrorCode = 0;
    public String sErrorDescriptor = "成功";
    public int iTotalNum;
    public Object result = null;
    public String sErrorStrack = "";

    public DBResult() {
    }
}
