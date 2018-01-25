package net.doyouhike.app.bbs.ui.release.tags;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.biz.openapi.response.users.MinilogTagResp;
import net.doyouhike.app.library.ui.utils.BaseBitmapUtil;

import java.util.List;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-1-27
 */
public class TagsAdapter extends CommonAdapter<BaseTag> {
    private ReleaseTagActivity mReleaseTagActivity;

    public TagsAdapter(Context context, List<BaseTag> datas) {
        super(context, datas, R.layout.item_home_tag_content);
        mReleaseTagActivity = (ReleaseTagActivity) context;
    }


    @Override
    public void convert(ViewHolder holder, final BaseTag tagsEntity) {
        final TextView title = holder.getView(R.id.tv_item_tag_content);
        holder.setText(R.id.tv_item_tag_content, tagsEntity.getTag_name());
        if (tagsEntity.isSelected()) {
            title.setTextColor(mContext.getResources().getColor(R.color.color_embellish));
            title.setBackgroundDrawable(BaseBitmapUtil.getDrawable(mContext, R.drawable.bg_home_pop_btn_bule));

            if (!isContainById(tagsEntity.getTag_id())) {
                mReleaseTagActivity.tagInfos.add(tagsEntity);
            }
        }
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelected(tagsEntity, title);
                mReleaseTagActivity.tvComplete();
            }
        });
    }

    private void isSelected(BaseTag tagsEntity, TextView title) {
        if (tagsEntity.isSelected()) {

            title.setTextColor(mContext.getResources().getColor(R.color.tag_txt_default));
            title.setBackgroundDrawable(BaseBitmapUtil.getDrawable(mContext, R.drawable.bg_home_tag_content));
            tagsEntity.setSelected(false);
            removeActivityTag(tagsEntity.getTag_id());
        } else {
            selected(tagsEntity, title);

        }
    }

    private Boolean isContainById(String tagId) {
        for (BaseTag tag : mReleaseTagActivity.tagInfos) {
            if (tagId.equals(tag.getTag_id())) {
                return true;
            }
        }
        return false;
    }

    private void removeActivityTag(String tagId) {
        for (BaseTag tag : mReleaseTagActivity.tagInfos) {
            if (tagId.equals(tag.getTag_id())) {
                mReleaseTagActivity.tagInfos.remove(tag);
                break;
            }
        }


    }

    private void selected(BaseTag tagsEntity, TextView title) {
        if (mReleaseTagActivity.tagInfos.size() < 3) {
            title.setTextColor(mContext.getResources().getColor(R.color.color_embellish));
            title.setBackgroundDrawable(BaseBitmapUtil.getDrawable(mContext, R.drawable.bg_home_pop_btn_bule));

            tagsEntity.setSelected(true);

            if (!isContainById(tagsEntity.getTag_id())) {
                mReleaseTagActivity.tagInfos.add(tagsEntity);

                mReleaseTagActivity.tvComplete();
            }

        } else {
            mReleaseTagActivity.tipExceeding();
        }
    }
}
