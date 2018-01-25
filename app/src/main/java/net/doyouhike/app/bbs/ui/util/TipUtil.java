package net.doyouhike.app.bbs.ui.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.util.UIUtils;

/**
 * 提示工具类
 * Created by zengjiang on 16/6/21.
 */
public class TipUtil {


    public static void alert(Context context, String title) {

        MaterialDialog dialog = new MaterialDialog(context);
        dialog.isTitleShow(false)
                .content(title)
                .btnNum(1)
                .btnText("确定")
                .show();

    }

    public static void alert(Context context, String content, OnBtnClickL clickL) {
        MaterialDialog dialog = new MaterialDialog(context);
        dialog.isTitleShow(false)
                .content(content)
                .btnNum(1)
                .btnText("确定")
                .show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnBtnClickL(clickL);
    }

    public static void alert(Context context, String title, String msg) {
        MaterialDialog dialog = new MaterialDialog(context);
        dialog.content(msg)
                .title(title)
                .btnNum(1)
                .btnText("确定")
                .show();
    }

    public static MaterialDialog alert(Context context, String title, String msg, String[] btnTxt, OnBtnClickL... onBtnClickLs) {

        MaterialDialog dialog = new MaterialDialog(context);
        dialog.content(msg)
                .title(title)
                .isTitleShow(!TextUtils.isEmpty(title));

        if (null != btnTxt) {
            dialog.btnText(btnTxt);
            dialog.btnNum(btnTxt.length);

        } else {
            dialog.btnNum(1);
        }

        if (null != onBtnClickLs) {
            dialog.setOnBtnClickL(onBtnClickLs);
        }
        dialog.show();

        return dialog;

    }

    public static void showProgress(ImageView ivProgress, boolean show) {
        UIUtils.showView(ivProgress, show);
        if (show) {
            Animation operatingAnim = AnimationUtils.loadAnimation(ivProgress.getContext(), R.anim.share_loading);
            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
            ivProgress.clearAnimation();
            ivProgress.setAnimation(operatingAnim);
        } else {
            ivProgress.clearAnimation();
        }
    }

    public static void showProgress(ProgressBar progressBar, boolean show) {
        UIUtils.showView(progressBar, show);
    }
}
