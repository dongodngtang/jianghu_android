package net.doyouhike.app.bbs.ui.util;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.db.dao.UserInfoDbUtil;
import net.doyouhike.app.bbs.biz.db.green.entities.ChatUserInfo;
import net.doyouhike.app.bbs.biz.event.GlobalDialogEvent;
import net.doyouhike.app.bbs.biz.event.im.GetOtherHxIdEvent;
import net.doyouhike.app.bbs.biz.event.im.HxidEvent;
import net.doyouhike.app.bbs.biz.helper.im.action.IHxIdAction;
import net.doyouhike.app.bbs.chat.helper.EMHelper;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import de.greenrobot.event.EventBus;

/**
 * 统一处理用户头像 昵称 点击 弹窗
 * Created by zengjiang on 16/8/3.
 */

public class UserHeadNickClickHelper implements View.OnClickListener {
    private static UserHeadNickClickHelper instance;

    String userId;

    /**
     * 点击头像弹框
     */
    private ActionSheetDialog dialog;
    private String[] actionListWithIm = {"个人主页", "私聊"};
    private String[] actionListWithoutIm = {"个人主页"};
    /**
     * 聊天对象用户ID
     */
    private String toChatUserId = "";

    public static synchronized UserHeadNickClickHelper getInstance() {
        if (null == instance) {
            initInstance();
        }
        return instance;
    }

    private synchronized static void initInstance() {
        if (null == instance) {
            instance = new UserHeadNickClickHelper();
        }
    }

    public void onDestroy() {
        if (null != instance) {
            MyApplication.getInstance().unregisterEventBus(UserHeadNickClickHelper.this);
        }



        if (null!=dialog){
            dialog.dismiss();
            dialog = null;
        }



        instance = null;
    }

    private UserHeadNickClickHelper() {
        userId = UserInfoUtil.getInstance().getUserId();
    }

    /**
     * 所有带关注button的用户列表，整栏可点至用户个人主页
     *
     * @param parentView 整栏
     * @param nickName   昵称
     * @param userId     用户ID
     */
    public void setParentViewClickListener(View parentView, final String nickName, final String userId) {
        if (UserInfoUtil.getInstance().isCurrentUser(userId)) {
            parentView.setOnClickListener(null);
            parentView.setBackgroundColor(Color.WHITE);
        } else {
            parentView.setBackgroundResource(R.drawable.selector_list_item_bg);
            parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityRouter.openOtherPageActivity(v.getContext(), userId);
                }
            });
        }
    }

    /**
     * 所有可点的头像（注：以此区分自己的头像、注册后的推荐关注的头像），点按操作如上图
     *
     * @param headView 所有可点的头像
     * @param nickName 昵称
     * @param userId   用户ID
     */
    public void setClickListener(View headView, final String nickName, final String userId, String uuid, String avatar) {


        if (isCurrentUser(userId)) {
            //当前用户不处理
            headView.setOnClickListener(null);
            return;
        }

        //设置view tag

        ChatUserInfo userInfo = (ChatUserInfo) headView.getTag(R.id.tag_user_info);


        if (null == userInfo) {

            userInfo = new ChatUserInfo();
        }

        userInfo.setNick_name(nickName);
        userInfo.setUser_id(userId);
        userInfo.setAvatar(avatar);
        headView.setTag(R.id.tag_user_info, userInfo);

        //设置监听事件
        headView.setOnClickListener(this);

    }


    @Override
    public void onClick(final View v) {
        final ChatUserInfo userInfo = (ChatUserInfo) v.getTag(R.id.tag_user_info);


        if (null == userInfo) {
            return;
        }


        initDialog(v.getContext());

        toChatUserId = userInfo.getUser_id();

        //打开对话框更新或保存用户信息
        UserInfoDbUtil.getInstance().addOrUpdateUserInfo(userInfo);

        String imId = UserInfoDbUtil.getInstance().getImId(toChatUserId);

        if (TextUtils.isEmpty(imId)) {
            setItems(false);
            MyApplication.getInstance().registerEventBus(UserHeadNickClickHelper.this);
            //获取环信ID
            EventBus.getDefault().post(new GetOtherHxIdEvent(userInfo.getUser_id()));

        } else {
            userInfo.setIm_id(imId);
            setItems(true);
        }


        if (TextUtils.isEmpty(userInfo.getNick_name())) {
            //昵称为空
            dialog.isTitleShow(false);
        } else {
            //设置昵称为标题
            dialog.title(userInfo.getNick_name());
        }

        //点击事件
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        //跳转他人主页
                        ActivityRouter.openOtherPageActivity(v.getContext(), userInfo.getUser_id());
                        break;
                    case 1:
                        //跳转聊天
                        if (UserInfoUtil.getInstance().isLogin()) {

                            if (userInfo.getIm_enabled()){
                                ActivityRouter.openChatActvity(v.getContext(), userInfo.getIm_id(), userInfo.getUser_id());
                            }else {
                                EventBus.getDefault().post(GlobalDialogEvent.getGlobalDialogEvent("无法发送", "账号被冻结，有疑问请联系磨房网"));
                            }

                        } else {
                            //还没登陆
                            ActivityRouter.openLoginActivity(v.getContext());
                        }

                        break;
                }
            }
        });


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                toChatUserId = "";
                MyApplication.getInstance().unregisterEventBus(UserHeadNickClickHelper.this);
            }
        });

        dialog.show();


    }

    /**
     * 初始化对话框
     *
     * @param context
     */
    private void initDialog(Context context) {

        if (dialog == null) {
            dialog = new ActionSheetDialog(context, actionListWithoutIm, null);
        } else {


            if (!dialog.getmContext().equals(context)) {
                dialog = null;
                //切换账号时userid可能会变
                this.userId = UserInfoUtil.getInstance().getUserId();
                initDialog(context);
            }

        }
    }


    private boolean isCurrentUser(String userId) {

        if (null != this.userId && null != userId) {
            return this.userId.equals(userId);
        }

        return false;
    }


    private void setItems(boolean hasImId) {
        if (null!=dialog){
            dialog.setItems(hasImId && EMHelper.getInstance().isLoggedIn()? actionListWithIm : actionListWithoutIm);
        }

    }


    /**
     * 获取IM用户信息 回调
     *
     * @param hxidEvent
     */
    public void onEventMainThread(HxidEvent hxidEvent) {

        if (TextUtils.isEmpty(toChatUserId)) {
            return;
        }

        if (hxidEvent.getAction().equals(IHxIdAction.REQUEST_ID)) {


            if (hxidEvent.getUserId().equals(toChatUserId)) {
                if (hxidEvent.isSuccess() && hxidEvent.getInfo() != null) {
                    //获取到环信ID,更新UI
                    setItems(!TextUtils.isEmpty(hxidEvent.getInfo().getIm_id()));
                }
            }
        }


    }

}
