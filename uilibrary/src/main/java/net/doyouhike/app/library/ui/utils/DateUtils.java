/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: YolineTools.java
 * Author: wu-yoline(伍建鹏)
 * Version: 1.0
 * Create: 2015-10-3
 *
 * Changes (from 2015-10-12)
 * -----------------------------------------------------------------
 * 2015-10-6 : 	1、创建本类(wu-yoline);
 * 				2、添加getRawSize()方法(wu-yoline)
 * 				3、添加getIntFromDimens()方法(wu-yoline)
 * -----------------------------------------------------------------
 * 2015-10-12 : 1、添加几个处理时间的工具方法，getStrDate()、isTody()、isThisYear()及它们的重载方法(wu-yoline);
 * 				2、添加一个私有方法isDate()(wu-yoline);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.library.ui.utils;

import android.content.Context;

import net.doyouhike.app.library.ui.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 处理日期的工具类
 *
 * @author wu-yoline
 */
public class DateUtils {


    public static final String FORMAT ="yyyy/MM/dd";// 时间的格式

    public static String splitDateString(String date) {
        //1942年
        return date.split(" ")[0];
    }


    /**
     * 获取指定格式的时间字符串
     *
     * @param date   时间
     * @param format 格式
     * @return
     */
    public static String getStrDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获取指定格式的时间字符串
     *
     * @param time   时间
     * @param format 格式
     * @return
     */
    public static String getStrDate(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }

    public static boolean isCloseEnough(long var0, long var2) {
        long var4 = var0 - var2;
        if(var4 < 0L) {
            var4 = -var4;
        }

        return var4 < 5*60000L;
    }

    /**
     * 获取按需求格式的时间描述
     *
     * @param context
     * @param time
     * @return
     */
    public static String getMsgDateFormate(Context context, long time) {
        long now = new Date().getTime();
        String dateStr = "";
        if (now - time < 1 * 60 * 1000) { // 小于1分钟显示“刚刚”
            dateStr = context.getString(R.string.just); // TODO
        } else if (now - time < 1 * 60 * 60 * 1000) { // 小于1小时显示距今时间
            long minute = (now - time) / (60 * 1000L);
            dateStr = (int) minute + context.getString(R.string.minute_ago); // TODO
        } else if (DateUtils.isToday(time)) {
            dateStr = DateUtils.getStrDate(time, "HH:mm");
        } else if (DateUtils.isThisYear(time)) {
            dateStr = DateUtils.getStrDate(time, "MM.dd HH:mm");
        } else {
            dateStr = DateUtils.getStrDate(time, "yyyy.MM.dd");
        }
        return dateStr;
    }
    /**
     * 获取按需求格式的时间描述
     *
     * @param context
     * @param time
     * @return
     */
    public static String getStrDate(Context context, long time) {
        long now = new Date().getTime();
        String dateStr = "";
        if (now - time < 1 * 60 * 1000) { // 小于1分钟显示“刚刚”
            dateStr = context.getString(R.string.just); // TODO
        }else if (DateUtils.isToday(time)) {
            dateStr = DateUtils.getStrDate(time, "HH:mm");
        }else {
            dateStr = DateUtils.getStrDate(time, FORMAT);
        }
        return dateStr;
    }

    public static String getChatStrDate(long time){
        String dateStr = "";
        if (DateUtils.isToday(time)) {
            dateStr = DateUtils.getStrDate(time, "HH:mm");
        }else {
            dateStr = DateUtils.getStrDate(time, "yy/MM/dd HH:mm");
        }
        return dateStr;
    }



    /**
     * 获取按需求格式的时间描述
     *
     * @param context
     * @param time
     * @return
     */
    public static String getStrDate2(Context context, long time) {
        long now = new Date().getTime();
        String dateStr = "";
        if (DateUtils.isToday(time)) {
            dateStr = "今天 " + DateUtils.getStrDate(time, "HH:mm");
        } else if (DateUtils.isThisYear(time)) {
            dateStr = DateUtils.getStrDate(time, "MM.dd HH:mm");
        } else {
            dateStr = DateUtils.getStrDate(time, "yyyy.MM.dd");
        }
        return dateStr;
    }

    /**
     * 获取按需求格式的时间描述
     * 用于点赞的时间
     * @param context
     * @param time
     * @return
     */
    public static String getStrDate3(Context context, long time) {
        long now = new Date().getTime();
        String dateStr = "";
        if (now - time < 1 * 60 * 1000) { // 小于1分钟显示“刚刚”
            dateStr = context.getString(R.string.just); // TODO
        } else if (DateUtils.isToday(time)) {
            dateStr = DateUtils.getStrDate(time, "HH:mm");
        } else {
            dateStr = DateUtils.getStrDate(time, "yyyy.MM.dd");
        }
        return dateStr;
    }
    /**
     * 判断传入的时间是否为当天
     *
     * @param time 时间
     * @return
     */
    public static boolean isToday(long time) {
        Date date = new Date(time);
        return isToday(date);
    }

    /**
     * 判断传入的时间是否为当天
     *
     * @param date 时间
     * @return
     */
    public static boolean isToday(Date date) {
        boolean[] isDate = isDate(date);
        return isDate[0] && isDate[1] && isDate[2];
    }

    /**
     * 判断指定时间是否为今年
     *
     * @param time 时间
     * @return
     */
    public static boolean isThisYear(long time) {
        return isThisYear(new Date(time));
    }

    /**
     * 判断指定时间是否为今年
     *
     * @param date 时间
     * @return
     */
    public static boolean isThisYear(Date date) {
        boolean[] isDate = isDate(date);
        return isDate[0];
    }

    /**
     * 返回现在的时间和指定的时间的年月日是否相同
     *
     * @param date 指定时间
     * @return 返回长度为3的boolean数组，下标为0的为年是否相同，下标为1的为月是否相同，下标为2的为日是否相同
     */
    private static boolean[] isDate(Date date) {
        Date now = new Date();
        Calendar caleDate = Calendar.getInstance();
        caleDate.setTime(date);
        Calendar caleNow = Calendar.getInstance();
        caleNow.setTime(now);

        boolean[] isDate = new boolean[3];

        isDate[0] = caleDate.get(Calendar.YEAR) == caleNow.get(Calendar.YEAR);
        isDate[1] = caleDate.get(Calendar.MONTH) == caleNow.get(Calendar.MONTH);
        isDate[2] = caleDate.get(Calendar.DAY_OF_MONTH) == caleNow
                .get(Calendar.DAY_OF_MONTH);
        return isDate;
    }

    /**
     * 获取指定日后 后 dayAddNum 天的 日期
     *
     * @param day       日期，格式为String："2013-9-3";
     * @param dayAddNum 增加天数 格式为int;
     * @return
     */
    public static String getDateStr(String day, int dayAddNum) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate2 = new Date(nowDate.getTime() + dayAddNum * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }


}
