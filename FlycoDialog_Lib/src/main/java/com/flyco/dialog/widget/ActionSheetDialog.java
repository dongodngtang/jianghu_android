package com.flyco.dialog.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.dialog.R;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BottomBaseDialog;

import java.util.ArrayList;

/**
 * Dialog like iOS ActionSheet(iOS风格对话框)
 */
public class ActionSheetDialog extends BottomBaseDialog<ActionSheetDialog> {

    /**
     * 弹窗动画延时
     */
    private final static int Duration = 100;
    /**
     * ListView
     */
    private ListView mLv;
    /**
     * title
     */
    private TextView mTvTitle;
    /**
     * title underline(标题下划线)
     */
    private View mVLineTitle;
    /**
     * mCancel button(取消按钮)
     */
    private TextView mTvCancel;
    /**
     * corner radius,dp(圆角程度,单位dp)
     */
    private float mCornerRadius = 0;
    /**
     * title background color(标题背景颜色)
     */
    private int mTitleBgColor = Color.parseColor("#ddffffff");
    /**
     * title text(标题)
     */
    private String mTitle = "提示";
    /**
     * title height(标题栏高度)
     */
    private float mTitleHeight = 48;
    /**
     * title textcolor(标题颜色)
     */
    private int mTitleTextColor = Color.parseColor("#42000000");
    /**
     * title textsize(标题字体大小,单位sp)
     */
    private float mTitleTextSize = 15f;
    /**
     * ListView background color(ListView背景色)
     */
    private int mLvBgColor = Color.parseColor("#ddffffff");
    /**
     * divider color(ListView divider颜色)
     */
    private int mDividerColor = Color.parseColor("#FFE1E1E1");
    /**
     * divider height(ListView divider高度)
     */
    private float mDividerHeight = 0.5f;
    /**
     * item press color(ListView item按住颜色)
     */
    private int mItemPressColor = Color.parseColor("#FFEDF3F1");
    /**
     * item textcolor(ListView item文字颜色)
     */
    private int mItemTextColor = Color.parseColor("#DE000000");

    private int backgroudColor = Color.parseColor("#F6F8F6");

    /**
     * item textcolor(ListView item文字颜色 红色)
     */
    private int mItemTextColorOrange = Color.parseColor("#FE3824");

    /**
     * item textsize(ListView item文字大小)
     */
    private float mItemTextSize = 17.5f;
    /**
     * item height(ListView item高度)
     */
    private float mItemHeight = 48;
    /**
     * enable title show(是否显示标题)
     */
    private boolean mIsTitleShow = true;
    /***
     * cancel btn text(取消按钮内容)
     */
    private String mCancelText = "取消";
    /**
     * cancel btn text color(取消按钮文字颜色)
     */
    private int mCancelTextColor = Color.parseColor("#DE000000");
    /**
     * cancel btn text size(取消按钮文字大小)
     */
    private float mCancelTextSize = 17.5f;
    /**
     * adapter(自定义适配器)
     */
    private BaseAdapter mAdapter;
    /**
     * operation items(操作items)
     */
    private ArrayList<DialogMenuItem> mContents = new ArrayList<>();
    private OnOperItemClickL mOnOperItemClickL;
    private LayoutAnimationController mLac;

    public void setOnOperItemClickL(OnOperItemClickL onOperItemClickL) {
        mOnOperItemClickL = onOperItemClickL;
    }

    public ActionSheetDialog(Context context, ArrayList<DialogMenuItem> baseItems, View animateView) {
        super(context, animateView);
        mContents.addAll(baseItems);
        init();
    }

    public ActionSheetDialog(Context context, String[] items, View animateView) {
        super(context, animateView);
        mContents = new ArrayList<>();
        for (String item : items) {
            DialogMenuItem customBaseItem = new DialogMenuItem(item, 0);
            mContents.add(customBaseItem);
        }
        init();
    }

    public ActionSheetDialog(Context context, BaseAdapter adapter, View animateView) {
        super(context, animateView);
        mAdapter = adapter;
        init();
    }

