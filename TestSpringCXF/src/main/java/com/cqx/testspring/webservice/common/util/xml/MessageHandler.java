package com.cqx.testspring.webservice.common.util.xml;

import com.cqx.testspring.webservice.common.ReqDataIntf;
import com.cqx.testspring.webservice.common.RequestObjectIntf;
import com.cqx.testspring.webservice.common.RespDataIntf;
import com.cqx.testspring.webservice.common.ResponseObjectIntf;
import com.cqx.testspring.webservice.common.model.HeaderResp20;
import com.cqx.testspring.webservice.common.model.System20;
import com.cqx.testspring.webservice.common.model.User20;
import com.cqx.testspring.webservice.common.model.RequestObject;
import com.cqx.testspring.webservice.common.util.other.DateUtil;
import com.cqx.testspring.webservice.common.util.other.SpringProperties;
import com.cqx.testspring.webservice.common.util.session.StringUtil;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * MessageHandler
 *
 * @author chenqixu
 */
public class MessageHandler {
    private static Logger logger = Logger.getLogger(MessageHandler.class);
    private static MessageHandler messageHandler;

    public MessageHandler() {
    }

    public static MessageHandler newInstance() {
        if (messageHandler == null) {
            messageHandler = new MessageHandler();
        }

        return messageHandler;
    }

    public String getCurrentDate() {
        return DateUtil.getCurrDate("yyyyMMddHHmmssSSS");
    }

    public String getReqSeq(RequestObjectIntf requestObject) {
        try {
            return requestObject.getHeaderReq().getSystem().getReqSeq();
        } catch (Exception var3) {
            logger.error("ReqSeq获取失败", var3);
            return null;
        }
    }

    public String getPageId(RequestObjectIntf requestObject) {
        try {
            return requestObject.getHeaderReq().getAppInfo().getPageId();
        } catch (Exception var3) {
            logger.error("pageId获取失败", var3);
            return null;
        }
    }

    public String getPageDesc(RequestObjectIntf requestObject) {
        try {
            return requestObject.getHeaderReq().getAppInfo().getPageDesc();
        } catch (Exception var3) {
            logger.error("PageDesc获取失败", var3);
            return null;
        }
    }

    public String getActionId(RequestObjectIntf requestObject) {
        try {
            return requestObject.getHeaderReq().getAppInfo().getActionId();
        } catch (Exception var3) {
            logger.error("ActionId获取失败", var3);
            return null;
        }
    }

    public String getLoginSeq(RequestObjectIntf requestObject) {
        try {
            return requestObject.getHeaderReq().getAppInfo().getLogin_seq();
        } catch (Exception var3) {
            logger.error("Login_seq获取失败", var3);
            return null;
        }
    }

    public String getActionDesc(RequestObjectIntf requestObject) {
        try {
            return requestObject.getHeaderReq().getAppInfo().getActionDesc();
        } catch (Exception var3) {
            logger.error("ActionDesc获取失败", var3);
            return null;
        }
    }

    public String getOptType(RequestObjectIntf requestObject) {
        try {
            return requestObject.getHeaderReq().getAppInfo().getOptType();
        } catch (Exception var3) {
            logger.error("OptType获取失败", var3);
            return null;
        }
    }

    public String getPrivId(RequestObjectIntf requestObject) {
        try {
            return requestObject.getHeaderReq().getAppInfo().getPrivId();
        } catch (Exception var3) {
            logger.error("PrivId获取失败", var3);
            return null;
        }
    }

    public String getFromPriv(RequestObjectIntf requestObject) {
        try {
            return requestObject.getHeaderReq().getAppInfo().getFromPriv();
        } catch (Exception var3) {
            logger.error("FromPriv获取失败", var3);
            return null;
        }
    }

    public String getAopMethodId(RequestObjectIntf requestObject) {
        try {
            return requestObject.getHeaderReq().getAppInfo().getAopMethodId();
        } catch (Exception var3) {
            logger.error("AopMethodId获取失败", var3);
            return null;
        }
    }

    public String getWebIp(RequestObjectIntf requestObject) {
        try {
            return requestObject.getHeaderReq().getReqIp();
        } catch (Exception var3) {
            logger.error("ReqIp获取失败", var3);
            return null;
        }
    }

