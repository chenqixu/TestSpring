package com.cqx.testpring.win7.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 拦截控制类
 *
 * @author chenqixu
 */
@Component
public class ApiControlInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ApiControlInterceptor.class);

    @Autowired
    private ApiControlProperties apiControlProperties;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        logger.warn("==preHandle==");

        if (!apiControlProperties.isEnabled()) {
            response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
            response.getWriter().write("API控制服务已禁用");
            return false;
        } else {
            String requestUri = request.getRequestURI();
            String contextPath = request.getContextPath();
            List<String> disabledEndpoints = apiControlProperties.getDisabledEndpoints();

            logger.info("API控制服务已启用，requestUri={}，contextPath={}，disabledEndpoints={}，"
                    , requestUri, contextPath, disabledEndpoints);

            // 处理上下文路径
            if (contextPath != null && requestUri.startsWith(contextPath)) {
                requestUri = requestUri.substring(contextPath.length());
            }

            // 检查是否在禁用列表中
            if (apiControlProperties.getDisabledEndpoints().contains(requestUri)) {
                logger.warn("接口已被禁用: {}", requestUri);
                response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
                response.getWriter().write("接口暂时不可用: " + requestUri);
                return false;
            }
        }

        return true;
    }
}
