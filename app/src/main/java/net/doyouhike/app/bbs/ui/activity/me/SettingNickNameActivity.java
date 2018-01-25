/*
* -----------------------------------------------------------------
* Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
* -----------------------------------------------------------------
*
* File: SettingNickNameActivity.java
* Author: ChenWeiZhen
* Version: 1.0
* Create: 2015-11-26
*
* Changes (from 2015-11-26)
* -----------------------------------------------------------------
* 2015-11-26 创建SettingNickNameActivity.java (ChenWeiZhen);
* -----------------------------------------------------------------
*/
package net.doyouhike.app.bbs.ui.activity.me;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.flyco.dialog.listener.OnBtnClickL;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.request.users.UserEditProfilePut;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.UIUtils;

import de.greenrobot.event.EventBus;

public class SettingNickNameActivity extends BaseActivity {

    private static final int MSG_NICKNAME_IS_EXIST = 1101027;

    private static final int NICK_NAME_LENGTH =20;

    private Context thisContext = this;

    private TextView confirmTv;
    private EditText nickNameEt;
    private TextView surplusTv;
    private ImageView mIvClear;

    private CurrentUserDetails cuDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_setting_nickname);
        super.onCreate(savedInstanceState);

        bindControl();

        setListener();

        refreshDisplay();

    }

    /**
     * 绑定控件
     */
    private void bindControl() {

//        mDialog =new AsyncAlertDialog(thisContext);

        confirmTv = ((TitleView) findViewById(R.id.navigation_title)).getRight_text();
        nickNameEt = (EditText) findViewById(R.id.et_nickname);
        surplusTv = (TextView) findViewById(R.id.tv_surplus);
        mIvClear = (ImageView) findViewById(R.id.iv_setting_nickname_clear);

    }
    /**
     * 每当某项数据被修改就马上调用修改接口
     */
    private void saveData(String key, String value) {
        UsersHelper.getSingleTon().editUserProfile(this,key, value,listener);
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

                NickNameInfo nickNameInfo = new NickNameInfo();
                nickNameInfo.nickName = nickNameEt.getText().toString();
                if (nickNameInfo.nickName.length() >= 2
                        && nickNameInfo.nickName.length() <= NICK_NAME_LENGTH) {

                    // 友好提示，不确定需不需要
//					makeToast(R.string.please_wait_fo_check_nickname);
//                    mDialog.show();
                    UIUtils.showSoftInput(nickNameEt,false);

                    saveData(UserEditProfilePut.NICK_NAME,nickNameInfo.nickName);

                } else if (nickNameInfo.nickName.length() < 2) {
                    makeToast(R.string.register_user_name_too_short);
                } else {
                    makeToast(R.string.register_user_name_too_long);
                }

                confirmTv.setEnabled(false);
            }
        });
        confirmTv.setEnabled(false);

        nickNameEt.setOnKeyListener(new OnKeyListener() {

            @SuppressLint("NewApi")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    confirmTv.callOnClick();
                }
                return false;
            }
        });

        nickNameEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (null==s){
                    return;
                }

                int length= s.length();

                if (length >= 2 && length <= NICK_NAME_LENGTH) {
                    surplusTv.setText((NICK_NAME_LENGTH - length) + "");
                    surplusTv.setTextColor(getResources().getColor(R.color.gray_word));
                    confirmTv.setEnabled(true);
                } else {
                    if (length > NICK_NAME_LENGTH) {
                        surplusTv.setText((length - NICK_NAME_LENGTH) + "");
                        surplusTv.setTextColor(getResources().getColor(R.color.orange_word));
                    } else {
                        surplusTv.setText((NICK_NAME_LENGTH - length) + "");
                        surplusTv.setTextColor(getResources().getColor(R.color.gray_word));
                    }
                    confirmTv.setEnabled(false);
                }

                String nickName = s.toString().trim();
                if (nickName.length() == 0) {
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
                nickNameEt.setText(null);
            }
        });
    }

    /**
     * 设置显示数据
     */
    private void refreshDisplay() {
        cuDetails = SharedPreferencesManager.getUserDetailsInfo(thisContext);
        if (null==cuDetails){
            return;
        }

        // 昵称
        String nickName = cuDetails.getNick_name();

        if (nickName == null) {
            return;
        }

        if (!nickName.equals("")) {
            nickNameEt.setText(nickName);
        }
        nickNameEt.requestFocus();
        nickNameEt.setSelection(nickName.length());
    }

    // -----------------------------EventBus 接收----------------------------------//

    IOnResponseListener<Response<CurrentUserDetails>> listener = new IOnResponseListener<Response<CurrentUserDetails>>() {
        @Override
        public void onSuccess(Response<CurrentUserDetails> response) {
            SharedPreferencesManager.setUserDetailsInfo(SettingNickNameActivity.this, cuDetails);
            NickNameInfo nickNameInfo = new NickNameInfo();
            nickNameInfo.nickName = response.getData().getNick_name();
            EventBus.getDefault().post(nickNameInfo);
            finish();
        }

        @Override
        public void onError(Response response) {
            makeToast(response.getMsg());
            refreshDisplay();
            confirmTv.setEnabled(true);
        }
    };


    /**
     * 提示昵称已经存在的对话框
     */
    @SuppressLint("InflateParams")
    private void showNickNameExistDialog() {



        TipUtil.alert(this
                , getString(R.string.nickname_is_exist)
                , getString(R.string.nickname_is_exist_hint)
                , new String[]{
                        getString(R.string.cancel)
                        , getString(R.string.re_input)}
                , new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {

                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        nickNameEt.setText("");
                    }
                }
        );

    }

    public class NickNameInfo {
        String nickName;
    }
}
