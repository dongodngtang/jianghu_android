package net.doyouhike.app.bbs.ui.adapter.action;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;

import java.util.List;

/**
 * 功能：
 *
 * @author：曾江 日期：16-4-6.
 */
public class ActionAdapter extends BaseActionAdapter {


    private static final int VIEW_TYPE_HEAD = 0;
    private static final int VIEW_TYPE_CONTENT = 1;

    public ActionAdapter(Context mainActivity, List<EventsListResp.ItemsBean> list) {
        super(mainActivity, list);
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
            // 活动头部部分
            if (null == convertView) {
                convertView = LayoutInflater.
                        from(mContext).inflate(R.layout.item_action_head, parent, false);
            }


            return convertView;
        }

        return super.getView(position, convertView, parent);
    }
}
