package net.doyouhike.app.bbs.ui.release.sendutil;

import android.content.Context;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.SendingTimeline;
import net.doyouhike.app.bbs.ui.adapter.NodeTimelineAdapter;
import net.doyouhike.app.bbs.util.LogUtil;

/**
 * 功能：
 *
 * @author：曾江 日期：16-2-29.
 */
public class SendUtil {

    private String TAG = SendUtil.class.getSimpleName();
    private static SendUtil instance;

    //    ExecutorService service = Executors.newFixedThreadPool(3);
    BaseSendEntity liveEntity;
    BaseSendEntity eventEntity;

    public static synchronized SendUtil getInstance() {

        if (null == instance) {
            initInstance();
        }

        return instance;
    }

    private static synchronized void initInstance() {
        if (null == instance) {
            instance = new SendUtil();
        }
    }

    private SendUtil() {
    }

    /**
     * 发送直播
     *
     * @param context
     * @param timeline
     */
    public synchronized void sendLive(Context context, SendingTimeline timeline) {

        LogUtil.d("SendReleaseUtil", "sendLive");
        if (null == liveEntity) {
            liveEntity = new SendLiveEntity();
        }

        liveEntity.initialize(timeline);
        sendTimeline(context, liveEntity);
    }

    /**
     * 发送约伴活动
     *
     * @param context
     * @param timeline 发送内容
     */
    public synchronized void sendAction(Context context, SendingTimeline timeline) {

        LogUtil.d("SendReleaseUtil", "sendEvent");
        if (null == eventEntity) {
            eventEntity = new SendEventEntity();
        }

        eventEntity.initialize(timeline);
        sendTimeline(context, eventEntity);

    }


    /**
     * 执行发送
     *
     * @param context    主要是为了获取路径
     * @param sendEntity 发送实体
     */
    private synchronized void sendTimeline(Context context, BaseSendEntity sendEntity) {

        sendEntity.setHasCancel(false);
        sendEntity.resetHasSend();

        if (sendEntity.getTimeline().getReleaseStatus() == Content.WAIT || sendEntity.getTimeline().getReleaseStatus() == Content.SEND_FAIL) {
            sendEntity.setSendState(Content.SENDING, Content.RELEASE_LIVE_SENDING);
            MyApplication.getInstance().getExecutorService().execute(new UploadImgTask(context, sendEntity));
        }

    }

    public void cancelLiveTask() {
        if (null != liveEntity) {
            liveEntity.setSendState(Content.SEND_FAIL, Content.RELEASE_LIVE_CANCEL);
            liveEntity.setHasCancel(true);
        }
    }

    public void cancelEventTask() {
        if (null != eventEntity) {
            eventEntity.setSendState(Content.SEND_FAIL, Content.RELEASE_LIVE_CANCEL);
            eventEntity.setHasCancel(true);
        }
    }


}
