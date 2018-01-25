package net.doyouhike.app.library.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片 处理 相关
 * Created by terry on 5/19/16.
 */
public class BaseBitmapUtil {

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */

    public static Bitmap readBitmapFromRes(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 保存Bitmap到本地
     * @param context
     * @param bmp
     * @param savePath
     * @return
     */
    public static boolean saveImageToGallery(Context context, Bitmap bmp,
                                             String savePath) {
        // 首先保存图片
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(savePath, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("LogUtil", e.getMessage());

            return false;
        } catch (IOException e) {
            Log.e("LogUtil", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 获取当前时间生成的保存路径（不包括文件名）
     *
     * @param context
     * @return
     */
    public static String getSaveImagePath(Context context) {

        String imagePath = context.getFilesDir() + "/images/";

        String sdStatus = Environment.getExternalStorageState();
        if (sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            // 可用，则指向sd卡目录
            imagePath = Environment.getExternalStorageDirectory()
                    + "/doyouhike/jianghu/";
        }

        File file = new File(imagePath);
        if (!file.exists()) {
            file.mkdirs();// 创建文件夹
        }

        return imagePath;

    }

    public static Drawable getDrawableFromRes(Context context, int resId) {
        return new BitmapDrawable(context.getResources(), readBitmapFromRes(context, resId));
    }

    public static Drawable getDrawable(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }

    public static Bitmap decodeScaleImage(String var0, int var1, int var2) {
        BitmapFactory.Options var3 = getBitmapOptions(var0);
        int var4 = calculateInSampleSize(var3, var1, var2);
        Log.d("LogUtil img", "original wid" + var3.outWidth + " original height:" + var3.outHeight + " sample:" + var4);
        Bitmap var5 = null;
        if (var4 > 0) {
            var3.inSampleSize = var4;
            var3.inJustDecodeBounds = false;
            var5 = BitmapFactory.decodeFile(var0, var3);
        } else {
            var3.inSampleSize = 1;
            var3.inJustDecodeBounds = false;
            var5 = BitmapFactory.decodeFile(var0, var3);
            var5 = big(var5, Math.abs(var4));
        }

        int var6 = readPictureDegree(var0);
        Bitmap var7 = null;
        if (var5 != null && var6 != 0) {
            var7 = rotaingImageView(var6, var5);
            var5.recycle();
            var5 = null;
            return var7;
        } else {
            return var5;
        }
    }

    private static Bitmap big(Bitmap bitmap, float bigsize) {
        Matrix matrix = new Matrix();
        matrix.postScale(bigsize, bigsize); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    public static int readPictureDegree(String var0) {
        short var1 = 0;

        try {
            ExifInterface var2 = new ExifInterface(var0);
            int var3 = var2.getAttributeInt("Orientation", 1);
            switch (var3) {
                case 3:
                    var1 = 180;
                    break;
                case 6:
                    var1 = 90;
                    break;
                case 8:
                    var1 = 270;
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return var1;
    }

    public static int calculateInSampleSize(BitmapFactory.Options var0, int var1, int var2) {
        int var3 = var0.outHeight;
        int var4 = var0.outWidth;
        int var5 = 1;
        if (var3 > var2 || var4 > var1) {
            int var6 = Math.round((float) var3 / (float) var2);
            int var7 = Math.round((float) var4 / (float) var1);
            var5 = var6 > var7 ? var6 : var7;
        } else {
            int var6 = Math.round((float) var2 / (float) var3);
            int var7 = Math.round((float) var2 / (float) var4);
            var5 = -(var6 < var7 ? var6 : var7);
        }

        return var5;
    }

    public static Bitmap rotaingImageView(int var0, Bitmap var1) {
        Matrix var2 = new Matrix();
        var2.postRotate((float) var0);
        Bitmap var3 = Bitmap.createBitmap(var1, 0, 0, var1.getWidth(), var1.getHeight(), var2, true);
        return var3;
    }

    public static BitmapFactory.Options getBitmapOptions(String var0) {
        BitmapFactory.Options var1 = new BitmapFactory.Options();
        var1.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(var0, var1);
        return var1;
    }
}
