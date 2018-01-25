package net.doyouhike.app.bbs.biz.openapi.presenter;

import android.content.Context;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.openapi.request.AppVersionGet;
import net.doyouhike.app.bbs.biz.openapi.request.account.AccountBindMobilePost;
import net.doyouhike.app.bbs.biz.openapi.request.account.AccountChangePwdPost;
import net.doyouhike.app.bbs.biz.openapi.request.account.AccountMobileAuthVcodePost;
import net.doyouhike.app.bbs.biz.openapi.request.account.AccountPasswordResetPost;
import net.doyouhike.app.bbs.biz.openapi.request.account.AccountRegisterPost;
import net.doyouhike.app.bbs.biz.openapi.request.LoginPost;
import net.doyouhike.app.bbs.biz.openapi.request.account.AccountVcodePost;
import net.doyouhike.app.bbs.biz.openapi.request.account.AccountVerifyVcode;
import net.doyouhike.app.bbs.biz.openapi.request.account.AdLoadingGet;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.util.EnCoder;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;

/**
 * 作者：luochangdong on 16/9/8
 * 描述：账户相关接口
 */
public class AccountHelper {

    private static AccountHelper instance;

    public static AccountHelper getInstance() {
        if (instance == null)
            instance = new AccountHelper();
        return instance;
    }


    /**
     * app更新
     *
     * @param context
     * @param app_vcode
     * @param listener
     */
    public void appVersionGet(Context context, String app_vcode, IOnResponseListener listener) {
        AppVersionGet get = new AppVersionGet();
        get.setCancelSign(context);
        get.setApp_vcode(app_vcode);
        ApiReq.doGet(get, listener);
    }

    /**
     * 用户通过旧密码 修改密码
     *
     * @param context
     * @param user_id
     * @param old_pwd
     * @param new_pwd
     * @param listener
     */
    public void changePwdByOldPwd(Context context, String user_id, String old_pwd, String new_pwd, IOnResponseListener listener) {

        AccountChangePwdPost post = new AccountChangePwdPost(user_id);
        post.setOld_pwd(old_pwd);
        post.setNew_pwd(new_pwd);
        post.setCancelSign(context);
        ApiReq.doPost(post, listener);
    }

    /**
     * 获取登录广告数据
     *
     * @param context
     * @param listener
     */
    public void getAdLoading(Context context, IOnResponseListener listener) {
        AdLoadingGet get = new AdLoadingGet();
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 绑定手机号
     *
     * @param context
     * @param mobile
     * @param vcode
     * @param listener
     */
    public void bindMobile(Context context, String mobile, String vcode, IOnResponseListener listener) {
        AccountBindMobilePost post = new AccountBindMobilePost();
        post.setCancelSign(context);
        post.setMobile(mobile);
        post.setVcode(vcode);

        ApiReq.doPost(post, listener);
    }


    /**
     * 获取绑定手机号的验证码
     *
     * @param context
     * @param mobile
     * @param listener
     */
    public void bindMobileAuthVcode(Context context, String mobile, IOnResponseListener listener) {
        AccountMobileAuthVcodePost post = new AccountMobileAuthVcodePost();
        post.setCancelSign(context);
        post.setMobile(mobile);

        ApiReq.doPost(post, listener);
    }

    /**
     * 用户忘记密码，重置密码
     *
     * @param type
     * @param uid
     * @param password
     * @param code
     */
    public void resetPassword(Context context, String type, String uid, String password, String code) {
        AccountPasswordResetPost param = new AccountPasswordResetPost();
        param.setPassword(password);
        param.setCode(code);
        param.setType(type);
        param.setUid(uid);
        param.setCancelSign(context);

        ApiReq.doPost(param);

    }

    /**
     * 获取验证码
     *
     * @param mobile
     * @param context
     */
    public void getVCode(String mobile, String type, Context context) {
        AccountVcodePost param = new AccountVcodePost();
        param.setMobile(mobile);
        param.setVcode_type(type);
        param.setCancelSign(context);
        ApiReq.doPost(param);
    }

    /**
     * 校对验证码
     *
     * @param mobile
     * @param vcode
     * @param type
     * @param context
     * @param listener
     */
    public void verifyVcode(String mobile, String vcode, String type,
                            Context context, IOnResponseListener listener) {
        AccountVerifyVcode post = new AccountVerifyVcode();
        post.setMobile(mobile);
        post.setVcode(vcode);
        post.setVcode_type(type);
        post.setCancelSign(context);
        ApiReq.doPost(post, listener);
    }


    /**
     * 注册
     *
     * @param nickName
     * @param mobile
     * @param password
     * @param vcode
     * @param context
     */
    public void register(String nickName, String mobile, String password,
                         String vcode, Context context, IOnResponseListener listener) {
        AccountRegisterPost post = new AccountRegisterPost();
        post.setMobile(mobile);
        post.setNick_name(nickName);
        post.setPassword(password);
        post.setVcode(vcode);
        post.setRegister_type("mobile");
        post.setCancelSign(context);

        ApiReq.doPost(post, listener);
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param loginType
     * @param baseActivity
     * @param listener
     */
    public void login(String username, String password,
                      LoginType loginType, LoginActivity baseActivity,
                      IOnResponseListener listener) {
        LoginPost loginParam = getLoginParam(username, password, loginType);
        loginParam.setCancelSign(baseActivity);
        ApiReq.doPost(loginParam, listener);

    }


    /**
     * 获取请求参数对象
     *
     * @param username
     * @param password
     * @param loginType
     * @return
     */
    private LoginPost getLoginParam(String username, String password, LoginType loginType) {
        LoginPost param = new LoginPost();
        if (loginType != LoginType.TOKEN)
            param.setPassword(EnCoder.MD5(password));
        else
            param.setToken(password);

        if (loginType == null)
            loginType = getLoginType(username);

        param.setLogin_type(loginType.toString());
        switch (loginType) {
            case VCODE:
                param.setVcode(username);
                break;
            case MOBILE:
                param.setMobile(username);
                break;
            case UNAME:
                param.setUname(username);
                break;
            case TOKEN:
                param.setUser_id(username);
                break;

        }
        return param;
    }

    /**
     * 获取登录类型
     *
     * @param username
     * @return
     */
    private LoginType getLoginType(String username) {
        if (StringUtil.isMobileNum(username))
            return LoginType.MOBILE;
        else
            return LoginType.UNAME;

    }


    public enum LoginType {
        VCODE("vcode"),
        MOBILE("mobile"),
        UNAME("uname"),
        TOKEN("token");

        private String typeString;

        LoginType(String typeString) {
            this.typeString = typeString;
        }

        @Override
        public String toString() {
            return typeString;
        }
    }


}
