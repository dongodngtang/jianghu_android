/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: SettingMainActivity.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-22
 *
 * Changes (from 2015-10-22)
 * -----------------------------------------------------------------
 * 2015-10-22 创建SettingMainActivity.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.MaterialDialog;

import net.doyouhike.app.bbs.BuildConfig;
import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.event.LogoutEvent;
import net.doyouhike.app.bbs.biz.helper.userinfo.UserInfoClearUtil;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.AccountHelper;
import net.doyouhike.app.bbs.biz.openapi.response.AppVersionResp;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.CommonUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StorageUnit;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class SettingMainActivity extends BaseActivity implements
        OnClickListener {

    private Context thisContext = this;

    private RelativeLayout changePasswordRlyt;

    private RelativeLayout setBellRlyt;
    private RelativeLayout wipeCacheRlyt;
    private TextView cacheTv;
    private RelativeLayout rlCheckUpdate;
    private View iv_app_update_indicate;
    private RelativeLayout rlytPrivacySetting;
    private RelativeLayout feedbackSuggestRlyt;
    // private RelativeLayout gotoAppStoreRlyt;
    private RelativeLayout aboutMofangRlyt;

    private TextView logOutTv;

    private DecimalFormat format = new DecimalFormat("#0.00");

    private AppVersionResp appVersion;

    private View iv_feedback_indicate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_setting_main);
        super.onCreate(savedInstanceState);

        bindControl();

        setListener();

    }

    /**
     * 绑定控件
     */
    private void bindControl() {
        changePasswordRlyt = (RelativeLayout) findViewById(R.id.rlyt_change_password);
        rlytPrivacySetting = (RelativeLayout) findViewById(R.id.rlyt_privacy_setting);
        setBellRlyt = (RelativeLayout) findViewById(R.id.rlyt_seetting_bell);
        wipeCacheRlyt = (RelativeLayout) findViewById(R.id.rlyt_wipe_cache);
        cacheTv = (TextView) findViewById(R.id.tv_cache);
        rlCheckUpdate = (RelativeLayout) findViewById(R.id.rlyt_wipe_update);
        iv_app_update_indicate = findViewById(R.id.iv_app_update_indicate);

        // 本机的缓存目录
        File cacheDir = Glide.getPhotoCacheDir(this);
//        if (CommonUtil.ExistSDCard() && isEnoughExternalStorage()) {
//            cacheDir = getExternalCacheDir();
//        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        CacheMessage clearMsg = controlCacheFolder(cacheDir,
                System.currentTimeMillis(), false);
        long cacheSize = clearMsg.getCacheSize();

        double cacheMB = 0;
        if (cacheSize > (1024 * 1024 / 100)) {
            cacheMB = (double) cacheSize / (1024 * 1024);
        } else {
            wipeCacheRlyt.setClickable(false);
            cacheMB = 0;
        }
        cacheTv.setText(format.format(cacheMB) + getResources().getString(R.string.cache_mb));

        feedbackSuggestRlyt = (RelativeLayout) findViewById(R.id.rlyt_feedback_suggest);
        // gotoAppStoreRlyt = (RelativeLayout)
        // findViewById(R.id.rlyt_goto_app_store);
        aboutMofangRlyt = (RelativeLayout) findViewById(R.id.rlyt_about_mofang);

        logOutTv = (TextView) findViewById(R.id.tv_log_out);
        iv_feedback_indicate = findViewById(R.id.iv_feedback_indicate);

        //APP更新
        appVersion = SharedPreferencesManager.getAppUpdateInfo();
        UIUtils.showView(iv_app_update_indicate, appVersion != null &&
                appVersion.getUpgrade() != 0);
        //反馈数量
        int feedbackNum = SharedPreferencesManager.getFeedBackNum();
        UIUtils.showView(iv_feedback_indicate, feedbackNum != 0);


    }

    /**
     * 给控件添加监听者
     */
    private void setListener() {
        changePasswordRlyt.setOnClickListener(this);
        rlytPrivacySetting.setOnClickListener(this);
        setBellRlyt.setOnClickListener(this);
        wipeCacheRlyt.setOnClickListener(this);

        feedbackSuggestRlyt.setOnClickListener(this);
        // gotoAppStoreRlyt.setOnClickListener(this);
        aboutMofangRlyt.setOnClickListener(this);

        logOutTv.setOnClickListener(this);
        rlCheckUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.rlyt_change_password:
                intent.setClass(thisContext, SettingChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rlyt_seetting_bell:
                intent.setClass(thisContext, SettingMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.rlyt_wipe_update:
                //检查更新
                showUpdateTip();
                break;
            case R.id.rlyt_wipe_cache:
                // 清理缓存
                if (cacheTv.getText().toString().equals("0.00MB")) {
//                    makeToast(R.string.cache_zero_mb);
                    break;
                }

                // 本机的缓存目录
//                File cacheDir =  Glide.getPhotoCacheDir(this);
////                if (CommonUtil.ExistSDCard() && isEnoughExternalStorage()) {
////                    cacheDir = getExternalCacheDir();
////                }
//                if (!cacheDir.exists()) {
//                    cacheDir.mkdirs();
//                }

//                CacheMessage clearMsg = controlCacheFolder(cacheDir,
//                        System.currentTimeMillis(), true);
//                int delCount = clearMsg.getCacheCount();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(thisContext).clearDiskCache();
                    }
                }).start();

                StringUtil.showSnack(
                        thisContext,
                        getResources().getString(R.string.total_count_wipe_cache)
                                + cacheTv.getText().toString());

                cacheTv.setText("0.00MB");
                break;
            case R.id.rlyt_feedback_suggest:
                // 反馈界面
