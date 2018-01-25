/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: SettingAboutMofangActivity.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-12-09
 *
 * Changes (from 2015-12-09)
 * -----------------------------------------------------------------
 * 2015-12-09 创建SettingAboutMofangActivity.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.me;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.dialog.listener.OnBtnClickL;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.ui.activity.live.InviteDialog;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.util.ShareUtil;
import net.doyouhike.app.bbs.util.timemachine.TimeMachineUtil;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.log.LocalLogUtil;

public class SettingAboutMofangActivity extends net.doyouhike.app.bbs.base.activity.BaseActivity implements
        OnClickListener {

    private static String OFFICIAL_WECHAT;
    private static String OFFICIAL_PHONE;
    private static String OFFICIAL_MAILBOX;

    private RelativeLayout timeMachineRlyt;
    private RelativeLayout officialWechatRlyt;
    private RelativeLayout officialPhoneRlyt;
    private RelativeLayout officialMailboxRlyt;

    /**
     * 本地日志开关
     */
    private TextView tvLocalLog;
    /**
     * 服务器地址配置
     */
    private TextView tv_edit_server_url;

    /**
     * 版本号
     */
    private TextView tvVersion;
    /**
     * 推荐好友
     */
    private TextView tvRecommend;


    @Override
    public void setContentView(int layoutResID) {
        isSetSystemBar = false;
        super.setContentView(layoutResID);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting_about_mofang;
    }

    @Override
    protected void initViewsAndEvents() {
        Resources resources = getResources();
        OFFICIAL_WECHAT = resources.getString(R.string.official_wechat_account);
        OFFICIAL_PHONE = resources.getString(R.string.official_phone_number);
        OFFICIAL_MAILBOX = resources
                .getString(R.string.official_mailbox_number);

        bindControl();

        setListener();

        refreshDisplay();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareUtil.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 绑定控件
     */
    private void bindControl() {

        View view = findViewById(R.id.navigation_title);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + UIUtils.getTranslucentStatusHeight(this), layoutParams.rightMargin, layoutParams.bottomMargin);

        timeMachineRlyt = (RelativeLayout)
                findViewById(R.id.rlyt_time_machine);
        officialWechatRlyt = (RelativeLayout) findViewById(R.id.rlyt_official_wechat);
        officialPhoneRlyt = (RelativeLayout) findViewById(R.id.rlyt_official_phone);
        officialMailboxRlyt = (RelativeLayout) findViewById(R.id.rlyt_official_mailbox);
        tv_edit_server_url = (TextView) findViewById(R.id.tv_edit_server_url);
        tvLocalLog = (TextView) findViewById(R.id.tv_switch_log);
        tvVersion = (TextView) findViewById(R.id.tv_app_version);
        tvRecommend = (TextView) findViewById(R.id.tv_recommend);

//        UIUtils.showView(timeMachineRlyt, !TextUtils.isEmpty(UserInfoUtil.getInstance().getUUID()));
    }

    /**
     * 给控件添加监听者
     */
    private void setListener() {
        timeMachineRlyt.setOnClickListener(this);
        officialWechatRlyt.setOnClickListener(this);
        officialPhoneRlyt.setOnClickListener(this);
        officialMailboxRlyt.setOnClickListener(this);
        tvLocalLog.setOnClickListener(this);
        tv_edit_server_url.setOnClickListener(this);
        tvRecommend.setOnClickListener(this);
    }

    /**
     * 设置显示数据
     */
    private void refreshDisplay() {

        // 获取版本号
        PackageInfo info = null;
        PackageManager manager = getPackageManager();
        try {
            info = manager.getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        if (info != null) {
            tvVersion.setText("磨房" + info.versionName);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlyt_time_machine:
//			//  时光机
                gotoTimeMachine();
                break;
            case R.id.rlyt_official_wechat:
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
            case R.id.rlyt_official_phone:
                // 打开拨号盘，并拨号
                Intent intentDial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                        + OFFICIAL_PHONE));
                startActivity(intentDial);
                break;
            case R.id.rlyt_official_mailbox:
                // TODO 打开邮件
                Intent intentMail = new Intent(Intent.ACTION_SEND);
                intentMail.setType("message/rfc822"); // use from live device
                intentMail.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{OFFICIAL_MAILBOX});
                intentMail.putExtra(Intent.EXTRA_SUBJECT, "来自磨房App的邮件");
                intentMail.putExtra(Intent.EXTRA_TEXT, "请填写您对磨房的一些建议或意见");
                startActivity(Intent.createChooser(intentMail, "请选择通过哪个应用发送邮件"));
                break;
            case R.id.tv_switch_log:
                openLocalLogClick();

                break;
            case R.id.tv_edit_server_url:
                openEditServerUrlClick();

                break;
            case R.id.tv_recommend:
                //推荐好友
                new InviteDialog(this).show();
                break;
            default:
                break;
        }
    }

    private void openEditServerUrlClick() {
        long currentTime = System.currentTimeMillis();

        if (0 == lastClickTime) {
            lastClickTime = currentTime;
        }

        if (currentTime - lastClickTime < 3000) {
            lastClickTime = currentTime;
            clickCount++;

            if (clickCount > 2) {
                showToast(String.valueOf(clickCount));
            }


            if (clickCount == 5) {
                clickCount = 0;
                lastClickTime = 0;
                startActivity(new Intent(this, EidtServerUrlActivity.class));

            }


        }
    }


    private long lastClickTime = 0;
    private int clickCount = 0;

    private Toast mToast;

    /**
     * 显示消息,为了覆盖之前的消息,这样写
     *
     * @param text toast文本
     */
    public void showToast(String text) {

        StringUtil.showSnack(this, text);

    }

    /**
     * 开关消息记录开关
     */
    private void openLocalLogClick() {

        long currentTime = System.currentTimeMillis();
        if (0 == lastClickTime) {
            lastClickTime = currentTime;
        }
        if (currentTime - lastClickTime < 3000) {
            lastClickTime = currentTime;
            clickCount++;
            if (clickCount > 2) {
                showToast(String.valueOf(clickCount));
            }

            if (clickCount == 5) {
                boolean previewState = LocalLogUtil.isDebugModel();
                LocalLogUtil.resetDebugModel();
                showToast(previewState ? "打开日志记录成功" : "关闭日志记录成功");
                clickCount = 0;
                lastClickTime = 0;
            }
        }

    }


    /**
     * 跳转时光机
     */
    private void gotoTimeMachine() {
        startActivity(TimeMachineUtil.getTimeMachineIntent(this));
        finish();
    }


    /**
     * 微信号已经复制的对话框
     */
    @SuppressLint("InflateParams")
    private void showWechatCopyDialog() {


        TipUtil.alert(SettingAboutMofangActivity.this
                , getString(R.string.copy_wechat_title)
                , getString(R.string.copy_wechat_hint)
                , new String[]{
                        getString(R.string.cancel)
                        , getString(R.string.open_wechat_to_search)}
                , new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {

                    }
                }, new OnBtnClickL() {
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
                }
        );
    }

}
