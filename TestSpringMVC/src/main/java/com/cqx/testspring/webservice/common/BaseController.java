package com.cqx.testspring.webservice.common;

import com.cqx.testspring.webservice.common.model.NLSession;
import com.cqx.testspring.webservice.common.model.UserInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * BaseController
 *
 * @author chenqixu
 */
public class BaseController {
    private NLSession session = new NLSession(this.getRequest().getSession());
    public static final String SESSION_USER = "nledc_user_session";

    public BaseController() {
    }

    public static BaseController getInstance() {
        return new BaseController();
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    public NLSession getSession() {
        return this.session;
    }

    public UserInfo getSessionUser() {
        UserInfo userInfo = (UserInfo)this.getSession().getCacheObject("nledc_user_session", UserInfo.class);
        return userInfo;
    }
}
