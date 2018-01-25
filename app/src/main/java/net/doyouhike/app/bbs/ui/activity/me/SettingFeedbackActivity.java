/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: SettingFeedbackActivity.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-22
 *
 * Changes (from 2015-10-22)
 * -----------------------------------------------------------------
 * 2015-10-22 创建SettingFeedbackActivity.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.me;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.util.AsyncAlertDialog;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.util.UserInfoUtil;

public class SettingFeedbackActivity extends BaseActivity {

    private TextView feedbackSubmitTv;
    private EditText feedbackContentEt;

    private boolean isRequest = false;
    AsyncAlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_setting_feedback);
        super.onCreate(savedInstanceState);

        bindControl();

        setListener();

    }

    /**
     * 绑定控件
     */
    private void bindControl() {
        mDialog =new AsyncAlertDialog(this);
        feedbackSubmitTv = ((TitleView) findViewById(R.id.navigation_title)).getRight_text();
        feedbackContentEt = (EditText) findViewById(R.id.et_feedback_content);
    }

    /**
     * 给控件添加监听者
     */
    private void setListener() {
        feedbackSubmitTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isRequest) {
                    return;
                }

                String feedbackContent = feedbackContentEt.getText().toString();

                LoginUser cUser = UserInfoUtil.getInstance().getCurrentUser();
                if (cUser != null) {
                    // 发送反馈信息到服务器

                    feedbackSubmitTv.setEnabled(false);
                    isRequest = true;
                    mDialog.show();
                } else {
                    makeToast(R.string.please_to_login);
                }

                closeInputSoft();
            }
        });
        feedbackSubmitTv.setEnabled(false);

        feedbackContentEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    feedbackSubmitTv.setEnabled(true);
                } else {
                    feedbackSubmitTv.setEnabled(false);
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
    }


}
