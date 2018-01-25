package net.doyouhike.app.bbs.ui.activity.action;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.ui.widget.action.RichTextView;
import net.doyouhike.app.bbs.ui.widget.common.OnScrollListenerScrollView;

/**
 * 活动详情View
 * Created by terry on 4/6/16.
 */
public interface IActionDetailView {

    /**
     * 返回头部
     *
     * @return
     */
    View getHeader();



    /**
     * 返回底部 编辑布局
     *
     * @return
     */
    View getBottomView();

    /**
     * @return 内容视图
     */
    OnScrollListenerScrollView getContentView();


    void onLoadStart();

    void onLoadSuccess();

    void onLoadError(int code);

}
