package net.doyouhike.app.bbs.ui.util;

import android.widget.TextView;

import net.doyouhike.app.bbs.R;

/**
 * 状态按钮样式 加载中 正常 不可点击
 * Created by zengjiang on 16/7/15.
 */
public class StateButtomHelper {


    public static void setLoadingView(TextView view,String content){
        view.setText(content);
        view.setTextColor(view.getContext().getResources().getColor(
                R.color.transparency_white_word));
        view.setBackgroundDrawable(view.getContext().getResources().getDrawable(R.drawable.selector_login_button));
        view.setEnabled(false);
    }
    public static void setNormalView(TextView view,String content){
        view.setEnabled(true);
        view.setText(content);
        view.setTextColor(view.getContext().getResources().getColor(R.color.white));
        view.setBackgroundDrawable(view.getContext().getResources().getDrawable(R.drawable.selector_login_button));
    }
    public static void setNormalView(TextView view){
        view.setEnabled(true);
        view.setTextColor(view.getContext().getResources().getColor(R.color.white));
        view.setBackgroundDrawable(view.getContext().getResources().getDrawable(R.drawable.selector_login_button));
    }
    public static void setUnclickableView(TextView view){
        view.setTextColor(view.getContext().getResources().getColor(
                R.color.text_common_hint));
        view.setEnabled(false);
        view.setBackgroundDrawable(view.getContext().getResources().getDrawable(R.drawable.shape_blue_button));
    }
}
