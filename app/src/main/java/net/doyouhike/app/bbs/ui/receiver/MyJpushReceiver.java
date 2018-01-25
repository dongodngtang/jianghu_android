/*
* -----------------------------------------------------------------
* Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
* -----------------------------------------------------------------
*
* File: MyJpushReceiver.java
* Author: ChenWeiZhen
* Version: 1.0
* Create: 2015-12-03
*
* Changes (from 2015-12-03)
* -----------------------------------------------------------------
* 2015-12-03 创建MyJpushReceiver.java (ChenWeiZhen);
* -----------------------------------------------------------------
*/
package net.doyouhike.app.bbs.ui.receiver;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import net.doyouhike.app.bbs.biz.entity.JPushNotificationInfo;
import net.doyouhike.app.bbs.biz.event.live.CheckoutMainPageEvent;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.ui.activity.start.StartActivity;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

public class MyJpushReceiver extends BroadcastReceiver {

    public static final String TAG = MyJpushReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("jpush的action = " + intent.getAction());

        Log.d(TAG, "----------      MyJpushReceiver      ------------------   ");
        Bundle bundle = intent.getExtras();

        if (intent.getAction().endsWith("cn.jpush.android.intent.REGISTRATION")) {

        } else if (intent.getAction().equals("cn.jpush.android.intent.UNREGISTRATION")) {

        } else if (intent.getAction().equals("cn.jpush.android.intent.MESSAGE_RECEIVED")) {
            // 接受到消息

        } else if (intent.getAction().equals("cn.jpush.android.intent.NOTIFICATION_RECEIVED")) {
            Log.d(TAG, "----------      cn.jpush.android.intent.NOTIFICATION_RECEIVED------------------   ");
            // 接受到通知  解析其内的json，更新示
            String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            LogUtil.d(TAG, alert + " extras:" + extras);

            JPushNotificationInfo jpushNotInfo = new JPushNotificationInfo();
            jpushNotInfo.setAlert(alert);
            jpushNotInfo.setExtrasByJson(extras);

            EventBus.getDefault().post(jpushNotInfo);

        } else if (intent.getAction().equals("cn.jpush.android.intent.NOTIFICATION_OPENED")) {
            // 用户点击通知栏  拉起应用主页，打开msg页
            Log.d(TAG, "----------      cn.jpush.android.intent.NOTIFICATION_OPENED      ------------------   ");
            // 判断是否有Activity在运行
            boolean isHasActivity = hasActivity(context);

            Intent openMainIntent = new Intent();
            if (isHasActivity) {
                openMainIntent.setClass(context, MainActivity.class);
            } else {
                openMainIntent.setClass(context, StartActivity.class);
            }

            openMainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            openMainIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pIntent = PendingIntent.getActivity(context, 0, openMainIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            JPushInterface.clearAllNotifications(context);
            try {
                pIntent.send();
            } catch (CanceledException e) {
                e.printStackTrace();
            }


            if (isHasActivity && bundle != null) {

                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);


                if (!TextUtils.isEmpty(extras)) {

                    if (extras.equals("fans")) {
                        //粉丝跳转到我的页面
                        EventBus.getDefault().post(new CheckoutMainPageEvent(MainActivity.PAGE_ME));
                    } else {
                        //默认跳转到消息页面
                        EventBus.getDefault().post(new CheckoutMainPageEvent(MainActivity.PAGE_MESSAGE));
                    }


                }


            }


        } else if (intent.getAction().equals("cn.jpush.android.intent.ACTION_RICHPUSH_CALLBACK")) {

        } else if (intent.getAction().equals("cn.jpush.android.intent.CONNECTION")) {

        }
    }

    @SuppressWarnings("deprecation")
    private boolean hasActivity(Context context) {

        String packageName = context.getPackageName();
        ActivityManager activityManager = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(10);
        boolean isRunning = false;
        for (RunningTaskInfo runningTaskInfo : runningTaskInfos) {
            if (packageName.equals(runningTaskInfo.topActivity
                    .getPackageName())) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }


    private void setNoticeOffOn(String alert, Context context) {
        Boolean noticeComment = SharedPreferencesManager.getMsgCommentState(context);
        Boolean noticeLike = SharedPreferencesManager.getMsgLikeState(context);
        Boolean noticeAction = SharedPreferencesManager.getMsgActionState(context);


    }

}
