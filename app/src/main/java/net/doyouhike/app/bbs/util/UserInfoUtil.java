package net.doyouhike.app.bbs.util;

import android.text.TextUtils;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;

/**
 * 功能：获取用户信息工具类
 * 作者：曾江
 * 日期：15-12-30.
 */
public class UserInfoUtil {
    private static UserInfoUtil instance = new UserInfoUtil();

    private UserInfoUtil() {
    }

    public static UserInfoUtil getInstance() {
        return instance;
    }


    /**
     * @return 用户token
     */
    public String getToken() {

        LoginUser currentUser = getCurrentUser();

        if (null != currentUser) {
            String token = currentUser.getOpenapi_token();
            if (null == token) {
                token = "";
            }
            return token;
        }
        return "";
    }


    public String getUUID() {

        LoginUser currentUser = getCurrentUser();

        if (null == currentUser || currentUser.getUser() == null) {
            return "";
        }
        String uuid = currentUser.getUser().getUser_id();

        if (TextUtils.isEmpty(uuid)) {
            return "";
        }

        return uuid;
    }

    /**
     * @return 用户ID
     */
    public String getUserId() {


        LoginUser currentUser = getCurrentUser();

        if (null != currentUser && currentUser.getUser() != null) {
            String strUserId = currentUser.getUser().getUser_id();
            if (null != strUserId) {
                return strUserId;
            }
        }


        return "";
    }

    /**
     * @return 用户ID
     */
    public String getUserMobile() {


        LoginUser currentUser = getCurrentUser();

        if (null != currentUser) {
            String mobile = currentUser.getUser().getMobile();
            if (null != mobile) {
                return mobile;
            }
        }


        return "";
    }


    public boolean isSameUser(String userId, String otherUser) {


        //判断用户id为空,返回false
        if (TextUtils.isEmpty(userId)) {
            return false;
        }


        //登陆用户id为空,返回false
        if (TextUtils.isEmpty(otherUser)) {
            return false;
        }


        return userId.equals(otherUser);
    }


    /**
     * @param userId 用户id
     * @return 是否当前用户
     */
    public boolean isCurrentUser(String userId) {

        //判断用户id为空,返回false
        if (TextUtils.isEmpty(userId)) {
            return false;
        }


        //登陆用户id为空,返回false
        if (TextUtils.isEmpty(getUserId())) {
            return false;
        }

        return getUserId().equals(userId);
    }

    /**
     * @return 当前用户（登陆信息）
     */
    public LoginUser getCurrentUser() {
        return SharedPreferencesManager.getUserInfo(MyApplication.getInstance());
    }


    public boolean isLogin() {
        return null != getCurrentUser();
    }
}
