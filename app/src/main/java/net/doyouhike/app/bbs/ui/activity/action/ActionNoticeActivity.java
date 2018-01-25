/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ActionNoticeActivity.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-10-14
 *
 */
package net.doyouhike.app.bbs.ui.activity.action;

import android.os.Bundle;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;

/**
 * @author ZouWenJie 报名活动须知界面
 */
public class ActionNoticeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_action_notice);
        super.onCreate(savedInstanceState);
        bindControl();
        setListener();
    }

    /**
     * 绑定控件
     */
    private void bindControl() {

    }

    /**
     * 设置监听
     */
    private void setListener() {
    }

}
