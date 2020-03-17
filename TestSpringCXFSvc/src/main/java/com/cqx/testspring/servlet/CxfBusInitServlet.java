package com.cqx.testspring.servlet;

import com.cqx.testspring.util.ClassUtil;
import com.cqx.testspring.webservice.common.manage.WebServiceMgt;
import com.cqx.testspring.webservice.common.manage.WebServiceMgtImpl;
import com.cqx.testspring.webservice.common.model.ServiceBean;
import com.cqx.testspring.webservice.common.util.other.ParameterConfig;
import com.cqx.testspring.webservice.common.util.session.SpringBeanFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * CxfBusInitServlet
 *
 * @author chenqixu
 */
public class CxfBusInitServlet extends CXFServlet {
    private static final Logger logger = LoggerFactory.getLogger(CxfBusInitServlet.class);
    private static Map<String, Bus> BusMap = new HashMap();
    private ClassUtil classUtil = new ClassUtil();
    private WebServiceMgt webServiceMgt = new WebServiceMgtImpl();


    public CxfBusInitServlet() {
    }

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        BusMap.put("servletBus", this.getBus());

        try {
            logger.info("---------services init start--------------------------------------");
            ServiceBean serviceBean = new ServiceBean();
            serviceBean.setServiceStatus("1");
            String project_name = ParameterConfig.getValue("WS_SVC_CONF_PROJECT_NAME");
            if (StringUtils.isNotEmpty(project_name)) {
                serviceBean.setProject_name(project_name);
            }

            List<ServiceBean> serviceBeanList = this.webServiceMgt.queryService(serviceBean);
            logger.info("services count：" + serviceBeanList.size());
            int succ = 0;
            int fail = 0;
            JaxWsServerFactoryBean serverFactoryBean = null;
            Iterator serviceBeanIterator = serviceBeanList.iterator();

            while (serviceBeanIterator.hasNext()) {
                ServiceBean service = (ServiceBean) serviceBeanIterator.next();

                try {
                    Class serviceClass = Class.forName(service.getServiceImplClass());
                    Object ins = serviceClass.newInstance();
                    if (ins == null) {
                        ++fail;
                    } else {
                        try {
                            SpringBeanFactory.getSpringConfigBeanFactory().registerBeanDefinition(serviceClass.getName(), BeanDefinitionBuilder.genericBeanDefinition(ins.getClass()).getRawBeanDefinition());
                            ins = SpringBeanFactory.getBean(serviceClass.getName());
                            logger.info("---------spring bean:" + serviceClass.getName() + "inject success-----");
                        } catch (Exception var13) {
                            ins = serviceClass.newInstance();
                        }

                        serverFactoryBean = new JaxWsServerFactoryBean();
                        serverFactoryBean.setServiceClass(ins.getClass());
                        serverFactoryBean.setAddress("/" + service.getServiceName());
                        serverFactoryBean.setServiceBean(ins);
                        serverFactoryBean.create();
                        ++succ;
                        logger.info("---------service:" + service.getServiceName() + "：" + serviceClass.getName() + "reigster success-----");
                    }
                } catch (Exception var14) {
                    ++fail;
                    var14.printStackTrace();
                }
            }

            logger.info("---------services init end,register success：" + succ + "，failed：" + fail + "--------------------------------------");
        } catch (Exception var15) {
            var15.printStackTrace();
            logger.error("services init exception:\n" + this.getExceptionInfo(var15));
        }

    }

    public static Bus getMyBus(String name) {
        return (Bus) BusMap.get(name);
    }

    private String getExceptionInfo(Exception e) {
        StackTraceElement[] astacktraceelement = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString());
        sb.append("\n");

        for (int i = 0; i < astacktraceelement.length; ++i) {
            sb.append("\tat ").append(astacktraceelement[i]).toString();
            sb.append("\n");
        }

        Throwable throwable = e.getCause();
        if (throwable != null) {
            this.getCauseInfo(sb, throwable);
        }

        return sb.toString();
    }

    private StringBuilder getCauseInfo(StringBuilder sb, Throwable throwable) {
        sb.append("Caused by: " + throwable.toString());
        sb.append("\n");
        StackTraceElement[] astacktraceelement = throwable.getStackTrace();

        for (int i = 0; i < astacktraceelement.length; ++i) {
            sb.append("\tat ").append(astacktraceelement[i]).toString();
            sb.append("\n");
        }

        return sb;
    }
}
