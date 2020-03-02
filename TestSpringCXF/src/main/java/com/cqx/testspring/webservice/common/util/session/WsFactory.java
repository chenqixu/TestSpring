package com.cqx.testspring.webservice.common.util.session;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * WsFactory
 *
 * @author chenqixu
 */
public class WsFactory {
    Class<?> serviceClass = null;
    String address = null;
    Object serviceInstance = null;
    Map<String, Object> outProps = null;
    boolean isHttps = false;
    boolean isCrypt = false;

    public WsFactory() {
    }

    public boolean isCrypt() {
        return this.isCrypt;
    }

    public void setCrypt(boolean isCrypt) {
        this.isCrypt = isCrypt;
    }

    public boolean isHttps() {
        return this.isHttps;
    }

    public void setHttps(boolean isHttps) {
        this.isHttps = isHttps;
    }

    public Map<String, Object> getOutProps() {
        return this.outProps;
    }

    public void setOutProps(Map<String, Object> outProps) {
        this.outProps = outProps;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getServiceClass() {
        return this.serviceClass;
    }

    public void setServiceClass(Class<?> serviceClass) throws Exception {
        this.serviceClass = serviceClass;
    }

    public Object create() throws Exception {
        WsInvocationHandler handler = new WsInvocationHandler(this.address, this.serviceClass);
        this.serviceInstance = Proxy.newProxyInstance(WsInvocationHandler.class.getClassLoader(), new Class[]{this.serviceClass}, handler);
        WsClientProxy.putClient(String.valueOf(this.serviceInstance.hashCode()), handler);
        return this.serviceInstance;
    }
}
