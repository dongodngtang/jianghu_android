/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ChooseImageActivity.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-11-07
 *
 * Changes (from 2015-11-07)
 * -----------------------------------------------------------------
 * 2015-11-07 创建ChooseImageActivity.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.me;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.activity.live.LookPicsForChooseActivity;
import net.doyouhike.app.bbs.ui.adapter.live.ChooseImageAdapter;
import net.doyouhike.app.bbs.ui.adapter.live.ChooseImageAdapter.OnSelectPicListener;
import net.doyouhike.app.bbs.ui.home.ImageFloder;
import net.doyouhike.app.bbs.ui.widget.ListImageDirPopupWindow;
import net.doyouhike.app.bbs.util.ImageUtil;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SaveFileUtil;
import net.doyouhike.app.bbs.util.StringUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ChooseImageActivity extends BaseActivity implements ListImageDirPopupWindow.OnImageDirSelected {

    public static final String CHOOSE_TYPE = "choose_type";
    public static final String INTENT_EXTRA_SELECTED_PICS_STR_ARRAY = "selected_pics";
    public static final String SELECT_COUNT = "SELECT_COUNT";
    public static final int REQUEST_CODE_TO_LOOK_PIC = 1503;

    public static final int RESULT_CODE_SELECT_PICS = 1600;

    public static final int CHOOSE_TYPE_POST = 0;
    public static final int CHOOSE_TYPE_HEAD = 1;

    private GridView cImageGv;
    private ChooseImageAdapter ciAdapter;

    private TextView cCountTv;
    private TextView cComplete;
    private TextView tv_title;

    private List<String> pathsList = new ArrayList<>();
    private int chooseType;
    private List<String> selectedPics = new ArrayList<>();

    private String savePicUri;
    private RelativeLayout ll_select_img;
    private ImageView iv_select_img;
    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
    public int firstCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_choose_image);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        chooseType = intent.getIntExtra(CHOOSE_TYPE, CHOOSE_TYPE_HEAD);

        getIntentDate(intent);

        bindControl();

        setListener();

        getImages();

    }

    private void getIntentDate(Intent intent) {

        firstCount = intent
                .getIntExtra(INTENT_EXTRA_SELECTED_PICS_STR_ARRAY, 0);
    }

    /**
     * 绑定控件
     */
    private void bindControl() {
        cImageGv = (GridView) findViewById(R.id.gv_choose_image);
        tv_title = (TextView) findViewById(R.id.tv_title);
        cCountTv = (TextView) findViewById(R.id.tv_choose_count);
        cComplete = (TextView) findViewById(R.id.tv_choose_complete);
        ll_select_img = (RelativeLayout) findViewById(R.id.ll_select_img);
        cCountTv.setVisibility(View.GONE);
        if (chooseType == CHOOSE_TYPE_HEAD) {
            cComplete.setVisibility(View.GONE);
        }
        iv_select_img = (ImageView) findViewById(R.id.iv_select_img);
    }

    /**
     * 给控件添加监听者
     */
    private void setListener() {
        cImageGv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                if (chooseType == CHOOSE_TYPE_HEAD) {
                    // 头像
                    String chooseImagePath = pathsList.get(position);

                    Intent intent = new Intent();
                    intent.setClass(ChooseImageActivity.this,
                            TailorImageActivity.class);
                    intent.putExtra(TailorImageActivity.IMAGE_PATH, chooseImagePath);
                    intent.putExtra(TailorImageActivity.FROM_CHOOSE, true);

                    startActivity(intent);
                    ChooseImageActivity.this.finish();
                    System.gc();

                    return;
                }


                if (position != 0) {
                    // 图片项
                    if (chooseType == CHOOSE_TYPE_POST) {
                        Intent intent = getToLookIntent(position);
                        startActivityForResult(intent, REQUEST_CODE_TO_LOOK_PIC);
                    }
                } else {
                    if (chooseType == CHOOSE_TYPE_POST) {
                        Intent intent = getToLookIntent(position);
                        startActivityForResult(intent, REQUEST_CODE_TO_LOOK_PIC);
                    } else {
                        // 相机项
                        savePicUri = SaveFileUtil.getSaveImagePath(
                                ChooseImageActivity.this, false);

                        File out = new File(savePicUri);
                        Uri uri = Uri.fromFile(out);

                        LogUtil.d("uri.getPath() = " + uri.getPath());

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, 1);
                    }

                }
            }

        });

        cComplete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (chooseType == CHOOSE_TYPE_POST) {

                    if (selectedPics != null) {
                        Intent intent = new Intent();
                        intent.putStringArrayListExtra(INTENT_EXTRA_SELECTED_PICS_STR_ARRAY,
                                (ArrayList<String>) selectedPics);
                        setResult(RESULT_CODE_SELECT_PICS, intent);
                        finish();
                    }
                }
            }
        });

        ll_select_img.setOnClickListener(new

                                                 OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         mListImageDirPopupWindow.showAsDropDown(v, 0, 0);
                                                         iv_select_img.setImageResource(R.drawable.ic_home_topic_up);
                                                     }
                                                 });
    }

    private Intent getToLookIntent(int position) {
        Intent intent = new Intent(ChooseImageActivity.this,
                LookPicsForChooseActivity.class);
        if (pathsList != null) {
            intent.putExtra(
                    LookPicsForChooseActivity.INTENT_EXTRA_NAME_PICS_STR_ARRAY,
                    pathsList.toArray(new String[pathsList.size()]));
        }
        intent.putExtra(LookPicsForChooseActivity.INTENT_EXTRA_NAME_AIM_INT,
                LookPicsForChooseActivity.OPENED_FOR_CHOOSE);
        intent.putExtra(
                LookPicsForChooseActivity.INTENT_EXTRA_NAME_SHOW_INDEX_INT,
                position);
        if (selectedPics != null) {
            intent.putStringArrayListExtra(
                    LookPicsForChooseActivity.INTENT_EXTRA_NAME_SELECT_PICS_STR_LIST,
                    (ArrayList<String>) selectedPics);
            intent.putExtra(SELECT_COUNT, firstCount);

        }
        return intent;
    }

    // 拍照返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            // TODO 低版本可能会空指针
            Bitmap bitmap = ImageUtil.getSmallBitmap(savePicUri, 1000, 1000);
            if (bitmap == null) {
                return;
            }
            // 旋转
            Matrix matrix = new Matrix();
            matrix.postRotate(SaveFileUtil.readPictureDegree(savePicUri));
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);


            SaveFileUtil.saveBitmapToJPG(this, bitmap, savePicUri);

            if (bitmap != null) {
                bitmap.recycle();
//                pathsList.add(0, "file://" + savePicUri);
                pathsList.add(0, savePicUri);
                if (chooseType == CHOOSE_TYPE_POST) {
                    ciAdapter.addPisPata(savePicUri);
                }
                refreshResource();
            }

        } else if (requestCode == REQUEST_CODE_TO_LOOK_PIC) {
            if (resultCode == LookPicsForChooseActivity.RESULT_CODE_COMPLETE) {
                if (data != null) {
                    List<String> selected = data
                            .getStringArrayListExtra(LookPicsForChooseActivity.INTENT_EXTRA_NAME_SELECT_PICS_STR_LIST);
                    if (selected != null) {
                        Intent data2 = new Intent();
                        data2.putStringArrayListExtra(INTENT_EXTRA_SELECTED_PICS_STR_ARRAY,
                                (ArrayList<String>) selected);
                        ChooseImageActivity.this.setResult(
                                RESULT_CODE_SELECT_PICS, data2);
                    }
                }
                finish();
            } else if (resultCode == LookPicsForChooseActivity.RESULT_CODE_NOT_COMPLETE) {
                if (data != null) {
                    List<String> strList = data
                            .getStringArrayListExtra(LookPicsForChooseActivity.INTENT_EXTRA_NAME_SELECT_PICS_STR_LIST);
                    int tempCount = strList.size() - selectedPics.size();

                    selectedPics = strList;
                    ciAdapter.setSelected(selectedPics);
                    ciAdapter.notifyDataSetChanged();
//                    if (selectedPics != null) {
//                        setCCountTv(selectedPics.size());
//                    } else {
//                        setCCountTv(0);
//                    }
                    firstCount = firstCount + tempCount;
                    setCCountTv(firstCount);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 刷新图片列表的显示
     */
    private void refreshResource() {
        if (ciAdapter == null) {
            ciAdapter = new ChooseImageAdapter(this, pathsList, chooseType, selectedPics);
            ciAdapter.setOnSelectPicListener(new OnSelectPicListener() {
                @Override
                public void onUnselected(String path, int count) {
                    setCCountTv(count);
                }

                @Override
                public void onSelected(String path, int count) {
                    setCCountTv(count);
                }
            });
            cImageGv.setAdapter(ciAdapter);
        } else {
            ciAdapter.notifyDataSetChanged();
        }


        setCCountTv(firstCount);

    }

    public void setCCountTv(int count) {
        if (count > 0) {
            cCountTv.setVisibility(View.VISIBLE);
            cCountTv.setText(count + "");
        } else {
            cCountTv.setVisibility(View.GONE);
        }
    }


    private ProgressDialog mProgressDialog;

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private List<String> mImgs;

    private List<String> mAllImages;

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<>();

    int totalCount = 0;
    private ListImageDirPopupWindow mListImageDirPopupWindow;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            // 为View绑定数据
            data2View();
            // 初始化展示文件夹的popupWindw
            initListDirPopupWindw();
        }
    };

    /**
     * 为View绑定数据
     */
    private void data2View() {
        if (mImgDir == null) {
            StringUtil.showSnack(getApplicationContext(), "擦，一张图片没扫描到");
            return;
        }

        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */

        pathsList.clear();
        orderByDate(mImgDir.getAbsolutePath());
        tv_title.setText(mImgDir.getName());
        refreshResource();

    }

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                mImageFloders, LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.list_dir, null));
        mListImageDirPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iv_select_img.setImageResource(R.drawable.nav_icon_indicator_expand);
            }
        });

        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            StringUtil.showSnack(this, "暂无外部存储");
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = ChooseImageActivity.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=? or "+ MediaStore.Images.Media.MIME_TYPE + "=? ",
                        new String[]{"image/jpeg", "image/png", "image/jpg"}, MediaStore.Images.Media.DATE_MODIFIED + " desc");

                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder = new ImageFloder();
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }


                    String[] list = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            return filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg");
                        }
                    });
                    if (list == null)
                        continue;
                    int picSize = list.length;
                    totalCount += picSize;

                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();

    }

    @Override
    public void selected(ImageFloder floder) {

        pathsList.clear();
        orderByDate(floder.getDir());
        tv_title.setText(floder.getName());
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */

        refreshResource();
        mListImageDirPopupWindow.dismiss();
    }

    /**
     * 根据文件夹 按时间排序 NewToOld
     *
     * @param fliePath
     */
    public void orderByDate(String fliePath) {
        File file = new File(fliePath);
        File[] fs = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".jpg") || filename.endsWith(".png")
                        || filename.endsWith(".jpeg");
            }
        });
        Arrays.sort(fs, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f2.lastModified() - f1.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;
            }


        });

        for (File item : fs) {
            pathsList.add(item.getAbsolutePath());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
