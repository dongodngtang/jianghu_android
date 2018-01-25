package net.doyouhike.app.bbs.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.List;

/**
 * 作者:luochangdong on 16/5/18 16:15
 * 描述:
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{

    private LayoutInflater mInflater;
    private List<String> mDatas;
    private Context mContext;
    private String[] urls;

    public GalleryAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setmDatas(List<String> datas){
        mDatas = datas;
        urls = new String[getItemCount()];
        urls = mDatas.toArray(urls);
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_live_image_gallery,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.liveImage = (ImageView) view.findViewById(R.id.iv_live_image);


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GlideHelper.displayNetImage(mContext, holder.liveImage, mDatas.get(position), R.drawable.home_big_pic_fail_white);
        setClickImage(holder.liveImage,urls,position);
    }

    private void setClickImage(ImageView imageView, final String[] url,
                               final int index) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityRouter.openGalleryActivity(mContext,url,index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        ImageView liveImage;
    }
}
