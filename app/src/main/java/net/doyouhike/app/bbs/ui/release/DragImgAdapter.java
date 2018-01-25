package net.doyouhike.app.bbs.ui.release;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventDetailResp;
import net.doyouhike.app.bbs.util.draghelper.ItemTouchHelperAdapter;
import net.doyouhike.app.bbs.util.draghelper.ItemTouchHelperViewHolder;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.Collections;
import java.util.List;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-2-16
 */
public class DragImgAdapter extends RecyclerView.Adapter<DragImgAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {
    NewEditLiveActivity baseActivity;
    List<EventDetailResp.ContentBean> mDatas;


    public DragImgAdapter(NewEditLiveActivity activity, List<EventDetailResp.ContentBean> datas) {
        baseActivity = activity;
        mDatas = datas;

    }

    public List<EventDetailResp.ContentBean> getmDatas() {
        return mDatas;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (mDatas.get(toPosition).getWhole_photo_path().equals(PresenterEditLive.ADD_IMG)) {

            return false;
        } else {
            EventDetailResp.ContentBean item = mDatas.get(fromPosition);
            mDatas.remove(fromPosition);
            mDatas.add(toPosition , item);
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

    }

    @Override
    public void onItemDismiss(int position) {
        if (!mDatas.get(position).getWhole_photo_path().equals(PresenterEditLive.ADD_IMG)) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }

    }

    @Override
    public String getUrl(int position) {
        return mDatas.get(position).getWhole_photo_path();
    }

    @Override
    public DragImgAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drag_sort_img, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final DragImgAdapter.ItemViewHolder holder, final int position) {

        String url = mDatas.get(position).getWhole_photo_path();

        if (url.equals(PresenterEditLive.ADD_IMG)) {
            if (mDatas.size() == 10) {
                holder.rl_item_view.setVisibility(View.GONE);
            } else {
                holder.rl_item_view.setVisibility(View.VISIBLE);
                holder.iv_select_del.setVisibility(View.GONE);
            }
            holder.iv_img.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.clear(holder.iv_img);
            holder.iv_img.setImageResource(R.drawable.selector_show_gallery);
            holder.iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    baseActivity.showGetImageDialog();
                }
            });


        } else {
            holder.iv_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            GlideHelper.displayLocalImage(baseActivity, holder.iv_img, url);
            holder.iv_select_del.setVisibility(View.VISIBLE);
            holder.iv_select_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatas.remove(position);
                    baseActivity.updateIsCanSend();
                    baseActivity.updatePics();
                }
            });

            holder.iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    baseActivity.lookBigPic(position);
                }
            });

        }

    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public final ImageView iv_img;
        public final ImageView iv_select_del;
        public final RelativeLayout rl_item_view;

        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_send_img);
            iv_select_del = (ImageView) itemView.findViewById(R.id.iv_select_del);
            rl_item_view = (RelativeLayout) itemView.findViewById(R.id.rl_select_view);
        }

        @Override
        public void onItemSelected() {
            iv_img.setAlpha(0.5f);
        }

        @Override
        public void onItemClear() {
            iv_img.setAlpha(1f);
        }
    }
}
