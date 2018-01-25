/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: SpTools.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-11-18
 *
 */
package net.doyouhike.app.bbs.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author ZouWenJie
 *         使用SharedPreference保存数据的工具类
 */
public class SpTools {
    private static String SPNAME = "cache";


    /**
     * @param context
     * @param key     关键字
     * @param value   设置的值
     */
    public static void setInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    /**
     * @param context
     * @param key      关键字
     * @param defValue 默认的值
     * @return
     */
    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    /**
     * @param context
     * @param key     关键字
     * @param value   设置的值
     */
    public static void setString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    /**
     * @param context
     * @param key      关键字
     * @param defValue 默认的值
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    /**
     * @param context
     * @param key
     * @param value   保存布尔类型的值
     */
    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * @param context
     * @param key
     * @param defValue 获取保存的布尔值
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 清理数据
     */
    public static void clear(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

}
