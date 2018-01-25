package net.doyouhike.app.bbs.chat.chatui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.utils.PreferenceManager;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.hyphenate.util.NetUtils;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.event.GlobalDialogEvent;
import net.doyouhike.app.bbs.biz.event.im.HxidEvent;
import net.doyouhike.app.bbs.biz.helper.im.action.IHxIdAction;
import net.doyouhike.app.bbs.biz.helper.message.MessageHelper;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.chat.helper.EMHelper;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.library.ui.snackbar.SnackBar;

import de.greenrobot.event.EventBus;

/**
 * luochangdong on 16/7/20
 * 描述: 消息对话碎片
 */
public class ConversationListFragment extends EaseConversationListFragment {

    private TextView errorText;

    /**
     * 赞、评论、活动 消息业务工具
     */
    MessageHelper mMessageHelper;

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        View errorView =  View.inflate(getActivity(), R.layout.em_chat_neterror_item, null);
//        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);

        //  赞、评论、活动 添加到Header
        mMessageHelper = new MessageHelper(getContext());

        View newsHeader = mMessageHelper.getView();
        conversationListView.addHeaderView(newsHeader);

    }


    @Override
    protected void setUpView() {
        conversationListView.setConversationListHelper(new EaseConversationList.EaseConversationListHelper() {
            @Override
            public String onSetItemSecondaryText(EMMessage lastMessage) {
                return null;
            }

            @Override
            public void setAvatarAndNick(Context context, ImageView imageView, TextView textView, String hxId) {
                EMHelper.setUserAvatar(context, hxId, imageView, textView);
            }

            @Override
            public String getNickByHxId(String hxId) {
                return EMHelper.getNickByHxId(hxId);
            }

            @Override
            public void refreshCount(String hxId) {
                //统计数量刷新
                mMessageHelper.refreshCount();
                //草稿删除
                PreferenceManager.getInstance().setChatDraft(hxId,"");
            }
        });
        super.setUpView();
        //点击对话项跳转到聊天界面
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    return;
                EMConversation conversation = conversationListView.getItem(position - 1);
                String username = conversation.getUserName();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    StringUtil.showSnack(getActivity(), R.string.Cant_chat_with_yourself);
                else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    if (conversation.isGroup()) {
                        if (conversation.getType() == EMConversationType.ChatRoom) {
                            // it's group chat
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                        } else {
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        }

                    }
                    // it's single chat 单聊
                    intent.putExtra(ChatActivity.IM_USER_ID, username);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())) {
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setPadding(0, UIUtils.getTranslucentStatusHeight(getContext()), 0, 0);
        setSystemBarTintDrawable(R.color.system_bar_bg);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            LogUtil.d("mMessageHelper");
            mMessageHelper.refreshCount();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("mMessageHelper onResume");
        mMessageHelper.refreshCount();
    }

    /**
     * 沉浸式状态栏管理
     */
    public void setSystemBarTintDrawable(int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (null != getActivity() && getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).setSystemBarTintDrawable(resourceId);
            }
        }

    }

    @Override
    protected void onLoginAnotherDevice() {
        //账户在另一台设备登陆,弹框
        EventBus.getDefault().post(GlobalDialogEvent.getLoginOutEvent());
    }

    /**
     * 获取用户信息 刷新
     *
     * @param hxidEvent
     */
    public void onEventMainThread(HxidEvent hxidEvent) {
        if (null != hxidEvent.getAction() && hxidEvent.getAction().equals(IHxIdAction.REQUEST_BY_HX_ID))
            refresh();
    }

    @Override
    public void onDestroyView() {
        mMessageHelper.onDestroy();
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
