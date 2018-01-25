package net.doyouhike.app.bbs.ui.adapter.me;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.model.request.get.UserEventsReq;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;
import net.doyouhike.app.bbs.ui.adapter.action.BaseActionAdapter;

import java.util.List;

/**
 * 功能：活动页适配器
 * 作者：曾江
 * 日期：16-1-14.
 */
public class MeEventAdapter extends BaseActionAdapter {


    private static final int VIEW_TYPE_HEAD = 0;
    private static final int VIEW_TYPE_CONTENT = 1;

    private int actionType = UserEventsReq.TYPE_QUERY_ALL;

    public MeEventAdapter(Context context, List<EventsListResp.ItemsBean> datas) {
        super(context, datas);
    }

    @Override
    public EventsListResp.ItemsBean getItem(int position) {

        if (position == 0) {
            return null;
        }

        return mDatas.get(position - 1);
    }

    @Override
    public int getCount() {
        return mDatas.size() + 1;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEAD : VIEW_TYPE_CONTENT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (getItemViewType(position) == VIEW_TYPE_HEAD) {
            // 活动筛选类型部分
            if (null == convertView) {
                convertView = LayoutInflater.
                        from(mContext).inflate(R.layout.item_action_head, parent, false);
            }
            TextView typeTv = (TextView) convertView.findViewById(R.id.tv_item_user_event_head);

            switch (actionType) {
                case UserEventsReq.TYPE_QUERY_ALL:
                    //全部
                    typeTv.setVisibility(View.GONE);
                    typeTv.setText(mContext.getResources().getString(R.string.all));
                    break;
                case UserEventsReq.TYPE_QUERY_SPINSOR:
                    typeTv.setVisibility(View.VISIBLE);
                    typeTv.setText("发起");
                    break;
                case UserEventsReq.TYPE_QUERY_JOIN:
                    typeTv.setVisibility(View.VISIBLE);
                    typeTv.setText("参与");
                    break;

                default:
                    break;
            }

            return convertView;
        }

        return super.getView(position, convertView, parent);
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

}
