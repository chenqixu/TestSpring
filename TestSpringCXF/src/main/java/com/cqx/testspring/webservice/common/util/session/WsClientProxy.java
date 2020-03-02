package com.cqx.testspring.webservice.common.util.session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * WsClientProxy
 *
 * @author chenqixu
 */
public class WsClientProxy {
    static ConcurrentHashMap clientMap = new ConcurrentHashMap();

    public WsClientProxy() {
    }

    public static void putClient(String classpath, WsInvocationHandler handler) {
        clientMap.put(classpath, handler);
    }

    public static WsInvocationHandler getClient(String classpath) {
        return (WsInvocationHandler) clientMap.get(classpath);
    }
}
