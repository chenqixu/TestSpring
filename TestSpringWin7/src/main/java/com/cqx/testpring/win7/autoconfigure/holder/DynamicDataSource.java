package com.cqx.testpring.win7.autoconfigure.holder;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * DynamicDataSource
 *
 * @author chenqixu
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    public DynamicDataSource() {
    }

    protected Object determineCurrentLookupKey() {
        return "DataSourceType";
    }
}
