package com.cqx.testspring.webservice.common.util.other;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * StringText
 *
 * @author chenqixu
 */
public class StringText {
    private static String server_name = "";
    private static String js_version = null;

    public StringText() {
    }

    public static int Str2Int(String str) {
        int k = -1;

        try {
            if (str != null && str.trim().length() > 0) {
                k = Integer.parseInt(str);
            }
        } catch (Exception var3) {
        }

        return k;
    }

    public static Integer Str2Integer(String str) {
        Integer k = null;

        try {
            k = Integer.valueOf(str);
        } catch (Exception var3) {
        }

        return k;
    }

    public static Long Str2Long(String str) {
        Long k = null;

        try {
            k = Long.valueOf(str);
        } catch (Exception var3) {
        }

        return k;
    }

    public static Long Str2Long(String str, String default_long) {
        Long k = null;

        try {
            k = Long.valueOf(str);
        } catch (Exception var4) {
        }

        if (k == null) {
            k = Str2Long(default_long);
        }

        return k;
    }

    public static BigDecimal Str2BigDecimal(String str) {
        BigDecimal k = null;

        try {
            k = new BigDecimal(str);
        } catch (Exception var3) {
        }

        return k;
    }

    public static Double Str2Double(String str) {
        Double d = null;

        try {
            d = Double.valueOf(str);
        } catch (Exception var3) {
        }

        return d;
    }

    public Float Str2Float(String str) {
        Float f = null;

        try {
            f = Float.valueOf(str);
        } catch (Exception var4) {
        }

        return f;
    }

    public static Long BigDec2Long(BigDecimal bd) {
        Long l = null;

        try {
            if (bd != null) {
                l = new Long(bd.longValue());
            }
        } catch (Exception var3) {
        }

        return l;
    }

    public static Double BigDec2Double(BigDecimal bd) {
        Double d = null;

        try {
            if (bd != null) {
                d = new Double(bd.doubleValue());
            }
        } catch (Exception var3) {
        }

        return d;
    }

    public static String getStr(String str) {
        return getStr(str, "");
    }

    public static String getStr(String str, String rtr) {
        String v = "";
        if (str != null && str.trim().length() > 0) {
            v = str.trim();
        } else {
            v = rtr;
        }

        return v;
    }

    public static String getStr(Integer i) {
        return getStr(i, "");
    }

    public static String getStr(Integer i, String rtr) {
        String v = "";
        if (i != null) {
            v = i.toString();
        } else {
            v = rtr;
        }

        return v;
    }

    public static String getStr(Long i) {
        return getStr(i, "");
    }

    public static String getStr(Long i, String str) {
        String v = "";
        if (i != null) {
            v = i.toString();
        } else {
            v = str;
        }

        return v;
    }

    public static String getStr(BigDecimal bd, String rtr) {
        String v = "";
        if (bd != null) {
            v = bd.toString();
        } else {
            v = rtr;
        }

        return v;
    }

    public static String getStr(BigDecimal bd) {
        return getStr(bd, "");
    }

    public static String getStr(Double d, String str) {
        String v = "";
        if (d != null) {
            v = d.toString();
        } else {
            v = str;
        }

        return v;
    }

    public static String getStr(Double d) {
        return getStr(d, "");
    }

    public static String getStr(double d) {
        return getStr(d, "");
    }

    public static String getStr(double d, String text) {
        String str = text;

        try {
            str = String.valueOf(d);
        } catch (Exception var5) {
        }

        return str;
    }

    public static String getStr(long l) {
        return getStr(l, "");
    }

    public static String getStr(long l, String text) {
        String str = text;

        try {
            str = String.valueOf(l);
        } catch (Exception var5) {
        }

        return str;
    }

    public static String getStr(HashMap map, String name) {
        String str = "";
        str = (String) map.get(name);
        if (str == null) {
            str = "";
        }

        return str;
    }

    public String getStr(Object o) {
        if (o != null) {
            String str = (String) o;
            return str;
        } else {
            return "";
        }
    }

