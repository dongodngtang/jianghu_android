package net.doyouhike.app.bbs.ui.activity.live;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.ui.activity.me.ChooseImageActivity;
import net.doyouhike.app.bbs.ui.fragment.PictureFragment;
import net.doyouhike.app.bbs.ui.widget.common.pictureview.PictureViewPager;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.StrUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看图片的activity
 *
 * @author wu-yoline
 */
public class LookPicsForChooseActivity extends FragmentActivity {

    public static final String INTENT_EXTRA_NAME_PICS_STR_ARRAY = "pic_urls";
    public static final String INTENT_EXTRA_NAME_PICS_WIDTH_ARRAY = "pic_widths";
    public static final String INTENT_EXTRA_NAME_SELECT_PICS_STR_LIST = "select_pic_urls";
    public static final String INTENT_EXTRA_NAME_AIM_INT = "open_aim";
    public static final String INTENT_EXTRA_NAME_SHOW_INDEX_INT = "show_index";

    /**
     * 确定该activity是用来作为选择图片的
     */
    public static final int OPENED_FOR_CHOOSE = 1147;

    /**
     * 确定该activity是用来单纯查看图片的
     */
    public static final int OPENED_FOR_LOOK = 1148;

    /**
     * 在EditLiveActivity中预览
     */
    public static final int OPEND_FOR_LIVE_LOOK = 1149;

    /**
     * 直接点击完成的结果码
     */
    public static final int RESULT_CODE_COMPLETE = 1514;


    /**
     * 点击"x"时的结果码
     */
    public static final int RESULT_CODE_NOT_COMPLETE = 1515;

    /**
     * 确定打开此界面的目的，有OPENED_FOR_CHOOSE或OPENED_FOR_LOOK
     */
    private int openFor = OPENED_FOR_LOOK;

    /**
     * 图片的ViewPage
     */
    private PictureViewPager vpPics;
    private LinearLayout ivBack;
    private TextView tvCount;
    private TextView tvComplete;
    private RelativeLayout rlytTop;
    private RelativeLayout rlytChoose;
    private View vButtom;
    // private LinearLayout llytNode;
    private TextView tvNode;
    private ImageView ivChoose;

    private String[] urls; // 全部要查看的图片
    private int[] picWidths;
    private List<String> selected; // 已经被选择了的图片
    // private View[] nodeViews;

    List<Fragment> fragments = new ArrayList<Fragment>();

    private int currentPicIndex = 0;
    private final int maxChooseCount = 9;
    private int imageCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        setContentView(R.layout.activity_look_pics);

        getIntentDate();

        findView();

        initView();

