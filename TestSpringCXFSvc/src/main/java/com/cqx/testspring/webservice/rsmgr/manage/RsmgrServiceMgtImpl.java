package com.cqx.testspring.webservice.rsmgr.manage;

import com.cqx.common.utils.system.TimeCostUtil;
import com.cqx.testspring.webservice.rsmgr.bean.RsmgrRespObject;
import com.cqx.testspring.webservice.rsmgr.utils.IntfHuaWeiTenantProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 资源服务化的Mgt实现类
 *
 * @author chenqixu
 */
@Repository("com.cqx.testspring.webservice.rsmgr.manage.RsmgrServiceMgtImpl")
public class RsmgrServiceMgtImpl {
    private static final Logger logger = LoggerFactory.getLogger(RsmgrServiceMgtImpl.class);
    @Autowired
    private IntfHuaWeiTenantProcess intfHuaWeiTenantProcess;

    /**
     * 接口调用
     */
    public RsmgrRespObject interfaceCall() {
        logger.info("【interfaceCall】接口调用");
        TimeCostUtil tc = new TimeCostUtil();
        tc.start();
        intfHuaWeiTenantProcess.createTenant();
        logger.info("【interfaceCall】接口调用完成，耗时：{}", tc.stopAndGet());
        return new RsmgrRespObject();
    }
}
