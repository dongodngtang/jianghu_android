package net.doyouhike.app.bbs.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 作者:luochangdong on 16/6/21 18:16
 * 描述:
 */
public class GlideTransformation extends BitmapTransformation {


    public GlideTransformation(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        // If no matching Bitmap is in the pool, get will return null, so we should allocate.
        if (result == null) {
            // Use ARGB_8888 since we're going to add alpha to the image.
            result = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        }
        // Create a Canvas backed by the result Bitmap.
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAlpha(128);
        // Draw the original Bitmap onto the result Bitmap with a transformation.
        canvas.drawBitmap(toTransform, 0, 0, paint);
        // Since we've replaced our original Bitmap, we return our new Bitmap here. Glide will
        // will take care of returning our original Bitmap to the BitmapPool for us.
        return result;
    }

    @Override
    public String getId() {
        return GlideTransformation.class.getPackage().getName();
    }

}
