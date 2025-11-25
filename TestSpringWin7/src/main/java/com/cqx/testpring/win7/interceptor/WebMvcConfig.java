package com.cqx.testpring.win7.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Web配置类
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Autowired
    private ApiControlInterceptor apiControlInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("==addInterceptors==");
        registry.addInterceptor(apiControlInterceptor)
                .addPathPatterns("/photo/**");
//                .excludePathPatterns("/api/health", "/api/status");
    }
}
