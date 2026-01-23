package com.cqx.testpring.win7;

import com.cqx.testpring.win7.interceptor.ApiControlProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = {"com.cqx.testpring.win7", "com.newland.bd"})
@EnableConfigurationProperties({ApiControlProperties.class})
public class Win7Application {

    public static void main(String[] args) {
        SpringApplication.run(Win7Application.class, args);
    }
}
