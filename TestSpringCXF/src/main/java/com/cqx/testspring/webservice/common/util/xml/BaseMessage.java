package com.cqx.testspring.webservice.common.util.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl;

import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * BaseMessage
 *
 * @author chenqixu
 */
public abstract class BaseMessage {
    private static Log log = LogFactory.getLog(BaseMessage.class);
    protected Document document;
    protected Element root;

    public BaseMessage() {
    }

    protected int checkType(Class clazz) {
        String klass = clazz.getName();
        if (clazz.isPrimitive() && "boolean".equals(klass)) {
            return 200;
        } else if (clazz.isPrimitive()) {
            if ("int".equals(klass)) {
                return 201;
            } else if ("double".equals(klass)) {
                return 203;
            } else if ("float".equals(klass)) {
                return 204;
            } else {
                return "long".equals(klass) ? 202 : 0;
            }
        } else if (clazz.isInterface()) {
            if (klass.equals(Collection.class.getName())) {
                return 21;
            } else if (klass.equals(List.class.getName())) {
                return 11;
            } else if (klass.equals(Set.class.getName())) {
                return 14;
            } else {
                return klass.equals(Map.class.getName()) ? 15 : 98;
            }
        } else if (klass.equals(String.class.getName())) {
            return 5;
        } else if (klass.equals(Integer.class.getName())) {
            return 1;
        } else if (klass.equals(Long.class.getName())) {
            return 2;
        } else if (klass.equals(Double.class.getName())) {
            return 3;
        } else if (klass.equals(Float.class.getName())) {
            return 4;
        } else if (klass.equals(Boolean.class.getName())) {
            return 7;
        } else if (klass.equals(Vector.class.getName())) {
            return 13;
        } else if (klass.equals(ArrayList.class.getName())) {
            return 12;
        } else if (klass.equals(HashMap.class.getName())) {
            return 16;
        } else if (klass.equals(Collections.class.getName())) {
            return 22;
        } else if (klass.equals(Class.class.getName())) {
            return 100;
        } else if (klass.equals(Date.class.getName())) {
            return 6;
        } else {
            return !klass.equals(GenericArrayTypeImpl.class.getName()) && !klass.equals(StringArray.class.getName()) ? 99 : 305;
        }
    }

    protected void parseXml(String xml) throws MessageException {
        if (xml != null && xml.trim().length() != 0) {
            SAXReader reader = new SAXReader();

            try {
                this.document = reader.read(new ByteArrayInputStream(xml.trim().getBytes()));
                this.root = this.document.getRootElement();
            } catch (DocumentException var4) {
                log.error("!!" + var4);
            }

        } else {
            String error = "解析服务返回报文串异常：服务返回报文为空";
            throw new MessageException(error);
        }
    }

    protected Element getElemment(String xPath) {
        if (this.root == null) {
            return null;
        } else {
            Node node = this.root.selectSingleNode(xPath);
            if (node == null) {
                log.error(xPath + ":不存在");
                return null;
            } else {
                return (Element)node;
            }
        }
    }

    protected List getElemmentList(String xPath) {
        return this.root.selectNodes(xPath);
    }
}
