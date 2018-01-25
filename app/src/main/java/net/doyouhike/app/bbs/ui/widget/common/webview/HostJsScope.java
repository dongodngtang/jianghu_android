
/**
 * Summary: js脚本所能执行的函数空间
 * Version 1.0
 * Date: 13-11-20
 * Time: 下午4:40
 * Copyright: Copyright (c) 2013
 */
package net.doyouhike.app.bbs.ui.widget.common.webview;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebView;
import android.widget.Toast;

import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.util.ShareUtil;
import net.doyouhike.app.bbs.util.StringUtil;


//HostJsScope中需要被JS调用的函数，必须定义成public static，且必须包含WebView这个参数
public class HostJsScope {


    /**
     * 短暂气泡提醒
     *
     * @param webView 浏览器
     * @param message 提示信息
     */
    public static void toast(WebView webView, String message) {
        StringUtil.showSnack(webView.getContext(), message);
    }


    /**
     * 结束当前窗口
     *
     * @param view 浏览器
     */
    public static void goBack(WebView view) {
        if (view.getContext() instanceof Activity) {
            ((Activity) view.getContext()).finish();
        }
    }

    //回到APP首页
    public static void TimeMachineClose(WebView view) {

        if (view.getContext() instanceof Activity) {

            Activity activity = (Activity) view.getContext();

            // 打开首页
            Intent intent = new Intent();
            intent.setClass(activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);

            activity.finish();
        }

    }

    //分享到微信
    public static void shareWX(WebView view, String url, String title, String content, String imgUrl) {
        ShareUtil.init(view.getContext());
        ShareUtil.shareToWeixin(view.getContext(), imgUrl, title, content, url);
    }

    //分享到朋友圈
    public static void shareWXFriend(WebView view, String url, String title, String imgUrl) {

        ShareUtil.init(view.getContext());
        ShareUtil.shareToPyq(view.getContext(), imgUrl, title, title, url);
    }

}