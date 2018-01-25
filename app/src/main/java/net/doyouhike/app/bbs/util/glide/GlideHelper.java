package net.doyouhike.app.bbs.util.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.ui.widget.common.MaterialProgressDrawable;
import net.doyouhike.app.bbs.util.LogUtil;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-2-18
 */
public class GlideHelper {
    private final static String TAG = GlideHelper.class.getSimpleName();
    public static int DiskCacheSize = 300 * ByteConstants.MB;

    static GlideCircleTransform getGlideCircleTransform = new GlideCircleTransform(MyApplication.getInstance());
    static GlideTransformation highGlide = new GlideTransformation(MyApplication.getInstance());
    static GlideRoundTransform roundTransform = new GlideRoundTransform(MyApplication.getInstance());


    public static void displayNetImage(Context mContext, ImageView imageView,@NonNull String url, int error) {
        Glide.with(mContext)
                .load(getMidUrl(url))
                .error(error)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.color.default_image_background)
                .into(imageView);
    }

    public static void displayNetTempImage(Context mContext, ImageView imageView, String url) {
        Glide.with(mContext)
                .load(getMidUrl(url))
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    private static String getMidUrl(String url) {
        String m_url = url;
        if (url.contains(".jpg") && url.contains("http")) {
            if (url.contains("_s.jpg")) {
                m_url = url.replace("_s.jpg", "_m.jpg");
            } else if (url.contains("_b.jpg")) {
                m_url = url.replace("_b.jpg", "_m.jpg");
            } else if (url.contains("_m.jpg"))
                m_url = url;
            else
                m_url = url.replace(".jpg", "_m.jpg");
        }
        return m_url;
    }

    private static String getSmallUrl(String url) {
        String m_url = url;
        if (url.contains(".jpg") && url.contains("http")) {
            if (url.contains("_t.jpg")) {
                m_url = url;
            } else if (url.contains("_b.jpg")) {
                m_url = url.replace("_b.jpg", "_t.jpg");
            } else if (url.contains("_m.jpg"))
                m_url = url.replace("_m.jpg", "_t.jpg");
            else if (url.contains("_s.jpg"))
                m_url = url.replace("_s.jpg", "_t.jpg");
            else
                m_url = url.replace(".jpg", "_t.jpg");
        }
        return m_url;
    }

    private static String getBigUrl(String url) {
        String m_url = url;
        if (url.contains(".jpg") && url.contains("http")) {
            if (url.contains("_s.jpg")) {
                m_url = url.replace("_s.jpg", "_b.jpg");
            } else if (url.contains("_b.jpg")) {
                m_url = url;
            } else if (url.contains("_m.jpg"))
                m_url = url.replace("_m.jpg", "_b.jpg");
            else
                m_url = url.replace(".jpg", "_b.jpg");
        }
        return m_url;
    }

    /**
     * 线路图片显示
     *
     * @param mContext
     * @param imageView
     * @param url
     */
    public static void displayNetImage(Context mContext, ImageView imageView, String url) {
        Glide.with(mContext)
                .load(getMidUrl(url))
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.default_image_background)
                .into(imageView);
    }


    public static void displayChatImage(Context mContext, ImageView imageView, String url) {
        Glide.with(mContext)
                .load(url)
                .crossFade()
                .fitCenter()
                .transform(roundTransform)
                .placeholder(R.color.default_image_background)
                .into(imageView);
    }

    public static void displayNetForwardImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(getMidUrl(url))
                .crossFade()
                .centerCrop()
                .placeholder(R.drawable.ic_home_repost_link_3x)
                .error(R.drawable.ic_home_repost_link_3x)
                .into(imageView);
    }

    public static void displayAdImage(Context mContext, ImageView imageView, String url) {

        Glide.with(mContext)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .placeholder(R.color.default_image_background)
                .into(imageView);
    }

    /**
     * 可配置启动页——广告图片
     */
    public static void displayIndexAdImage(Context mContext, ImageView imageView, String url) {

        Glide.with(mContext)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .placeholder(R.drawable.start_logo)
                .into(imageView);
    }

    /**
     * 设置网络图片 带背景图片
     *
     * @param mContext
     * @param imageView
     * @param errorImge
     * @param url
     */
    public static void displayNetRoadListImage(Context mContext, ImageView imageView, int errorImge, String url) {
        Glide.with(mContext)
                .load(url)
                .crossFade()
                .placeholder(errorImge)
                .error(errorImge)
                .into(imageView);
    }


    public static void displayNetActionImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(getSmallUrl(url))
                .crossFade()
                .centerCrop()
                .placeholder(R.drawable.activity_pic)
                .error(R.drawable.activity_pic)
                .into(imageView);
    }

    public static void displayHeader(Context context, final ImageView imageView,@NonNull String url) {
        if (url.contains("none_header.gif") ||
                url.contains("none.gif")) {
            imageView.setImageResource(R.drawable.none_header);
        } else {
            Glide.with(context)
                    .load(url)
                    .transform(getGlideCircleTransform)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.none_header)
                    .into(imageView);
        }

    }

    public static void displayHeader(Context context, final ImageView imageView, Uri url) {

        Glide.with(context)
                .load(url)
                .transform(getGlideCircleTransform)
                .placeholder(R.drawable.none_header)
                .into(imageView);


    }


    public static void displayHeader(Context context, final ImageView imageView, String url, final ImageLoadFinish loadFinish) {

        if (url.contains("none_header.gif") ||
                url.contains("none.gif")) {
            imageView.setImageResource(R.drawable.none_header);
        } else {
            Glide.with(context)
                    .load(url)
                    .transform(getGlideCircleTransform)
                    .placeholder(R.drawable.none_header)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            imageView.setImageDrawable(resource);
                            loadFinish.onLoadFinish();
                        }
                    });
        }

    }

    public interface ImageLoadFinish {
        public abstract void onLoadFinish();
    }


    public static void displayLocalImage(Context mContext, ImageView imageView, String url) {
        Glide.with(mContext)
                .load(url)
                .crossFade()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }




    public static void glideClear(Context mContext) {
        LogUtil.d("glideClear");
        Glide.get(mContext).clearMemory();

    }

    public static void setBuidler(Context context) {
        GlideBuilder builder = new GlideBuilder(context);
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, 300 * ByteConstants.MB));
    }


    public class ByteConstants {
        public static final int KB = 1024;
        public static final int MB = 1024 * KB;

        private ByteConstants() {
        }
    }
}
