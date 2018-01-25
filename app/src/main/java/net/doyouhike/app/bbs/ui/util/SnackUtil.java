package net.doyouhike.app.bbs.ui.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.library.ui.snackbar.SnackBarItem;

/**
 * Created by zengjiang on 16/6/21.
 */
public class SnackUtil {

    public static void showSnack(Context context, int msgId) {

        if (context instanceof Activity) {
            Activity mActivity = (Activity) context;
            int txtColor = context.getResources().getColor(R.color.color_snack);
            new SnackBarItem.Builder(mActivity)
                    .setMessageResource(msgId)
                    .setDuration(1000)
                    .setSnackBarMessageColor(txtColor)
                    .show();
        } else {
            Toast.makeText(context, context.getString(msgId), Toast.LENGTH_SHORT).show();
        }

    }

    public static void showSnack(Context context, String msg) {
        if (msg == null)
            return;
        if (context instanceof Activity) {
            Activity mActivity = (Activity) context;
            int txtColor = context.getResources().getColor(R.color.color_snack);
            new SnackBarItem.Builder(mActivity)
                    .setMessage(msg)
                    .setDuration(1000)
                    .setSnackBarMessageColor(txtColor)
                    .show();
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }

    }
}
