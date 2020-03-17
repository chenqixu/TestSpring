package com.cqx.testspring.webservice.common.util.xml;

import com.cqx.testspring.webservice.common.*;
import com.cqx.testspring.webservice.common.model.WSBean;
import com.cqx.testspring.webservice.common.util.bean.XmlBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.*;

/**
 * WSXmlParser
 *
 * @author chenqixu
 */
public class WSXmlParser {
    private static final Logger log = LoggerFactory.getLogger(WSXmlParser.class);

    public WSXmlParser() {
    }

    public static Object parserXmlNode(Document xml, String xPath) {
        return XMLUtil.getXpathNodeValue(xml, xPath);
    }

    public static Object parserXml(Document xml, Class classs) {
        MessageHelperer messageHelperer = new XmlMessageHelper(xml);
        Object respObj = messageHelperer.fetchObject(classs);
        return respObj;
    }

    public static Object parserXml(Document xml, String xPath, Class classs) {
        MessageHelperer messageHelperer = new XmlMessageHelper(xml);
        Object respObj = messageHelperer.fetchObject(xPath, classs);
        return respObj;
    }

    public static Object desParserXml(String xml, String xPath, Class classs) throws MalformedURLException, DocumentException {
        Document xmlDoc = XMLUtil.buildDocument(xml);
        MessageHelperer messageHelperer = new DesMessageHelper(xmlDoc);
        Object respObj = messageHelperer.fetchObject(xPath, classs);
        return respObj;
    }

    public static List parserXml2List(Document xml, String xPath, Class classs) {
        MessageHelperer messageHelperer = new XmlMessageHelper(xml);
        Object respObj = messageHelperer.fetchObjectList(xPath, classs);
        return (List) respObj;
    }

    public static List parserXml2List(String xmlStr, String xPath, Class classs) throws MalformedURLException, DocumentException {
        Document xml = XMLUtil.buildDocument(xmlStr);
        MessageHelperer messageHelperer = new XmlMessageHelper(xml);
        Object respObj = messageHelperer.fetchObjectList(xPath, classs);
        return (List) respObj;
    }

    public static Object parserXml(InputStream xml, Class classs) throws MalformedURLException, DocumentException {
        Document xmlDoc = XMLUtil.buildDocument(xml);
        MessageHelperer messageHelperer = new XmlMessageHelper(xmlDoc);
        Object respObj = messageHelperer.fetchObject(classs);
        return respObj;
    }

    public static Object parserXml(String xml, Class classs) throws MalformedURLException, DocumentException {
        Document xmlDoc = XMLUtil.buildDocument(xml);
        MessageHelperer messageHelperer = new XmlMessageHelper(xmlDoc);
        Object respObj = messageHelperer.fetchObject(classs);
        return respObj;
    }

    public static Document appendXml(Document xml, Object obj, String xPath) throws Exception {
        return appendXml(xml, obj, xPath, "", "");
    }

    public static Document appendXml(Document xml, Object obj, String xPath, String nodeName) throws Exception {
        return appendXml(xml, obj, xPath, nodeName, "");
    }

    public static Document appendXml(Document xml, Object obj, String xPath, String nodeName, String encode) throws Exception {
        if (encode != null && !"".equals(encode)) {
            xml.setXMLEncoding(encode);
        }

        Element pathEl = (Element) xml.selectSingleNode(xPath);
        List objList;
        int i;
        if (nodeName != null && !"".equals(nodeName)) {
            if (obj instanceof Collection) {
                objList = (List) obj;

                for (i = 0; i < objList.size(); ++i) {
                    buildRowNode(objList.get(i), pathEl.addElement(nodeName));
                }
            } else if (obj instanceof String) {
                pathEl.addElement(nodeName).setText(obj.toString());
            } else {
                buildRowNode(obj, pathEl.addElement(nodeName));
            }
        } else if (obj instanceof Collection) {
            objList = (List) obj;

            for (i = 0; i < objList.size(); ++i) {
                buildRowNode(objList.get(i), pathEl);
            }
        } else if (obj instanceof String) {
            pathEl.addElement(obj.toString()).setText(obj.toString());
        } else {
            buildRowNode(obj, pathEl);
        }

        return xml;
    }

