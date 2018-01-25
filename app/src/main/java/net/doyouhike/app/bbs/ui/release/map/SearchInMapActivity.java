package net.doyouhike.app.bbs.ui.release.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.event.SearchNearLocationEvent;
import net.doyouhike.app.bbs.ui.widget.action.XEditText;

import butterknife.InjectView;

/**
 * 作者:luochangdong on 16/5/27 09:27
 * 描述:
 */
public class SearchInMapActivity extends BaseActivity {

    @InjectView(R.id.et_activityKeyWord)
    XEditText etActivityKeyWord;
    @InjectView(R.id.tv_cancel)
    TextView tvCancel;
    @InjectView(R.id.lv_search_in_map_list)
    ListView lvSearchInMapList;
    @InjectView(R.id.line_search_in_map_down)
    View lineSearchInMapDown;
    @InjectView(R.id.ll_search_in_map_content)
    LinearLayout llSearchInMapContent;

    /**
     * 辅助工具
     */
    PresenterSearchInMap presenter;

    /**
     * Activity 之间传递的参数
     */
   private Bundle extras;



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_search_in_map;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        this.extras = extras;
    }

    @Override
    protected void initViewsAndEvents() {
        presenter = new PresenterSearchInMap(this);
        presenter.getBundleExtras(extras);
        etActivityKeyWord.clearFocus();

    }

    @Override
    protected View getLoadingTargetView() {
        return llSearchInMapContent;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onResume() {
        presenter.onResumeEvent();
        super.onResume();
    }

    /**
     * 地图定位回调 定位完成后开始搜索附近
     *
     * @param event
     */
    public void onEvent(SearchNearLocationEvent event) {
       presenter.onEventSearchNearLocation(event);
    }
}
