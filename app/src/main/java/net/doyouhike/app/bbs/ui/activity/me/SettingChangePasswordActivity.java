/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: SettingChangePassword.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-22
 *
 * Changes (from 2015-10-22)
 * -----------------------------------------------------------------
 * 2015-10-22 创建SettingChangePassword.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.me;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.util.AsyncAlertDialog;
import net.doyouhike.app.bbs.biz.event.login.AccountPasswordResetEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.AccountHelper;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.util.EnCoder;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;

public class SettingChangePasswordActivity extends BaseActivity implements
        TextWatcher, OnClickListener {

    private static final int MSG_PSW_FAIL = 110014;

    private Context thisContext = this;

    private LinearLayout containerLlyt;
    TitleView mTitleView;
    private TextView confirmTv;
    private EditText oldPasswordEt;
    private EditText newPasswordEt;
    private EditText newConfirmEt;

    private LinearLayout rlyt_old_password, rlyt_new_password, rlyt_new_password_confirm;


    private int softInputGap = 0;

    AsyncAlertDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_setting_change_password);
        super.onCreate(savedInstanceState);
        bindControl();

        setListener();

    }

    /**
     * 绑定控件
     */
    private void bindControl() {
        mTitleView = (TitleView) findViewById(R.id.navigation_title);
        containerLlyt = (LinearLayout) findViewById(R.id.llyt_container);

        confirmTv = mTitleView.getRight_text();

        oldPasswordEt = (EditText) findViewById(R.id.et_old_password);
        newPasswordEt = (EditText) findViewById(R.id.et_new_password);
        newConfirmEt = (EditText) findViewById(R.id.et_new_password_confirm);

        mDialog = new AsyncAlertDialog(thisContext);
    }

    IOnResponseListener pwdListener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            mDialog.dismiss();
            makeToast(R.string.change_password_succeed);
            finish();
        }

        @Override
        public void onError(Response response) {
            mDialog.dismiss();
            if (response.getCode() == -1) {
                System.out.println("onFailure,ErrorMessage="
                        + response.getMsg());
            } else {
                System.out.println("code != 0---------------msg="
                        + response.getMsg());

                if (response.getCode() == MSG_PSW_FAIL) {
                    makeToast(R.string.old_password_fail);
                } else {
                    makeToast(response.getMsg());
                }
            }
            confirmTv.setEnabled(true);
        }
    };

    /**
     * 给控件添加监听者
     */
    private void setListener() {
        confirmTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!v.isEnabled()) {
                    return;
                }

                String oldPwd = oldPasswordEt.getText().toString();
                String newPwd = newPasswordEt.getText().toString();
                String confirmPwd = newConfirmEt.getText().toString();

                if (newPwd.equals(confirmPwd) && checkPasswordInput() && UserInfoUtil.getInstance().getUserId() != null) {
                    mDialog.show();
                    // 调用接口
                    AccountHelper.getInstance().changePwdByOldPwd(SettingChangePasswordActivity.this,
                            UserInfoUtil.getInstance().getUserId(), EnCoder.MD5(oldPwd), EnCoder.MD5(newPwd), pwdListener);

                    confirmTv.setEnabled(false);
                } else if (UserInfoUtil.getInstance().isLogin())
                    makeToast(R.string.please_to_login);
                else
                    makeToast(R.string.please_make_new_password_correct);


                closeInputSoft();
            }
        });

        confirmTv.setEnabled(false);


        rlyt_new_password_confirm = (LinearLayout) findViewById(R.id.rlyt_new_password_confirm);
        rlyt_new_password = (LinearLayout) findViewById(R.id.rlyt_new_password);
        rlyt_old_password = (LinearLayout) findViewById(R.id.rlyt_old_password);

        rlyt_new_password.setOnClickListener(this);
        rlyt_new_password_confirm.setOnClickListener(this);
        rlyt_old_password.setOnClickListener(this);


        oldPasswordEt.addTextChangedListener(this);
        newPasswordEt.addTextChangedListener(this);
        newConfirmEt.addTextChangedListener(this);
        adjustWithShowInput();

        oldPasswordEt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                rlyt_old_password.setSelected(hasFocus);
            }
        });

        newPasswordEt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                rlyt_new_password.setSelected(hasFocus);
            }
        });

        newConfirmEt.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                rlyt_new_password_confirm.setSelected(hasFocus);
                if (hasFocus) {
                    containerLlyt.setPadding(0, softInputGap, 0, 0);
                } else {
                    containerLlyt.setPadding(0, 0, 0, 0);
                }
            }
        });

        newConfirmEt.setOnKeyListener(new OnKeyListener() {

            @SuppressLint("NewApi")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    confirmTv.callOnClick();
                }
                return false;
            }
        });
    }

    /**
     * 检查密码输入格式是否正确
     */
    private boolean checkPasswordInput() {
        String passwordStr = newPasswordEt.getText().toString();
        if (passwordStr.length() >= 6 && passwordStr.length() <= 20) {
            return true;
        } else {
            makeToast(R.string.please_input_legitimate_password);
            return false;
        }
    }


    /**
     * 出现软键盘时，登录按钮及其以上部分内容向上移动一定距离 目的是使登录按钮不被遮盖住
     */
    @SuppressLint("NewApi")
    @Override
    protected void OnSoftInputShow(int height) {
        // 屏幕高度
        Point outSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(outSize);
        int windowH = outSize.y;

        int gapHeight = height
                - (windowH - statusBarHeight - mTitleView.getHeight() - containerLlyt
                .getBottom());

        if (gapHeight < 0) {
            gapHeight = 0;
        }

        softInputGap = -gapHeight;

        if (newConfirmEt.isFocused() && height > 0) {
            containerLlyt.setPadding(0, softInputGap, 0, 0);
        } else {
            containerLlyt.setPadding(0, 0, 0, 0);
        }
    }

    // 接下来是三个TextWatcher的方法
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String oldPwd = oldPasswordEt.getText().toString();
        String newPwd = newPasswordEt.getText().toString();
        String confirmPwd = newConfirmEt.getText().toString();
        if (oldPwd.length() > 0 && newPwd.length() > 0
                && confirmPwd.length() > 0) {
            confirmTv.setEnabled(true);
        } else {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlyt_old_password:
                oldPasswordEt.requestFocus();
                UIUtils.showSoftInput(oldPasswordEt, true);
                break;
            case R.id.rlyt_new_password:
                newPasswordEt.requestFocus();
                UIUtils.showSoftInput(newPasswordEt, true);
                break;
            case R.id.rlyt_new_password_confirm:
                newConfirmEt.requestFocus();
                UIUtils.showSoftInput(newConfirmEt, true);
                break;
        }
    }

}
