package com.cqx.testspring.webservice.common.util.session;

import com.cqx.testspring.webservice.common.model.RequestObject;
import com.cqx.testspring.webservice.common.model.User;
import com.cqx.testspring.webservice.common.util.other.SpringProperties;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsClientFactoryBean;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * ServiceFactory
 *
 * @author chenqixu
 */
public class ServiceFactory {
    private static Logger log = LoggerFactory.getLogger(ServiceFactory.class);
    private static Map<String, Object> factoryMap = Collections.synchronizedMap(new HashMap());
    private static Properties noCxfClientConf = new Properties();
    private static boolean noCxfWay = false;

    static {
        try {
            InputStream in = ServiceFactory.class.getResourceAsStream("/resources/config/client_without_cxf_service.properties");
            noCxfClientConf.load(in);
        } catch (Exception var1) {
            noCxfWay = true;
            var1.printStackTrace();
        }

    }

    public ServiceFactory() {
    }

    public static Object createService(Class<?> classPath) throws ClassNotFoundException, UnsupportedEncodingException {
        return createService((String) classPath.getName(), (User) null);
    }

    public static Object createService(String classPath) throws ClassNotFoundException, UnsupportedEncodingException {
        return createService((String) classPath, (User) null);
    }

    public static Object createService2(Class<?> classPath, String svcAddress) throws ClassNotFoundException, UnsupportedEncodingException {
        return createService2(classPath.getName(), svcAddress);
    }

    public static Object createService2(String classPath, String svcAddress) throws ClassNotFoundException, UnsupportedEncodingException {
        return createService2((String) classPath, (User) null, svcAddress);
    }

    public static Object createService(Class<?> classPath, User user) throws ClassNotFoundException, UnsupportedEncodingException {
        return createService((String) classPath.getName(), user, (RequestObject) null);
    }

    public static Object createService(String classPath, User user) throws ClassNotFoundException, UnsupportedEncodingException {
        return createService((String) classPath, user, (RequestObject) null);
    }

    public static Object createService(Class<?> classPath, User user, RequestObject requestObject) throws ClassNotFoundException, UnsupportedEncodingException {
        return createService(classPath.getName(), user, requestObject, (String) null);
    }

    public static Object createService(String classPath, User user, RequestObject requestObject) throws ClassNotFoundException, UnsupportedEncodingException {
        return createService(classPath, user, requestObject, (String) null);
    }

    public static Object createService2(Class<?> classPath, User user, String svcAddress) throws ClassNotFoundException, UnsupportedEncodingException {
        return createService(classPath.getName(), user, (RequestObject) null, svcAddress);
    }

    public static Object createService2(String classPath, User user, String svcAddress) throws ClassNotFoundException, UnsupportedEncodingException {
        return createService(classPath, user, (RequestObject) null, svcAddress);
    }

    public static Object createService2(Class<?> classPath, User user, RequestObject requestObject, String svcAddress) throws ClassNotFoundException, UnsupportedEncodingException {
        return createService(classPath.getName(), user, requestObject, svcAddress);
    }

    public static Object createService2(String classPath, User user, RequestObject requestObject, String svcAddress) throws ClassNotFoundException, UnsupportedEncodingException {
        return createService(classPath, user, requestObject, svcAddress);
    }

    public static synchronized Object createService(String classPath, User user, RequestObject requestObject, String svcAddress) throws ClassNotFoundException, UnsupportedEncodingException {
        if (factoryMap.get(classPath) != null) {
            return factoryMap.get(classPath);
        } else {
            if (!noCxfWay) {
                String serviceName = classPath.substring(classPath.lastIndexOf(46) + 1);
                String cxfWay = (String) noCxfClientConf.get(serviceName);
                if (StringUtil.isBlank(cxfWay)) {
                    return createCxfService(classPath, user, requestObject, svcAddress);
                }
            }

            log.info(classPath + "go http client!");
            Class<?> objectClass = ServiceFactory.class.getClassLoader().loadClass(classPath);
            WsFactory factory = new WsFactory();

            try {
                factory.setServiceClass(objectClass);
            } catch (Exception var10) {
                var10.printStackTrace();
            }

            if (Function.equalsNull(svcAddress)) {
                svcAddress = SpringProperties.getProperty(classPath);
            }

            if (Function.equalsNull(svcAddress)) {
                throw new ClassNotFoundException("服务" + classPath + "的配置没找到");
            } else {
                boolean isSecuritySvc = false;
                if (svcAddress.indexOf("#") >= 0) {
                    String[] svcAddressArr = svcAddress.split("#");
                    svcAddress = svcAddressArr[0];
                    if (svcAddressArr.length > 1 && "security".equals(svcAddressArr[1].trim())) {
                        isSecuritySvc = true;
                    }
                }

                if (isSecuritySvc) {
                    return user == null ? createSecurityService(classPath, svcAddress, requestObject) : createSecurityService(classPath, svcAddress, user, requestObject);
                } else {
                    factory.setAddress(svcAddress);
                    Object serviceObject = null;

                    try {
                        serviceObject = factory.create();
                        factoryMap.put(classPath, serviceObject);
                    } catch (Exception var9) {
                        var9.printStackTrace();
                    }

                    return serviceObject;
                }
            }
        }
    }

    private static Object createSecurityService(String classPath, String svcAddress) throws ClassNotFoundException, UnsupportedEncodingException {
        return createSecurityService(classPath, svcAddress, (RequestObject) null);
    }

