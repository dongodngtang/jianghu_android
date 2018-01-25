package net.doyouhike.app.bbs.chat.chatui;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentHelper;
import com.hyphenate.easeui.utils.EaseImageUtils;
import com.hyphenate.easeui.utils.PreferenceManager;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.DateUtils;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.PathUtil;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.db.dao.UserInfoDbUtil;
import net.doyouhike.app.bbs.biz.db.green.entities.ChatUserInfo;
import net.doyouhike.app.bbs.biz.event.im.SendPrivateMsgEvent;
import net.doyouhike.app.bbs.biz.helper.im.ImLoginHelper;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.MyImInfo;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.chat.helper.EMHelper;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.ui.util.SnackUtil;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.widget.common.webview.BaseWebViewActivity;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * luochangdong on 16/7/20
 * 描述:聊天碎片
 */
public class ChatFragment extends EaseChatFragment implements EaseChatFragmentHelper {

    // constant start from 11 to avoid conflict with constant in base class
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;


    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

    //red packet code : 红包功能使用的常量
    private static final int MESSAGE_TYPE_RECV_RED_PACKET = 5;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET = 6;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET_ACK = 7;
    private static final int MESSAGE_TYPE_RECV_RED_PACKET_ACK = 8;
    private static final int REQUEST_CODE_SEND_RED_PACKET = 16;
    private static final int ITEM_RED_PACKET = 16;
    //end of red packet code

