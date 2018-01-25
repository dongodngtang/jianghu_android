/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: BindPhoneActivity.java
 * Author: ZhangKai
 * Version: 1.0
 * Create: 2015-10-03
 *
 * Changes (from 2015-10-03)
 * -----------------------------------------------------------------
 * 2015-10-03 创建BindPhoneActivity.java (ZhangKai);
 * -----------------------------------------------------------------
 * 2015-10-15 界面响应添加、接口调用 (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.event.LogoutEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.AccountHelper;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.util.InputTools;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

public class BindPhoneActivity extends BaseActivity implements OnClickListener,
        TextWatcher {

    private final static int MSG_PHONE_ALREADY_REGISTERED = 1101012; // 已经注册过
    private final static int MSG_NO_PERMISSION = 1000000; // 无法重复获取验证码
    private final static int MSG_PHONE_VCODE_NOT_EXIST = 1000003; // 验证码错误

    private RelativeLayout topBarRlyt;
    private LinearLayout containerLlyt;

    private  RelativeLayout rl_bind_phone_num;
    private  RelativeLayout rl_bind_phone_vcode;

    private LinearLayout rollBackIv;
    private TextView skipTv;

    private EditText phoneNumderEt;
    private TextView getVcodeTv;
    private EditText verifyCodeEt;
    private TextView submitBtn;

    private Timer timer;

    private final int MSG_CHANGE_TIME = 0;
    private final int MSG_STOP_TIME = 1;

    private int softInputGap = 0;

    private boolean isCountDown = false; // 判断位，是否正在倒数
    private boolean isRequest = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_bind_phone);
        super.onCreate(savedInstanceState);

        bindControl();

        setListener();

    }

    /**
     * 绑定控件
     */
    private void bindControl() {
        topBarRlyt = (RelativeLayout) findViewById(R.id.rlyt_top_bar);
        containerLlyt = (LinearLayout) findViewById(R.id.llyt_container);

        rollBackIv = (LinearLayout) findViewById(R.id.title_left_content);
        skipTv = (TextView) findViewById(R.id.tv_skip);

        phoneNumderEt = (EditText) findViewById(R.id.et_bind_phone_numder);
        getVcodeTv = (TextView) findViewById(R.id.tv_get_verify_code);
        verifyCodeEt = (EditText) findViewById(R.id.et_bind_verify_code);
        submitBtn = (TextView) findViewById(R.id.btn_bind_phone_submit);

        rl_bind_phone_num = (RelativeLayout) findViewById(R.id.rl_bind_phone_num);
        rl_bind_phone_vcode = (RelativeLayout) findViewById(R.id.rl_bind_phone_vcode);
    }

    /**
     * 给控件添加监听者
     */
    private void setListener() {
        rollBackIv.setOnClickListener(this);
        skipTv.setOnClickListener(this);
        getVcodeTv.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        getVcodeTv.setTextColor(getResources().getColor(R.color.unclickable));
        getVcodeTv.setClickable(false);

        phoneNumderEt.addTextChangedListener(this);
        verifyCodeEt.addTextChangedListener(this);
        adjustWithShowInput();

        verifyCodeEt.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    containerLlyt.setPadding(0, softInputGap, 0, 0);
                    rl_bind_phone_vcode.setBackgroundResource(R.drawable.shape_common_button_normal);
                } else {
                    containerLlyt.setPadding(0, 0, 0, 0);
                    rl_bind_phone_vcode.setBackgroundResource(R.drawable.shape_login_button);
                }
            }
        });

        verifyCodeEt.setOnKeyListener(new OnKeyListener() {

            @SuppressLint("NewApi")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    submitBtn.callOnClick();
                }
                return false;
            }
        });
        phoneNumderEt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rl_bind_phone_num.setBackgroundResource(R.drawable.shape_common_button_normal);
                } else {
                    rl_bind_phone_num.setBackgroundResource(R.drawable.shape_login_button);
                }
            }
        });


        submitBtn.setClickable(false);
    }

    @Override
    public void onClick(View v) {
        if (!v.isClickable()) {
            return;
        }

        switch (v.getId()) {
            case R.id.title_left_content:
                rollBack(rollBackIv);
                SharedPreferencesManager.clearUserInfo(this);
                EventBus.getDefault().post(new LogoutEvent());
                break;
            case R.id.tv_skip:
                // 打开首页
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                System.out.println("打开新的主页");
                startActivity(intent);

                //关掉其他activity,除了自己
                MyApplication.getInstance().removeActivity(this);
                MyApplication.getInstance().finishActivies();


                finish();
                break;
            case R.id.tv_get_verify_code:
                if (checkPhoneNumberInput()) {


                    if (UserInfoUtil.getInstance().isLogin()) {

                        InputTools.HideKeyboard(v);
                        AccountHelper.getInstance().bindMobileAuthVcode(this,getMobile(),authListener);


                        // 变灰，不可点击
                        getVcodeTv.setTextColor(getResources().getColor(
                                R.color.unclickable));
                        getVcodeTv.setClickable(false);
                        getVcodeTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_blue_button));
                        skipTv.setTextColor(getResources().getColor(
                                R.color.unclickable));
                        skipTv.setClickable(false);

                        makeToast(R.string.wait_for_send_vcode);
                    } else {
                        makeToast(R.string.please_to_login);
                    }
                }
                break;
            case R.id.btn_bind_phone_submit:
                if (checkPhoneNumberInput()) {
                    InputTools.HideKeyboard(v);
                    AccountHelper.getInstance().bindMobile(this,getMobile(),getVcode(),bindListener);

                    isRequest = true;

                    submitBtn.setTextColor(getResources().getColor(
                            R.color.white));
                    submitBtn.setClickable(false);
                    submitBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_blue_button));


                    skipTv.setTextColor(getResources()
                            .getColor(R.color.unclickable));
                    skipTv.setClickable(false);
                }
                break;

            default:
                break;
        }
    }

    IOnResponseListener bindListener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            if (timer != null) {
                timer.cancel();
            }
            makeToast(R.string.bind_phone_succeed);

            //关掉其他activity,除了自己
            MyApplication.getInstance().removeActivity(BindPhoneActivity.this);
            MyApplication.getInstance().finishActivies();

            // 打开首页
            Intent intent = new Intent();
            intent.setClass(BindPhoneActivity.this, MainActivity.class);
            System.out.println("打开新的主页");
            startActivity(intent);

            finish();
        }

        @Override
        public void onError(Response response) {
            if (response.getCode() == -1) {

                makeToast(response.getMsg());
            } else {
                System.out.println("code != 0---------------msg="
                        + response.getMsg());

                if (response.getCode() == MSG_PHONE_VCODE_NOT_EXIST) {
                    makeToast(R.string.verify_code_is_wrong);
                } else {
                    makeToast(response.getMsg());
                }
            }

            isRequest = false;

            submitBtn.setTextColor(getResources().getColor(R.color.white_word));
            submitBtn.setClickable(true);
            submitBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_login_button));

            skipTv.setTextColor(getResources().getColor(
                    R.color.white));
            skipTv.setClickable(true);
        }
    };


    /**
     * 请求验证码响应
     */
    IOnResponseListener authListener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            getVerifyCodeSuccess();
            skipTv.setTextColor(getResources().getColor(
                    R.color.white));
            skipTv.setClickable(true);
        }

        @Override
        public void onError(Response response) {

            if (response.getCode() == -1) {

                makeToast(response.getMsg());

                // 验证码发送失败，可点击
                getVcodeTv.setTextColor(getResources().getColor(
                        R.color.white));
                getVcodeTv.setClickable(true);
                getVcodeTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_login_button));
            } else {
                System.out.println("code != 0---------------msg="
                        + response.getMsg());

                if (response.getCode() == MSG_NO_PERMISSION) {
//					makeToast(R.string.no_permission);
                    getVerifyCodeSuccess();
                } else {
                    // 验证码发送失败，可点击
                    getVcodeTv.setTextColor(getResources().getColor(R.color.white));
                    getVcodeTv.setClickable(true);
                    getVcodeTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_login_button));
                }
                makeToast(response.getMsg());
            }

            skipTv.setTextColor(getResources().getColor(
                    R.color.white));
            skipTv.setClickable(true);
        }
    };

    private String getVcode(){
        return verifyCodeEt.getText().toString().trim();
    }

    private String getMobile(){
        return phoneNumderEt.getText()
                .toString().trim();
    }

    /**
     * 检查手机号码输入格式是否正确
     */
    private boolean checkPhoneNumberInput() {
        if (phoneNumderEt.getText().toString().length() == 11) {
            return true;
        } else if (phoneNumderEt.getText().toString().length() < 11) {
            makeToast(R.string.please_input_short_phone_number);
            return false;
        } else {
            makeToast(R.string.please_input_long_phone_number);
            return false;
        }
    }

    /**
     * 60秒倒计时
     */
    private void countDown() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            BindPhoneMsg msg;

            @Override
            public void run() {
                String remainingTimeStr = getVcodeTv.getText().toString();
                int remainingTime = Integer.parseInt(remainingTimeStr
                        .substring(0, remainingTimeStr.indexOf(getResources()
                                .getString(R.string.second))));
                remainingTime--;

                if (msg == null) {
                    msg = new BindPhoneMsg();
                }
                if (remainingTime < 0) {
                    msg.what = MSG_STOP_TIME;
                } else {
                    msg.what = MSG_CHANGE_TIME;
                    msg.remainingTime = remainingTime;
                }
                EventBus.getDefault().post(msg);
            }
        }, 1000, 1000);
    }

    /**
     * 60秒倒计时,计时器msg得接收处理
     *
     * @param msg
     */
    public void onEventMainThread(BindPhoneMsg msg) {
        switch (msg.what) {
            case MSG_CHANGE_TIME:
                isCountDown = true;
                getVcodeTv.setText(msg.remainingTime
                        + getResources().getString(R.string.second));
                break;
            case MSG_STOP_TIME:
                isCountDown = false;
                timer.cancel();
                getVcodeTv.setText(getResources().getString(
                        R.string.register_get_verify_code));
                getVcodeTv.setTextColor(getResources().getColor(R.color.white));
                getVcodeTv.setClickable(true);
                getVcodeTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_login_button));
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }



    /**
     * 获取验证码成功（接口返回成功，或者不能重复获取时调用）
     */
    private void getVerifyCodeSuccess() {
//		makeToast(R.string.send_verify_code);

        // 转换成60s倒数
        getVcodeTv.setText(getResources().getString(
                R.string.second_sixty));
        countDown();
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
                - (windowH - statusBarHeight - topBarRlyt.getHeight() - containerLlyt
                .getBottom());

        if (gapHeight < 0) {
            gapHeight = 0;
        }

        softInputGap = -gapHeight;

        if (verifyCodeEt.isFocused() && height > 0) {
            containerLlyt.setPadding(0, softInputGap, 0, 0);
        } else {
            containerLlyt.setPadding(0, 0, 0, 0);
        }
    }

    // 接下来是三个TextWatcher的方法

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        String phoneNum = phoneNumderEt.getText().toString();
        String vcode = verifyCodeEt.getText().toString();

        if (!isRequest && !isCountDown) {
            if (phoneNum.length() > 0) {
                getVcodeTv.setTextColor(getResources().getColor(
                        R.color.white));
                getVcodeTv.setClickable(true);
                getVcodeTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_login_button));
            } else {
                getVcodeTv.setTextColor(getResources().getColor(
                        R.color.unclickable));
                getVcodeTv.setClickable(false);
                getVcodeTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_blue_button));
            }
        }

        if (phoneNum.length() > 0 && vcode.length() > 0) {
            submitBtn.setTextColor(getResources().getColor(R.color.white));
            submitBtn.setClickable(true);
            submitBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_login_button));
        } else {
            submitBtn.setTextColor(getResources().getColor(
                    R.color.unclickable));
            submitBtn.setClickable(false);
            submitBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_blue_button));
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private static class BindPhoneMsg {
        public int what;
        public int remainingTime;
    }
}
