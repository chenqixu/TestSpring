package com.cqx.testpring.win7.interceptor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author chenqixu
 */
@Configuration
@ConfigurationProperties(prefix = "api.control")
@Data
public class ApiControlProperties {
    private boolean enabled = true;
    private List<String> disabledEndpoints = new ArrayList<>();
    private boolean maintenanceMode = false;
}
