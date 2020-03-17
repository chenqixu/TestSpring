package com.cqx.testspring.webservice.common;

import com.cqx.testspring.webservice.common.model.UserInfo;
import com.cqx.testspring.webservice.common.util.session.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * BaseClient
 *
 * @author chenqixu
 */
public class BaseClient {
    private Logger logger = LoggerFactory.getLogger(BaseClient.class);

    public BaseClient() {
    }

    protected <T> T getService(Class<?> T) {
        try {
            return (T) ServiceFactory.createService(T);
        } catch (UnsupportedEncodingException var3) {
            this.logger.error("创建服务" + T.getName() + "失败！", var3);
            throw new RuntimeException(var3);
        } catch (ClassNotFoundException var4) {
            this.logger.error("无法找到类：" + T.getName() + "，请确认类或配置是否存在！", var4);
            throw new RuntimeException(var4);
        }
    }

    protected UserInfo getSessionUser() {
        BaseController bc = BaseController.getInstance();
        return bc.getSessionUser();
    }

    protected String getTokenCode() {
        return this.getSessionUser().getToken_code();
    }
}
