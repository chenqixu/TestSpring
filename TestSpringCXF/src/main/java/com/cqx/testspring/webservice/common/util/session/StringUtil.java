package com.cqx.testspring.webservice.common.util.session;

import com.cqx.testspring.webservice.common.util.other.ParameterConfig;
import com.cqx.testspring.webservice.common.util.xml.XMLUtil;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.*;

/**
 * StringUtil
 *
 * @author chenqixu
 */
public class StringUtil {
    public static final String nullString = "null";

    public StringUtil() {
    }

    public static void replaceStr(StringBuffer sb, String sStr, String sRepStr) {
        try {
            if (sb == null || sStr == null || sRepStr == null) {
                return;
            }

            if (sb.length() == 0 || sStr.length() == 0) {
                return;
            }

            int iStartIndex = 0;
            int iLen = sb.length();
            int iLen2 = sStr.length();

            while(iStartIndex < iLen) {
                if (sb.substring(iStartIndex, iLen2 + iStartIndex).equals(sStr)) {
                    sb.replace(iStartIndex, iLen2 + iStartIndex, sRepStr);
                    iLen = sb.length();
                    iStartIndex += sRepStr.length();
                } else {
                    ++iStartIndex;
                }
            }
        } catch (Exception var6) {
        }

    }

    public static String replaceStr(String sSrcStr, String sStr, String sRepStr) {
        try {
            if (sSrcStr != null && sStr != null && sRepStr != null) {
                if (sSrcStr.length() != 0 && sStr.length() != 0) {
                    StringBuffer sb = new StringBuffer(sSrcStr);
                    replace(sb, sStr, sRepStr);
                    return new String(sb);
                } else {
                    return sSrcStr;
                }
            } else {
                return sSrcStr;
            }
        } catch (Exception var4) {
            return sSrcStr;
        }
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static String filterHTMLTag(String input) {
        StringBuffer buffer = new StringBuffer(input.length());

        for(int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            if (c == '<') {
                buffer.append("&lt;");
            } else if (c == '>') {
                buffer.append("&gt;");
            } else if (c == '"') {
                buffer.append("&quot;");
            } else if (c == '+') {
                buffer.append("&#43;");
            } else if (c == '&') {
                buffer.append("&amp;");
            } else {
                buffer.append(c);
            }
        }

        return buffer.toString();
    }

    public static int getDays(int mon) {
        int mon_mm = mon % 100;
        int mon_yyyy = mon / 100;
        byte count;
        if (mon_mm < 13) {
            if ((mon_yyyy % 400 == 0 || mon_yyyy % 4 == 0 && mon_yyyy % 100 > 0) && mon_mm == 2) {
                count = 29;
            } else if (mon_mm != 1 && mon_mm != 3 && mon_mm != 5 && mon_mm != 7 && mon_mm != 8 && mon_mm != 10 && mon_mm != 12) {
                if (mon_mm != 4 && mon_mm != 6 && mon_mm != 9 && mon_mm != 11) {
                    count = 28;
                } else {
                    count = 30;
                }
            } else {
                count = 31;
            }
        } else {
            count = 1;
        }

        return count;
    }

    public static String convertStr(String s) {
        String newStr;
        if (s.indexOf("'") != -1) {
            newStr = s.replaceAll("\\'", "''");
        } else {
            newStr = s;
        }

        return newStr;
    }

    public static String convertString(String msg) {
        try {
            String convertMsg = new String(msg.getBytes("ISO-8859-1"), "GBK");
            return convertMsg;
        } catch (UnsupportedEncodingException var3) {
            String s1 = "can't not encoding code";
            return s1;
        }
    }

    public static String getCookieValue(String cookieName, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return "";
        } else {
            for(int i = 0; i < cookies.length; ++i) {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }

            return "";
        }
    }

    public static void setCookie(Cookie cookie, int maxAge, HttpServletResponse res) {
        cookie.setMaxAge(maxAge);
        res.addCookie(cookie);
    }

    public static boolean isSetCookie(String cookieName, HttpServletRequest req) {
        return getCookieValue(cookieName, req) != null;
    }

