package widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;

/**
 * 自定义的日期选择�?
 *
 * @author sxzhang
 */
public class DatePicker extends LinearLayout {

    private Calendar calendar = Calendar.getInstance(); //������
    private WheelView newDays;
    private ArrayList<DateObject> dateList;
    private OnChangeListener onChangeListener; //onChangeListener
    private final int MARGIN_RIGHT = 0;
    private DateObject dateObject;        //日期数据对象

    //Constructors
    public DatePicker(Context context) {
        super(context);
        init(context);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始�?
     *
     * @param context
     */
    private void init(Context context) {
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int week = calendar.get(Calendar.DAY_OF_WEEK);
//        dateList = new ArrayList<DateObject>();
//        for (int i = 0; i < 300; i++) {
//            dateObject = new DateObject(year, month, day + i, week + i);
//            dateList.add(dateObject);
//        }

        initDate();
        newDays = new WheelView(context);
        LayoutParams newDays_param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        newDays_param.setMargins(0, 0, MARGIN_RIGHT, 0);
        newDays.setLayoutParams(newDays_param);
        newDays.setAdapter(new StringWheelAdapter(dateList, 30));
        newDays.setVisibleItems(5);
        newDays.setCyclic(false);
        newDays.addChangingListener(onDaysChangedListener);
        addView(newDays);
    }

    public void setNewDays(Date date) {

        DateObject select = new DateObject(date);

        for (int index = 0; index < dateList.size(); index++) {
            if (dateList.get(index).equals(select)) {
                newDays.setCurrentItem(index);
                break;
            }
        }


    }

    public void initDate() {
        dateList = new ArrayList<>();
        //请注意月份是从0-11
        Calendar start = Calendar.getInstance();
        start.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH));
        Calendar end = Calendar.getInstance();
        end.set(start.get(Calendar.YEAR) + 1, start.get(Calendar.MONTH) + 2, 1);


        int sumSunday = 0;
        int sumSat = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        while (start.compareTo(end) <= 0) {
            int w = start.get(Calendar.DAY_OF_WEEK);
            if (w == Calendar.SUNDAY)
                sumSunday++;
            if (w == Calendar.SATURDAY)
                sumSat++;
            //打印每天

            dateObject = new DateObject(start.getTime());
            dateList.add(dateObject);
//			System.out.println(format.format(start.getTime()));
            //循环，每次天数加1
            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
        }
    }

    /**
     * 滑动改变监听�?
     */
    private OnWheelChangedListener onDaysChangedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView mins, int oldValue, int newValue) {
            calendar.set(Calendar.DAY_OF_MONTH, newValue + 1);
            change();
        }
    };

    /**
     * 滑动改变监听器回调的接口
     */
    public interface OnChangeListener {
        void onChange(int year, int month, int day, int day_of_week);
    }

    /**
     * 设置滑动改变监听�?
     *
     * @param onChangeListener
     */
    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }


    /**
     * 滑动�?��调用的方�?
     */
    private void change() {
        if (onChangeListener != null) {
            onChangeListener.onChange(
                    dateList.get(newDays.getCurrentItem()).getYear(),
                    dateList.get(newDays.getCurrentItem()).getMonth(),
                    dateList.get(newDays.getCurrentItem()).getDay(),
                    dateList.get(newDays.getCurrentItem()).getWeek());

        }
    }

    public String getCurrentItem() {
        return dateList.get(newDays.getCurrentItem()).getListItem();
    }

    public Date getCurrentDate() {
        return dateList.get(newDays.getCurrentItem()).getDate();
    }


    /**
     * 根据day_of_week得到汉字星期
     *
     * @return
     */
    public static String getDayOfWeekCN(int day_of_week) {
        String result = null;
        switch (day_of_week) {
            case 1:
                result = "星期日";
                break;
            case 2:
                result = "星期一";
                break;
            case 3:
                result = "星期二";
                break;
            case 4:
                result = "星期三";
                break;
            case 5:
                result = "星期四";
                break;
            case 6:
                result = "星期五";
                break;
            case 7:
                result = "星期六";
                break;
            default:
                break;
        }
        return result;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
