package net.doyouhike.app.bbs.chat.helper;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.PreferenceManager;

import net.doyouhike.app.bbs.util.LogUtil;

/**
 * 作者:luochangdong on 16/7/21 09:21
 * 描述:
 */
public class EMLogin {

    private static EMLogin instance;

    private static String TAG = "EMLogin";

    public static String currentUserNick = "";

    private EMLogin() {

    }

    public static EMLogin getInstance() {
        if (instance == null) {
            instance = new EMLogin();
        }
        return instance;
    }

    public void loginEm(){
        EMClient.getInstance().login("5914047c846f20859739f7327dea7fec", "ba337f080d4804e68aa13071cfb7d455", new EMCallBack() {

            @Override
            public void onSuccess() {
                LogUtil.d(TAG, "login: onSuccess");

                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().updateCurrentUserNick(currentUserNick);
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }

                // get user's info (this should be get from App's server or 3rd party service)
                PreferenceManager.getInstance().setCurrentUserNick(currentUserNick);



            }

            @Override
            public void onProgress(int progress, String status) {
                LogUtil.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                LogUtil.d(TAG, "login: onError: " + code);

            }
        });
    }
}
