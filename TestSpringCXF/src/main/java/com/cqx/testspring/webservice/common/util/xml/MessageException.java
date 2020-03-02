package com.cqx.testspring.webservice.common.util.xml;

/**
 * MessageException
 *
 * @author chenqixu
 */
public class MessageException extends Exception {
    private static final long serialVersionUID = 1L;

    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

    public String toString() {
        return this.getMessage();
    }
}
