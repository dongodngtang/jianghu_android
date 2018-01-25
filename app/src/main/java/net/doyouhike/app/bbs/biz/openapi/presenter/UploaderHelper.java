package net.doyouhike.app.bbs.biz.openapi.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;

import net.doyouhike.app.bbs.BuildConfig;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.newnetwork.Event.BaseResponseEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.nohttp.CallServer;
import net.doyouhike.app.bbs.biz.nohttp.HttpInterface;
import net.doyouhike.app.bbs.biz.nohttp.HttpListener;
import net.doyouhike.app.bbs.biz.openapi.request.upload.BaseUploaderPost;
import net.doyouhike.app.bbs.biz.openapi.request.upload.UploaderAvatarPost;
import net.doyouhike.app.bbs.biz.openapi.request.upload.UploaderPhotoPost;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.utils.ImageCompressUtils;

import java.io.File;

import de.greenrobot.event.EventBus;

/**
 * 作者：luochangdong on 16/9/28
 * 描述：
 */
public class UploaderHelper {

    private final static String TAG = UploaderHelper.class.getSimpleName();
    private static UploaderHelper instance;

    public static UploaderHelper getInstance() {
        if (instance == null)
            instance = new UploaderHelper();
        return instance;
    }


    /**
     * 头像上传
     *
     * @param context
     * @param filePath
     * @param listener
     */
    public void uploaderAvatar(Context context, String filePath, final IOnResponseListener listener) {
        UploaderAvatarPost post = new UploaderAvatarPost();
        post.setPhotoPath(filePath);
        uploaderImage(context, post, listener);
    }


    /**
     * 通用图片上传
     *
     * @param context
     * @param filePath
     * @param upload_type
     * @param listener
     */
    public synchronized void uploaderPhoto(Context context, String filePath, final String upload_type, final IOnResponseListener listener) {
        UploaderPhotoPost post = new UploaderPhotoPost();
        post.setUpload_type(upload_type);
        post.setPhotoPath(filePath);
        uploaderImage(context, post, listener);
    }

    /**
     * 上传图片服务
     *
     * @param context
     * @param post
     * @param listener
     */
    private synchronized void uploaderImage(final Context context, final BaseUploaderPost post, final IOnResponseListener listener) {
        if (StringUtil.isNotEmpty(post.getPhotoPath())) {
            File origin = new File(post.getPhotoPath());
            if (origin.exists()) {

                LogUtil.d(TAG, origin.getAbsolutePath() + "原文件:" + origin.length() / 1024 + "k");
                //并行压缩图片
                ImageCompressUtils.from(context)
                        .setFilename(origin.getName())
                        .load(origin.getAbsolutePath())
                        .execute(new ImageCompressUtils.OnCompressListener() {
                            @Override
                            public void onSuccess(File target) {
                                LogUtil.d(TAG, target.getAbsolutePath() + "压缩文件:" + target.length() / 1024 + "k");
                                executeUpload(target, post, listener);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });

            }

        }
    }


    public String getName(String path) {
        int separatorIndex = path.lastIndexOf(File.separator);
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1);
    }

    /**
     * 执行上传任务。
     *
     * @param file
     * @param post
     * @param listener
     */
    private synchronized void executeUpload(File file, BaseUploaderPost post, final IOnResponseListener listener) {

        //文件处理
        FileBinary fileBinary = new FileBinary(file, file.getName());
        /**
         * 监听上传过程，如果不需要监听就不用设置。
         * 第一个参数：what，what和handler的what一样，会在回调被调用的回调你开发者，作用是一个Listener可以监听多个文件的上传状态。
         * 第二个参数： 监听器。
         */
        fileBinary.setUploadListener(0, mOnUploadListener);

        //融合现有框架

        //Process 针对返回json的处理工具
        final IResponseProcess process = post.getProcess();
        process.setExtraTag(post.getExtraInfo());
        final BaseResponseEvent event = post.getResponseEvent();

        String url = HttpInterface.getInterface(post.getSubUrl());
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);

        request.addHeader("X-ZAITU-APP-KEY", BuildConfig.X_ZAITU_APP_KEY);
        if (StringUtil.isNotEmpty(StringUtil.getHostIp()))
            request.addHeader("X-ZAITU-CLIENT-IP", StringUtil.getHostIp());
        if (StringUtil.isNotEmpty(UserInfoUtil.getInstance().getToken()))
            request.addHeader("X-ZAITU-OPENAPI-TOKEN", UserInfoUtil.getInstance().getToken());

        //参数区分
        if (post instanceof UploaderPhotoPost) {
            UploaderPhotoPost photoPost = (UploaderPhotoPost) post;
            request.add("upload_type", photoPost.getUpload_type());
            request.add("photo_file", fileBinary);
        } else if (post instanceof UploaderAvatarPost) {
            request.add("avatar_file", fileBinary);
        }


        CallServer.getRequestInstance().add(0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, com.yolanda.nohttp.rest.Response<String> response) {
                deliveryResponse(response.get(), process, listener, event);
            }

            @Override
            public void onFailed(int what, com.yolanda.nohttp.rest.Response<String> response) {
                deliveryError(response.getException(), process, listener, event);
            }
        });

    }

    /**
     * 文件上传监听。
     */
    private OnUploadListener mOnUploadListener = new OnUploadListener() {

        @Override
        public void onStart(int what) {// 这个文件开始上传。
            LogUtil.d("开始上传");
        }

        @Override
        public void onCancel(int what) {// 这个文件的上传被取消时。
            LogUtil.d("上传取消");
        }

        @Override
        public void onProgress(int what, int progress) {// 这个文件的上传进度发生边耍
        }

        @Override
        public void onFinish(int what) {// 文件上传完成
            LogUtil.d("上传成功");
        }

        @Override
        public void onError(int what, Exception exception) {// 文件上传发生错误。
            LogUtil.d("上传发生错误:" + exception.toString());
        }
    };

    /**
     * @param result             网络响应结果
     * @param process            网络响应处理器
     * @param onResponseListener 响应监听
     * @param event
     */
    private static void deliveryResponse(final String result,
                                         final IResponseProcess process,
                                         final IOnResponseListener onResponseListener,
                                         final BaseResponseEvent event) {


        MyApplication.getInstance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {

                final Response response = process.getResponse(result);

                if (null != event) {
                    event.setResponse(response);
                    EventBus.getDefault().post(event);
                }

                Handler handler = new Handler(Looper.getMainLooper());

                if (null != onResponseListener && handler != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccess()) {
                                onResponseListener.onSuccess(response);
                            } else {
                                onResponseListener.onError(response);
                            }
                        }
                    });
                }


            }
        });

    }

    private static void deliveryError(Exception error,
                                      final IResponseProcess process,
                                      final IOnResponseListener onResponseListener,
                                      final BaseResponseEvent event) {

        Response response = process.getErrorResponse(error);

        if (null != event) {
            event.setResponse(response);
            EventBus.getDefault().post(event);
        }

        if (null != onResponseListener) {
            onResponseListener.onError(response);
        }
    }


}
