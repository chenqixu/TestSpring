package com.cqx.testspring.webservice.common.util.xml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MessageAnnotation
 *
 * @author chenqixu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MessageAnnotation {
    int mode() default 2;

    String nodeName() default "row";

    boolean sign() default false;
}
