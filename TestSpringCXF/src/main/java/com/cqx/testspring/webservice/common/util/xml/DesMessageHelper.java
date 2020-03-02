package com.cqx.testspring.webservice.common.util.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * DesMessageHelper
 *
 * @author chenqixu
 */
public class DesMessageHelper extends BaseMessage implements MessageHelperer {
    private static Log log = LogFactory.getLog(DesMessageHelper.class);
    private int mode = 1;
    private String nodeName = "row";
    private boolean sign = false;

    public DesMessageHelper() {
    }

    public DesMessageHelper(Document document) {
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
                        DesClassInfo classInfo = DesClassInfo.getInstance(currentClass);
                        String[] properties = classInfo.getReadablePropertyNames();

                        for (int i = 0; i < properties.length; ++i) {
                            String propertyName = properties[i];
                            Object value = null;
                            value = classInfo.getGetter(propertyName).invoke(object);
                            if (value != null) {
                                Class clazz = classInfo.getGetterType(propertyName);
                                switch (this.checkType(clazz)) {
                                    case 11:
                                    case 21:
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

            if (current == null) {
                return object;
            }

            if (this.checkType(clazz) != 305) {
                object = clazz.newInstance();
            }

            if (current.elements().size() <= 0) {
                return object;
            }

            DesClassInfo classInfo = DesClassInfo.getInstance(clazz);
            String[] writeablePropertyNames = classInfo.getWriteablePropertyNames();

            for (int i = 0; i < writeablePropertyNames.length; ++i) {
                String propertyName = writeablePropertyNames[i];
                Class attrClass = classInfo.getWriteableFieldType(propertyName);
                if (attrClass != null) {
                    Element dest = null;
                    Method setMethod;
//                    Method setMethod;
                    Field setfield;
                    List nodes;
//                    List nodes;
//                    Method setMethod;
//                    Field setfield;
                    switch (this.checkType(attrClass)) {
                        case 1:
                        case 201:
                            dest = current.element(propertyName);
                            if (dest != null) {
                                setMethod = classInfo.getSetter(propertyName);

                                try {
                                    setfield = object.getClass().getDeclaredField(propertyName);
                                    setfield.setAccessible(true);
                                    setfield.set(object, Integer.parseInt(dest.getTextTrim()));
                                } catch (Exception var37) {
                                    try {
                                        setMethod.invoke(object, Integer.parseInt(dest.getTextTrim()));
                                    } catch (Exception var36) {
                                        log.info(var36.getStackTrace());
                                        var36.printStackTrace();
                                    }
                                }
                            }
                            break;
                        case 2:
                        case 202:
                            dest = current.element(propertyName);
                            if (dest != null) {
                                setMethod = classInfo.getSetter(propertyName);

                                try {
                                    setfield = object.getClass().getDeclaredField(propertyName);
                                    setfield.setAccessible(true);
                                    setfield.set(object, Long.parseLong(dest.getTextTrim()));
                                } catch (Exception var35) {
                                    try {
                                        setMethod.invoke(object, Long.parseLong(dest.getTextTrim()));
                                    } catch (Exception var34) {
                                        log.info(var34.getStackTrace());
                                        var34.printStackTrace();
                                    }
                                }
                            }
                            break;
                        case 3:
                        case 203:
                            dest = current.element(propertyName);
                            if (dest != null) {
                                setMethod = classInfo.getSetter(propertyName);

                                try {
                                    setfield = object.getClass().getDeclaredField(propertyName);
                                    setfield.setAccessible(true);
                                    setfield.set(object, Double.parseDouble(dest.getTextTrim()));
                                } catch (Exception var31) {
                                    try {
                                        setMethod.invoke(object, Double.parseDouble(dest.getTextTrim()));
                                    } catch (Exception var30) {
                                        log.info(var30.getStackTrace());
                                        var30.printStackTrace();
                                    }
                                }
                            }
                            break;
                        case 4:
                        case 204:
                            dest = current.element(propertyName);
                            if (dest != null) {
                                setMethod = classInfo.getSetter(propertyName);

                                try {
                                    setfield = object.getClass().getDeclaredField(propertyName);
                                    setfield.setAccessible(true);
                                    setfield.set(object, Float.parseFloat(dest.getTextTrim()));
                                } catch (Exception var33) {
                                    try {
                                        setMethod.invoke(object, Float.parseFloat(dest.getTextTrim()));
                                    } catch (Exception var32) {
                                        log.info(var32.getStackTrace());
                                        var32.printStackTrace();
                                    }
                                }
                            }
                            break;
                        case 7:
                        case 200:
                            dest = current.element(propertyName);
                            if (dest != null) {
                                setMethod = classInfo.getSetter(propertyName);

                                try {
                                    setfield = object.getClass().getDeclaredField(propertyName);
                                    setfield.setAccessible(true);
                                    setfield.set(object, Boolean.parseBoolean(dest.getTextTrim()));
                                } catch (Exception var29) {
                                    try {
                                        setMethod.invoke(object, Boolean.parseBoolean(dest.getTextTrim()));
                                    } catch (Exception var28) {
                                        log.info(var28.getStackTrace());
                                        var28.printStackTrace();
                                    }
                                }
                            }
                            break;
                        case 11:
                        case 21:
                            Field field = classInfo.getField(propertyName);
                            if (field != null) {
                                MessageAnnotation ma = (MessageAnnotation) field.getAnnotation(MessageAnnotation.class);
                                if (ma != null) {
                                    this.setMode(ma.mode());
                                    this.setNodeName(ma.nodeName());
                                    this.setSign(ma.sign());
                                }
                            }

                            nodes = classInfo.getFieldGenericTypes(propertyName);
                            if (nodes == null || nodes.size() < 1) {
                                break;
                            }

                            Class klazz = (Class) nodes.get(0);
//                            Method setMethod;
//                            Field setfield;
                            if (flag && this.getMode() == 1) {
                                setMethod = null;
                                nodes = current.elements(propertyName);
                                this.restoreDefault();
                                if (nodes == null || nodes.size() <= 0) {
                                    break;
                                }

                                List list = new Vector();
                                int s;
                                Element temp;
                                if (this.checkType(klazz) == 5) {
                                    for (s = 0; s < nodes.size(); ++s) {
                                        temp = (Element) nodes.get(s);
                                        ((List) list).add(temp.getTextTrim());
                                    }
                                } else {
                                    int k;
                                    Element temp2;
                                    List subNodes;
                                    if (this.checkType(klazz) == 305) {
                                        for (s = 0; s < nodes.size(); ++s) {
                                            temp = (Element) nodes.get(s);
                                            subNodes = temp.elements();
                                            List subList = new Vector();

                                            for (k = 0; k < subNodes.size(); ++k) {
                                                temp2 = (Element) subNodes.get(k);
                                                subList.add(temp2.getTextTrim());
                                            }

                                            ((List) list).add(subList.toArray(new String[subList.size()]));
                                        }
                                    } else {
                                        Object subList;
                                        if (this.checkType(klazz) == 12) {
                                            for (s = 0; s < nodes.size(); ++s) {
                                                temp = (Element) nodes.get(s);
                                                subNodes = temp.elements();
                                                subList = new Vector();

                                                for (k = 0; k < subNodes.size(); ++k) {
                                                    temp2 = (Element) subNodes.get(k);
                                                    subList = (ArrayList) this.fetchObject(temp2, List.class, flag);
                                                }

                                                ((List) list).add(subList);
                                            }
                                        } else if (this.checkType(klazz) == 11) {
                                            for (s = 0; s < nodes.size(); ++s) {
                                                temp = (Element) nodes.get(s);
                                                subNodes = temp.elements();
                                                subList = new Vector();

                                                for (k = 0; k < subNodes.size(); ++k) {
                                                    temp2 = (Element) subNodes.get(k);
                                                    subList = (List) this.fetchObject(temp2, List.class, flag);
                                                }

                                                ((List) list).add(subList);
                                            }
                                        } else {
                                            list = this.fetchObjectList(nodes, klazz, true);
                                        }
                                    }
                                }

                                setMethod = classInfo.getSetter(propertyName);

                                try {
                                    setfield = object.getClass().getDeclaredField(propertyName);
                                    setfield.setAccessible(true);
                                    setfield.set(object, list);
                                } catch (Exception var43) {
                                    try {
                                        setMethod.invoke(object, list);
                                    } catch (Exception var42) {
                                        log.info(var42.getStackTrace());
                                        var42.printStackTrace();
                                    }
                                }
                            } else if (flag && this.getMode() == 2) {
                                this.restoreDefault();
                                Element listElement = current.element(propertyName);
                                if (listElement != null) {
                                    List list = this.fetchObjectList(listElement.selectNodes("row"), klazz, true);
                                    setMethod = classInfo.getSetter(propertyName);

                                    try {
                                        setfield = object.getClass().getDeclaredField(propertyName);
                                        setfield.setAccessible(true);
                                        setfield.set(object, list);
                                    } catch (Exception var41) {
                                        try {
                                            setMethod.invoke(object, list);
                                        } catch (Exception var40) {
                                            log.info(var40.getStackTrace());
                                            var40.printStackTrace();
                                        }
                                    }
                                }
                            }
                            break;
                        case 12:
                            Field afield = classInfo.getField(propertyName);
                            if (afield != null) {
                                MessageAnnotation ma = (MessageAnnotation) afield.getAnnotation(MessageAnnotation.class);
                                if (ma != null) {
                                    this.setMode(ma.mode());
                                    this.setNodeName(ma.nodeName());
                                    this.setSign(ma.sign());
                                }
                            }

                            nodes = classInfo.getFieldGenericTypes(propertyName);
                            if (nodes != null && nodes.size() >= 1) {
                                klazz = (Class) nodes.get(0);
                                ArrayList list;
                                if (flag && this.getMode() == 1) {
                                    setMethod = null;
                                    nodes = current.elements(propertyName);
                                    this.restoreDefault();
                                    if (nodes != null && nodes.size() > 0) {
                                        list = new ArrayList();
                                        int s;
                                        Element temp;
                                        if (this.checkType(klazz) == 5) {
                                            for (s = 0; s < nodes.size(); ++s) {
                                                temp = (Element) nodes.get(s);
                                                list.add(temp.getTextTrim());
                                            }
                                        } else {
                                            List subNodes;
                                            int k;
                                            Element temp2;
                                            if (this.checkType(klazz) == 305) {
                                                for (s = 0; s < nodes.size(); ++s) {
                                                    temp = (Element) nodes.get(s);
                                                    subNodes = temp.elements();
                                                    ArrayList subList = new ArrayList();

                                                    for (k = 0; k < subNodes.size(); ++k) {
                                                        temp2 = (Element) subNodes.get(k);
                                                        subList.add(temp2.getTextTrim());
                                                    }

                                                    list.add(subList.toArray(new String[subList.size()]));
                                                }
                                            } else {
                                                Object subList;
                                                if (this.checkType(klazz) == 12) {
                                                    for (s = 0; s < nodes.size(); ++s) {
                                                        temp = (Element) nodes.get(s);
                                                        subNodes = temp.elements();
                                                        subList = new Vector();

                                                        for (k = 0; k < subNodes.size(); ++k) {
                                                            temp2 = (Element) subNodes.get(k);
                                                            subList = (ArrayList) this.fetchObject(temp2, List.class, flag);
                                                        }

                                                        list.add(subList);
                                                    }
                                                } else if (this.checkType(klazz) == 11) {
                                                    for (s = 0; s < nodes.size(); ++s) {
                                                        temp = (Element) nodes.get(s);
                                                        subNodes = temp.elements();
                                                        subList = new Vector();

                                                        for (k = 0; k < subNodes.size(); ++k) {
                                                            temp2 = (Element) subNodes.get(k);
                                                            subList = (List) this.fetchObject(temp2, List.class, flag);
                                                        }

                                                        list.add(subList);
                                                    }
                                                } else {
                                                    list = (ArrayList) this.fetchObjectList(nodes, klazz, true);
                                                }
                                            }
                                        }

                                        setMethod = classInfo.getSetter(propertyName);

                                        try {
                                            setfield = object.getClass().getDeclaredField(propertyName);
                                            setfield.setAccessible(true);
                                            setfield.set(object, list);
                                        } catch (Exception var47) {
                                            try {
                                                setMethod.invoke(object, list);
                                            } catch (Exception var46) {
                                                log.info(var46.getStackTrace());
                                                var46.printStackTrace();
                                            }
                                        }
                                    }
                                } else if (flag && this.getMode() == 2) {
                                    this.restoreDefault();
                                    Element listElement = current.element(propertyName);
                                    if (listElement != null) {
                                        list = (ArrayList) this.fetchObjectList(listElement.selectNodes("row"), klazz, true);
                                        setMethod = classInfo.getSetter(propertyName);

                                        try {
                                            setfield = object.getClass().getDeclaredField(propertyName);
                                            setfield.setAccessible(true);
                                            setfield.set(object, list);
                                        } catch (Exception var45) {
                                            try {
                                                setMethod.invoke(object, list);
                                            } catch (Exception var44) {
                                                log.info(var44.getStackTrace());
                                                var44.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        case 14:
                        case 16:
                        case 17:
                        case 22:
                        case 98:
                        case 100:
                            break;
                        case 99:
                            dest = current.element(propertyName);
                            if (dest != null) {
                                Object tmp = this.fetchObject(dest, attrClass, flag);
                                if (tmp != null) {
                                    setMethod = classInfo.getSetter(propertyName);

                                    try {
                                        setfield = object.getClass().getDeclaredField(propertyName);
                                        setfield.setAccessible(true);
                                        setfield.set(object, tmp);
                                    } catch (Exception var39) {
                                        try {
                                            setMethod.invoke(object, tmp);
                                        } catch (Exception var38) {
                                            log.info(var38.getStackTrace());
                                            var38.printStackTrace();
                                        }
                                    }
                                }
                            }
                            break;
                        case 305:
                            Field sfield = classInfo.getField(propertyName);
                            if (sfield != null) {
                                MessageAnnotation ma = (MessageAnnotation) sfield.getAnnotation(MessageAnnotation.class);
                                if (ma != null) {
                                    this.setMode(ma.mode());
                                    this.setNodeName(ma.nodeName());
                                    this.setSign(ma.sign());
                                }
                            }

                            List<Class> sList = classInfo.getFieldGenericTypes(propertyName);
                            if (sList == null || sList.size() < 1) {
                                break;
                            }

                            klazz = (Class) sList.get(0);
                            Element listElement;
                            ArrayList list;
//                            Field setfield;
                            if (flag && this.getMode() == 1) {
                                listElement = null;
                                nodes = current.elements(propertyName);
                                this.restoreDefault();
                                if (nodes == null || nodes.size() <= 0) {
                                    break;
                                }

                                list = new ArrayList();
                                if (this.checkType(klazz) == 5) {
                                    for (int s = 0; s < nodes.size(); ++s) {
                                        Element temp = (Element) nodes.get(s);
                                        list.add(temp.getTextTrim());
                                    }
                                } else {
                                    list = (ArrayList) this.fetchObjectList(nodes, klazz, true);
                                }

                                setMethod = classInfo.getSetter(propertyName);

                                try {
                                    setfield = object.getClass().getDeclaredField(propertyName);
                                    setfield.setAccessible(true);
                                    setfield.set(object, list.toArray(new String[list.size()]));
                                } catch (Exception var51) {
                                    try {
                                        setMethod.invoke(object, list.toArray(new String[list.size()]));
                                    } catch (Exception var50) {
                                        log.info(var50.getStackTrace());
                                        var50.printStackTrace();
                                    }
                                }
                            } else if (flag && this.getMode() == 2) {
                                this.restoreDefault();
                                listElement = current.element(propertyName);
                                if (listElement != null) {
                                    list = (ArrayList) this.fetchObjectList(listElement.selectNodes("item"), klazz, true);
                                    setMethod = classInfo.getSetter(propertyName);

                                    try {
                                        setfield = object.getClass().getDeclaredField(propertyName);
                                        setfield.setAccessible(true);
                                        setfield.set(object, list.toArray(new String[list.size()]));
                                    } catch (Exception var49) {
                                        try {
                                            setMethod.invoke(object, list.toArray(new String[list.size()]));
                                        } catch (Exception var48) {
                                            log.info(var48.getStackTrace());
                                            var48.printStackTrace();
                                        }
                                    }
                                }
                            }
                            break;
                        default:
                            dest = current.element(propertyName);
                            if (dest != null) {
                                setMethod = classInfo.getSetter(propertyName);

                                try {
                                    setfield = object.getClass().getDeclaredField(propertyName);
                                    setfield.setAccessible(true);
                                    setfield.set(object, dest.getTextTrim());
                                } catch (Exception var27) {
                                    try {
                                        setMethod.invoke(object, dest.getTextTrim());
                                    } catch (Exception var26) {
                                        log.info(var26.getStackTrace());
                                        java.lang.System.out.println("propertyName:" + propertyName);
                                        var26.printStackTrace();
                                    }
                                }
                            }
                    }
                }
            }

            classInfo = null;
        } catch (Exception var52) {
            var52.printStackTrace();
            log.error("fuck:" + var52);
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
