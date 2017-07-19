package com.zx.zxutils.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Xiangb on 2017/3/30.
 * 功能：日期工具类
 */
public class ZXTimeUtil {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前时间，格式默认为yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime() {
        return getTime(System.currentTimeMillis());
    }

    /**
     * 获取当前时间，格式需要传入
     *
     * @param dateFormat 时间格式
     */
    public static String getCurrentTime(SimpleDateFormat dateFormat) {
        return getTime(System.currentTimeMillis(), dateFormat);
    }

    /**
     * 根据时间毫秒获取时间数，格式需要传入
     *
     * @param timeInMillis 毫秒数
     * @return 时间字符串
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * 根据时间毫秒获取时间数，格式需要传入
     *
     * @param timeInMillis 毫秒数
     * @param dateFormat   时间格式
     * @return 时间字符串
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    private Date endDate;

    private static SimpleDateFormat formatBuilder;
    public static final int WEEKDAYS = 7;
    public static String[] WEEK = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * UTM转换成日期描述，如三周前，上午，昨天等
     *
     * @param milliseconds milliseconds
     * @param isShowWeek   是否采用周的形式显示  true 显示为3周前，false 则显示为时间格式mm-dd
     * @return 如三周前，上午，昨天等
     */

    private static String getTimeDesc(long milliseconds, boolean isShowWeek) {
        StringBuffer sb = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        long hour = calendar.get(Calendar.HOUR_OF_DAY);

        calendar.setTimeInMillis(System.currentTimeMillis());
        long hourNow = calendar.get(Calendar.HOUR_OF_DAY);

        Log.v("---------->---", System.currentTimeMillis() + "----------" + milliseconds);
        long datetime = System.currentTimeMillis() - (milliseconds);
        long day = (long) Math.floor(datetime / 24 / 60 / 60 / 1000.0f) + (hourNow < hour ? 1 : 0);// 天前

        if (day <= 7) {// 一周内
            if (day == 0) {// 今天
                if (hour <= 4) {
                    sb.append(" 凌晨 ");
                } else if (hour > 4 && hour <= 6) {
                    sb.append(" 早上 ");
                } else if (hour > 6 && hour <= 11) {
                    sb.append(" 上午 ");
                } else if (hour > 11 && hour <= 13) {
                    sb.append(" 中午 ");
                } else if (hour > 13 && hour <= 18) {
                    sb.append(" 下午 ");
                } else if (hour > 18 && hour <= 19) {
                    sb.append(" 傍晚 ");
                } else if (hour > 19 && hour <= 24) {
                    sb.append(" 晚上 ");
                } else {
                    sb.append("今天 ");
                }
            } else if (day == 1) {// 昨天
                sb.append(" 昨天 ");
            } else if (day == 2) {// 前天
                sb.append(" 前天 ");
            } else {
                sb.append(" " + DateToWeek(milliseconds) + " ");
            }
        } else {// 一周之前
            if (isShowWeek) {
                sb.append((day % 7 == 0 ? (day / 7) : (day / 7 + 1)) + "周前");
            } else {
                formatBuilder = new SimpleDateFormat("MM-dd");
                String time = formatBuilder.format(milliseconds);
                sb.append(time);
            }
        }
        Log.v("sb---", sb.toString() + "");
        return sb.toString();

    }

    /**
     * UTM转换成日期描述，如三周前，上午，昨天等
     *
     * @param milliseconds 时间
     * @return UTM转换成日期描述，如三周前，上午，昨天等
     */
    public static String getTimeDesc(long milliseconds) {

        return getTimeDesc(milliseconds, true);
    }

    /**
     * UTM转换成日期 ,hh:mm
     *
     * @param milliseconds milliseconds
     * @return UTM转换成日期 ,hh:mm
     */

    public static String getDisplayTime(long milliseconds) {
        formatBuilder = new SimpleDateFormat("HH:mm");
        String time = formatBuilder.format(milliseconds);
        return time;
    }

    /**
     * UTM转换成带描述的日期
     *
     * @param milliseconds milliseconds
     * @return UTM转换成带描述的日期
     */

