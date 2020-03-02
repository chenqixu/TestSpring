package com.cqx.testspring.webservice.common.util.session;

import com.cqx.testspring.webservice.common.model.UserInfo;
import com.cqx.testspring.webservice.common.util.other.SpringProperties;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * SessionFactory
 *
 * @author chenqixu
 */
public class SessionFactory {
    private static Logger logger = Logger.getLogger(SessionFactory.class);
    private static ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    public SessionFactory() {
    }

    public static UserInfo getUserInfoSession() {
        try {
            UserInfo userInfo = (UserInfo) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(SpringProperties.getProperty("SESSION_NAME_NLEDC_USER"));
            return userInfo;
        } catch (Exception var1) {
            return null;
        }
    }
}
