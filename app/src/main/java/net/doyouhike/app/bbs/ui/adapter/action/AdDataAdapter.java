/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ActionCarouselFigureAdapter.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-10-27
 *
 */
package net.doyouhike.app.bbs.ui.adapter.action;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import net.doyouhike.app.bbs.biz.newnetwork.model.response.action.AdDataResp;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventBannersResp;
import net.doyouhike.app.bbs.ui.activity.action.ActionDetailActivity;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZouWenJie 活动页轮播图的适配器
 */
public class AdDataAdapter extends PagerAdapter {
    private Context context;
    private List<EventBannersResp.ItemsBean> datas = new ArrayList<>();

    public static final String INTENT_EXTRA_NAME_LIVE_ID = "liveId";
    public static final String INTENT_EXTRA_NAME_LIVE_TYPE = "live_type";
    public static final String INTENT_EXTRA_NAME_LINKURL = "LinkUrl";

    public AdDataAdapter(Context context, List<EventBannersResp.ItemsBean> adsData) {
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
    public Object instantiateItem(ViewGroup container, int position) {


        final int clickPosition = position % datas.size();
        ImageView iv = new ImageView(context);
        iv.setScaleType(ScaleType.CENTER_CROP);
        GlideHelper.displayAdImage(context, iv, datas.get(clickPosition).getImage_url());
        //设置点击事件

        iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EventBannersResp.ItemsBean actionAdsInfo = datas.get(clickPosition);
                if ("mf_link".equals(actionAdsInfo.getLink_type()) || "link".equals(actionAdsInfo.getLink_type())) {
                    //链接类型
                    ActivityRouter.openWebActivity(context,actionAdsInfo.getLink_url());

                } else  if ("4".equals(actionAdsInfo.getNode_type())) {
                        //活动类型
                        Intent actionIntent = new Intent(context,
                                ActionDetailActivity.class);
                        actionIntent.putExtra("nodeId", datas.get(clickPosition).getLink_url());
                        context.startActivity(actionIntent);
                }
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