    public static String getDisplayTimeAndDesc(long milliseconds) {
        formatBuilder = new SimpleDateFormat("HH:mm");
        String time = formatBuilder.format(milliseconds);
        StringBuffer sb = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        long hour = calendar.get(Calendar.HOUR_OF_DAY);
        Log.v("---------->---", System.currentTimeMillis() + "----------" + milliseconds);
        long datetime = System.currentTimeMillis() - (milliseconds);
        long day = (long) Math.ceil(datetime / 24 / 60 / 60 / 1000.0f);// 天前
        Log.v("day---hour---time---", day + "----" + hour + "-----" + time);

        if (day <= 7) {// 一周内
            if (day == 0) {// 今天
                if (hour <= 4) {
                    sb.append(" 凌晨 " + time);
                } else if (hour > 4 && hour <= 6) {
                    sb.append(" 早上 " + time);
                } else if (hour > 6 && hour <= 11) {
                    sb.append(" 上午 " + time);
                } else if (hour > 11 && hour <= 13) {
                    sb.append(" 中午 " + time);
                } else if (hour > 13 && hour <= 18) {
                    sb.append(" 下午 " + time);
                } else if (hour > 18 && hour <= 19) {
                    sb.append(" 傍晚 " + time);
                } else if (hour > 19 && hour <= 24) {
                    sb.append(" 晚上 " + time);
                } else {
                    sb.append("今天 " + time);
                }
            } else if (day == 1) {// 昨天
                sb.append("昨天 " + time);
            } else if (day == 2) {// 前天
                sb.append("前天 " + time);
            } else {
                sb.append(DateToWeek(milliseconds) + time);
            }
        } else {// 一周之前
            sb.append(day % 7 + "周前");
        }
        Log.v("sb---", sb.toString() + "");
        return sb.toString();

    }

    /**
     * 日期变量转成对应的星期字符串
     *
     * @param milliseconds data
     * @return 日期变量转成对应的星期字符串
     */
    public static String DateToWeek(long milliseconds) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        return WEEK[dayIndex - 1];
    }

    /**
     * 将时间间隔转换成描述性字符串，如2天前，3月1天后等。
     *
     * @param toDate 相对的日期
     * @param isFull 是否全部显示 true 全部显示 false 简单显示
     * @return 将时间间隔转换成描述性字符串，如2天前，3月1天后等。
     */
    public static String getDateDesc(Date toDate, boolean isFull) {
        String diffDesc = "";
        String fix = "";
        Long diffTime;
        Date curDate = new Date();
        if (curDate.getTime() > toDate.getTime()) {
            diffTime = curDate.getTime() - toDate.getTime();
            fix = "前";
        } else {
            diffTime = toDate.getTime() - curDate.getTime();
            fix = "后";
        }

        //换算成分钟数，防止Int溢出。
        diffTime = diffTime / 1000 / 60;

        Long year = diffTime / (60 * 24 * 30 * 12);
        diffTime = diffTime % (60 * 24 * 30 * 12);
        if (year > 0) {
            diffDesc = diffDesc + year + "年";
            if (!isFull) {
                return diffDesc + fix;
            }
        }

        Long month = diffTime / (60 * 24 * 30);
        diffTime = diffTime % (60 * 24 * 30);
        if (month > 0) {
            diffDesc = diffDesc + month + "月";
            if (!isFull) {
                return diffDesc + fix;
            }
        }

        Long day = diffTime / 60 / 24;
        diffTime = diffTime % (60 * 24);
        if (day > 0) {
            diffDesc = diffDesc + day + "天";
            if (!isFull) {
                return diffDesc + fix;
            }
        }

        Long hour = diffTime / (60);
        diffTime = diffTime % (60);
        if (hour > 0) {
            diffDesc = diffDesc + hour + "时";
            if (!isFull) {
                return diffDesc + fix;
            }
        }

        Long minitue = diffTime;
        if (minitue > 0) {
            diffDesc = diffDesc + minitue + "分";
            if (!isFull) {
                return diffDesc + fix;
            }
        }

        return diffDesc + fix;
    }


    /**
     * 返回两个时间的时间差
     *
     * @param startTime 毫秒值
     * @param endTime   毫秒值
     * @return 格式：2天1时30分20秒
     */
    public static String getTimeDifference(long startTime, long endTime) {
        if (null == formatBuilder) {
            formatBuilder = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        String start = formatBuilder.format(startTime);
        String end = formatBuilder.format(endTime);//如果服务器返回的时间是unix时间，单位是秒，而java中获取得单位是毫秒，需要注意
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(end).getTime() - sd.parse(start).getTime();
            long day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            return day + "天" + hour + "时" + min + "分" + sec + "秒";
        } catch (Exception e) {
            return null;
        }
    }

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2String(long millis) {
        return new SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault()).format(new Date(millis));
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为pattern</p>
     *
     * @param millis  毫秒时间戳
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String millis2String(long millis, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(millis));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Millis(String time) {
        return string2Millis(time, DEFAULT_PATTERN);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(String time, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_PATTERN);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return Date类型
     */
    public static Date string2Date(String time, String pattern) {
        return new Date(string2Millis(time, pattern));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param date Date类型时间
     * @return 时间字符串
     */
    public static String date2String(Date date) {
        return date2String(date, DEFAULT_PATTERN);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为pattern</p>
     *
     * @param date    Date类型时间
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param date Date类型时间
     * @return 毫秒时间戳
     */
    public static long date2Millis(Date date) {
        return date.getTime();
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param millis 毫秒时间戳
     * @return Date类型时间
     */
    public static Date millis2Date(long millis) {
        return new Date(millis);
    }
}
