package net.doyouhike.app.bbs.ui.activity.live;

import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.live.DestRoutePage;
import net.doyouhike.app.bbs.ui.widget.common.IUpdateView;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.biz.helper.list_helper.SimpleListHelper;

import butterknife.InjectView;

/**
 * 目的地线路列表
 * Created by zengjiang on 16/5/31.
 */
public class DestRouteListMoreActivity extends BaseActivity implements IUpdateView{


    public static final String I_NODE_SLUG=DestRouteListMoreActivity.class.getSimpleName()+"param1";


    /**
     * 标题栏
     */
    @InjectView(R.id.navigation_title)
    TitleView mTitle;
    /**
     * 列表栏
     */
    @InjectView(R.id.lv_activity_dest_route_list)
    PullToRefreshListView mListView;


    private String mNodeSlug;

    /**
     * 列表助手
     */
    SimpleListHelper mListHelper;
    /**
     * 目的地线路列表页
     */
    DestRoutePage mPage;



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_dest_route_list;
    }

    @Override
    protected View getLoadingTargetView() {
        return mListView;
    }

    @Override
    protected void initViewsAndEvents() {

        //设置NodeSlug值
        mNodeSlug=getIntent().getStringExtra(I_NODE_SLUG);

        mPage=new DestRoutePage(this,mNodeSlug);
        mListHelper=new SimpleListHelper(mListView,this,mPage);

        //获取数据,刷新
        mListHelper.getData(true);

    }

    @Override
    protected void onDestroy() {
        mListHelper.onDestroy();
        super.onDestroy();
    }
}
