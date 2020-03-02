package com.cqx.testspring.webservice.common.util.session;

import com.cqx.testspring.webservice.common.RequestObjectIntf;
import com.cqx.testspring.webservice.common.model.RequestObject;
import com.cqx.testspring.webservice.common.util.xml.MessageHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * WsInvocationHandler
 *
 * @author chenqixu
 */
public class WsInvocationHandler implements InvocationHandler {
    private static Logger log = Logger.getLogger(WsInvocationHandler.class);
    int receiveTimeout = 0;
    int connTimeout = 0;
    String rootElmentName = "";
    int isNewProtocol = -1;
    String address = null;
    Class<?> serviceClass = null;
    Object serviceInstance = null;
    boolean isHttps = false;
    Map<String, Object> outProps = null;
    boolean isCrypt = false;

    public boolean isCrypt() {
        return this.isCrypt;
    }

    public void setCrypt(boolean isCrypt) {
        this.isCrypt = isCrypt;
    }

    public WsInvocationHandler(String _address, Class<?> _serviceClass) {
        this.address = _address;
        this.serviceClass = _serviceClass;
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

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (methodName.equals("hashCode")) {
            return this.serviceClass.hashCode();
        } else if (methodName.equals("toString")) {
            return this.serviceClass.toString();
        } else {
            String packageName = this.serviceClass.getPackage().getName();
            if (args != null && args.length != 0) {
                this.rootElmentName = this.getRootElementName(this.serviceClass, methodName, args[0]);
                if (this.isNewProtocol(args[0])) {
                    this.isNewProtocol = 1;
                } else {
                    this.isNewProtocol = 0;
                }

                String nameSpace = this.getNameSpace(this.serviceClass);
                String soapReqMsg = null;
                String soapResMsg;
                if (this.isNewProtocol == 0) {
                    soapReqMsg = MessageHandler.newInstance().getRequestSOAPMessage((RequestObject) args[0], packageName, methodName, this.rootElmentName, nameSpace);
                    log.info("old soapReqMsg:");
                    log.info(soapReqMsg);
                    soapResMsg = this.send(soapReqMsg, 0);
                    log.info("old soapResMsg:");
                    log.info(soapResMsg);
                    return MessageHandler.newInstance().parseMessageToObject(soapResMsg, "xml", method.getReturnType(), this.rootElmentName);
                } else {
                    soapReqMsg = MessageHandler.newInstance().getRequestSOAPMessage((RequestObjectIntf) args[0], packageName, methodName, this.rootElmentName, nameSpace);
                    log.info("new soapReqMsg:");
                    log.info(soapReqMsg);
                    soapResMsg = this.send(soapReqMsg, 0);
                    log.info("new soapResMsg:");
                    log.info(soapResMsg);
                    return MessageHandler.newInstance().parseMessageToObject(soapResMsg, "xml", method.getReturnType(), this.rootElmentName);
                }
            } else {
                return "";
            }
        }
    }

    private String send(String soapReqMsg, int tryTime) throws Throwable {
        ++tryTime;
        if (tryTime > 2) {
            throw new Exception("重试2次仍然无法获取数据，请检查服务!");
        } else if (!this.isCrypt) {
            try {
                PostMethod postMethod = new PostMethod(this.address);
                byte[] b = soapReqMsg.getBytes("utf-8");
                InputStream is = new ByteArrayInputStream(b, 0, b.length);
                RequestEntity re = new InputStreamRequestEntity(is, (long) b.length, "application/soap+xml; charset=utf-8");
                postMethod.setRequestEntity(re);
                HttpClient httpClient = new HttpClient();
                if (this.connTimeout <= 0) {
                    this.connTimeout = 60000;
                }

                if (this.receiveTimeout <= 0) {
                    this.receiveTimeout = 60000;
                }

                httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(this.connTimeout);
                httpClient.getHttpConnectionManager().getParams().setSoTimeout(this.receiveTimeout);
                int status = httpClient.executeMethod(postMethod);
                if (status == 200) {
                    is = postMethod.getResponseBodyAsStream();
                    String soapResponseData = IOUtils.toString(is, "utf-8");
                    return soapResponseData;
                }
            } catch (Exception var10) {
                log.error("address:" + this.address);
                log.error("soapReqMsg:" + soapReqMsg);
                log.error(var10);
                var10.printStackTrace();
            }

            return this.send(soapReqMsg, tryTime);
        } else {
            ++tryTime;
            return "";
        }
    }

    private boolean isNewProtocol(Object obj) {
        Class[] ary = obj.getClass().getInterfaces();
        Class[] var6 = ary;
        int var5 = ary.length;

        for (int var4 = 0; var4 < var5; ++var4) {
            Class<?> cl = var6[var4];
            log.info(" interface name:" + cl.getName() + "  RequestObjectIntf.class.getName():" + RequestObjectIntf.class.getName());
            if (cl.getName().equals(RequestObjectIntf.class.getName())) {
                return true;
            }
        }

        return false;
    }

    private String getRootElementName(Class<?> obj, String methodName, Object param) {
        try {
            log.info("obj:" + obj.getName() + " methodName:" + methodName + " param:" + param.getClass().getName());
            Method m1 = obj.getMethod(methodName, param.getClass());
            Annotation[] ary = m1.getAnnotations();
            Annotation[] var9 = ary;
            int var8 = ary.length;

            for (int var7 = 0; var7 < var8; ++var7) {
                Annotation a = var9[var7];
                String all = a.toString();
                int beg = all.indexOf("(");
                int end = all.indexOf(")");
                String sub = all.substring(beg + 1, end);
                log.info("annotation:" + sub);
                if (sub.indexOf("name=message") > -1) {
                    return "message";
                }

                if (sub.indexOf("name=Message") > -1) {
                    return "Message";
                }

                log.error("please view the request model bean,and set annotation message or Message!");
            }
        } catch (Exception var14) {
            log.error("getRootElementName error:");
            var14.printStackTrace();
        }

        return "Message";
    }

    private String getNameSpace(Class<?> obj) {
        try {
            Annotation[] ary = obj.getAnnotations();
            Annotation[] var6 = ary;
            int var5 = ary.length;

            for (int var4 = 0; var4 < var5; ++var4) {
                Annotation a = var6[var4];
                String all = a.toString();
                int beg = all.indexOf("targetNamespace=");
                if (beg > -1) {
                    all = all.substring(beg);
                    int end = all.indexOf(",");
                    beg = all.indexOf("=");
                    if (beg > -1 && end > -1) {
                        String sub = all.substring(beg + 1, end - 1);
                        log.info("getNameSpace:" + sub);
                        return sub;
                    }
                }
            }
        } catch (Exception var11) {
        }

        return "";
    }

    public long getConnTimeout() {
        return (long) this.connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public long getReceiveTimeout() {
        return (long) this.receiveTimeout;
    }

    public void setReceiveTimeout(int receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }
}
