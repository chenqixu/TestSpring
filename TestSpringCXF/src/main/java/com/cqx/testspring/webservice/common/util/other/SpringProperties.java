package com.cqx.testspring.webservice.common.util.other;

import com.cqx.testspring.webservice.common.util.session.SpringBeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * SpringProperties
 *
 * @author chenqixu
 */
public class SpringProperties {
    private static String contextFile = "resources/cxf-clients.xml";
    private static Properties properties = null;

    public SpringProperties() {
        contextFile = "resources/cxf-clients.xml";
        properties = null;
        init();
    }

    public SpringProperties(String contextFile) {
        contextFile = contextFile;
        properties = null;
        init();
    }

    private static void init() {
        try {
            if (properties != null) {
                return;
            }

            SpringPropertiesFiles springPropertiesFiles = (SpringPropertiesFiles) SpringBeanFactory.getBean(contextFile, "propertiesFiles");
            List<String> propFiles = springPropertiesFiles.getPropFile();
            properties = new Properties();
            Iterator var3 = propFiles.iterator();

            while (var3.hasNext()) {
                String file = (String) var3.next();
                PropertiesLoaderUtils.fillProperties(properties, new ClassPathResource(file));
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        } catch (BeansException var5) {
            var5.printStackTrace();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static Properties getProperties() {
        return properties;
    }

    public static String getProperty(String key) throws UnsupportedEncodingException {
        try {
            init();
            String value = "";
            if (properties.getProperty(key) != null) {
                value = new String(properties.getProperty(key).getBytes("ISO-8859-1"), "GBK");
            }

            return value;
        } catch (Exception var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public static void setProperties(Properties properties) {
        properties = properties;
    }

}
