package net.doyouhike.app.bbs.util.timemachine;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.ui.widget.common.webview.TimeMachineActivity;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.SpTools;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.List;

/**
 * 功能：
 *
 * @author：曾江 日期：16-3-8.
 */
public class TimeMachineUtil {

    public static boolean isReady(Context context) {

        LoginUser currentUser = UserInfoUtil.getInstance().getCurrentUser();

        if (null == currentUser) {
            return false;
        }

        Boolean first = SpTools.getBoolean(context, Content.TIME_MACHINE_FIRSR, true);

        if (!first) {
            return false;
        }

        String uuid = currentUser.getUser().getUser_id();

        if (TextUtils.isEmpty(uuid)) {
            return false;
        }

        String userId = currentUser.getUser().getInternal_id();

        //本地已读时光机的用户id列表
        List<String> useridLists = SharedPreferencesManager.getReadedTimelineMachine(context);

        //已读时光机用户列表中不包含时光机
        return !useridLists.contains(userId);
    }

    public static Intent getTimeMachineIntent(Context context) {

        LoginUser currentUser = UserInfoUtil.getInstance().getCurrentUser();

        boolean isFuture = SpTools.getBoolean(context, Content.TIME_MACHINE_REGISTER, false);

        String url = isFuture ? Content.TIME_MACHINE_URL_FUTURE : Content.TIME_MACHINE_URL_PAST;


        if (null != currentUser && !TextUtils.isEmpty(currentUser.getUser().getUser_id())) {
            url += currentUser.getUser().getUser_id();
        }

        Intent intent = new Intent(context, TimeMachineActivity.class);
        intent.putExtra(TimeMachineActivity.I_URL, url);

        return intent;
    }

    public static void resetHasRead(Context context) {
        SpTools.setBoolean(context, Content.TIME_MACHINE_FIRSR, true);
        SpTools.setBoolean(context, Content.TIME_MACHINE_REGISTER, false);
    }

    public static void setHasRead(Context context) {
        SpTools.setBoolean(context, Content.TIME_MACHINE_FIRSR, false);
        SpTools.setBoolean(context, Content.TIME_MACHINE_REGISTER, false);
    }
}
