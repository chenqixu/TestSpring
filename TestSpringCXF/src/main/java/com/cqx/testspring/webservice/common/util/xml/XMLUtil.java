package com.cqx.testspring.webservice.common.util.xml;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.*;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.SAXReader;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.*;

/**
 * XMLUtil
 *
 * @author chenqixu
 */
public class XMLUtil {

    public XMLUtil() {
    }

    public static Element createElement(String name) {
        Element el = DocumentHelper.createElement(name);
        return el;
    }

    public static String getNodeValue(Document doc, String parentName, String name) {
        if (doc == null) {
            return null;
        } else {
            Node node = doc.selectSingleNode("//" + parentName + "/" + name);
            return node == null ? null : node.getText();
        }
    }

    public static String getNodeValue(Document doc, String parentName, String name, String child) {
        if (doc == null) {
            return null;
        } else {
            Node node = doc.selectSingleNode("//" + parentName + "/" + name + "/" + child);
            return node == null ? null : node.getText();
        }
    }

    public static Map<String, String> getNodeMap(Document doc, String name) {
        Map<String, String> nodeMap = new HashMap();
        Element rootEl = doc.getRootElement();
        Element element = rootEl.element(name);
        List elList = element.elements();
        Iterator iter = elList.iterator();

        while (iter.hasNext()) {
            Element el = (Element) iter.next();
            nodeMap.put(el.getName(), el.getTextTrim());
        }

        return nodeMap;
    }

    public static Map<String, String> getNodeMap(Document doc, String parentName, String name) {
        Map<String, String> nodeMap = new HashMap();
        Element rootEl = doc.getRootElement();
        Element parentEl = rootEl.element(parentName);
        List elList = parentEl.elements(name);
        Iterator iter = elList.iterator();

        while (iter.hasNext()) {
            Element el = (Element) iter.next();
            nodeMap.put(el.getName(), el.getTextTrim());
        }

        return nodeMap;
    }

    public static String getNodeValue(Document doc, String name) {
        if (doc == null) {
            return null;
        } else {
            Node node = doc.selectSingleNode("//" + name);
            return node == null ? null : node.getText();
        }
    }

    public static String getXpathNodeValue(Document doc, String name) {
        if (doc == null) {
            return null;
        } else {
            Node node = doc.selectSingleNode(name);
            return node == null ? null : node.getText();
        }
    }

    public static Node getNode(Document doc, String parentName, String name) {
        if (doc == null) {
            return null;
        } else {
            Node node = doc.selectSingleNode("//" + parentName + "/" + name);
            return node == null ? null : node;
        }
    }

    public static Node getNode(Document doc, String name) {
        if (doc == null) {
            return null;
        } else {
            Node node = doc.selectSingleNode("//" + name);
            return node == null ? null : node;
        }
    }

    public static List getNodeList(Document doc, String name) {
        if (doc == null) {
            return null;
        } else {
            new ArrayList();
            List nodeList = doc.selectNodes("//" + name);
            return nodeList == null ? null : nodeList;
        }
    }

    public static void setNodeValue(Document doc, String parentName, String name, String value) {
        if (doc != null) {
            Node node = doc.selectSingleNode("//" + parentName + "/" + name);
            if (node != null) {
                if (value != null) {
                    node.setText(value);
                }

            }
        }
    }

    public static void setNodeValue(Document doc, String name, String value) {
        if (doc != null) {
            Node node = doc.selectSingleNode("//" + name);
            if (node != null) {
                node.setText(value);
            }
        }
    }

    public static Element addElement(Element el, String fieldName, String fieldValue) {
        if (fieldValue == null) {
            fieldValue = "";
        }

        Element child = el.addElement(fieldName);
        child.setText(fieldValue);
        return child;
    }

    public static String getNodeAttribute(Node el, String attrName) {
        if (el == null) {
            return "";
        } else {
            try {
                String attrValue = ((Element) el).attributeValue(attrName);
                if (attrValue == null) {
                    attrValue = "";
                }

                return attrValue;
            } catch (Exception var5) {
                String s1 = "";
                return s1;
            }
        }
    }

