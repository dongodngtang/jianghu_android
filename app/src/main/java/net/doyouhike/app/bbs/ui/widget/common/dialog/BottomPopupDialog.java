package net.doyouhike.app.bbs.ui.widget.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.util.DensityUtil;
import net.doyouhike.app.bbs.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 底部弹出dailog
 * 注意 : 泛型T 要覆盖 toString 方法 ,item的显示直接调用 T.toString{@link BottomPopupDialog.DialogAdapter#getView(int, View, ViewGroup)}
 * 注意 : 泛型T 要覆盖 toString 方法 ,item的显示直接调用 T.toString{@link BottomPopupDialog.DialogAdapter#getView(int, View, ViewGroup)}
 *
 * @see BottomPopupDialog.BottomDialogListener 回调接口
 * Created by zengjiang on 16/5/27.
 */
public abstract class BottomPopupDialog<T> extends Dialog {


    public interface BottomDialogListener<T> {

        /**
         * 消失回调
         */
        void onDismiss();

        /**
         * 选中回调
         *
         * @param t 选中ITEM
         */
        void setSelectTopic(T t);

    }

    private View mDialogView;

    //左边按键
    TextView tvLeft;

    //右边按钮
    TextView tvRight;

    //item列表
    protected ListView mListView;

    private BottomDialogListener listener;


    /**
     * 全部列表内容
     */
    private List<T> mItems = new ArrayList<>();

    private DialogAdapter mAdapter;


    public BottomPopupDialog(Context context) {
        super(context, R.style.dialog_full_screen_buttom_up);
        initialize(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mDialogView);
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        //设置宽度
//        lp.width = UIUtils.getValidWidth(getContext());
//        //设置高度
//        lp.height = UIUtils.getValidHeight(getContext()) - UIUtils.getIntFromDimens(getContext(), R.dimen.dimen_100dp) ;
//        lp.gravity = Gravity.BOTTOM;
//        getWindow().setAttributes(lp);


        notifyDataSetChanged();
    }

    public void setListener(BottomDialogListener listener) {
        this.listener = listener;
    }

    public void notifyDataSetChanged() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = UIUtils.measuredListView(mListView) ;
        params.width = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
    }


    /**
     * 设置选中item
     *
     * @param t
     */
    public void setSelectItem(T t) {
        mAdapter.setSelected(t);
    }

    public T getSelectItem() {
        return mAdapter.getSelected();
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public String getRightText() {
        return null;
    }

    public void onRightClick() {
    }

    public void upDataItem(List<T> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }


    private void initialize(Context context) {
        initView(context);
        initData();
        initListener();

    }

    private void initView(Context context) {
        mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_road_list_type, null);
        mListView = (ListView) mDialogView.findViewById(R.id.lv_roadList_type);
        tvLeft = (TextView) mDialogView.findViewById(R.id.tv_roadList_type_cancle);
        tvRight = (TextView) mDialogView.findViewById(R.id.tv_roadList_type_right);
        UIUtils.showView(tvLeft, true);
        UIUtils.showView(tvRight, false);
        tvRight.setText(getRightText());
    }


    private void initListener() {
        //转发item点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T t = mItems.get(position);
                setSelectItem(t);
                listener.setSelectTopic(t);
                dismiss();
            }
        });
        //消失监听
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                listener.onDismiss();
            }
        });
        //取消按钮监听事件
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //右边按钮监听事件
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightClick();
                dismiss();
            }
        });
    }

    private void initData() {
        mAdapter = new DialogAdapter();
        mListView.setAdapter(mAdapter);
    }


    /**
     * 列表适配器
     */
    class DialogAdapter extends BaseAdapter {
        private T t;
        private LayoutInflater mInflater;

        @Override
        public int getCount() {
            return mItems.size() + 1;
        }

        @Override
        public T getItem(int position) {

            if (position >= mItems.size()) {
                return null;
            }

            return mItems.get(position);
        }

        @Override
        public boolean isEnabled(int position) {
            return position!=mItems.size();
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == mInflater)
                mInflater = LayoutInflater.from(getContext());
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.dialog_road_list_type_item, parent, false);
            }


            TextView textView = (TextView) convertView.findViewById(R.id.tv_raodList_type_name);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_roadList_type_selected);

            if (position >= mItems.size()) {
                //底部多一空白
                imageView.setVisibility(View.GONE);
                textView.setText("");
            } else {


                //选中 需实现 equals 方法
                if (mItems.get(position).equals(t)) {
                    imageView.setVisibility(View.VISIBLE);
                    textView.setTextColor(getContext().getResources().getColor(R.color.text_road_list_dailog_selected));
                } else {
                    imageView.setVisibility(View.GONE);
                    textView.setTextColor(getContext().getResources().getColor(R.color.text_black_color_default));
                }
                //需实现toString方法
                textView.setText(getContent(mItems.get(position)));
            }
            return convertView;
        }

        public void setSelected(T t) {
            this.t = t;
            notifyDataSetChanged();
        }

        public T getSelected() {
            return t;
        }
    }

    public String getContent(T t) {
        return t.toString();
    }
}
