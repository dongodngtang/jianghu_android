package net.doyouhike.app.bbs.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommonUtil {


    /**
     * 获取设备id
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        return Secure
                .getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    /**
     * 版本名称
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        PackageInfo info = null;
        PackageManager manager = context.getPackageManager();
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionName;
    }

    /**
     * 把数据变成list
     *
     * @param pics
     * @return
     */
    public static <T> List<T> ToList(T[] pics) {
        if (pics != null) {
            List<T> list = new ArrayList<T>();
            for (int i = 0; i < pics.length; i++) {
                list.add(pics[i]);
            }
            return list;
        }
        return null;
    }

    /**
     * 检查sd卡是否存在
     *
     * @return
     */
    public static boolean ExistSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取文件夹dir下的剩余空间
     *
     * @param dir
     * @param unit 返回单位
     * @return
     */
    public static long getFreeSize(File dir, StorageUnit unit) {
        if (dir != null) {
            return getFreeSize(dir.getAbsolutePath(), unit);
        } else {
            return 0L;
        }
    }

    /**
     * 获取path路径下的
     *
     * @param path
     * @param unit 返回单位
     * @return
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getFreeSize(String path, StorageUnit unit) {
        StatFs sf = new StatFs(path);
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        if (VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = sf.getBlockSizeLong();
        }
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        if (VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            freeBlocks = sf.getAvailableBlocksLong();
        }
        // 返回SD卡空闲大小
        switch (unit) {
            case UNIT_B:
                return freeBlocks * blockSize; // 单位Byte

            case UNIT_KB:
                return (freeBlocks * blockSize) / 1024; // 单位KB

            case UNIT_MB:
                return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
        }

        return freeBlocks * blockSize; // 单位Byte
    }

    /**
     * 获取sd卡剩余空间
     *
     * @param unit 返回单位
     * @return
     */
    public static long getSDFreeSize(StorageUnit unit) {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        return getFreeSize(path, unit);

    }


    public static boolean isOpenLocationService(Context context) {
        LocationManager alm = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        return alm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static <T> List<T> copyList(List<T> toThis, List<T> fromThis) {
        if (fromThis != null) {
            toThis.clear();
            for (int i = 0; i < fromThis.size(); i++) {
                toThis.add(fromThis.get(i));
            }
            return toThis;
        } else {
            return null;
        }
    }

    public static void testLog(String msg) {
        LogUtil.d("yoline_test", msg);
    }

    public static void timeLog(String msg) {
        Log.w("yoline_time", msg);
    }

    public static void requestLog(String msg) {
        Log.w("yoline_request", msg);
    }

    public static void errorLog(String msg) {
        Log.e("yoline_error", msg);
    }



}