    private static String toHexString(int i) {
        String sTemp = Integer.toHexString(i).toUpperCase();
        return sTemp.length() >= 2 ? sTemp : "0".concat(String.valueOf(String.valueOf(sTemp)));
    }

    public static String bindPassword(String pass) {
        String sRet = "";
        int strLen = pass.length();

        int i;
        for(i = 0; i < strLen; ++i) {
            String sTemp = toHexString(pass.charAt(i) + "FMCCABT".charAt(i % 4));
            if (sTemp.length() != 2) {
                sTemp = String.valueOf(String.valueOf((new StringBuffer("")).append(i + 1).append(sTemp)));
            }

            sRet = sRet + sTemp;
        }

        for(i = 0; i < 15 - strLen; ++i) {
            sRet = sRet + toHexString("FMCCABT".charAt(i % 4) + strLen);
        }

        sRet = sRet + toHexString(strLen + 6);
        return sRet;
    }

    private static int hexStringToInt(String sHex) {
        try {
            int i = Integer.parseInt(sHex, 16);
            return i;
        } catch (NumberFormatException var3) {
            int j = 0;
            return j;
        }
    }

    public static String unBindPassword(String pass) {
        if (pass.length() != 32) {
            return "";
        } else {
            String sTemp = pass.substring(30, 32);
            int strLen = hexStringToInt(sTemp) - 6;
            if (strLen > 30) {
                return "";
            } else {
                String sRet = "";

                for(int i = 0; i < strLen; ++i) {
                    sTemp = pass.substring(i * 2, i * 2 + 2);
                    sRet = sRet + String.valueOf((char)(hexStringToInt(sTemp) - "FMCCABT".charAt(i % 4)));
                }

                return sRet;
            }
        }
    }

    public static String formatDateNumber(long dt) {
        new String();
        String datestr = String.valueOf(dt);
        String ret = "";
        SimpleDateFormat sdf;
        ParsePosition pp;
        Date d;
        SimpleDateFormat sd;
        if (datestr.length() == 8) {
            sdf = new SimpleDateFormat("yyyyMMdd");
            pp = new ParsePosition(0);
            d = sdf.parse(datestr, pp);
            sd = new SimpleDateFormat("yyyy-MM-dd");
            ret = sd.format(d);
        } else if (datestr.length() == 6) {
            sdf = new SimpleDateFormat("yyyyMM");
            pp = new ParsePosition(0);
            d = sdf.parse(datestr, pp);
            sd = new SimpleDateFormat("yyyy-MM");
            ret = sd.format(d);
        } else {
            ret = "format error";
        }

        return ret;
    }

    public static String formatPercent(double per) {
        String ret = "";
        NumberFormat nf = NumberFormat.getPercentInstance(Locale.PRC);
        nf.setMaximumFractionDigits(3);
        nf.setMinimumFractionDigits(1);
        ret = nf.format(per);
        return ret;
    }

    public static String decodeError(int errid) {
        String ret = "";
        switch(errid) {
            case 0:
                ret = "Waiting...";
                break;
            case 1:
                ret = "Running...";
                break;
            case 2:
                ret = "Error!";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            default:
                ret = "Unknown";
                break;
            case 9:
                ret = "Normal";
        }

        return ret;
    }

    public static String decodeType(int type) {
        String ret = "";
        switch(type) {
            case 1:
                ret = "日";
                break;
            case 2:
                ret = "周";
                break;
            case 3:
            case 4:
            default:
                ret = "其他";
                break;
            case 5:
                ret = "月";
        }

        return ret;
    }

    public static String decodeETLType(String type) {
        String ret = "";
        if (type.equals("D")) {
            ret = "日";
        } else if (type.equals("W")) {
            ret = "周";
        } else if (type.equals("M")) {
            ret = "月";
        } else if (type.equals("Y")) {
            ret = "年";
        } else {
            ret = "其他";
        }

        return ret;
    }

