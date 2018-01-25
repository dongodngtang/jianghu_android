package net.doyouhike.app.bbs.ui.widget.common;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.util.LogUtil;

import butterknife.ButterKnife;

/**
 * 功能：滑动悬停嵌套listview基础控件
 *
 * @author：曾江 日期：16-3-18.
 */
public abstract class BaseScrollListView extends ScrollView {

    protected static final String TAG = "BaseScrollListView";

    /**
     * 滑动列表
     */
    PullToRefreshListView mListView;

    RelativeLayout rlLayScrollListContent;

    protected FrameLayout viLayScrollListRoot;
    LinearLayout viLayScrollListParent;

    protected boolean isContentViEmpty = true;
    /**
     * 滑动时需要隐藏的视图的可见高度
     */
    protected int mHideViewVisibleHeight;


    /**
     * 滑动时需要隐藏的视图
     */
    private View hideView;
    /**
     * 滑动时悬停视图
     */
    protected View stickView;
    /**
     * 悬停占位视图,与悬停视图重叠,被悬停视图覆盖
     */
    protected View stickHideView;

    /**
     * 当前滚动位置
     */
    protected int mScrollY = -1;

    public BaseScrollListView(Context context) {
        super(context);
    }

    public BaseScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView(getContext());
    }

    /**
     * @return 获取填充高度值
     */
    protected abstract int getPaddingHeight();


    /**
     * @param isTop listview是否在第一条,用于开关刷新操作
     */
    protected abstract void onListScroll(boolean isTop);

    /**
     * @return 滑动隐藏的页面
     */
    protected abstract View getHideView();

    protected abstract int getHideViewHeight();
    /**
     * @return 悬停页面
     */
    protected abstract View getStickyView();

    /**
     * @return 悬停页面高度
     */
    protected abstract int getStickyViewHeight();

    /**
     * 添加view
     */
    protected abstract void initAddView();

    /**
     * 用于加载view后调用
     */
    protected abstract void initialize();

    /**
     * @return 是否沉浸式
     */
    protected abstract boolean isTranslucentStatus();

    /**
     * public method
     */

    public void showView() {
        requestFocus();
        scrollTo(0, 0);
    }
    public void smoothShowView() {
        requestFocus();
        smoothScrollTo(0, 0);
    }

    /**
     * @return 返回上拉加载更多视图
     */

    public PullToRefreshListView getListView() {
        return mListView;
    }

    public void setContentView(boolean isContentViEmpty) {
        this.isContentViEmpty = isContentViEmpty;

//        LogUtil.d(TAG, "getContentViewHeight:" + getContentViewHeight(isContentViEmpty));
        setContentViewHeight(getContentViewHeight(isContentViEmpty));
    }

    /**
     * 视图销毁调用
     */
    public void onDestroyView() {

        ButterKnife.reset(this);
    }


    /**
     * public method
     */


    /**
     * override method
     */

    protected float oldDownX;
    protected float oldDownY;


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = super.onInterceptTouchEvent(ev);

//        LogUtil.d(TAG, "onInterceptTouchEvent" + "   isIntercept:" + isIntercept);

        getHideViewVisibleHeight();


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldDownX = ev.getRawX();
                oldDownY = ev.getRawY();

                //按下事件不拦截
                isIntercept = false;

//                LogUtil.d(TAG, "onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                float currentDownY = ev.getRawY();
                float currentDownX = ev.getRawX();
                float deltaY = currentDownY - oldDownY;
                float deltaX = currentDownX - oldDownX;
                float distanceX = Math.abs(deltaX);
                float distanceY = Math.abs(deltaY);


//                LogUtil.d(TAG, "onInterceptTouchEvent ACTION_MOVE");
                if (deltaY > 0) {

//                    LogUtil.d(TAG, "onInterceptTouchEvent 下拉 deltaY:" + deltaY
//                            + "  isIntercept:" + isIntercept + "    mListView.getTop:" + mListView.getTop());

                    isIntercept = isListViewTop();

                } else {
//                    LogUtil.d(TAG, "onInterceptTouchEvent 上拉 deltaY:" + deltaY + "  isIntercept:" + isIntercept);

                    if (mHideViewVisibleHeight <= 0) {
                        isIntercept = false;
                    }
                }

//                LogUtil.d(TAG, "onInterceptTouchEvent distanceY:" + distanceY + "   distanceX:" + distanceX);
                if (mHideViewVisibleHeight > 0 && distanceX > distanceY) {
//                    LogUtil.d(TAG, "onInterceptTouchEvent 水平滑动不拦截");
                    isIntercept = false;
                }

                break;
        }

