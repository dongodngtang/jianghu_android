package net.doyouhike.app.bbs.ui.util;

import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.db.green.entities.Follow;
import net.doyouhike.app.bbs.biz.event.open.AccountUserFollowEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.AttendState;
import net.doyouhike.app.bbs.biz.openapi.presenter.IEventListener;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import de.greenrobot.event.EventBus;

/**
 * 功能：
 *
 * @author：曾江 日期：16-5-3.
 */
public class FollowButtonHelper {

    public static void setTextState(TextView tvFollow, final int isFollowed, final String userId) {
        setTextState(tvFollow, isFollowed, userId, null);
    }


    public static void setTextState(TextView tvFollow, final int isFollowed, final String userId, IEventListener uiListener) {
        // 按钮显示及点击监听
        // 在别人的列表中，出现自己，则没有按钮
        if (UserInfoUtil.getInstance().getUserId().equals(userId)) {
            tvFollow.setVisibility(View.GONE);
            return;
        }

        setFollowStyle(tvFollow, isFollowed);
        setFollowHandle(tvFollow, userId, uiListener);
    }


    private static void setFollowHandle(final TextView tvFollow, final String userId, final IEventListener uiListener) {
        tvFollow.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            // 右边按钮事件

                                            if (!UserInfoUtil.getInstance().isLogin()) {
                                                ActivityRouter.openLoginActivity(v.getContext());
                                                return;
                                            }

                                            if (((int) tvFollow.getTag()) == AttendState.ATTENTION_TO_INVITE) {
                                                // 发送短信 zwj
                                                if (PhoneNumberUtils.isGlobalPhoneNumber(userId)) {
                                                    Intent intent = new Intent(Intent.ACTION_SENDTO,
                                                            Uri.parse("smsto:" + userId));
                                                    String format = v.getContext().getString(R.string.invite_msg);
                                                    String nickname = UserInfoUtil.getInstance().getCurrentUser().getUser().getNick_name();
                                                    String content = String.format(format, nickname);
                                                    intent.putExtra("sms_body", content);
                                                    v.getContext().startActivity(intent);
                                                } else {
                                                    if (UIUtils.isShowToast) {
                                                        StringUtil.showSnack(v.getContext(),
                                                                "号码错误，无法邀请该好友, phone = " + userId);
                                                    } else {
                                                        StringUtil.showSnack(v.getContext(), "号码错误，无法邀请该好友");
                                                    }
                                                }
                                            } else if (((int) tvFollow.getTag()) == AttendState.NOT_ATTEND) {
                                                // 去执行 关注
                                                UsersHelper.getSingleTon().follow(userId, new IOnResponseListener<Response<Follow>>() {

                                                    @Override
                                                    public void onSuccess(Response<Follow> response) {
                                                        if (response.getData() != null) {
                                                            UsersHelper.getSingleTon().addFollow(response.getData());
                                                            AccountUserFollowEvent event = new AccountUserFollowEvent();
                                                            event.setResponse(response);
                                                            EventBus.getDefault().post(event);
                                                            if (tvFollow != null) {
                                                                setFollowStyle(tvFollow, AttendState.ATTENDING);

                                                            }
                                                        }
                                                        if (uiListener != null)
                                                            uiListener.onSucceed();

                                                    }

                                                    @Override
                                                    public void onError(Response response) {
                                                        if (uiListener != null)
                                                            uiListener.onFailed();
                                                        if (response.getCode() == 1110002) {
                                                            if (tvFollow != null) {
                                                                setFollowStyle(tvFollow, AttendState.ATTENDING);

                                                            }
                                                        }
                                                    }
                                                });

                                            } else {
                                                // 去执行 取消关注
                                                UsersHelper.getSingleTon().unFollow(userId, new IOnResponseListener<Response<Follow>>() {

                                                    @Override
                                                    public void onSuccess(Response<Follow> response) {
                                                        unFollow(response);
                                                        if (uiListener != null)
                                                            uiListener.onSucceed();
                                                    }

                                                    @Override
                                                    public void onError(Response response) {
                                                        if (uiListener != null)
                                                            uiListener.onFailed();
                                                        if (response.getCode() == 1110002) {
                                                            unFollow(response);
                                                        }
                                                    }
                                                });
                                            }

                                        }


                                        private void unFollow(Response response) {
                                            String user_id = (String) response.getExtraTag();
                                            if (user_id.equals(userId)) {
                                                UsersHelper.getSingleTon().deleteFollow(user_id);
                                                AccountUserFollowEvent event = new AccountUserFollowEvent();
                                                event.setResponse(response);
                                                EventBus.getDefault().post(event);
                                                if (tvFollow != null)
                                                    setFollowStyle(tvFollow, AttendState.NOT_ATTEND);
                                            }
                                        }
                                    }
        );
    }

    /**
     * 设置关注按钮状态
     *
     * @param tvFollow   关注按钮
     * @param isFollowed 关注状态
     */
    private static void setFollowStyle(TextView tvFollow, int isFollowed) {
        tvFollow.setTag(isFollowed);
        tvFollow.setGravity(Gravity.CENTER);
        tvFollow.setVisibility(View.VISIBLE);
        tvFollow.setBackgroundResource(R.drawable.selector_common_button);
        tvFollow.setTextColor(tvFollow.getResources().getColorStateList(R.color.txt_button_common_light));
        tvFollow.setSelected(true);

        if (isFollowed == 2) { // 相互关注
            tvFollow.setText("相互关注");
            tvFollow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        } else if (isFollowed == 1) { // 自己关注对方
            tvFollow.setText(tvFollow.getContext().getString(R.string.following));
            tvFollow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        } else if (isFollowed == 0) { // 自己没关注对方
            tvFollow.setText(tvFollow.getContext().getString(R.string.follow));
            tvFollow.setSelected(false);
            tvFollow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        } else if (isFollowed == 3) {
            tvFollow.setText("邀请");
            tvFollow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tvFollow.setTextColor(tvFollow.getResources().getColorStateList(R.color.txt_dark_content));
            tvFollow.setBackgroundResource(R.drawable.shape_follow_3);
        }
    }
}
