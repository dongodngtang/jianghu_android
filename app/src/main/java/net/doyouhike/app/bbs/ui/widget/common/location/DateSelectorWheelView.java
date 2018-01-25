package net.doyouhike.app.bbs.ui.widget.common.location;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.library.ui.utils.DateUtils;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.wheelview.OnWheelChangedListener;
import net.doyouhike.app.bbs.util.wheelview.StrericWheelAdapter;
import net.doyouhike.app.bbs.util.wheelview.WheelView;

/**
 * 项目名称:  DateSelector
 * 类名称:   DateSelectorWheelView
 * 创建人:    xhl
 * 创建时间:  2015-1-14 下午5:07:34
 * 版本:      v1.0
 * 类描述:
 */
public class DateSelectorWheelView extends RelativeLayout implements
        OnWheelChangedListener {
    private final String flag = "PfpsDateWheelView";
    private LinearLayout llWheelViews;
    private WheelView wvHour;
    private WheelView wvMinute;
    private String[] hours = new String[24];
    private String[] minutes = new String[60];
    private StrericWheelAdapter huorsAdapter;
    private StrericWheelAdapter minuteAdapter;

    public DateSelectorWheelView(Context context, AttributeSet attrs,
                                 int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    public DateSelectorWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public DateSelectorWheelView(Context context) {
        super(context);
        initLayout(context);
    }

    private void initLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.dete_time_layout, this,
                true);
        llWheelViews = (LinearLayout) findViewById(R.id.ll_wheel_views);
        wvHour = (WheelView) findViewById(R.id.wv_date_of_hour);
        wvMinute = (WheelView) findViewById(R.id.wv_date_of_min);
        wvHour.addChangingListener(this);
        wvMinute.addChangingListener(this);
        llWheelViews.setVisibility(VISIBLE);
        setData();
    }

    private void setData() {
        for (int i = 0; i < hours.length; i++) {
            if(i< 10){
                hours[i] = "0" + i;
            }else{
                hours[i] = 0 + i + "";
            }

        }
        for (int i = 0; i < minutes.length; i++) {
            if (i < 10) {
                minutes[i] = "0" + (0 + i);
            } else {
                minutes[i] = (0 + i) + "";
            }
        }

        huorsAdapter = new StrericWheelAdapter(hours);
        minuteAdapter = new StrericWheelAdapter(minutes);
        wvHour.setAdapter(huorsAdapter);
        wvHour.setCyclic(true);
        wvMinute.setAdapter(minuteAdapter);
        wvMinute.setCyclic(true);

    }

    public void setDefual(String defualData){
        String str = defualData.replace("时", "").replace("分", "");
        LogUtil.d("集合时间",str);
        int hourIndex = Integer.parseInt(str.substring(0,2));
        int minIndex = Integer.parseInt(str.substring(2));
        LogUtil.d("集合时间","时"+hourIndex+"分"+minIndex);
        wvHour.setCurrentItem(hourIndex);
        wvMinute.setCurrentItem(minIndex);


    }


    /**
     * 获取选择的日期的值
     *
     * @return
     */
    public HourMinuteEntity getSelectedDate() {
        HourMinuteEntity entity = new HourMinuteEntity();
        entity.setHour(wvHour.getCurrentItemValue().trim());
        entity.setMinute(wvMinute.getCurrentItemValue().trim());
        return entity;
    }


    /**
     * 设置日期选择器的日期转轮是否可见
     *
     * @param visibility
     */
    public void setDateSelectorVisiblility(int visibility) {
        llWheelViews.setVisibility(visibility);
    }


    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        String trim = null;
        switch (wheel.getId()) {
            case R.id.wv_date_of_hour:
                trim = DateUtils.splitDateString(wvHour.getCurrentItemValue())
                        .trim();
                break;
            case R.id.wv_date_of_min:
                trim = DateUtils.splitDateString(wvMinute.getCurrentItemValue())
                        .trim();
                break;
        }
    }


}
