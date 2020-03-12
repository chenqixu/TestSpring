package com.spring.test.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * SessionServlet
 *
 * @author chenqixu
 */
public class SessionServlet extends SpringSupportServlet {

    private static final Logger logger = LoggerFactory.getLogger(SessionServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.addHeader("P3P", "CP=CAO PSA OUR");//iframe框架下session丢失的问题
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        HttpSession session = request.getSession();
        //将数据存储到session中
        session.setAttribute("data", "孤傲苍狼");
        //获取session的Id
        String sessionId = session.getId();
        //判断session是不是新创建的
        if (session.isNew()) {
            logger.info("session创建成功，session的id是：" + sessionId);
            response.getWriter().print("session创建成功，session的id是：" + sessionId);
        } else {
            logger.info("服务器已经存在该session了，session的id是：" + sessionId);
            response.getWriter().print("服务器已经存在该session了，session的id是：" + sessionId);
        }
//        HttpSession session = request.getSession();
//        session.setAttribute("user_name", "admin");
//        session.setAttribute("user_id", "123456");
//        session.setMaxInactiveInterval(60 * 20); //单位秒
//        Object user_name = httpSession.getAttribute("user_name");
//        logger.info("getAttribute，user_name：{}", user_name == null ? "" : user_name);
//        if (user_name == null || user_name.toString().length() == 0) {
//            httpSession.setAttribute("user_name", "admin");
//            user_name = httpSession.getAttribute("user_name");
//            logger.info("setAttribute，getAttribute，user_name：{}", (String) user_name);
//        }
    }

}
