package com.cqx.testspringboot.activiti.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenqixu
 */
public class ValidateUtil {

    public static boolean validateMobilePhone(String mobilePhone) {
        String pattern = "^[1][0-9]{10}$";
        return Pattern.matches(pattern, mobilePhone);
    }

    public static boolean validateEmail(String email) {
        String pattern = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
        return Pattern.matches(pattern, email);
    }

    public static boolean validateUserName(String userName) {
        try {
            return Math.ceil(userName.getBytes("GBK").length / 2.0) <= 30;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    /**
     * 判断是否是日期的格式：yyyy-mm-dd hh-mi-mm 2017-07-31 00:00:00
     *
     * @param timeStr
     * @return
     */
    public static boolean isDateFormat(String timeStr) {
        String regex = "\\d{4}-\\d{2}-\\d{2}\\s{1}\\d{2}:\\d{2}:\\d{2}";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(timeStr);
        return matcher.matches();
    }

    public static void main(String[] args) {
//        System.out.println(ValidateUtil.validateMobilePhone("01255442"));
//        System.out.println(ValidateUtil.validateMobilePhone("15802020202"));
//        System.out.println(ValidateUtil.validateMobilePhone("21202121212"));
//        System.out.println(ValidateUtil.validateMobilePhone("158020202021"));
//        System.out.println(ValidateUtil.validateMobilePhone("1580202020"));
        System.out.println(ValidateUtil.validateEmail("441030517@QQ..com"));
        System.out.println(ValidateUtil.validateEmail("abc_1232-@163.com"));
        System.out.println(ValidateUtil.validateEmail("_ad123dw@163.com.cn"));
        System.out.println(ValidateUtil.validateUserName("床前明月光疑似地上霜举头望明月低头思故乡离离原上草一岁一枯荣野火烧不尽春风吹又生床前明月光疑似地上霜举头望明月低头思故乡离离原上"));
        System.out.println(ValidateUtil.validateUserName("床前明月光疑似地上霜举头望明月低头思故乡离离原上草一岁一枯荣野火烧不尽春风吹又生床前明666666666666666666666"));
        System.out.println(ValidateUtil.validateUserName("床前明月光疑似地上霜举头望明月低头思故乡离离原上草一岁一枯23"));
        System.out.println(ValidateUtil.isDateFormat("2018-07-26 00:00:00d"));
    }

}
