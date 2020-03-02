package com.cqx.testspring.webservice.common.util.xml;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.util.*;

/**
 * XmlMessageHelper
 *
 * @author chenqixu
 */
public class XmlMessageHelper extends BaseMessage implements MessageHelperer {
    private static Log log = LogFactory.getLog(XmlMessageHelper.class);
    private int mode = 1;
    private String nodeName = "row";
    private boolean sign = false;

    public XmlMessageHelper() {
    }

    public XmlMessageHelper(Document document) {
        this.setDocument(document);
    }

    public void restoreDefault() {
        this.mode = 1;
        this.nodeName = "row";
        this.sign = false;
    }

    public void setDocument(Document document) {
        if (document != null) {
            this.document = document;
            this.root = document.getRootElement();
        }

    }

    public Document getDocument() {
        return this.document;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public boolean isSign() {
        return this.sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    private void appendObject(Element parent, String newNode, Object object, boolean flag) {
        if (parent != null && object != null) {
            List current;
            if (!(object instanceof List) && !(object instanceof ArrayList)) {
                if (!(object instanceof String) && !(object instanceof Boolean) && !(object instanceof Integer) && !(object instanceof Long) && !(object instanceof Double) && !(object instanceof Float)) {
                    current = null;
                    Element currentElement;
                    if (newNode != null && !"".equals(newNode)) {
                        currentElement = parent.addElement(newNode);
                    } else {
                        currentElement = parent;
                    }

                    Class currentClass = object.getClass();

                    try {
                        ClassInfo classInfo = ClassInfo.getInstance(currentClass);
                        String[] properties = classInfo.getReadablePropertyNames();

                        for (int i = 0; i < properties.length; ++i) {
                            String propertyName = properties[i];
                            Object value = null;
                            value = classInfo.getGetter(propertyName).invoke(object);
                            if (value != null) {
                                Class clazz = classInfo.getGetterType(propertyName);
                                switch (this.checkType(clazz)) {
                                    case 11:
                                        Field field = classInfo.getField(propertyName);
                                        if (field != null) {
                                            MessageAnnotation ma = (MessageAnnotation) field.getAnnotation(MessageAnnotation.class);
                                            if (ma != null) {
                                                this.setMode(ma.mode());
                                                this.setSign(ma.sign());
                                                this.setNodeName(ma.nodeName());
                                            }
                                        }

                                        if (flag && this.getMode() == 1) {
                                            this.restoreDefault();
                                            Element e = currentElement.addElement(propertyName);
                                            this.appendObject(e, "row", value, flag);
                                        } else if (flag && this.getMode() == 2) {
                                            if (this.isSign()) {
                                                this.restoreDefault();
                                                this.appendObject(currentElement, propertyName, value, flag);
                                            } else {
                                                this.restoreDefault();
                                                this.appendObject(currentElement, this.getNodeName(), value, flag);
                                            }
                                        }
                                    case 14:
                                    case 16:
                                    case 17:
                                    case 21:
                                    case 22:
                                    case 98:
                                    case 100:
                                        break;
                                    case 99:
                                        this.appendObject((Element) currentElement.addElement(propertyName), (String) null, value, flag);
                                        break;
                                    default:
                                        this.appendValue(currentElement.addElement(propertyName), value);
                                }
                            }
                        }
                    } catch (Exception var15) {
                        log.error(var15);
                    }

                } else {
                    this.appendValue(parent.addElement(newNode), object);
                }
            } else {
                current = (List) object;
                Iterator it = current.iterator();

                while (it.hasNext()) {
                    this.appendObject(parent, newNode, it.next(), flag);
                }

            }
        }
    }

    public void appendObject(String xPath, String newNode, Object value) {
        this.appendObject(xPath, newNode, value, true);
    }

    public void appendObject(String xPath, String newNode, Object value, boolean flag) {
        Element dest = this.getElemment(xPath);
        this.appendObject(dest, newNode, value, flag);
    }

    private void appendValue(Element current, Object value) {
        current.setText(String.valueOf(value));
    }

    public void appendValue(String xPath, String newNode, Object value) {
        Element dest = this.getElemment(xPath);
        if (dest != null) {
            if (newNode != null && !"".equals(newNode)) {
                this.appendValue(dest.addElement(newNode), value);
            } else {
                this.appendValue(dest, value);
            }

        }
    }

    public void appendCDATAValue(String xPath, String newNode, Object value) {
        Element dest = this.getElemment(xPath);
        if (dest != null) {
            if (newNode != null && !"".equals(newNode)) {
                dest = dest.addElement(newNode);
                dest.addCDATA(String.valueOf(value));
            } else {
                dest.addCDATA(String.valueOf(value));
            }

        }
    }

    public void appendValueList(String xPath, String newNode, List dataList) {
        if (newNode != null && !"".equals(newNode)) {
            Element dest = this.getElemment(xPath);
            if (dest != null && dataList != null && dataList.size() >= 0) {
                Iterator it = dataList.iterator();

                while (it.hasNext()) {
                    this.appendValue(dest.addElement(newNode), it.next());
                }

            }
        }
    }

    public Object fetchObject(Class clazz) {
        return this.fetchObject(this.root, clazz, true);
    }

    public Object fetchObject(Class clazz, boolean flag) {
        return this.fetchObject(this.root, clazz, flag);
    }

    public Object fetchObject(String xPath, Class clazz) {
        return this.fetchObject(xPath, clazz, true);
    }

    public Object fetchObject(String xPath, Class clazz, boolean flag) {
        Element current = this.getElemment(xPath);
        return current == null ? null : this.fetchObject(current, clazz, flag);
    }

    private Object fetchObject(Element current, Class clazz, boolean flag) {
        Object object = null;

        try {
            if (this.checkType(clazz) == 5) {
                object = current.getTextTrim();
                return object;
            }

            if (current == null || current.elements().size() <= 0) {
                return object;
            }

            object = clazz.newInstance();
            ClassInfo classInfo = ClassInfo.getInstance(clazz);
            String[] writeablePropertyNames = classInfo.getWriteablePropertyNames();

            for (int i = 0; i < writeablePropertyNames.length; ++i) {
                String propertyName = writeablePropertyNames[i];
                Class attrClass = classInfo.getWriteableFieldType(propertyName);
                if (attrClass != null) {
                    Element dest = null;
                    switch (this.checkType(attrClass)) {
                        case 6:
                            dest = current.element(propertyName);
                            if (dest != null) {
                                BeanUtils.setProperty(object, propertyName, new Date(Long.valueOf(dest.getTextTrim())));
                            }
                        case 99:
                            dest = current.element(propertyName);
                            if (dest != null) {
                                Object tmp = this.fetchObject(dest, attrClass, flag);
                                if (tmp != null) {
                                    BeanUtils.setProperty(object, propertyName, tmp);
                                }
                            }
                            break;
                        case 11:
                            Field field = classInfo.getField(propertyName);
                            if (field != null) {
                                MessageAnnotation ma = (MessageAnnotation) field.getAnnotation(MessageAnnotation.class);
                                if (ma != null) {
                                    this.setMode(ma.mode());
                                    this.setNodeName(ma.nodeName());
                                    this.setSign(ma.sign());
                                }
                            }

                            List<Class> gtList = classInfo.getFieldGenericTypes(propertyName);
                            if (gtList != null && gtList.size() >= 1) {
                                Class klazz = (Class) gtList.get(0);
                                Element listElement;
                                if (flag && this.getMode() == 1) {
                                    listElement = null;
                                    List nodes = current.elements(propertyName);
                                    this.restoreDefault();
                                    if (nodes != null && nodes.size() > 0) {
                                        List list = new Vector();
                                        if (this.checkType(klazz) == 5) {
                                            for (int s = 0; s < nodes.size(); ++s) {
                                                Element temp = (Element) nodes.get(s);
                                                ((List) list).add(temp.getTextTrim());
                                            }
                                        } else {
                                            list = this.fetchObjectList(nodes, klazz, true);
                                        }

                                        BeanUtils.setProperty(object, propertyName, list);
                                    }
                                } else if (flag && this.getMode() == 2) {
                                    this.restoreDefault();
                                    listElement = current.element(propertyName);
                                    if (listElement != null) {
                                        List list = this.fetchObjectList(listElement.selectNodes("row"), klazz, true);
                                        BeanUtils.setProperty(object, propertyName, list);
                                    }
                                }
                            }
                        case 14:
                        case 16:
                        case 17:
                        case 21:
                        case 22:
                        case 98:
                        case 100:
                            break;
                        default:
                            dest = current.element(propertyName);
                            if (dest != null) {
                                BeanUtils.setProperty(object, propertyName, dest.getTextTrim());
                            }
                    }
                }
            }
        } catch (Exception var18) {
            log.error("fuck:" + var18);
        }

        return object;
    }

    public String fetchValue(String xPath) {
        Element dest = this.getElemment(xPath);
        return dest == null ? null : dest.getTextTrim();
    }

    public List fetchValueList(String xPath) {
        return null;
    }

    private List fetchObjectList(List nodes, Class clazz, boolean flag) {
        List<Object> list = new ArrayList();
        if (nodes != null && nodes.size() > 0) {
            Iterator it = nodes.iterator();

            while (it.hasNext()) {
                Element temp = (Element) it.next();
                Object obj = this.fetchObject(temp, clazz, flag);
                if (obj != null) {
                    list.add(obj);
                }
            }

            return list;
        } else {
            return list;
        }
    }

    public List fetchObjectList(String xPath, Class clazz, boolean flag) {
        return this.fetchObjectList(this.getElemmentList(xPath), clazz, flag);
    }

    public List fetchObjectList(String xPath, Class clazz) {
        return this.fetchObjectList(xPath, clazz, true);
    }

    public String getErrorMsg() {
        return null;
    }

    public boolean isValid() {
        return false;
    }
}
