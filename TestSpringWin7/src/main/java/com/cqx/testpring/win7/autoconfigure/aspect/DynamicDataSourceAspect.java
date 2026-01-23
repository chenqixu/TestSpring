package com.cqx.testpring.win7.autoconfigure.aspect;

import com.cqx.testpring.win7.autoconfigure.annotation.TargetDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;

/**
 * DynamicDataSourceAspect
 *
 * @author chenqixu
 */
@Aspect
@Order(-1)
@EnableAspectJAutoProxy(
        exposeProxy = true
)
@Slf4j
public class DynamicDataSourceAspect {
    public DynamicDataSourceAspect() {
    }


    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint point, TargetDataSource targetDataSource) throws Throwable {
        String dsId = targetDataSource.value();
        if (StringUtils.isEmpty(dsId)) {
            Object[] objs = point.getArgs();
            if (objs != null && objs.length > 0) {
                dsId = objs[0].toString();
            }
        }

        log.info("☆☆☆ Change to {}", dsId);
    }

    @After("@annotation(targetDataSource)")
    public void restoreDataSource(JoinPoint point, TargetDataSource targetDataSource) {
        log.info("☆☆☆ Restore DataSource");
    }
}
