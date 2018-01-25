package net.doyouhike.app.bbs.ui.widget.action;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.ui.widget.common.popwin.PopupWinListener;

/**
 * 功能：
 * 作者：曾江
 * 日期：15-12-29.
 */
public class DesPopupWin {


    GridView gv_atcion_des;
    private PopupWindow popupWindow;
    PopupWinListener listener;
    TextView desAll;

    public DesPopupWin(Context context) {
        init(context);
    }


    public void setAdapter(BaseAdapter adapter) {
        gv_atcion_des.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setListener(PopupWinListener listener) {
        this.listener = listener;
    }

    public void showPopWin(View view) {
        popupWindow.showAsDropDown(view);
    }


    public void dismiss() {
        popupWindow.dismiss();
    }


    /**
     * 初始化目的地选择框
     */
    private void init(Context context) {


        View viewOfEventDes = View.inflate(context,
                R.layout.dialog_action_des, null);
        desAll = (TextView) viewOfEventDes
                .findViewById(R.id.tv_des_all);

        gv_atcion_des = (GridView) viewOfEventDes
                .findViewById(R.id.gv_atcion_des);

        popupWindow = new PopupWindow(viewOfEventDes,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                listener.onDismiss();
            }
        });
        // 全部目的地
        desAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onItemSelected(PopupWinListener.ALL_SELECT);
                popupWindow.dismiss();
                ((TextView) v).setTextColor(v.getContext().getResources().getColor(R.color.clickable));
                v.setBackgroundColor(v.getContext().getResources().getColor(
                        R.color.item_pressed));
            }

        });

        gv_atcion_des.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                listener.onItemSelected(position);
                popupWindow.dismiss();
            }
        });
        View llytEventDesRoot = viewOfEventDes
                .findViewById(R.id.llyt_action_event_des);
        llytEventDesRoot.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return false;
            }
        });
        popupWindow.setFocusable(true);
    }

    public void setDesAllFail() {
        desAll.setTextColor(desAll.getContext().getResources().getColor(R.color.text_info));
        desAll.setBackgroundColor(desAll.getContext().getResources().getColor(
                R.color.white_word));
    }

}
