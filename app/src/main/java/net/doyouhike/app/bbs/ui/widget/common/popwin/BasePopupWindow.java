package net.doyouhike.app.bbs.ui.widget.common.popwin;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

import net.doyouhike.app.bbs.R;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-3-11
 */
public abstract class BasePopupWindow extends PopupWindow {

    protected View popRootView;

    public BasePopupWindow() {
        super();
    }

    public BasePopupWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BasePopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePopupWindow(Context context) {
        super(context);
    }

    public BasePopupWindow(int width, int height) {
        super(width, height);
    }

    public BasePopupWindow(View contentView, int width, int height,
                           boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public BasePopupWindow(View contentView) {
        super(contentView);
        initialize(contentView);
    }

    public BasePopupWindow(View contentView, int width, int height) {
        super(contentView, width, height, true);
        initialize(contentView);
    }

    private void initialize(View view) {
        this.popRootView = view;
        setFocusable(true);
        setOutsideTouchable(true);
        setTouchable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        setAnimationStyle(R.style.anim_fade);
        initViews();
        initEvents();
        init();
    }

    public abstract void initViews();

    public abstract void initEvents();

    public abstract void init();

    public View findViewById(int id) {
        return popRootView.findViewById(id);
    }
}