    public static boolean parseNumber(String param) {
        if (!param.equals("") && param != null) {
            switch(param.charAt(0)) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    return true;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public static String formatNumber(long num) {
//        Parameters p = new Parameters();
//        String result = Format.sprintf("%02d", p.add(num));
//        return result;
        return "";
    }

    public static String formatBirthday(String birthday) {
        String result = "";
        String yy = "";
        String mm = "";
        String dd = "";

        try {
            yy = birthday.substring(0, 4);
            mm = birthday.substring(4, 6);
            dd = birthday.substring(6, 8);
            result = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(yy)))).append("-").append(mm).append("-").append(dd)));
        } catch (Exception var6) {
            result = "";
        }

        return result;
    }

    public static String getBirthYear(String birthday) {
        return birthday.substring(0, 4);
    }

    public static String getBirthMonth(String birthday) {
        return birthday.substring(4, 6);
    }

    public static String getBirthDay(String birthday) {
        return birthday.substring(6, 8);
    }

    public static Document getDocument(String filePath) throws Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new File(filePath));
        return doc;
    }

    public static Document callCGI(String svrUrl, String strXML) throws Exception {
        URL url;
        try {
            url = new URL(svrUrl);
        } catch (MalformedURLException var16) {
            throw new Exception(var16);
        }

        try {
            HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
            httpUrlConn.setRequestMethod("POST");
            httpUrlConn.setDoInput(true);
            if (strXML != null && !strXML.equals("")) {
                httpUrlConn.setDoOutput(true);
                OutputStream sendStream = null;

                try {
                    sendStream = httpUrlConn.getOutputStream();
                    sendStream.write(strXML.getBytes());
                    sendStream.flush();
                    sendStream.close();
                } finally {
                    if (sendStream != null) {
                        try {
                            sendStream.close();
                        } catch (Exception var14) {
                        }
                    }

                }
            }

            int respCode = httpUrlConn.getResponseCode();
            if (respCode == 200) {
                try {
                    InputStream is = httpUrlConn.getInputStream();
                    Document document = XMLUtil.buildDocument(is);
                    return document;
                } catch (Exception var15) {
                    throw new Exception("parse xml error. ".concat(String.valueOf(String.valueOf(var15))));
                }
            } else {
                throw new Exception(String.valueOf(String.valueOf((new StringBuffer("HTTP Server Error, ")).append(respCode).append(": ").append(httpUrlConn.getResponseMessage()))));
            }
        } catch (IOException var18) {
            throw new Exception(var18);
        }
    }

    public static String htmlEscape(String sSourceString) {
        if (sSourceString == null) {
            return "";
        } else {
            String sDestString = sSourceString;

            try {
                sDestString = replace(sDestString, "\\", "\\\\");
                sDestString = replace(sDestString, "\"", "\\\"");
                sDestString = replace(sDestString, "'", "\\'");
                sDestString = replace(sDestString, "\r", "\\r");
                sDestString = replace(sDestString, "\n", "\\n");
                sDestString = replace(sDestString, "<", "&lt;");
                sDestString = replace(sDestString, ">", "&gt;");
                sDestString = replace(sDestString, "<br>", "\\r\\n");
                return sDestString;
            } catch (Exception var3) {
                return sSourceString;
            }
        }
    }

    public static String replace(String sSrcStr, String sStr, String sRepStr) {
        try {
            if (sSrcStr != null && sStr != null && sRepStr != null) {
                if (sSrcStr.length() != 0 && sStr.length() != 0) {
                    StringBuffer sb = new StringBuffer(sSrcStr);
                    replace(sb, sStr, sRepStr);
                    return new String(sb);
                } else {
                    return sSrcStr;
                }
            } else {
                return sSrcStr;
            }
        } catch (Exception var4) {
            return sSrcStr;
        }
    }

    public static void replace(StringBuffer sb, String sStr, String sRepStr) {
        try {
            if (sb == null || sStr == null || sRepStr == null) {
                return;
            }

            if (sb.length() == 0 || sStr.length() == 0) {
                return;
            }

            int iStartIndex = 0;
            int iLen = sb.length();
            int iLen2 = sStr.length();

            while(iStartIndex < iLen) {
                if (sb.substring(iStartIndex, iLen2 + iStartIndex).equals(sStr)) {
                    sb.replace(iStartIndex, iLen2 + iStartIndex, sRepStr);
                    iLen = sb.length();
                    iStartIndex += sRepStr.length();
                } else {
                    ++iStartIndex;
                }
            }
        } catch (Exception var6) {
        }

    }

    public static String turnStr(String s) {
        return s == null ? "" : s.trim();
    }

    public static List getClassMethodName(Class clazz) {
        List<String> methodList = new ArrayList();
        Method[] methods = clazz.getMethods();

        for(int i = 0; i < methods.length; ++i) {
            Method m = methods[i];
            methodList.add(m.getName());
        }

        return methodList;
    }

    public static int str2Int(String str) {
        int k = 0;

        try {
            if (str != null && str.trim().length() > 0) {
                k = Integer.parseInt(str);
            }
        } catch (Exception var3) {
        }

        return k;
    }

    public static double str2double(String str) {
        double d = 0.0D;

        try {
            if (str != null && str.trim().length() > 0) {
                d = Double.parseDouble(str);
            }
        } catch (Exception var4) {
        }

        return d;
    }

    public static String getStr(int i) {
        try {
            String str = String.valueOf(i);
            return str;
        } catch (Exception var2) {
            return "";
        }
    }

    public static String getStr(long l) {
        try {
            String str = String.valueOf(l);
            return str;
        } catch (Exception var3) {
            return "";
        }
    }

    public static String getStr(double d) {
        try {
            String str = String.valueOf(d);
            return str;
        } catch (Exception var3) {
            return "";
        }
    }

    public static String getStr(double d, String format) {
        try {
            String str = "";
            if (format != null && format.trim().length() > 0) {
                DecimalFormat myFormatter = new DecimalFormat(format);
                str = myFormatter.format(d);
            } else {
                str = String.valueOf(d);
            }

            return str;
        } catch (Exception var5) {
            return "";
        }
    }

    public static String getStr(String str, String def) {
        if (str == null || str.trim().length() == 0) {
            if (def != null && def.trim().length() > 0) {
                str = def;
            } else {
                str = "";
            }
        }

        return str;
    }

    public static long str2long(String str) {
        long l = 0L;

        try {
            if (str != null && str.trim().length() > 0) {
                l = Long.parseLong(str);
            }
        } catch (Exception var4) {
        }

        return l;
    }

    public static boolean isBlank(String obj) {
        return obj == null || "".equals(obj.trim()) || "null".equals(obj);
    }

    public static boolean isBlank(Object obj) {
        return obj == null || "".equals(obj) || "null".equals(obj);
    }

    public static String CRLFToHtml(String content) {
        String s = content.replaceAll("\r\n", "<br/>");
        s = s.replaceAll("\t", "　　");
        return s;
    }

    public static String format(Number value) {
        return format(value, 2);
    }

    public static String format(Number value, int decimal) {
        String len = "";

        for(int i = 0; i < decimal; ++i) {
            len = len + "0";
        }

        if (value == null && decimal == 0) {
            return "0";
        } else if (value == null && decimal > 0) {
            return "0." + len;
        } else {
            DecimalFormat format = new DecimalFormat("##,###,###,###,##0" + len);
            return format.format(value);
        }
    }

    public static int stringToInt(String value) {
        return stringToInt(value, 0);
    }

    public static int stringToInt(String value, int def) {
        return !isBlank(value) && isNumeric(value) ? Integer.parseInt(value) : def;
    }

    public static String escape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);

        for(int i = 0; i < src.length(); ++i) {
            char j = src.charAt(i);
            if (!Character.isDigit(j) && !Character.isLowerCase(j) && !Character.isUpperCase(j)) {
                if (j < 256) {
                    tmp.append("%");
                    if (j < 16) {
                        tmp.append("0");
                    }

                    tmp.append(Integer.toString(j, 16));
                } else {
                    tmp.append("%u");
                    tmp.append(Integer.toString(j, 16));
                }
            } else {
                tmp.append(j);
            }
        }

        return tmp.toString();
    }

    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0;
        boolean var3 = false;

        while(lastPos < src.length()) {
            int pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                char ch;
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char)Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char)Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else if (pos == -1) {
                tmp.append(src.substring(lastPos));
                lastPos = src.length();
            } else {
                tmp.append(src.substring(lastPos, pos));
                lastPos = pos;
            }
        }

        return tmp.toString();
    }

    public static String compressString(String sourcecode) throws UnsupportedEncodingException {
        ByteArrayOutputStream encodeout = new ByteArrayOutputStream();

        try {
            ByteArrayInputStream bin = new ByteArrayInputStream(sourcecode.getBytes());
            BufferedReader in = new BufferedReader(new InputStreamReader(bin));
            BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(encodeout));

            int c;
            while((c = in.read()) != -1) {
                out.write(c);
            }

            in.close();
            bin.close();
            out.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return encodeout.toString();
    }

    public static OutputStream compress(String sourcecode) throws UnsupportedEncodingException {
        ByteArrayOutputStream encodeout = new ByteArrayOutputStream();

        try {
            ByteArrayInputStream bin = new ByteArrayInputStream(sourcecode.getBytes());
            BufferedReader in = new BufferedReader(new InputStreamReader(bin));
            BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(encodeout));

            int c;
            while((c = in.read()) != -1) {
                out.write(c);
            }

            in.close();
            bin.close();
            out.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return encodeout;
    }

    public static String uncompressString(String encode) {
        String decode = "";

        try {
            ByteArrayInputStream bain = new ByteArrayInputStream(encode.getBytes());
            BufferedReader bin = new BufferedReader(new InputStreamReader(new GZIPInputStream(bain)));

            for(String tmpStr = null; (tmpStr = bin.readLine()) != null; tmpStr = null) {
                decode = decode + tmpStr;
            }

            bin.close();
            bain.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return decode;
    }

    public static String uncompress(InputStream encodeStream) {
        String decode = "";

        try {
            BufferedReader bin = new BufferedReader(new InputStreamReader(new GZIPInputStream(encodeStream)));

            for(String tmpStr = null; (tmpStr = bin.readLine()) != null; tmpStr = null) {
                decode = decode + tmpStr;
            }

            bin.close();
            encodeStream.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return decode;
    }

    public static byte[] unzip(byte[] zipBytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(zipBytes);
        ZipInputStream zis = new ZipInputStream(bais);
        zis.getNextEntry();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int BUFSIZ = true;
        byte[] inbuf = new byte[4096];

        int n;
        while((n = zis.read(inbuf, 0, 4096)) != -1) {
            baos.write(inbuf, 0, n);
        }

        byte[] data = baos.toByteArray();
        zis.close();
        return data;
    }

    public static byte[] zip(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipEntry ze = new ZipEntry("servletservice");
        ZipOutputStream zos = new ZipOutputStream(baos);
        zos.putNextEntry(ze);
        zos.write(data, 0, data.length);
        zos.close();
        byte[] zipBytes = baos.toByteArray();
        return zipBytes;
    }

    public static String sqlStr(String sql) {
        String ret = StringUtils.replaceChars(sql, "'", "''''");
        return ret;
    }

    public static String jsToJspEncode(String param) {
        if (param == null) {
            param = "";
        } else {
            try {
                if (ParameterConfig.getValue("IS_ENCODE").equals("true")) {
                    param = new String(param.getBytes("ISO8859-1"));
                }
            } catch (UnsupportedEncodingException var2) {
                System.out.println("转码失败");
                var2.printStackTrace();
            }
        }

        return param;
    }

    public static String appendDimStr(String sb, String key, String value) {
        return sb != null && !sb.equals("") ? sb + "0x09" + key + "=" + value : key + "=" + value;
    }

    public static List arrToList(Object[] arr) {
        List arrList = new ArrayList();

        for(int i = 0; i < arr.length; ++i) {
            arrList.add(arr[i]);
        }

        return arrList;
    }

    public static String systemEncode(String str) {
//        FTPClient ftpClient = new FTPClient();
//
//        try {
//            str = new String(str.getBytes(), ftpClient.getControlEncoding());
//        } catch (UnsupportedEncodingException var3) {
//            System.out.println("!!!!!!!!!!!!!!!!编码失败");
//            var3.printStackTrace();
//        }

//        return str;
        return "";
    }
}