    public String getSvcIp(ResponseObjectIntf responseObject) {
        try {
            return responseObject.getHeaderResp().getRespIp();
        } catch (Exception var3) {
            logger.error("RespIp获取失败", var3);
            return null;
        }
    }

    public void setPrivId(RequestObjectIntf requestObject, String privId) {
        try {
            requestObject.getHeaderReq().getAppInfo().setPrivId(privId);
        } catch (Exception var4) {
            logger.error("PrivId设置失败", var4);
        }

    }

    public void setFromPriv(RequestObjectIntf requestObject, String fromPriv) {
        try {
            requestObject.getHeaderReq().getAppInfo().setFromPriv(fromPriv);
        } catch (Exception var4) {
            logger.error("fromPriv设置失败", var4);
        }

    }

    public void setOptType(RequestObjectIntf requestObject, String optType) {
        try {
            requestObject.getHeaderReq().getAppInfo().setOptType(optType);
        } catch (Exception var4) {
            logger.error("optType设置失败", var4);
        }

    }

    public void setPageId(RequestObjectIntf requestObject, String pageId) {
        try {
            requestObject.getHeaderReq().getAppInfo().setPageId(pageId);
        } catch (Exception var4) {
            logger.error("pageId设置失败", var4);
        }

    }

    public void setPageDesc(RequestObjectIntf requestObject, String pageDesc) {
        try {
            requestObject.getHeaderReq().getAppInfo().setPageDesc(pageDesc);
        } catch (Exception var4) {
            logger.error("pageDesc设置失败", var4);
        }

    }

    public void setActionId(RequestObjectIntf requestObject, String actionId) {
        try {
            requestObject.getHeaderReq().getAppInfo().setActionId(actionId);
        } catch (Exception var4) {
            logger.error("actionId设置失败", var4);
        }

    }

    public void setLoginSeq(RequestObjectIntf requestObject, String login_seq) {
        try {
            requestObject.getHeaderReq().getAppInfo().setLogin_seq(login_seq);
        } catch (Exception var4) {
            logger.error("login_seq设置失败", var4);
        }

    }

    public void setActionDesc(RequestObjectIntf requestObject, String actionDesc) {
        try {
            requestObject.getHeaderReq().getAppInfo().setActionDesc(actionDesc);
        } catch (Exception var4) {
            logger.error("actionDesc设置失败", var4);
        }

    }

    public void setAopMethodId(RequestObjectIntf requestObject, String aopMethodId) {
        try {
            requestObject.getHeaderReq().getAppInfo().setAopMethodId(aopMethodId);
        } catch (Exception var4) {
            logger.error("aopMethodId设置失败", var4);
        }

    }

    public void setClientIdPassword(RequestObjectIntf requestObject, String clientId, String passWord) {
        try {
            User20 user = null;
            user = requestObject.getHeaderReq().getUser();
            if (user == null) {
                user = new User20();
            }

            user.setClientId(clientId);
            user.setPassWord(passWord);
            requestObject.getHeaderReq().setUser(user);
        } catch (Exception var5) {
        }

    }

    public String getClientId(RequestObjectIntf requestObject) {
        try {
            User20 user = null;
            user = requestObject.getHeaderReq().getUser();
            return user == null ? null : user.getClientId();
        } catch (Exception var3) {
            return null;
        }
    }

    public String getPassword(RequestObjectIntf requestObject) {
        try {
            User20 user = null;
            user = requestObject.getHeaderReq().getUser();
            return user == null ? null : user.getPassWord();
        } catch (Exception var3) {
            return null;
        }
    }

    public String getTokencode(RequestObjectIntf requestObject) {
        try {
            User20 user = null;
            user = requestObject.getHeaderReq().getUser();
            return user == null ? null : user.getVerifyCode();
        } catch (Exception var3) {
            return null;
        }
    }

    public void setTokenCode(RequestObjectIntf requestObject, String tokenCode) {
        try {
            User20 user = null;
            user = requestObject.getHeaderReq().getUser();
            if (user == null) {
                user = new User20();
            }

            user.setVerifyCode(tokenCode);
            requestObject.getHeaderReq().setUser(user);
        } catch (Exception var4) {
        }

    }

    public void setSystem(RequestObjectIntf requestObject, System20 system) {
        try {
            requestObject.getHeaderReq().setSystem(system);
        } catch (Exception var4) {
        }

    }

