package com.cqx.testspringboot.prometheus.dao;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.common.TextFormat;

import java.io.IOException;
import java.io.StringWriter;

/**
 * 计数器
 *
 * @author chenqixu
 */
public class CounterDao {
    private static final Counter requests = Counter.build()
            .name("requests_total")
            .help("Total requests.")
            .register();

    public void processRequest() throws IOException {
        requests.inc();
        StringWriter sw = new StringWriter();
        TextFormat.write004(sw, null);
    }
}
