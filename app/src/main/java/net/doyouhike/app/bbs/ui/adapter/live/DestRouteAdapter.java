package net.doyouhike.app.bbs.ui.adapter.live;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.DestRouteItem;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetDestByKeywordResp;
import net.doyouhike.app.bbs.biz.openapi.response.routes.DestsRouteChildResp;
import net.doyouhike.app.bbs.util.ActivityRouter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通过关键字获取目的地线路列表
 * Created by zengjiang on 16/5/31.
 */
public class DestRouteAdapter extends CommonAdapter<DestsRouteChildResp.ChildDestsBean> {

    public final String TYPE_NODE_TYPE = "node_type";
    public final String TYPE_POI = "poi";
    public final String TYPE_CITY = "city";

    public DestRouteAdapter(Context context, List<DestsRouteChildResp.ChildDestsBean> datas) {
        super(context, datas, R.layout.item_dest_route);
    }


    @Override
    public void convert(ViewHolder holder, final DestsRouteChildResp.ChildDestsBean item) {
        ImageView ivTypeLabel = holder.getView(R.id.iv_type_label);

        ivTypeLabel.setVisibility(View.VISIBLE);


        //设置图标
        if (item.getNode_cat().equals(TYPE_NODE_TYPE)) {
            ivTypeLabel.setImageResource(R.drawable.activity_map_disable);
        }else if (item.getNode_cat().equals(TYPE_CITY)) {
            ivTypeLabel.setImageResource(R.drawable.activity_map_disable);
        } else if (item.getNode_cat().equals(TYPE_POI)) {
            ivTypeLabel.setImageResource(R.drawable.activity_map2_disable);
        } else {
            ivTypeLabel.setImageResource(R.drawable.activity_map_disable);
        }

        //设置目的地名称
        holder.setText(R.id.tv_dest, item.getNode_name());
        //item 点击事件
        holder.setOnClickListener(R.id.rl_item_dest_route_parent, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (item.getNode_cat().equals(TYPE_POI)) {
//                    ActivityRouter.openRoadDetailActivity(mContext, item.getNode_slug());
//                } else {
//                    ActivityRouter.openDesAndRoadListActivity(mContext, item.getNode_slug());
//                }
            }
        });

    }

}
