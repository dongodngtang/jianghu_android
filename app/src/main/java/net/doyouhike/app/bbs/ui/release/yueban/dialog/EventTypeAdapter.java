package net.doyouhike.app.bbs.ui.release.yueban.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.library.ui.utils.BaseBitmapUtil;

import java.util.List;

/**
 * 作者:luochangdong on 16/4/30 17:00
 * 描述:
 */
public class EventTypeAdapter extends CommonAdapter<BaseTag> {


    TagDialog mTagDialog;
    TextView tvTagTip;


    public EventTypeAdapter(Context context, List<BaseTag> datas, TagDialog tagDialog) {
        super(context, datas, R.layout.item_home_tag_content);
        mTagDialog = tagDialog;
    }

    public void setTvTagTip(TextView tvTagTip) {
        this.tvTagTip = tvTagTip;
    }

    @Override
    public void convert(ViewHolder holder, final BaseTag typeSelectEntity) {
        holder.setText(R.id.tv_item_tag_content, typeSelectEntity.getTag_name());
        final TextView title = holder.getView(R.id.tv_item_tag_content);
        if (typeSelectEntity.isSelected()) {
            title.setTextColor(mContext.getResources().getColor(R.color.color_embellish));
            title.setBackgroundDrawable(BaseBitmapUtil.getDrawable(mContext, R.drawable.bg_home_pop_btn_bule));
            if (!isContainById(typeSelectEntity.getTag_id())) {

                mTagDialog.tagInfos.add(typeSelectEntity);
            }
        }
        if (tvTagTip != null)
            UIUtils.showView(tvTagTip, mTagDialog.tagInfos.size() >= 3);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelected(typeSelectEntity, title);
                if (tvTagTip != null)
                    UIUtils.showView(tvTagTip, mTagDialog.tagInfos.size() >= 3);
            }
        });

    }

    private void isSelected(BaseTag typeSelectEntity, TextView title) {

        if (typeSelectEntity.isSelected()) {
            title.setTextColor(mContext.getResources().getColor(R.color.tag_txt_default));
            title.setBackgroundDrawable(BaseBitmapUtil.getDrawable(mContext, R.drawable.bg_home_tag_content));
            typeSelectEntity.setSelected(false);
            removeById(typeSelectEntity.getTag_id());


        } else {
            selected(typeSelectEntity, title);

        }
    }

    private void selected(BaseTag typeSelectEntity, TextView title) {

        if (mTagDialog.tagInfos.size() < 3) {
            title.setTextColor(mContext.getResources().getColor(R.color.color_embellish));
            title.setBackgroundDrawable(BaseBitmapUtil.getDrawable(mContext, R.drawable.bg_home_pop_btn_bule));

            typeSelectEntity.setSelected(true);
            mTagDialog.tagInfos.add(typeSelectEntity);

        }

    }

    private Boolean isContainById(String id) {
        for (BaseTag item : mTagDialog.tagInfos) {
            if (id.equals(item.getTag_id())) {
                return true;
            }
        }
        return false;
    }


    private void removeById(String tagId) {
        BaseTag entity = null;
        for (BaseTag item : mTagDialog.tagInfos) {
            if (tagId.equals(item.getTag_id())) {
                entity = item;
            }
        }
        if (entity != null)
            mTagDialog.tagInfos.remove(entity);

    }


}