    public static String getNodeValue(Node node) {
        String s1 = node.getText();
        return s1;
    }

    public static String getNodeValue(Node el, String tagName) {
        String ret = null;
        if (el == null) {
            return "";
        } else {
            try {
                Node node = el.selectSingleNode("//" + tagName);
                ret = node.getText();
                return ret;
            } catch (Exception var4) {
                return "";
            }
        }
    }

    public static Node getNodeElement(Node el, String tagName) {
        if (el == null) {
            return null;
        } else {
            try {
                Node node = el.selectSingleNode(tagName);
                return node;
            } catch (Exception var4) {
                Node node1 = null;
                return (Node) node1;
            }
        }
    }

    public static List getNodeElements(Node el, String tagName) {
        if (el == null) {
            return null;
        } else {
            List nodes = el.selectNodes(tagName);
            return nodes;
        }
    }

    public static String getXMLString(Document dom, String unicode) {
        String ret = dom.asXML();
        return ret.toString();
    }

    public static String parseUseXSL(String strXML, String xslFile, String xmlEncoding, String outEncoding) throws Exception {
        Document document = DocumentHelper.parseText(strXML);
        document.setXMLEncoding("GBK");
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(xslFile));
        DocumentSource source = new DocumentSource(document);
        DocumentResult result = new DocumentResult();
        transformer.transform(source, result);
        Document transformedDoc = result.getDocument();
        return transformedDoc.asXML();
    }

    public static void parseUseXSL(String strXML, String xslFile, String xmlEncoding, String outEncoding, OutputStream out) throws TransformerException, TransformerConfigurationException, UnsupportedEncodingException {
        ByteArrayInputStream iStream = xmlEncoding != null ? new ByteArrayInputStream(strXML.getBytes(xmlEncoding)) : new ByteArrayInputStream(strXML.getBytes());

        try {
            StreamSource source = new StreamSource(iStream);
            TransformerFactory transFactory = TransformerFactory.newInstance();
            StreamSource stylesheet = new StreamSource(xslFile);
            Transformer transformer = transFactory.newTransformer(stylesheet);
            if (outEncoding != null) {
                transformer.setOutputProperty("encoding", outEncoding);
            }

            StreamResult result = new StreamResult(out);
            transformer.transform(source, result);
        } finally {
            if (iStream != null) {
                try {
                    iStream.close();
                } catch (Exception var16) {
                }
            }

        }

    }

    public static String getNodeValueByName(Element element, String name) {
        Node node = element.selectSingleNode(name);
        String str = "";
        if (node != null) {
            str = node.getText();
        }

        return str;
    }

    public static String getNodeValueByName(Document document, String name) {
        Element root = document.getRootElement();
        String ret = null;
        Iterator i = root.elementIterator(name);
        if (i.hasNext()) {
            Element el = (Element) i.next();
            ret = el.getTextTrim();
        }

        return ret;
    }

    public static void addTextNode(Document document, String parentName, String nodeValue) {
        Element root = document.getRootElement();
        Element el = root.addElement(parentName);
        el.setText(nodeValue);
    }

    public static void replaceTextNode(Document document, String nodeName, String nodeValue) {
        try {
            if (nodeValue != null) {
                Node node = document.selectSingleNode("//" + nodeName);
                node.setText(nodeValue);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void addEmptyNode(Document document, String parentNodeName, String nodeName) {
        addNode(document, parentNodeName, nodeName, (String) null);
    }

    public static void addNode(Document document, String parentNodeName, String nodeName, String nodeValue) {
        Element root = document.getRootElement();
        Iterator i = root.elementIterator(parentNodeName);

        while (i.hasNext()) {
            Element el = (Element) i.next();
            Element child = el.addElement(nodeName);
            if (nodeValue != null) {
                child.setText(nodeValue);
            }
        }

    }

    public static String docToXMLString(Document doc) {
        String ret = doc.asXML();
        return ret;
    }

    public static String transDOM2String(Node node) {
        String str = node.asXML();
        return str;
    }

    public static Document buildDocument(InputStream is) throws MalformedURLException, DocumentException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(is);
        return doc;
    }

    public static Document buildDocument(String docStr) throws MalformedURLException, DocumentException {
        Document doc = DocumentHelper.parseText(docStr);
        doc.setXMLEncoding("GBK");
        return doc;
    }

    public static Document createEmptyDocument() {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("GBK");
        return document;
    }

    public static Map<String, String> parseXML(Document xmlDoc, String svcContNode) {
        Map<String, String> propertyMap = new HashMap();
        Element rootEl = xmlDoc.getRootElement();
        Element svcEl = rootEl.element(svcContNode);
        List elList;
        Iterator iter;
        Element el;
        if (svcEl != null) {
            elList = svcEl.elements();
            iter = elList.iterator();

            while (true) {
                while (iter.hasNext()) {
                    el = (Element) iter.next();
                    if (el.getTextTrim() != null && !el.getTextTrim().equals("")) {
                        propertyMap.put(el.getName(), el.getTextTrim());
                    } else {
                        propertyMap.put(el.getName(), "-999999");
                    }
                }

                return propertyMap;
            }
        } else {
            elList = rootEl.elements();
            iter = elList.iterator();

            while (true) {
                while (iter.hasNext()) {
                    el = (Element) iter.next();
                    if (el.getTextTrim() != null && !el.getTextTrim().equals("")) {
                        propertyMap.put(el.getName(), el.getTextTrim());
                    } else {
                        propertyMap.put(el.getName(), "-999999");
                    }
                }

                return propertyMap;
            }
        }
    }

    public static Object setProperty(Document doc, Object obj, String svcContNode) throws IllegalAccessException, InvocationTargetException {
        Map<String, String> valueList = new HashMap();
        if (doc != null) {
            valueList = parseXML(doc, svcContNode);
        } else {
            System.out.println("------您没有把与您的Bean相对应的dom4j Document对象传进来！-------");
            System.out.println("------并且您的Bean对象的第一个属性以外的属性必须与您的文档对象要解析的节点列表一一对应！-------");
            System.out.println("------通常Bean对象要序列化,第一个属性是serialVersionUID-------");
        }

        Field[] allFields = obj.getClass().getDeclaredFields();

        for (int i = 0; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            if (!fieldName.equalsIgnoreCase("serialVersionUID")) {
                Object propertyValue = ((Map) valueList).get(fieldName);
                if (propertyValue == null) {
                    Object value = null;

                    try {
                        value = PropertyUtils.getProperty(obj, fieldName);
                    } catch (IllegalAccessException var10) {
                        var10.printStackTrace();
                    } catch (InvocationTargetException var11) {
                        var11.printStackTrace();
                    } catch (NoSuchMethodException var12) {
                        var12.printStackTrace();
                    }

                    if (value instanceof Collection) {
                        propertyValue = null;
                    } else {
                        propertyValue = "-999999";
                    }
                }

                BeanUtils.setProperty(obj, fieldName, propertyValue);
            }
        }

        return obj;
    }

    public static Document buildRespDocument(List beanList, boolean rowFlag) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Document content = createEmptyDocument();
        Element svcContEl = content.addElement("svc_cont");
        Iterator iter = beanList.iterator();

        while (iter.hasNext()) {
            Object beanObj = iter.next();
            if (rowFlag) {
                buildXml(svcContEl, beanObj);
            } else {
                buildXmlNoRow(svcContEl, beanObj);
            }
        }

        return content;
    }

    public static Document buildRespDocument(List beanList, String rowName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Document contents = createEmptyDocument();
        Iterator iter = beanList.iterator();

        while (iter.hasNext()) {
            Element rowEl = contents.addElement(rowName);
            Object beanObj = iter.next();
            buildXmls(rowEl, beanObj, rowName);
        }

        return contents;
    }

    public static Element buildXmls(Element content, Object beanObj, String rowName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            Object value = null;
            value = BeanUtils.getProperty(beanObj, fieldName);
            Element nodeName = content.addElement(fieldName);
            if (value == null) {
                nodeName.addText("");
            } else {
                nodeName.addText(value.toString());
            }
        }

        return content;
    }

    public static Element buildXml(Element svcContEl, Object beanObj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Element rowEl = svcContEl.addElement("row");
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            Object value = null;
            value = BeanUtils.getProperty(beanObj, fieldName);
            Element nodeName = rowEl.addElement(fieldName);
            if (value == null) {
                nodeName.addText("");
            } else {
                nodeName.addText(value.toString());
            }
        }

        return svcContEl;
    }

    public static Document buildRespDocMoreSvc(List beanList, String svcContNodeName, String rowNodeName, String nextRowNodeName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Document content = createEmptyDocument();
        Iterator iter = beanList.iterator();

        while (true) {
            while (true) {
                while (iter.hasNext()) {
                    Element svcContEl = content.addElement(svcContNodeName);
                    Object beanObj = iter.next();
                    if (rowNodeName != null && rowNodeName != "") {
                        if (nextRowNodeName != null && nextRowNodeName != "") {
                            buildXml(svcContEl, beanObj, rowNodeName, nextRowNodeName);
                        } else {
                            buildXml(svcContEl, beanObj, rowNodeName);
                        }
                    } else {
                        buildXmlNoRow(svcContEl, beanObj);
                    }
                }

                return content;
            }
        }
    }

    public static Document buildRespDocument(List beanList, String svcContNodeName, String rowNodeName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Document content = createEmptyDocument();
        Element svcContEl = content.addElement(svcContNodeName);
        Iterator iter = beanList.iterator();

        while (true) {
            while (iter.hasNext()) {
                Object beanObj = iter.next();
                if (rowNodeName != null && rowNodeName != "") {
                    buildXml(svcContEl, beanObj, rowNodeName);
                } else {
                    buildXmlNoRow(svcContEl, beanObj);
                }
            }

            return content;
        }
    }

    public static Document buildRespDocument(List beanList, String svcContNodeName, String rowNodeName, String nextRowNodeName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Document content = createEmptyDocument();
        Element svcContEl = content.addElement(svcContNodeName);
        Iterator iter = beanList.iterator();

        while (true) {
            while (iter.hasNext()) {
                Object beanObj = iter.next();
                if (rowNodeName != null && rowNodeName != "") {
                    buildXml(svcContEl, beanObj, rowNodeName, nextRowNodeName);
                } else {
                    buildXmlNoRow(svcContEl, beanObj);
                }
            }

            return content;
        }
    }

    public static Document buildRespDocument(Object beanObj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Document content = createEmptyDocument();
        Element svcContEl = content.addElement("svc_cont");
        buildXmlNoRow(svcContEl, beanObj);
        return content;
    }

    public static Element buildXml(Element svcContEl, Object beanObj, String rowNodeName, String nextRowNodeName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            Object value = null;
            String fieldName = allFields[i].getName();
            value = BeanUtils.getProperty(beanObj, fieldName);
            if (!fieldName.equals("subBeanList")) {
                Element svcNodeEl = svcContEl.addElement(fieldName);
                if (value == null) {
                    svcNodeEl.addText("");
                } else if (value.equals("NaN")) {
                    svcNodeEl.addText("0");
                } else {
                    svcNodeEl.addText(value.toString());
                }
            } else {
                value = PropertyUtils.getProperty(beanObj, fieldName);
                Vector rowAllFields = (Vector) value;
                Iterator iter = rowAllFields.iterator();

                while (iter.hasNext()) {
                    Element rowEl = svcContEl.addElement(rowNodeName);
                    Object subBeanObj = iter.next();
                    buildRowNode(subBeanObj, rowEl, nextRowNodeName);
                }
            }
        }

        return svcContEl;
    }

    public static Element buildXml(Element svcContEl, Object beanObj, String rowNodeName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            Object value = null;
            String fieldName = allFields[i].getName();
            value = BeanUtils.getProperty(beanObj, fieldName);
            if (!fieldName.equals("subBeanList")) {
                Element svcNodeEl = svcContEl.addElement(fieldName);
                if (value == null) {
                    svcNodeEl.addText("");
                } else if (value.equals("NaN")) {
                    svcNodeEl.addText("0");
                } else {
                    svcNodeEl.addText(value.toString());
                }
            } else {
                value = PropertyUtils.getProperty(beanObj, fieldName);
                Vector rowAllFields = (Vector) value;
                Iterator iter = rowAllFields.iterator();

                while (iter.hasNext()) {
                    Element rowEl = svcContEl.addElement(rowNodeName);
                    Object subBeanObj = iter.next();
                    buildRowNode(subBeanObj, rowEl, rowNodeName);
                }
            }
        }

        return svcContEl;
    }

    private static Element buildRowNode(Object beanObj, Element rowEl, String rowNodeName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Field[] rowAllFields = beanObj.getClass().getDeclaredFields();

        for (int j = 1; j < rowAllFields.length; ++j) {
            Object rowValue = null;
            String rowFieldName = rowAllFields[j].getName();
            rowValue = BeanUtils.getProperty(beanObj, rowFieldName);
            if (!rowFieldName.equals("subBeanList")) {
                Element svcNodeEl = rowEl.addElement(rowFieldName);
                if (rowValue == null) {
                    svcNodeEl.addText("");
                } else if (rowValue.equals("NaN")) {
                    svcNodeEl.addText("0");
                } else {
                    svcNodeEl.addText(rowValue.toString());
                }
            } else {
                rowValue = PropertyUtils.getProperty(beanObj, rowFieldName);
                List rowNextAllFields = (List) rowValue;
                Iterator iter = rowNextAllFields.iterator();

                while (iter.hasNext()) {
                    Element rowElNext = rowEl.addElement(rowNodeName);
                    Object nextSubBeanObj = iter.next();
                    if (nextSubBeanObj != null) {
                        buildRowNode(nextSubBeanObj, rowElNext, rowNodeName);
                    }
                }
            }
        }

        return rowEl;
    }

    public static Element buildXmlNoRow(Element svcContEl, Object beanObj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            Object value = null;
            value = BeanUtils.getProperty(beanObj, fieldName);
            Element nodeName = svcContEl.addElement(fieldName);
            if (value == null) {
                nodeName.addText("");
            } else {
                System.out.println(value.toString());
                nodeName.addText(value.toString());
            }
        }

        return svcContEl;
    }

    public static Map parserXml(Document xmlDoc, List<String> nodeNameList, String flag) {
        Map<String, Object> menuPropertyMap = new HashMap();
        Iterator itr = nodeNameList.iterator();

        while (itr.hasNext()) {
            String nodeName = (String) itr.next();
            String nodeValue = getNodeValue(xmlDoc, "content", "svc_cont", nodeName);
            menuPropertyMap.put(nodeName, nodeValue);
        }

        return menuPropertyMap;
    }

    public static List parserXml(Document xmlDoc, List<String> nodeNameList) {
        List<String> menuPropertyMap = new Vector();

        String nodeValue;
        for (Iterator itr = nodeNameList.iterator(); itr.hasNext(); menuPropertyMap.add(nodeValue)) {
            String nodeName = (String) itr.next();
            nodeValue = getNodeValue(xmlDoc, "content", "svc_cont", nodeName);
            if (nodeValue == null || nodeValue.equals("")) {
                nodeValue = "-999999";
            }
        }

        return menuPropertyMap;
    }

    public static Map parserXml(Document xmlDoc, String contNode, String svcContNode) {
        Map<String, String> menuPropertyMap = new HashMap();
        Element rootEl = xmlDoc.getRootElement();
        Element contElement = rootEl.element(contNode);
        Element svcElement = contElement.element(svcContNode);
        List elList = svcElement.elements();
        Iterator iter = elList.iterator();

        while (true) {
            while (iter.hasNext()) {
                Element el = (Element) iter.next();
                if (el.getTextTrim() != null && !el.getTextTrim().equals("")) {
                    menuPropertyMap.put(el.getName(), el.getTextTrim());
                } else {
                    menuPropertyMap.put(el.getName(), "-999999");
                }
            }

            return menuPropertyMap;
        }
    }

    public static Map parserXml(Document xmlDoc, String contNode, String svcContNode, List rowNodeName, Class rowObj, Class nexRowObj) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, Object> propertyMap = new HashMap();
        Element rootEl = xmlDoc.getRootElement();
        Element contElement = rootEl.element(contNode);
        Element svcElement = contElement.element(svcContNode);
        List elList = svcElement.elements();
        List rowList = new Vector();
        List nextRowList = new Vector();
        Iterator iter = elList.iterator();

        while (true) {
            while (iter.hasNext()) {
                Element el = (Element) iter.next();
                if (rowNodeName.contains(el.getName())) {
                    List rowElList = el.elements();
                    Map<Object, Object> subPropertyMap = new HashMap();
                    Iterator itr = rowElList.iterator();

                    while (true) {
                        while (itr.hasNext()) {
                            Element rowEl = (Element) itr.next();
                            if (rowNodeName.contains(rowEl.getName())) {
                                List nextRowElList = rowEl.elements();
                                Map<Object, Object> nextSubPropertyMap = new HashMap();
                                Iterator itrs = nextRowElList.iterator();

                                while (true) {
                                    while (itrs.hasNext()) {
                                        Element nextRowEl = (Element) itrs.next();
                                        if (nextRowEl.getTextTrim() != null && !nextRowEl.getTextTrim().equals("")) {
                                            nextSubPropertyMap.put(nextRowEl.getName(), nextRowEl.getTextTrim());
                                        } else {
                                            nextSubPropertyMap.put(nextRowEl.getName(), "-999999");
                                        }
                                    }

                                    Object nextRObj = nexRowObj.newInstance();
                                    setObjectProperty(nextSubPropertyMap, nextRObj);
                                    nextRowList.add(nextRObj);
                                    break;
                                }
                            } else if (rowEl.getTextTrim() != null && !rowEl.getTextTrim().equals("")) {
                                subPropertyMap.put(rowEl.getName(), rowEl.getTextTrim());
                            } else {
                                subPropertyMap.put(rowEl.getName(), "-999999");
                            }
                        }

                        subPropertyMap.put("subBeanList", nextRowList);
                        Object rObj = rowObj.newInstance();
                        setObjectProperty(subPropertyMap, rObj);
                        rowList.add(rObj);
                        break;
                    }
                } else if (el.getTextTrim() != null && !el.getTextTrim().equals("")) {
                    propertyMap.put(el.getName(), el.getTextTrim());
                } else {
                    propertyMap.put(el.getName(), "-999999");
                }
            }

            propertyMap.put("subBeanList", rowList);
            return propertyMap;
        }
    }

    public static Map parserXml(Document xmlDoc, String contNode, String svcContNode, String rowNodeName, Class rowObj) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, Object> propertyMap = new HashMap();
        Element rootEl = xmlDoc.getRootElement();
        Element contElement = rootEl.element(contNode);
        Element svcElement = contElement.element(svcContNode);
        List elList = svcElement.elements();
        List rowList = new Vector();
        Iterator iter = elList.iterator();

        while (true) {
            while (iter.hasNext()) {
                Element el = (Element) iter.next();
                if (el.getName().equals(rowNodeName)) {
                    List rowElList = el.elements();
                    Map<Object, Object> subPropertyMap = new HashMap();
                    Iterator itr = rowElList.iterator();

                    while (true) {
                        while (itr.hasNext()) {
                            Element rowEl = (Element) itr.next();
                            if (rowEl.getTextTrim() != null && !rowEl.getTextTrim().equals("")) {
                                subPropertyMap.put(rowEl.getName(), rowEl.getTextTrim());
                            } else {
                                subPropertyMap.put(rowEl.getName(), "-999999");
                            }
                        }

                        Object rObj = rowObj.newInstance();
                        setObjectProperty(subPropertyMap, rObj);
                        rowList.add(rObj);
                        break;
                    }
                } else if (el.getTextTrim() != null && !el.getTextTrim().equals("")) {
                    propertyMap.put(el.getName(), el.getTextTrim());
                } else {
                    propertyMap.put(el.getName(), "-999999");
                }
            }

            propertyMap.put("subBeanList", rowList);
            return propertyMap;
        }
    }

    public static Object setObjectProperty(Map<Object, Object> valueMap, Object beanObj) throws IllegalAccessException, InvocationTargetException {
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            Object propertyValue = valueMap.get(fieldName);
            if (propertyValue == null) {
                Object value = null;

                try {
                    value = PropertyUtils.getProperty(beanObj, fieldName);
                } catch (IllegalAccessException var8) {
                    var8.printStackTrace();
                } catch (InvocationTargetException var9) {
                    var9.printStackTrace();
                } catch (NoSuchMethodException var10) {
                    var10.printStackTrace();
                }

                if (value instanceof Collection) {
                    propertyValue = null;
                } else {
                    propertyValue = "-999999";
                }
            }

            BeanUtils.setProperty(beanObj, fieldName, propertyValue);
        }

        return beanObj;
    }

    public static Element buildXml(Element svcContEl, Object beanObj, List<String> respNodeNameList) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Element rowEl = svcContEl.addElement("row");
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            Object value = null;
            value = BeanUtils.getProperty(beanObj, fieldName);
            Element nodeName = rowEl.addElement(((String) respNodeNameList.get(i - 1)).toString());
            if (value == null) {
                nodeName.addText("");
            } else {
                nodeName.addText(value.toString());
            }
        }

        return svcContEl;
    }

    public static Element buildXmlNoRow(Element svcContEl, Object beanObj, List<String> respNodeNameList) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            Object value = null;
            value = BeanUtils.getProperty(beanObj, fieldName);
            Element nodeName = svcContEl.addElement(((String) respNodeNameList.get(i - 1)).toString());
            if (value == null) {
                nodeName.addText("");
            } else {
                nodeName.addText(value.toString());
            }
        }

        return svcContEl;
    }

    public static Object setAllObjectProperty(Document doc, Object obj, List<String> nodeNameList) throws IllegalAccessException, InvocationTargetException {
        List<String> valueList = new Vector();
        if (doc != null) {
            valueList = parserXml(doc, nodeNameList);
        } else {
            System.out.println("---------您没有把与您的Bean相对应的dom4j Document对象传进来！----------");
            System.out.println("---------并且您的Bean对象的第一个属性以外的属性必须与您的节点列表一一对应！----------");
            System.out.println("---------通常Bean对象要序列化,第一个属性是serialVersionUID----------");
        }

        Field[] allFields = obj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            BeanUtils.setProperty(obj, fieldName, ((List) valueList).get(i - 1));
        }

        return obj;
    }

    public static Object setAllObjectProperty(Document doc, Object obj, String contNode, String svcContNode) throws IllegalAccessException, InvocationTargetException {
        Map<String, String> valueList = new HashMap();
        if (doc != null) {
            valueList = parserXml(doc, contNode, svcContNode);
        } else {
            System.out.println("---------您没有把与您的Bean相对应的dom4j Document对象传进来！----------");
            System.out.println("---------并且您的Bean对象的第一个属性以外的属性必须与您的文档对象要解析的节点列表一一对应！----------");
            System.out.println("---------通常Bean对象要序列化,第一个属性是serialVersionUID----------");
        }

        Field[] allFields = obj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            Object propertyValue = ((Map) valueList).get(fieldName);
            if (propertyValue == null) {
                Object value = null;

                try {
                    value = PropertyUtils.getProperty(obj, fieldName);
                } catch (IllegalAccessException var11) {
                    var11.printStackTrace();
                } catch (InvocationTargetException var12) {
                    var12.printStackTrace();
                } catch (NoSuchMethodException var13) {
                    var13.printStackTrace();
                }

                if (value instanceof Collection) {
                    propertyValue = null;
                } else {
                    propertyValue = "-999999";
                }
            }

            BeanUtils.setProperty(obj, fieldName, propertyValue);
        }

        return obj;
    }

    public static Object setPropertyHaveSubBean(Document doc, Object obj, Class subObj, String contNode, String svcContNode, String rowNodeName) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, String> valueList = new HashMap();
        if (doc != null) {
            valueList = parserXml(doc, contNode, svcContNode, rowNodeName, subObj);
        } else {
            System.out.println("---------您没有把与您的Bean相对应的dom4j Document对象传进来！----------");
            System.out.println("---------并且您的Bean对象的第一个属性以外的属性必须与您的文档对象要解析的节点列表一一对应！----------");
            System.out.println("---------通常Bean对象要序列化,第一个属性是serialVersionUID----------");
        }

        Field[] allFields = obj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            Object propertyValue = ((Map) valueList).get(fieldName);
            if (propertyValue == null) {
                Object value = null;

                try {
                    value = PropertyUtils.getProperty(obj, fieldName);
                } catch (IllegalAccessException var13) {
                    var13.printStackTrace();
                } catch (InvocationTargetException var14) {
                    var14.printStackTrace();
                } catch (NoSuchMethodException var15) {
                    var15.printStackTrace();
                }

                if (value instanceof Collection) {
                    propertyValue = null;
                } else {
                    propertyValue = "-999999";
                }
            }

            BeanUtils.setProperty(obj, fieldName, propertyValue);
        }

        return obj;
    }

    public static Object setPropertyHaveNextSubBean(Document doc, Object obj, Class subObj, Class nextSubObj, String contNode, String svcContNode, List rowNodeNameList) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, String> valueList = new HashMap();
        if (doc != null) {
            valueList = parserXml(doc, contNode, svcContNode, rowNodeNameList, subObj, nextSubObj);
        } else {
            System.out.println("---------您没有把与您的Bean相对应的dom4j Document对象传进来！----------");
            System.out.println("---------并且您的Bean对象的第一个属性以外的属性必须与您的文档对象要解析的节点列表一一对应！----------");
            System.out.println("---------通常Bean对象要序列化,第一个属性是serialVersionUID----------");
        }

        Field[] allFields = obj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            Object propertyValue = ((Map) valueList).get(fieldName);
            if (propertyValue == null) {
                Object value = null;

                try {
                    value = PropertyUtils.getProperty(obj, fieldName);
                } catch (IllegalAccessException var14) {
                    var14.printStackTrace();
                } catch (InvocationTargetException var15) {
                    var15.printStackTrace();
                } catch (NoSuchMethodException var16) {
                    var16.printStackTrace();
                }

                if (value instanceof Collection) {
                    propertyValue = null;
                } else {
                    propertyValue = "-999999";
                }
            }

            BeanUtils.setProperty(obj, fieldName, propertyValue);
        }

        return obj;
    }

    public static Element getElement(Document doc, String name) {
        if (doc == null) {
            return null;
        } else {
            Element el = doc.getRootElement().element(name);
            return el;
        }
    }

    public static Element getElement(Element element, String name) {
        if (element != null && name != null && name.trim().length() > 0) {
            Element el = element.element(name);
            return el;
        } else {
            return null;
        }
    }

    public static Element getElement(Document doc, String parentName, String name) {
        if (doc == null) {
            return null;
        } else {
            Element pEl = doc.getRootElement().element(parentName);
            if (pEl == null) {
                return null;
            } else {
                Element el = pEl.element(name);
                return el;
            }
        }
    }
}
