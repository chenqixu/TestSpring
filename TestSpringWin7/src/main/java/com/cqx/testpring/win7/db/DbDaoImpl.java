package com.cqx.testpring.win7.db;

import com.cqx.testpring.win7.model.SmUserBean;
import com.newland.bd.koala.invoker.JdbcInvoke;
import com.newland.bd.utils.db.springjdbc.dao.basedao.CommonBaseDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * DB测试dao
 *
 * @author chenqixu
 */
// @Repository 是 Spring 框架中一个专门用于数据访问层（DAO 层）​ 的注解。
// 它的核心作用是将一个类标识为与数据库交互的组件，并让 Spring 容器能够管理它
@Repository
// @Slf4j是 Project Lombok 提供的一个注解，用于在编译时自动生成 SLF4J 日志对象。
// 让你无需在每个类中手动编写创建日志对象的样板代码
@Slf4j
public class DbDaoImpl {

    @Resource
    protected JdbcInvoke jdbcInvoke;

    @Resource(name = "com.newland.bd.utils.db.springjdbc.dao.basedao.CommonBaseDao")
    protected CommonBaseDao commonBaseDao;

    @PostConstruct
    private void init() {
        // druid配置
        com.newland.bd.multidb.autoconfigure.RegisterMultiDataSource a;
        // druid连接池，内部类 CreateConnectionThread
        com.alibaba.druid.pool.DruidDataSource b;
    }

    public String query1() {
        List<SmUserBean> smUserBeanList;
        try {
            // 测试jdbcInvoke
            smUserBeanList = jdbcInvoke.query("select user_id,org_id,region_id,nick_name,user_name from sm_user", SmUserBean.class);
            log.info("[jdbcInvoke测试]query_cnt={}", smUserBeanList.size());
            // 测试commonBaseDao
//            smUserBeanList = commonBaseDao.query("select user_id,org_id,region_id,nick_name,user_name from sm_user", SmUserBean.class);
//            log.info("[jcommonBaseDao测试]query_cnt={}", smUserBeanList.size());
            return smUserBeanList.size() + "";
        } catch (Exception e) {
            log.error("xxxxxxxxxx=====");
//            log.error(e.getMessage());
        }
        return "-1";
    }
}