    public static String getStr(Object o, String type) {
        if (o != null && type != null && type.trim().length() != 0) {
            String str = "";
            if (type != null && type.trim().equals("class java.lang.String")) {
                str = getStr((String) o);
            } else if (type != null && type.trim().equals("class java.lang.Integer")) {
                str = getStr((Integer) o);
            } else if (type != null && type.trim().equals("class java.lang.Long")) {
                str = getStr((Long) o);
            } else if (type != null && type.trim().equals("class java.util.Date")) {
                str = getStr((Date) o);
            } else if (type != null && type.trim().equals("class java.lang.Double")) {
                str = getStr((Double) o);
            } else if (type != null && type.trim().equals("class java.math.BigDecimal")) {
                str = getStr((BigDecimal) o);
            } else if (type != null && type.trim().equals("int")) {
                str = getStr((Integer) o);
            } else if (type != null && type.trim().equals("long")) {
                str = getStr((Long) o);
            } else if (type != null && type.trim().equals("double")) {
                str = getStr((Double) o);
            }

            return str;
        } else {
            return "";
        }
    }

    public static String getStr(Date date, String fmtstr, String str) {
        String v = "";
        if (date != null) {
            String fmtStr = fmtstr;
            if (fmtstr == null || fmtstr.trim().length() == 0) {
                fmtStr = "yyyy-MM-dd";
            }

            SimpleDateFormat format = new SimpleDateFormat(fmtStr);
            v = format.format(date);
        } else {
            v = str;
        }

        return v;
    }

    public static String getStr(Date date) {
        return getStr(date, "yyyy-MM-dd");
    }

    public static String getStr(Date date, String fmtstr) {
        return getStr(date, fmtstr, "");
    }

    public static Date Str2Date(String date_str) {
        return Str2Date(date_str, "yyyy-MM-dd");
    }

    public static Date Str2Date(String date_str, String fmtStr) {
        Date date = null;
        if (date_str != null) {
            if (fmtStr != null && fmtStr.trim().length() != 0) {
                if (date_str.trim().length() <= 10) {
                    fmtStr = "yyyy-MM-dd";
                }
            } else {
                fmtStr = "yyyy-MM-dd";
            }

            SimpleDateFormat format = new SimpleDateFormat(fmtStr);

            try {
                date = format.parse(date_str);
            } catch (ParseException var5) {
            }
        }

        return date;
    }

    public static java.sql.Date Str2SqlDate(String date_str) {
        return Str2SqlDate(date_str, (String) null);
    }

    public static java.sql.Date Str2SqlDate(String date_str, String fmtStr) {
        java.sql.Date date = null;
        if (date_str != null) {
            if (fmtStr == null || fmtStr.trim().length() == 0) {
                fmtStr = "yyyy-MM-dd";
            }

            SimpleDateFormat format = new SimpleDateFormat(fmtStr);

            try {
                Date u_date = format.parse(date_str);
                date = new java.sql.Date(u_date.getTime());
            } catch (ParseException var5) {
            }
        }

        return date;
    }

    public static String[] Str2Array(String rstr, String flag) {
        Vector vec = new Vector();
        String v_value = "";
        StringTokenizer st = new StringTokenizer(rstr, flag);

        while (st.hasMoreTokens()) {
            v_value = st.nextToken();
            vec.addElement(v_value);
        }

        String[] strResult = new String[vec.size()];
        vec.copyInto(strResult);
        return strResult;
    }

    public static String[] Str2ArrByStr(String rstr, String flagstr) {
        Vector vec = new Vector();
        String temp = rstr;

        for (int k = rstr.indexOf(flagstr); k != -1; k = temp.indexOf(flagstr)) {
            vec.add(temp.substring(0, k).trim());
            temp = temp.substring(k + flagstr.length(), temp.length());
        }

        if (temp != null && temp.trim().length() > 0) {
            vec.add(temp.trim());
        }

        String[] strResult = new String[vec.size()];
        vec.copyInto(strResult);
        return strResult;
    }

    public static String stringReplace(String str, String str1, String str2) {
        StringBuffer strbuff = new StringBuffer();

        try {
            if (str != null && str.length() != 0) {
                for (int intL = str.indexOf(str1); intL != -1; intL = str.indexOf(str1)) {
                    strbuff.append(str.substring(0, intL) + str2);
                    str = str.substring(intL + str1.length(), str.length());
                }

                if (str != null && str.length() != 0) {
                    strbuff.append(str.substring(0, str.length()));
                }
            }
        } catch (Exception var5) {
            System.out.print(var5.toString());
        }

        return strbuff.toString();
    }

