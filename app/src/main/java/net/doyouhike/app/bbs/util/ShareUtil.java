package net.doyouhike.app.bbs.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.ClipboardManager;
import android.text.TextUtils;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.BaseShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.ui.adapter.NodeTimelineAdapter;

import java.util.List;

@SuppressWarnings("deprecation")
public class ShareUtil {


    public static final int SHARE_TO_WEIXIN = 1456;
    public static final int SHARE_TO_WEIBO = 1457;
    public static final int SHARE_TO_PYQ = 1458;
    public static final int COPY_LINK = 1459;
    public static final int SHARE_TO_QQ = 1460;
    public static final int SHARE_TO_QZONE = 1461;

    private static final String DESCRIPTOR = "com.umeng.share";
    private static UMSocialService mController = UMServiceFactory
            .getUMSocialService(DESCRIPTOR);

    //QQ互联申请的APP ID
    private static String qqAppID = "1101088989";
    //在QQ互联申请的APP kEY
    private static String qqAppSecret = "UxWJkUbYk7ivo7B4";

    private static String wxAppID = "wxeb0cc0ab07a7eaf7";
    private static String wxAppSecret = "d4624c36b6795d1d99dcf0547af5443d";
    static boolean hasInited = false;


    public static void init(Context context) {
        if (hasInited) {
            return;
        }
        mController.getConfig().closeToast();
        addWeiboPlatform(context);
        addWXPlatform(context);
        addQQPlatform(context);
        addQZonePlatform(context);

        hasInited = true;
    }

