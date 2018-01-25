package net.doyouhike.app.bbs.ui.activity.action;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.presenter.action.SearchPresenter;
import net.doyouhike.app.bbs.ui.widget.action.XEditText;
import net.doyouhike.app.bbs.ui.widget.common.IUpdateView;
import net.doyouhike.app.bbs.util.UIUtils;

import butterknife.InjectView;

/**
 * 功能：活动和线路搜索
 *
 * @author：曾江 日期：16-5-9.
 */
public class ActionAndRoadSearchActivity extends BaseActivity implements View.OnClickListener, IUpdateView {

    /**
     * 切换页面标志,默认为true,即搜索活动,false为搜索路线
     */
    public static final String I_CHECKOUT_PAGE="param1";


    /**
     * 活动标签
     */
    SearchPresenter mPresenter;


    /**
     * 搜索输入框
     */
    @InjectView(R.id.et_activityKeyWord)
    XEditText etActivityKeyWord;
    /**
     * 取消
     */
    @InjectView(R.id.tv_cancel)
    TextView tvCancel;
    /**
     * 活动标题下标指示
     */
    @InjectView(R.id.vi_activity_action_search_indicate)
    View viActivityActionSearchIndicate;
    /**
     * 活动搜索按钮
     */
    @InjectView(R.id.ll_activity_action_search)
    LinearLayout llActivityActionSearch;
    /**
     * 线路标题下标指示
     */
    @InjectView(R.id.vi_activity_road_search_indicate)
    View viActivityRoadSearchIndicate;
    /**
     * 线路按钮
     */
    @InjectView(R.id.ll_activity_road_search)
    LinearLayout llActivityRoadSearch;
    /**
     * 列表
     */
    @InjectView(R.id.lv_activity_action_road_search)
    PullToRefreshListView mDataListView;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_search;
    }

    @Override
    protected View getLoadingTargetView() {
        return mDataListView;
    }

    @Override
    protected void initViewsAndEvents() {
        setListener();
        setupPage();
    }

    private void setupPage() {
        mPresenter=new SearchPresenter(this);
        //切换页面,tur为活动页面(默认),false为路线,由上级页面传过来
        checkoutPage(getIntent().getBooleanExtra(I_CHECKOUT_PAGE,true));
    }

    private void setListener() {
        tvCancel.setOnClickListener(this);
        llActivityActionSearch.setOnClickListener(this);
        llActivityRoadSearch.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                //取消
                UIUtils.showSoftInput(v,false);
                finish();
                break;
            case R.id.ll_activity_action_search:
                //切换到活动标签页
                checkoutPage(true);
                break;
            case R.id.ll_activity_road_search:
                checkoutPage(false);
                break;
            default:
                break;
        }
    }


    public XEditText getEtActivityKeyWord() {
        return etActivityKeyWord;
    }

    private void checkoutPage(boolean isAction) {
        viActivityActionSearchIndicate.setSelected(isAction);
        viActivityRoadSearchIndicate.setSelected(!isAction);

        mPresenter.checkoutPage(isAction);
    }
    public PullToRefreshListView getmDataListView() {
        return mDataListView;
    }

}
