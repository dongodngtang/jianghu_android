package net.doyouhike.app.bbs.biz.helper.jiguang;

import android.content.Context;

import net.doyouhike.app.bbs.BuildConfig;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 极光推送相关操作
 * Created by zengjiang on 16/6/24.
 */
public class JPushUtil {

    private static JPushUtil instance = new JPushUtil();

    private JPushUtil() {
    }

    public static JPushUtil getInstance() {
        return instance;
    }

    public void init(Context context) {

        // 基础初始化
        JPushInterface.setDebugMode(BuildConfig.DEBUG); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(context.getApplicationContext()); // 初始化 JPush
    }


    /**
     * 停止推送
     */
    public void stopPush() {
        //  重置设置别名状态
        SharedPreferencesManager.setJPushAliasState(MyApplication.getInstance().getApplicationContext(), "");
        //清除通知
        JPushInterface.clearAllNotifications(MyApplication.getInstance().getApplicationContext());
        //设置空别名
        setAlias("");
        //停止推送服务
        if (!JPushInterface.isPushStopped(MyApplication.getInstance().getApplicationContext())) {
            JPushInterface.stopPush(MyApplication.getInstance().getApplicationContext());
        }
    }


    /**
     * 设置别名
     */
    public void setAlias() {


        if (UserInfoUtil.getInstance().isLogin()) {

            //已经登录了
            String historyAlias = SharedPreferencesManager.getJPushAliasState(MyApplication.getInstance().getApplicationContext());
            String envirment;
            if (SharedPreferencesManager.getServerEnvirment().equals("production")) {
                envirment = "product";
            }else{
                envirment = SharedPreferencesManager.getServerEnvirment();
            }

            String alias = envirment + "_" + UserInfoUtil.getInstance().getUserId();


            // 调用 JPush 接口来设置别名。
            LogUtil.d("JPush", "设置别名: " + alias);


            if (!alias.equals(historyAlias)) {
                //还未设置过别名了
                setAlias(alias);
            }

            resumePush();

        } else {
            //未登录,停止推送服务
            stopPush();
        }

    }


    /**
     * 设置别名
     *
     * @param alias
     */
    private void setAlias(String alias) {
        // 调用 JPush 接口来设置别名。
        JPushInterface.setAliasAndTags(MyApplication.getInstance().getApplicationContext(),
                alias,
                null,
                mAliasCallback);
    }


    /**
     * 恢复推送
     */
    private void resumePush() {

        if (JPushInterface.isPushStopped(MyApplication.getInstance().getApplicationContext())) {
            JPushInterface.resumePush(MyApplication.getInstance().getApplicationContext());
        }

    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    SharedPreferencesManager.setJPushAliasState(MyApplication.getInstance().getApplicationContext(), alias);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // 延迟 60 秒来 设置别名
                    delaySetAlias();
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
        }
    };

    /**
     * 延迟 60 秒来 设置别名
     */
    private void delaySetAlias() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                setAlias();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 60 * 1000);
    }


}
