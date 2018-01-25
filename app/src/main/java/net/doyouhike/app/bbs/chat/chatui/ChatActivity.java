package net.doyouhike.app.bbs.chat.chatui;

import android.content.Intent;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.event.im.GetOtherHxIdEvent;
import net.doyouhike.app.bbs.biz.event.im.HxidEvent;
import net.doyouhike.app.bbs.biz.helper.im.action.IHxIdAction;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.StringUtil;

import de.greenrobot.event.EventBus;

/**
 * luochangdong on 16/7/20
 * 描述: 聊天窗口
 */
public class ChatActivity extends BaseActivity {
    public static String USER_ID = "userId";
    public static String IM_USER_ID = "hx_userId";

    /**
     * 没有环信 ID 再次获取
     */
    public boolean RETRY = true;
    /**
     * 聊天碎片
     */
    private EaseChatFragment chatFragment;
    /**
     * 对方的user ID
     */
    String toChatUserId;

    /**
     * 对方环信ID
     */
    String toChatHxId;


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.em_activity_chat;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }


    @Override
    protected void initViewsAndEvents() {
        //get user id or group id
        toChatUserId = getIntent().getExtras().getString(USER_ID);
        toChatHxId = getIntent().getExtras().getString(IM_USER_ID);

        LogUtil.d(TAG_LOG, "用户 toChatUserId : " + toChatUserId + " toChatHxId: " + toChatHxId);

        if (StringUtil.isNotEmpty(toChatHxId) && StringUtil.isNotEmpty(toChatUserId)) {
            initChatFragment(toChatHxId);

        } else if (StringUtil.isNotEmpty(toChatHxId) && StringUtil.isEmpty(toChatUserId)) {
            initChatFragment(toChatHxId);
        } else if (StringUtil.isNotEmpty(toChatUserId) && StringUtil.isEmpty(toChatHxId)) {
            //获取环信信息
            EventBus.getDefault().post(new GetOtherHxIdEvent(toChatUserId));
        }


    }

    /**
     * 初始化聊天Fragment
     * @param toChatHxId
     */
    private void initChatFragment(String toChatHxId) {
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        LogUtil.d(TAG_LOG, "用户环信ID: " + toChatHxId);
        getIntent().putExtra(EaseConstant.EXTRA_USER_ID, toChatHxId);
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    /**
     * 获取用户信息 回调
     *
     * @param hxidEvent
     */
    public void onEventMainThread(HxidEvent hxidEvent) {
        if (hxidEvent.getAction().equals(IHxIdAction.REQUEST_ID))
            if (hxidEvent.getUserId().equals(toChatUserId)) {
                if (hxidEvent.isSuccess() && hxidEvent.getInfo() != null && StringUtil.isNotEmpty(hxidEvent.getInfo().getIm_id())) {
                    //use EaseChatFratFragment
                    initChatFragment(hxidEvent.getInfo().getIm_id());
                } else {
                    //获取环信信息
                    if (RETRY) {
                        RETRY = false;
                        EventBus.getDefault().post(new GetOtherHxIdEvent(toChatUserId));
                    }
                }
            }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra(IM_USER_ID);
        if (toChatHxId.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        if (chatFragment != null)
            chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            finish();
        }
    }


}
