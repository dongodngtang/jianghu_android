/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: SearchUserActivity.java
 * Author: wu-yoline(伍建鹏)
 * Version: 1.0
 * Create: 2015-10-4
 *
 * Changes (from 2015-10-6)
 * -----------------------------------------------------------------
 * 2015-10-4 : 1、创建本类，设置ContentView(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-10-6 : 	1、把父类改为BaseActivity(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-11-03 : 1、实现推荐用户的显示;(wu-yoline)
 * 				2、添加onEventMainThread(FollowUserResponse response)方法;(wu-yoline)
 * 				3、实现点击搜索按钮跳到搜索界面;(wu-yoline)
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.live;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.RecommendUser;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.biz.openapi.response.users.RecommendUsersResp;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.adapter.live.RecommendUserAdapter;
import net.doyouhike.app.bbs.util.InputTools;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.ShareUtil;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索用户界面
 *
 * @author wu-yoline
 */
public class SearchUserActivity extends BaseActivity {
    private LinearLayout llyt_phone_adress_book;
    private ListView lvRecommendUser; // 推荐用户列表
    private RecommendUserAdapter userAdapter; // 推荐用户列表的adapter
    private List<RecommendUser> users = new ArrayList<RecommendUser>(); // 系统推荐的用户
    private View recommendUserVi;
    private TextView tv_act_search_user;
    private View v_act_search_user_line;
    private RelativeLayout rl_invite_friend;
    private InviteDialog inviteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_search_user);
        super.onCreate(savedInstanceState);

        findView(); // 绑定组件

        initView(); // 初始化组件

        requestData(); // 请求网络数据

        shwoTip(false);

    }

    private void requestData() {
        LoginUser user = UserInfoUtil.getInstance().getCurrentUser();
        if (user != null && StringUtil.isNotEmpty(user.getOpenapi_token())) {

            UsersHelper.getSingleTon().getRecommendUsers(this,recommendsListener);

        } else {
            startActivity(new Intent(this, LoginActivity.class));
            String toast = StrUtils.getResourcesStr(this,
                    R.string.please_to_login);
            StringUtil.showSnack(this, toast);
        }
    }

    IOnResponseListener<Response<RecommendUsersResp>> recommendsListener = new IOnResponseListener<Response<RecommendUsersResp>>() {
        @Override
        public void onSuccess(Response<RecommendUsersResp> response) {


            // 获取返回数据
            if (response.getData().getItems() != null) {
                users = response.getData().getItems();
                // 更新界面
                updateLvRecommendUser();
            }

        }

        @Override
        public void onError(Response response) {

        }
    };

    private void initView() {
        initLvRecommendUser();
        initSearchBtn();

        inviteDialog = new InviteDialog(this);
        rl_invite_friend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                inviteDialog.show();
            }
        });
    }

    private void initSearchBtn() {
        LinearLayout llytSearch = (LinearLayout) findViewById(R.id.ll_add_user);
        llytSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchUserActivity.this,
                        ToSearchActivity.class);
                SearchUserActivity.this.startActivity(intent);
            }
        });
    }

    private void initLvRecommendUser() {
        userAdapter = new RecommendUserAdapter(this, users);
        lvRecommendUser.setAdapter(userAdapter);
        setLvRecommendUserMaxHeight(); // 设置lvRecommendUser的最大高度

        llyt_phone_adress_book.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchUserActivity.this,
                        PhoneContactsActivity.class));
            }
        });

        lvRecommendUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                LogUtil.d("setOnTouchListener");
                if (ev.getAction() == MotionEvent.ACTION_DOWN) {

                    if (InputTools.isShouldHideInput(v, ev)) {
                        InputTools.HideKeyboard(v);
                    }

                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ShareUtil.onActivityResult(requestCode, resultCode, data);
    }

    private void setLvRecommendUserMaxHeight() {
        // TODO 因为这个lvRecommendUser的高度为自适应，所以必须得计算出最大高度限制其高度
    }

    private void updateLvRecommendUser() {
        if (users != null) {

            recommendUserVi.setVisibility(users.isEmpty() ? View.GONE : View.VISIBLE);

            userAdapter.setUsers(users);
            shwoTip(userAdapter.getCount()>0);
            userAdapter.notifyDataSetChanged();
        }
    }

    private void findView() {
        lvRecommendUser = (ListView) this.findViewById(R.id.lv_recommend_user);
        recommendUserVi = findViewById(R.id.rl_act_search_user_recommend_user_parent);
        llyt_phone_adress_book = (LinearLayout) findViewById(R.id.llyt_phone_adress_book);
        v_act_search_user_line = findViewById(R.id.v_act_search_user_line);
        tv_act_search_user = (TextView) findViewById(R.id.tv_act_search_user);
        rl_invite_friend = (RelativeLayout) findViewById(R.id.rl_invite_friend);
    }

    private void shwoTip(boolean isshow){
        UIUtils.showView(v_act_search_user_line,isshow);
        UIUtils.showView(tv_act_search_user,isshow);
    }





    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.d("setOnTouchListener");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (InputTools.isShouldHideInput(v, ev)) {

                InputTools.HideKeyboard(v);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

}
