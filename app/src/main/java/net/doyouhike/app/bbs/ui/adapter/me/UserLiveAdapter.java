package net.doyouhike.app.bbs.ui.adapter.me;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.ui.adapter.NodeTimelineAdapter;
import net.doyouhike.app.bbs.ui.home.NewLiveAdapter;

import java.util.List;

/**
 * Created by zengjiang on 16/5/30.
 */
public class UserLiveAdapter extends NodeTimelineAdapter{

    private static final int VIEW_TYPE_HEAD = 0;
    private static final int VIEW_TYPE_CONTENT = 1;

    public UserLiveAdapter(Context context, List<NodeTimeline.ItemsBean> datas) {
        super(context, datas);
    }

    @Override
    public NodeTimeline.ItemsBean getItem(int position) {

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
