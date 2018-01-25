/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: SaveFileUtil.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-11-07
 *
 * Changes (from 2015-11-07)
 * -----------------------------------------------------------------
 * 2015-11-07 创建SaveFileUtil.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class SaveFileUtil {

    public static String saveHeadBitmapToJPG(Context context, Bitmap bitmap) {

        // 获取这个图片的宽和高
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = (float) 100 / width;
        float scaleHeight = (float) 100 / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) width,
                (int) height, matrix, true);

        String fileName = getSaveImagePath(context, true);

        FileOutputStream fileOps = null;

        boolean isSave = false;
        try {
            fileOps = new FileOutputStream(fileName);
            isSave = newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOps);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOps.flush();
                fileOps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (isSave) {
            return fileName;
        } else {
            return null;
        }
    }

    public static String saveBitmapToJPG(Context context, Bitmap bitmap,
                                         String saveUri) {

        String fileName = saveUri;

        FileOutputStream fileOps = null;

        boolean isSave = false;
        try {
            fileOps = new FileOutputStream(fileName);
            isSave = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOps);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOps.flush();
                fileOps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (isSave) {
            return fileName;
        } else {
            return null;
        }
    }

    /**
     * 获取本地保存路径（到目录下）
     *
     * @param context
     * @param isHead
     * @return
     */
    public static String getSaveImageDirectory(Context context, boolean isHead) {
        String imagePath = context.getFilesDir() + "/images/";

        String sdStatus = Environment.getExternalStorageState();
        if (sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            // 可用，则指向sd卡目录
            imagePath = Environment.getExternalStorageDirectory()
                    + "/doyouhike/jianghu/";
        }

        if (isHead) {
            imagePath += "head/";
        }

        return imagePath;
    }

    /**
     * 获取当前时间生成的保存路径（包括文件名）
     *
     * @param context
     * @param isHead
     * @return
     */
    public static String getSaveImagePath(Context context, boolean isHead) {

        String imagePath = context.getFilesDir() + "/images/";

        String sdStatus = Environment.getExternalStorageState();
        if (sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            // 可用，则指向sd卡目录
            imagePath = Environment.getExternalStorageDirectory()
                    + "/doyouhike/jianghu/";
        }
        if (isHead) {
            imagePath += "head/";
        }

        File file = new File(imagePath);
        if (!file.exists()) {
            file.mkdirs();// 创建文件夹
        }

        // 照片的命名，目标文件夹下，以当前时间数字串为名称，即可确保每张照片名称不相同。
        String imageName = String.valueOf(new Date().getTime());

        return imagePath + imageName + ".jpg";
    }

    /*
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     *
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 递归删除文件或文件夹
     *
     * @param file            要删除的根目录或文件
     * @param isDeleteOneSelf
     */
    public static void DeleteFile(File file, boolean isDeleteOneSelf) {
        DeleteFile(file);
        if (isDeleteOneSelf == false) {
            file.mkdirs();
        }

    }

    private static void DeleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null) {
                    return;
                }

                if (childFile.length == 0) {
                    file.delete();
                    return;
                }

                for (File f : childFile) {
                    DeleteFile(f);
                }

                file.delete();
            }
        }
    }

    /**
     * 递归判断文件夹中的文件个数
     *
     * @param file 要判断的根目录
     * @return 如果是目录不存在，返回-1，如果是文件返回-2，如果是目录就返回里面文件的个数
     */
    public static int CheckFileCount(File file) {
        if (file.exists() == false) {
            return -1;
        } else {
            if (file.isFile()) {
                return -2;
            }
            if (file.isDirectory()) {
                return CheckFileCount(file, 0);
            } else {
                return -2;
            }
        }

    }

    private static int CheckFileCount(File file, int count) {

        File[] childFile = file.listFiles();

        if (childFile == null) {
            return 0;
        }

        if (childFile.length == 0) {
            return 0;
        }

        for (int i = 0; i < childFile.length; i++) {
            if (childFile[i].isDirectory()) {
                count += CheckFileCount(childFile[i], 0);
            } else {
                count++;
            }
        }

        return count;

    }

    public static boolean Copy(File oldfile, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            //File     oldfile     =     new     File(oldPath);
            if (oldfile.exists()) {
                LogUtil.d("文件大小:"+oldfile.length() / 1024 + "k");
                InputStream inStream = new FileInputStream(oldfile);
                FileOutputStream fs = new FileOutputStream(newPath);

                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }

                inStream.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
