/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.doyouhike.app.bbs.base.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.base.util.BaseView;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.nohttp.CallServer;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.library.ui.base.BaseAppCompatActivity;
import net.doyouhike.app.library.ui.eventbus.EventCenter;
import net.doyouhike.app.library.ui.netstatus.NetUtils;
import net.doyouhike.app.library.ui.uistate.UiState;


/**
 * Author:  luochangdong
 * Date:    15/7/21
 * Description:
 */
public abstract class BaseActivity extends BaseAppCompatActivity implements BaseView {

    protected boolean isSetSystemBar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);


        //沉浸式状态栏的配色 主色（渐变式）
        if(isSetSystemBar)
        setSystemBarTintDrawable(R.color.system_bar_bg);
// 获取工具类实例
       // MyApplication.getInstance().addActivity(this);
//        if (android.os.Build.VERSION.SDK_INT >= 14) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }







    @Override
    protected void onDestroy() {
        MyApplication.getInstance().removeActivity(this);
        UserHeadNickClickHelper.getInstance().onDestroy();
        super.onDestroy();
    }

    protected MyApplication getBaseApplication() {
        return (MyApplication) getApplication();
    }

    @Override
    public void showError(String msg) {
        updateView(UiState.ERROR);
    }

    @Override
    public void showException(String msg) {
    }

    @Override
    public void showNetError() {
    }

    @Override
    public void showLoading(String msg) {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onEvent(EventCenter eventCenter) {

    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }


    /**
     * 标题栏左边回退按钮调用
     */
    public void rollBack(View view) {
        finish();
    }
}
