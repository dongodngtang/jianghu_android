package net.doyouhike.app.bbs.base.util;

import android.util.Log;

import de.greenrobot.event.EventBus;

/**
 * Created by Hawaken on 2016/3/17.
 */
public class BaseEventBus {
    private static final String TAG = BaseEventBus.class.getSimpleName();

    public BaseEventBus() {
    }

    public static void register(Object obj) {
        if(obj != null) {
            boolean bRegistered = EventBus.getDefault().isRegistered(obj);
            if(!bRegistered) {
                Log.i(TAG, obj.getClass().getSimpleName() + " register EventBus...");
                EventBus.getDefault().register(obj);
            }

        }
    }

    public static void unregister(Object obj) {
        if(obj != null) {
            boolean bRegistered = EventBus.getDefault().isRegistered(obj);
            if(bRegistered) {
                Log.i(TAG, obj.getClass().getSimpleName() + " unregister EventBus...");
                EventBus.getDefault().unregister(obj);
            }

        }
    }

    public static boolean isRegistered(Object obj) {
        boolean bRegistered = EventBus.getDefault().isRegistered(obj);
        return bRegistered;
    }

    public static void postEvent(Object event) {
        EventBus.getDefault().post(event);
    }
}
