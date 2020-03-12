package com.spring.test.servlet;

import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 让Servlet支持spring注入方式使用Spring Bean
 */
public abstract class SpringSupportServlet extends HttpServlet {

    private static final long serialVersionUID = 3572281877655980104L;

    /**
     * 初始化
     *
     * @throws ServletException
     */
    public void init() throws ServletException {
        super.init();
        WebApplicationContextUtils
                .getWebApplicationContext(getServletContext())
                .getAutowireCapableBeanFactory().autowireBean(this);
    }

    /**
     * get方式请求
     *
     * @param response
     * @param request
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    /**
     * post方式请求
     *
     * @param response
     * @param request
     * @throws IOException
     */
    public abstract void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