    public String getSystemId(RequestObjectIntf requestObject) {
        try {
            System20 system20 = null;
            system20 = requestObject.getHeaderReq().getSystem();
            return system20 == null ? null : system20.getSystemId();
        } catch (Exception var3) {
            return null;
        }
    }

    public String getReqSource(RequestObjectIntf requestObject) {
        try {
            System20 system20 = null;
            system20 = requestObject.getHeaderReq().getSystem();
            return system20 == null ? null : system20.getReqSource();
        } catch (Exception var3) {
            return null;
        }
    }

    public void setReqSource(RequestObjectIntf requestObject, String reqSource) {
        try {
            System20 system = null;
            system = requestObject.getHeaderReq().getSystem();
            if (system == null) {
                system = new System20();
            }

            system.setReqSource(reqSource);
            system.setReqTime(DateUtil.getCurrDate("yyyyMMddHHmmssSSS"));
            requestObject.getHeaderReq().setSystem(system);
        } catch (Exception var4) {
        }

    }

    public void setSystemId(RequestObjectIntf requestObject, String systemId) {
        try {
            System20 system = null;
            system = requestObject.getHeaderReq().getSystem();
            if (system == null) {
                system = new System20();
            }

            system.setSystemId(systemId);
            requestObject.getHeaderReq().setSystem(system);
        } catch (Exception var4) {
        }

    }

    public String parseObjectToMessage(Object messageObject, String messageType) throws Exception {
        return WSXmlParser.buildXml(messageObject, "Message", true, "GBK").asXML();
    }

    public Object parseMessageToObject(String message, String messageType, Class<?> classs) throws Exception {
        return WSXmlParser.desParserXml(message, "//Message", classs);
    }

    public Object parseMessageToObject(String message, String messageType, Class<?> classs, String rootName) throws Exception {
        return WSXmlParser.desParserXml(message, "//" + rootName, classs);
    }

    public String getRequestSOAPMessage(RequestObjectIntf messageObject, String packageName, String methodName) throws Exception {
        return this.getRequestSOAPMessage(messageObject, packageName, methodName, "Message");
    }

    public String getRequestSOAPMessage(RequestObjectIntf messageObject, String packageName, String methodName, String rootElemName) throws Exception {
        return this.getRequestSOAPMessage((RequestObjectIntf) messageObject, packageName, methodName, rootElemName, (String) null);
    }

    public String getRequestSOAPMessage(RequestObjectIntf messageObject, String packageName, String methodName, String rootElemName, String nameSpace) throws Exception {
        String namespace = "http://";
        if (StringUtil.isBlank(nameSpace)) {
            String[] packageArr = packageName.replace(".", "/").split("/");

            for (int i = packageArr.length - 1; i >= 0; --i) {
                if (i == packageArr.length - 1) {
                    namespace = namespace + packageArr[i];
                } else {
                    namespace = namespace + "." + packageArr[i];
                }
            }
        } else {
            namespace = nameSpace;
        }

        String message = "";
        message = message;
        message = message + "<?xml version='1.0' encoding='utf-8'?>\n";
        message = message + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n";
        message = message + "<soapenv:Body>\n";
        message = message + "<ns1:" + methodName + " xmlns:ns1=\"" + namespace + "/\">\n";
        String bodyStr = WSXmlParser.buildXml(messageObject, rootElemName, true, "GBK").asXML();
        message = message + bodyStr.substring(37) + "\n";
        message = message + "</ns1:" + methodName + ">\n";
        message = message + "</soapenv:Body>\n";
        message = message + "</soapenv:Envelope>\n";
        return message;
    }

    public String getRequestSOAPMessage(RequestObject messageObject, String packageName, String methodName) throws Exception {
        return this.getRequestSOAPMessage(messageObject, packageName, methodName, "Message");
    }

    public String getRequestSOAPMessage(RequestObject messageObject, String packageName, String methodName, String rootElemName) throws Exception {
        return this.getRequestSOAPMessage((RequestObject) messageObject, packageName, methodName, rootElemName, (String) null);
    }

