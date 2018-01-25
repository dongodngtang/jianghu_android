package com.hyphenate.easeui.ui;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.model.EaseImageCache;
import com.hyphenate.easeui.utils.EaseLoadLocalBigImgTask;
import com.hyphenate.easeui.widget.photoview.EasePhotoView;
import com.hyphenate.easeui.widget.photoview.PhotoViewAttacher;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.ImageUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：luochangdong on 16/7/29 11:05
 * 邮箱：2270333671@qq.com
 * 描述：
 */
public class ChatShowBigImageFragment extends Fragment {
    private static final String TAG = "ShowBigImage";

    private static String SECTRET = "secret";
    private static String REMOTEPATH = "remotepath";
    private static String LOCAL = "localUrl";

    private ProgressDialog pd;
    private EasePhotoView image;
    private int default_res = R.drawable.ease_default_image;

    private Bitmap bitmap;
    private boolean isDownloaded;
    private ProgressBar loadLocalPb;

    private String secret;
    private String remotepath;
    private String localFilePath;

    public static ChatShowBigImageFragment newInstance(String secret, String remotepath, String localUrl) {
        ChatShowBigImageFragment fragment = new ChatShowBigImageFragment();
        Bundle args = new Bundle();
        args.putCharSequence(SECTRET, secret);
        args.putCharSequence(REMOTEPATH, remotepath);
        args.putCharSequence(LOCAL, localUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            secret = getArguments().getString(SECTRET);
            remotepath = getArguments().getString(REMOTEPATH);
            localFilePath = getArguments().getString(LOCAL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ease_activity_show_big_image, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image = (EasePhotoView) view.findViewById(R.id.image);
        loadLocalPb = (ProgressBar) view.findViewById(R.id.pb_load_local);
        Uri uri = getArguments().getParcelable("uri");
        EMLog.d(TAG, "show big image uri:" + uri + " remotepath:" + remotepath);

        //show the image if it exist in local path
        if (uri != null && new File(uri.getPath()).exists()) {
            EMLog.d(TAG, "showbigimage file exists. directly show it");
            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            // int screenWidth = metrics.widthPixels;
            // int screenHeight =metrics.heightPixels;
            bitmap = EaseImageCache.getInstance().get(uri.getPath());
            if (bitmap == null) {
                EaseLoadLocalBigImgTask task = new EaseLoadLocalBigImgTask(getContext(), uri.getPath(), image, loadLocalPb, ImageUtils.SCALE_IMAGE_WIDTH,
                        ImageUtils.SCALE_IMAGE_HEIGHT);
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    task.execute();
                }
            } else {
                image.setImageBitmap(bitmap);
            }
        } else if (remotepath != null) { //download image from server
            EMLog.d(TAG, "download remote image");
            Map<String, String> maps = new HashMap<String, String>();
            if (!TextUtils.isEmpty(secret)) {
                maps.put("share-secret", secret);
            }
            downloadImage(remotepath, maps);
        } else {
            image.setImageResource(default_res);
        }

        image.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                getActivity().finish();
            }
        });
    }
    /**
     * download image
     *
     * @param remoteFilePath
     */
    @SuppressLint("NewApi")
    private void downloadImage(final String remoteFilePath, final Map<String, String> headers) {
        String str1 = getResources().getString(R.string.Download_the_pictures);
        pd = new ProgressDialog(getContext());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage(str1);
        pd.show();
        File temp = new File(localFilePath);
        final String tempPath = temp.getParent() + "/temp_" + temp.getName();
        final EMCallBack callback = new EMCallBack() {
            public void onSuccess() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new File(tempPath).renameTo(new File(localFilePath));

                        DisplayMetrics metrics = new DisplayMetrics();
                        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                        int screenWidth = metrics.widthPixels;
                        int screenHeight = metrics.heightPixels;

                        bitmap = ImageUtils.decodeScaleImage(localFilePath, screenWidth, screenHeight);
                        if (bitmap == null) {
                            image.setImageResource(default_res);
                        } else {
                            image.setImageBitmap(bitmap);
                            EaseImageCache.getInstance().put(localFilePath, bitmap);
                            isDownloaded = true;
                        }
                        if (getActivity().isFinishing() || getActivity().isDestroyed()) {
                            return;
                        }
                        if (pd != null) {
                            pd.dismiss();
                        }
                    }
                });
            }

            public void onError(int error, String msg) {
                EMLog.e(TAG, "offline file transfer error:" + msg);
                File file = new File(tempPath);
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity().isFinishing() || getActivity().isDestroyed()) {
                            return;
                        }
                        image.setImageResource(default_res);
                        pd.dismiss();
                    }
                });
            }

            public void onProgress(final int progress, String status) {
                EMLog.d(TAG, "Progress: " + progress);
                final String str2 = getResources().getString(R.string.Download_the_pictures_new);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity().isFinishing() || getActivity().isDestroyed()) {
                            return;
                        }
                        pd.setMessage(str2 + progress + "%");
                    }
                });
            }
        };

        EMClient.getInstance().chatManager().downloadFile(remoteFilePath, tempPath, headers, callback);

    }


}
