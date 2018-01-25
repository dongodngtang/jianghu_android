package net.doyouhike.app.bbs.ui.fragment;


import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.fragment.BaseFragment;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.IPage;
import net.doyouhike.app.bbs.ui.widget.common.IUpdateView;
import net.doyouhike.app.bbs.biz.helper.list_helper.SimpleListHelper;

import butterknife.InjectView;

/**
 * 列表加载fragment
 * @param <T> list item
 */
public abstract class ListFragment <T> extends BaseFragment implements IUpdateView {


    @InjectView(R.id.refresh_loadmore_listview)
    PullToRefreshListView refreshLoadmoreListview;

   protected SimpleListHelper<T> simpleListHelper;

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return refreshLoadmoreListview;
    }

    @Override
    protected void initViewsAndEvents() {
        simpleListHelper=getSimpleListHelper();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_list;
    }

    public SimpleListHelper<T> getSimpleListHelper() {
        return new SimpleListHelper<>(refreshLoadmoreListview,this,getPage());
    }

    protected abstract IPage<T> getPage();

    @Override
    public void onDestroyView() {
        simpleListHelper.onDestroy();
        super.onDestroyView();
    }
}
