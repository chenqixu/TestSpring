package com.cqx.testspringboot.activiti.exception;

/**
 * 流程审批异常类
 *
 * @author chenqixu
 */
public class ProcessException extends BaseRuntimeException {

    private static final String TYPE = "process";

    public ProcessException(String message, String code) {
        super(message, code);
    }

    public ProcessException(Throwable throwable, String code) {
        super(throwable, code);
    }

    public ProcessException(String message, Throwable throwable, String code) {
        super(message, throwable, code);
    }

    public String getType() {
        return "process";
    }
}
