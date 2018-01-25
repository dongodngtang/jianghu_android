package com.hyphenate.easeui.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseMessageAdapter;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

public class EaseChatMessageList extends RelativeLayout {

    protected static final String TAG = "EaseChatMessageList";
    protected ListView listView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected Context context;
    protected EMConversation conversation;
    protected int chatType;
    protected String toChatUsername;
    protected EaseMessageAdapter messageAdapter;
    protected boolean showUserNick;
    protected boolean showAvatar;
    protected Drawable myBubbleBg;
    protected Drawable otherBuddleBg;

    public EaseChatMessageList(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public EaseChatMessageList(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseStyle(context, attrs);
        init(context);
    }

    public EaseChatMessageList(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.ease_chat_message_list, this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.chat_swipe_layout);
        listView = (ListView) findViewById(R.id.list);
    }

    /**
     * init widget
     *
     * @param toChatUsername
     * @param chatType
     * @param customChatRowProvider
     */
    public void init(String toChatUsername, int chatType, EaseCustomChatRowProvider customChatRowProvider) {
        this.chatType = chatType;
        this.toChatUsername = toChatUsername;

        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername, EaseCommonUtils.getConversationType(chatType), true);
        messageAdapter = new EaseMessageAdapter(context, toChatUsername, chatType, listView);
        messageAdapter.setShowAvatar(showAvatar);
        messageAdapter.setShowUserNick(showUserNick);
        messageAdapter.setMyBubbleBg(myBubbleBg);
        messageAdapter.setOtherBuddleBg(otherBuddleBg);
        messageAdapter.setCustomChatRowProvider(customChatRowProvider);
        // set message adapter
        listView.setAdapter(messageAdapter);

        refreshSelectLast();
    }

    protected void parseStyle(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EaseChatMessageList);
        showAvatar = ta.getBoolean(R.styleable.EaseChatMessageList_msgListShowUserAvatar, true);
        myBubbleBg = ta.getDrawable(R.styleable.EaseChatMessageList_msgListMyBubbleBackground);
        otherBuddleBg = ta.getDrawable(R.styleable.EaseChatMessageList_msgListMyBubbleBackground);
        showUserNick = ta.getBoolean(R.styleable.EaseChatMessageList_msgListShowUserNick, false);
        ta.recycle();
    }


    /**
     * refresh
     */
    public void refresh() {
        if (messageAdapter != null) {
            messageAdapter.refresh();
        }
    }

    /**
     * refresh and jump to the last
     */
    public void refreshSelectLast() {
        if (messageAdapter != null) {
            messageAdapter.refreshSelectLast();
        }
    }

    /**
     * refresh and jump to the position
     *
     * @param position
     */
    public void refreshSeekTo(int position) {
        if (messageAdapter != null) {
            messageAdapter.refreshSeekTo(position);
        }
    }

    public ListView getListView() {
        return listView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public EMMessage getItem(int position) {
        return messageAdapter.getItem(position);
    }

    public void setShowUserNick(boolean showUserNick) {
        this.showUserNick = showUserNick;
    }

    public boolean isShowUserNick() {
        return showUserNick;
    }

    public interface MessageListItemClickListener {
        void onResendClick(EMMessage message);

        /**
         * there is default handling when bubble is clicked, if you want handle it, return true
         * another way is you implement in onBubbleClick() of chat row
         *
         * @param message
         * @return
         */
        boolean onBubbleClick(EMMessage message);

        void onBubbleLongClick(EMMessage message);

        void onUserAvatarClick(String username);

        void onUserAvatarLongClick(String username);

        /**
         * 对方头像
         * @return
         */
        String getToChatUserAvatar();

        /**
         * 对方 userId
         * @return
         */
        String getToChatUserId();

        /**
         * 对方 昵称
         * @return
         */
        String getToChatNickname();

        /**
         * 我的 头像
         * @return
         */
        String getMeChatUserAvatar();

        /**
         * 我的 userId
         * @return
         */
        String getMeChatUserId();

        /**
         * 设置用户头像
         * @param context
         * @param avatar
         * @param imageView
         */
        void setUserAvatar(Context context, String avatar, ImageView imageView);

        /**
         * 拨号回调
         * @param phoneNumber
         */
        void rowTextOpenPhoneDialgo(String phoneNumber);

        /**
         * 网页链接回调
         * @param webUrl
         */
        void rowTextOpenWeb(String webUrl);

        /**
         * 显示大图
         * @param imgBody
         */
        void showBigImage(EMImageMessageBody imgBody);

        /**
         * 显示聊天列表图
         * @param imgBody
         * @param iv
         */
        void displayImage(EMImageMessageBody imgBody, ImageView iv);

        Intent bigImageActivity();
    }

    /**
     * set click listener
     *
     * @param listener
     */
    public void setItemClickListener(MessageListItemClickListener listener) {
        if (messageAdapter != null) {
            messageAdapter.setItemClickListener(listener);
        }
    }

    /**
     * set chat row provider
     *
     * @param rowProvider
     */
    public void setCustomChatRowProvider(EaseCustomChatRowProvider rowProvider) {
        if (messageAdapter != null) {
            messageAdapter.setCustomChatRowProvider(rowProvider);
        }
    }
}
