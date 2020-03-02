package com.cqx.testspring.webservice.common.util.session;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ContextLoader;

/**
 * SpringBeanFactory
 *
 * @author chenqixu
 */
public class SpringBeanFactory {
    private static ApplicationContext ctx = null;
    private static ConfigurableApplicationContext cac = null;

    public SpringBeanFactory() {
    }

    public static Object getBean(String contextFile, String beanName) throws BeansException, Exception {
        return getBeanFactory(contextFile).getBean(beanName);
    }

    private static BeanFactory getBeanFactory(String contextFile) throws Exception {
        Resource res = new ByteArrayResource(IOUtils.toByteArray(Resources.getResourceAsStream(contextFile)));
        XmlBeanFactory factory = new XmlBeanFactory(res);
        return factory;
    }

    public static Object getBean(String interfaceName) {
        if (ctx == null) {
            ctx = ContextLoader.getCurrentWebApplicationContext();
        }

        return ctx.getBean(interfaceName);
    }

    public static DefaultListableBeanFactory getSpringConfigBeanFactory() {
        if (cac == null) {
            cac = (ConfigurableApplicationContext) ContextLoader.getCurrentWebApplicationContext();
        }

        return (DefaultListableBeanFactory) cac.getAutowireCapableBeanFactory();
    }
}
