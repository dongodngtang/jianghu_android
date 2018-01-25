package net.doyouhike.app.bbs.ui.widget.common.location;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.util.wheelview.OnWheelChangedListener;
import net.doyouhike.app.bbs.util.wheelview.StrericWheelAdapter;
import net.doyouhike.app.bbs.util.wheelview.WheelView;

/**
 * 作者:luochangdong on 16/4/20 09:45
 * 描述:
 */
public class NumPickerWheel extends RelativeLayout implements OnWheelChangedListener {

    WheelView wv_number_picker;
    String[] numbers = new String[15];
    StrericWheelAdapter numAdapter;

    public NumPickerWheel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    public NumPickerWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public NumPickerWheel(Context context) {
        super(context);
        initLayout(context);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {

    }


    private void initLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.wheel_number_picker, this,
                true);
        wv_number_picker = (WheelView) findViewById(R.id.wv_number_picker);
        wv_number_picker.addChangingListener(this);

        setData();
    }

    private void setData() {
        for (int i = 0; i < 15; i++) {
            numbers[i] = 2 + i + "";
        }

        numAdapter = new StrericWheelAdapter(numbers);
        wv_number_picker.setAdapter(numAdapter);
    }

    public String getCurrentValue() {
        return wv_number_picker.getCurrentItemValue();
    }
}
