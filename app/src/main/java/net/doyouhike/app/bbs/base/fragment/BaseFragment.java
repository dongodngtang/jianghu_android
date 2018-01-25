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

package net.doyouhike.app.bbs.base.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.nohttp.CallServer;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.glide.GlideHelper;
import net.doyouhike.app.library.ui.base.BaseLazyFragment;
import net.doyouhike.app.library.ui.eventbus.EventCenter;

/**
 * fragment基类
 */
public abstract class BaseFragment extends BaseLazyFragment {

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onEvent(EventCenter eventCenter) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isNeedPaddingTop()) {
            view.setPadding(0, UIUtils.getTranslucentStatusHeight(mContext), 0, 0);
            setSystemBarTintDrawable(R.color.system_bar_bg);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        GlideHelper.glideClear(getContext());


    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    public void postEvent(Object event) {
        MyApplication.getInstance().postEvent(event);
    }

    /**
     * @return 是否需要沉浸状态栏, 默认不处理沉浸, 返回true为为内容添加一个padding, 防止内容嵌套进状态栏
     */
    public boolean isNeedPaddingTop() {
        return false;
    }

    /**
     * use SytemBarTintManager
     *
     * @param resourceId
     */
    protected void setSystemBarTintDrawable(int resourceId) {

        if (null != getActivity() && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setSystemBarTintDrawable(resourceId);
        }

    }

    protected void setSystemBarTintAlpha(int resourceId, float alpha) {

        if (null != getActivity() && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setSystemBarTintAlpha(resourceId, alpha);
        }

    }

    public Context getApplicationContext() {
        return MyApplication.getInstance().getApplicationContext();
    }

}
