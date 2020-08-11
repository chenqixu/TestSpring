package com.cqx.testspringboot.session;

import com.cqx.testspringboot.activiti.dao.CommonBaseDao;
import com.cqx.testspringboot.session.model.TenantInfo;
import com.cqx.testspringboot.session.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SessionServer
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@CrossOrigin
@RequestMapping("${cmcc.web.servlet-path}/session")
public class SessionServer {
    private static final Logger logger = LoggerFactory.getLogger(SessionServer.class);

    private static Map<String, UserInfo> userInfoMap = new HashMap<>();
    private static UserInfo userInfo;

    @Resource(name = "com.cqx.testspringboot.activiti.dao.CommonBaseDao")
    protected CommonBaseDao dao;

//    @Resource(name = "com.cqx.testspringboot.session.dao.DemoDao")
//    protected DemoDao demoDao;
//
//    @Resource(name = "com.cqx.testspringboot.session.dao.BaseDemoDao")
//    protected BaseDemoDao baseDemoDao;

    static {
        // 默认用户
        userInfo = new UserInfo();
        userInfo.setUser_id("9991227");
        userInfo.setUser_name("测试人员");
        userInfo.setNick_name("9991227");
        userInfo.setOrg_id("9990001");
        userInfo.setOrg_name("省公司业务支撑系统部");
        userInfo.setHome_city("590");
        userInfo.setHome_city_desc("福建省");
        userInfo.setHome_county("591");
        userInfo.setHome_county_desc("福州市");
        userInfo.setMobile_phone("13511111111");
        userInfo.setMail_addr("13511111111@139.com");
        userInfo.setToken_code("token_code");

        // 资源运营者
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setUser_id("9990158");
        userInfo1.setUser_name("资源运营者");
        userInfo1.setNick_name("9990158");
        userInfo1.setOrg_id("9990001");
        userInfo1.setOrg_name("省公司业务支撑系统部");
        userInfo1.setHome_city("590");
        userInfo1.setHome_city_desc("福建省");
        userInfo1.setHome_county("591");
        userInfo1.setHome_county_desc("福州市");
        userInfo1.setMobile_phone("13511111111");
        userInfo1.setMail_addr("13511111111@139.com");
        userInfo1.setToken_code("token_code");

        // 资源配置者
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setUser_id("9990888");
        userInfo2.setUser_name("资源配置者");
        userInfo2.setNick_name("9990888");
        userInfo2.setOrg_id("9990001");
        userInfo2.setOrg_name("省公司业务支撑系统部");
        userInfo2.setHome_city("590");
        userInfo2.setHome_city_desc("福建省");
        userInfo2.setHome_county("591");
        userInfo2.setHome_county_desc("福州市");
        userInfo2.setMobile_phone("13511111111");
        userInfo2.setMail_addr("13511111111@139.com");
        userInfo2.setToken_code("token_code");

        // 俞章健 1001531
        UserInfo userInfo3 = new UserInfo();
        userInfo3.setUser_id("1001531");
        userInfo3.setUser_name("俞章健");
        userInfo3.setNick_name("1001531");
        userInfo3.setOrg_id("9990001");
        userInfo3.setOrg_name("省公司业务支撑系统部");
        userInfo3.setHome_city("590");
        userInfo3.setHome_city_desc("福建省");
        userInfo3.setHome_county("591");
        userInfo3.setHome_county_desc("福州市");
        userInfo3.setMobile_phone("13511111111");
        userInfo3.setMail_addr("13511111111@139.com");
        userInfo3.setToken_code("token_code");

        userInfoMap.put(userInfo.getUser_id(), userInfo);
        userInfoMap.put(userInfo1.getUser_id(), userInfo1);
        userInfoMap.put(userInfo2.getUser_id(), userInfo2);
        userInfoMap.put(userInfo3.getUser_id(), userInfo3);
    }

    @RequestMapping("/queryUserInfo")
    public UserInfo queryUserInfo() {
        logger.info("userInfo：{}", userInfo);
        return userInfo;
    }

    @RequestMapping(value = "/setUserInfo/{userId}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void setUserInfo(@PathVariable String userId) {
        UserInfo _userInfo = userInfoMap.get(userId);
        if (_userInfo != null) userInfo = _userInfo;
        logger.info("setUserInfo：{}，result：{}", userId, userInfo);
    }

    @RequestMapping(value = "/queryTenantsByTenantLevel/{tenant_level}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TenantInfo> queryTenantsByTenantLevel(@PathVariable String tenant_level) {
        List<TenantInfo> tenantInfos;
        String sql = "select tenant_id,tenant_name,tenant_ename,tenant_level,p_tenant_id from sm2_tenant where tenant_level=:tenant_level";
        Map<String, String> params = new HashMap<>();
        params.put("tenant_level", tenant_level);
        // Resource要在org.springframework.aop.framework.CglibAopProxy才会生成
//        logger.info("demoDao-0：{}，demoDao.jdbcTemplate：{}，demoDao.namedParameterJdbcTemplate：{}",
//                demoDao, demoDao.jdbcTemplate, demoDao.namedParameterJdbcTemplate);
//        demoDao.test();
//        logger.info("demoDao-1：{}，demoDao.jdbcTemplate：{}，demoDao.namedParameterJdbcTemplate：{}",
//                demoDao, demoDao.jdbcTemplate, demoDao.namedParameterJdbcTemplate);
//        logger.info("baseDemoDao-0：{}，baseDemoDao.jdbcTemplate：{}，baseDemoDao.namedParameterJdbcTemplate：{}",
//                baseDemoDao, baseDemoDao.getJdbcTemplate(), baseDemoDao.getNamedParameterJdbcTemplate());
        tenantInfos = dao.query(sql, params, TenantInfo.class);
        return tenantInfos;
    }

    @RequestMapping(value = "/queryTenantsByPTenantId/{p_tenant_id}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TenantInfo> queryTenantsByPTenantId(@PathVariable String p_tenant_id) {
        List<TenantInfo> tenantInfos;
        String sql = "select tenant_id,tenant_name,tenant_ename,tenant_level,p_tenant_id from sm2_tenant where p_tenant_id=:p_tenant_id";
        Map<String, String> params = new HashMap<>();
        params.put("p_tenant_id", p_tenant_id);
        tenantInfos = dao.query(sql, params, TenantInfo.class);
        return tenantInfos;
    }

    @RequestMapping(value = "/queryTimeOut/{sleep}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String queryTimeOut(@PathVariable String sleep) {
        int _sleep = Integer.valueOf(sleep);
        try {
            logger.info("准备休眠{}毫秒，开始休眠", sleep);
            Thread.sleep(_sleep);
            logger.info("准备休眠{}毫秒，休眠完成", sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "休眠了" + sleep;
    }
}