    public static String stringReplaceFirst(String str, String str1, String str2) {
        StringBuffer strbuff = new StringBuffer();

        try {
            if (str != null && str.length() != 0) {
                int intL = str.indexOf(str1);
                if (intL != -1) {
                    strbuff.append(str.substring(0, intL) + str2);
                    str = str.substring(intL + str1.length(), str.length());
                }

                if (str != null && str.length() != 0) {
                    strbuff.append(str.substring(0, str.length()));
                }
            }
        } catch (Exception var5) {
            System.out.print(var5.toString());
        }

        return strbuff.toString();
    }

    public static String stringReplaceLast(String str, String str1, String str2) {
        StringBuffer strbuff = new StringBuffer();

        try {
            if (str != null && str.length() != 0) {
                int intL = str.lastIndexOf(str1);
                if (intL != -1) {
                    strbuff.append(str.substring(0, intL) + str2);
                    str = str.substring(intL + str1.length(), str.length());
                }

                if (str != null && str.length() != 0) {
                    strbuff.append(str.substring(0, str.length()));
                }
            }
        } catch (Exception var5) {
            System.out.print(var5.toString());
        }

        return strbuff.toString();
    }

    public static String replace_special(String v_str) {
        v_str = stringReplace(v_str, "\"", "“");
        v_str = stringReplace(v_str, "\\", "＼");
        return v_str;
    }

    public static String str2GBK(String str) {
        String re_str = str;

        try {
            if (str != null) {
                if (server_name == null || server_name.trim().length() == 0) {
                    server_name = ParameterConfig.getValue("WEB服务容器");
                    if (server_name != null && server_name.trim().length() > 0) {
                        server_name = server_name.toUpperCase();
                    }
                }

                if (server_name != null && !server_name.trim().equals("TONGWEB")) {
                    re_str = new String(str.getBytes("ISO-8859-1"), "GBK");
                }
            }
        } catch (Exception var3) {
        }

        return re_str;
    }

    public static String str2ISO8859(String str) {
        String re_str = str;

        try {
            if (str != null) {
                if (server_name == null || server_name.trim().length() == 0) {
                    server_name = ParameterConfig.getValue("WEB服务容器");
                    if (server_name != null && server_name.trim().length() > 0) {
                        server_name = server_name.toUpperCase();
                    }
                }

                if (server_name != null && !server_name.trim().equals("TONGWEB")) {
                    re_str = new String(str.getBytes(), "ISO-8859-1");
                }
            }
        } catch (Exception var3) {
        }

        return re_str;
    }

    public static String str2Encode(String str, String type) {
        if (str != null && type != null) {
            String re_str = str;
            if (type.trim().toUpperCase().equals("GBK")) {
                re_str = str2GBK(str);
            } else if (type.trim().toUpperCase().equals("ISO-8859-1")) {
                re_str = str2ISO8859(str);
            }

            return re_str;
        } else {
            return "";
        }
    }

    public static String convertStr(String str, String convert_char) {
        return convertStr(str, convert_char, (String) null);
    }

    public static String convertStr(String str, String convert_char, String byte_char) {
        String re_str = str;

        try {
            if (isStrNotNull(str) && isStrNotNull(convert_char)) {
                if (isStrNotNull(byte_char)) {
                    re_str = new String(str.getBytes(byte_char), convert_char);
                } else {
                    re_str = new String(str.getBytes(), convert_char);
                }
            }
        } catch (Exception var5) {
        }

        return re_str;
    }

    public String removeRepeatStr(String diviStr, String str, String returnType) {
        String strResult = "";
        boolean flag = false;

        try {
            String[] op_Str = str.split(diviStr);
//            int op_StrIndex = false;

            for (int i = 0; i < op_Str.length; ++i) {
                for (int j = i + 1; j < op_Str.length; ++j) {
                    if (op_Str[i].compareTo(op_Str[j]) == 0) {
                        flag = true;
//                        int len = false;
                        int op_StrIndex = str.indexOf(op_Str[i] + diviStr);
                        if (returnType.compareTo("1") == 0) {
                            int len;
                            if (op_StrIndex != 0) {
                                len = op_StrIndex + op_Str[i].length() + 1;
                                strResult = str.substring(0, op_StrIndex) + str.substring(len, str.length());
                            } else {
                                len = op_Str[i].length() + 1;
                                strResult = str.substring(len, str.length());
                            }

                            str = strResult;
                        } else if (returnType.compareTo("2") == 0) {
                            strResult = strResult + op_Str[i] + diviStr;
                        }
                        break;
                    }
                }
            }

            if (!flag) {
                strResult = str;
            }

            if (returnType.compareTo("2") == 0) {
                strResult = this.removeRepeatStr(diviStr, strResult, "1");
            }
        } catch (Exception var11) {
            strResult = str;
        }

        return strResult;
    }

