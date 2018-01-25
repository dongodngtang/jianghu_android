package net.doyouhike.app.bbs.ui.adapter.road;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.road.RoadListInfo;
import net.doyouhike.app.bbs.biz.openapi.response.routes.DestsRoutesResp;
import net.doyouhike.app.bbs.ui.activity.road.RoadDetailActivity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能：路线搜索适配器
 *
 * @author：曾江 日期：16-5-10.
 */
public class RoadSearchAdapter extends CommonAdapter<DestsRoutesResp.ItemsBean> {

    private String keyword = "";
    private static final int VIEW_TYPE_END = 0;
    private static final int VIEW_TYPE_CONTENT = 1;


    public RoadSearchAdapter(Context context, List<DestsRoutesResp.ItemsBean> datas) {
        super(context, datas, R.layout.item_road_search);
    }

    public void setKeyword(String keyword) {

        if (null == keyword) {
            keyword = "";
        }

        this.keyword = keyword;
    }




    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == mDatas.size()-1 ? VIEW_TYPE_END : VIEW_TYPE_CONTENT;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == VIEW_TYPE_END) {
            //尾部内容
            ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
                    R.layout.item_road_search_end, position);
            convert(holder, getItem(position));
            return holder.getConvertView();
        }else {
            return super.getView(position, convertView, parent);
        }


    }

    @Override
    public void convert(ViewHolder holder, final DestsRoutesResp.ItemsBean roadListInfo) {
        TextView tvContent = holder.getView(R.id.tv_item_road_search_content);
        setContent(tvContent, roadListInfo.getRoute_name());

        holder.setOnClickListener(R.id.rl_item_road_search_content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(v.getContext(), RoadDetailActivity.class);
                itent.putExtra("road_slug",roadListInfo.getNode_slug());
                v.getContext().startActivity(itent);
            }
        });
    }


    /**
     * 填充内容,关键词高亮
     * @param textView 填充内容的textView
     * @param content 内容
     */
    private void setContent(TextView textView, String content) {
        SpannableString s = new SpannableString(content);


        Pattern p = Pattern.compile(keyword);

        Matcher m = p.matcher(s);

        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.orange_bg)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setText(s);
    }
}
