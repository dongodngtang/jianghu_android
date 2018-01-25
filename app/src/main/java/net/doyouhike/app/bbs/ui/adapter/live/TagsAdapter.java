package net.doyouhike.app.bbs.ui.adapter.live;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.TagInfo;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.library.ui.utils.BaseBitmapUtil;

import java.util.List;

public class TagsAdapter extends BaseAdapter {

    private List<TagInfo> tags;
    private Context context;
    private onClickTagListener listener;
    private boolean[] isCheckeds; // 判断是否被选中
    private int checkCount = 0; // 被选中的个数

    public TagsAdapter(Context context, List<TagInfo> tags,
                       onClickTagListener listener, boolean[] isCheckeds) {
        this.tags = tags;
        this.context = context;
        this.listener = listener;
        initIsCheckeds(isCheckeds, tags);
    }

    private void initIsCheckeds(boolean[] isCheckeds, List<TagInfo> tags) {
        int countTags = tags == null ? 0 : tags.size();
        int countCheck = isCheckeds == null ? 0 : isCheckeds.length;
        if (countCheck == 0 || countCheck < countTags) {
            this.isCheckeds = new boolean[countTags];
            if (countCheck < countTags) {
                for (int i = 0; i < countCheck; i++) {
                    this.isCheckeds[i] = isCheckeds[i];
                }
            }
        } else {
            this.isCheckeds = isCheckeds;
        }
    }

    @Override
    public int getCount() {
        if (tags != null) {
            return tags.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (tags != null) {
            return tags.size();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (tags != null && tags.get(position) != null) {
            TagInfo tag = tags.get(position);
            if (convertView == null) {
                convertView = createTvTag(tag, position);
            } else {
                convertView = setTvTagText((TextView) convertView,
                        tag.getDesc(), tag.getId(), position);
            }
        }

        return convertView;
    }

    private TextView createTvTag(TagInfo tag, int position) {
        TextView tvTag = new TextView(context);
        if (tag != null) {
            String desc = tag.getDesc();
            String id = tag.getId();

            AbsListView.LayoutParams params = getParams(tvTag);
            tvTag.setLayoutParams(params);
            setTvTagText(tvTag, desc, id, position);
            setTvTagonClick(tvTag, tag, position);
            if (isCheckeds[position]) {
                tvTag.setBackgroundDrawable(BaseBitmapUtil.getDrawable(context,R.drawable.shape_tag_bg_selected));
                checkCount++;
            }
        }
        return tvTag;
    }

    private void setTvTagonClick(TextView tvTag, final TagInfo tag,
                                 final int position) {
        tvTag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (isCheckeds != null && position < isCheckeds.length) {
                        if (isCheckeds[position]) {
                            if (checkCount > 0) {
                                checkCount--;
                                v.setBackgroundDrawable(BaseBitmapUtil.getDrawable(context,R.drawable.shape_tag_bg_defaul));
                                isCheckeds[position] = !isCheckeds[position];
                            }
                        } else {
                            if (checkCount < 3) {
                                checkCount++;
                                v.setBackgroundDrawable(BaseBitmapUtil.getDrawable(context,R.drawable.shape_tag_bg_selected));
                                isCheckeds[position] = !isCheckeds[position];
                            }
                        }
                    }
                    listener.onclickTag(tag);
                }
            }
        });

    }

    private TextView setTvTagText(TextView tvTag, String desc, String id, int position) {
        if (tvTag != null && desc != null && StrUtils.hasContent(id)) {
            tvTag.setText(desc);
            tvTag.setBackgroundDrawable(BaseBitmapUtil.getDrawable(context,R.drawable.shape_tag_bg_defaul));
            tvTag.setTextColor(0xFFFFFFFF);
            tvTag.setGravity(Gravity.CENTER);
            if (desc.length() <= 3) {
                tvTag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17); // TODO
            } else if (desc.length() == 4) {
                tvTag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // TODO
            } else {
                tvTag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14); // TODO
            }
            if (isCheckeds[position]) {
                tvTag.callOnClick();
            }
        }
        return tvTag;
    }

    private LayoutParams getParams(TextView tvTag) {
        AbsListView.LayoutParams params = (AbsListView.LayoutParams) tvTag
                .getLayoutParams();
        if (params == null) {
            params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
        }
        params.width = getTagWidth();
        params.height = LayoutParams.WRAP_CONTENT;
        return params;
    }

    private int getTagWidth() {
        int spacing = UIUtils.getIntFromDimens(context, R.dimen.dimen_4dp) * 3;
        int side = UIUtils.getIntFromDimens(context, R.dimen.dimen_17dp) * 2;
        int validWidth = UIUtils.getValidWidth(context);
        return (validWidth - spacing - side) / 4;
    }

    public interface onClickTagListener {
        public void onclickTag(TagInfo tag);
    }
}
