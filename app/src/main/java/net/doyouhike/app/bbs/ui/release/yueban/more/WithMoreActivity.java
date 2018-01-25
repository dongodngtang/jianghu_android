package net.doyouhike.app.bbs.ui.release.yueban.more;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.release.yueban.EditEventActivity;
import net.doyouhike.app.bbs.ui.release.yueban.PresenterEvent;
import net.doyouhike.app.bbs.ui.release.yueban.dialog.LimitDialog;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.ui.widget.action.EditData;
import net.doyouhike.app.bbs.ui.widget.action.RichTextEditor;
import net.doyouhike.app.bbs.util.BitmapUtil;
import net.doyouhike.app.bbs.util.InputTools;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SaveFileUtil;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;

public class WithMoreActivity extends BaseActivity {

    public static final String TAG = "WithMoreActivity";

    private static final int REQUEST_CODE_PICK_IMAGE = 1023;

    private static final int REQUEST_CODE_PICK_TEMPLATE = 1024;
    private final static int TAKE_PHOTO_WITH_DATA = 1026;

    /**
     * 人数限制
     */
    LimitDialog limitDialog;
    /**
     * 选择图片弹窗
     */
    ActionSheetDialog sheetDialog;


    /**
     * 拍照的保存路径
     */
    String savePicUri;

    /**
     * 标题栏
     */
    @InjectView(R.id.title_act_with_more)
    TitleView titleActWithMore;
    /**
     * 人数限制
     */
    @InjectView(R.id.tv_act_with_num_limit)
    TextView tvActWithNumLimit;
    /**
     * 更多文字编辑框
     */
    @InjectView(R.id.edt_act_with_template)
    RichTextEditor edt_act_with_template;
    /**
     * 目的地
     */
    @InjectView(R.id.tv_act_with_more_destination)
    EditText tvActWithMoreDestination;


    /**
     * 选择图片
     */
    @InjectView(R.id.btn_act_with_add_photo)
    LinearLayout btn_act_with_add_photo;

    @InjectView(R.id.ll_limit_num)
    RelativeLayout llLimitNum;
    @InjectView(R.id.ll_with_more)
    LinearLayout llWithMore;
    /**
     * 约伴标题
     */
    private String mEventTitle;
    /**
     * 直播内容,大编辑框填写的内容
     */
    private EditData mEventContent;
    /**
     * 人数限制
     */
    private String mEventLimit;

    /**
     * 已参加人数
     */
    private int mJioned;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_with_more;
    }

    /**
     * 添加文字模板
     *
     */
    @OnClick(R.id.btn_act_with_add_template)
    public void addTemplate() {
        readyGoForResult(WithTemplateActivity.class, REQUEST_CODE_PICK_TEMPLATE);
    }

    /**
     * 打开系统相册
     */
    @OnClick(R.id.btn_act_with_add_photo)
    public void addPhoto() {
        InputTools.HideKeyboard(btn_act_with_add_photo);
        String[] menu = {"拍照", "从手机相册中选择"};
        sheetDialog = new ActionSheetDialog(mContext, menu, null);
        sheetDialog.title("添加图片");
        sheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        savePicUri = SaveFileUtil.getSaveImagePath(
                                mContext, false);

                        File out = new File(savePicUri);
                        Uri uri = Uri.fromFile(out);


                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, TAKE_PHOTO_WITH_DATA);
                        break;
                    case 1:
                        // 打开系统相册
                        intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");// 相片类型
                        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                        break;
                }
                sheetDialog.dismiss();
            }
        });
        sheetDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //添加模板 回调
        if (requestCode == REQUEST_CODE_PICK_TEMPLATE) {
            if (data == null)
                return;
            edt_act_with_template.insertTemplateContent(data.getStringExtra(WithTemplateActivity.R_TEMPLATE_ATTENTION),
                    data.getStringExtra(WithTemplateActivity.R_TEMPLATE_DISCLAIMER));
        }
        //打开相册回调
        else if (requestCode == REQUEST_CODE_PICK_IMAGE && null != data) {

            Uri uri = data.getData();
            insertBitmap(BitmapUtil.getRealFilePathByUri(this, uri));
        } else if (requestCode == TAKE_PHOTO_WITH_DATA) {

            Bitmap bitmap = BitmapUtil.decodeFile(savePicUri);
            if (bitmap == null) {
                return;
            }
            bitmap.recycle();
            insertBitmap(savePicUri);

        }
    }


    @Override
    protected void initViewsAndEvents() {

        if (mEventTitle != null) {
            tvActWithMoreDestination.setText(mEventTitle);
        }


        if (!TextUtils.isEmpty(mEventLimit)) {
            //人数限制
            tvActWithNumLimit.setText(mEventLimit);
        }


        titleActWithMore.setListener(new TitleView.ClickListener() {
            @Override
            public void clickLeft() {
                //返回键
                goback();
            }

            @Override
            public void clickRight() {

            }
        });

            edt_act_with_template.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            if (mEventContent != null) {
                                //文字模板赋值
                                String json = new Gson().toJson(mEventContent);
                                LogUtil.d("草稿图文数据:" + json);
                                edt_act_with_template.initData(mEventContent);
                            } else {
                                mEventContent = new EditData();
                            }
                            edt_act_with_template.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                    });


        limitDialog = new LimitDialog(this);
        limitDialog.setCallbackLimit(new LimitDialog.CallbackLimit() {
            @Override
            public void setLimit(String limit) {
                setTvLimit(limit);
            }
        });
        llLimitNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limitDialog.show();
                dialogMatch(limitDialog);
            }
        });


    }


    @Override
    public void onBackPressed() {
        goback();
    }

    private void goback() {
        Intent intent = new Intent();
        mEventTitle = tvActWithMoreDestination.getText().toString().trim();
        mEventLimit = tvActWithNumLimit.getText().toString().trim();
        if (mEventLimit.isEmpty()) {
            mEventLimit = "16";
        }
        mEventContent = edt_act_with_template.buildEditData();
        intent.putExtra(PresenterEvent.EVENT_CONTENT, mEventContent);
        intent.putExtra(PresenterEvent.EVENT_TITLE, mEventTitle);
        intent.putExtra(PresenterEvent.EVENT_LIMIT, mEventLimit);

        setResult(EditEventActivity.REQUEST_CODE_MORE, intent);
        finish();
    }

    private void setTvLimit(String limit) {
        int limitInt = Integer.parseInt(limit);
        if(limitInt >= mJioned){
            tvActWithNumLimit.setText(limit);
            mEventLimit = limit;
        }else{
            TipUtil.alert(mContext,"无法修改","人数限制小于实际已参加");
        }


    }

    private void dialogMatch(Dialog dialog) {
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    /**
     * @param extras 从编辑页面传过来的值
     */
    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        mEventTitle = extras.getString(PresenterEvent.EVENT_TITLE);
        mEventLimit = extras.getString(PresenterEvent.EVENT_LIMIT);
        mEventContent = (EditData) extras.getSerializable(PresenterEvent.EVENT_CONTENT);
        mJioned = extras.getInt(PresenterEvent.EVENT_JION,0);
    }

    @Override
    protected void onFirstUserVisible() {
        super.onFirstUserVisible();


    }


    private void insertBitmap(String imagePath) {
        LogUtil.d("imagePath=", imagePath);
        edt_act_with_template.insertImage2(imagePath);
    }

}
