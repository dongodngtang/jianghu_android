package net.doyouhike.app.bbs.ui.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.hyphenate.util.FileUtils;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.presenter.live.SaveImageTask;
import net.doyouhike.app.bbs.ui.activity.live.LookPicsForChooseActivity;
import net.doyouhike.app.bbs.ui.widget.BottomDialogWindow;
import net.doyouhike.app.bbs.ui.widget.common.look_photo.PhotoView;
import net.doyouhike.app.bbs.ui.widget.common.look_photo.PhotoViewAttacher.OnViewTapListener;
import net.doyouhike.app.bbs.ui.widget.me.MeUiHandle;
import net.doyouhike.app.bbs.util.CommonUtil;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.glide.GlideHelper;
import net.doyouhike.app.library.ui.uistate.UiState;
import net.doyouhike.app.library.ui.uistate.UiStateController;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看图片时图片的fragment
 *
 * @author wu-yoline
 * @create 2015-11-10
 */
public class PictureFragment extends Fragment {

    private static final String TAG = "PictureFragment";
    /**
     * 显示图片的组件
     */
    private PhotoView ivPic;
    /**
     * 图片id
     */
    private String picUrl;

    /**
     * 打开查看页面的目的
     */
    private int openFor;

    private Context context;
    /**
     * 长按保存对话框
     */
    private BottomDialogWindow bottomPopupWindow;

    private int picWidth = 0;
    /**
     * ui状态控制
     */
    protected UiStateController uiStateController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_look_pics,
                container, false);
        context = inflater.getContext();
        if (getArguments() != null) {
            picUrl = getArguments().getString("picUrl");
            picWidth = getArguments().getInt("picWidth");
            openFor = getArguments().getInt("openFor");
        }
        findView(contentView);

        initView();

        return contentView;
    }


    @Override
    public void onDestroyView() {
        Glide.clear(ivPic);
        bottomPopupWindow = null;
        ivPic = null;
        uiStateController = null;
        super.onDestroyView();
    }

    private void initView() {
        if (picUrl != null && ivPic != null) {
            final float scale = getpicScale();
            CommonUtil.testLog("scale = " + scale);

            if (openFor == LookPicsForChooseActivity.OPENED_FOR_LOOK) {
                updateView(UiState.LOADING, null);

                LogUtil.d(TAG, "图片类型:" + picUrl);

                    Glide.with(context)
                            .load(picUrl)
                            .fitCenter()
                            .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    updateView(UiState.NORMAL, null);
                                    return false;
                                }
                            })
                            .error(R.drawable.home_big_pic_fail)
                            .into(ivPic);

                ivPic.setOnViewTapListener(new OnViewTapListener() {
                    @Override
                    public void onViewTap(View view, float x, float y) {
                        getActivity().finish();
                    }
                });
                ivPic.setOnLongClickListener(new OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        // 长按保存图片
                        if (picUrl != null && picUrl.length() > 0) {
                            System.out.println("picUrl = " + picUrl);

                            // 弹出对话框
                            if (bottomPopupWindow == null) {

                                List<String> itemStrList = new ArrayList<String>();
                                List<Integer> itemColorList = new ArrayList<Integer>();
                                itemStrList.add(getResources().getString(
                                        R.string.save_pic));
                                itemColorList.add(R.color.black_word);

                                bottomPopupWindow = new BottomDialogWindow(
                                        context, itemStrList, itemColorList);

                                bottomPopupWindow
                                        .setOnItemClickListener(new OnItemClickListener() {

                                            @Override
                                            public void onItemClick(
                                                    AdapterView<?> parent,
                                                    View view, int position,
                                                    long id) {
                                                switch (position) {
                                                    case 0:
                                                        new SaveImageTask(context).execute(picUrl);

                                                        break;

                                                    default:
                                                        break;
                                                }
                                                bottomPopupWindow.dismiss();
                                            }
                                        });
                            }
                            bottomPopupWindow.show();
                        }
                        return false;
                    }
                });
            } else {
                GlideHelper.displayLocalImage(getContext(), ivPic, picUrl);
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private float getpicScale() {
        int validWidth = UIUtils.getValidWidth(context);
        CommonUtil.testLog("validWidth = " + validWidth);
        CommonUtil.testLog("picWidth = " + picWidth);
        if (picWidth > 0 && validWidth > picWidth) {
            return ((float) validWidth) / ((float) picWidth);
        }
        return 1;
    }

    private void findView(View contentView) {
        ivPic = (PhotoView) contentView.findViewById(R.id.iv_pic);
        uiStateController = new UiStateController(ivPic);
        MeUiHandle meUiHandle = new MeUiHandle(ivPic);
        meUiHandle.setTempUrl(picUrl);
        uiStateController.setUiStateHandle(meUiHandle);
    }


    /**
     * 更新ui，根据ui状态更新界面
     */
    public void updateView(UiState state, View.OnClickListener onClickListener) {
        if (null == uiStateController) {
            throw new IllegalArgumentException("You must return a right target view for ui state");
        }

        uiStateController.updateView(state, onClickListener);
    }
}
