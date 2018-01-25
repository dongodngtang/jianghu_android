package net.doyouhike.app.bbs.biz.helper.userinfo;

import com.hyphenate.chat.EMClient;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.db.dao.UserInfoDbUtil;
import net.doyouhike.app.bbs.biz.helper.jiguang.JPushUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;

/**
 * Created by zengjiang on 16/8/10.
 */

public class UserInfoClearUtil {

    public void clearAllInfo() {


        SharedPreferencesManager.setPostLive(null);
        SharedPreferencesManager.setPostEvent(null);
        SharedPreferencesManager.setPostEventEdit(null);
        SharedPreferencesManager.clearToEventLiveInfo(MyApplication.getInstance().getApplicationContext());

        logout();
    }

    public void logout() {
        //清空群发,失败草稿
        UserInfoDbUtil.getInstance().getDrafts();
        //首页标签纪录,防止历史纪录,退出登录时,切换到其他标签
        SharedPreferencesManager.setMainTopicPage(null);
        //清空IM信息
        SharedPreferencesManager.saveImUserInfo(null);
        //环信退出登陆
        EMClient.getInstance().logout(true);//此方法为同步方法，里面的参数 true 表示退出登录时解绑 GCM 或者小米推送的 token
        // 点击“确认退出”后正常发送请求
        SharedPreferencesManager.clearUserInfo(MyApplication.getInstance().getApplicationContext());

        // 退出当前账号，停止极光服务
        JPushUtil.getInstance().stopPush();
    }
}
