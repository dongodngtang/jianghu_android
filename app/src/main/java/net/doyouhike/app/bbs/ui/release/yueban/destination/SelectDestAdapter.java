package net.doyouhike.app.bbs.ui.release.yueban.destination;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetDestByKeywordResp;
import net.doyouhike.app.bbs.util.UIUtils;

import java.util.List;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-3-23
 */
public class SelectDestAdapter extends CommonAdapter<GetDestByKeywordResp> {

    public final static String TYPE_CITY = "city";
    public final static String TYPE_ROUTE = "route";
    public final static String TYPE_NULL = "null";
    private String keyword;

    public SelectDestAdapter(Context context, List<GetDestByKeywordResp> datas) {
        super(context, datas, R.layout.item_select_dest);
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    @Override
    public void convert(ViewHolder holder, GetDestByKeywordResp getDestByKeywordResp) {
        ImageView ivTypeLabel = holder.getView(R.id.iv_type_label);
        TextView tvDest = holder.getView(R.id.tv_dest);
        TextView tv_no_data = holder.getView(R.id.tv_no_data);
        View v_item_select_dest_divider = holder.getView(R.id.v_item_select_dest_divider);
        UIUtils.showView(v_item_select_dest_divider,holder.getmPosition() != getCount() -1);

        tv_no_data.setVisibility(View.VISIBLE);
        tvDest.setVisibility(View.VISIBLE);
        ivTypeLabel.setVisibility(View.VISIBLE);
        if (getDestByKeywordResp.getNode_cat().equals(TYPE_CITY)) {
            ivTypeLabel.setImageResource(R.drawable.road_cat_city);
        } else if (getDestByKeywordResp.getNode_cat().equals(TYPE_ROUTE)) {
            ivTypeLabel.setImageResource(R.drawable.road_cat_route_dark);
        } else if (getDestByKeywordResp.getNode_cat().equals(TYPE_NULL)) {
            ivTypeLabel.setVisibility(View.GONE);
        }

        if (getDestByKeywordResp.getNode_cat().equals(TYPE_NULL)){
            tvDest.setVisibility(View.GONE);
            StringBuilder sb = new StringBuilder();
            sb.append("暂未收录，手动添加“");
            sb.append(getDestByKeywordResp.getNode_name());
            sb.append("”");
            UIUtils.setKeyword(tv_no_data, sb.toString(),keyword);
        }
        else{
            tv_no_data.setVisibility(View.GONE);
            UIUtils.setKeyword(tvDest, getDestByKeywordResp.getNode_name(),keyword);

        }


    }


}
