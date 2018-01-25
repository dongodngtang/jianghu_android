package net.doyouhike.app.bbs.biz.helper.im;

import android.text.TextUtils;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import net.doyouhike.app.bbs.biz.newnetwork.model.bean.MyImInfo;
import net.doyouhike.app.bbs.chat.helper.EMHelper;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;

/**
 * Created by zengjiang on 16/8/8.
 */

public class ImLoginHelper {


    public void doImLogin(MyImInfo info) {

        //空检测
        if (null == info || TextUtils.isEmpty(info.getIm_id()) || TextUtils.isEmpty(info.getIm_password())) {


//            info=new MyImInfo();
//            info.setIm_id("9c93ea5635169d01cc6fc46bcea9aab9");
//            info.setIm_password("b7f0f886660948b19117dec8361ed115");
//            info.setIm_enabled(true);

            return;
        }

        EMClient.getInstance().login(info.getIm_id(), info.getIm_password(), new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMHelper.getInstance().setLogined(true);
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                LogUtil.d("登录聊天服务器成功！");

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                LogUtil.d("登录聊天服务器失败: "+message);
            }
        });
    }

    public static void loginIm(EMCallBack emCallBack){
        MyImInfo info = SharedPreferencesManager.getImUserInfo();
        if(info != null){
            EMClient.getInstance().login(info.getIm_id(), info.getIm_password(),emCallBack);
        }

    }
}