    public static Document buildXml(Object obj, String rootTagName) throws Exception {
        return buildXml(obj, rootTagName, "");
    }

    public static Document buildXml(Object obj, String rootTagName, String nodeName) throws Exception {
        return buildXml(obj, rootTagName, nodeName, "");
    }

    public static Document buildXml(Object obj, String rootTagName, String nodeName, String encode) throws Exception {
        Document content = XMLUtil.createEmptyDocument();
        if (encode != null && !"".equals(encode)) {
            content.setXMLEncoding(encode);
        }

        Element rootEl = content.addElement(rootTagName);
        if (obj instanceof Collection) {
            List beanList = (List) obj;
            Iterator iter = beanList.iterator();

            while (true) {
                while (true) {
                    Object beanObj;
                    do {
                        if (!iter.hasNext()) {
                            return content;
                        }

                        beanObj = iter.next();
                    } while (beanObj == null);

                    if (nodeName != null && !"".equals(nodeName)) {
                        buildRowNode(beanObj, rootEl.addElement(nodeName));
                    } else {
                        buildRowNode(beanObj, rootEl);
                    }
                }
            }
        } else {
            if (nodeName != null && !"".equals(nodeName)) {
                buildRowNode(obj, rootEl.addElement(nodeName));
            } else {
                buildRowNode(obj, rootEl);
            }

            return content;
        }
    }

    public static Document buildXml(Object obj, String rootTagName, boolean isSample, String encode) throws Exception {
        Document content = XMLUtil.createEmptyDocument();
        if (encode != null && !"".equals(encode)) {
            content.setXMLEncoding(encode);
        }

        Element rootEl = content.addElement(rootTagName);
        if (obj instanceof Collection) {
            List beanList = (List) obj;
            Iterator iter = beanList.iterator();

            while (iter.hasNext()) {
                Object beanObj = iter.next();
                if (beanObj != null) {
                    buildRowNode(beanObj, rootEl, isSample);
                }
            }

            return content;
        } else {
            buildRowNode(obj, rootEl, isSample);
            return content;
        }
    }

    public static Document buildResponseDocument(Object obj) throws Exception {
        List beanList = new Vector();
        beanList.add(obj);
        return buildRespDocument(beanList);
    }

    public static Document buildRespDocument(List beanList) throws Exception {
        Document content = XMLUtil.createEmptyDocument();
        Iterator iter = beanList.iterator();

        while (iter.hasNext()) {
            Object beanObj = iter.next();
            if (beanObj != null) {
                buildXml(content, beanObj);
            }
        }

        return content;
    }

    private static Document buildXml(Document content, Object beanObj) throws Exception {
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 0; i < allFields.length; ++i) {
            Object value = null;
            String fieldName = allFields[i].getName();
            if (!fieldName.equals("serialVersionUID")) {
                value = PropertyUtils.getProperty(beanObj, fieldName);
                if (value != null) {
                    if (value instanceof Collection) {
                        Vector rowAllFields = (Vector) value;
                        Iterator iter = rowAllFields.iterator();

                        while (iter.hasNext()) {
                            Element rowEl = content.addElement(fieldName);
                            Object subBeanObj = iter.next();
                            if (subBeanObj != null) {
                                buildRowNode(subBeanObj, rowEl);
                            }
                        }
                    } else {
                        Element rowEl;
                        if (isBuildRowNode(value)) {
                            rowEl = content.addElement(fieldName);
                            buildRowNode(value, rowEl);
                        } else if (value != null && !"".equals(value)) {
                            rowEl = content.addElement(fieldName);
                            rowEl.addText(value.toString());
                        }
                    }
                }
            }
        }

