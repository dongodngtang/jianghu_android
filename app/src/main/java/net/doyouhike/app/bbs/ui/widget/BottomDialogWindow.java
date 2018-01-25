/*
* -----------------------------------------------------------------
* Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
* -----------------------------------------------------------------
*
* File: BottomDialogWindow.java
* Author: ChenWeiZhen
* Version: 1.0
* Create: 2015-10-6
*
* Changes (from 2015-10-6)
* -----------------------------------------------------------------
* 2015-10-6 创建BottomDialogWindow.java (ChenWeiZhen);
* -----------------------------------------------------------------
*/
package net.doyouhike.app.bbs.ui.widget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.ui.adapter.live.BottomDialogWindowAdapter;

import java.util.List;

/**
 * 下部弹出框的类
 */
public class BottomDialogWindow {

    private Context context;
    private ActionSheetDialog bottomDialog;
    OnItemClickListener mItemClickListener;

    @SuppressLint("NewApi")
    public BottomDialogWindow(Context context, List<String> itemStrList, List<Integer> itemColorList) {
        this.context = context;

        bottomDialog = new ActionSheetDialog(context, itemStrList.toArray(new String[itemStrList.size()]), null);
        bottomDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != mItemClickListener) {
                    mItemClickListener.onItemClick(parent,
                            view, position, id);
                }
            }
        });
        bottomDialog.isTitleShow(false);

    }


    /**
     * 设置列表项的点击监听
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    /**
     * 显示
     */
    public void show() {
        if (bottomDialog != null) {
            bottomDialog.show();
        }
    }

    /**
     * 解散
     */
    public void dismiss() {
        if (bottomDialog != null) {
            bottomDialog.dismiss();
        }
    }

}
