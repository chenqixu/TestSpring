package com.cqx.testspring.webservice.common.util.xml;

import org.dom4j.Document;

import java.util.List;

/**
 * MessageHelperer
 *
 * @author chenqixu
 */
public interface MessageHelperer {
    int MODE_ONE = 1;
    int MODE_TWO = 2;
    String DEFAULT_LIST_NODE_NAME = "row";
    String DEFAULT_ARRAY_NODE_NAME = "item";

    void setMode(int var1);

    int getMode();

    void appendValue(String var1, String var2, Object var3);

    void appendCDATAValue(String var1, String var2, Object var3);

    void appendValueList(String var1, String var2, List var3);

    void appendObject(String var1, String var2, Object var3);

    void appendObject(String var1, String var2, Object var3, boolean var4);

    Object fetchObject(String var1, Class var2);

    Object fetchObject(String var1, Class var2, boolean var3);

    List fetchObjectList(String var1, Class var2);

    List fetchObjectList(String var1, Class var2, boolean var3);

    String fetchValue(String var1);

    List fetchValueList(String var1);

    Document getDocument();

    void setDocument(Document var1);

    boolean isValid();

    String getErrorMsg();

    Object fetchObject(Class var1);

    Object fetchObject(Class var1, boolean var2);
}
