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
import net.doyouhike.app.bbs.biz.entity.dynamic.DesAndRoadInfo;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.library.ui.utils.BaseBitmapUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能：目的地  线路列表 Adapter
 *
 * @author：朱林 日期：16-5-30.
 */
public class DesAndRoadListAdapter extends CommonAdapter<DesAndRoadInfo.HotChildDestsBean> {

    private String keyword = "";


    public DesAndRoadListAdapter(Context context, List<DesAndRoadInfo.HotChildDestsBean> datas) {
        super(context, datas, R.layout.item_des_and_road_list);
    }

    public void setKeyword(String keyword) {

        if (null == keyword) {
            keyword = "";
        }

        this.keyword = keyword;
    }


    @Override
    public void convert(final ViewHolder holder, final DesAndRoadInfo.HotChildDestsBean hotChildrenCity) {
        TextView tvContent = holder.getView(R.id.tv_item_road_search_content);
        ImageView imageView = holder.getView(R.id.iv_des_and_road_item);
        View line = holder.getView(R.id.item_des_and_road_list_line);
        UIUtils.showView(line, getCount() - 1 != holder.getmPosition());

        tvContent.setText(hotChildrenCity.getNode_name());
        if (hotChildrenCity.getNode_cat().equals(Content.ROUTE)) {
            imageView.setImageDrawable(BaseBitmapUtil.getDrawableFromRes(mContext, R.drawable.activity_map2_disable));
        } else
            imageView.setImageDrawable(BaseBitmapUtil.getDrawableFromRes(mContext, R.drawable.activity_map_disable));
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hotChildrenCity.getNode_cat().equals(Content.ROUTE)) {
                    ActivityRouter.openRoadDetailActivity(mContext, hotChildrenCity.getNode_slug());

                } else {

                    ActivityRouter.openDesAndRoadListActivity(mContext, hotChildrenCity.getNode_slug());
                }

            }
        });
    }


    /**
     * 填充内容,关键词高亮
     *
     * @param textView 填充内容的textView
     * @param content  内容
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
