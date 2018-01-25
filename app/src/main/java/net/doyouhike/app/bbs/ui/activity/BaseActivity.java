/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: BaseActivity.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-9-29
 *
 * Changes (from 2015-10-7)
 * -----------------------------------------------------------------
 * 2015-9-29 创建BaseActivity.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 * 2015-10-6 添加方法adjustWithShowInput()(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-10-7 1、修改adjustWithShowInput()(wu-yoline);
 * 			 2、添加方法OnSoftInputShow()(wu-yoline);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.event.LogoutEvent;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.greenrobot.event.EventBus;

public class BaseActivity extends Activity {

    private int layoutId; // setContentView(R.layout.**)的R.layout.**

    protected LinearLayout thisLlyt; // 全局布局LinearLayout

    protected int statusBarHeight = 0;

    private boolean softInputIsShow = false;

    private SystemBarTintManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        MyApplication.getInstance().addActivity(this);
        // 注册EventBus
        EventBus.getDefault().register(this);

        thisLlyt = (LinearLayout) findViewById(R.id.llyt_this);

        // 状态栏高度，与软件盘适配、状态栏沉浸都有关
        statusBarHeight = getStatusBarHeight();

        // 版本判断，4.4及以上进行标题沉浸
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            if (thisLlyt != null) {

                manager = new SystemBarTintManager(BaseActivity.this);
                manager.setStatusBarTintEnabled(true);
                manager.setStatusBarTintResource(R.color.system_bar_bg);

                // 判断是否为虚拟按键
                if (!checkDeviceHasNavigationBar()) {
                    thisLlyt.setPadding(0, statusBarHeight, 0, 0);

                } else {
                    // 有虚拟按键
                    thisLlyt.setFitsSystemWindows(true);
//                    thisLlyt.setBackgroundColor(Color.BLACK);

                    thisLlyt.setPadding(0, 0, 0, 0);
                }
            }
        }
//		if (android.os.Build.VERSION.SDK_INT >= 14) {
//			getWindow().getDecorView().setSystemUiVisibility(
//					View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//		}

    }

    /**
     * 设置setContentView(R.layout.**)中的R.layout.**
     */
    protected void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    /**
     * 登出的时候，所有界面关闭，打开新的登录界面 （登录界面复写本方法，防止被错误关闭）
     */
    public void onEvent(LogoutEvent logout) {
//        finish();
    }

    @Override
    protected void onDestroy() {
        // 反注册EventBus
        EventBus.getDefault().unregister(this);
        UserHeadNickClickHelper.getInstance().onDestroy();
        MyApplication.getInstance().removeActivity(this);
        super.onDestroy();
    }

    /**
     * 获取状态栏高度
     */
    protected int getStatusBarHeight() {

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
    protected boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        int id = getResources().getIdentifier("config_showNavigationBar",
                "bool", "android");
        if (id > 0) {
            hasNavigationBar = getResources().getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class
                    .forName("android.os.SystemProperties");
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

    /**
     * 在本Activity上监听输入法弹出和隐藏，当输入弹出或隐藏时，会调用OnSoftInputShow()
     */
    protected void adjustWithShowInput() {
        thisLlyt.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    // 当键盘弹出隐藏的时候会 调用此方法。
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        // 获取当前界面可视部分
                        BaseActivity.this.getWindow().getDecorView()
                                .getWindowVisibleDisplayFrame(r);
                        // 获取屏幕的高度
                        int screenHeight = UIUtils.getValidHeight(BaseActivity.this);
                        // 此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                        int heightDifference = screenHeight - r.bottom;

                        // 此判断防止重复被调用此方法
                        if ((heightDifference > 0) == softInputIsShow) {
                            softInputIsShow = !softInputIsShow;
                            OnSoftInputShow(heightDifference);
                        }
                    }

                });
    }

    /**
     * 关闭输入法
     */
    protected void closeInputSoft() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 当输入法窗口弹出时会调用此方法
     *
     * @param height 输入发窗口的高度
     */
    protected void OnSoftInputShow(int height) {
    }

    /**
     * 标题栏左边回退按钮调用
     */
    public void rollBack(View view) {
        finish();
    }

    protected void makeToast(int ResStrId) {
        StringUtil.showSnack(this, getResources().getString(ResStrId));
    }

    protected void makeToast(String content) {
        StringUtil.showSnack(this, content);
    }

    protected void closeStatusCustomBg() {
        if (manager != null) {
            manager.setStatusBarTintEnabled(false);
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