    /**
     * 对方的信息
     */
    ChatUserInfo toChatUserInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void setUpView() {
        toChatUserInfo = UserInfoDbUtil.getInstance().getUserFromImId(toChatHxUserId);
        setChatFragmentListener(this);
        super.setUpView();
        // set click listener 返回事件
        titleBar.setLeftLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                onBackPressed();
            }
        });


        //未发送的草稿
        String draft = PreferenceManager.getInstance().getChatDraft(toChatHxUserId);
        if (draft != null)
            inputMenu.getPrimaryMenu().getEditText().setText(draft);
    }

    @Override
    protected void registerExtendMenuItem() {
        //use the menu in base class
        super.registerExtendMenuItem();
        //extend menu items

        //聊天室暂时不支持红包功能
        //red packet code : 注册红包菜单选项

        //end of red packet code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case ContextMenuActivity.RESULT_CODE_COPY: // copy
                    clipboard.setPrimaryClip(ClipData.newPlainText(null,
                            ((EMTextMessageBody) contextMenuMessage.getBody()).getMessage()));
                    break;
                case ContextMenuActivity.RESULT_CODE_DELETE: // delete
                    conversation.removeMessage(contextMenuMessage.getMsgId());
                    messageList.refresh();
                    break;

                case ContextMenuActivity.RESULT_CODE_FORWARD: // forward
//                Intent intent = new Intent(getActivity(), ForwardMessageActivity.class);
//                intent.putExtra("forward_msg_id", contextMenuMessage.getMsgId());
//                startActivity(intent);

                    break;

                default:
                    break;
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //send the video
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(CompressFormat.JPEG, 100, fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_FILE: //send the file
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_AT_USER:
                    if (data != null) {
                        String username = data.getStringExtra("username");
                        inputAtUsername(username, false);
                    }
                    break;
                //red packet code : 发送红包消息到聊天界面
                case REQUEST_CODE_SEND_RED_PACKET:
//                if (data != null){
//                    sendMessage(RedPacketUtil.createRPMessage(getActivity(), data, toChatUserId));
//                }
                    break;
                //end of red packet code
                default:
                    break;
            }
        }

    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        message.setAttribute("msg_time", DateUtils.getTimestampStr());
        message.setAttribute("nick_name", UserInfoUtil.getInstance().getCurrentUser().getUser().getNick_name());

    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }

    @Override
    public String getToChatUserId() {
        if (toChatUserInfo == null)
            return "";
        else
            return toChatUserInfo.getUser_id();
    }

    @Override
    public String getToChatAvatar() {
        if (toChatUserInfo == null)
            return "";
        else
            return Constant.PHOTO_DOMAIN_PATH + toChatUserInfo.getAvatar();
    }

    @Override
    public String getToChatNickname() {
        if (toChatUserInfo == null)
            return toChatHxUserId;
        else
            return toChatUserInfo.getNick_name();
    }

    @Override
    public String getMeChatHxId() {
        return UserInfoUtil.getInstance().getUUID();
    }

    @Override
    public String getMeChatAvatar() {
        return Constant.PHOTO_DOMAIN_PATH +
                UserInfoUtil.getInstance().getCurrentUser().getUser().getAvatar();
    }

    @Override
    public String getMeUserId() {
        return UserInfoUtil.getInstance().getUserId();
    }

    @Override
    public void setUserAvatar(Context context, String avatar, ImageView imageView) {
        if (avatar != null)
            GlideHelper.displayHeader(context, imageView, avatar);
    }

    @Override
    public void sendPrivateMsg(final EMMessage message) {
        EventBus.getDefault().post(new SendPrivateMsgEvent(getToChatUserId()));
        if (!EMHelper.getInstance().isLogined())
            ImLoginHelper.loginIm(new EMCallBack() {
                @Override
                public void onSuccess() {
                    EMHelper.getInstance().setLogined(true);
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    LogUtil.d("登录聊天服务器成功！");
                    resendMessage(message);

                }

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onProgress(int i, String s) {

                }
            });

    }

    /**
     * 拨号回调
     *
     * @param phoneNumber
     */
    @Override
    public void rowTextOpenPhoneDialog(final String phoneNumber) {
        String[] items = new String[]{getContext().getString(R.string.cancel), "拨打"};
        TipUtil.alert(getContext(), "拨打电话", phoneNumber, items, new OnBtnClickL() {
            @Override
            public void onBtnClick() {

            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
                getActivity().startActivity(intent);
            }
        });
    }

    /**
     * 链接跳转回调
     *
     * @param webUrl
     */
    @Override
    public void rowTextOpenWeb(String webUrl) {
        Intent intent = new Intent();
        intent.putExtra(BaseWebViewActivity.I_URL, webUrl);
        intent.setClass(getContext(), BaseWebViewActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void showBigImage(EMImageMessageBody imgBody) {

        LogUtil.d(TAG, "LocalUrl: " + imgBody.getLocalUrl() + " RemoteUrl: " + imgBody.getRemoteUrl());
        String url = "";
        if (StringUtil.isNotEmpty(imgBody.getLocalUrl())) {
            url = imgBody.getLocalUrl();
        } else if (StringUtil.isNotEmpty(imgBody.getRemoteUrl())) {
            url = imgBody.getRemoteUrl();
        }
        ActivityRouter.openGalleryActivity(getActivity(), new String[]{url}, 0);
    }

    @Override
    public void displayImage(EMImageMessageBody imgBody, ImageView iv) {
        String url = "";
        if (StringUtil.isNotEmpty(imgBody.getLocalUrl())) {
            url = EaseImageUtils.getThumbnailImagePath(imgBody.getLocalUrl());
        } else if (StringUtil.isNotEmpty(imgBody.thumbnailLocalPath())) {
            url = imgBody.thumbnailLocalPath();
        }
        GlideHelper.displayChatImage(getContext(), iv, url);
    }

    @Override
    public Intent bigImageActivity() {
        return new Intent(getActivity(), ShowBigImageActivity.class);
    }

    @Override
    public boolean isEnableUser() {
        MyImInfo info = SharedPreferencesManager.getImUserInfo();
        if (info == null)
            return false;
        else if (!info.isIm_enabled()) {
            TipUtil.alert(getActivity(), "无法发送", "账号被冻结，有疑问请联系磨房网");
        }
        return info.isIm_enabled();
    }


    @Override
    public void onEnterToChatDetails() {
        if (chatType == Constant.CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatHxUserId);
            if (group == null) {
                SnackUtil.showSnack(getActivity(), R.string.gorup_not_found);
                return;
            }
            //群聊跳转界面
//            startActivityForResult(
//                    (new Intent(getActivity(), GroupDetailsActivity.class).putExtra("groupId", toChatUserId)),
//                    REQUEST_CODE_GROUP_DETAIL);
        } else if (chatType == Constant.CHATTYPE_CHATROOM) {

//        	startActivityForResult(new Intent(getActivity(), ChatRoomDetailsActivity.class).putExtra("roomId", toChatUserId), REQUEST_CODE_GROUP_DETAIL);
        }
    }

    @Override
    public void onAvatarClick(String hxUserId) {
        //handling when user click avatar
        if (hxUserId.equals(toChatHxUserId))
            ActivityRouter.openOtherPageActivity(getContext(), getToChatUserId());
    }

    @Override
    public void onAvatarLongClick(String hxUserId) {
        inputAtUsername(hxUserId);
    }


    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
        //red packet code : 拆红包页面
//        if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)){
//            RedPacketUtil.openRedPacket(getActivity(), chatType, message, toChatUserId, messageList);
//            return true;
//        }
        //end of red packet code
        return false;
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //red packet code : 处理红包回执透传消息
//        for (EMMessage message : messages) {
//            EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
//            String action = cmdMsgBody.action();//获取自定义action
//            if (action.equals(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
//                RedPacketUtil.receiveRedPacketAckMessage(message);
//                messageList.refresh();
//            }
//        }
        //end of red packet code
        super.onCmdMessageReceived(messages);
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        // no message forward when in chat room
        if (message.getType() == EMMessage.Type.TXT) {
            startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class)).putExtra("message", message)
                            .putExtra("ischatroom", chatType == EaseConstant.CHATTYPE_CHATROOM),
                    REQUEST_CODE_CONTEXT_MENU);
        }

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VIDEO:

                break;
            case ITEM_FILE: //file
                selectFileFromLocal();
                break;
            case ITEM_VOICE_CALL:
                break;
            case ITEM_VIDEO_CALL:
                break;
            //red packet code : 进入发红包页面
            case ITEM_RED_PACKET:

                break;
            //end of red packet code
            default:
                break;
        }
        //keep exist extend menu
        return false;
    }

    /**
     * select file
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }


    /**
     * chat row provider
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
            //which is used to count the number of different chat row
            return 8;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                //voice call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    //video call
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
                //red packet code : 红包消息和红包回执消息的chat row type

                //end of red packet code
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                // voice call or video call
                //red packet code : 红包消息和红包回执消息的chat row
                //end of red packet code
            }
            return null;
        }

    }


}
