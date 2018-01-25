/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ActionAttendActivity.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-10-13
 *
 */
package net.doyouhike.app.bbs.ui.activity.action;

import android.content.ComponentName;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.flyco.dialog.listener.OnBtnClickL;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventApplyPost;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.ui.activity.me.SettingBindPhoneActivity;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.SpTools;
import net.doyouhike.app.bbs.util.StatisticsEventUtil;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.util.Map;

import butterknife.InjectView;

/**
 * @author ZouWenJie 参加活动界面
 */
public class ActionAttendActivity extends BaseActivity implements
        OnClickListener {
    private static String OFFICIAL_WECHAT;


    private TitleView mTitleView;
    private LinearLayout ll_notice;
    private EditText et_phone;
    private String mobile;
    private EditText et_name;
    private EditText et_message;
    private EditText et_insurance;
    private EditText et_number;
    private EditText et_contact_name;
    private EditText et_contact_phone;
    private String realName;
    private String message;
    private String insurance;
    private String insuraceNumber;
    private String contactName;
    private String contactPhone;
    private LoginUser userInfo;
    private String token;
    private Map<String, String> map;
    private String userPhone;
    private String nodeId;
    private RelativeLayout insuranceLlyt;
    private LinearLayout ll_this;
    @InjectView(R.id.llyt_content)
    LinearLayout ll_content;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_action_attend;
    }

    @Override
    protected View getLoadingTargetView() {
        return ll_content;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void initViewsAndEvents() {
        nodeId = getIntent().getStringExtra("nodeId");
        bindControl();
        initData();
        setListener();
    }

    /**
     * 自动填写数据
     */
    private void initData() {

        userInfo = UserInfoUtil.getInstance().getCurrentUser();
        if (userInfo == null) {
            return;
        }

        token = userInfo.getOpenapi_token();
        mobile = userInfo.getUser().getMobile();
        OFFICIAL_WECHAT = getResources().getString(R.string.official_wechat_insurance);
        if (StrUtils.hasContent(mobile)) {
            et_phone.setText(mobile);
        }
        if (StrUtils.hasContent(SpTools.getString(this, "USERNAME", ""))) {
            et_name.setText(SpTools.getString(this, "USERNAME", ""));
        }

        setComfirmEnable();
    }

    /**
     * 绑定控件
     */
    private void bindControl() {
        ll_notice = (LinearLayout) findViewById(R.id.ll_action_notice);
        ll_this = (LinearLayout) findViewById(R.id.llyt_this);
        insuranceLlyt = (RelativeLayout) findViewById(R.id.rlyt_insurance);
        et_phone = (EditText) findViewById(R.id.et_attend_phone);
        et_name = (EditText) findViewById(R.id.et_attend_name);
        et_message = (EditText) findViewById(R.id.et_attend_message);
        et_insurance = (EditText) findViewById(R.id.et_attend_insurance);
        et_number = (EditText) findViewById(R.id.et_attend_insurance_number);
        et_contact_name = (EditText) findViewById(R.id.et_contact_name);
        et_contact_phone = (EditText) findViewById(R.id.et_contact_phone);


        mTitleView = (TitleView) findViewById(R.id.navigation_title);

    }

    /**
     * 设置监听
     */
    private void setListener() {
        ll_notice.setOnClickListener(this);
        insuranceLlyt.setOnClickListener(this);
        et_phone.setOnClickListener(this);
        ll_this.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        ll_content.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        mTitleView.setListener(new TitleView.ClickListener() {
            @Override
            public void clickLeft() {
                onBack();
            }

            @Override
            public void clickRight() {
                //确认报名
                if (!StrUtils.hasContent(et_name.getText().toString().trim())) {
                    //提醒
                    showAlertDialog();
                } else if (!StrUtils.hasContent(mobile)) {
                    //提醒
                    showMobileAlertDialog();
                } else {
                    showConfirmDialog();
                }
            }
        });

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setComfirmEnable();
            }
        });
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setComfirmEnable();
            }
        });
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_action_notice:// 跳转到声明
                Intent intent = new Intent(ActionAttendActivity.this,
                        ActionNoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.rlyt_insurance://微信购买保险
                // 复制账号
                if (android.os.Build.VERSION.SDK_INT > 11) {
                    android.content.ClipboardManager c = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    c.setText(OFFICIAL_WECHAT);
                } else {
                    android.text.ClipboardManager c = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    c.setText(OFFICIAL_WECHAT);
                }

                // 打开对话框
                showWechatCopyDialog();
                break;
            case R.id.et_attend_phone:
                if (!StrUtils.hasContent(mobile)) {
                    Intent bindIntent = new Intent(ActionAttendActivity.this, SettingBindPhoneActivity.class);
                    startActivity(bindIntent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    /**
     * 没有绑定号码提醒
     */
    private void showMobileAlertDialog() {
        TipUtil.alert(ActionAttendActivity.this, "请先绑定手机号");
    }

    /**
     * 显示打开微信提醒框
     */
    private void showWechatCopyDialog() {

        TipUtil.alert(ActionAttendActivity.this,
                getString(R.string.copy_wechat_title),
                "可打开微信粘贴至搜索框，在公众号中完成保险购买",
                new String[]{getString(R.string.cancel), getString(R.string.open_wechat_to_search)},
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {

                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        // 打开微信
                        try {
                            Intent intent = new Intent();
                            ComponentName cmp = new ComponentName("com.tencent.mm",
                                    "com.tencent.mm.ui.LauncherUI");
                            intent.setAction(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_LAUNCHER);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setComponent(cmp);
                            startActivityForResult(intent, 0);
                        } catch (Exception e) {
                            showToast(getString(R.string.open_wechat_fail));
                        }
                    }
                });
    }

    /**
     * 提醒必填姓名
     */
    private void showAlertDialog() {
        TipUtil.alert(ActionAttendActivity.this, "请输入您的真实姓名");

    }

    /**
     * 确认提交与否
     */
    private void showConfirmDialog() {


        TipUtil.alert(ActionAttendActivity.this,
                "确认提交",
                "确认资料无误，并填写完毕",
                new String[]{getString(R.string.cancel), getString(R.string.confirm)},
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {

                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        mTitleView.getRight_text().setEnabled(false);
                        sendConfirmRequest();
                    }
                });
    }

    private void sendConfirmRequest() {
        // 获取数据
        realName = et_name.getText().toString().trim();
        userPhone = et_phone.getText().toString().trim();
        message = et_message.getText().toString().trim();
        insurance = et_insurance.getText().toString().trim();
        insuraceNumber = et_number.getText().toString().trim();
        contactName = et_contact_name.getText().toString().trim();
        contactPhone = et_contact_phone.getText().toString().trim();
        if (!StrUtils.hasContent(token)) {

            showToast(getString(R.string.please_to_login));
            return;
        }

        EventApplyPost applyPost = new EventApplyPost(nodeId);
        applyPost.setReal_name(realName);
        applyPost.setContact_name(contactName);
        applyPost.setContact_tel(contactPhone);
        applyPost.setMemo(message);
        applyPost.setInsurance_name(insurance);
        applyPost.setInsurance_number(insuraceNumber);
        applyPost.setCancelSign(mContext);

        updateView(UiState.SHOW_DIALOG);
        ApiReq.doPost(applyPost, new IOnResponseListener() {
            @Override
            public void onSuccess(Response response) {
                updateView(UiState.NORMAL);
                applyEventResponse(response);
            }

            @Override
            public void onError(Response response) {
                updateView(UiState.NORMAL);
                mTitleView.getRight_text().setEnabled(true);
                TipUtil.alert(ActionAttendActivity.this, response.getMsg());

            }
        });
    }


    private void applyEventResponse(Response response) {
        updateView(UiState.NORMAL);

        mTitleView.getRight_text().setEnabled(true);

        if (response.getCode() == 0) {
            //参加活动成功
            showAlertDialog("报名成功");
            Intent intent = new Intent();
            intent.putExtra("me_role", 9);
            this.setResult(RESULT_OK, intent);
            //保存报名姓名userName
            SpTools.setString(this, "USERNAME", realName);
            StatisticsEventUtil.JoinSuccess(this);
        }
    }

    private void showAlertDialog(String msg) {

        TipUtil.alert(ActionAttendActivity.this, null, msg, new String[]{"确定"}, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                finish();
            }
        });

    }

    private void onBack() {
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
        //弹出提醒框


        TipUtil.alert(ActionAttendActivity.this
                , "放弃填写？"
                , "确认放弃填写、参加活动"
                , new String[]{"继续填写", "放弃"}
                , new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {

                    }
                }
                , new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        finish();
                    }
                });


    }

    private void setComfirmEnable() {

        mTitleView.getRight_text().setEnabled(
                !TextUtils.isEmpty(et_phone.getText())//电话不为空
                        && !TextUtils.isEmpty(et_name.getText())//名称不为空
        );

    }

    @Override
    public void onResume() {
        mobile = SharedPreferencesManager.getUserInfo(this).getUser().getMobile();
        if (StrUtils.hasContent(mobile)) {
            et_phone.setText(mobile);
        }
        super.onResume();
    }

}
