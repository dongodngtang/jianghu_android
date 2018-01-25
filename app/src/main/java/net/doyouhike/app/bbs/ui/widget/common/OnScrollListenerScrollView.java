package net.doyouhike.app.bbs.ui.widget.common;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import net.doyouhike.app.bbs.util.LogUtil;

/**
 * 滑动隐藏底部菜单
 * Created by zengjiang on 16/7/27.
 */

public class OnScrollListenerScrollView extends ScrollView implements Animation.AnimationListener {

    private GestureDetector mDetector;
    ScrollDetector mScrollDetector;


    private int mSlop;

    private float mDownY;
    private boolean mDirection;
    private boolean mIgnore = false;

    private boolean isHidding = false;


    /**
     * 是否滑动开关
     */
    private boolean openScroll=false;


    public interface OnScrollListener {
        void onScroll(int y, int oldY);
    }


    private OnScrollListener listener;


    public OnScrollListenerScrollView(Context context) {
        super(context);
        init(context);
    }

    public OnScrollListenerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }


    private void init(Context context) {
        mScrollDetector = new ScrollDetector();
        mDetector = new GestureDetector(context, mScrollDetector);
        mSlop = getSlop(context);
    }

    protected int getSlop(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            //noinspection deprecation
            return ViewConfiguration.getTouchSlop() * 2;
        } else {
            return ViewConfiguration.get(context).getScaledPagingTouchSlop();
        }
    }


    @Override
    public void onAnimationStart(Animation animation) {
        LogUtil.d("animationView.getTop()","animationView.getTop()"+animationView.getTop()
                +" animationView.getY"+animationView.getY()+" animationView.getPaddingTop"+animationView.getPaddingTop());
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mIgnore = false;

        if (isScrollDown()) {
            startApperaAnimation();
        }

        if (isScrollUp()) {
            startApperaAnimation();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        if (!openScroll) {
            return ;
        }

        if (mIgnore) {
            return;
        }


        if (isScrollDown() || isScrollUp()) {
            startApperaAnimation();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {


        mDetector.onTouchEvent(ev);

        return super.onTouchEvent(ev);
    }

    private View animationView;

    public void setAnimationView(View view) {

        this.animationView = view;


    }

    private void startHiddenAnimation() {


        if (isHidding) {
            return;
        }


        if (null == animationView) {
            return;
        }


        TranslateAnimation hiddenAnim = new TranslateAnimation(0, 0, 0, animationView.getHeight());//设置动画的偏移位移

        hiddenAnim.setDuration(500);//设置动画的时长
        hiddenAnim.setFillAfter(true);//设置动画结束后停留在该位置




        hiddenAnim.setAnimationListener(this);
//        animationView.clearAnimation();
        isHidding = true;
        mIgnore = true;
        animationView.startAnimation(hiddenAnim);

    }

    private void startApperaAnimation() {


        if (!isHidding) {
            return;
        }

        if (null == animationView) {
            return;
        }

        mIgnore = true;


        TranslateAnimation apperaAnim = new TranslateAnimation(0, 0, animationView.getHeight(), 0);//设置动画的偏移位移
        apperaAnim.setDuration(500);//设置动画的时长
        apperaAnim.setFillAfter(true);//设置动画结束后停留在该位置

        apperaAnim.setAnimationListener(this);
//        animationView.clearAnimation();
        isHidding = false;
        animationView.startAnimation(apperaAnim);
    }


    private class ScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (!openScroll) {
                return false;
            }

            if (mIgnore) {
                return false;
            }


            if (isScrollDown() || isScrollUp()) {
                startApperaAnimation();
                return false;
            }

            if (mDirection != distanceY > 0) {
                mDirection = !mDirection;
                mDownY = e2.getY();
            }

            float distance = mDownY - e2.getY();


            if (distance < -mSlop) {
                startApperaAnimation();
            } else if (distance > mSlop) {
                startHiddenAnimation();
            }

            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {


            if (!openScroll) {
                return false;
            }



            if (mIgnore) {
                return false;
            }


            if (isScrollDown() || isScrollUp()) {
                startApperaAnimation();
                return false;
            }


            if (velocityY > 0) {
                startApperaAnimation();
            } else if (velocityY < 0) {
                startHiddenAnimation();
            }

            return false;


        }

        @Override
        public boolean onDown(MotionEvent e) {
            mDownY = e.getY();
            return super.onDown(e);
        }

    }


    /**
     * 是否直接滑动到底部
     */
    protected boolean isScrollDown() {


        if (null != animationView) {

            return getHeight() + getScrollY() >= getChildAt(0).getHeight() - animationView.getHeight();
        }

        return getHeight() + getScrollY() == getChildAt(0).getHeight();
    }

    /**
     * 是否直接滑到顶部
     */
    protected boolean isScrollUp() {

        return getScrollY() == 0;
    }
}
