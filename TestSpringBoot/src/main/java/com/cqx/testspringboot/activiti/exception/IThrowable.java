package com.cqx.testspringboot.activiti.exception;

/**
 * IThrowable
 *
 * @author chenqixu
 */
public interface IThrowable {

    /**
     * The cause of the Throwable.
     *
     * @return Throwable throwable
     */
    public Throwable getCause();

    /**
     * The string of exception type
     *
     * @return Exception type string
     */
    public String getType();
}
