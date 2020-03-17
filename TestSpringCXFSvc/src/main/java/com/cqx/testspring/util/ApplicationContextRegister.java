package com.cqx.testspring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ApplicationContextRegister
 *
 * @author chenqixu
 */
@Component
public class ApplicationContextRegister implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextRegister.class);
    private static ApplicationContext APPLICATION_CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LOGGER.info("ApplicationContext registed-->{}", applicationContext);
        APPLICATION_CONTEXT = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }
}
