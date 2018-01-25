
package net.doyouhike.app.bbs.base.application;

import android.app.Activity;
import android.app.Application;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

import net.doyouhike.app.bbs.BuildConfig;
import net.doyouhike.app.bbs.base.util.BaseEventBus;
import net.doyouhike.app.bbs.util.CommonUtil;
import net.doyouhike.app.bbs.util.StorageUnit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wu-yoline
 */
public class MyApplication extends Application {

    private final String TAG = MyApplication.class.getSimpleName();

    public static String serverUrl = BuildConfig.SERVER_URL;

    public final static String BASE_FILE_PATH = "/.net.douyouhike.app.bbs";

    private static MyApplication instance;

    // 创建一个线程池
    public ExecutorService executorService = Executors.newFixedThreadPool(5);

    public List<Activity> activities = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MultiDex.install(this);
        // EventBus.getDefault().register(getApplicationContext());
        //初始化数据
        NoHttp.initialize(this);
        Logger.setTag("JianghuHttp");
        Logger.setDebug(BuildConfig.DEBUG);// 开始NoHttp的调试模式, 这样就能看到请求过程和日志
//       CrashHandler.getInstance().init(this);
    }


    /**
     * @author zhulin
     * @time:2016/3/14 22:24
     */
    public void addActivity(Activity activity) {
        if (!activities.contains(activity))
            activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activities.contains(activity))
            activities.remove(activity);
    }

    public void finishActivies() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

    /**
     * @param clazz 除了这个类,其他的activity都关闭
     */
    public void finishActiviesExcept(Class<?> clazz) {
        for (Activity activity : activities) {
            if (activity.getClass().isAssignableFrom(clazz)) {
                continue;
            }
            activity.finish();
        }
    }


    /**
     * @author Hawaken
     * @time:2016/3/14 22:22
     */
    public void exitSystem() {
        finishActivies();
        System.exit(0);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    private String getImageDiscPath() {
        File cacheDir = getCacheDir();
        if (CommonUtil.ExistSDCard() && isEnoughExternalStorage()) {
            cacheDir = getExternalCacheDir();
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir.getPath();
    }


    public boolean isEnoughExternalStorage() {
        long sdFreeSize = CommonUtil.getFreeSize(getExternalCacheDir(),
                StorageUnit.UNIT_MB);
        long FreeSize = CommonUtil.getFreeSize(getCacheDir(),
                StorageUnit.UNIT_MB);
        return sdFreeSize > 100 || sdFreeSize > FreeSize;
    }


    // ----------------------网络响应回调-----------------------------//


    @Override
    public void onTerminate() {
        // 终止所有请求链接
        super.onTerminate();
    }

    public static MyApplication getInstance() {
        return instance;
    }


    public void registerEventBus(Object obj) {
        if (obj != null) {
            boolean bRegistered = BaseEventBus.isRegistered(obj);
            if (!bRegistered) {
                Log.i(TAG, obj.getClass().getSimpleName() + " register EventBus...");
                BaseEventBus.register(obj);
            }

        }
    }

    public void unregisterEventBus(Object obj) {
        if (obj != null) {
            boolean bRegistered = BaseEventBus.isRegistered(obj);
            if (bRegistered) {
                Log.i(TAG, obj.getClass().getSimpleName() + " unregister EventBus...");
                BaseEventBus.unregister(obj);
            }

        }
    }


    public void postEvent(Object event) {
        BaseEventBus.postEvent(event);
    }


    public ExecutorService getExecutorService() {
        return executorService;
    }

    public static String getServerUrl() {
        return serverUrl;
    }

    public static void setServerUrl(String serverUrl) {
        MyApplication.serverUrl = serverUrl;
    }
}
