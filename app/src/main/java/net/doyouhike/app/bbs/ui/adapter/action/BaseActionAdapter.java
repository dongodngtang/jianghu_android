package net.doyouhike.app.bbs.ui.adapter.action;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventHelper;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.library.ui.utils.DateUtils;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.List;

/**
 * 功能：活动列表适配器
 * 作者：曾江
 * 日期：15-12-28.
 */
public class BaseActionAdapter extends CommonAdapter<EventsListResp.ItemsBean> implements View.OnClickListener {

    private final String format = DateUtils.FORMAT;// 时间的格式
    private String startTime;

    public BaseActionAdapter(Context mainActivity, List<EventsListResp.ItemsBean> list) {
        super(mainActivity, list, R.layout.item_action_main);
    }


    @Override
    public void convert(ViewHolder holder, EventsListResp.ItemsBean actionInfo) {

        ImageView head = holder.getView(R.id.action_gouper_head);
        ImageView actionPic = holder.getView(R.id.iv_action_pic);
        TextView ivStatus = holder.getView(R.id.iv_action_type);
        TextView time = holder.getView(R.id.tv_action_during_time);
        TextView tvTitle = holder.getView(R.id.tv_action_title);
        if (actionInfo.getTitle() != null)
            tvTitle.setText(actionInfo.getTitle());
        if (actionInfo.getLeader_user().getNick_name() != null)
            holder.setText(R.id.tv_action_grouper_name, actionInfo.getLeader_user().getNick_name());


        if (actionInfo.getBegin_date() != null)
            startTime = DateUtils.getStrDate(
                    Long.parseLong(actionInfo.getBegin_date()) * 1000L,
                    format);
        if (actionInfo.getDays() != null)
            time.setText(startTime + "    共" + actionInfo.getDays() + "天");

        String avatarUrl = Constant.PHOTO_DOMAIN_PATH + actionInfo.getLeader_user().getAvatar();
        GlideHelper.displayHeader(mContext, head, avatarUrl);
        String imageUrl = SharedPreferencesManager.getPhotoDomainPath()
                + actionInfo.getBanner().getPhoto_path()
                + "." + actionInfo.getBanner().getPhoto_ext();
        GlideHelper.displayNetActionImage(mContext, actionPic, imageUrl);
        if (actionInfo.getEvent_states() != null) {
            if (actionInfo.getEvent_states().equals(Constant.EVENT_RECRUTING) || actionInfo.getEvent_states().equals(Constant.EVENT_FULL)) {// 召集中

                ivStatus.setTextColor(mContext.getResources().getColor(R.color.orange_bg));
            } else {// 已结束
                ivStatus.setTextColor(mContext.getResources().getColor(R.color.action_progess_status));
            }
            EventHelper.getInstance().setEventStatus(ivStatus, actionInfo.getEvent_states());
        }

        View convertView = holder.getConvertView();
        holder.setOnClickListener(convertView.getId(), this);
        convertView.setTag(R.id.tag_me_event_item, actionInfo.getNode_id());
    }


    @Override
    public void onClick(View v) {

        String nodeId = (String) v.getTag(R.id.tag_me_event_item);

        if (TextUtils.isEmpty(nodeId)) {
            return;
        }

        Intent intent = new Intent(mContext, net.doyouhike.app.bbs.ui.activity.action.ActionDetailActivity.class);
        intent.putExtra("nodeId", nodeId);
        mContext.startActivity(intent);
    }
}