        System.gc();


    }


    private void getIntentDate() {
        Intent intent = this.getIntent();
        urls = intent.getStringArrayExtra(INTENT_EXTRA_NAME_PICS_STR_ARRAY);
        picWidths = intent.getIntArrayExtra(INTENT_EXTRA_NAME_PICS_WIDTH_ARRAY);
        openFor = intent
                .getIntExtra(INTENT_EXTRA_NAME_AIM_INT, OPENED_FOR_LOOK);
        currentPicIndex = intent.getIntExtra(INTENT_EXTRA_NAME_SHOW_INDEX_INT,
                0);
        selected = intent
                .getStringArrayListExtra(INTENT_EXTRA_NAME_SELECT_PICS_STR_LIST);
        imageCount = intent.getIntExtra(ChooseImageActivity.SELECT_COUNT, 0);
    }

    private void initView() {
        switch (openFor) {
            case OPENED_FOR_CHOOSE:
                // top
                initBackBtn();
                updateCountText();
                initCompleteBtn();

                // bottom
                // llytNode.setVisibility(View.GONE);
                tvNode.setVisibility(View.GONE);
                vButtom.setVisibility(View.VISIBLE);
                initChooseBtn();
                break;

            case OPENED_FOR_LOOK:
                // top
                rlytTop.setVisibility(View.GONE);

                // bottom
                // initPicNodes();
                vButtom.setVisibility(View.GONE);
                rlytChoose.setVisibility(View.GONE);
                break;
            case OPEND_FOR_LIVE_LOOK:
                // top
                initBackBtn();
                updateCountText();
                initCompleteBtn();

                // bottom
                // llytNode.setVisibility(View.GONE);
                tvNode.setVisibility(View.GONE);
                vButtom.setVisibility(View.VISIBLE);
                initChooseBtn();
                break;

        }
        initVpPics();
    }

    private void initChooseBtn() {
        if (openFor == OPENED_FOR_CHOOSE || openFor == OPEND_FOR_LIVE_LOOK) {
            rlytChoose.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (urls != null && currentPicIndex < urls.length) {
                        addOrRemovePic(urls[currentPicIndex], ivChoose);
                    }
                }
            });
        }
        updateCurrentPicChooseState(currentPicIndex);
    }

    private void updateCurrentPicChooseState(int currentIndex) {
        if (openFor == OPENED_FOR_CHOOSE || openFor == OPEND_FOR_LIVE_LOOK) {
            ivChoose.setSelected(checkIsChoose(currentIndex));
        }
    }

    private boolean checkIsChoose(int currentPicIndex) {
        if (selected != null && urls != null && currentPicIndex < urls.length
                && urls[currentPicIndex] != null) {
            for (int i = 0; i < selected.size(); i++) {
                if (urls[currentPicIndex].equals(selected.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addOrRemovePic(String path, View ivChoose) {
        Context context = LookPicsForChooseActivity.this;
        if (selected == null) {
            selected = new ArrayList<String>();
        }
        boolean isExist = selected.remove(path);
        if (!isExist) { // 需要添加
            if (imageCount < maxChooseCount) {
                selected.add(path);
                imageCount++;
                ivChoose.setSelected(true);
            } else {
                Toast.makeText(
                        context,
                        StrUtils.getResourcesStr(context,
                                R.string.can_t_more_9_pics), Toast.LENGTH_LONG)
                        .show();
            }
        } else { // 移除成功时
            ivChoose.setSelected(false);
            imageCount--;
        }
        updateCountText();
    }

    // private void initPicNodes() {
    // if (openFor == OPENED_FOR_LOOK) {
    // if (urls != null && urls.length > 1) {
    // // 点点
    // llytNode.setVisibility(View.VISIBLE);
    // llytNode.removeAllViews();
    // if (nodeViews == null) {
    // nodeViews = new View[urls.length];
    // for (int i = 0; i < nodeViews.length; i++) {
    // nodeViews[i] = new View(LookPicsForChooseActivity.this);
    // setNodeView(nodeViews[i]);
    // llytNode.addView(nodeViews[i]);
    // }
    // }
    // } else {
    // llytNode.setVisibility(View.GONE);
    // }
    // }
    // }
    //
    // private View setNodeView(View view) {
    // int height = UIUtils.getIntFromDimens(LookPicsForChooseActivity.this,
    // R.dimen.dimen_12px);
    // int width = UIUtils.getIntFromDimens(LookPicsForChooseActivity.this,
    // R.dimen.dimen_12px);
    // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
    // height);
    // params.rightMargin = UIUtils.getIntFromDimens(
    // LookPicsForChooseActivity.this, R.dimen.dimen_10px);
    // view.setLayoutParams(params);
    // view.setBackgroundResource(R.drawable.selector_pic_node_bg);
    // return view;
    //
    // }

    private void updateCountText() {
        if (openFor == OPENED_FOR_CHOOSE || openFor == OPEND_FOR_LIVE_LOOK) {
            if (selected != null && selected.size() > 0) {
                tvCount.setVisibility(View.VISIBLE);
                tvCount.setText(selected.size() + "");
            } else {
                tvCount.setVisibility(View.GONE);
            }
        }

    }

    private void initCompleteBtn() {
        if (openFor == OPENED_FOR_CHOOSE) {
            tvComplete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!selected.contains(urls[currentPicIndex]) && imageCount < 9) {

                        selected.add(urls[currentPicIndex]);
                    }

                    Intent data = new Intent();
                    data.putStringArrayListExtra(
                            INTENT_EXTRA_NAME_SELECT_PICS_STR_LIST,
                            (ArrayList<String>) selected);
                    LookPicsForChooseActivity.this.setResult(
                            RESULT_CODE_COMPLETE, data);
                    LookPicsForChooseActivity.this.finish();
                }
            });
        } else if (openFor == OPEND_FOR_LIVE_LOOK) {
            tvComplete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent data = new Intent();
                    data.putStringArrayListExtra(
                            INTENT_EXTRA_NAME_SELECT_PICS_STR_LIST,
                            (ArrayList<String>) selected);
                    LookPicsForChooseActivity.this.setResult(
                            RESULT_CODE_COMPLETE, data);
                    LookPicsForChooseActivity.this.finish();
                }
            });

        }
    }

    private void initBackBtn() {


        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goFinish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        goFinish();
    }

    private void goFinish() {
        if (openFor == OPENED_FOR_CHOOSE) {

            Intent data = new Intent();
            data.putStringArrayListExtra(
                    INTENT_EXTRA_NAME_SELECT_PICS_STR_LIST,
                    (ArrayList<String>) selected);
            LookPicsForChooseActivity.this.setResult(
                    RESULT_CODE_NOT_COMPLETE, data);
            LookPicsForChooseActivity.this.finish();

        } else if (openFor == OPEND_FOR_LIVE_LOOK) {

            LookPicsForChooseActivity.this.setResult(
                    RESULT_CODE_NOT_COMPLETE, new Intent());
            finish();
        } else {
            finish();
        }
    }

    private void initVpPics() {
        FragmentPagerAdapter adapter = createFragmentAdapter();
        if (adapter != null) {
            //设置viewpager里fragment缓存为2
            vpPics.setOffscreenPageLimit(2);
            vpPics.setAdapter(adapter);
        }
        vpPics.setCurrentItem(currentPicIndex);
        if (openFor == OPENED_FOR_LOOK) {
            updaeNodes(currentPicIndex, currentPicIndex);
        }
        vpPics.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int index) {
                if (index == -1) {
                    LogUtil.d("onPageSelected:" + index);
                    return;
                }

                if (openFor == OPENED_FOR_LOOK) {
                    updaeNodes(index, currentPicIndex);
                }
                currentPicIndex = index;

                if (openFor == OPENED_FOR_CHOOSE || openFor == OPEND_FOR_LIVE_LOOK) {
                    updateCurrentPicChooseState(index);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    /**
     * 修改图片底部的小圆点显示的位置
     *
     * @param index           前一次显示的位置
     * @param currentPicIndex 现在要显示的位置
     */
    private void updaeNodes(int currentPicIndex, int index) {
        // if (nodeViews == null) {
        // initPicNodes();
        // }
        // if (nodeViews != null && currentPicIndex < nodeViews.length
        // && index < nodeViews.length) {
        // nodeViews[index].setSelected(false);
        // nodeViews[currentPicIndex].setSelected(true);
        // }

        // cwz
        if (urls != null && currentPicIndex < urls.length
                && index < urls.length) {
            tvNode.setText((currentPicIndex + 1) + "/" + urls.length);
        }
    }


    private FragmentPagerAdapter createFragmentAdapter() {
        if (urls != null) {
            return new PhotoFragmentAdapter(getSupportFragmentManager());
        }
        return null;

    }

    /**
     * 图片适配器
     */
    class PhotoFragmentAdapter extends FragmentPagerAdapter {

        public PhotoFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return createFragment(position);
        }

        @Override
        public int getCount() {
            return urls.length;
        }
    }

    private Fragment createFragment(int index) {
        PictureFragment fragment = new PictureFragment();

        if (openFor == OPENED_FOR_LOOK) {
            int picWidth = 0;
            if (picWidths != null && index < picWidths.length) {
                picWidth = picWidths[index];
            }
            Bundle bundle = new Bundle();
            bundle.putString("picUrl", urls[index]);
            bundle.putInt("openFor", openFor);
            bundle.putInt("picWidth", picWidth);
            fragment.setArguments(bundle);
            return fragment;
        } else if (openFor == OPENED_FOR_CHOOSE || openFor == OPEND_FOR_LIVE_LOOK) {
            Bundle bundle = new Bundle();
            bundle.putString("picUrl", urls[index]);
            bundle.putInt("openFor", openFor);
            fragment.setArguments(bundle);
            return fragment;
        }
        return null;
    }

    private void findView() {
        vpPics = (PictureViewPager) findViewById(R.id.vp_pics);
        ivBack = (LinearLayout) findViewById(R.id.iv_back);
        tvCount = (TextView) findViewById(R.id.tv_count);
        tvComplete = (TextView) findViewById(R.id.tv_choose_complete);
        rlytTop = (RelativeLayout) findViewById(R.id.rlyt_top);
        // llytNode = (LinearLayout) findViewById(R.id.llyt_nodes);
        tvNode = (TextView) findViewById(R.id.tv_nodes); // cwz
        rlytChoose = (RelativeLayout) findViewById(R.id.rlyt_choose_pic);
        ivChoose = (ImageView) findViewById(R.id.iv_choose_pic);
        vButtom = findViewById(R.id.v_bottom);

        if (urls == null || urls.length <= 1) {
            tvNode.setVisibility(View.GONE);
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void finish() {
        super.finish();
        try {
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
