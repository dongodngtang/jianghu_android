/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: StringUtils.java
 * Author: wu-yoline(伍建鹏)
 * Version: 1.0
 * Create: 2015-10-15
 *
 * Changes (from 2015-10-15)
 * -----------------------------------------------------------------
 * 2015-10-15 : 1、创建 本类 (wu-yoline);
 * 				2、添加hasContent()方法(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-11-02 : 1、添加getResourcesStr()方法;(wu-yoline);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wu-yoline
 *
 */
public class StrUtils {

	/**
	 * 判读字符串是否有内容，即不为null或为""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasContent(String str) {
		return str != null && !str.isEmpty();
	}
	
	/**
	 * 判读字符串是否有内容，即不为null或为""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasContent(CharSequence str) {
		return str != null && !"".equals(str);
	}

	/** 获取对应id的字符串
	 * @param context
	 * @param id
	 * @return
	 */
	public static String getResourcesStr(Context context, int id) {
		if (context != null && context.getResources() != null) {
			return context.getResources().getString(id);
		}
		return null;
	}

	/**
	 * 时间戳转换成日期格式字符串
	 * @param seconds 精确到秒的字符串
	 * @param format
	 * @return
	 */
	public static String timeStamp2Date(String seconds,String format) {
		if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
			return "";
		}
		if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds+"000")));
	}
	/**
	 * 日期格式字符串转换成时间戳
	 * @param date_str 字符串日期
	 * @param format 如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long date2TimeStamp(String date_str,String format){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date_str).getTime()/1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * @param string 字符内容
	 * @return 转换为int类型
	 */
	public static int toInt(String string){
		if (hasContent(string)){
			return Integer.valueOf(string);
		}

		return 0;
	}

}
