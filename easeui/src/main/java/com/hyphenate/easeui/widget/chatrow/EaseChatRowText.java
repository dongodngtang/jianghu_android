package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.exceptions.HyphenateException;

import net.doyouhike.app.library.ui.widgets.linkify.SmartTextCallback;
import net.doyouhike.app.library.ui.widgets.linkify.SmartTextClickMovement;

public class EaseChatRowText extends EaseChatRow implements SmartTextCallback {

    private TextView contentView;

    public EaseChatRowText(Context context, EMMessage message, int position,
                           BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
    }

    @Override
    protected void onFindViewById() {

        contentView = (TextView) findViewById(R.id.tv_chatcontent);
    }

    @Override
    public void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        Spannable spannable = EaseSmileUtils.getSmiledText(context, txtBody.getMessage(),this);

        // 设置内容
        contentView.setText(spannable);
        contentView.setMovementMethod(SmartTextClickMovement.getInstance());
        contentView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                bubbleLayout.performLongClick();
                return false;
            }
        });
        handleTextMessage();
    }

    protected void handleTextMessage() {
        if (message.direct() == EMMessage.Direct.SEND) {
            setMessageSendCallback();
            switch (message.status()) {
                case CREATE:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.GONE);
                    break;
                case FAIL:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.VISIBLE);
                    break;
                case INPROGRESS:
                    progressBar.setVisibility(View.VISIBLE);
                    statusView.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        } else {
            if (!message.isAcked() && message.getChatType() == ChatType.Chat) {
                try {
                    EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onBubbleClick() {
        // TODO Auto-generated method stub

    }


    @Override
    public void hashTagClick(String hashTag) {

    }

    @Override
    public void mentionClick(String mention) {

    }

    @Override
    public void emailClick(String email) {

    }

    @Override
    public void phoneNumberClick(final String phoneNumber) {
        if (itemClickListener != null)
            itemClickListener.rowTextOpenPhoneDialgo(phoneNumber);
    }

    @Override
    public void webUrlClick(String webUrl) {
        if (itemClickListener != null)
            itemClickListener.rowTextOpenWeb(webUrl);
    }


}
