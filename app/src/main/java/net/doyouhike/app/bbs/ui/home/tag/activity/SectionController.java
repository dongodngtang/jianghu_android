package net.doyouhike.app.bbs.ui.home.tag.activity;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.ui.widget.common.dslv.DragSortController;
import net.doyouhike.app.bbs.ui.widget.common.dslv.DragSortListView;
import net.doyouhike.app.bbs.util.LogUtil;

/**
 * 功能：
 *
 * @author：曾江 日期：16-2-25.
 */
public class SectionController extends DragSortController {

    private int mPos;

    private SectionAdapter mAdapter;

    DragSortListView mDslv;

    public SectionController(DragSortListView dslv, SectionAdapter adapter) {
        super(dslv, R.id.vi_item_home_tag_parent, DragSortController.ON_DOWN, 0);
        setRemoveEnabled(false);
        mDslv = dslv;
        mAdapter = adapter;
    }

    @Override
    public int startDragPosition(MotionEvent ev) {
        int res = super.dragHandleHitPosition(ev);
        if (res >= mAdapter.getDivPosition()) {
            return DragSortController.MISS;
        }

        int width = mDslv.getWidth() * 44 / 375;

        if ((int) ev.getX() < width) {
            return res;
        } else {
            return DragSortController.MISS;
        }
    }

    @Override
    public View onCreateFloatView(int position) {
        mPos = position;

        View v = mAdapter.getView(position, null, mDslv);
        v.getBackground().setLevel(10000);
        return v;
    }

    private int origHeight = -1;

    @Override
    public void onDragFloatView(View floatView, Point floatPoint, Point touchPoint) {
        final int first = mDslv.getFirstVisiblePosition();
        final int lvDivHeight = mDslv.getDividerHeight();

        if (origHeight == -1) {
            origHeight = floatView.getHeight();
        }

        View div = mDslv.getChildAt(mAdapter.getDivPosition() - first);

        if (touchPoint.x > mDslv.getWidth() / 2) {
            float scale = touchPoint.x - mDslv.getWidth() / 2;
            scale /= (float) (mDslv.getWidth() / 5);
            ViewGroup.LayoutParams lp = floatView.getLayoutParams();
            lp.height = Math.max(origHeight, (int) (scale * origHeight));
            Log.d("mobeta", "setting height " + lp.height);
            floatView.setLayoutParams(lp);
        }

        if (div != null) {
            if (mPos > mAdapter.getDivPosition()) {
                // don't allow floating View to go above
                // section divider
                final int limit = div.getBottom() + lvDivHeight;
                if (floatPoint.y < limit) {
                    floatPoint.y = limit;
                }
            } else {
                // don't allow floating View to go below
                // section divider
                final int limit = div.getTop() - lvDivHeight - floatView.getHeight();
                LogUtil.d("mobeta", "limit=" + "div.getTop:" + div.getTop() + "-lvDivHeight" + lvDivHeight
                        + "-floatView.getHeight" + floatView.getHeight() + "=" + limit);
                if (floatPoint.y > limit) {
                    floatPoint.y = limit;
                }
            }
        }
    }

    @Override
    public void onDestroyFloatView(View floatView) {
        //do nothing; block super from crashing
    }

}
