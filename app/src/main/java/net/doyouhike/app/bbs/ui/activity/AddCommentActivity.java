package net.doyouhike.app.bbs.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodesCommentPost;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventCommentListResp;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventCommentResp;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class AddCommentActivity extends BaseActivity {

    public static final String INTENT_EXTRA_NAME_LIVE_ID = "liveid";
    public static final String INTENT_EXTRA_NAME_COMMENT_ID = "commentId";
    public static final String INTENT_EXTRA_NAME_COMMENT_NICKNAME = "commentNickName";
    public static final String INTENT_EXTRA_NAME_USER_ID = "userId";

    public static final String INTENT_EXTRA_NAME_COMMENT_INFO = "comment_info";//评论界面返回数据码

    public static final int RESULT_CODE_TO_COMMENT_FINISH = 2315;
    /**
     * 标题,如 回复谁谁谁
     */
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    /**
     * 发布
     */
    @InjectView(R.id.tv_send)
    TextView tvSend;
    /**
     * 数量提示
     */
    @InjectView(R.id.tv_text_count)
    TextView tvTextCount;
    @InjectView(R.id.title_left_content)
    LinearLayout mTitleBack;
    /**
     * 添加评论
     */
    @InjectView(R.id.edt_activity_comment)
    EditText edtActivityComment;

    /**
     * 发送评论参数
     */
    NodesCommentPost mPublishCommentParam;


    /**
     * 被回复评论者昵称
     */
    private String reply_to_nick_name;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_add_comment;
    }

    @Override
    protected void initViewsAndEvents() {
        initData();
        initTitle();
        initListener();
    }


    /**
     * 初始化监听事件
     */
    private void initListener() {

        mTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCommentActivity.this.finish();
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSensitive();
            }
        });

        edtActivityComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null == s) {
                    return;
                }
                setTextCountTip(s.toString().trim().length());
            }
        });

    }


    /**
     * 初始化数据
     */
    private void initData() {

        mPublishCommentParam = new NodesCommentPost();


        // 主题ID
        String topicId = getIntent().getStringExtra(INTENT_EXTRA_NAME_LIVE_ID);
        mPublishCommentParam.setNode_id(topicId);
        mPublishCommentParam.setExtraInfo(topicId);
        //评论ID
        mPublishCommentParam.setRefrence_id(getIntent().getStringExtra(INTENT_EXTRA_NAME_COMMENT_ID));
        //被回复评论者ID
        mPublishCommentParam.setReply_to(getIntent().getStringExtra(INTENT_EXTRA_NAME_USER_ID));

        //被回复者昵称
        reply_to_nick_name = getIntent().getStringExtra(INTENT_EXTRA_NAME_COMMENT_NICKNAME);

    }


    /**
     * 设置标题
     */
    private void initTitle() {
        //被回复的评论ID,如果对评论进行评论，commentId值为被评论的commentId,如果不是，commentId为0
        if (StringUtil.isEmpty(mPublishCommentParam.getRefrence_id())) {
            return;
        }
        tvTitle.setText("回复评论");

        if (!TextUtils.isEmpty(reply_to_nick_name)) {
            edtActivityComment.setHint("回复 " + reply_to_nick_name + ":");
        }
    }

    /**
     * @param len 输入文本长度变化
     */
    private void setTextCountTip(int len) {
        if (len <= 0) {
            tvTextCount.setVisibility(View.GONE);
            setSendButtonState(SendButtonState.DISABLE);
        } else if (len > 0 && len < 490) {
            tvTextCount.setVisibility(View.GONE);
            setSendButtonState(SendButtonState.ENABLE);
        } else if (len >= 490 && len <= 500) {
            tvTextCount.setVisibility(View.VISIBLE);
            tvTextCount.setEnabled(true);
            tvTextCount.setText(String.valueOf(500 - len));
            setSendButtonState(SendButtonState.ENABLE);
        } else if (len > 500) {
            tvTextCount.setVisibility(View.VISIBLE);
            tvTextCount.setEnabled(false);
            tvTextCount.setText(String.valueOf(len - 500));
            setSendButtonState(SendButtonState.DISABLE);
        }
    }


    /**
     * @param state 发送按钮状态
     */
    private void setSendButtonState(SendButtonState state) {
        switch (state) {
            case ENABLE:
                tvSend.setText(R.string.comment_send);
                tvSend.setEnabled(true);
                break;
            case DISABLE:
                tvSend.setText(R.string.comment_send);
                tvSend.setEnabled(false);
                break;
            case SENDING:
                tvSend.setText(R.string.comment_sending);
                tvSend.setEnabled(false);
                break;
        }

    }


    /**
     * 发送按钮状态
     */
    enum SendButtonState {
        ENABLE, DISABLE, SENDING
    }


    private void checkSensitive() {

        //隐藏键盘
        UIUtils.showSoftInput(edtActivityComment, false);
        setSendButtonState(SendButtonState.SENDING);

        String strContent = edtActivityComment.getText().toString();
        //	发送敏感词检测
//        doPost(this,new CheckSensitiveWordPostReq(strContent), checkSensitiveWordListener);
        //零时调试
        sendComment();

    }


    private void sendComment() {
        String strComment = edtActivityComment.getText().toString();

        List<NodesCommentPost.ContentBean> arrayContent = new ArrayList<>();


        String[] contents = strComment.split("\\n");

        for (String content : contents) {
            NodesCommentPost.ContentBean textContent = new NodesCommentPost.ContentBean();
            textContent.setType(Constant.TXET);
            textContent.setContent(content);
            arrayContent.add(textContent);
        }
        if (arrayContent.size() > 0) {
            mPublishCommentParam.setContent(arrayContent);
            mPublishCommentParam.setCancelSign(mContext);
            ApiReq.doPost(mPublishCommentParam, sendCommentListener);
        } else {
            TipUtil.alert(mContext, "内容不能为空");
        }

    }


    private IOnResponseListener<Response> checkSensitiveWordListener = new IOnResponseListener<Response>() {
        @Override
        public void onSuccess(Response response) {
            sendComment();
        }

        @Override
        public void onError(Response response) {
            TipUtil.alert(AddCommentActivity.this, response.getMsg());

            setSendButtonState(SendButtonState.ENABLE);
        }
    };


    private IOnResponseListener<Response<EventCommentResp>> sendCommentListener = new IOnResponseListener<Response<EventCommentResp>>() {
        @Override
        public void onSuccess(Response<EventCommentResp> response) {

            if (null == edtActivityComment) {
                return;
            }
            setCommentResult(response.getData());
        }

        @Override
        public void onError(Response response) {

            TipUtil.alert(AddCommentActivity.this, response.getMsg());

            setSendButtonState(SendButtonState.ENABLE);
        }
    };

    private void setCommentResult(EventCommentResp publishCommentRepo) {

        EventCommentListResp.ItemsBean lastListData = new EventCommentListResp.ItemsBean();

        lastListData.setComment_id(publishCommentRepo.getComment_id());
        lastListData.setCreated_at(System.currentTimeMillis());
        lastListData.setContent(mPublishCommentParam.getContent());

        EventCommentListResp.ItemsBean.ReplyToBean replyToBean = new EventCommentListResp.ItemsBean.ReplyToBean();
        replyToBean.setComment_id(mPublishCommentParam.getRefrence_id());
        replyToBean.setNick_name(reply_to_nick_name);
        replyToBean.setUser_id(mPublishCommentParam.getReply_to());

        lastListData.setReply_to(replyToBean);

        EventCommentListResp.ItemsBean.UserBean userBean = new EventCommentListResp.ItemsBean.UserBean();
        userBean.setNick_name(UserInfoUtil.getInstance().getCurrentUser().getUser().getNick_name());
        userBean.setUser_id(UserInfoUtil.getInstance().getUserId());
        userBean.setAvatar(UserInfoUtil.getInstance().getCurrentUser().getUser().getAvatar());

        lastListData.setUser(userBean);

        final String result = new Gson().toJson(lastListData);

        StringUtil.showSnack(AddCommentActivity.this, "已发布");

        edtActivityComment.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (null == edtActivityComment) {
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(INTENT_EXTRA_NAME_COMMENT_INFO, result);
                setResult(RESULT_CODE_TO_COMMENT_FINISH, intent);
                AddCommentActivity.this.finish();
            }
        }, 1000);


    }


}
