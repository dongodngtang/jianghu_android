package net.doyouhike.app.bbs.util.linktext;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

import net.doyouhike.app.library.ui.widgets.linkify.OnTextClick;

/**
 * 功能：
 * 作者：曾江
 * 日期：16-1-25.
 */
public class ClickableMovementMethod extends LinkMovementMethod {

    private static ClickableMovementMethod sInstance;
    private OnTextClick onTextClick;
    private long lastClickTime;

    private static final long CLICK_DELAY = 500l;

    public static ClickableMovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new ClickableMovementMethod();
        }
        return sInstance;
    }

    public ClickableMovementMethod() {

    }

    public ClickableMovementMethod(OnTextClick onTextClick) {
        this.onTextClick = onTextClick;
    }

    public boolean onTouchEvent(TextView widget, Spannable buffer,
                                MotionEvent event) {
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
            UrlClickableImgSpan[] imageSpans = buffer.getSpans(off, off, UrlClickableImgSpan.class);
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
            } else if (imageSpans.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    if (System.currentTimeMillis() - lastClickTime < CLICK_DELAY)
                        imageSpans[0].onClick(widget);
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(imageSpans[0]),
                            buffer.getSpanEnd(imageSpans[0]));
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


}
