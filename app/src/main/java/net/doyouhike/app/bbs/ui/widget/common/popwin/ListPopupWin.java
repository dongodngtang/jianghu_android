package net.doyouhike.app.bbs.ui.widget.common.popwin;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import net.doyouhike.app.bbs.R;

/**
 * 功能：条件筛选框
 * 作者：曾江
 * 日期：15-12-28.
 */
public class ListPopupWin {
    PopupWindow popupWindow;
    PopupWinListener listener;
    ListView lvCondition;

    public ListPopupWin(Context context) {
        init(context);
    }

    public void init(Context context) {

        View viewOfEventType = View.inflate(context,
                R.layout.layout_dialog_popup_condition, null);
        lvCondition = (ListView) viewOfEventType
                .findViewById(R.id.lv_event_type);
        View llytEventTypesRoot = (LinearLayout) viewOfEventType
                .findViewById(R.id.llyt_action_event_types);
        // 触摸弹出框的灰色部分，关闭自己
        llytEventTypesRoot.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return false;
            }
        });


        popupWindow = new PopupWindow(viewOfEventType,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT));


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (listener != null) {
                    listener.onDismiss();
                }
            }
        });

        // 类型选择项的点击事件
        lvCondition.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (listener != null) {
                    listener.onItemSelected(position);
                }
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);

    }

    public void setAdapter(BaseAdapter adapter) {
        lvCondition.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setListener(PopupWinListener listener) {
        this.listener = listener;
    }

    public void setLvHeightWrapContent() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        lvCondition.setLayoutParams(layoutParams);
    }

    public void showPopWin(View view) {
        popupWindow.showAsDropDown(view);
    }


    public void dismiss() {
        popupWindow.dismiss();
    }


}
