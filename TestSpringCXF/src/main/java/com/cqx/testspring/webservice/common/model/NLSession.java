package com.cqx.testspring.webservice.common.model;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

/**
 * NLSession
 *
 * @author chenqixu
 */
public class NLSession {
    private HttpSession session;

    public NLSession(HttpSession session) {
        this.session = session;
    }

    public void cacheObject(String attrName, Object obj) {
        this.session.setAttribute(attrName, obj);
    }

    public <T> T getCacheObject(String attrName, Class<?> T) {
        T obj = (T) this.session.getAttribute(attrName);
        if (obj == null) {
            return null;
        } else if (obj instanceof Collection) {
            throw new RuntimeException("目标为列表对象，请使用getCacheList方法获取!");
        } else {
            return obj;
        }
    }

    public <T> List<T> getCacheList(String attrName, Class<?> T) {
        Object obj = this.session.getAttribute(attrName);
        if (obj == null) {
            return null;
        } else if (obj instanceof Collection) {
            return (List) obj;
        } else {
            throw new RuntimeException("目标非列表对象，请使用getCacheObject方法获取!");
        }
    }

    public boolean containObject(String attrName) {
        Object obj = this.session.getAttribute(attrName);
        return obj != null;
    }
}
