package net.doyouhike.app.bbs.ui.widget.action;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.im.GroupMsgDetail;
import net.doyouhike.app.bbs.biz.event.im.SendGroupMsgEvent;
import net.doyouhike.app.bbs.biz.event.live.CheckoutMainPageEvent;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.ui.util.TipUtil;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 群发消息对话框
 * Created by zengjiang on 16/7/26.
 */

public class GroupMsgDialog extends AlertDialog {

    private int mCount;

    /**
     * 群发内容输入
     */
    private EditText edtActionManageGroupMsgContent;

    /**
     * 确认
     */
    private TextView tvSure;

    List<String> users;

    /**
     * @param count   确认成员数量
     * @param users   要发生消息的成员列表
     * @param context
     */
    public GroupMsgDialog(int count, List<String> users, Context context) {
        super(context);
        mCount = count;
        this.users = users;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View parent = LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_action_manage_group_msg, null);
        setContentView(parent);
        setCanceledOnTouchOutside(false);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
/**
 Dialog的width和height默认值为WRAP_CONTENT，正是因为如此，当屏幕中有足够的空间时，Dialog是不会被压缩的
 但是设置width和height为MATCH_PARENT的代价是无法设置gravity的值，这就无法调整Dialog中内容的位置，Dialog的内容会显示在屏幕左上角位置不过可以通过Padding来调节Dialog内容的位置。
 **/
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        getWindow().setBackgroundDrawableResource(R.color.transparent);

        init(parent);


    }

    private void init(View parent) {

        edtActionManageGroupMsgContent = (EditText) parent.findViewById(R.id.edt_action_manage_group_msg_content);

        parent.findViewById(R.id.tv_action_manage_group_msg_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                dismiss();
            }
        });


        tvSure = (TextView) parent.findViewById(R.id.tv_action_manage_group_msg_done);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定
                sure();

                dismiss();

            }
        });

        TextView tvTitle = (TextView) parent.findViewById(R.id.tv_action_manage_group_msg_title);
        tvTitle.setText(String.format("已确认(%d)", mCount));


        edtActionManageGroupMsgContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 500 || s.length() == 0) {
                    tvSure.setEnabled(false);
                } else {
                    tvSure.setEnabled(true);
                }
            }
        });

    }

    /**
     * 点发送按钮
     */
    private void sure() {
        String content = edtActionManageGroupMsgContent.getText().toString();

        GroupMsgDetail msgDetail= GroupMsgDetail.getNewMsg(content,users);
        SendGroupMsgEvent event=new SendGroupMsgEvent(msgDetail);

        EventBus.getDefault().post(event);


        TipUtil.alert(getContext(), "已发送", "可前往消息查看发送状态", new String[]{"关闭", "查看"}, new OnBtnClickL() {
            @Override
            public void onBtnClick() {

            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                //查看

                EventBus.getDefault().post(new CheckoutMainPageEvent(MainActivity.PAGE_MESSAGE));


            }
        });


    }
}