    private static synchronized Object createSecurityService(String classPath, String svcAddress, RequestObject requestObject) throws ClassNotFoundException, UnsupportedEncodingException {
        User user = new User();
        return createSecurityService(classPath, svcAddress, user, requestObject);
    }

    private static Object createSecurityService(String classPath, String svcAddress, User user, RequestObject requestObject) throws ClassNotFoundException, UnsupportedEncodingException {
        if (factoryMap.get(classPath) != null) {
            return factoryMap.get(classPath);
        } else {
            Class<?> objectClass = ServiceFactory.class.getClassLoader().loadClass(classPath);
            ClientProxyFactoryBean factory = new ClientProxyFactoryBean(new JaxWsClientFactoryBean());
            factory.setServiceClass(objectClass);
            factory.setAddress(svcAddress);
            System.setProperty("javax.xml.soap.MessageFactory", "com.sun.xml.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl");
            Map<String, Object> inProps = new HashMap();
            inProps.put("action", "Signature Encrypt");
            inProps.put("signaturePropFile", SpringProperties.getProperty("SECURITY.DEC_PROP_FILE"));
            inProps.put("decryptionPropFile", SpringProperties.getProperty("SECURITY.SIG_PROP_FILE"));
            inProps.put("passwordCallbackRef", new KeystorePasswordHandler(SpringProperties.getProperty("SECURITY.CLIENTID"), SpringProperties.getProperty("SECURITY.CLIENTID_PASSWORD")));
            factory.getInInterceptors().add(new SAAJInInterceptor());
            factory.getInInterceptors().add(new WSS4JInInterceptor(inProps));
            Map<String, Object> outProps = new HashMap();
            outProps.put("action", "Signature Encrypt UsernameToken");
            outProps.put("user", SpringProperties.getProperty("SECURITY.CLIENTID"));
            outProps.put("passwordType", SpringProperties.getProperty("SECURITY.PASSWORD_TYPE"));
            outProps.put("signaturePropFile", SpringProperties.getProperty("SECURITY.SIG_PROP_FILE"));
            outProps.put("signatureKeyIdentifier", SpringProperties.getProperty("SECURITY.SIG_KEY_ID"));
            outProps.put("encryptionPropFile", SpringProperties.getProperty("SECURITY.DEC_PROP_FILE"));
            outProps.put("encryptionUser", SpringProperties.getProperty("SECURITY.ENCRYPTION_USER"));
            outProps.put("signatureParts", SpringProperties.getProperty("SECURITY.SIGNATURE_PARTS"));
            outProps.put("encryptionParts", SpringProperties.getProperty("SECURITY.ENCRYPTION_PARTS"));
            outProps.put("encryptionSymAlgorithm", SpringProperties.getProperty("SECURITY.ENC_SYM_ALGO"));
            outProps.put("passwordCallbackRef", new KeystorePasswordHandler(SpringProperties.getProperty("SECURITY.CLIENTID"), SpringProperties.getProperty("SECURITY.CLIENTID_PASSWORD")));
            factory.getOutInterceptors().add(new SAAJOutInterceptor());
            factory.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
            Object serviceObject = factory.create();
            factoryMap.put(classPath, serviceObject);
            return serviceObject;
        }
    }

    public static synchronized Object createCxfService(String classPath, User user, RequestObject requestObject, String svcAddress) throws ClassNotFoundException, UnsupportedEncodingException {
        if (factoryMap.get(classPath) != null) {
            return factoryMap.get(classPath);
        } else {
            Class<?> objectClass = ServiceFactory.class.getClassLoader().loadClass(classPath);
            ClientProxyFactoryBean factory = new ClientProxyFactoryBean(new JaxWsClientFactoryBean());
            factory.setServiceClass(objectClass);
            if (Function.equalsNull(svcAddress)) {
                svcAddress = SpringProperties.getProperty(classPath);
            }

            if (Function.equalsNull(svcAddress)) {
                throw new ClassNotFoundException("服务" + classPath + "的配置没找到");
            } else {
                boolean isSecuritySvc = false;
                if (svcAddress.indexOf("#") >= 0) {
                    String[] svcAddressArr = svcAddress.split("#");
                    svcAddress = svcAddressArr[0];
                    if (svcAddressArr.length > 1 && "security".equals(svcAddressArr[1].trim())) {
                        isSecuritySvc = true;
                    }
                }

                if (isSecuritySvc) {
                    return user == null ? createSecurityService(classPath, svcAddress, requestObject) : createSecurityService(classPath, svcAddress, user, requestObject);
                } else {
                    factory.setAddress(svcAddress);
                    Object serviceObject = factory.create();
                    factoryMap.put(classPath, serviceObject);
                    return serviceObject;
                }
            }
        }
    }

    public static void timeOutWrapper(Object service, long receiveTimeout, long connTimeout) {
        log.info("proxy class name:" + service.hashCode());
        WsInvocationHandler client = WsClientProxy.getClient(String.valueOf(service.hashCode()));
        if (client != null) {
            client.setReceiveTimeout((int) receiveTimeout);
        } else {
            Conduit conduit = ClientProxy.getClient(service).getConduit();
            HTTPConduit hc = (HTTPConduit) conduit;
            HTTPClientPolicy clientWs = new HTTPClientPolicy();
            clientWs.setReceiveTimeout(receiveTimeout);
            hc.setClient(clientWs);
        }
    }
}