    /**
     * @return
     * @功能描述 : 添加QQ空间分享平台
     */
    private static void addQZonePlatform(Context context) {
        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context,
                qqAppID, qqAppSecret);
        qZoneSsoHandler.addToSocialSDK();
    }

    /**
     * @return
     * @功能描述 : 添加QQ分享平台
     */
    private static void addQQPlatform(Context context) {

        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context,
                qqAppID, qqAppSecret);
        qqSsoHandler.addToSocialSDK();
    }

    /**
     * @return
     * @功能描述 : 添加微信平台分享
     */
    private static void addWXPlatform(Context context) {
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context, wxAppID, wxAppSecret);
        wxHandler.addToSocialSDK();
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, wxAppID,
                wxAppSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    /**
     * @return
     * @功能描述 : 添加微博平台分享
     */
    private static void addWeiboPlatform(Context context) {
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
    }

    /**
     * @param context      上下文
     * @param shareImgUrl  分享的图片的网址
     * @param shareContent 分享的文本内容
     * @param shareTitle   分享的标题
     * @param targetUrl    用户点击分享后跳转的地址
     */
    public static void shareToWeixin(final Context context, int shareImgUrl,
                                     String shareContent, String shareTitle, String targetUrl) {
        StatisticsEventUtil.ShareToWechatSession(context);
        WeiXinShareContent weixinContent = new WeiXinShareContent();

        setShareContent(weixinContent, context, shareImgUrl,
                shareContent, shareTitle, targetUrl);

        mController.setShareMedia(weixinContent);

        performShare(context, SHARE_MEDIA.WEIXIN);
    }

    public static void shareToWeixin(final Context context, String shareImgUrl,
                                     String shareContent, String shareTitle, String targetUrl) {
        StatisticsEventUtil.ShareToWechatSession(context);
        UMImage urlImage = new UMImage(context, shareImgUrl);
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(shareContent);
        weixinContent.setTitle(shareTitle);
        weixinContent.setTargetUrl(targetUrl);
        weixinContent.setShareMedia(urlImage);
        mController.setShareMedia(weixinContent);

        performShare(context, SHARE_MEDIA.WEIXIN);
    }

    /**
     * @param context      上下文
     * @param shareImgUrl  分享的图片的网址
     * @param shareContent 分享的文本内容
     * @param shareTitle   分享的标题
     * @param targetUrl    用户点击分享后跳转的地址
     */
    public static void shareToPyq(final Context context, int shareImgUrl,
                                  String shareContent, String shareTitle, String targetUrl) {
        StatisticsEventUtil.ShareToWechatTimeline(context);
        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();

        setShareContent(circleMedia, context, shareImgUrl,
                shareContent, shareTitle, targetUrl);

        mController.setShareMedia(circleMedia);

        performShare(context, SHARE_MEDIA.WEIXIN_CIRCLE);
    }

    public static void shareToPyq(final Context context, String shareImgUrl,
                                  String shareContent, String shareTitle, String targetUrl) {
        StatisticsEventUtil.ShareToWechatTimeline(context);
        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(shareContent);
        circleMedia.setTitle(shareTitle);
        UMImage urlImage = new UMImage(context, shareImgUrl);
        circleMedia.setShareMedia(urlImage);
        circleMedia.setTargetUrl(targetUrl);
        mController.setShareMedia(circleMedia);

        performShare(context, SHARE_MEDIA.WEIXIN_CIRCLE);
    }

    /**
     * @param context      上下文
     * @param shareImgUrl  分享的图片的网址
     * @param shareContent 分享的文本内容
     * @param shareTitle   分享的标题
     * @param targetUrl    用户点击分享后跳转的地址
     */
    public static void shareToWeibo(final Context context, int shareImgUrl,
                                    String shareContent, String shareTitle, String targetUrl) {
        StatisticsEventUtil.ShareToSina(context);
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        SinaShareContent sinaContent = new SinaShareContent();

        setShareContent(sinaContent, context, shareImgUrl,
                shareContent, shareTitle, targetUrl);

        mController.setShareMedia(sinaContent);

        performShare(context, SHARE_MEDIA.SINA);
    }

    /**
     * 分享到QQ好友
     *
     * @param context      上下文
     * @param shareImgUrl  分享的图片的网址
     * @param shareContent 分享的文本内容
     * @param shareTitle   分享的标题
     * @param targetUrl    用户点击分享后跳转的地址
     */
    public static void shareQQ(final Context context, int shareImgUrl,
                               String shareContent, String shareTitle, String targetUrl) {
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        QQShareContent qqShareContent = new QQShareContent();

        setShareContent(qqShareContent, context, shareImgUrl,
                shareContent, shareTitle, targetUrl);

        mController.setShareMedia(qqShareContent);

        performShare(context, SHARE_MEDIA.QQ);
    }

    /**
     * 分享到QQ空间
     *
     * @param context      上下文
     * @param shareImgUrl  分享的图片的网址
     * @param shareContent 分享的文本内容
     * @param shareTitle   分享的标题
     * @param targetUrl    用户点击分享后跳转的地址
     */
    public static void shareQZone(final Context context, int shareImgUrl,
                                  String shareContent, String shareTitle, String targetUrl) {
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        QZoneShareContent qZoneShareContent = new QZoneShareContent();

        setShareContent(qZoneShareContent, context, shareImgUrl,
                shareContent, shareTitle, targetUrl);

        mController.setShareMedia(qZoneShareContent);

        performShare(context, SHARE_MEDIA.QZONE);
    }

    // 执行分享
    private static void performShare(final Context context, SHARE_MEDIA platform) {
        mController.postShare(context, platform, new SnsPostListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode,
                                   SocializeEntity entity) {
                if (eCode == StatusCode.ST_CODE_SUCCESSED) {
//					showText += "平台分享成功";
                } else if (eCode != StatusCode.ST_CODE_ERROR_CANCEL) {
                    String showText = platform.toString();
                    showText += "平台分享失败";
                    showText += eCode;
                    StringUtil.showSnack(context, showText);
                }

            }
        });
    }

    public static void copyLink(Context context, String content) {
        ClipboardManager clip = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(content); // 复制
        StringUtil.showSnack(context, R.string.copy_success_can_to_share);
        StatisticsEventUtil.ShareUrl(context);
    }

    /**
     * 判断微信是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {

        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = null;

        if (null != mController) {
            ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        }

        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public static void setShareContent(BaseShareContent shareContent, Context context, int shareImgUrl,
                                       String strContent, String shareTitle, String targetUrl) {

        shareContent.setShareContent(strContent);
        shareContent.setTitle(shareTitle);
        UMImage urlImage = new UMImage(context, shareImgUrl);
        shareContent.setShareMedia(urlImage);
        shareContent.setTargetUrl(targetUrl);
    }

    /**
     * 获取分享标题
     *
     * @param shareTo
     * @param timeline
     * @return
     */
    public static String getShareTitle(int shareTo, NodeTimeline.ItemsBean timeline) {

        if (null == timeline) {
            return "";
        }

        NodeTimeline.ItemsBean.NodeBean.ForwardBean forward = timeline.getNode().getForward();
        String shareTitle;
        String liveTyep;
        if (forward == null) {
            liveTyep = timeline.getNode().getMinilog_type();
        } else {
            liveTyep = forward.getNode().getMinilog_type();
        }

        if (liveTyep.equals(NodeTimelineAdapter.NODE_EVENT)) {
            //活动类型
            String title = "";
            if (forward == null) {
                if (timeline.getNode().getEvent() != null &&
                        !TextUtils.isEmpty(timeline.getNode().getEvent().getTitle())) {
                    title = timeline.getNode().getEvent().getTitle();
                }
            } else {
                if (forward.getNode().getEvent() != null && !TextUtils.isEmpty(forward.getNode().getEvent().getTitle())) {
                    title = forward.getNode().getEvent().getTitle();
                }
            }

            shareTitle = getActionShareTitle(shareTo, title);
        } else if (liveTyep.equals(NodeTimelineAdapter.NODE_DISCUSSION)) {
            //帖子类型
            shareTitle = getWebPostShareTitle(shareTo, timeline);
        } else {
            //默认直播
            shareTitle = getLiveShareTitle(shareTo, timeline);
        }

        if (TextUtils.isEmpty(shareTitle)) {
            return "";
        }

        return shareTitle;
    }

    /**
     * 获取活动分享标题
     *
     * @param shareTo
     * @param title
     * @return
     */
    public static String getActionShareTitle(int shareTo, String title) {


        switch (shareTo) {
            case SHARE_TO_PYQ:
                //朋友圈
                title = "【磨房】" + title;
                break;
            case SHARE_TO_WEIBO:
                //微博
                title = "";
                break;
            default:
                //微信好友 & QQ & 空间
                title = "【磨房】" + title;
        }
        return title;
    }

    /**
     * 获取直播类标题
     *
     * @param shareTo
     * @param timeline
     * @return
     */
    private static String getLiveShareTitle(int shareTo, NodeTimeline.ItemsBean timeline) {


        String nickName = "";

        NodeTimeline.ItemsBean.NodeBean.ForwardBean forward = timeline.getNode().getForward();
        if (forward == null) {
            if (timeline.getNode().getUser() != null) {
                nickName = timeline.getNode().getUser().getNick_name();
            }
        } else {
            if (forward.getNode().getUser() != null) {
                nickName = forward.getNode().getUser().getNick_name();
            }
        }

        String title;
        switch (shareTo) {
            case SHARE_TO_PYQ:
                //朋友圈
                if (forward == null) {
                    if (timeline.getNode().getMinilog_type().equals(NodeTimelineAdapter.NODE_TEXT_PHOTO)) {
                        if (TextUtils.isEmpty(timeline.getNode().getContent().getText())) {
                            title = nickName + "发布的图片";
                        } else {
                            title = timeline.getNode().getContent().getText();
                        }
                    } else {
                        title = timeline.getNode().getContent().getText();

                    }
                } else {
                    if (forward.getNode().getMinilog_type().equals(NodeTimelineAdapter.NODE_TEXT_PHOTO)) {
                        if (TextUtils.isEmpty(forward.getNode().getContent().getText())) {
                            title = nickName + "发布的图片";
                        } else {
                            title = forward.getNode().getContent().getText();
                        }
                    } else {
                        title = forward.getNode().getContent().getText();
                    }
                }


                break;
            case SHARE_TO_WEIBO:
                //微博
                title = "";
                break;
            default:
                //微信好友 & QQ & 空间
                title = nickName + "的磨房动态";
        }
        return title;
    }

    private static String getWebPostShareTitle(int shareTo, NodeTimeline.ItemsBean timeline) {

        String title = "";
        NodeTimeline.ItemsBean.NodeBean.ForwardBean forward = timeline.getNode().getForward();

        if (forward == null) {
            if (timeline.getNode().getTopic() != null && !TextUtils.isEmpty(timeline.getNode().getTopic().getTitle())) {
                title = timeline.getNode().getTopic().getTitle();
            }
        } else {
            if (forward.getNode().getTopic() != null && !TextUtils.isEmpty(forward.getNode().getTopic().getTitle())) {
                title = forward.getNode().getTopic().getTitle();
            }
        }


        switch (shareTo) {
            case SHARE_TO_PYQ:
                //朋友圈
                title = "【磨房】" + title;
                break;
            case SHARE_TO_WEIBO:
                //微博
                title = "";
                break;
            default:
                //微信好友 & QQ & 空间
                title = "【磨房】" + title;
        }
        return title;
    }

    /**
     * 获取分享内容
     *
     * @param shareTo
     * @param timeline
     * @return
     */
    public static String getShareContent(int shareTo, NodeTimeline.ItemsBean timeline) {

        String content;
        String liveType;
        NodeTimeline.ItemsBean.NodeBean.ForwardBean forward = timeline.getNode().getForward();
        if (forward == null) {
            liveType = timeline.getNode().getMinilog_type();
        } else {
            liveType = forward.getNode().getMinilog_type();
        }

        if (liveType.equals(NodeTimelineAdapter.NODE_EVENT)) {
            //活动类型
            String title = "";

            if (forward != null) {
                if (forward.getNode().getEvent() != null && !TextUtils.isEmpty(forward.getNode().getEvent().getTitle())) {
                    title = forward.getNode().getEvent().getTitle();
                }
            } else {
                if (timeline.getNode().getEvent() != null && !TextUtils.isEmpty(timeline.getNode().getEvent().getTitle())) {
                    title = timeline.getNode().getEvent().getTitle();
                }
            }

            content = getActionShareContent(shareTo, title);
        } else if (liveType.equals(NodeTimelineAdapter.NODE_DISCUSSION)) {
            //帖子类型
            content = getWebPostShareContent(shareTo, timeline);
        } else {
            //默认直播
            if (forward == null) {
                content = timeline.getNode().getContent().getText();
                if (timeline.getNode().getMinilog_type().equals(NodeTimelineAdapter.NODE_TEXT_PHOTO)) {
                    if (TextUtils.isEmpty(content)) {
                        content = "[图片]";
                    }
                }
            } else {
                content = forward.getNode().getContent().getText();
                if (forward.getNode().getMinilog_type().equals(NodeTimelineAdapter.NODE_TEXT_PHOTO)) {
                    if (TextUtils.isEmpty(content)) {
                        content = "[图片]";
                    }
                }
            }
        }

        if (TextUtils.isEmpty(content)) {
            return "";
        }

        return content;
    }


    /**
     * 获取转发帖子分享内容
     *
     * @param shareTo
     * @param timeline
     * @return
     */
    @NonNull
    private static String getWebPostShareContent(int shareTo, NodeTimeline.ItemsBean timeline) {


        String nickName = "";

        NodeTimeline.ItemsBean.NodeBean.ForwardBean forward = timeline.getNode().getForward();
        if (forward == null) {
            if (timeline.getNode().getUser() != null) {
                nickName = timeline.getNode().getUser().getNick_name();
            }
        } else {
            if (forward.getNode().getUser() != null) {
                nickName = forward.getNode().getUser().getNick_name();
            }
        }

        String title = "";

        String content;
        if (forward == null) {
            if (timeline.getNode().getTopic() != null && !TextUtils.isEmpty(timeline.getNode().getTopic().getTitle())) {
                title = timeline.getNode().getTopic().getTitle();
            }
        } else {
            if (forward.getNode().getTopic() != null && !TextUtils.isEmpty(forward.getNode().getTopic().getTitle())) {
                title = forward.getNode().getTopic().getTitle();
            }
        }

        switch (shareTo) {
            case SHARE_TO_PYQ:
                //朋友圈
                content = "【磨房】" + title;
                break;
            case SHARE_TO_WEIBO:
                //微博
                content = "分享了一篇来自磨房的好帖《" + title + "》，详情：";
                break;
            default:
                //微信好友 & QQ & 空间
                content = nickName + "在磨房 APP 发现了一篇喜欢的好帖";
        }
        return content;
    }

    /**
     * 获取活动分享内容
     *
     * @param shareTo
     * @param title
     * @return
     */
    @NonNull
    public static String getActionShareContent(int shareTo, String title) {

        String content;

        switch (shareTo) {
            case SHARE_TO_PYQ:
                //朋友圈
                content = "【磨房】" + title;
                break;
            case SHARE_TO_WEIBO:
                //微博
                content = "在磨房 APP 发现一个好活动【" + title + "】，与有趣的人一起约伴同行。详情：";
                break;
            default:
                //微信好友 & QQ & 空间
                content = "在磨房APP，约到靠谱的队友，更多精彩等你发现";
        }
        return content;
    }

    public static String getShareContent(String autherName, String content) {

        if (null == autherName) {
            autherName = "";
        }


        if (null == content) {
            content = "";
        }


        return autherName + " : " + content;
    }
}
