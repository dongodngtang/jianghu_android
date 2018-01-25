/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: RegisterSecondActivity.java
 * Author: ZhangKai
 * Version: 1.0
 * Create: 2015-10-03
 *
 * Changes (from 2015-10-03)
 * -----------------------------------------------------------------
 * 2015-10-03 创建RegisterSecondActivity.java (ZhangKai);
 * -----------------------------------------------------------------
 * 2015-10-13 界面响应添加、接口调用 (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.event.login.AccountRegisterEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.biz.openapi.presenter.AccountHelper;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.util.StateButtomHelper;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.SpTools;

import de.greenrobot.event.EventBus;

public class RegisterSecondActivity extends BaseActivity implements
        OnClickListener {

    private final static int MSG_USER_EXIST = 100022;
    private final static int MSG_NICKNAME_IS_EXIST = 1101027;


    private EditText userNameEt;
    private TextView confirmBtn;

    private String phoneNumber;
    String password;
    String vcode;

    private boolean isRequest = false; // 判断位，是否正在等待请求响应

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_register_second);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("PARAM_PHONE_NUMBER");
        password = intent.getStringExtra("PARAM_PASSWORD");
        vcode = intent.getStringExtra("PARAM_VCODE");

        bindControl();

        setListener();
    }

    /**
     * 绑定控件
     */
    private void bindControl() {
        userNameEt = (EditText) findViewById(R.id.et_register_user_name);
        confirmBtn = (TextView) findViewById(R.id.btn_register_confirm);
    }

    /**
     * 给控件添加监听者
     */
    private void setListener() {
        userNameEt.setOnKeyListener(new OnKeyListener() {

            @SuppressLint("NewApi")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    confirmBtn.callOnClick();
                }
                return false;
            }
        });

        userNameEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (userNameEt.getText().toString().length() >= 2&& userNameEt.getText().toString().length() <= 20) {
                    StateButtomHelper.setNormalView(confirmBtn,"我就叫这个");
                } else {
                    StateButtomHelper.setUnclickableView(confirmBtn);
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

        confirmBtn.setOnClickListener(this);

        StateButtomHelper.setUnclickableView(confirmBtn);
    }

    @Override
    public void onClick(View v) {
        if (!v.isClickable()) {
            return;
        }

        String userName = userNameEt.getText().toString();
        if (userName.length() >= 2 && userName.length() <= 20) {
            if (!isRequest && password != null) {

                AccountHelper.getInstance().register(userName,phoneNumber,password
                        ,vcode,this,listener);


                makeToast(R.string.in_register);

                isRequest = true;
                StateButtomHelper.setLoadingView(confirmBtn,"我就叫这个...");
            }
        } else if (userName.length() < 2) {
            makeToast(R.string.register_user_name_too_short);
        } else {
            makeToast(R.string.register_user_name_too_long);
        }

        closeInputSoft();
    }


    /**
     * 注册响应
     */
    IOnResponseListener<Response<LoginUser>> listener = new IOnResponseListener<Response<LoginUser>>(){

        @Override
        public void onSuccess(Response<LoginUser> response) {
          LoginUser  currentUser = response.getData();
            if (currentUser == null)
                return;
            SharedPreferencesManager.setUserInfo(MyApplication.getInstance(), currentUser);

            makeToast(R.string.register_succeed);
            SpTools.setBoolean(RegisterSecondActivity.this, Content.TIME_MACHINE_REGISTER, true);
            finish();
            EventBus.getDefault().post(new AccountRegisterEvent());
        }

        @Override
        public void onError(Response response) {
            if (response.getCode() == -1) {

                System.out.println("onFailure,ErrorMessage="
                        + response.getMsg());
            } else {

                System.out.println("code != 0, == " + response.getCode()
                        + "---------------msg=" + response.getMsg());

                if (response.getCode() == MSG_USER_EXIST || response.getCode() == MSG_NICKNAME_IS_EXIST) {
                    makeToast(R.string.nickname_is_exist_try_other);
                } else {
                    makeToast(response.getMsg());
                }
            }
            isRequest = false;
            StateButtomHelper.setNormalView(confirmBtn,"我就叫这个");
        }
    };


}
