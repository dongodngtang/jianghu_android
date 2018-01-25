/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: TailorImageActivity.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-23
 *
 * Changes (from 2015-10-23)
 * -----------------------------------------------------------------
 * 2015-10-23 创建TailorImageActivity.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.me;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UploaderHelper;
import net.doyouhike.app.bbs.biz.openapi.response.uploader.UploadAvatarResp;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.widget.common.look_photo.PhotoView;
import net.doyouhike.app.bbs.ui.widget.me.TailorImageView;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SaveFileUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import de.greenrobot.event.EventBus;

public class TailorImageActivity extends BaseActivity {

    public static final String IMAGE_PATH = "image_path";
    public static final String FROM_CHOOSE = "form_choose";
    private String imagePath;

    private Context thisContext = this;

    private TextView tailorkSubmitTv;
    private RelativeLayout tailorRlyt;
    private TailorImageView tailorImageIv;
    private PhotoView touchImageIv;

    private String fileName;

    private boolean isRequest = false;
    private boolean isFromChoose = false;

    // private float[] scopeCoord = new float[4];

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_tailor_image);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        imagePath = intent.getStringExtra(IMAGE_PATH);
        isFromChoose = intent.getBooleanExtra(FROM_CHOOSE, false);

        bindControl();

        setEffectiveCoverage();

        setListener();

    }

    @Override
    public void onBackPressed() {
        rollBack(null);
    }

    @Override
    public void rollBack(View view) {

        if (!isFromChoose) {
            finish();
            return;
        }

        Intent intent = new Intent();
        intent.setClass(this, ChooseImageActivity.class);
        intent.putExtra(ChooseImageActivity.CHOOSE_TYPE,
                ChooseImageActivity.CHOOSE_TYPE_HEAD);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        try {
            Glide.clear(touchImageIv);
            touchImageIv.destroyDrawingCache();
            touchImageIv.setImageBitmap(null);
            touchImageIv = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();

    }

    /**
     * 绑定控件
     */
    private void bindControl() {
        tailorkSubmitTv = (TextView) findViewById(R.id.tv_tailor_submit);

        tailorRlyt = (RelativeLayout) findViewById(R.id.rlyt_tailor_image);
        tailorImageIv = (TailorImageView) findViewById(R.id.iv_frame_image);
        touchImageIv = (PhotoView) findViewById(R.id.iv_tailor_image);

        if (StringUtil.isNotEmpty(imagePath)) {


            String tempPath = imagePath;
            if (imagePath.startsWith("file://")) {
                tempPath = imagePath.replace("file://", "");
            }

            LogUtil.d(this.getClass().getSimpleName(), imagePath + "    tempPath:  " + tempPath);

            Glide.clear(touchImageIv);
            GlideHelper.displayLocalImage(this, touchImageIv, tempPath);
        }
    }

    /**
     * 设置有效区域
     */
    private void setEffectiveCoverage() {
        // 获取截图部分宽、高
        // int w =
        // View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        // int h =
        // View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        // tailorRlyt.measure(w, h);
        // int width = tailorRlyt.getMeasuredWidth();
        // int height = tailorRlyt.getMeasuredHeight();
        //
        // System.out.println("MeasuredWidth="+width);
        // System.out.println("MeasuredHeight="+height);
        // scopeCoord[0] = 0;
        // scopeCoord[1] = (height-width)/2;
        // scopeCoord[2] = 0 + width;
        // scopeCoord[3] = (height-width)/2 + width;
        //
        // tailorImageIv.setScopeCoord(scopeCoord);
        // touchImageIv.setScopeCoord(scopeCoord);
    }

    /**
     * 给控件添加监听者
     */
    @SuppressLint({"ClickableViewAccessibility", "SdCardPath"})
    private void setListener() {
        tailorkSubmitTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (isRequest) {
                    return;
                }


                touchImageIv.setDrawingCacheEnabled(true);

                Bitmap bitmap = touchImageIv.getDrawingCache();
                float[] scopeCoord = tailorImageIv.getScopeCoord();
                int left = (int) scopeCoord[0];
                int top = (int) scopeCoord[1];
                int right = (int) scopeCoord[2];
                int bottom = (int) scopeCoord[3];

                if (bitmap != null) {
                    bitmap = Bitmap.createBitmap(bitmap, left, top, right
                            - left, bottom - top);

                    // 本地保存
                    fileName = SaveFileUtil.saveHeadBitmapToJPG(
                            TailorImageActivity.this, bitmap);
                    bitmap.recycle();

                    if (fileName != null) {
                        // 调用接口
                        makeToast(R.string.upload_hrad_image_toast);
                        UploaderHelper.getInstance().uploaderAvatar(thisContext, fileName, listener);
                        isRequest = true;
                        tailorkSubmitTv.setClickable(false);
                        tailorkSubmitTv.setTextColor(getResources().getColor(
                                R.color.unclickable));
                    }

                }
            }
        });
    }

    IOnResponseListener<Response<UploadAvatarResp>> listener = new IOnResponseListener<Response<UploadAvatarResp>>() {
        @Override
        public void onSuccess(Response<UploadAvatarResp> response) {

            finish();
        }

        @Override
        public void onError(Response response) {
            makeToast(response.getMsg());
            isRequest = false;
            tailorkSubmitTv.setClickable(true);
            tailorkSubmitTv
                    .setTextColor(getResources().getColor(R.color.clickable));
        }
    };


}
