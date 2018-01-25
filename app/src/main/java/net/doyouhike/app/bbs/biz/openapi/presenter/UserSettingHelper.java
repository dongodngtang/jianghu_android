package net.doyouhike.app.bbs.biz.openapi.presenter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersSettingPut;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserSettingResp;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.UserInfoUtil;

/**
 * 作者：luochangdong on 16/10/17
 * 描述：
 */
public class UserSettingHelper {

    private static UserSettingHelper instance;

    public static UserSettingHelper getInstance() {
        if (instance == null)
            instance = new UserSettingHelper();
        return instance;
    }

    private void initDialog(Context context) {
        String[] item = {"关闭提醒"};
        dialog = new ActionSheetDialog(context, item, null);
        dialog.isTitleShow(false)
                .itemTextColor(context.getResources().getColor(R.color.textcolor_red));
        dialog.show();
    }

    private UserSettingResp.UserSettingBean userSettingBean;

    public UserSettingResp.UserSettingBean getUserSettingBean() {
        return SharedPreferencesManager.getUserSetting(MyApplication.getInstance());
    }

    /**
     * 底部弹窗
     */
    ActionSheetDialog dialog;

    public void setUserSettingBean(UserSettingResp.UserSettingBean userSettingBean) {
        if (userSettingBean != null)
            SharedPreferencesManager.setUserSetting(MyApplication.getInstance(), userSettingBean);
    }

    public void updateSettingBean() {
        if (userSettingBean != null)
            SharedPreferencesManager.setUserSetting(MyApplication.getInstance(), userSettingBean);
    }


    /**
     * 活动设置
     *
     * @param context
     * @param listener
     */
    public void pushEvent(final Context context, final IOnResponseListener listener, boolean showDialog) {
        userSettingBean = SharedPreferencesManager.getUserSetting(MyApplication.getInstance());
        final boolean value = !userSettingBean.isPush_event_msg();
        userSettingBean.setPush_event_msg(value);
        if (showDialog) {
            if (value) {
                UsersHelper.getSingleTon().putUserSetting(context, UserInfoUtil.getInstance().getUserId(), UsersSettingPut.PUSH_EVENT_MSG, value, listener);
            } else {
                initDialog(context);
                dialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.dismiss();
                        UsersHelper.getSingleTon().putUserSetting(context, UserInfoUtil.getInstance().getUserId(), UsersSettingPut.PUSH_EVENT_MSG, value, listener);
                    }
                });
            }
        } else
            UsersHelper.getSingleTon().putUserSetting(context, UserInfoUtil.getInstance().getUserId(), UsersSettingPut.PUSH_EVENT_MSG, value, listener);
    }

    /**
     * 点赞设置
     *
     * @param context
     * @param listener
     */
    public void pushLike(final Context context, final IOnResponseListener listener, boolean showDialog) {
        userSettingBean = SharedPreferencesManager.getUserSetting(MyApplication.getInstance());
        final boolean value = !userSettingBean.isPush_like_msg();
        userSettingBean.setPush_like_msg(value);
        if (showDialog) {
            if (value) {
                UsersHelper.getSingleTon().putUserSetting(context, UserInfoUtil.getInstance().getUserId(), UsersSettingPut.PUSH_LIKE_MSG, value, listener);
            } else {
                initDialog(context);
                dialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.dismiss();
                        UsersHelper.getSingleTon().putUserSetting(context, UserInfoUtil.getInstance().getUserId(), UsersSettingPut.PUSH_LIKE_MSG, value, listener);
                    }
                });
            }
        } else
            UsersHelper.getSingleTon().putUserSetting(context, UserInfoUtil.getInstance().getUserId(), UsersSettingPut.PUSH_LIKE_MSG, value, listener);
    }

    /**
     * 评论设置
     *
     * @param context
     * @param listener
     */
    public void pushComment(final Context context, final IOnResponseListener listener, boolean showDialog) {
        userSettingBean = SharedPreferencesManager.getUserSetting(MyApplication.getInstance());
        final boolean value = !userSettingBean.isPush_comment_msg();
        userSettingBean.setPush_comment_msg(value);
        if (showDialog) {
            if (value) {
                UsersHelper.getSingleTon().putUserSetting(context,
                        UserInfoUtil.getInstance().getUserId(), UsersSettingPut.PUSH_COMMENT_MSG, value, listener);
            } else {
                initDialog(context);
                dialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.dismiss();
                        UsersHelper.getSingleTon().putUserSetting(context,
                                UserInfoUtil.getInstance().getUserId(), UsersSettingPut.PUSH_COMMENT_MSG, value, listener);
                    }
                });
            }
        } else
            UsersHelper.getSingleTon().putUserSetting(context, UserInfoUtil.getInstance().getUserId(), UsersSettingPut.PUSH_COMMENT_MSG, value, listener);
    }

    /**
     * 粉丝设置
     *
     * @param context
     * @param listener
     */
    public void pushFans(final Context context, final IOnResponseListener listener, boolean showDialog) {
        userSettingBean = SharedPreferencesManager.getUserSetting(MyApplication.getInstance());
        final boolean value = !userSettingBean.isPush_fans_msg();
        userSettingBean.setPush_fans_msg(value);
        if (showDialog) {
            if (value) {
                UsersHelper.getSingleTon().putUserSetting(context,
                        UserInfoUtil.getInstance().getUserId(), UsersSettingPut.PUSH_FANS_MSG, value, listener);
            } else {
                initDialog(context);
                dialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.dismiss();
                        UsersHelper.getSingleTon().putUserSetting(context,
                                UserInfoUtil.getInstance().getUserId(), UsersSettingPut.PUSH_FANS_MSG, value, listener);
                    }
                });
            }
        } else
            UsersHelper.getSingleTon().putUserSetting(context,
                    UserInfoUtil.getInstance().getUserId(), UsersSettingPut.PUSH_FANS_MSG, value, listener);
    }

    /**
     * 附近设置
     *
     * @param context
     * @param listener
     */
    public void pushNearly(Context context, IOnResponseListener listener) {
        userSettingBean = SharedPreferencesManager.getUserSetting(MyApplication.getInstance());
        boolean value = !userSettingBean.isFind_by_nearly();
        userSettingBean.setFind_by_nearly(value);
        UsersHelper.getSingleTon().putUserSetting(context, UserInfoUtil.getInstance().getUserId(), UsersSettingPut.FIND_BY_NEARLY, value, listener);
    }

    /**
     * 手机号搜索
     *
     * @param context
     * @param listener
     */
    public void pushFindByPhone(Context context, IOnResponseListener listener) {
        userSettingBean = SharedPreferencesManager.getUserSetting(MyApplication.getInstance());
        boolean value = !userSettingBean.isFind_by_phone();
        userSettingBean.setFind_by_phone(value);
        UsersHelper.getSingleTon().putUserSetting(context, UserInfoUtil.getInstance().getUserId(), UsersSettingPut.FIND_BY_PHONE, value, listener);
    }
}
