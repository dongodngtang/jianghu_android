package net.doyouhike.app.bbs.biz.event;

import android.content.Context;
import android.text.TextUtils;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;

import net.doyouhike.app.bbs.biz.event.im.GetMyImIdRequestEvent;
import net.doyouhike.app.bbs.biz.helper.im.ImLoginHelper;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.MyImInfo;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import de.greenrobot.event.EventBus;

/**
 * 全局对话框事件
 *
 * @see GlobalDialogEvent#getLoginOutEvent() 环信被踢事件
 * @see GlobalDialogEvent#getGlobalDialogEvent(String, String) 一般提醒类型对话框事件
 * <p>
 * Created by zengjiang on 16/8/8.
 */

public class GlobalDialogEvent {


    /**
     * 标题
     */
    String title;
    /**
     * 内容
     */
    String msg;
    /**
     * 按钮文字
     */
    String[] btnTxt;
    /**
     * 按钮事件
     */
    OnBtnClickL[] onBtnClickLs;

    /**
     * 能否取消
     */
    boolean cancelAble=true;


    private DialogCreater mDialogCreater;


    /**
     * @return 环信被踢, 全局对话框
     */
    public static GlobalDialogEvent getLoginOutEvent() {


        return new GlobalDialogEvent()
                .setTitle("磨房账号已在其他设备登录")
                .setMsg("账号已在另一设备上登录，继续使用当前设备将无法收到私信。如非本人操作，则密码可能已泄漏，建议修改密码。")
                .setBtnTxt("忽略", "重新登录")
                .setCancelAble(false)
                .setOnBtnClickLs(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {

                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        //重新登录

                        MyImInfo info = SharedPreferencesManager.getImUserInfo();
                        if (null != info) {
                            new ImLoginHelper().doImLogin(info);
                        } else {
                            //请求环信用户信息,并登陆
                            EventBus.getDefault().post(new GetMyImIdRequestEvent(UserInfoUtil.getInstance().getUUID()));
                        }
                    }
                });
    }

    /**
     * @return 一般提示, 全局对话框
     */
    public static GlobalDialogEvent getGlobalDialogEvent(String title, String msg) {


        return new GlobalDialogEvent()
                .setTitle(title)
                .setMsg(msg)
                .setBtnTxt("确定");
    }


    /**
     * @param btnTxt 按钮文字
     * @return 全局对话框事件
     */
    public GlobalDialogEvent setBtnTxt(String... btnTxt) {
        this.btnTxt = btnTxt;
        return this;
    }


    /**
     * @param title 标题
     * @return 全局对话框事件
     */
    public GlobalDialogEvent setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @param msg 消息
     * @return 全局对话框事件
     */
    public GlobalDialogEvent setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public GlobalDialogEvent setCancelAble(boolean cancelAble) {
        this.cancelAble = cancelAble;
        return this;
    }

    /**
     * @param onBtnClickLs 按钮点击事件,顺序与按钮文字对应
     * @return 全局对话框事件
     */
    public GlobalDialogEvent setOnBtnClickLs(OnBtnClickL... onBtnClickLs) {
        this.onBtnClickLs = onBtnClickLs;
        return this;
    }

    public GlobalDialogEvent setmDialogCreater(DialogCreater mDialogCreater) {
        this.mDialogCreater = mDialogCreater;
        return this;
    }

    public MaterialDialog getGlobalDialog(Context context) {


        MaterialDialog dialog;


        if (null!=mDialogCreater){
            dialog=mDialogCreater.createDialog(context);
        }else {
            dialog = new MaterialDialog(context);
            dialog.content(msg)
                    .title(title)
                    .isTitleShow(!TextUtils.isEmpty(title))
                    .setCanceledOnTouchOutside(cancelAble);

            if (null != btnTxt) {
                dialog.btnText(btnTxt);
                dialog.btnNum(btnTxt.length);

            } else {
                dialog.btnNum(1);
            }

            if (null != onBtnClickLs) {
                dialog.setOnBtnClickL(onBtnClickLs);
            }

        }



        return dialog;

    }

    public interface DialogCreater {
        MaterialDialog createDialog(Context context);
    }


}
