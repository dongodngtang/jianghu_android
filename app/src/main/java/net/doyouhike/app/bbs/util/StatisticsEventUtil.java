package net.doyouhike.app.bbs.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

/**
 * 功能：友盟统计事件
 *
 * @author：曾江 日期：16-4-29.
 */
public class StatisticsEventUtil {


    private static final String TAG = StatisticsEventUtil.class.getSimpleName();
    /**
     * 新浪分享统计
     * @param context
     */
    public static void ShareToSina(Context context){
        MobclickAgent.onEvent(context, "ShareToSina");
    }

    /**
     * 发送直播统计
     * @param context
     */
    public static void PostSuccess(Context context){
        MobclickAgent.onEvent(context, "PostSuccess");
        LogUtil.d(TAG, "PostSuccess");
    }
    /**
     * 发送活动统计
     * @param context
     */
    public static void PostAction(Context context){
        MobclickAgent.onEvent(context, "PostAction");
        LogUtil.d(TAG,"PostAction");
    }
    /**
     * 转发统计
     * @param context
     */
    public static void Foward(Context context){
        MobclickAgent.onEvent(context, "Foward");
        LogUtil.d(TAG,"Foward");


    }

    /**
     * 参加活动
     * @param context
     */
    public static void JoinSuccess(Context context){
        MobclickAgent.onEvent(context, "JoinSuccess");
    }

    /**
     * 转发朋友分享统计
     * @param context
     */
    public static void ShareToWechatSession(Context context){
        MobclickAgent.onEvent(context, "ShareToWechatSession");
        LogUtil.d(TAG,"ShareToWechatSession");
    }

    /**
     * 朋友圈分享统计
     * @param context
     */
    public static void ShareToWechatTimeline(Context context){
        MobclickAgent.onEvent(context, "ShareToWechatTimeline");
        LogUtil.d(TAG,"ShareToWechatTimeline");
    }

    /**
     * 复制链接分享统计
     * @param context
     */
    public static void ShareUrl(Context context){
        MobclickAgent.onEvent(context, "ShareUrl");
    }

    /**
     * 确认成员统计
     * @param context
     */
    public static void ConfirmMember(Context context){
        MobclickAgent.onEvent(context, "ConfirmMember");
        LogUtil.d(TAG, "ConfirmMember");
    }

    /**
     * 关注事件统计
     * @param context
     */
    public static void Follow(Context context){
        MobclickAgent.onEvent(context, "Follow");
    }

    /**
     * 点赞事件统计
     * @param context
     */
    public static void Like(Context context){
        MobclickAgent.onEvent(context, "Like");
        LogUtil.d("SendReleaseUtil", "Like");
    }

    /**
     * 收藏统计
     * @param context
     */
    public static void collect(Context context) {
        MobclickAgent.onEvent(context, "Collect");
    }


    /**
     * 线路详情
     * @param context
     */
    public static void collectRouteDetail(Context context) {
        try {
            MobclickAgent.onEvent(context, "RouteDetail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 线路详情 轨迹
     * @param context
     */
    public static void collectRouteDetailMap(Context context) {
        try{
            MobclickAgent.onEvent(context, "RouteDetailMap");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
