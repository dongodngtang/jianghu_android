package net.doyouhike.app.bbs.util;

import android.content.Context;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedSectionListView;

import net.doyouhike.app.library.ui.utils.DateUtils;

/**
 * 关于上下拉刷新的一些工具方法
 */
public class PullToRefreshLVUtil {

	/**
	 * 设置上下拉刷新各个阶段的文字 (一般刷新ListView) 
	 */
	public static void setRefreshAndLoadLabel(PullToRefreshListView ptrLv) {
		ILoadingLayout startLabels = ptrLv.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在刷新...");// 刷新时
		startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
		
		ILoadingLayout endLabels = ptrLv.getLoadingLayoutProxy(false, true);
		endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("正在加载...");// 刷新时
		endLabels.setReleaseLabel("放开加载...");// 下来达到一定距离时，显示的提示
	}

	/**
	 * 设置个人页面的上拉加载各个阶段的文字 (刷新不需要lable了) 
	 */
	public static void setLoadLabel(PullToRefreshListView ptrLv) {
		ILoadingLayout startLabels = ptrLv.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("");// 刷新时
		startLabels.setReleaseLabel("");// 下来达到一定距离时，显示的提示
		startLabels.setLoadingDrawable(null);
		
		ILoadingLayout endLabels = ptrLv.getLoadingLayoutProxy(false, true);
		endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("正在加载...");// 刷新时
		endLabels.setReleaseLabel("放开加载...");// 下来达到一定距离时，显示的提示

	}

	/**
	 * 设置上下拉刷新各个阶段的文字(悬浮项刷新ListView)
	 * @param Lv 
	 */
	public static void setRefreshAndLoadLabel(
			PullToRefreshPinnedSectionListView ptrpsLv) {
		ILoadingLayout startLabels = ptrpsLv.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在刷新...");// 刷新时
		startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

		ILoadingLayout endLabels = ptrpsLv.getLoadingLayoutProxy(false, true);
		endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("正在加载...");// 刷新时
		endLabels.setReleaseLabel("放开加载...");// 下来达到一定距离时，显示的提示
	}

	/**
	 * 设置最后刷新时间
	 */
	public static void setLastUpdatedLabel(Context context,
			PullToRefreshBase<ListView> refreshView) {
		// 显示最后更新的时间
		/*String label = DateUtils.formatDateTime(
				context, System.currentTimeMillis(),
				DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);*/
		String label = DateUtils.getStrDate(context, System.currentTimeMillis());
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		
	}

}