    public String getRequestSOAPMessage(RequestObject messageObject, String packageName, String methodName, String rootElemName, String nameSpace) throws Exception {
        String namespace = "http://";
        if (StringUtil.isBlank(nameSpace)) {
            String[] packageArr = packageName.replace(".", "/").split("/");

            for (int i = packageArr.length - 1; i >= 0; --i) {
                if (i == packageArr.length - 1) {
                    namespace = namespace + packageArr[i];
                } else {
                    namespace = namespace + "." + packageArr[i];
                }
            }
        } else {
            namespace = nameSpace;
        }

        String message = "";
        message = message;
        message = message + "<?xml version='1.0' encoding='utf-8'?>\n";
        message = message + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n";
        message = message + "<soapenv:Body>\n";
        message = message + "<ns1:" + methodName + " xmlns:ns1=\"" + namespace + "/\">\n";
        String bodyStr = WSXmlParser.buildXml(messageObject, rootElemName, true, "GBK").asXML();
        message = message + bodyStr.substring(37) + "\n";
        message = message + "</ns1:" + methodName + ">\n";
        message = message + "</soapenv:Body>\n";
        message = message + "</soapenv:Envelope>\n";
        return message;
    }

    public String getResponseSOAPMessage(ResponseObjectIntf messageObject, String packageName, String methodName) throws Exception {
        return this.getResponseSOAPMessage(messageObject, packageName, methodName, "Message");
    }

    public String getResponseSOAPMessage(ResponseObjectIntf messageObject, String packageName, String methodName, String rootElemName) throws Exception {
        String namespace = "http://";
        String[] packageArr = packageName.replace(".", "/").split("/");

        for (int i = packageArr.length - 1; i >= 0; --i) {
            if (i == packageArr.length - 1) {
                namespace = namespace + packageArr[i];
            } else {
                namespace = namespace + "." + packageArr[i];
            }
        }

        String message = "";
        message = message + "<?xml version='1.0' encoding='utf-8'?>\n";
        message = message + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n";
        message = message + "<soap:Body>\n";
        message = message + "<ns2:" + methodName + "Response xmlns:ns2=\"" + namespace + "/\">\n";
        String bodyStr = WSXmlParser.buildXml(messageObject, rootElemName, true, "GBK").asXML();
        message = message + bodyStr.substring(37) + "\n";
        message = message + "</ns2:" + methodName + "Response>\n";
        message = message + "</soap:Body>\n";
        message = message + "</soap:Envelope>\n";
        return message;
    }

    public String getRespResult(ResponseObjectIntf responseObject) {
        try {
            return responseObject != null ? responseObject.getHeaderResp().getRespResult() : "-1";
        } catch (Exception var3) {
            return "-1";
        }
    }

    public boolean IsResponseError(ResponseObjectIntf responseObject) {
        try {
            String errorCode = "0";
            if (responseObject != null) {
                errorCode = responseObject.getHeaderResp().getRespResult();
            }

            return !"0".equals(errorCode);
        } catch (Exception var3) {
            return true;
        }
    }

    public String getResponseErrorMsg(ResponseObjectIntf responseObject) {
        try {
            String respResult = responseObject.getHeaderResp().getRespResult();
            String respCode = responseObject.getHeaderResp().getRespCode();
            String respDesc = responseObject.getHeaderResp().getRespDesc();
            return respCode + "(" + respResult + "):" + respDesc;
        } catch (Exception var5) {
            return "-1";
        }
    }

    public void setHeaderResp(ResponseObjectIntf responseObject, String respResult, String respCode, String respDesc) {
        try {
            HeaderResp20 errorResp = new HeaderResp20();
            errorResp.setRespCode(respCode);
            if (respDesc != null && respDesc.indexOf("Exception") == -1) {
                errorResp.setRespDesc(respDesc);
            } else {
                errorResp.setRespDesc("后台服务处理异常");
            }

            errorResp.setRespResult(respResult);
            errorResp.setRespTime(this.getCurrentDate());
            responseObject.setHeaderResp(errorResp);
        } catch (Exception var6) {
        }

    }

