package net.doyouhike.app.library.ui.widgets.linkify;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

/**
 * 作者：luochangdong on 16/8/19
 * 描述：解决点击事件 与父控件冲突问题
 */
public class SmartTextClickMovement extends LinkMovementMethod {
    private static SmartTextClickMovement sInstance;
    private OnTextClick onTextClick;
    private long lastClickTime;

    private static final long CLICK_DELAY = 500l;

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
            if (action == MotionEvent.ACTION_DOWN)
                lastClickTime = System.currentTimeMillis();
            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    if (System.currentTimeMillis() - lastClickTime < CLICK_DELAY)
                        link[0].onClick(widget);
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));
                }

                return true;
            } else {
                Selection.removeSelection(buffer);
                if (action == MotionEvent.ACTION_UP && onTextClick != null) {
                    if (System.currentTimeMillis() - lastClickTime < CLICK_DELAY)
                        onTextClick.onTextClicked();
                }

            }
        }

        return false;
    }

    public static SmartTextClickMovement getInstance() {
        if (sInstance == null)
            sInstance = new SmartTextClickMovement();

        return sInstance;
    }

    public void setOnTextClick(OnTextClick onTextClick) {
        this.onTextClick = onTextClick;
    }


}
