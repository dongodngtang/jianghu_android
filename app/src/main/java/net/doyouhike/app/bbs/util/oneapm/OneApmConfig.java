package net.doyouhike.app.bbs.util.oneapm;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.HashMap;

/**
 * 作者:luochangdong on 16/6/27 09:26
 * 描述:
 */
public class OneApmConfig {

    /**
     * 用户配置信息
     */
    public static void ConfigUserInfo(){


//        ContextConfig config = new ContextConfig();
//        config.setSearchValue(user.getUserName()); // 设置一个搜索值
//        config.setExtra(extraData);
//
//        OneApmAgent.init(MyApplication.getInstance().getApplicationContext())
//                .setContextConfig(config).setToken(BuildConfig.ONE_APM_KEY)
//                .start();

    }

    /**
     * WebView 性能监控 已加
     * @param webView
     */
    public static void ConfigWebView(WebView webView){
        webView.setWebViewClient(new WebViewClient(){});
    }


}
