package com.cqx.testspring.servlet;

import com.cqx.testspring.webservice.common.manage.CommonMgtImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * InitActionServlet
 *
 * @author chenqixu
 */
public class InitActionServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(InitActionServlet.class);

    public InitActionServlet() {
    }

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        logger.info("==【工程初始化InitActionServlet】：启动");
        CommonMgtImpl.initWac(null);
        logger.info("==【工程初始化InitActionServlet】：完成");
    }
}
