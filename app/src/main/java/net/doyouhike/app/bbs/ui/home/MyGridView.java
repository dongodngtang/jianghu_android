package net.doyouhike.app.bbs.ui.home;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-1-6
 */
public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }


    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