        return content;
    }

    private static Element buildRowNode(Object beanObj, Element rowEl) throws Exception {
        return buildRowNode(beanObj, rowEl, false);
    }

    private static Element buildRowNode(Object beanObj, Element rowEl, boolean isSample) throws Exception {
        if (!isBuildRowNode(beanObj)) {
            rowEl.addText(beanObj.toString());
            return rowEl;
        } else {
            Map rowAllMapFields = BeanUtil.getClassFields(beanObj.getClass(), true);
            Object[] rowAllFields = rowAllMapFields.keySet().toArray();

            for (int j = 0; j < rowAllFields.length; ++j) {
                Object rowValue = null;
                String rowFieldName = rowAllFields[j].toString();
                if (!rowFieldName.equals("serialVersionUID")) {
                    try {
                        if (isSample) {
                            DesClassInfo classInfo = DesClassInfo.getInstance(beanObj.getClass());
                            Method getMethod = classInfo.getGetter(rowFieldName);

                            try {
                                Field field = beanObj.getClass().getDeclaredField(rowFieldName);
                                field.setAccessible(true);
                                rowValue = field.get(beanObj);
                            } catch (Exception var14) {
                                try {
                                    rowValue = getMethod.invoke(beanObj);
                                } catch (Exception var13) {
                                    log.info(var13.getMessage(), var13.getStackTrace());
                                    rowValue = null;
                                }
                            }

                            classInfo = null;
                        } else {
                            rowValue = PropertyUtils.getProperty(beanObj, rowFieldName);
                        }
                    } catch (Exception var15) {
                        var15.printStackTrace();

                        try {
                            rowValue = PropertyUtils.getProperty(beanObj, rowFieldName);
                        } catch (Exception var12) {
                            var15.printStackTrace();
                        }
                    }

                    if (rowValue != null) {
                        if (rowValue instanceof Collection) {
                            List rowNextAllFields = (List) rowValue;
                            Iterator iter = rowNextAllFields.iterator();

                            while (iter.hasNext()) {
                                Element rowElNext = rowEl.addElement(rowFieldName);
                                Object nextSubBeanObj = iter.next();
                                buildRowNode(nextSubBeanObj, rowElNext, isSample);
                            }
                        } else {
                            Element rowElNext;
                            if (isDate(rowValue)) {
                                rowElNext = rowEl.addElement(rowFieldName);
                                rowElNext.addText(String.valueOf(((Date) rowValue).getTime()));
                            } else if (isBuildRowNode(rowValue)) {
                                rowElNext = rowEl.addElement(rowFieldName);
                                buildRowNode(rowValue, rowElNext, isSample);
                            } else if (rowValue != null && !"".equals(rowValue)) {
                                rowElNext = rowEl.addElement(rowFieldName);
                                rowElNext.addText(rowValue.toString());
                            }
                        }
                    }
                }
            }

            return rowEl;
        }
    }

    public static Document buildXTableDocByList(List resultList) {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("GBK");
        Element xtable = doc.addElement("xtable");
        String xtableData = "";

        for (Iterator itr = resultList.iterator(); itr.hasNext(); xtableData = xtableData + "0x7f") {
            List dataList = (List) itr.next();

            String data;
            for (Iterator itrs = dataList.iterator(); itrs.hasNext(); xtableData = xtableData + data + "0x09") {
                data = (String) itrs.next();
                if (data.equals("")) {
                    data = "-999999";
                }
            }

            if (xtableData.length() > 4) {
                xtableData = xtableData.substring(0, xtableData.length() - 4);
            }
        }

        if (xtableData.length() > 4) {
            xtableData = xtableData.substring(0, xtableData.length() - 4);
        }

        xtable.addText(xtableData);
        return doc;
    }

    public static Document buildXTableDocument(List resultList) throws Exception {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("GBK");
        Element xtable = doc.addElement("xtable");
        String xtableTxt = "";
        String xtableTitle = "";
        String xtableData = "";
        if (resultList.size() > 0) {
            Object titleBean = resultList.get(0);
            xtableTitle = buildXTableTitle(titleBean, xtableTitle);

            Object objBean;
            for (Iterator itr = resultList.iterator(); itr.hasNext(); xtableData = buildXTableData(objBean, xtableData)) {
                objBean = itr.next();
            }
        }

        if (xtableData.length() > 4) {
            xtableData = xtableData.substring(0, xtableData.length() - 4);
        }

        xtableTxt = xtableTitle + xtableData;
        xtable.addText(xtableTxt);
        return doc;
    }

    private static String buildXTableTitle(Object beanObj, String titleTxt) throws Exception {
        Field[] rowAllFields = beanObj.getClass().getDeclaredFields();

        for (int j = 0; j < rowAllFields.length; ++j) {
            Object rowValue = null;
            String rowFieldName = rowAllFields[j].getName();
            if (!rowFieldName.equals("serialVersionUID")) {
                titleTxt = titleTxt + rowFieldName + "0x09";
            }
        }

        if (titleTxt.length() > 4) {
            titleTxt = titleTxt.substring(0, titleTxt.length() - 4);
        }

        titleTxt = titleTxt + "0x7f";
        return titleTxt;
    }

    private static String buildXTableData(Object beanObj, String dataTxt) throws Exception {
        Field[] rowAllFields = beanObj.getClass().getDeclaredFields();

        for (int j = 0; j < rowAllFields.length; ++j) {
            Object rowValue = null;
            String rowFieldName = rowAllFields[j].getName();
            if (!rowFieldName.equals("serialVersionUID")) {
                rowValue = PropertyUtils.getProperty(beanObj, rowFieldName);
                if (rowValue != null && !rowValue.equals("")) {
                    dataTxt = dataTxt + rowValue + "0x09";
                } else {
                    dataTxt = dataTxt + "-999999" + "0x09";
                }
            }
        }

        if (dataTxt.length() > 4) {
            dataTxt = dataTxt.substring(0, dataTxt.length() - 4);
        }

        dataTxt = dataTxt + "0x7f";
        return dataTxt;
    }

    public static List parseXml2List(String xmlStr, String xpath, boolean isTable) throws Exception {
        Document xml = XMLUtil.buildDocument(xmlStr);
        new XmlMessageHelper(xml);
        Element root = xml.getRootElement();
        List retList = new Vector();
        Element[] linetitle;
        if (!isTable) {
            List xmlList = root.selectNodes(xpath);
            linetitle = new Element[xmlList.size()];
            int linetitle_cnt = 0;
            Iterator it = xmlList.iterator();

            while (true) {
                List elementList;
                do {
                    do {
                        if (!it.hasNext()) {
                            return retList;
                        }

                        linetitle[linetitle_cnt] = (Element) it.next();
                        elementList = linetitle[linetitle_cnt].elements();
                        linetitle_cnt++;
                    } while (elementList == null);
                } while (elementList.size() <= 0);

                List tempList = new Vector();

                for (int i = 0; i < elementList.size(); ++i) {
                    Element dest = (Element) elementList.get(i);
                    if (dest != null) {
                        String tempVal = dest.getTextTrim();
                        if (tempVal == null) {
                            tempVal = "";
                        }

                        tempList.add(tempVal);
                    }
                }

                retList.add(tempList);
            }
        } else {
            String xtablestr = "";
            if (root != null) {
                xtablestr = root.getTextTrim();
            }

            if (xtablestr != null && xtablestr.trim().length() > 0) {
                System.out.println("==============" + xtablestr);
                String[] rowdata = xtablestr.split("0x7f");
                linetitle = null;

                for (int i = 0; i < rowdata.length; ++i) {
                    String[] linedata = rowdata[i].split("0x09");
                    List linedataList = new Vector();
                    Map tempBeanMap = new HashMap();

                    for (int j = 0; j < linedata.length; ++j) {
                        linedataList.add(TypeConvert.nullToZero(linedata[j]));
                        if (linetitle != null && ((Object[]) linetitle).length > 0) {
                            tempBeanMap.put(((Object[]) linetitle)[j], TypeConvert.nullToZero(linedata[j]));
                        }
                    }

                    retList.add(linedataList);
                }
            }

            return retList;
        }
    }

    private static boolean isWSBean(Object beanObj) {
        return beanObj instanceof XmlBean || beanObj instanceof WSBean || beanObj instanceof WSBean || beanObj instanceof XmlBean || beanObj instanceof RequestObjectIntf || beanObj instanceof ResponseObjectIntf || beanObj instanceof BodyReqIntf || beanObj instanceof ReqDataIntf || beanObj instanceof BodyRespIntf || beanObj instanceof RespDataIntf;
    }

    private static boolean isDate(Object object) {
        return object instanceof Date;
    }

    private static boolean isBuildRowNode(Object object) {
        return !(object instanceof String) && !(object instanceof Boolean) && !(object instanceof Integer) && !(object instanceof Long) && !(object instanceof Double) && !(object instanceof Float);
    }
}
