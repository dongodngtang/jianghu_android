package net.doyouhike.app.bbs.ui.widget.common.dialog;

import android.content.Context;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.event.GlobalDialogEvent;
import net.doyouhike.app.bbs.biz.event.LogoutEvent;
import net.doyouhike.app.bbs.biz.helper.userinfo.UserInfoClearUtil;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.util.ActivityRouter;

import de.greenrobot.event.EventBus;

/**
 * 密码修改 弹窗 单例模式 避免生成多个dialog
 * Created by zengjiang on 16/8/18.
 */

public class PasswordChangeDialog implements GlobalDialogEvent.DialogCreater {

    public static PasswordChangeDialog instance;

    public synchronized static PasswordChangeDialog getInstance() {

        if (null == instance) {
            initInstance();
        }

        return instance;
    }

    private synchronized static void initInstance() {

        if (null == instance) {
            instance = new PasswordChangeDialog();
        }
    }


    MaterialDialog mDialog;


    @Override
    public MaterialDialog createDialog(Context context) {


        if (null == mDialog) {
            mDialog = new MaterialDialog(context);
            mDialog.content("密码已修改，请重新登录")
                    .isTitleShow(false)
                    .btnText("稍后", "登陆")
                    .btnNum(2)
                    .setOnBtnClickL(new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {

                        }
                    }, new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {

                            EventBus.getDefault().post(new LogoutEvent());
                            //清理一些登陆信息
                            new UserInfoClearUtil().logout();
                            //  跳转登录页
                            ActivityRouter.jumpToLogin();
                            MyApplication.getInstance().finishActiviesExcept(LoginActivity.class);
                        }
                    });

        }


        return mDialog;
    }

    public void onDestroy() {


        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }

        instance = null;

    }

}
