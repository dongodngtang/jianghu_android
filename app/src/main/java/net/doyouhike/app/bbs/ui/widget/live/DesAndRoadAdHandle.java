package net.doyouhike.app.bbs.ui.widget.live;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import net.doyouhike.app.bbs.biz.entity.dynamic.DesAndRoadListAdInfo;
import net.doyouhike.app.bbs.ui.adapter.live.DesAndRoadAdAdapter;
import net.doyouhike.app.bbs.ui.widget.action.InterceptScrollViewPager;

import java.util.List;

/**
 * Created by terry on 5/30/16.
 * 目的地 线路 列表 图片轮播
 */
public class DesAndRoadAdHandle  extends Handler {

    private Context context;
    DesAndRoadAdAdapter adapter;
    InterceptScrollViewPager vp;
    private LunboTask task;

    public DesAndRoadAdHandle(Context context,InterceptScrollViewPager viewPager) {
        this.context = context;
        this.vp = viewPager;
        init();
    }

    private void init() {
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


    public void setAdsData(List<DesAndRoadListAdInfo> adsData) {

        adapter = new DesAndRoadAdAdapter(context, adsData);
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
                vp.setCurrentItem((vp.getCurrentItem() + 1));
                postDelayed(this, 4000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
