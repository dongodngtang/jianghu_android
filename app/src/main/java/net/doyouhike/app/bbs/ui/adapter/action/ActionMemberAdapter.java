package net.doyouhike.app.bbs.ui.adapter.action;

import android.content.Context;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventMembersResp;
import net.doyouhike.app.bbs.ui.util.FollowButtonHelper;
import net.doyouhike.app.bbs.util.StringUtil;

import java.util.List;

/**
 * Created by zengjiang on 16/7/19.
 */
public class ActionMemberAdapter extends BaseActionMemberAdapter {

    private final String[] roles;
    boolean isConfirmed = true;

    public ActionMemberAdapter(Context context, List<EventMembersResp.ItemsBean> datas, boolean isConfirmed) {
        super(context, datas);

        this.isConfirmed = isConfirmed;

        roles = new String[]{"", "发起人", "协作", "财务", "成员", "留守"};

        if (isConfirmed){
            setFormat("已确认%d人");
        }
    }

    @Override
    public void convert(ViewHolder holder, EventMembersResp.ItemsBean members) {
        super.convert(holder, members);


        holder.setText(R.id.tv_action_manager_member, members.getMemo());

        TextView operate = holder.getView(R.id.btn_action_manager_sure);
        TextView info = holder.getView(R.id.tv_action_manager_member);

        info.setText(members.getMemo());

        FollowButtonHelper.setTextState(
                operate,
                members.getIsFollow(),
                members.getUser().getUser_id());


        if (isConfirmed) {
            int roleIndex = members.getRole();
            info.setText(roles[roleIndex]);
        } else {
            info.setText(members.getMemo());
        }

    }
}