    public void setHeaderResp(ResponseObjectIntf responseObject, Exception e) {
        HeaderResp20 errorResp = new HeaderResp20();

        try {
            Long.parseLong(e.getMessage());
            errorResp.setRespCode(e.getMessage());
            errorResp.setRespDesc(e.getMessage());
            errorResp.setRespResult(e.getMessage());
            errorResp.setRespTime(this.getCurrentDate());
        } catch (Exception var23) {
            ByteArrayOutputStream buf = null;
            PrintWriter pw = null;
            String expMessage = e.getMessage();

            try {
                buf = new ByteArrayOutputStream();
                pw = new PrintWriter(buf, true);
                e.printStackTrace(pw);
                expMessage = buf.toString();
            } catch (Exception var21) {
            } finally {
                try {
                    if (pw != null) {
                        pw.close();
                    }

                    if (buf != null) {
                        buf.close();
                    }
                } catch (Exception var19) {
                }

            }

            expMessage = expMessage.toLowerCase();
            String exceptionRusult = "999";
            String exceptionCode = "OtherErr";
            String IS_CATCH_SERVICE_ESCEPTION = "false";

            try {
                IS_CATCH_SERVICE_ESCEPTION = SpringProperties.getProperty("IS_CATCH_SERVICE_ESCEPTION");
                IS_CATCH_SERVICE_ESCEPTION = IS_CATCH_SERVICE_ESCEPTION.toLowerCase();
            } catch (UnsupportedEncodingException var20) {
            }

            if ("true".equals(IS_CATCH_SERVICE_ESCEPTION)) {
                if (expMessage.indexOf("sql") >= 0) {
                    exceptionRusult = "901";
                    exceptionCode = "Sqlcode";
                    expMessage = "数据库错误，错误代码由sqlcode指定";
                } else if (expMessage.indexOf("net") >= 0) {
                    exceptionRusult = "906";
                    exceptionCode = "NetErr";
                    expMessage = "网络连接错误";
                } else if (expMessage.indexOf("time") >= 0) {
                    exceptionRusult = "904";
                    exceptionCode = "TimeoutErr";
                    expMessage = "超时错误";
                } else {
                    exceptionRusult = "999";
                    exceptionCode = "OtherErr";
                    expMessage = "其它错误";
                }
            }

            errorResp.setRespDesc("服务处理异常：" + expMessage);
            errorResp.setRespResult(exceptionRusult);
            errorResp.setRespCode(exceptionCode);
            errorResp.setRespTime(this.getCurrentDate());
        }

        logger.error(errorResp.getRespDesc());
        errorResp.setRespDesc("后台服务处理异常");
        responseObject.setHeaderResp(errorResp);
    }

    public void setReqData(RequestObjectIntf requestObject, ReqDataIntf reqData) {
        try {
            requestObject.getBodyReq().setReqData(reqData);
        } catch (Exception var4) {
        }

    }

    public void setRespData(ResponseObjectIntf responseObject, RespDataIntf respData) {
        try {
            responseObject.getBodyResp().setRespData(respData);
        } catch (Exception var4) {
        }

    }

    public Object getReqData(RequestObjectIntf requestObject) {
        try {
            return requestObject.getBodyReq().getReqData();
        } catch (Exception var3) {
            return null;
        }
    }

    public Object getRespData(ResponseObjectIntf responseObject) {
        try {
            return responseObject.getBodyResp().getRespData();
        } catch (Exception var3) {
            return null;
        }
    }

    public String getTotalCount(ResponseObjectIntf responseObject) {
        try {
            return responseObject.getBodyResp().getTotalCount();
        } catch (Exception var3) {
            return null;
        }
    }

    public void setTotalCount(ResponseObjectIntf responseObject, String totalCount) {
        try {
            responseObject.getBodyResp().setTotalCount(totalCount);
        } catch (Exception var4) {
        }

    }

    public String getPageCount(RequestObjectIntf requestObject) {
        try {
            return requestObject.getBodyReq().getPageCount();
        } catch (Exception var3) {
            return null;
        }
    }

    public void setPageCount(RequestObjectIntf requestObject, String pageCount) {
        try {
            requestObject.getBodyReq().setPageCount(pageCount);
        } catch (Exception var4) {
        }

    }

    public String getStart(RequestObjectIntf requestObject) {
        try {
            return requestObject.getBodyReq().getStart();
        } catch (Exception var3) {
            return null;
        }
    }

    public void setStart(RequestObjectIntf requestObject, String start) {
        try {
            requestObject.getBodyReq().setStart(start);
        } catch (Exception var4) {
        }

    }

    public static void main(String[] args) throws Exception {
    }

    public static class MessageType {
        public static final String XML = "xml";
        public static final String TXT = "txt";
        public static final String EXCEL = "excel";

        public MessageType() {
        }
    }
}
