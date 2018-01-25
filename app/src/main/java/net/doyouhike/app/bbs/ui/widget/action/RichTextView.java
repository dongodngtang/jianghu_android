package net.doyouhike.app.bbs.ui.widget.action;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.common.RichTextContent;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventDetailResp;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.util.BitmapUtil;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.linktext.linkutil.LinkTextUtil;

import java.util.List;

/**
 * 富文本 显示
 */
@SuppressLint({"NewApi", "InflateParams"})
public class RichTextView extends LinearLayout {

    private final String TAG = RichTextView.class.getSimpleName();

    private int viewTagIndex = 1; // 新生的view都会打一个tag，对每个view来说，这个tag是唯一的。
    //    private LinearLayout allLayout; // 这个是所有子view的容器，scrollView内部的唯一一个ViewGroup
    private LayoutInflater inflater;
    //private LayoutTransition mTransitioner; // 只在图片View添加或remove时，触发transition动画
    private int disappearingImageIndex = 0;

    public RichTextView(Context context) {
        this(context, null);
    }

    public RichTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = LayoutInflater.from(context);

        // 1. 初始化allLayout
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(Color.WHITE);
        //setupLayoutTransitions();
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
//        addView(allLayout, layoutParams);
        setLayoutParams(layoutParams);


        LinearLayout.LayoutParams firstEditParam = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        TextView firstEdit = createTextView("", 0);
        addView(firstEdit, firstEditParam);

    }

    /**
     * 生成文本输入框
     */
    private TextView createTextView(String hint, int paddingTop) {
        TextView editText = (TextView) inflater.inflate(R.layout.widget_action_detail_more_edit_item_view,
                null);
        editText.setTag(viewTagIndex++);
        editText.setHint(hint);
        editText.setLinksClickable(true);
        return editText;
    }

    /**
     * 生成图片View
     */
    private RelativeLayout createImageLayout() {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
                R.layout.widget_action_detail_more_edit_imageview, null);
        layout.setTag(viewTagIndex++);
        View closeView = layout.findViewById(R.id.image_close);
        closeView.setVisibility(View.GONE);

        return layout;
    }

    /**
     * 根据绝对路径添加view
     *
     * @param imagePath
     */
    public void insertImage(String imagePath) {
        addImageViewAtIndex(imagePath);
    }


    /**
     * 在特定位置插入EditText
     * index   位置
     *
     * @param editStr EditText显示的文字
     */
    private void addTextViewAtIndex(String editStr) {
        TextView editText2 = createTextView("", dip2px(5));
        //SpannableString sp = new SpannableString(editStr);
        if (StringUtil.isNotEmpty(editStr.trim())) {
            editText2.setMovementMethod(LinkMovementMethod.getInstance());
            SpannableStringBuilder spannable = new LinkTextUtil().getSpannableString(editStr);
            editText2.setText(spannable);
        } else
            editText2.setText(editStr);

        //editText2.setInputType(InputType.TYPE_NULL);
        // editText2.setText(editStr);
        // editText2.setEnabled(false);
        //editText2.setLinksClickable(true);
        // 请注意此处，EditText添加、或删除不触动Transition动画
        setLayoutTransition(null);
        addView(editText2);
        //allLayout.setLayoutTransition(mTransitioner); // remove之后恢复transition动画
    }

    /**
     * dp和pixel转换
     *
     * @param dipValue dp值
     * @return 像素值
     */
    public int dip2px(float dipValue) {
        float m = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    /**
     * 在特定位置添加ImageView
     */
    private void addImageViewAtIndex(
            final String imagePath) {
        final RelativeLayout imageLayout = createImageLayout();
        final DataImageView imageView = (DataImageView) imageLayout
                .findViewById(R.id.edit_imageView);
        imageView.setAbsolutePath(imagePath);

        Glide.with(getContext())
                .load(imagePath)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.home_big_pic_fail_white)
                .placeholder(R.color.default_image_background)
                .fitCenter()
                .into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        if (imageLayout != null && imageView != null && resource != null) {
                            imageView.setImageBitmap(resource);
                            imageView.setBitmap(resource);
                            // 调整imageView的高度 等比例缩放
                            int imageHeight = getWidth() * resource.getHeight() / resource.getWidth();

                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                                    LayoutParams.MATCH_PARENT, imageHeight);
                            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                            lp.setMargins(0, dip2px(5), 0, dip2px(5));
                            imageView.setLayoutParams(lp);
                            setLayoutTransition(null);
                            requestLayout();
                        }

                    }
                });

        addView(imageLayout);

    }

    private void addPicture(Bitmap resource, String imagePath) {
        RelativeLayout imageLayout = createImageLayout();
        DataImageView imageView = (DataImageView) imageLayout
                .findViewById(R.id.edit_imageView);
        imageView.setImageBitmap(resource);
        imageView.setBitmap(resource);
        imageView.setAbsolutePath(imagePath);
        // 调整imageView的高度 等比例缩放
        int imageHeight = getWidth() * resource.getHeight() / resource.getWidth();

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, imageHeight);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imageView.setLayoutParams(lp);
        setLayoutTransition(null);
        addView(imageLayout);
    }

    /**
     * 根据view的宽度，动态缩放bitmap尺寸
     *
     * @param width view的宽度
     */
    private Bitmap getScaledBitmap(String filePath, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int sampleSize = options.outWidth > width ? options.outWidth / width
                + 1 : 1;
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        return BitmapUtil.decodeFile(filePath, options);
    }

    /**
     * 初始化transition动画
     */
    /*private void setupLayoutTransitions() {
		mTransitioner = new LayoutTransition();
		allLayout.setLayoutTransition(mTransitioner);
		mTransitioner.addTransitionListener(new TransitionListener() {

			@Override
			public void startTransition(LayoutTransition transition,
					ViewGroup container, View view, int transitionType) {

			}

			@Override
			public void endTransition(LayoutTransition transition,
					ViewGroup container, View view, int transitionType) {
				if (!transition.isRunning()
						&& transitionType == LayoutTransition.CHANGE_DISAPPEARING) {
					// transition动画结束，合并EditText
					// mergeEditText();
				}
			}
		});
		mTransitioner.setDuration(300);
	}*/

    /**
     * 初始化 data
     *
     * @param list
     */
    public void initData(List<EventDetailResp.ContentBean> list) {
        if (null != list && list.size() > 0) {
            int size = list.size();
            removeAllViews();
            for (int i = 0; i < size; i++) {

                if (list.get(i).getType().equals(Constant.TXET)) {
                    addTextViewAtIndex(list.get(i).getContent());

                } else if (list.get(i).getType().equals(Constant.IMAGE))
                    insertImage(list.get(i).getWhole_photo_path());


            }

        }

    }

}
