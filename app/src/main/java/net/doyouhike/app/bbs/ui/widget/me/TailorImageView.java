/*
* -----------------------------------------------------------------
* Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
* -----------------------------------------------------------------
*
* File: TailorImageView.java
* Author: ChenWeiZhen
* Version: 1.0
* Create: 2015-10-23
*
* Changes (from 2015-10-23)
* -----------------------------------------------------------------
* 2015-10-23 创建TailorImageView.java (ChenWeiZhen);
* -----------------------------------------------------------------
*/
package net.doyouhike.app.bbs.ui.widget.me;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.util.BitmapUtil;

public class TailorImageView extends View {

    private float frameX;
    private float frameY;
    private float frameWidth;
    private float frameHeight;

    public TailorImageView(Context context) {
        super(context);
    }

    public TailorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TailorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (frameWidth == 0 && frameHeight == 0) {
            frameX = 0;
            frameY = (getHeight() - getWidth()) / 2;
            frameWidth = getWidth();
            frameHeight = getWidth();
        }

        int saveFlags = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
                | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                | Canvas.CLIP_TO_LAYER_SAVE_FLAG;
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, saveFlags);

        canvas.drawARGB(204, 0, 0, 0);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

//        Bitmap bitmap = BitmapUtil.readBitmapFromRes(getContext(), R.drawable.crop_image_frame_3x);
//
//        float scaleWidth = ((float) frameWidth) / bitmap.getWidth();
//        float scaleHeight = ((float) frameWidth) / bitmap.getHeight();
//
//        Matrix matrix = new Matrix();
//        matrix.postScale(scaleWidth, scaleHeight);
//
//        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//                bitmap.getHeight(), matrix, true);
//        canvas.drawBitmap(bitmap, frameX, frameY, paint);

        paint.setColor(Color.WHITE);
        PorterDuffXfermode xfermode = new PorterDuffXfermode(
                PorterDuff.Mode.DST_OUT);
        paint.setXfermode(xfermode);
        canvas.drawCircle(frameX + frameWidth / 2, frameY + frameWidth / 2,
                frameWidth / 2, paint);

        super.onDraw(canvas);
    }

    /**
     * 获取有效范围的  left, top, right, bottom
     */
    public float[] getScopeCoord() {
        float[] scopeCoord = new float[4];
        scopeCoord[0] = frameX;
        scopeCoord[1] = frameY;
        scopeCoord[2] = frameX + frameWidth;
        scopeCoord[3] = frameY + frameWidth;

        return scopeCoord;
    }

    /**
     * 设置有效范围的  left, top, right, bottom
     */
    public void setScopeCoord(float[] scopeCoord) {
        frameX = scopeCoord[0];
        frameY = scopeCoord[1];
        frameWidth = scopeCoord[2] - scopeCoord[0];
        frameHeight = scopeCoord[3] - scopeCoord[1];
        invalidate();
    }
}
