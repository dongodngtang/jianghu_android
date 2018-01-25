package net.doyouhike.app.bbs.ui.release.map;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.LocationInfo;
import net.doyouhike.app.bbs.util.StringUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者:luochangdong on 16/5/27 10:47
 * 描述:
 */
public class SearchInMapAdapter extends CommonAdapter<LocationInfo> {

    /**
     * 搜索关键字
     */
    private String keyword;

    public SearchInMapAdapter(Context context, List<LocationInfo> datas) {
        super(context, datas, R.layout.item_search_in_map);
    }

    @Override
    public void convert(ViewHolder holder, LocationInfo locationInfo) {
        TextView title = holder.getView(R.id.item_search_in_map_title);
        TextView content = holder.getView(R.id.item_search_in_map_content);
        setText(title, locationInfo.getLocation());
        setText(content, locationInfo.getSnippet());

    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    /**
     * 关键字颜色处理
     * @param tv
     * @param content
     */
    private void setText(TextView tv, String content) {
        if(StringUtil.isNotEmpty(content)){

            SpannableString s = new SpannableString(content);
            if(StringUtil.isNotEmpty(keyword)){
                Pattern p = Pattern.compile(keyword);
                Matcher m = p.matcher(s);
                while (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    s.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.orange_bg)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            tv.setVisibility(View.VISIBLE);
            tv.setText(s);
        }else{
            tv.setVisibility(View.GONE);
        }

    }
}
