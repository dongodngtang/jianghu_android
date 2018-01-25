/*
* -----------------------------------------------------------------
* Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
* -----------------------------------------------------------------
*
* File: SettingUserdescActivity.java
* Author: ChenWeiZhen
* Version: 1.0
* Create: 2015-10-5
*
* Changes (from 2015-10-5)
* -----------------------------------------------------------------
* 2015-10-5 创建SettingUserdescActivity.java (ChenWeiZhen);
* -----------------------------------------------------------------
*/
package net.doyouhike.app.bbs.ui.activity.me;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.util.AsyncAlertDialog;
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;

import de.greenrobot.event.EventBus;

public class SettingUserdescActivity extends BaseActivity {

    private TextView confirmTv;
    private EditText userdescEt;
    private TextView surplusTv;
    private ImageView mIvClear;

    private CurrentUserDetails cuDetails;
    AsyncAlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_setting_userdesc);
        super.onCreate(savedInstanceState);

        bindControl();

        setListener();

        refreshDisplay();

    }

    /**
     * 绑定控件
     */
    private void bindControl() {
        mDialog=new AsyncAlertDialog(this);
        confirmTv = ((TitleView) findViewById(R.id.navigation_title)).getRight_text();
        userdescEt = (EditText) findViewById(R.id.et_userdesc);
        surplusTv = (TextView) findViewById(R.id.tv_surplus);
        mIvClear=(ImageView) findViewById(R.id.iv_setting_userdes_clear);
    }

    /**
     * 给控件添加监听者
     */
    private void setListener() {

        // 确定按钮
        confirmTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!v.isEnabled()) {
                    return;
                }

                UserdescInfo userdescInfo = new UserdescInfo();
                userdescInfo.userdesc = userdescEt.getText().toString();
                if (userdescInfo.userdesc.length() <= 10) {
                    if (userdescInfo.userdesc.length() == 0) {
                        userdescInfo.userdesc += " ";
                    }
                    EventBus.getDefault().post(userdescInfo);
                    finish();
                }
            }
        });

        userdescEt.setOnKeyListener(new OnKeyListener() {

            @SuppressLint("NewApi")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    confirmTv.callOnClick();
                }
                return false;
            }
        });

        userdescEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 10) {
                    surplusTv.setText((10 - s.length()) + "");
                    surplusTv.setTextColor(getResources().getColor(R.color.gray_word));
                    confirmTv.setEnabled(true);
                } else {
                    surplusTv.setText(s.length() + "");
                    surplusTv.setTextColor(getResources().getColor(R.color.orange_word));
                    confirmTv.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mIvClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                userdescEt.setText(null);
            }
        });
    }

    /**
     * 设置显示数据
     */
    private void refreshDisplay() {
        cuDetails = SharedPreferencesManager.getUserDetailsInfo(this);

        // 昵称
        String userDesc = cuDetails.getUser_desc();

        if (userDesc == null) {
            return;
        }

        if (!userDesc.equals("")) {
            userdescEt.setText(userDesc);
        }

        userdescEt.requestFocus();
        userdescEt.setSelection(userDesc.length());
    }

    public class UserdescInfo {
        String userdesc;
    }
}
