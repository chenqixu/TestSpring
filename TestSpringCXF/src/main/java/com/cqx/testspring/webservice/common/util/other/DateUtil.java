package com.cqx.testspring.webservice.common.util.other;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * DateUtil
 *
 * @author chenqixu
 */
public class DateUtil {
    private static Calendar curdate = Calendar.getInstance();
    private static Logger log = Logger.getLogger(DateUtil.class);

    public DateUtil() {
    }

    public static String getCurrDate(String sDateFormat) {
        Calendar gc = new GregorianCalendar();
        Date date = gc.getTime();
        SimpleDateFormat sf = new SimpleDateFormat(sDateFormat);
        String result = sf.format(date);
        return result;
    }

    public static Date parseDate(String dateStr) {
        if (dateStr != null && !dateStr.equals("")) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

            try {
                Date date = sf.parse(dateStr);
                return date;
            } catch (Exception var3) {
                var3.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static Date parseDateFull(String dateStr) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

        try {
            Date date = sf.parse(dateStr);
            return date;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Date parseDateByFormat(String dateStr, String formatStr) {
        Date re_date = null;

        try {
            SimpleDateFormat sf = new SimpleDateFormat(formatStr);
            re_date = sf.parse(dateStr);
        } catch (Exception var4) {
            log.error("====parseDateByFormat(String dateStr,String formatStr) Exception:", var4);
        }

        return re_date;
    }

    public static Date parseNextDateByFormat(String dateStr, String formatStr, int days) {
        Date re_date = null;

        try {
            SimpleDateFormat sf = new SimpleDateFormat(formatStr);
            re_date = sf.parse(dateStr);
            Calendar calInu = Calendar.getInstance();
            calInu.setTime(re_date);
            calInu.add(5, days);
            re_date = calInu.getTime();
        } catch (Exception var6) {
            log.error("====parseDateByFormat(String dateStr,String formatStr) Exception:", var6);
        }

        return re_date;
    }

    public static Date getNextDate(Date date, int days) {
        Date re_date = null;

        try {
            Calendar calInu = Calendar.getInstance();
            calInu.setTime(date);
            calInu.add(5, days);
            re_date = calInu.getTime();
        } catch (Exception var4) {
            log.error("====getNextDateByMormat(Date date,int days) Exception:", var4);
        }

        return re_date;
    }

    public static String getNextDateStrByFormat(String dateStr, String formatStr, int days) {
        String result = null;
        Date re_date = null;

        try {
            SimpleDateFormat sf = new SimpleDateFormat(formatStr);
            re_date = sf.parse(dateStr);
            Calendar calInu = Calendar.getInstance();
            calInu.setTime(re_date);
            calInu.add(5, days);
            re_date = calInu.getTime();
            result = sf.format(re_date);
        } catch (Exception var7) {
            log.error("====parseDateByFormat(String dateStr,String formatStr) Exception:", var7);
        }

        return result;
    }

    public static String getNextDateStrByFormat(Date date, String formatStr, int days) {
        String result = null;
        Date re_date = null;

        try {
            Calendar calInu = Calendar.getInstance();
            calInu.setTime(date);
            calInu.add(5, days);
            re_date = calInu.getTime();
            SimpleDateFormat sf = new SimpleDateFormat(formatStr);
            result = sf.format(re_date);
        } catch (Exception var7) {
            log.error("====parseDateByFormat(String dateStr,String formatStr) Exception:", var7);
        }

        return result;
    }

    public static String getSysDateByFormat(String formatStr) {
        String datestr = "";

        try {
            DateFormat df = new SimpleDateFormat(formatStr);
            Date date = new Date();
            datestr = df.format(date);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return datestr;
    }

    public static boolean checkExpired(String expiredDate) {
        if (expiredDate != null && !expiredDate.equals("") && !expiredDate.equals("0")) {
            Date expDate = parseDate(expiredDate);
            Date curDate = new Date();
            Calendar cal = new GregorianCalendar();
            cal.setTime(expDate);
            Calendar calCur = new GregorianCalendar();
            calCur.setTime(curDate);
            return cal.compareTo(calCur) < 0;
        } else {
            return false;
        }
    }

    public static boolean checkExpired(String inureDate, String expiredDate) {
        if (inureDate != null && !inureDate.equals("")) {
            if (expiredDate != null && !expiredDate.equals("")) {
                Date inuDate = parseDate(inureDate);
                Date expDate = parseDate(expiredDate);
                Date curDate = new Date();
                Calendar calInu = new GregorianCalendar();
                calInu.setTime(inuDate);
                Calendar calExp = new GregorianCalendar();
                calExp.setTime(expDate);
                Calendar calCur = new GregorianCalendar();
                calCur.setTime(curDate);
                int i = calCur.compareTo(calInu);
                int j = calCur.compareTo(calExp);
                return i <= 0 || j > 0;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean checkExpiredFull(String expiredDate) {
        Date expDate = parseDateFull(expiredDate);
        Date curDate = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(expDate);
        Calendar calCur = new GregorianCalendar();
        calCur.setTime(curDate);
        return cal.compareTo(calCur) < 0;
    }

    public static boolean checkExpiredFull(String inureDate, String expiredDate) {
        if (inureDate != null && !inureDate.equals("")) {
            if (expiredDate != null && !expiredDate.equals("")) {
                Date inuDate = parseDateFull(inureDate);
                Date expDate = parseDateFull(expiredDate);
                Date curDate = new Date();
                Calendar calInu = new GregorianCalendar();
                calInu.setTime(inuDate);
                Calendar calExp = new GregorianCalendar();
                calExp.setTime(expDate);
                Calendar calCur = new GregorianCalendar();
                calCur.setTime(curDate);
                int i = calCur.compareTo(calInu);
                int j = calCur.compareTo(calExp);
                return i > 0 && j < 0;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean checkTime(String timeAllowed) {
        if (timeAllowed == null && timeAllowed.equals("")) {
            return true;
        } else if (timeAllowed.length() != 10) {
            return true;
        } else if (!StringUtils.isNumeric(timeAllowed)) {
            return true;
        } else {
            int beginWeekDay = Integer.parseInt(timeAllowed.substring(0, 1));
            int endWeekDay = Integer.parseInt(timeAllowed.substring(1, 2));
            int beginTime = Integer.parseInt(timeAllowed.substring(2, 6));
            int endTime = Integer.parseInt(timeAllowed.substring(6));
            Calendar cal = GregorianCalendar.getInstance();
            int curWeekDay = getWeekDay();
            int curTime = cal.get(11) * 100 + cal.get(12);
            StringBuffer buf = new StringBuffer();
            buf.append("week:").append(beginWeekDay).append(":").append(endWeekDay);
            buf.append("time:").append(beginTime).append(":").append(endTime);
            buf.append(" cur week:").append(curWeekDay).append(" cur time:").append(curTime);
            log.info(buf.toString());
            return curWeekDay >= beginWeekDay && curWeekDay <= endWeekDay && curTime > beginTime && curTime <= endTime;
        }
    }

    public static String getSomeDaysAgoDate(int days, String timeFormat) {
        Calendar calInu = new GregorianCalendar();
        calInu.add(5, days);
        Date d = calInu.getTime();
        SimpleDateFormat sf = new SimpleDateFormat(timeFormat);
        String newdate = sf.format(d);
        return newdate;
    }

    public static int getWeekDay() {
        Calendar cal = GregorianCalendar.getInstance();
        int ret = cal.get(7) - 1;
        if (ret == 0) {
            ret = 7;
        }

        return ret;
    }

    public static String getLastDayOfMonth(String dateStr) {
        if (dateStr.length() < 8) {
            dateStr = dateStr + "01";
        }

        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if (dateStr != null && dateStr.trim().length() == 6) {
            format = "yyyyMM";
        }

        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);

        try {
            Date date = bartDateFormat.parse(dateStr);
            calendar.setTime(date);
        } catch (Exception var5) {
            System.out.println(var5.getMessage());
        }

        calendar.roll(2, 0);
        calendar.roll(5, 0 - calendar.get(5));
        calendar.set(10, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return bartDateFormat.format(calendar.getTime()).substring(6, 8);
    }

    public static String getTime(String sDate, int iIndex, int iDistance) {
        String sTemp;
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(1, Integer.parseInt(sDate.substring(0, 4), 10));
            cal.set(2, Integer.parseInt(sDate.substring(4, 6), 10) - 1);
            cal.set(5, Integer.parseInt(sDate.substring(6, 8), 10));
            if (iIndex == 1) {
                cal.add(1, iDistance);
            } else if (iIndex == 2) {
                cal.add(2, iDistance);
            } else if (iIndex == 3) {
                cal.add(5, iDistance);
            }

            sTemp = cal.get(1) + "";
            sTemp = sTemp + getFullLength(cal.get(2) + 1, 2);
            sTemp = sTemp + getFullLength(cal.get(5), 2);
        } catch (Exception var5) {
            System.out.println(var5.toString());
            sTemp = "20020101";
        }

        return sTemp;
    }

    private static String getFullLength(int iOral, int iLength) {
        int iIndex = ("" + iOral).length();
        if (iIndex > iLength) {
            return "" + iOral;
        } else {
            String sTemp = "" + iOral;

            for(int i = 0; i < iLength - iIndex; ++i) {
                sTemp = "0" + sTemp;
            }

            return sTemp;
        }
    }

    public static String getLastMonth(String dateStr) {
        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if (dateStr != null && dateStr.trim().length() == 6) {
            format = "yyyyMM";
        }

        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);

        try {
            Date date = bartDateFormat.parse(dateStr);
            calendar.setTime(date);
        } catch (Exception var5) {
            System.out.println(var5.getMessage());
        }

        calendar.add(2, -1);
        return bartDateFormat.format(calendar.getTime()).toString();
    }

    public static String getLastDay(String dateStr) {
        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if (dateStr != null && dateStr.trim().length() == 6) {
            format = "yyyyMM";
        }

        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);

        try {
            Date date = bartDateFormat.parse(dateStr);
            calendar.setTime(date);
        } catch (Exception var5) {
            System.out.println(var5.getMessage());
        }

        calendar.add(5, -1);
        return bartDateFormat.format(calendar.getTime()).toString();
    }

    public static String getYear() {
        Calendar cal = GregorianCalendar.getInstance();
        int ret = cal.get(1);
        return String.valueOf(ret);
    }

    public static String getMonth() {
        Calendar cal = GregorianCalendar.getInstance();
        int ret = cal.get(2) + 1;
        return String.valueOf(ret);
    }

    public static String getLastMonths(String month, int monthNum) {
        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if (month != null && month.trim().length() == 6) {
            format = "yyyyMM";
        }

        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);

        try {
            Date date = bartDateFormat.parse(month);
            calendar.setTime(date);
        } catch (Exception var6) {
            System.out.println(var6.getMessage());
        }

        calendar.add(2, -monthNum);
        return bartDateFormat.format(calendar.getTime()).toString();
    }

    public static String getNextMonths(String month, int monthNum) {
        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if (month != null && month.trim().length() == 6) {
            format = "yyyyMM";
        }

        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);

        try {
            Date date = bartDateFormat.parse(month);
            calendar.setTime(date);
        } catch (Exception var6) {
            System.out.println(var6.getMessage());
        }

        calendar.add(2, monthNum);
        return bartDateFormat.format(calendar.getTime()).toString();
    }

    public static long getSubDate(String startDate, String endDate) {
        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if (startDate != null && startDate.trim().length() == 6) {
            format = "yyyyMM";
        }

        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);

        try {
            Date date = bartDateFormat.parse(startDate);
            calendar.setTime(date);
        } catch (Exception var10) {
            System.out.println(var10.getMessage());
        }

        Calendar calendar1 = new GregorianCalendar();
        String format1 = "yyyyMMdd";
        if (endDate != null && endDate.trim().length() == 6) {
            format1 = "yyyyMM";
        }

        SimpleDateFormat bartDateFormat1 = new SimpleDateFormat(format1);

        try {
            Date date = bartDateFormat1.parse(endDate);
            calendar1.setTime(date);
        } catch (Exception var9) {
            System.out.println(var9.getMessage());
        }

        return (calendar1.getTime().getTime() - calendar.getTime().getTime()) / 86400000L;
    }

    public static String getDefaultFirstDate(int circle_id, int Date_Bwtween) {
        String tj_date = "";
        Date d;
        SimpleDateFormat sf;
        if (circle_id == 1) {
            curdate.add(5, 1 - Date_Bwtween);
            d = curdate.getTime();
            sf = new SimpleDateFormat("yyyyMMdd");
            tj_date = sf.format(d);
            curdate.add(5, Date_Bwtween - 1);
        } else if (circle_id == 11) {
            curdate.add(5, 1);
            curdate.add(2, -1 * Date_Bwtween);
            d = curdate.getTime();
            sf = new SimpleDateFormat("yyyyMM");
            tj_date = sf.format(d);
            curdate.add(2, Date_Bwtween);
            curdate.add(5, -1);
        } else if (circle_id == 2) {
            curdate.add(5, 1);
            d = curdate.getTime();
            sf = new SimpleDateFormat("dd");
            int dd = Integer.parseInt(sf.format(d));
//            byte dd;
            if (dd <= 7) {
                dd = 4;
                curdate.add(2, -1);
            } else if (dd <= 14) {
                dd = 1;
            } else if (dd <= 21) {
                dd = 2;
            } else {
                dd = 3;
            }

            d = curdate.getTime();
            sf = new SimpleDateFormat("yyyyMM");
            tj_date = sf.format(d) + "0" + dd;
            int yyyy = Integer.parseInt(tj_date) / 10000;
            int mm = Integer.parseInt(tj_date) / 100 % 100;
            int day = Integer.parseInt(tj_date) % 100;
            day = day - Date_Bwtween % 4 + 1;

            int i;
            for(i = 0; day <= 0; ++i) {
                day += 4;
            }

            mm = mm - i - Date_Bwtween / 4;

            for(i = 0; mm <= 0; ++i) {
                mm += 12;
            }

            yyyy -= i;
            tj_date = String.valueOf(yyyy * 10000 + mm * 100 + day);
            if (dd == 4) {
                curdate.add(2, 1);
            }

            curdate.add(5, -1);
        } else if (circle_id == 21) {
            curdate.add(5, 1);
            curdate.add(1, -1 * Date_Bwtween);
            d = curdate.getTime();
            sf = new SimpleDateFormat("yyyy");
            tj_date = sf.format(d);
            curdate.add(1, Date_Bwtween);
            curdate.add(5, -1);
        }

        return tj_date;
    }

    public static boolean checkIsDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");

        try {
            Date date = formatter.parse(strDate);
            return strDate.equals(formatter.format(date));
        } catch (Exception var3) {
            return false;
        }
    }

    public static String formatData(double pass, String formatstr) {
        try {
            DecimalFormat nf = (DecimalFormat) NumberFormat.getInstance();
            nf.applyPattern(formatstr);
            return nf.format(pass);
        } catch (Exception var4) {
            return String.valueOf(pass);
        }
    }

    public static Double getDateProcess(int cur_date) {
        if (String.valueOf(cur_date).length() != 8) {
            return null;
        } else {
            int mm = cur_date / 100 % 100;
            int days = 0;

            for(int i = 0; i < mm; ++i) {
                days += getDays(cur_date / 100);
            }

            return 1.0D - 0.2D * (double)days / 365.0D;
        }
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

    public static String toyyyyMMdd(String str) {
        String[] tempStr = null;
//        int tempInt = false;
        String returnStr = "";
        if (!str.equals("") && str != null) {
            tempStr = str.split(" ");
            tempStr = tempStr[0].split("/");
            returnStr = tempStr[2] + "-";
            if (Integer.parseInt(tempStr[0]) < 10) {
                returnStr = returnStr + "0" + tempStr[0] + "-";
            } else {
                returnStr = returnStr + tempStr[0] + "-";
            }

            if (Integer.parseInt(tempStr[1]) < 10) {
                returnStr = returnStr + "0" + tempStr[1];
            } else {
                returnStr = returnStr + tempStr[1];
            }
        }

        return returnStr;
    }

    public static String toyyyyMMdd1(String str) {
        String[] tempStr = null;
//        int tempInt = false;
        String returnStr = "";
        if (!str.equals("") && str != null) {
            tempStr = str.split(" ");
            tempStr = tempStr[0].split("/");
            returnStr = tempStr[2];
            if (Integer.parseInt(tempStr[0]) < 10) {
                returnStr = returnStr + "0" + tempStr[0];
            } else {
                returnStr = returnStr + tempStr[0];
            }

            if (Integer.parseInt(tempStr[1]) < 10) {
                returnStr = returnStr + "0" + tempStr[1];
            } else {
                returnStr = returnStr + tempStr[1];
            }
        }

        return returnStr;
    }

    public static boolean isDate(String str_input, String rDateFormat) {
        if (str_input != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
            formatter.setLenient(false);

            try {
                formatter.format(formatter.parse(str_input));
                return true;
            } catch (Exception var4) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String getSomeDaysAgoOrAfter(String dateStr, int dcount) {
        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if (dateStr != null && dateStr.trim().length() == 6) {
            format = "yyyyMM";
        }

        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);

        try {
            Date date = bartDateFormat.parse(dateStr);
            calendar.setTime(date);
        } catch (Exception var6) {
            System.out.println(var6.getMessage());
        }

        calendar.add(5, dcount);
        return bartDateFormat.format(calendar.getTime()).toString();
    }

    public static String getLastDateOfMonth(String dateStr) {
        if (dateStr.length() < 8) {
            dateStr = dateStr + "01";
        }

        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if (dateStr != null && dateStr.trim().length() == 6) {
            format = "yyyyMM";
        }

        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);

        try {
            Date date = bartDateFormat.parse(dateStr);
            calendar.setTime(date);
        } catch (Exception var5) {
            System.out.println(var5.getMessage());
        }

        calendar.roll(2, 0);
        calendar.roll(5, 0 - calendar.get(5));
        calendar.set(10, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return bartDateFormat.format(calendar.getTime());
    }

    public static String getDateStrByFormat(Date date, String sDateFormat) {
        if (null == date) {
            return null;
        } else {
            String result = null;

            try {
                SimpleDateFormat sf = new SimpleDateFormat(sDateFormat);
                result = sf.format(date);
            } catch (Exception var4) {
                log.error("===getDateStrByFormat(Date date,String sDateFormat) 异常:", var4);
            }

            return result;
        }
    }

    public static int daysBetween(Date begin_date, Date end_date) {
        int k = -9999;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            begin_date = sdf.parse(sdf.format(begin_date));
            end_date = sdf.parse(sdf.format(end_date));
            Calendar cal = Calendar.getInstance();
            cal.setTime(begin_date);
            long time1 = cal.getTimeInMillis();
            cal.setTime(end_date);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / 86400000L;
            k = Integer.parseInt(String.valueOf(between_days));
        } catch (Exception var11) {
            log.error("===daysBetween(Date begin_date,Date end_date) 异常:", var11);
        }

        return k;
    }

    public static int daysBetween(String begin_date, String end_date) {
        int k = -9999;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(begin_date));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(end_date));
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / 86400000L;
            k = Integer.parseInt(String.valueOf(between_days));
        } catch (Exception var11) {
            log.error("===daysBetween(Date begin_date,Date end_date) 异常:", var11);
        }

        return k;
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(getDateStrByFormat(date, "yyyy-MM-dd HH:mm:ss"));
        System.out.println(String.valueOf(Integer.parseInt("2012") + 1));
        System.out.println(getCurrDate("yyyy-MM-dd HH:mm:ss"));
        System.out.println(getCurrDate("yyyy-MM-dd HH:mm:ss SSS"));
        System.out.println(getCurrDate("YYYYMMDD"));
        System.out.println(getCurrDate("yyyyMMdd"));
    }
}