    public static String getRandomStr(int n) {
        StringBuffer buf = new StringBuffer();
        Random r = new Random();
        int i = 0;

        while (true) {
            int c;
            do {
                if (i >= n) {
                    return buf.toString();
                }

                c = r.nextInt(122);
            } while ((c <= 48 || c >= 58) && (c <= 64 || c >= 91) && (c <= 96 || c >= 123));

            if (c != 79 && c != 111) {
                buf.append((char) c);
                ++i;
            }
        }
    }

    public static String getRandomNum(int n) {
        StringBuffer buf = new StringBuffer();
        Random r = new Random();
        int i = 0;

        while (i < n) {
            int c = r.nextInt(122);
            if (c > 47 && c < 58) {
                System.out.print((char) c);
                buf.append((char) c);
                ++i;
            }
        }

        return buf.toString();
    }

    public static boolean isStrNotNull(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static boolean isStrNull(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNumNotNull(Integer i) {
        return i != null;
    }

    public static boolean equalsString(String str, String equ_str) {
        boolean flag = false;

        try {
            if (isStrNotNull(str) && isStrNotNull(equ_str) && str.trim().equals(equ_str.trim())) {
                flag = true;
            }
        } catch (Exception var4) {
        }

        return flag;
    }

    public static boolean equalsStringByLower(String str, String equ_str) {
        boolean flag = false;

        try {
            if (isStrNotNull(str) && isStrNotNull(equ_str) && str.trim().toLowerCase().equals(equ_str.trim().toLowerCase())) {
                flag = true;
            }
        } catch (Exception var4) {
        }

        return flag;
    }

    public static boolean equalsStringByUpper(String str, String equ_str) {
        boolean flag = false;

        try {
            if (isStrNotNull(str) && isStrNotNull(equ_str) && str.trim().toUpperCase().equals(equ_str.trim().toUpperCase())) {
                flag = true;
            }
        } catch (Exception var4) {
        }

        return flag;
    }

    public static String getJsVersion() {
        if (js_version == null) {
            js_version = Guid.generate();
        }

        return js_version;
    }

    public static String createRandomSeq() {
        String seq_random = "";

        try {
            String cct_key = getCctEncryptKey();
            seq_random = getRandomStr(3) + cct_key + System.currentTimeMillis();
            // TODO: 2020/2/28  NlEncryptUtil
//            seq_random = NlEncryptUtil.encryptByAES(seq_random);
        } catch (Exception var2) {
        }

        return seq_random;
    }

    public static String paresRandomSeq(String seq_random) {
        String timeMillis = "";

        try {
            if (isStrNotNull(seq_random)) {
                // TODO: 2020/2/28  NlEncryptUtil
//                seq_random = NlEncryptUtil.decryptByAES(seq_random);
                String cct_key = getCctEncryptKey();
                int key_length = 0;
                if (isStrNotNull(cct_key)) {
                    key_length = cct_key.length();
                }

                if (seq_random.length() > key_length + 3) {
                    timeMillis = seq_random.substring(key_length + 3);
                }
            }
        } catch (Exception var4) {
        }

        return timeMillis;
    }

    public static String getCctEncryptKey() {
        String cct_key = "";

        try {
            cct_key = ParameterConfig.getValue("CCT_ENCRYPT_KEY");
        } catch (Exception var2) {
        }

        if (isStrNull(cct_key)) {
            cct_key = "_cct+nl=fmcc-";
        }

        System.out.println("  cct_key:" + cct_key);
        return cct_key;
    }

    public static void main(String[] args) {
        Double label_ratio = Str2Double("0.33");
        System.out.println("========label_ratio:" + label_ratio);
        if (label_ratio == null) {
            label_ratio = 0.66D;
        }

        long k = Math.round(13.0D * label_ratio);
        System.out.println("========k:" + k);
        System.out.println("========getJsVersion:" + getJsVersion());
        System.out.println("========getRandomSeq:" + createRandomSeq());
        System.out.println("========paresRandomSeq:" + paresRandomSeq("vShziR3lhuM+okRofRlWgnEePV37iw6jnBtKNAOeZM0="));
    }
}
