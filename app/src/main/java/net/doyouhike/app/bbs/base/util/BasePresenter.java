package net.doyouhike.app.bbs.base.util;

import android.content.Context;

import de.greenrobot.event.EventBus;

/**
 * presenter
 * Created by terry on 5/8/16.
 */
public class BasePresenter {

    protected Context context;

    protected void postEvent(Object object){
        EventBus.getDefault().post(object);
    }

    public BasePresenter(Context context){
        this.context = context;
    }
}
