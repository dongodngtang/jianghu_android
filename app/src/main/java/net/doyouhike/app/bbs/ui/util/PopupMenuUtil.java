package net.doyouhike.app.bbs.ui.util;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.util.UIUtils;

/**
 * Created by zengjiang on 16/6/28.
 */
public class PopupMenuUtil {


    public static void showMenu(View view,final OnPopupMenuItemClickListener listener, String... items) {


        if (null == items || items.length == 0) {
            return;
        }


        LayoutInflater mInflater = (LayoutInflater) view.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = mInflater.inflate(R.layout.layout_comment_popup_menu, null);


        TextView item1 = (TextView) layout.findViewById(R.id.tv_menu_item1);
        TextView item2 = (TextView) layout.findViewById(R.id.tv_menu_item2);


        if (items.length >= 2) {

            UIUtils.showView(item1, true);
            UIUtils.showView(item2, true);

            item1.setText(items[0]);
            item2.setText(items[1]);

        } else if (items.length == 1) {

            UIUtils.showView(item1, true);
            UIUtils.showView(item2, false);

            item1.setText(items[0]);
        }


        layout.measure(View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED);
        final PopupWindow mDropdown = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT, true);
        mDropdown.setBackgroundDrawable(new BitmapDrawable());
        mDropdown.setOutsideTouchable(true);

        mDropdown.showAsDropDown(view,view.getWidth()/2,0);


        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(0);
                mDropdown.dismiss();
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(1);
                mDropdown.dismiss();
            }
        });


    }
}
