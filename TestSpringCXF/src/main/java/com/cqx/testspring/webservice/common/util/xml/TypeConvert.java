package com.cqx.testspring.webservice.common.util.xml;

/**
 * TypeConvert
 *
 * @author chenqixu
 */
public class TypeConvert {

    public TypeConvert() {
    }

    public static long stringToLong(String inStr) {
        long outLong;
        if (inStr != null && !inStr.equals("")) {
            outLong = Long.parseLong(inStr);
        } else {
            outLong = Long.parseLong("-999999");
        }

        return outLong;
    }

    public static int stringToInt(String inStr) {
        int outLong;
        if (inStr != null && !inStr.equals("")) {
            outLong = Integer.parseInt(inStr);
        } else {
            outLong = Integer.parseInt("-999999");
        }

        return outLong;
    }

    public static String longToString(long inInt) {
        String outStr = String.valueOf(inInt == Long.parseLong("-999999") ? null : inInt);
        return outStr;
    }

    public static String intToString(int inInt) {
        String outStr = String.valueOf(inInt == Integer.parseInt("-999999") ? null : inInt);
        return outStr;
    }

    public static String checkStrNull(String inStr) {
        if (inStr == null) {
            return null;
        } else if ("".equals(inStr)) {
            return null;
        } else {
            inStr = inStr.trim();
            return inStr.equals("-999999") ? null : inStr;
        }
    }

    public static Object checkLongNull(long inLong) {
        return inLong == Long.parseLong("-999999") ? null : inLong;
    }

    public static Object checkIntNull(int inInt) {
        return inInt == Integer.parseInt("-999999") ? null : inInt;
    }

    public static String nullToZero(String inStr) {
        return inStr != null && !inStr.equals("-999999") && !inStr.equals("") ? inStr : " ";
    }

    public static String nullToRealZero(String inStr) {
        return inStr.equals("-999999") ? "0" : inStr;
    }

    public static String nullToStr(String inStr) {
        return inStr != null && !inStr.equals("") ? inStr : "-999999";
    }
}
