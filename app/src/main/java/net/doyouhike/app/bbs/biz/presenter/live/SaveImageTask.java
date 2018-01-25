package net.doyouhike.app.bbs.biz.presenter.live;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.hyphenate.util.FileUtils;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.util.ImageUtil;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SaveFileUtil;
import net.doyouhike.app.bbs.util.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 作者：luochangdong on 16/8/27
 * 描述：
 */
public class SaveImageTask extends AsyncTask<String, Void, File> {
    private
    final Context context;

    public SaveImageTask(Context context) {
        this.context = context;
    }

    @Override
    protected File doInBackground(String... params) {
        String url = params[0]; // should be easy to extend to share multiple images at once
        LogUtil.d("SaveImage Url"+url);
        try {
            return Glide
                    .with(context)
                    .load(url)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get(); // needs to be called on background thread

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(File result) {
        if (result == null) {
            return;
        }

        String path = result.getAbsolutePath();
        LogUtil.d("SaveImage 路径:"+path+"  大小:"+result.length()+" 类型:"+ FileUtils.getMIMEType(result));
        String fileName = System.currentTimeMillis() + ".jpg";
        String newPath = ImageUtil
                .getSaveImagePath(context)+fileName;
        boolean isSuccess = SaveFileUtil.Copy(result,newPath);

        if (isSuccess) {
            StringUtil.showSnack(
                    context,
                    R.string.save_to_gallery);
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        newPath, fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse("file://" + newPath)));
        } else {
            StringUtil.showSnack(
                    context,
                    R.string.save_fail_check_stoge);
        }
    }
}