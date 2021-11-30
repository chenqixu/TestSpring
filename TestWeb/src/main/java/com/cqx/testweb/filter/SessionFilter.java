
package com.cqx.testweb.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * SessionFilter
 *
 * @author chenqixu
 */
public class SessionFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SessionFilter.class);

    /**
     * 初始化
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 拦截过滤
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //==在这里可以对客户端请求进行检查==
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String name =  httpRequest.getRequestURI();
        String upperCaseName = name.toUpperCase();
//        if (upperCaseName.contains("INDEX")
//                || upperCaseName.contains("IMAGE")
//                || upperCaseName.contains("LOGOUT")
//                || upperCaseName.contains("TRANSFER")) {
            logger.info("不走权限控制，name：{}", name);
            chain.doFilter(request, response);
//        } else {
//            logger.info("需要权限控制，name：{}", name);
//            //权限控制
//            transfer(httpResponse, httpRequest);
//            //==沿过滤器链将请求传递到下一个过滤器==
//            chain.doFilter(request, response);
//        }
    }

    /**
     * 跳转
     *
     * @param httpResponse
     * @param httpRequest
     * @throws IOException
     */
    private void transfer(HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException {
        //判断是否为ajax请求，默认不是
        boolean isAjaxRequest = false;
        String xrw = httpRequest.getHeader("x-requested-with");
        if (xrw != null && xrw.length() > 0 && xrw.equals("XMLHttpRequest")) {
            isAjaxRequest = true;
        }
        logger.info("xrw：{}，isAjaxRequest：{}", xrw, isAjaxRequest);
        //请求转发到重登录页面
        if (isAjaxRequest) {
            //如果是ajax请求，则不是跳转页面，使用response返回结果
            httpResponse.setHeader("error_code", "999");
            httpResponse.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = httpResponse.getWriter();
            writer.write("{error_code:999,error_desc:\"会话超时\"}");
            writer.close();
            //上面已经和客户端交互完成，状态是正常的200，所以下面这个只是为了跳出，不走下面的过滤和传递了
            throw new RuntimeException("会话超时");
        } else {
            //刷新方式
//        httpResponse.sendRedirect(httpRequest.getContextPath() + "/goto/transfer.do");
            //F5重定向后端口问题
            try {
                //请求转发，前后页面共享一个request ; 这个是在服务端运行的，对浏览器来说是透明的
                httpRequest.getRequestDispatcher("/goto/transfer.do").forward(httpRequest, httpResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 销毁
     */
    @Override
    public void destroy() {

    }
}
