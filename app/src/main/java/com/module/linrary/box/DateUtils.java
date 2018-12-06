package com.module.linrary.box;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateUtils {
    public static final String PATTERN1 = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN2 = "yyyyMMddHHmmss";
    public static final String PATTERN3 = "yyyy-MM-dd";
    public static final String PATTERN4 = "yyyy年MM月dd日";
    public static final String PATTERN5 = "MM-dd HH:mm";
    public static final String PATTERN6 = "HH:mm";
    public static final String PATTERN7 = "yyyy年MM月";
    public static final String PATTERN8 = "HH:mm:ss";
    public static final String PATTERN9 = "yyyy-MM-dd HH:mm";
    public static final String PATTERN10 = "yyyy.MM.dd";

    public final static long ONE_MINUTE_TIME = 60l * 1000l;
    public final static long ONE_HOURS_TIME = 60l * 60l * 1000l;
    public final static long ONE_DAY_TIME = 24l * 60l * 60l * 1000l;

    /**
     * 通过正则表达式将String日期转为Date对象。
     *
     * @param dateString
     * @param regex
     * @return
     */
    public static Date stringConvertDateByRegex(String dateString, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            if (pattern.matcher(dateString).lookingAt()) {
                dateString = dateString.substring(dateString.indexOf("(") + 1, dateString.indexOf(")"));
                return new Date(Long.parseLong(dateString));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过正则表达式将String日期转为Date对象。
     *
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static Date stringConvertDateByPattern(String dateStr, String formatStr) {
        try {
            SimpleDateFormat dd = new SimpleDateFormat(formatStr, Locale.getDefault());
            return dd.parse(dateStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将日期对象按“yyyyMMddHHmmss”的模板转为String。
     *
     * @param date
     * @return
     */
    public static String dateConvertStringByYYYYMMDDHHMMSS(Date date) {
        String dateString = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(PATTERN2, Locale.getDefault());
            dateString = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    /**
     * 将当前日期对象按“yyyyMMddHHmmss”的模板转为String。
     *
     * @return
     */
    public static String getCurrentTimeByYYYYMMDDHHMMSS() {
        return dateConvertStringByYYYYMMDDHHMMSS(Calendar.getInstance().getTime());
    }

    /**
     * 将字符型的日期毫秒数直接生成为日期对象。
     *
     * @param longTime
     * @return
     */
    public static Date longStringConvertDate(String longTime) {
        try {
            return new Date(Timestamp.valueOf(longTime).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将日期对象转为Calendar对象。
     *
     * @param date
     * @return
     */
    public static Calendar dateConvertCalendar(Date date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将日期对象按Pattern模板转为String串。
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateConvertStringByPattern(Date date, String pattern) {
        String dateString = "";
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
            dateString = format.format(date);
        }
        return dateString;
    }

    /**
     * 显示时间：几分钟前，几小时前
     *
     * @param time
     * @return
     */
    public static String showTime(long time) {
        long tempTime = (System.currentTimeMillis() - time);
        if (tempTime > 0 && tempTime < ONE_MINUTE_TIME) {
            return "刚刚";
        } else if (tempTime > ONE_MINUTE_TIME && tempTime < ONE_HOURS_TIME) {
            int minute = (int) (tempTime % (60 * 60 * 1000) / (60 * 1000));
            return String.format("%1$s分钟前", minute);
        } else if (tempTime > ONE_HOURS_TIME && tempTime < ONE_DAY_TIME) {
            int hours = (int) (tempTime / (60 * 60 * 1000));
            return String.format("%1$s小时前", hours);
        } else {
            return DateUtils.dateConvertStringByPattern(new Date(time), DateUtils.PATTERN3);
        }
    }

    /**
     * 毫秒转分钟
     *
     * @param time
     * @return
     */
    public static long dateToMinute(long time) {
        return time / ONE_MINUTE_TIME;
    }

    /**
     * 时间戳
     */
    public static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }
}