    private void init() {
        widthScale(1f);
        /** LayoutAnimation */
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 6f, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(Duration);
        animation.setStartOffset(150);

        mLac = new LayoutAnimationController(animation, 0.12f);
        mLac.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    public View onCreateView() {
        LinearLayout ll_container = new LinearLayout(mContext);
        ll_container.setOrientation(LinearLayout.VERTICAL);
        ll_container.setBackgroundColor(backgroudColor);

        /** title */
        mTvTitle = new TextView(mContext);
        mTvTitle.setGravity(Gravity.CENTER);
        mTvTitle.setPadding(dp2px(10), dp2px(5), dp2px(10), dp2px(5));

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.topMargin = dp2px(0);

        ll_container.addView(mTvTitle, params);

        /** title underline */
        mVLineTitle = new View(mContext);
        ll_container.addView(mVLineTitle);

        /** listview */
        mLv = new ListView(mContext);
        mLv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
        mLv.setCacheColorHint(Color.TRANSPARENT);
        mLv.setFadingEdgeLength(0);
        mLv.setVerticalScrollBarEnabled(false);
        mLv.setSelector(new ColorDrawable(Color.TRANSPARENT));

        ll_container.addView(mLv);

        /** mCancel btn */
        mTvCancel = new TextView(mContext);
        mTvCancel.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.topMargin = dp2px(10);
        lp.bottomMargin = dp2px(0);
        mTvCancel.setLayoutParams(lp);

        ll_container.addView(mTvCancel);

        return ll_container;
    }

    @Override
    public void setUiBeforShow() {
        /** title */
        float radius = dp2px(mCornerRadius);
        mTvTitle.setHeight(dp2px(mTitleHeight));
        mTvTitle.setBackgroundDrawable(CornerUtils.cornerDrawable(mTitleBgColor, new float[]{radius, radius, radius,
                radius, 0, 0, 0, 0}));
        mTvTitle.setText(mTitle);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleTextSize);
        mTvTitle.setTextColor(mTitleTextColor);
        mTvTitle.setVisibility(mIsTitleShow ? View.VISIBLE : View.GONE);

        /** title underline */
        mVLineTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dp2px(mDividerHeight)));
        mVLineTitle.setBackgroundColor(mDividerColor);
        mVLineTitle.setVisibility(mIsTitleShow ? View.VISIBLE : View.GONE);

        /** mCancel btn */
        mTvCancel.setHeight(dp2px(mItemHeight));
        mTvCancel.setText(mCancelText);
        mTvCancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, mCancelTextSize);
        mTvCancel.setTextColor(mCancelTextColor);
        mTvCancel.setBackgroundDrawable(CornerUtils.listItemSelector(radius, mLvBgColor, mItemPressColor, 1, 0));

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        /** listview */
        mLv.setDivider(new ColorDrawable(mDividerColor));
        mLv.setDividerHeight(dp2px(mDividerHeight));

        if (mIsTitleShow) {
            mLv.setBackgroundDrawable(CornerUtils.cornerDrawable(mLvBgColor, new float[]{0, 0, 0, 0, radius, radius, radius,
                    radius}));
        } else {
            mLv.setBackgroundDrawable(CornerUtils.cornerDrawable(mLvBgColor, radius));
        }

        if (mAdapter == null) {
            mAdapter = new ListDialogAdapter();
        }

        mLv.setAdapter(mAdapter);
        mLv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnOperItemClickL != null) {
                    mOnOperItemClickL.onOperItemClick(parent, view, position, id);
                }
                if (isShowing()) {
                    dismiss();
                }
            }
        });

        mLv.setLayoutAnimation(mLac);
    }

    /**
     * set title background color(设置标题栏背景色)
     */
    public ActionSheetDialog titleBgColor(int titleBgColor) {
        mTitleBgColor = titleBgColor;
        return this;
    }

    /**
     * set title text(设置标题内容)
     */
    public ActionSheetDialog title(String title) {
        mTitle = title;
        return this;
    }

    /**
     * set titleHeight(设置标题高度)
     */
    public ActionSheetDialog titleHeight(float titleHeight) {
        mTitleHeight = titleHeight;
        return this;
    }

    /**
     * set title textsize(设置标题字体大小)
     */
    public ActionSheetDialog titleTextSize_SP(float titleTextSize_SP) {
        mTitleTextSize = titleTextSize_SP;
        return this;
    }

    /**
     * set title textcolor(设置标题字体颜色)
     */
    public ActionSheetDialog titleTextColor(int titleTextColor) {
        mTitleTextColor = titleTextColor;
        return this;
    }

    /**
     * enable title show(设置标题是否显示)
     */
    public ActionSheetDialog isTitleShow(boolean isTitleShow) {
        mIsTitleShow = isTitleShow;
        return this;
    }

    /**
     * set ListView background color(设置ListView背景)
     */
    public ActionSheetDialog lvBgColor(int lvBgColor) {
        mLvBgColor = lvBgColor;
        return this;
    }

    /**
     * set corner radius(设置圆角程度,单位dp)
     */
    public ActionSheetDialog cornerRadius(float cornerRadius_DP) {
        mCornerRadius = cornerRadius_DP;
        return this;
    }

    /**
     * set divider color(ListView divider颜色)
     */
    public ActionSheetDialog dividerColor(int dividerColor) {
        mDividerColor = dividerColor;
        return this;
    }

    /**
     * set divider height(ListView divider高度)
     */
    public ActionSheetDialog dividerHeight(float dividerHeight_DP) {
        mDividerHeight = dividerHeight_DP;
        return this;
    }

    /**
     * set item press color(item按住颜色)
     */
    public ActionSheetDialog itemPressColor(int itemPressColor) {
        mItemPressColor = itemPressColor;
        return this;
    }

    /**
     * set item textcolor(item字体颜色)* @return ActionSheetDialog
     */
    public ActionSheetDialog itemTextColor(int itemTextColor) {
        mItemTextColor = itemTextColor;
        return this;
    }

    /**
     * set item textsize(item字体大小)
     */
    public ActionSheetDialog itemTextSize(float itemTextSize_SP) {
        mItemTextSize = itemTextSize_SP;
        return this;
    }

    /**
     * set item height(item高度)
     */
    public ActionSheetDialog itemHeight(float itemHeight_DP) {
        mItemHeight = itemHeight_DP;
        return this;
    }

    /**
     * set layoutAnimation(设置layout动画 ,传入null将不显示layout动画)
     */
    public ActionSheetDialog layoutAnimation(LayoutAnimationController lac) {
        mLac = lac;
        return this;
    }

    /**
     * set cancel btn text(设置取消按钮内容)
     */
    public ActionSheetDialog cancelText(String cancelText) {
        mCancelText = cancelText;
        return this;
    }

    /**
     * cancel btn text color(取消按钮文字颜色)
     */
    public ActionSheetDialog cancelText(int cancelTextColor) {
        mCancelTextColor = cancelTextColor;
        return this;
    }

    /**
     * cancel btn text size(取消按钮文字大小)
     */
    public ActionSheetDialog cancelTextSize(float cancelTextSize) {
        mCancelTextSize = cancelTextSize;
        return this;
    }

    /**
     * cancel btn text size(取消按钮文字大小)
     */
    public ActionSheetDialog setItems(String... items) {

        if (null != items && mContents != null) {

            mContents.clear();
            for (String item : items) {
                DialogMenuItem customBaseItem = new DialogMenuItem(item, 0);
                mContents.add(customBaseItem);
            }
        }

        if (null != mAdapter) {
            mAdapter.notifyDataSetChanged();
        }
        return this;
    }


    class ListDialogAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mContents.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final DialogMenuItem item = mContents.get(position);

            LinearLayout llItem = new LinearLayout(mContext);
            llItem.setOrientation(LinearLayout.HORIZONTAL);
            llItem.setGravity(Gravity.CENTER_VERTICAL);

            ImageView ivItem = new ImageView(mContext);
            ivItem.setPadding(0, 0, dp2px(15), 0);
            llItem.addView(ivItem);

            TextView tvItem = new TextView(mContext);
            tvItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            tvItem.setSingleLine(true);
            tvItem.setGravity(Gravity.CENTER);
            if (mContext.getString(R.string.orange_give_up).contains(item.mOperName)) {
                tvItem.setTextColor(mItemTextColorOrange);
            } else {
                tvItem.setTextColor(mItemTextColor);
            }

            tvItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, mItemTextSize);
            tvItem.setHeight(dp2px(mItemHeight));

            llItem.addView(tvItem);
            float radius = dp2px(mCornerRadius);
            if (mIsTitleShow) {
                llItem.setBackgroundDrawable((CornerUtils.listItemSelector(radius, Color.TRANSPARENT, mItemPressColor,
                        position == mContents.size() - 1)));
            } else {
                llItem.setBackgroundDrawable(CornerUtils.listItemSelector(radius, Color.TRANSPARENT, mItemPressColor,
                        mContents.size(), position));
            }

            ivItem.setImageResource(item.mResId);
            tvItem.setText(item.mOperName);
            ivItem.setVisibility(item.mResId == 0 ? View.GONE : View.VISIBLE);

            return llItem;
        }
    }
}
