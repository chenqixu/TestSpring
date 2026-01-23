package com.cqx.testpring.win7.autoconfigure.annotation;

import java.lang.annotation.*;

/**
 * TargetDataSourcev
 *
 * @author chenqixu
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String value();
}
