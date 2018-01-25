/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: PhoneContactsActivity.java
 * Author: Zhangkai
 * Version: 1.0
 * Create: 2015-11-02
 *
 * -----------------------------------------------------------------
 * 2015-11-04 : 1、联网获取数据并显示; (wu-yoline)
 * 				2、实现排序功能;(wu-yoline)
 * -----------------------------------------------------------------
 */

package net.doyouhike.app.bbs.ui.activity.live;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.PhoneContactInfo;
import net.doyouhike.app.bbs.biz.entity.RecommendUser;
import net.doyouhike.app.bbs.biz.event.open.UsersContactsEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.biz.openapi.response.users.UsersContactsResp;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.adapter.action.PhoneContactsListAdapter;
import net.doyouhike.app.bbs.util.CommonUtil;
import net.doyouhike.app.bbs.util.PhoneContactUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 手机通讯录界面
 *
 * @author Zhangkai
 */
public class PhoneContactsActivity extends BaseActivity {
    private ListView lv_contact_list;
    private PhoneContactsListAdapter adapter;
    private List<PhoneContactInfo> contactList = new ArrayList<PhoneContactInfo>();
    private View vLoading;
    private RelativeLayout rlytLoading;
    private LinearLayout llytErrorUI;
    private AnimationDrawable animation;
    private LinearLayout llytNoContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_phone_contacts);
        super.onCreate(savedInstanceState);

        readData();

        findView();

        initView();

        showLoadingUI();

        requestData(contactList);
    }

    private void initView() {
        initPhoneList();
        initLoadingUI();
        initNoContentUI();
    }

    private void initNoContentUI() {
        TextView tvTip1 = (TextView) llytNoContent.findViewById(R.id.tv_no_result);
        TextView tvTip2 = (TextView) llytNoContent
                .findViewById(R.id.tv_no_result_tip);
        tvTip1.setText(R.string.phone_is_null);
        tvTip2.setText(R.string.your_phone_contacts_is_null);
    }

    private void initLoadingUI() {
        vLoading.setBackgroundResource(R.drawable.anim_loading);
    }

    private void initPhoneList() {
        adapter = new PhoneContactsListAdapter(PhoneContactsActivity.this,
                contactList);

        lv_contact_list.setAdapter(adapter);
        if (null != contactList) {
            Collections.sort(contactList); // 按姓名排序
        }
    }

    private void readData() {
        contactList = PhoneContactUtil
                .getConatctList(PhoneContactsActivity.this);

        if (contactList == null || contactList.size() == 0) {
            StringUtil.showSnack(getApplicationContext(),
                    R.string.permission_to_read_contact);
            finish();
        }
    }

    private void findView() {
        lv_contact_list = (ListView) findViewById(R.id.lv_contact_list);
        vLoading = findViewById(R.id.v_loading);
        rlytLoading = (RelativeLayout) findViewById(R.id.rlyt_loading);
        llytErrorUI = (LinearLayout) findViewById(R.id.llyt_network_anomaly);
        llytNoContent = (LinearLayout) findViewById(R.id.llyt_not_result);
    }

    private void requestData(List<PhoneContactInfo> contactList) {
        if (contactList == null || contactList.size() == 0) {
            showNoContent();
            return;
        }

        LoginUser user = UserInfoUtil.getInstance().getCurrentUser();
        if (user != null) {
            if (!refreshListByReadingSP()) {
                UsersHelper.getSingleTon().userContacts(this, user.getUser().getUser_id(), contactList, null);
            }
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            String toast = StrUtils.getResourcesStr(this,
                    R.string.please_to_login);
            Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
        }

    }

    public boolean refreshListByReadingSP() {
        List<RecommendUser> contactsUsers = SharedPreferencesManager
                .getContactsUsers(this);
        if (contactsUsers != null) {
            CommonUtil.testLog("获取联系人注册情况：contactsUsers = " + contactsUsers);
            Collections.sort(contactsUsers); // 按姓名排序
            addToCantactList(contactsUsers);
            updateCantactList();
            showPhoneList();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查联系人注册状态响应方法
     *
     * @param event
     */
    public void onEventMainThread(UsersContactsEvent event) {
        Response<UsersContactsResp> resp = event.getResponse();
        if (resp == null) {
            showError(R.string.app_error, R.string.back, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhoneContactsActivity.this.finish();
                }
            });
            return;
        }

        int code = resp.getCode();
        if (code == 0) {
            List<UsersContactsResp.ItemsBean> items = resp.getData().getItems();
            List<RecommendUser> users = new ArrayList<>();
            for (UsersContactsResp.ItemsBean item : items) {
                item.getUser().setFollow(item.getSocial());
                users.add(item.getUser());
            }
            if (users.size() > 0) {
                Collections.sort(users); // 按姓名排序
                addToCantactList(users);
                updateCantactList();
            }
//            SharedPreferencesManager.saveContactsUsers(this, msgBean);
            showPhoneList();
        } else {
            makeToast("网络好像好点问题，获取联系人注册情况失败");// TODO
            showPhoneList();
        }
    }



    private void addToCantactList(List<RecommendUser> users) {
        if (users != null) {
            for (int i = 0; i < users.size(); i++) {
                RecommendUser user = users.get(i);
                if (user != null && contactList != null) {
                    PhoneContactInfo userInfo = user.changeToPhoneContactInfo();
                    for (int j = 0; j < contactList.size(); j++) {
                        PhoneContactInfo contactInfo = contactList.get(j);
                        if (contactInfo != null) {
                            String phoneNum = contactInfo.phoneNum;
                            if (phoneNum != null
                                    && phoneNum.equals(userInfo.phoneNum)) {
                                PhoneContactInfo remove = contactList.remove(j);
                                if (remove != null) {
                                    userInfo.name = remove.name;
                                }
                                break;
                            }
                        }
                    }
                    contactList.add(0, userInfo);
                }
            }
        }
    }

    private void updateCantactList() {
        adapter.setContactList(contactList);
        adapter.notifyDataSetChanged();
    }

    private void showLoadingUI() {
        lv_contact_list.setVisibility(View.GONE);
        llytErrorUI.setVisibility(View.GONE);
        llytNoContent.setVisibility(View.GONE);
        rlytLoading.setVisibility(View.VISIBLE);
        animation = (AnimationDrawable) vLoading.getBackground();
        if (animation != null) {
            animation.start();
        }
    }

    private void showNoContent() {
        if (animation != null && animation.isRunning()) {
            animation.stop();
        }
        lv_contact_list.setVisibility(View.GONE);
        llytErrorUI.setVisibility(View.GONE);
        rlytLoading.setVisibility(View.GONE);
        llytNoContent.setVisibility(View.VISIBLE);
    }

    private void showPhoneList() {
        if (animation != null && animation.isRunning()) {
            animation.stop();
        }
        rlytLoading.setVisibility(View.GONE);
        llytErrorUI.setVisibility(View.GONE);
        llytNoContent.setVisibility(View.GONE);
        lv_contact_list.setVisibility(View.VISIBLE);
    }

    private void showError(String errorMsg, String btnText,
                           OnClickListener btnClickListener) {
        if (animation != null && animation.isRunning()) {
            animation.stop();
        }
        rlytLoading.setVisibility(View.GONE);
        lv_contact_list.setVisibility(View.GONE);
        llytNoContent.setVisibility(View.GONE);
        setErrorUI(errorMsg, btnText, btnClickListener);
        llytErrorUI.setVisibility(View.VISIBLE);
    }

    private void setErrorUI(String errorMsg, String btnText,
                            OnClickListener btnClickListener) {
        TextView btn = (TextView) llytErrorUI
                .findViewById(R.id.btn_network_anomaly);
        TextView tvTip = (TextView) llytErrorUI
                .findViewById(R.id.tv_error_ui_tip);
        tvTip.setText(errorMsg);
        btn.setVisibility(View.VISIBLE);
        btn.setText(btnText);
        btn.setOnClickListener(btnClickListener);
    }

    private void showError(int errorMsgId, int btnTextId,
                           OnClickListener btnClickListener) {
        if (animation != null && animation.isRunning()) {
            animation.stop();
        }
        rlytLoading.setVisibility(View.GONE);
        lv_contact_list.setVisibility(View.GONE);
        llytNoContent.setVisibility(View.GONE);
        setErrorUI(errorMsgId, btnTextId, btnClickListener);
        llytErrorUI.setVisibility(View.VISIBLE);
    }

    private void setErrorUI(int errorMsgId, int btnTextId,
                            OnClickListener btnClickListener) {
        TextView btn = (TextView) llytErrorUI
                .findViewById(R.id.btn_network_anomaly);
        TextView tvTip = (TextView) llytErrorUI
                .findViewById(R.id.tv_error_ui_tip);
        tvTip.setText(errorMsgId);
        btn.setVisibility(View.VISIBLE);
        btn.setText(btnTextId);
        btn.setOnClickListener(btnClickListener);
    }
}
