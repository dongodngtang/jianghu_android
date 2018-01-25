/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: BasePagerFragment.java
 * Author: ChenWeiZhen
 * Version: 1.0.0
 * Create: 2015-9-26
 *
 * Changes (from 2015-9-26)
 * -----------------------------------------------------------------
 * 2015-9-26 创建BasePagerFragment.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 * 
 * Changes (from 2015-9-29)
 * -----------------------------------------------------------------
 * 2015-9-29 添加沉浸处理 (ChenWeiZhen);
 * -----------------------------------------------------------------
 * 
 */
package net.doyouhike.app.bbs.ui.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.NullInfo;
import net.doyouhike.app.bbs.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.greenrobot.event.EventBus;


public abstract class BasePagerFragment extends Fragment {

    private LinearLayout thisLlyt; // 总的LinearLayout布局

    private View rootView;
    private boolean initialized = false;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 注册EventBus
        EventBus.getDefault().register(this);

        if (rootView == null) {
            rootView = inflater.inflate(
                    setContentLayout(inflater, container, savedInstanceState),
                    container, false);

            thisLlyt = (LinearLayout) findViewById(R.id.llyt_this);

            // 版本判断，设置沉浸效果
            if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
                getActivity().getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getActivity().getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

                // 判断是否为虚拟按键
                if (!checkDeviceHasNavigationBar()) {
                    thisLlyt.setPadding(0, getStatusBarHeight(), 0, 0);
                } else {
                    thisLlyt.setPadding(0, 0, 0, 0);
                }
            }

            initResource();
            initialized = true;
        }

        return rootView;
    }

    /**
     * EventBus要求要有这个方法，其实没用
     */
    public void onEvent(NullInfo nullInfo) {
        // System.out.println(getClass().getSimpleName()+":我不想的");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 反注册EventBus
        EventBus.getDefault().unregister(this);
        if (null != rootView) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }

    /**
     * 设置内容Layoutid
     */
    protected abstract int setContentLayout(LayoutInflater inflater,
                                            ViewGroup container, Bundle savedInstanceState);

    /**
     * 在该方法内进行数据及控件的初始化
     */
    protected abstract void initResource();

    public View findViewById(int id) {
        if (rootView == null) {
            throw new IllegalStateException("Fragment " + this
                    + " RootView not created");
        }
        return rootView.findViewById(id);
    }

    public boolean isInitialized() {
        return initialized;
    }

    /**
     * 获取状态栏高度的方法
     */
    private int getStatusBarHeight() {

        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 判断是否含有虚拟按钮
     */
    private boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        int id = getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = getResources().getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass,
                    "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasNavigationBar;

    }

    protected void makeToast(int ResStrId) {
        StringUtil.showSnack(getActivity(), getResources().getString(ResStrId));
    }

    protected void makeToast(String content) {
        StringUtil.showSnack(getActivity(), content);
    }
}
