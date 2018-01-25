package net.doyouhike.app.bbs.ui.adapter.road;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.openapi.response.routes.DestsRoutesResp;
import net.doyouhike.app.bbs.ui.activity.road.RoadDetailActivity;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.List;

/**
 * 功能：线路列表
 * 作者：朱林
 * 日期：2016.5.6.
 */
public class RoadListAdapter extends CommonAdapter<DestsRoutesResp.ItemsBean> implements View.OnClickListener {

    private Context context;

    public RoadListAdapter(Context mainActivity, List<DestsRoutesResp.ItemsBean> list) {
        super(mainActivity, list, R.layout.item_road_list);
        this.context = mainActivity;

    }


    @Override
    public void convert(ViewHolder holder, DestsRoutesResp.ItemsBean roadListInfo) {

        RelativeLayout relativeLayout = holder.getView(R.id.rl_road_list_item);
        relativeLayout.setOnClickListener(this);
        ImageView head = holder.getView(R.id.iv_road_list_item_bg);
        String imageUrl = roadListInfo.getBanner().getMain_photo();
        if(roadListInfo.getBanner() != null &&
                StringUtil.isNotEmpty(imageUrl)){
            GlideHelper.displayNetRoadListImage(context,head,R.color.default_image_background, imageUrl);
        }
        //线路名称
        TextView roadName =  holder.getView(R.id.tv_roadList_item_title);
        roadName.setText(roadListInfo.getRoute_name());
        //线路类型
        TextView roadType = holder.getView(R.id.tv_roadList_type);
        roadType.setText(roadListInfo.getRoute_type_name());

        View convertView = holder.getConvertView();
        holder.setOnClickListener(convertView.getId(), this);
        convertView.setTag(R.id.rl_road_list_item, roadListInfo.getNode_slug());
    }


    @Override
    public void onClick(View v) {
        String road_sulg = (String) v.getTag(R.id.rl_road_list_item);
        if(StringUtil.isNotEmpty(road_sulg)){
            Intent intent = new Intent(context, RoadDetailActivity.class);
            intent.putExtra("road_slug",road_sulg);
            context.startActivity(intent);
        }

    }
}
