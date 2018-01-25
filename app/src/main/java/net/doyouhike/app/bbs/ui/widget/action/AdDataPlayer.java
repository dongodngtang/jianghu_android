package net.doyouhike.app.bbs.ui.widget.action;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.action.AdDataResp;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventBannersResp;
import net.doyouhike.app.bbs.ui.adapter.action.AdDataAdapter;
import net.doyouhike.app.bbs.ui.widget.common.viewpager.FixedSpeedScroller;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 功能：轮播广告处理
 * 作者：曾江
 * 日期：15-12-30.
 */
public class AdDataPlayer {
    View lunBoTu;
    AdDataAdapter adapter;
    InterceptScrollViewPager vp;
    private LunboTask task;

    public AdDataPlayer(View view) {
        init(view);
    }

    private void init(View view) {

        lunBoTu = view;

        vp = (InterceptScrollViewPager) lunBoTu
                .findViewById(R.id.fragment_action_viewpager);

//        vp.setPageTransformer(true, new DepthPageTransformer());
        setViewPagerScrollSpeed(vp);

        task = new LunboTask();
        vp.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        task.stopLunbo();
                        break;
                    case MotionEvent.ACTION_UP:
                        task.startLunbo();
                    case MotionEvent.ACTION_CANCEL:
                        task.startLunbo();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }


    public void setAdsData(List<EventBannersResp.ItemsBean> adsData) {

        adapter = new AdDataAdapter(lunBoTu.getContext(), adsData);
        vp.setAdapter(adapter);

        int middle = 100 * adsData.size();
        int extra = middle % adsData.size();// 测试数据的条目
        int item = middle - extra;
        vp.setCurrentItem(item, false);
        task.startLunbo();
    }

    public void stopTask() {
        task.stopLunbo();
    }

    public void startTask() {
        if (null != task)
            task.startLunbo();
    }

    /**
     * 处理轮播图的轮播事件
     */
    private class LunboTask extends Handler implements Runnable {
        public void stopLunbo() {
            removeCallbacksAndMessages(null);
        }

        public void startLunbo() {
            stopLunbo();
            postDelayed(this, 4000);
        }

        @Override
        public void run() {
            try {
                vp.setCurrentItem((vp.getCurrentItem() + 1), true);
                postDelayed(this, 4000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();

            View backgroundView = page;

            if (0 <= position && position < 1) {
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }
            if (-1 < position && position < 0) {
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }

            if (position <= -1.0f || position >= 1.0f) {
            } else if (position == 0.0f) {
            } else {
                if (backgroundView != null) {
                    ViewHelper.setAlpha(backgroundView, 1.0f - Math.abs(position));

                }

            }
        }

    }


    /**
     * 切换时间控制
     * @param viewPager
     */
    private void setViewPagerScrollSpeed(ViewPager viewPager) {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        }
    }

}
