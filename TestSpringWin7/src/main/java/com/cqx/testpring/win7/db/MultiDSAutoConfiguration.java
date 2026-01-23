package com.cqx.testpring.win7.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * MultiDSAutoConfiguration
 *
 * @author chenqixu
 */
// @Configuration 标记一个类为配置类，替代传统的 XML 配置文件
@Configuration
// @ConditionalOnClass 条件化配置：仅在类路径下存在指定的类时，才注册被注解的Bean或配置类
@ConditionalOnClass({DruidDataSource.class})
// @Import 导入配置类
//@Import({RegisterDS.class})
public class MultiDSAutoConfiguration {
    public MultiDSAutoConfiguration() {
    }
}
