package com.cqx.testspringboot.activiti.exception;

import org.apache.commons.lang3.StringUtils;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * BaseRuntimeException
 *
 * @author chenqixu
 */
public abstract class BaseRuntimeException extends RuntimeException implements IThrowable {
    /**
     * the Throwable
     */
    private Throwable throwable;

    /**
     * 错误编码
     */
    private String code = ExceptionCode.UNKNOWN;

    /**
     * Construct a new BaseRuntimeException instance.
     *
     * @param message The detail message for this exception.
     * @param code    the error code
     */
    public BaseRuntimeException(final String message, final String code) {
        this(message, null, code);
    }

    /**
     * Construct a new BaseRuntimeException instance.
     *
     * @param throwable the root cause of the exception
     * @param code      the error code
     */
    public BaseRuntimeException(final Throwable throwable, final String code) {
        super(throwable.getMessage(), throwable);
        this.throwable = throwable;
        this.code = StringUtils.isEmpty(code) ? ExceptionCode.UNKNOWN : code;
    }

    /**
     * Construct a new BaseRuntimeException instance.
     *
     * @param message   The detail message for this exception.
     * @param throwable the root cause of the exception
     * @param code      the error code
     */
    public BaseRuntimeException(final String message, final Throwable throwable, final String code) {
        super(message, throwable);
        this.throwable = throwable;
        this.code = StringUtils.isEmpty(code) ? ExceptionCode.UNKNOWN : code;
    }

    /**
     * get error code
     *
     * @return the error code
     */
    public String getCode() {
        return code;
    }

    /**
     * Retrieve root cause of the exception.
     *
     * @return the root cause
     */
    public final Throwable getCause() {
        return this.throwable;
    }

    /**
     * Convert the Exception with the nested exceptions to a string.
     *
     * @return a string representation of the exception tree.
     */
    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append(super.toString());

        if (getCause() != null) {
            s.append(": ");
            s.append(getCause().toString());
        }

        return s.toString();
    }

    /**
     * print the stack trace for this Exception and all nested Exceptions.
     */
    public void printStackTrace() {
        super.printStackTrace();

        if (getCause() != null) {
            getCause().printStackTrace();
        }
    }

    /**
     * print the stack trace for this Exception and all nested Exceptions to the PrintStream.
     *
     * @param s a PrintStream object.
     */
    public void printStackTrace(final PrintStream s) {
        super.printStackTrace(s);

        if (getCause() != null) {
            getCause().printStackTrace(s);
        }
    }

    /**
     * print the stack trace for this Exception and all nested Exceptions. to the PrintWriter.
     *
     * @param s a PrintWriter object.
     */
    public void printStackTrace(final PrintWriter s) {
        super.printStackTrace(s);

        if (getCause() != null) {
            getCause().printStackTrace(s);
        }
    }
}
