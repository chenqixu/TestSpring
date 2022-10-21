package com.cqx.testspringboot.prometheus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Prometheus送数据服务
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@RequestMapping("PrometheusServer")
public class PrometheusServer {
    private static final Logger logger = LoggerFactory.getLogger(PrometheusServer.class);

    @PostConstruct
    private void init() {
    }
}
