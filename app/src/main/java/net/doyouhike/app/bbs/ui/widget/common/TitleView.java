package net.doyouhike.app.bbs.ui.widget.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;


public class TitleView extends RelativeLayout implements OnClickListener {

    private RelativeLayout mRlParent;
    private ImageView left_image, right_image;
    private TextView title, left_text, right_text;
    private ClickListener listener = null;

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    public interface ClickListener {
        public void clickLeft();

        public void clickRight();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setPadding() {
        left_image.setPadding(10, 3, 10, 3);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_title, this);

        mRlParent = (RelativeLayout) this.findViewById(R.id.title_view_parent);
        left_image = (ImageView) this.findViewById(R.id.back);
        right_image = (ImageView) this.findViewById(R.id.right_btn);
        title = (TextView) this.findViewById(R.id.title);
        left_text = (TextView) this.findViewById(R.id.left);
        right_text = (TextView) this.findViewById(R.id.right);


        //右边点击范围
        FrameLayout frameLayoutRight = (FrameLayout) this.findViewById(R.id.title_right_content);
        frameLayoutRight.setOnClickListener(this);

        //左边点击范围
        LinearLayout frameLayoutLeft = (LinearLayout) this.findViewById(R.id.title_left_content);
        frameLayoutLeft.setOnClickListener(this);

        left_image.setOnClickListener(this);
        right_image.setOnClickListener(this);
        left_text.setOnClickListener(this);
        right_text.setOnClickListener(this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);

        Drawable drawable=typedArray.getDrawable(R.styleable.TitleView_title_background);
        if (null!=drawable){
            mRlParent.setBackgroundDrawable(drawable);
        }


        int left_src = typedArray.getResourceId(R.styleable.TitleView_left_src, 0);
        if (left_src != 0) {
            left_image.setImageResource(left_src);
            left_image.setVisibility(View.VISIBLE);
        }
        String left = typedArray.getString(R.styleable.TitleView_left_text);
        if (!TextUtils.isEmpty(left)) {
            left_text.setVisibility(View.VISIBLE);
            left_text.setText(left);
        }
        String text = typedArray.getString(R.styleable.TitleView_m_title);
        if (text != null) title.setText(text);
        int right_src = typedArray.getResourceId(R.styleable.TitleView_right_src, 0);
        if (right_src != 0) {
            right_image.setVisibility(View.VISIBLE);
            right_image.setImageResource(right_src);
        }


        String right = typedArray.getString(R.styleable.TitleView_right_text);
        ColorStateList rightTextColor=typedArray.getColorStateList(R.styleable.TitleView_right_text_color);
        if (!TextUtils.isEmpty(right)) {
            right_text.setVisibility(View.VISIBLE);
            right_text.setText(right);
            if (null!=rightTextColor){
                right_text.setTextColor(rightTextColor);
            }
        }
        typedArray.recycle();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back:
                if (null != listener) {
                    listener.clickLeft();
                }else {
                    Context context = v.getContext();
                    if (listener == null && (context instanceof Activity)) {
                        ((Activity) context).onBackPressed();
                    }
                }
                break;
            case R.id.right_btn:
                if (null != listener) {
                    listener.clickRight();
                }
                break;
            case R.id.left:
                if (null != listener) {
                    listener.clickLeft();
                }else {
                    Context context = getContext();
                    if (listener == null && (context instanceof Activity)) {
                        ((Activity) context).onBackPressed();
                    }
                }
                break;
            case R.id.right:
                if (null != listener) {
                    listener.clickRight();
                }
                break;
            case R.id.title_right_content:
                if (null != listener) {
                    listener.clickRight();
                }
                break;
            case R.id.title_left_content:
                if (null != listener) {
                    listener.clickLeft();
                }else {
                    Context context = v.getContext();
                    if (listener == null && (context instanceof Activity)) {
                        ((Activity) context).onBackPressed();
                    }
                }
                break;
        }
    }

    public ImageView getRight_image() {
        return right_image;
    }

    public TextView getRight_text(){
        return right_text;
    }

    public TextView getLeft_text(){
        return left_text;
    }
    public TextView getTitle_text(){
        return title;
    }


}
