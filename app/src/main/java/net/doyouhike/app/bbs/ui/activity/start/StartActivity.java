/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: StartActivity.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-9-25
 *
 * Changes (from 2015-10-6)
 * -----------------------------------------------------------------
 * 2015-9-25 创建StartActivity.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 * 2015-10-4 添加搜索用户Activity进入接口 (wu-yoline);
 * -----------------------------------------------------------------
 * 2015-10-5 1、添加举报Activity进入接口 (wu-yoline);
 * 			2、添加转发Activity进入接口(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-10-6 1、添加"添加标签"Activity进入接口 (wu-yoline);
 * 			2、添加"编写直播"Activity进入接口(wu-yoline);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.start;

import android.support.v4.app.Fragment;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.alibaba.sdk.android.feedback.util.IWxCallback;
import com.umeng.analytics.MobclickAgent;

import net.doyouhike.app.bbs.BuildConfig;
import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.event.start.CheckoutEvent;
import net.doyouhike.app.bbs.biz.helper.jiguang.JPushUtil;
import net.doyouhike.app.bbs.chat.helper.EMHelper;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.adapter.live.PageFragmentAdapter;
import net.doyouhike.app.bbs.ui.fragment.start.AdStartFragment;
import net.doyouhike.app.bbs.ui.fragment.start.StartFragment;
import net.doyouhike.app.bbs.ui.widget.common.viewpager.FixedSpeedScroller;
import net.doyouhike.app.bbs.ui.widget.common.viewpager.ViewPageTransformer;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.log.LocalLogConfiurator;
import net.doyouhike.app.library.ui.widgets.XViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;

public class StartActivity extends BaseActivity {

    public static final String FIRSTIN = "firstIn";

    /**
     * 内容
     */
    @InjectView(R.id.vp_activity_start_content)
    XViewPager vp_activity_start_content;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_start;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void initViewsAndEvents() {


        MyApplication.getInstance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {

                SharedPreferencesManager.appContext = getApplicationContext();
                //本地日志配置
                LocalLogConfiurator.config();

                // 发送获取服务器地址的请求
                SharedPreferencesManager.setServerUrl(getApplicationContext(), MyApplication.getInstance().getServerUrl());

                //环信初始化
                EMHelper.getInstance().init(MyApplication.getInstance());
                MobclickAgent.setDebugMode(BuildConfig.DEBUG);


            }
        });


        initContent();

    }

    private void initContent() {

        List<Fragment> fragments = new ArrayList<>();

        //起始页 index=0
        fragments.add(new StartFragment());
        //广告 index=1
        fragments.add(new AdStartFragment());


        PageFragmentAdapter fragmentAdapter = new PageFragmentAdapter(getSupportFragmentManager(),
                fragments);
        vp_activity_start_content.setAdapter(fragmentAdapter);
        vp_activity_start_content.setEnableScroll(false);

        vp_activity_start_content.setPageTransformer(true, new ViewPageTransformer(ViewPageTransformer.TransformType.FADE));
        new FixedSpeedScroller(this, 500).setViewPagerScrollSpeed(vp_activity_start_content);

    }

    //切换到广告内容
    public void onEventMainThread(CheckoutEvent response) {
        LogUtil.d("切换到广告内容");
        vp_activity_start_content.setCurrentItem(1);
    }

    @Override
    public void onBackPressed() {
        if (UserInfoUtil.getInstance().isLogin()) {
            readyGo(MainActivity.class);
        } else {
            //启动登陆页
            readyGo(LoginActivity.class);
        }
        finish();
    }


    @Override
    public void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    public void onPause() {
        JPushInterface.onPause(this);
        super.onPause();

    }

}
