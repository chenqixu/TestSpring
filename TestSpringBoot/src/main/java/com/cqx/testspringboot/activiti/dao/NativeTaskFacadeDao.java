package com.cqx.testspringboot.activiti.dao;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 原生sql审核任务查询装饰类
 *
 * @author chenqixu
 */
@Repository("nativeTaskFacadeDao")
public class NativeTaskFacadeDao extends FacadeDao<INativeTaskDao> {

    /**
     * 对oracle, mysql的具体业务dao进行依赖注入.<br>
     * 每种数据库类型都需要进行注入. 注入的私有变量作为hashmap的value.<br>
     */
    @Resource(name = "nativeTaskOracleDao")
    private NativeTaskOracleDao nativeTaskOracleDao;

    @Resource(name = "nativeTaskMysqlDao")
    private NativeTaskMysqlDao nativeTaskMysqlDao;

    private ThreadLocal<Map<DaoInfo.DB_TYPE, INativeTaskDao>> tlMap = new ThreadLocal<Map<DaoInfo.DB_TYPE, INativeTaskDao>>() {
        @Override
        protected Map<DaoInfo.DB_TYPE, INativeTaskDao> initialValue() {
            Map<DaoInfo.DB_TYPE, INativeTaskDao> map = new HashMap<DaoInfo.DB_TYPE, INativeTaskDao>();
            /**
             * 把每个私有变量都加入到hashmap中<br>
             * hashmap的key现暂时可接受的包括oracle, mysql, sqlserver, db2, td
             * 你需要加几种数据库, 你就put几个值.
             */
            map.put(DaoInfo.DB_TYPE.ORACLE, nativeTaskOracleDao);
            map.put(DaoInfo.DB_TYPE.MYSQL, nativeTaskMysqlDao);

            return map;
        }
    };

    public INativeTaskDao getBaseDao() {
        Map<DaoInfo.DB_TYPE, INativeTaskDao> mapDbType = tlMap.get();
        return this.getBusDao(mapDbType);
    }
}