//                intent.setClass(thisContext, SettingFeedbackActivity.class);
//                startActivity(intent);
                Map<String, String> map = new HashMap<>();
                map.put("enableAudio", "0");//关闭语音
                map.put("themeColor", "#FF48ADA0");

                FeedbackAPI.setUICustomInfo(map);
                FeedbackAPI.setCustomContact(UserInfoUtil.getInstance().getCurrentUser().getUser().getMobile(), true);
                FeedbackAPI.openFeedbackActivity(thisContext);
                break;
            // case R.id.rlyt_goto_app_store:
            // // TODO goto app_store
            // try {
            // Uri uri = Uri.parse("market://search?q=pname:" + getPackageName());
            // Intent goToStoreIntent = new Intent(Intent.ACTION_VIEW);
            // goToStoreIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // goToStoreIntent.setData(uri);
            // startActivity(goToStoreIntent);
            // } catch (Exception e) {
            // Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=" +
            // getPackageName());
            // Intent goToStoreIntent = new Intent(Intent.ACTION_VIEW);
            // goToStoreIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // goToStoreIntent.setData(uri);
            // startActivity(goToStoreIntent);
            // }
            //
            // break;
            case R.id.rlyt_about_mofang:
                // TODO 关于磨房
                intent.setClass(thisContext, SettingAboutMofangActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_log_out:

                // 提示：询问是否取消关注
                showLogoutDialog();
                break;
            case R.id.rlyt_privacy_setting:
                intent.setClass(thisContext, SettingPrivacyActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    /**
     * 显示登出确认对话框
     */
    private void showLogoutDialog() {
        String[] itemStrList = {getResources().getString(R.string.confirm_quit)};
        final ActionSheetDialog mBottomPopupWindow = new ActionSheetDialog(thisContext, itemStrList, null);
        mBottomPopupWindow.isTitleShow(false);
        mBottomPopupWindow.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                //退出登陆
                loginOut();

            }
        });
        mBottomPopupWindow.show();

    }

    /**
     * 退出登陆
     */
    private void loginOut() {
        try {
            EventBus.getDefault().post(new LogoutEvent());

            new UserInfoClearUtil().clearAllInfo();
            //关掉其他activity,除了自己
            MyApplication.getInstance().removeActivity(SettingMainActivity.this);
            MyApplication.getInstance().finishActivies();


        } catch (Exception e) {
            e.printStackTrace();
        }

        //跳转登陆页
        Intent intent = new Intent();
        intent.setClass(thisContext, LoginActivity.class);
        startActivity(intent);

        this.finish();

    }

    /**
     * 判断内存存储是否足够
     */
    private boolean isEnoughExternalStorage() {
        long sdFreeSize = CommonUtil.getFreeSize(getExternalCacheDir(),
                StorageUnit.UNIT_MB);
        long FreeSize = CommonUtil.getFreeSize(getCacheDir(),
                StorageUnit.UNIT_MB);
        return sdFreeSize > 100 || sdFreeSize > FreeSize;
    }

    // ----------------------以下是关于清理缓存的方法-------------------------//

    /**
     * 遍历指定目录下所有文件的方法
     *
     * @param dir     路径
     * @param numDays 时间
     * @return CacheMessage实例
     */
    private CacheMessage controlCacheFolder(File dir, long numDays,
                                            boolean isClear) {
        int cacheCount = 0;
        int cacheSize = 0;
        if (dir != null && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                try {
                    for (File child : files) {
                        if (child == null) {
                            continue;
                        }
                        if (child.isDirectory()) {
                            CacheMessage clearMsg = controlCacheFolder(child,
                                    numDays, isClear);
                            cacheCount += clearMsg.getCacheCount();
                            cacheSize += clearMsg.getCacheSize();
                        } else if (child.lastModified() < numDays) {
                            cacheSize += child.length();
                            cacheCount++;
                            if (isClear) {
                                child.delete();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        CacheMessage clearMsg = new CacheMessage();
        clearMsg.setCacheCount(cacheCount);
        clearMsg.setCacheSize(cacheSize);
        return clearMsg;
    }


    /**
     * 检查更新
     */
    private void showUpdateTip() {

        if (appVersion == null)
            return;
        switch (appVersion.getUpgrade()) {
            case 0:
                TipUtil.alert(this, "已经是最新版本");
                break;
            case 1:
                String[] str = {"取消", "升级"};
                TipUtil.alert(this, "升级提示", appVersion.getTip(), str, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        ActivityRouter.openBrowser(SettingMainActivity.this, appVersion.getUrl());
                    }
                });
                break;
            case 2:
                MaterialDialog dialog = new MaterialDialog(this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.content(appVersion.getTip())
                        .title("升级提示")
                        .btnText("升级")
                        .setOnBtnClickL(new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                ActivityRouter.openBrowser(SettingMainActivity.this, appVersion.getUrl());
                            }
                        });
                dialog.show();
                break;
        }
    }


    /**
     * 用来返回关于缓存或删除缓存的信息
     */
    private class CacheMessage {

        private int cacheCount;
        private long cacheSize;

        public int getCacheCount() {
            return cacheCount;
        }

        public void setCacheCount(int cacheCount) {
            this.cacheCount = cacheCount;
        }

        public long getCacheSize() {
            return cacheSize;
        }

        public void setCacheSize(long cacheSize) {
            this.cacheSize = cacheSize;
        }

    }
}
