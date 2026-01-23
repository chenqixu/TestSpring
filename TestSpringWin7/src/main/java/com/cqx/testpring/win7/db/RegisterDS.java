package com.cqx.testpring.win7.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.cqx.testpring.win7.autoconfigure.aspect.DynamicDataSourceAspect;
import com.cqx.testpring.win7.autoconfigure.holder.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册DB测试类
 *
 * @author chenqixu
 */
@Slf4j
//@Configuration
//@ConditionalOnClass({DruidDataSource.class})
//@AutoConfigureBefore({DataSourceAutoConfiguration.class})
public class RegisterDS implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private DataSource defaultDataSource;
    private Map<String, DataSource> mapOtherDataSources = new HashMap();

    public RegisterDS() {
    }

    @Override
    public void setEnvironment(Environment environment) {
        // 第一个是config触发的
        // 第二个是aware触发的
        log.info("===触发setEnvironment");
        this.defaultDataSource = this.buildDataSource();
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        log.info("===触发registerBeanDefinitions");
        Map<Object, Object> targetDataSources = new HashMap();
        targetDataSources.put("dataSource", this.defaultDataSource);
        targetDataSources.putAll(this.mapOtherDataSources);
        GenericBeanDefinition dataSourceBean = new GenericBeanDefinition();
        dataSourceBean.setBeanClass(DynamicDataSource.class);
        dataSourceBean.setSynthetic(true);
        MutablePropertyValues mpv = dataSourceBean.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", this.defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dataSource", dataSourceBean);
        GenericBeanDefinition dataSourceAspectBean = new GenericBeanDefinition();
        dataSourceAspectBean.setBeanClass(DynamicDataSourceAspect.class);
        dataSourceAspectBean.setSynthetic(true);
        registry.registerBeanDefinition("dynamicDataSourceAspect", dataSourceAspectBean);
        log.info("===Dynamic DataSource and Aspect Registry Success!");
    }

    private DataSource buildDataSource() {
        log.info("===触发buildDataSource");
        return new DruidDataSource();
    }
}
