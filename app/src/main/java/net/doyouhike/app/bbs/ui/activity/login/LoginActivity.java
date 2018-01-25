/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: LoginActivity.java
 * Author: ZhangKai
 * Version: 1.0
 * Create: 2015-10-03
 *
 * Changes (from 2015-10-03)
 * -----------------------------------------------------------------
 * 2015-10-03 创建LoginActivity.java (ZhangKai);
 * -----------------------------------------------------------------
 * 2015-10-09 界面响应添加、接口调用 (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.event.LogoutEvent;
import net.doyouhike.app.bbs.biz.event.login.AccountPasswordResetEvent;
import net.doyouhike.app.bbs.biz.event.login.AccountRegisterEvent;
import net.doyouhike.app.bbs.biz.event.login.LoginEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.AccountHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.ui.activity.start.NewGuideActivity;
import net.doyouhike.app.bbs.ui.activity.start.StartActivity;
import net.doyouhike.app.bbs.util.InputTools;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.SpTools;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.library.ui.utils.BaseBitmapUtil;

import java.util.List;

import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity implements OnClickListener,
        TextWatcher {

    private static final int RESPONSE_MSG_PHONE_NUMBER_FORMAT_ERROR = 100001; // 用户名格式错误（全空格）
    private static final int RESPONSE_MSG_USER_NOT_EXIST = 1100006; // 用户不存在
    private static final int RESPONSE_MSG_PASSWORD_ERROR = 1100007; // 密码错误

    private static String TAG = "LoginActivity";

    private RelativeLayout userNameRlyt;
    private ImageView userNameIconIv;
    private EditText userNameInputEt;

    private RelativeLayout passawordRlyt;
    private ImageView passwordIconIv;
    private EditText passwordInputEt;
    private ImageView passwordClearIv;
    private ImageView userNameClearIv;

    private TextView loginBtn;
    // private ProgressBar loginPb;

    private RelativeLayout gotoRlty;
    private TextView gotoRegisterTv;
    private TextView gotoForgerPasswordTv;
    private TextView lookAroundTv;

    private LoginUser currentUser;

    private boolean isfirstIn = true;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewsAndEvents() {

        bindControl();

        setListener();
        isfirstIn = SpTools.getBoolean(this, StartActivity.FIRSTIN, true);
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }


    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    /**
     * 绑定控件
     */
    private void bindControl() {
        userNameRlyt = (RelativeLayout) findViewById(R.id.rlyt_user_name_input);
        userNameIconIv = (ImageView) findViewById(R.id.iv_user_name_icon);
        userNameInputEt = (EditText) findViewById(R.id.et_user_name_input);

        passawordRlyt = (RelativeLayout) findViewById(R.id.rlyt_password_input);
        passwordIconIv = (ImageView) findViewById(R.id.iv_passwodrd_icon);
        passwordInputEt = (EditText) findViewById(R.id.et_passwodrd_input);
        passwordClearIv = (ImageView) findViewById(R.id.iv_passwodrd_clear);
        userNameClearIv = (ImageView) findViewById(R.id.iv_user_name_clear);

        loginBtn = (TextView) findViewById(R.id.btn_login_button);
        // loginPb = (ProgressBar) findViewById(R.id.pb_login);
        // loginPb.setVisibility(View.GONE);

        gotoRlty = (RelativeLayout) findViewById(R.id.rlyt_goto);
        gotoRegisterTv = (TextView) findViewById(R.id.tv_goto_register);
        gotoForgerPasswordTv = (TextView) findViewById(R.id.tv_goto_forget_password);
        lookAroundTv = (TextView) findViewById(R.id.tv_look_around);

        InputTools.ShowKeyboard(userNameInputEt);

    }

    private void setClickale(boolean click) {
        gotoRegisterTv.setClickable(click);
        gotoForgerPasswordTv.setClickable(click);
        lookAroundTv.setClickable(click);
        loginBtn.setEnabled(click);
        passwordClearIv.setEnabled(click);
        userNameClearIv.setEnabled(click);
        passwordInputEt.setEnabled(click);
        userNameInputEt.setEnabled(click);
    }

    /**
     * 给控件添加监听者
     */
    private void setListener() {
        userNameInputEt.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                UIUtils.showView(userNameClearIv, userNameInputEt.getText().toString().length() > 0 &&
                        hasFocus);

                if (hasFocus) {
                    userNameIconIv.setImageDrawable(BaseBitmapUtil.getDrawableFromRes(LoginActivity.this, R.drawable.ic_login_user_3x));
                    userNameRlyt.setBackgroundDrawable(BaseBitmapUtil.getDrawable(LoginActivity.this, R.drawable.shape_common_button_normal));
                } else {
                    userNameIconIv.setImageDrawable(BaseBitmapUtil.getDrawableFromRes(LoginActivity.this, R.drawable.ic_login_user_3x_c));
                    userNameRlyt.setBackgroundDrawable(BaseBitmapUtil.getDrawable(LoginActivity.this, R.drawable.shape_login_button));

                }
            }
        });
        passwordInputEt.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                UIUtils.showView(passwordClearIv, passwordInputEt.getText().toString().length() > 0 &&
                        hasFocus);

                if (hasFocus) {
                    passwordIconIv.setImageDrawable(BaseBitmapUtil.getDrawableFromRes(LoginActivity.this, R.drawable.ic_login_key_3x));
                    passawordRlyt.setBackgroundDrawable(BaseBitmapUtil.getDrawable(LoginActivity.this, R.drawable.shape_common_button_normal));
                } else {
                    passwordIconIv.setImageDrawable(BaseBitmapUtil.getDrawableFromRes(LoginActivity.this, R.drawable.ic_login_key_3x));
                    passawordRlyt.setBackgroundDrawable(BaseBitmapUtil.getDrawable(LoginActivity.this, R.drawable.shape_login_button));
                }
            }
        });


        userNameInputEt.addTextChangedListener(this);
        passwordInputEt.addTextChangedListener(this);

        passwordClearIv.setOnClickListener(this);
        userNameClearIv.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        gotoRegisterTv.setOnClickListener(this);
        gotoForgerPasswordTv.setOnClickListener(this);
        lookAroundTv.setOnClickListener(this);

        loginBtn.setEnabled(false);

        passwordInputEt.setOnKeyListener(new OnKeyListener() {

            @SuppressLint("NewApi")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    loginBtn.callOnClick();
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

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_passwodrd_clear:
                passwordInputEt.setText("");
                break;
            case R.id.iv_user_name_clear:
                userNameInputEt.setText("");
                break;
            case R.id.btn_login_button:

                AccountHelper.getInstance().login(userNameInputEt.getText().toString(),
                        passwordInputEt.getText().toString(), null, this, listener);

                // loginPb.setVisibility(View.VISIBLE);

                loginBtn.setText(getResources().getString(R.string.logging));
                loginBtn.setTextColor(getResources().getColor(
                        R.color.transparency_white_word));
                InputTools.HideKeyboard(v);
                setClickale(false);
                break;
            case R.id.tv_goto_register:

                intent.setClass(LoginActivity.this, RegisterFirstActivity.class);
                startActivity(intent);

                // loginPb.setVisibility(View.GONE);
                break;
            case R.id.tv_goto_forget_password:
                intent.setClass(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
//                 loginPb.setVisibility(View.GONE);

                break;
            case R.id.tv_look_around:
                // 关闭之前可能由于“随便看看”打开的界面
//                EventBus.getDefault().post(new LogoutEvent());

                intent.setClass(LoginActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.INTENT_EXTRA_NAME_IS_LOOK_AROUND, true);
                startActivity(intent);
                finish();

                break;

            default:
                break;
        }
    }


    /**
     * 登录响应
     */
    IOnResponseListener<Response<LoginUser>> listener = new IOnResponseListener<Response<LoginUser>>() {
        @Override
        public void onSuccess(Response<LoginUser> response) {
            if (response.getData() == null || response.getData().getUser() == null || response.getData().getOpenapi_token() == null)
                return;
            currentUser = response.getData();
            if (currentUser == null)
                return;

            SharedPreferencesManager.setUserInfo(mContext, currentUser);
            SharedPreferencesManager.setLoginUsername(userNameInputEt.getText().toString());
            SharedPreferencesManager.setUserId(currentUser.getUser().getUser_id());

            if (StringUtil.isEmpty(currentUser.getUser().getMobile())) {
                // 打开绑定手机页
                Intent intentBP = new Intent();
                intentBP.putExtra("token", currentUser.getOpenapi_token());
                intentBP.setClass(mContext, BindPhoneActivity.class);
                startActivity(intentBP);

                // loginPb.setVisibility(View.GONE);
                setClickale(true);
                loginBtn.setText(getResources().getString(R.string.login));
                loginBtn.setTextColor(getResources().getColor(
                        R.color.white_word));
            } else {
                MobclickAgent.onEvent(mContext, "Login");
                //获取关注列表
                UsersHelper.getSingleTon().getNetUserFollows();
                openMainActivity();
            }
        }

        @Override
        public void onError(Response response) {
            if (response.getCode() == -1) {
                LogUtil.d("onFailure,ErrorMessage="
                        + response.getMsg());

                showToast(response.getCode());
            } else {
                LogUtil.d("code != 0---------------msg="
                        + response.getMsg());

                if (response.getCode() == RESPONSE_MSG_USER_NOT_EXIST
                        || response.getCode() == RESPONSE_MSG_PHONE_NUMBER_FORMAT_ERROR || response.getCode() == RESPONSE_MSG_PASSWORD_ERROR) {
                    showToast("账号或密码错误");
                } else {
                    showToast(response.getMsg());
                }

            }

            // loginPb.setVisibility(View.GONE);
            setClickale(true);
            loginBtn.setText(getResources().getString(R.string.login));
            loginBtn.setTextColor(getResources().getColor(
                    R.color.white_word));
        }
    };


    /**
     * "检查验证码"接口的响应(也是根据验证码修改密码的接口)
     *
     * @param response
     */
    public void onEventMainThread(AccountPasswordResetEvent response) {
        if (response.getCode() == 0) {
            currentUser = response.getData();
            if (currentUser == null)
                return;

            SharedPreferencesManager.setUserInfo(MyApplication.getInstance(), currentUser);
            openMainActivity();
        }
    }

    /**
     * "注册"接口的响应
     *
     * @param response
     */
    public void onEventMainThread(AccountRegisterEvent response) {
        MobclickAgent.onEvent(this, "Register");
        openMainActivity();
    }

    /**
     * 登出的时候，所有界面关闭，打开新的登录界面 （登录界面复写本方法，防止被错误关闭）
     */
    public void onEvent(LogoutEvent logout) {

    }

    /**
     * 登录、注册、忘记密码成功打开MainActivity的方法
     */
    private void openMainActivity() {

        EventBus.getDefault().post(new LoginEvent());

        List<Activity> activities = MyApplication.getInstance().getActivities();
        for (Activity activity : activities) {
            //如,在活动页,点赞,登录后直接关闭,不跳转
            if (activity.getClass().getSimpleName().equals("MainActivity")) {
                finish();
                return;
            }
        }


        // 打开首页
        Intent intent = new Intent();
        if (SharedPreferencesManager.getShowGuidePage())
            intent.setClass(this, NewGuideActivity.class);
        else {
            intent.setClass(this, MainActivity.class);
        }

        startActivity(intent);
        finish();
    }


    // 接下来是三个TextWatcher的方法
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        UIUtils.showView(userNameClearIv, userNameInputEt.getText().toString().length() > 0 &&
                userNameInputEt.isFocused());
        UIUtils.showView(passwordClearIv, passwordInputEt.getText().toString().length() > 0 &&
                passwordInputEt.isFocused());
        if (userNameInputEt.getText().toString().length() > 0
                && passwordInputEt.getText().toString().length() > 0) {
            loginBtn.setTextColor(getResources().getColor(R.color.white));
            loginBtn.setEnabled(true);
            loginBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_login_button));
        } else {
            loginBtn.setTextColor(getResources().getColor(
                    R.color.text_common_hint));
            loginBtn.setEnabled(false);
            loginBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_blue_button));

        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
