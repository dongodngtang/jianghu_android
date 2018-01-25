/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: SettingPhoneNunberActivity.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-5
 *
 * Changes (from 2015-10-5)
 * -----------------------------------------------------------------
 * 2015-10-5 创建SettingPhoneNunberActivity.java (ChenWeiZhen);
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
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.event.open.BindPhoneEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.biz.openapi.presenter.AccountHelper;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.util.InputTools;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;


public class SettingBindPhoneActivity extends BaseActivity implements
        OnClickListener, TextWatcher {

    private final static int MSG_PHONE_ALREADY_REGISTERED = 1101012; // 已经注册过
    private final static int MSG_NO_PERMISSION = 1000000; // 无法重复获取验证码
    private final static int MSG_PHONE_VCODE_NOT_EXIST = 1000003; // 验证码错误


    private TextView btn_binding;
    private ImageView nowBindIv;

    private TextView phoneHintTv;

    private EditText bindPhoneEt;
    private EditText bindVcodeEt;
    private TextView getVcodeTv;

    private Timer timer;

    private String newPhone;
    private String bindVcode;

    private final int MSG_CHANGE_TIME = 0;
    private final int MSG_STOP_TIME = 1;

    private boolean isRequest = false; // 判断位，是否正在等待请求响应
    private boolean isCountDown = false; // 判断位，是否正在倒数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_setting_bind_phone);
        super.onCreate(savedInstanceState);

        bindControl();

        setListener();

        setResource();

    }

    /**
     * 绑定控件
     */
    private void bindControl() {

        btn_binding = (TextView) findViewById(R.id.btn_binding);

        nowBindIv = (ImageView) findViewById(R.id.iv_now_bind_phone);

        phoneHintTv = (TextView) findViewById(R.id.tv_phone_hint);

        bindPhoneEt = (EditText) findViewById(R.id.et_bind_phone);
        bindVcodeEt = (EditText) findViewById(R.id.et_bind_verify_code);
        getVcodeTv = (TextView) findViewById(R.id.tv_get_verify_code);

    }

    /**
     * 给控件添加监听者
     */
    private void setListener() {
        btn_binding.setOnClickListener(this);
        getVcodeTv.setOnClickListener(this);
        btn_binding.setClickable(false);
        getVcodeTv.setClickable(false);

        bindPhoneEt.addTextChangedListener(this);
        bindVcodeEt.addTextChangedListener(this);
        adjustWithShowInput();

        bindVcodeEt.setOnKeyListener(new OnKeyListener() {

            @SuppressLint("NewApi")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    btn_binding.callOnClick();
                }
                return false;
            }
        });
    }

    /**
     * 设置控件显示数据
     */
    private void setResource() {
        LoginUser cUser = UserInfoUtil.getInstance().getCurrentUser();
        if (cUser.getUser().getMobile() != null && !cUser.getUser().getMobile().equals("")) {
            // 已经绑定
            nowBindIv.setImageResource(R.drawable.binding_phone_pic_done);

            phoneHintTv.setText(getResources().getString(
                    R.string.phone_hint_now) + " : " + cUser.getUser().getMobile());
            bindPhoneEt.setHint("新的手机号");
        } else {
            // 未绑定
            nowBindIv.setImageResource(R.drawable.binding_phone_pic_undone);
            bindPhoneEt.setHint("手机号码");
            phoneHintTv.setText(getResources().getString(
                    R.string.phone_hint_unbind));

        }
    }

    @Override
    public void onClick(View v) {
        if (!v.isClickable()) {
            return;
        }

        LoginUser cUser = UserInfoUtil.getInstance().getCurrentUser();

        switch (v.getId()) {
            case R.id.btn_binding:
                // 发送验证请求
                if (checkPhoneNumberInput()) {
                    if (!isRequest) {
                        InputTools.HideKeyboard(v);
                        newPhone = bindPhoneEt.getText().toString();
                        bindVcode = bindVcodeEt.getText().toString();
                        AccountHelper.getInstance().bindMobile(this, newPhone, bindVcode, bindListener);

                        isRequest = true;

                        makeToast(R.string.verifying);
                    }
                }

                break;
            case R.id.tv_get_verify_code:
                // 变灰，不可点击
                if (!bindPhoneEt.getText().toString().equals(cUser.getUser().getMobile())) {
                    getVcodeTv.setTextColor(getResources().getColor(
                            R.color.unclickable));
                    getVcodeTv.setClickable(false);
                    getVcodeTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_blue_button));
                    InputTools.HideKeyboard(v);
                    AccountHelper.getInstance().bindMobileAuthVcode(this, bindPhoneEt.getText()
                            .toString().trim(), authListener);
                } else {
                    makeToast(R.string.phone_is_bangding);
                }
                break;

            default:
                break;
        }

        closeInputSoft();
    }

    /**
     * 检查手机号码输入格式是否正确
     */
    private boolean checkPhoneNumberInput() {
        if (bindPhoneEt.getText().toString().length() == 11) {
            return true;
        } else if (bindPhoneEt.getText().toString().length() < 11) {
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
            SettingBindPhoneMsg msg;

            @Override
            public void run() {
                String remainingTimeStr = getVcodeTv.getText().toString();
                int remainingTime = Integer.parseInt(remainingTimeStr
                        .substring(0, remainingTimeStr.indexOf(getResources()
                                .getString(R.string.second))));
                remainingTime--;

                if (msg == null) {
                    msg = new SettingBindPhoneMsg();
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
    public void onEventMainThread(SettingBindPhoneMsg msg) {
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
                getVcodeTv.setTextColor(getResources().getColor(R.color.txt_dark_content));
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
     * "请求发送验证码"接口的响应
     */
    IOnResponseListener authListener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            getVerifyCodeSuccess();
        }

        @Override
        public void onError(Response response) {
            if (response.getCode() == -1) {

                makeToast(response.getMsg());

                // 验证码发送失败，可点击
                getVcodeTv.setTextColor(getResources().getColor(R.color.txt_dark_content));
                getVcodeTv.setClickable(true);
                getVcodeTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_login_button));

            } else {

                if (response.getCode() == MSG_NO_PERMISSION) {
                    getVerifyCodeSuccess();
                } else {
                    // 验证码发送失败，可点击
                    getVcodeTv.setTextColor(getResources().getColor(R.color.txt_dark_content));
                    getVcodeTv.setClickable(true);
                    getVcodeTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_login_button));
                }
                makeToast(response.getMsg());

            }
        }
    };


    /**
     * "检查验证码"接口的响应
     */
    IOnResponseListener bindListener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            if (timer != null) {
                timer.cancel();
            }

            makeToast(R.string.bind_phone_succeed);

            // 修改用户信息,就不另外再重新获取数据了
            LoginUser cUser = UserInfoUtil.getInstance().getCurrentUser();
            cUser.getUser().setMobile(newPhone);
            SharedPreferencesManager.setUserInfo(SettingBindPhoneActivity.this, cUser);

            CurrentUserDetails cuDetails = SharedPreferencesManager.getUserDetailsInfo(SettingBindPhoneActivity.this);
            cuDetails.setMobile(newPhone);
            SharedPreferencesManager.setUserDetailsInfo(SettingBindPhoneActivity.this, cuDetails);
            EventBus.getDefault().post(new BindPhoneEvent(newPhone));
            finish();
        }

        @Override
        public void onError(Response response) {
            makeToast(response.getMsg());
        }
    };


    // 接下来是三个TextWatcher的方法
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        String phoneNum = bindPhoneEt.getText().toString();
        String vcode = bindVcodeEt.getText().toString();

        if (!isRequest && !isCountDown) {
            if (phoneNum.length() > 0) {
                getVcodeTv.setTextColor(getResources().getColor(
                        R.color.txt_dark_content));
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
            btn_binding.setTextColor(getResources().getColor(R.color.txt_dark_content));
            btn_binding.setClickable(true);
            btn_binding.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_login_button));
        } else {
            btn_binding
                    .setTextColor(getResources().getColor(R.color.text_common_hint));
            btn_binding.setClickable(false);
            btn_binding.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_blue_button));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private static class SettingBindPhoneMsg {
        public int what;
        public int remainingTime;
    }

}
