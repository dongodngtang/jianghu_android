package net.doyouhike.app.bbs.util.linktext.event;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * 作者:luochangdong on 16/6/14 16:46
 * 描述:
 */
public class Clickable extends ClickableSpan implements View.OnClickListener {
    private final TextClickListener clickListener;
    private int position;

    public Clickable(TextClickListener clickListener, int position) {
        this.clickListener = clickListener;
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        clickListener.click(position);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        // TODO Auto-generated method stub
        super.updateDrawState(ds);
        ds.setColor(Color.WHITE); // 设置文件颜色
        ds.setUnderlineText(false);
    }
}
