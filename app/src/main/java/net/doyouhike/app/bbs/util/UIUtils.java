package net.doyouhike.app.bbs.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UIUtils {

    public static final boolean isShowToast = false;

    /**
     * 把value的对应类型值（TypedValue.COMPLEX_UNIT_PX、TypedValue.COMPLEX_UNIT_DIP、
     * TypedValue.COMPLEX_UNIT_SP、
     * TypedValue.COMPLEX_UNIT_PT、TypedValue.COMPLEX_UNIT_IN
     * 、TypedValue.COMPLEX_UNIT_MM）转变为px值
     *
     * @param context 上下文
     * @param unit    TypedValue类型
     * @param value   要转变为px的值
     * @return
     */
    public static float getRawSize(Context context, int unit, float value) {
        Resources res = context.getResources();
        return TypedValue.applyDimension(unit, value, res.getDisplayMetrics());
    }

    /**
     * 把values中dimens值（如：R.dimen.img_height）转变为px值
     *
     * @param context 上下文
     * @param index   id值（如：R.dimen.img_height）
     * @return
     */
    public static int getIntFromDimens(Context context, int index) {
        int result = context.getResources().getDimensionPixelSize(index);
        return result;
    }

    /**
     * 判断view是否在屏幕上显示
     *
     * @param view
     * @return
     */
    public static boolean isViewCovered(final View view) {

        if (view == null) {
            return false;
        }

        View currentView = view;

        Rect currentViewRect = new Rect();
        boolean partVisible = currentView.getGlobalVisibleRect(currentViewRect);
        boolean totalHeightVisible = (currentViewRect.bottom - currentViewRect.top) >= view
                .getMeasuredHeight();
        boolean totalWidthVisible = (currentViewRect.right - currentViewRect.left) >= view
                .getMeasuredWidth();
        boolean totalViewVisible = partVisible && totalHeightVisible
                && totalWidthVisible;
        if (!totalViewVisible)// if any part of the view is clipped by any of
            // its parents,return true
            return true;

        while (currentView.getParent() instanceof ViewGroup) {
            ViewGroup currentParent = (ViewGroup) currentView.getParent();
            if (currentParent.getVisibility() != View.VISIBLE)// if the parent
                // of view is
                // not
                // visible,return
                // true
                return true;

            int start = indexOfViewInParent(currentView, currentParent);
            for (int i = start + 1; i < currentParent.getChildCount(); i++) {
                Rect viewRect = new Rect();
                view.getGlobalVisibleRect(viewRect);
                View otherView = currentParent.getChildAt(i);
                Rect otherViewRect = new Rect();
                otherView.getGlobalVisibleRect(otherViewRect);
                if (Rect.intersects(viewRect, otherViewRect))// if view
                    // intersects
                    // its older
                    // brother(covered),return
                    // true
                    return true;
            }
            currentView = currentParent;
        }
        return false;
    }

    private static int indexOfViewInParent(View view, ViewGroup parent) {
        int index;
        for (index = 0; index < parent.getChildCount(); index++) {
            if (parent.getChildAt(index) == view)
                break;
        }
        return index;
    }

    /**
     * 获取屏幕有效高度
     *
     * @param context
     * @return 屏幕有效高度，为-1时表示无法获取屏幕高度
     */
    public static int getValidHeight(Context context) {
        DisplayMetrics metrics = getMetrics(context);
        if (metrics != null) {
            return metrics.heightPixels;
        }
        return -1;
    }

    /**
     * 获取屏幕有效宽度
     *
     * @param context
     * @return 屏幕有效宽度，为-1时表示无法获取屏幕宽度
     */
    public static int getValidWidth(Context context) {
        DisplayMetrics metrics = getMetrics(context);
        if (metrics != null) {
            return metrics.widthPixels;
        }
        return -1;
    }

    /**
     * 获取状态栏高度
     *
     * @param context 所在的activity
     * @return
     */
    //获取手机状态栏高度
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 沉浸状态栏需要填充的高度,不填充且不使用fitsSystemWindows,内容会嵌进状态栏
     *
     * @param context
     * @return 如果版本小于19, 没有沉浸式, 返回0, 否则返回状态栏高度
     */
    public static int getTranslucentStatusHeight(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return getStatusBarHeight(context);
        }
        return 0;
    }


    private static DisplayMetrics getMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * @param view   要显示的view
     * @param isShow 是否显示View true为显示,false为隐藏
     */
    public static void showView(View view, boolean isShow) {
        if (view != null)
            view.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 关闭键盘
     *
     * @param view 接受输入的view
     */
    public static void showSoftInput(View view, boolean requestShow) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//		boolean isOpen = imm.isActive();

        if (requestShow) {
            //请求打开键盘
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);

        } else {
            //请求关闭键盘
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

        }
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // get the list view adapter, so this function must be invoked after set the adapter.
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        // get the ListView count
        int count = listAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            // measure the child view
            listItem.measure(0, 0);
            // calculate the total height of items
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // get divider height for all items and add the total height
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * TODO 测量listView 的  具体高度. <br/>
     * 日期: 2015-9-28 下午3:50:05 <br/>
     *
     * @param listView
     * @return
     * @author zhulin
     * @since JDK 1.6
     */
    public static int measuredListView(ListView listView) {


        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        // get the ListView count
        int count = listAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            // measure the child view
            listItem.measure(0, 0);
            // calculate the total height of items
            totalHeight += listItem.getMeasuredHeight();
        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // get divider height for all items and add the total height
        int contentHeight = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));


        //titleBar
        contentHeight += DensityUtil.dip2px(listView.getContext(), 44);

        int maxHeight = getValidHeight(listView.getContext())
//                -getStatusBarHeight(listView.getContext())
                - UIUtils.getIntFromDimens(listView.getContext(), R.dimen.dimen_142dp);


        if (contentHeight < maxHeight) {
            return contentHeight;
        }

        return maxHeight;
    }

    public static void setKeyword(TextView tv, String dest, String keyword) {
        SpannableString s = new SpannableString(dest);
        Pattern p = Pattern.compile(keyword);

        Matcher m = p.matcher(s);

        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(tv.getContext().getResources().getColor(R.color.orange_bg)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(s);
    }
}
