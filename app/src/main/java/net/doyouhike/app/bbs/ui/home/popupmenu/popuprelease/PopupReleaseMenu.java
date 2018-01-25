package net.doyouhike.app.bbs.ui.home.popupmenu.popuprelease;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.flyco.dialog.widget.popup.base.BaseBubblePopup;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.ui.activity.MainActivity;

/**
 * 作者:luochangdong on 16/5/10 09:28
 * 描述:
 */
public class PopupReleaseMenu extends BaseBubblePopup<PopupReleaseMenu> {
    LinearLayout ll_popup_live, ll_popup_event;

    MainActivity baseActivity;

    public PopupReleaseMenu(MainActivity context) {
        super(context);
        baseActivity = context;
        bubbleColor(Color.parseColor("#FF48ADA0"));
        cornerRadius(2);
        margin(8, 8);
        gravity(Gravity.TOP);

    }

    @Override
    public View onCreateBubbleView() {
        View inflate = View.inflate(mContext, R.layout.popup_release_menu, null);
        ll_popup_live = (LinearLayout) inflate.findViewById(R.id.ll_popup_live);
        ll_popup_event = (LinearLayout) inflate.findViewById(R.id.ll_popup_event);


        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        super.setUiBeforShow();
        ll_popup_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.goSendEvent();
                dismiss();
            }
        });

        ll_popup_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.onSendLive();
                dismiss();
            }
        });
    }
}
