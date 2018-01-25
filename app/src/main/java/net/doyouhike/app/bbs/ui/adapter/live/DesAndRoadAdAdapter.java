package net.doyouhike.app.bbs.ui.adapter.live;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.doyouhike.app.bbs.biz.entity.dynamic.DesAndRoadListAdInfo;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by terry on 5/30/16.
 */
public class DesAndRoadAdAdapter extends PagerAdapter {


    private Context context;
    private List<DesAndRoadListAdInfo> datas = new ArrayList<>();

    public static final String INTENT_EXTRA_NAME_LIVE_ID = "liveId";
    public static final String INTENT_EXTRA_NAME_LIVE_TYPE = "live_type";
    public static final String INTENT_EXTRA_NAME_LINKURL = "LinkUrl";

    public DesAndRoadAdAdapter(Context context, List<DesAndRoadListAdInfo> adsData) {
        this.context = context;
        this.datas = adsData;
    }

    @Override
    public int getCount() {
        if (datas.size() != 0 && datas.size() != 0) {
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {


        final int clickPosition = position % datas.size();
        ImageView iv = new ImageView(context);

        GlideHelper.displayNetImage(context, iv, Constant.PHOTO_DOMAIN_PATH + datas.get(clickPosition).getPath());
        //设置点击事件
        final ArrayList<String> urls = new ArrayList<String>();
        for (DesAndRoadListAdInfo adInfo : datas) {
            urls.add(Constant.PHOTO_DOMAIN_PATH + adInfo.getPath());
        }
        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityRouter.openGalleryActivity(context,
                        urls.toArray(new String[urls.size()]), clickPosition);

            }
        });


        //设置点击事件
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
