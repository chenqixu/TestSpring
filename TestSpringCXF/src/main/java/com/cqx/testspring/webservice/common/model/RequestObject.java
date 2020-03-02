package com.cqx.testspring.webservice.common.model;

import com.cqx.testspring.webservice.common.util.other.DateUtil;
import com.cqx.testspring.webservice.common.util.other.MakeSequence;
import com.cqx.testspring.webservice.common.util.xml.WSXmlParser;
import org.dom4j.DocumentException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.net.MalformedURLException;
import java.util.Collection;

/**
 * RequestObject
 *
 * @author chenqixu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestObject")
public class RequestObject extends WSBean {
    private static final long serialVersionUID = 1L;
    private HeaderReq headerReq = new HeaderReq();
    private BodyReq bodyReq;

    public RequestObject() {
    }

    public BodyReq getBodyReq() {
        return this.bodyReq;
    }

    public void setBodyReq(BodyReq bodyReq) {
        this.bodyReq = bodyReq;
    }

    public Object getRequestData(Class<?> objClass) throws InstantiationException, IllegalAccessException, MalformedURLException, DocumentException {
        String reqXml = this.getBodyReq().getReqData().getReqObjectString();
        reqXml = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" + reqXml;
        if (objClass.newInstance() instanceof String) {
            return reqXml;
        } else {
            return reqXml.indexOf("<?xml version=\"1.0\" encoding=\"GBK\"?>\n<content><row>") >= 0 ? WSXmlParser.parserXml2List(reqXml, "//content/row", objClass) : WSXmlParser.parserXml(reqXml, objClass);
        }
    }

    public void setRequestData(Object obj) throws Exception {
        this.setRequestData(obj, (String) null);
    }

    public void setUser(User user) {
        HeaderReq authenReq = this.getHeaderReq();
        if (authenReq == null) {
            authenReq = new HeaderReq();
        }

        authenReq.setUser(user);
        this.setHeaderReq(authenReq);
    }

    public void setSystem(System system) {
        HeaderReq authenReq = this.getHeaderReq();
        if (authenReq == null) {
            authenReq = new HeaderReq();
        }

        authenReq.setSystem(system);
        this.setHeaderReq(authenReq);
    }

    public void setReqSource(String reqSource) {
        HeaderReq authenReq = this.getHeaderReq();
        System system = null;
        if (authenReq == null) {
            authenReq = new HeaderReq();
        }

        system = authenReq.getSystem();
        if (system == null) {
            system = new System();
        }

        system.setReqSource(reqSource);
        system.setReqTime(DateUtil.getCurrDate("yyyyMMddHHmmssSSS"));
        authenReq.setSystem(system);
        this.setHeaderReq(authenReq);
    }

    public void setRequestData(Object obj, String dataType) throws Exception {
        HeaderReq authenReq = this.getHeaderReq();
        if (authenReq == null) {
            authenReq = new HeaderReq();
        }

        System system = new System();
        system.setReqTime(DateUtil.getCurrDate("yyyyMMddHHmmss"));
        system.setReqSeq(MakeSequence.Instance().getSequence());
        authenReq.setSystem(system);
        this.setHeaderReq(authenReq);
        if (this.getBodyReq() != null) {
            if (obj instanceof String) {
                this.bodyReq.getReqData().setReqObjectString((String) obj);
            } else if (obj instanceof Collection) {
                this.bodyReq.getReqData().setReqObjectString(WSXmlParser.buildXml(obj, "content", "row").asXML().substring("<?xml version=\"1.0\" encoding=\"GBK\"?>\n".length()));
            } else {
                this.bodyReq.getReqData().setReqObjectString(WSXmlParser.buildXml(obj, "content").asXML().substring("<?xml version=\"1.0\" encoding=\"GBK\"?>\n".length()));
            }
        } else {
            BodyReq bodyReq = new BodyReq();
            ReqData reqData = new ReqData();
            if (obj instanceof String) {
                reqData.setReqObjectString((String) obj);
            } else if (obj instanceof Collection) {
                reqData.setReqObjectString(WSXmlParser.buildXml(obj, "content", "row").asXML().substring("<?xml version=\"1.0\" encoding=\"GBK\"?>\n".length()));
            } else {
                reqData.setReqObjectString(WSXmlParser.buildXml(obj, "content").asXML().substring("<?xml version=\"1.0\" encoding=\"GBK\"?>\n".length()));
            }

            bodyReq.setReqData(reqData);
            this.setBodyReq(bodyReq);
        }

    }

    public void setStart(int start) {
        if (this.getBodyReq() != null) {
            this.getBodyReq().getReqData().setStart(String.valueOf(start));
        } else {
            BodyReq bodyReq = new BodyReq();
            ReqData reqData = new ReqData();
            reqData.setStart(String.valueOf(start));
            bodyReq.setReqData(reqData);
            this.setBodyReq(bodyReq);
        }

    }

    public int getStart() {
        return Integer.valueOf(this.getBodyReq().getReqData().getStart());
    }

    public void setPageCount(int pageCount) {
        if (this.getBodyReq() != null) {
            this.getBodyReq().getReqData().setPageCount(String.valueOf(pageCount));
        } else {
            BodyReq bodyReq = new BodyReq();
            ReqData reqData = new ReqData();
            reqData.setPageCount(String.valueOf(pageCount));
            bodyReq.setReqData(reqData);
            this.setBodyReq(bodyReq);
        }

    }

    public int getPageCount() {
        return Integer.valueOf(this.getBodyReq().getReqData().getPageCount());
    }

    public void setTotalCount(int totalCount) {
        if (this.getBodyReq() != null) {
            this.getBodyReq().getReqData().setTotalCount(String.valueOf(totalCount));
        } else {
            BodyReq bodyReq = new BodyReq();
            ReqData reqData = new ReqData();
            reqData.setTotalCount(String.valueOf(totalCount));
            bodyReq.setReqData(reqData);
            this.setBodyReq(bodyReq);
        }

    }

    public long getTotalCount() {
        return (long) Integer.valueOf(this.getBodyReq().getReqData().getTotalCount());
    }

    public String getSOAPMessage(String packageName, String methodName) throws Exception {
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
        message = message;
        message = message + "<?xml version='1.0' encoding='utf-8'?>\n";
        message = message + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n";
        message = message + "<soapenv:Body>\n";
        message = message + "<ns1:" + methodName + " xmlns:ns1=\"" + namespace + "/\">\n";
        String bodyStr = WSXmlParser.buildXml(this, "Message", true, "GBK").asXML();
        message = message + bodyStr.substring(37) + "\n";
        message = message + "</ns1:" + methodName + ">\n";
        message = message + "</soapenv:Body>\n";
        message = message + "</soapenv:Envelope>\n";
        return message;
    }

    public HeaderReq getHeaderReq() {
        return this.headerReq;
    }

    public void setHeaderReq(HeaderReq headerReq) {
        this.headerReq = headerReq;
    }
}
