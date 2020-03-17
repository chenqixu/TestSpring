package com.cqx.testspring.webservice.common.util.other;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

/**
 * ParameterConfig
 *
 * @author chenqixu
 */
public class ParameterConfig {
    private static final Logger logger = LoggerFactory.getLogger(ParameterConfig.class);
    private static HashMap<String, String> hashmap = new HashMap();

    public ParameterConfig() {
    }

    public static void doParse() {
        SAXReader reader = new SAXReader();
        Document doc = null;
        URL url = Thread.currentThread().getContextClassLoader().getResource("resources/config/parameter_config.xml");
        if (url == null) {
            url = Thread.currentThread().getContextClassLoader().getResource("resources/config");
            logger.error("文件无法找到：" + url + "/parameter_config.xml，初始化ParameterConfig出错：" + ParameterConfig.class.getName());
        } else {
            try {
                doc = reader.read(url);
            } catch (DocumentException var12) {
                logger.error("解析parameter_config.xml文件失败", var12);
            }

            Element root = doc.getRootElement();
            String tmp_value = "";
            String tmp_name = "";
            String is_encode = "";
            String sainfo_path = "";
            String sainfo_name = "";
            String sainfo_type = "";

            for (Iterator i = root.elementIterator(); i.hasNext(); hashmap.put(tmp_name, tmp_value)) {
                Element element = (Element) i.next();
                tmp_name = element.elementText("name");
                tmp_value = element.elementText("value");
                is_encode = element.elementText("is_encode");
                sainfo_path = element.elementText("sainfo_path");
                sainfo_name = element.elementText("sainfo_name");
                sainfo_type = element.elementText("sainfo_type");
                //DES加密
                if (is_encode != null && is_encode.toUpperCase().trim().equals("Y")) {
                    tmp_value = EncryptUtil.decrypt(tmp_value);
                }
                //NLSam加密
//                if (sainfo_path != null && sainfo_path.trim().length() > 0) {
//                    if (sainfo_type != null && sainfo_type.trim().toLowerCase().equals("name")) {
//                        tmp_value = NlSamToolkit.parseSainfoUser(sainfo_path, sainfo_name);
//                    } else if (sainfo_type != null && sainfo_type.trim().toLowerCase().equals("pwd")) {
//                        tmp_value = NlSamToolkit.parseSainfoPwd(sainfo_path, sainfo_name);
//                    }
//                }
            }

        }
    }

    public static String getValue(String name) {
        if (hashmap == null || hashmap.isEmpty()) {
            doParse();
        }

        String str = (String) hashmap.get(name);
        if (str == null) {
            str = "";
        }

        return str;
    }

    public static boolean getBooleanValue(String name) {
        boolean b = false;
        if (hashmap == null || hashmap.isEmpty()) {
            doParse();
        }

        String str = (String) hashmap.get(name);
        if (str != null && str.trim().equals("TRUE")) {
            b = true;
        }

        return b;
    }
}