//        LogUtil.d(TAG, "onInterceptTouchEvent 是否拦截：" + isIntercept);

        return isIntercept;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        onScrollYChanged(t);
    }

    /**
     * 滑动改变
     *
     * @param scrollY y移动坐标
     */
    protected void onScrollYChanged(int scrollY) {

        if (mScrollY == scrollY) {
            return;
        }


        getHideViewVisibleHeight();

        mScrollY = scrollY;
        //条件选择框悬停
        int translationY;
//        if (mHideViewVisibleHeight==0){
//            translationY=Math.max(mScrollY, stickHideView.getTop()-hideView.getHeight()+getPaddingHeight());
//        }else {
//            translationY=Math.max(mScrollY, stickHideView.getTop());
//        }

        translationY=Math.max(mScrollY, stickHideView.getTop());
//        LogUtil.d(TAG, "隐藏悬停框在父视图的顶部：" + translationY +"mHideViewVisibleHeight:"+
//                mHideViewVisibleHeight+ "   scrollY:" + scrollY);

        stickView.setTranslationY(translationY);
    }


    /**
     * private method
     */


    private void initView(Context context) {
        View view = View.inflate(context, R.layout.layout_scroll_list, this);

        rlLayScrollListContent=(RelativeLayout)view.findViewById(R.id.rl_lay_scroll_list_content);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        viLayScrollListParent = (LinearLayout) view.findViewById(R.id.vi_lay_scroll_list_parent);
        //添加悬停占位视图到内容视图
        stickHideView = new View(getContext());
        viLayScrollListParent.addView(stickHideView, 0, layoutParams);
        //添加滑动隐藏视图到内容视图
        LinearLayout.LayoutParams lpHideView = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, getHideViewHeight());
        hideView = getHideView();
        viLayScrollListParent.addView(hideView, 0, lpHideView);


        viLayScrollListRoot = (FrameLayout) view.findViewById(R.id.vi_lay_scroll_list_root);

        FrameLayout.LayoutParams lpStickView = new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.MATCH_PARENT, getStickyViewHeight());
        //添加悬停视图到根视图
        stickView = getStickyView();
        viLayScrollListRoot.addView(stickView, lpStickView);
        initAddView();
        ButterKnife.inject(this);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int lvHeight = rlLayScrollListContent.getHeight();
                int requestHeight = getContentViewHeight(isContentViEmpty);

//                LogUtil.d(TAG, "requestHeight:" + requestHeight +
//                        "   lvHeight:" + lvHeight);

                if (lvHeight != requestHeight) {
                    setContentViewHeight(requestHeight);
                }
                onScrollYChanged(getScrollY());
            }
        });

        mListView =(PullToRefreshListView)view.findViewById(R.id.pur_lay_scroll_list_list);

        mListView.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                onListScroll(isListViewTop());
            }
        });

        //调整隐藏浮动栏高度
        stickView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int stickViewHeight = stickView.getHeight();

                if (stickViewHeight != stickHideView.getHeight()) {
                    LinearLayout.LayoutParams layoutParams = new
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            stickViewHeight);
                    stickHideView.setLayoutParams(layoutParams);
                }
            }
        });

        initialize();
        scrollToTop();
    }



    private boolean isListViewTop() {
        return mListView.getRefreshableView().getChildCount() > 0 && mListView.getRefreshableView().getFirstVisiblePosition() == 0
                && mListView.getRefreshableView().getChildAt(0).getTop() >= mListView.getRefreshableView().getPaddingTop();
    }

    /**
     * 获取需要隐藏的视图在屏幕中的可见高度
     */
    protected void getHideViewVisibleHeight() {

        Rect rectGlobalVisible = new Rect();
     boolean isVisible=   hideView.getGlobalVisibleRect(rectGlobalVisible);
        Rect rectLocalVisible = new Rect();
        hideView.getLocalVisibleRect(rectLocalVisible);

        int[] localScreen=new int[2];
        hideView.getLocationOnScreen(localScreen);

        int[] localWin=new int[2];
        hideView.getLocationInWindow(localWin);

        mHideViewVisibleHeight = rectGlobalVisible.bottom - rectGlobalVisible.top
                - getPaddingHeight();


        if (isTranslucentStatus()){
            //有沉浸式

            if (rectGlobalVisible.top <0||!isVisible) {
                //关于我页面完全被遮挡
                mHideViewVisibleHeight = 0;
            }
        }else {
            //无沉浸式

            if (rectGlobalVisible.top <=0||!isVisible) {
                //关于我页面完全被遮挡
                mHideViewVisibleHeight = 0;
            }
        }


//        LogUtil.d(TAG, "隐藏视图的可见高度:" + mHideViewVisibleHeight +
//                "  rectGlobalVisible:" + rectGlobalVisible
//                + "     rectLocalVisible:" + rectLocalVisible+"" +
//                "\n  hideView.getHeight::"+hideView.getHeight()+
//                "\n  LocationOnScreen_x-y:"+localScreen[0]+"-"+localScreen[1]+
//                "\n  localWin-y:"+localWin[0]+"-"+localWin[1]);
    }


    /**
     * 获取内容视图高度
     *
     * @param isContentViEmpty
     * @return
     */
    protected int getContentViewHeight(boolean isContentViEmpty) {

        if (isContentViEmpty) {
            return getHeight() - hideView.getHeight() - stickView.getHeight();
        }

        return getHeight() - stickView.getHeight() - getPaddingHeight();
    }

    protected void setContentViewHeight(int requestHeight) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, requestHeight);
        rlLayScrollListContent.setLayoutParams(layoutParams);
    }


    /**
     * 滑到顶部
     */
    protected void scrollToTop() {
        getHideViewVisibleHeight();
        if (mHideViewVisibleHeight > 0) {
            //如果需要隐藏视图的可视高度大于0
            //滑到顶部
            smoothScrollBy(0, mHideViewVisibleHeight);
        }
    }
    /**
     * private method
     */


}
