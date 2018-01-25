package net.doyouhike.app.bbs.ui.widget.action;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: InterceptScrollViewPager.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-10-26
 *
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author ZouWenJie 根据需求请求父组件不拦截触摸事件的自定义ViewPager类
 */
public class InterceptScrollViewPager extends ViewPager {

    private float downX;
    private float downY;
    private float moveX;
    private float moveY;

    public InterceptScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public InterceptScrollViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:
                moveX = ev.getX();
                moveY = ev.getY();
                float dx = moveX - downX;
                float dy = moveY - downY;
                if (Math.abs(dx) > 0) {//左右移动
                    // 请求父类不拦截触摸事件，自己处理
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else if (Math.abs(dy) > 0) {//上下移动
                    //分类处理滑动事件
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

}
