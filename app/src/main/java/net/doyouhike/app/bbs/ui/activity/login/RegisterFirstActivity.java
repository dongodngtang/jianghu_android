/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: RegisterFirstActivity.java
 * Author: ZhangKai
 * Version: 1.0
 * Create: 2015-10-03
 *
 * Changes (from 2015-10-03)
 * -----------------------------------------------------------------
 * 2015-10-03 创建RegisterFirstActivity.java (ZhangKai);
 * -----------------------------------------------------------------
 * 2015-10-13 界面响应添加、接口调用 (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.event.login.AccountVcodeRespEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.nohttp.CallServer;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.presenter.AccountHelper;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.util.StateButtomHelper;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.library.ui.utils.BaseBitmapUtil;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

public class RegisterFirstActivity extends BaseActivity implements
        OnClickListener, TextWatcher {

    private final static int MSG_PHONE_ALREADY_REGISTERED = 1101012; // 已经注册过
    private final static int MSG_NO_PERMISSION = 1000000; // 无法重复获取验证码
    private final static int MSG_PHONE_VCODE_NOT_EXIST = 110010; // 验证码错误
    private final static int MSG_PHONE_VCODE_OVER_REQUEST = 1000007;//请求次数过多

    private RelativeLayout topBarRlyt;
    private LinearLayout containerLlyt;

    private RelativeLayout rl_register_phone;

    private RelativeLayout rl_register_vcode;

    private RelativeLayout rl_register_pwd;

    private EditText phoneNumderEt;
    private TextView getVcodeTv;
    private EditText verifyCodeEt;
    private EditText passwordEt;
    private ImageView consentAgreementIv;
    private TextView agreementTv;
    private TextView finishInputBtn;

    private Timer timer;

    private final int MSG_CHANGE_TIME = 0;
    private final int MSG_STOP_TIME = 1;

    private int softInputGap = 0;

    private boolean isRequest = false; // 判断位，是否正在等待请求响应
    private boolean isCountDown = false; // 判断位，是否正在倒数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_register_first);
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

        phoneNumderEt = (EditText) findViewById(R.id.et_register_phone_numder);
        getVcodeTv = (TextView) findViewById(R.id.tv_get_verify_code);
        verifyCodeEt = (EditText) findViewById(R.id.et_register_verify_code);
        passwordEt = (EditText) findViewById(R.id.et_register_password);
        consentAgreementIv = (ImageView) findViewById(R.id.iv_consent_agreement);
        agreementTv = (TextView) findViewById(R.id.tv_agreement);
        finishInputBtn = (TextView) findViewById(R.id.btn_register_finish_input);

        rl_register_phone = (RelativeLayout) findViewById(R.id.rl_register_phone);
        rl_register_vcode = (RelativeLayout) findViewById(R.id.rl_register_vcode);
        rl_register_pwd = (RelativeLayout) findViewById(R.id.rl_register_pwd);
    }

    /**
     * 给控件添加监听者
     */
    private void setListener() {
        getVcodeTv.setOnClickListener(this);
        consentAgreementIv.setOnClickListener(this);
        agreementTv.setOnClickListener(this);
        finishInputBtn.setOnClickListener(this);

        getVcodeTv.setTextColor(getResources().getColor(R.color.unclickable));
        getVcodeTv.setClickable(false);
        consentAgreementIv.setSelected(true);
        finishInputBtn.setEnabled(false);

        phoneNumderEt.addTextChangedListener(this);
        verifyCodeEt.addTextChangedListener(this);
        passwordEt.addTextChangedListener(this);
        adjustWithShowInput();

        passwordEt.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    containerLlyt.setPadding(0, softInputGap, 0, 0);
                } else {
                    containerLlyt.setPadding(0, 0, 0, 0);
                }
            }
        });
        phoneNumderEt.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rl_register_phone.setBackgroundDrawable(BaseBitmapUtil.getDrawable(RegisterFirstActivity.this, R.drawable.shape_common_button_normal));
                } else {
                    rl_register_phone.setBackgroundDrawable(BaseBitmapUtil.getDrawable(RegisterFirstActivity.this, R.drawable.shape_login_button));
                }
            }
        });

        verifyCodeEt.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rl_register_vcode.setBackgroundDrawable(BaseBitmapUtil.getDrawable(RegisterFirstActivity.this, R.drawable.shape_common_button_normal));
                } else {
                    rl_register_vcode.setBackgroundDrawable(BaseBitmapUtil.getDrawable(RegisterFirstActivity.this, R.drawable.shape_login_button));
                }
            }
        });

        passwordEt.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rl_register_pwd.setBackgroundDrawable(BaseBitmapUtil.getDrawable(RegisterFirstActivity.this, R.drawable.shape_common_button_normal));
                } else {
                    rl_register_pwd.setBackgroundDrawable(BaseBitmapUtil.getDrawable(RegisterFirstActivity.this, R.drawable.shape_login_button));
                }
            }
        });

        passwordEt.setOnKeyListener(new OnKeyListener() {

            @SuppressLint("NewApi")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    finishInputBtn.callOnClick();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!v.isClickable()) {
            return;
        }

        switch (v.getId()) {
            case R.id.tv_get_verify_code:
                if (checkPhoneNumberInput()) {
                    // 变灰，不可点击
                    getVcodeTv.setTextColor(getResources().getColor(
                            R.color.unclickable));
                    getVcodeTv.setClickable(false);
                    getVcodeTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_blue_button));

                    String mobile = phoneNumderEt.getText().toString().trim();

                    AccountHelper.getInstance().getVCode(mobile, "register", this);

                }
                closeInputSoft();
                break;
            case R.id.iv_consent_agreement:
                if (consentAgreementIv.isSelected()) {
                    consentAgreementIv.setSelected(false);

                    StateButtomHelper.setUnclickableView(finishInputBtn);

                } else {
                    consentAgreementIv.setSelected(true);

                    String phoneNum = phoneNumderEt.getText().toString();
                    String vcode = verifyCodeEt.getText().toString();
                    String password = passwordEt.getText().toString();

                    if (phoneNum.length() > 0 && vcode.length() > 0
                            && password.length() > 0) {
                        StateButtomHelper.setNormalView(finishInputBtn);
                    } else {
                        StateButtomHelper.setUnclickableView(finishInputBtn);
                    }
                }
                break;
            case R.id.tv_agreement:
                // 打开用户协议页面
                Intent intent = new Intent();
                intent.setClass(this, AgreementActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register_finish_input:
                if (checkPhoneNumberInput() && checkPasswordInput()) {
                    if (!isRequest) {
                        StateButtomHelper.setLoadingView(finishInputBtn, "我输入好了...");
                        String mobile = phoneNumderEt.getText().toString().trim();
                        String vcode = verifyCodeEt.getText().toString().trim();
                        AccountHelper.getInstance().verifyVcode(mobile, vcode, "register", this, verifyVcodeListener);

                        isRequest = true;
                    }
                }

                closeInputSoft();
                break;

            default:
                break;
        }
    }

    /**
     * "检查验证码"接口的响应
     *
     * @param response
     */
    IOnResponseListener verifyVcodeListener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            StateButtomHelper.setNormalView(finishInputBtn, "我输入好了");

            if (timer != null) {
                timer.cancel();
            }
            Intent intent = new Intent();
            intent.setClass(RegisterFirstActivity.this, RegisterSecondActivity.class);
            intent.putExtra("PARAM_PHONE_NUMBER", phoneNumderEt
                    .getText().toString());
            intent.putExtra("PARAM_PASSWORD", passwordEt
                    .getText().toString());
            intent.putExtra("PARAM_VCODE", verifyCodeEt.getText()
                    .toString());
            startActivity(intent);

            finish();
        }

        @Override
        public void onError(Response response) {
            StateButtomHelper.setNormalView(finishInputBtn, "我输入好了");
            if (response.getCode() == -1) {
                isRequest = false;
                System.out.println("onFailure,ErrorMessage="
                        + response.getMsg());
                makeToast(response.getMsg());
            } else {
                isRequest = false;
                System.out.println("code != 0---------------msg="
                        + response.getMsg());

                if (response.getCode() == MSG_PHONE_VCODE_NOT_EXIST) {
                    makeToast(R.string.verify_code_is_wrong);
                } else if (response.getCode() == MSG_PHONE_VCODE_OVER_REQUEST) {
                    makeToast(response.getMsg());
                } else {
                    makeToast(response.getMsg());
                }
            }
        }
    };

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
     * 检查密码输入格式是否正确
     */
    private boolean checkPasswordInput() {
        String passwordStr = passwordEt.getText().toString();
        if (passwordStr.length() >= 6 && passwordStr.length() <= 20) {
            return true;
        } else {
            makeToast(R.string.please_input_legitimate_password);
            return false;
        }
    }

    /**
     * 60秒倒计时
     */
    private void countDown() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            RegisterFirsMsg msg;

            @Override
            public void run() {
                String remainingTimeStr = getVcodeTv.getText().toString();


                String strTime = "0";

                if (!TextUtils.isEmpty(remainingTimeStr)) {
                    strTime = remainingTimeStr.replace(getResources()
                            .getString(R.string.second), "");
                }

                int remainingTime = 0;

                if (!TextUtils.isEmpty(strTime)) {
                    remainingTime = Integer.parseInt(strTime);
                }


                remainingTime--;

                if (msg == null) {
                    msg = new RegisterFirsMsg();
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
    public void onEventMainThread(RegisterFirsMsg msg) {
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
                        R.string.register_get_again_vcode));
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
        System.gc();
        super.onDestroy();
    }

    // 接收EventBus发出的请求结果（注意：参数类型一致时才能接收到）

    /**
     * "请求发送验证码"接口的响应
     *
     * @param response
     */
    public void onEventMainThread(AccountVcodeRespEvent response) {
        if (response.getCode() == 0) {
            getVerifyCodeSuccess();
        } else if (response.getCode() == -1) {

            makeToast(response.getCode());

            // 验证码发送失败，可点击
            getVcodeTv.setTextColor(getResources().getColor(R.color.white));
            getVcodeTv.setClickable(true);
            getVcodeTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_login_button));

        } else {


            if (response.getCode() == MSG_NO_PERMISSION) {
//				makeToast(R.string.no_permission);
                getVerifyCodeSuccess();
            } else {
                // 验证码发送失败，可点击
                getVcodeTv.setTextColor(getResources().getColor(R.color.white));
                getVcodeTv.setClickable(true);
                getVcodeTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_login_button));
            }

            TipUtil.alert(this, response.getMsg());
        }
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

        if (passwordEt.isFocused() && height > 0) {
            containerLlyt.setPadding(0, softInputGap, 0, 0);
        } else {
            containerLlyt.setPadding(0, 0, 0, 0);
        }
    }

    // TextWatcher的方法
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String phoneNum = phoneNumderEt.getText().toString();
        String vcode = verifyCodeEt.getText().toString();
        String password = passwordEt.getText().toString();

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

        if (phoneNum.length() > 0 && vcode.length() > 0
                && password.length() > 5 && consentAgreementIv.isSelected()) {
            StateButtomHelper.setNormalView(finishInputBtn);
        } else {

            StateButtomHelper.setUnclickableView(finishInputBtn);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private static class RegisterFirsMsg {
        public int what;
        public int remainingTime;
    }
}
