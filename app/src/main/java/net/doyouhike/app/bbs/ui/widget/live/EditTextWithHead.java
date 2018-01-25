package net.doyouhike.app.bbs.ui.widget.live;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class EditTextWithHead extends EditText {

    private CharSequence headText = "";

    public EditTextWithHead(Context context) {
        super(context);
    }

    public EditTextWithHead(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextWithHead(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelection(int index) {
        if (headText != null) {
            index = index < headText.length() ? headText.length() : index;
        }
        super.setSelection(index);
    }

    @Override
    public void setSelection(int start, int stop) {
        if (headText != null) {
            start = start < headText.length() ? headText.length() : start;
            stop = stop < headText.length() ? headText.length() : stop;
        }
        super.setSelection(start, stop);
    }

    public void setHeadText(CharSequence headText) {
        if (headText != null) {
            setText(headText);
            this.headText = headText;
        } else {
            setText("");
            this.headText = "";
        }
        setSelection(0);
    }

    public CharSequence getTextWithoutHead() {
        int start = this.headText == null ? 0 : this.headText.length();
        return getText().subSequence(start, getText().length());
    }

    public CharSequence getHeadText() {
        return headText;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (headText != null) {
            int start = getSelectionStart();
            int end = getSelectionEnd();
            int len = headText.length();
            if ((start == end)) { // 单光标状态
                if (start < len) { // 光标在head里面
                    setSelection(0);
                    return true;
                } else if (start == len) { // 光标在head尾部
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        return true;
                    }
                }
            } else { // 双光标，即选择模式
                if (start < len || end < len) { // 有光标在head里面
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean bringPointIntoView(int offset) {
        if (headText != null && offset < headText.length()) {
            offset = headText.length();
        }
        return super.bringPointIntoView(offset);
    }

    @Override
    public Editable getEditableText() {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        int len = headText.length();
        if (start < len || end < len) {
            setSelection(0);
        }
        return super.getEditableText();
    }

    @Override
    public int getOffsetForPosition(float x, float y) {
        if (headText != null
                && super.getOffsetForPosition(x, y) < headText.length()) {
            return headText.length();
        } else {
            return super.getOffsetForPosition(x, y);
        }
    }

}
