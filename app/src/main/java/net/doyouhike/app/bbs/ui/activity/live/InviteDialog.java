package net.doyouhike.app.bbs.ui.activity.live;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.util.BaseDialog;
import net.doyouhike.app.bbs.util.ShareUtil;

import butterknife.InjectView;

/**
 * 作者：luochangdong on 16/7/29 14:26
 * 邮箱：2270333671@qq.com
 * 描述：
 */
public class InviteDialog extends BaseDialog implements View.OnClickListener {
    @InjectView(R.id.tv_dialog_share_title)
    TextView tvDialogShareTitle;
    @InjectView(R.id.iv_dialog_share_progress)
    ImageView ivDialogShareProgress;
    @InjectView(R.id.ll_share_weixin)
    LinearLayout llShareWeixin;
    @InjectView(R.id.ll_share_pyq)
    LinearLayout llSharePyq;
    @InjectView(R.id.ll_share_weibo)
    LinearLayout llShareWeibo;
    @InjectView(R.id.ll_copy_link)
    LinearLayout llCopyLink;
    @InjectView(R.id.ll_share_qq)
    LinearLayout llShareQq;
    @InjectView(R.id.ll_share_qzone)
    LinearLayout llShareQzone;
    @InjectView(R.id.tv_forwarding_item)
    TextView tvForwardingItem;
    /**
     * 分享时需传递activity 的context
     */
    Context context;


    private String title = "分享一个好玩的APP";
    private String content = "磨房 - 户外与自助旅行社区，约伴活动，游记攻略，自助游必备";
    private String url = "http://www.doyouhike.net/appdownload/mofangapp/app";

    public InviteDialog(Context context) {
        super(context, R.style.dialog_full_screen_buttom_up);
        ShareUtil.init(context);
        this.context=context;
    }


    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.dialog_invite;
    }

    @Override
    protected void initViewsAndEvents() {
        llSharePyq.setOnClickListener(this);
        llCopyLink.setOnClickListener(this);
        llShareQq.setOnClickListener(this);
        llShareQzone.setOnClickListener(this);
        llShareWeibo.setOnClickListener(this);
        llShareWeixin.setOnClickListener(this);
        tvForwardingItem.setOnClickListener(this);
    }

    @Override
    protected void onFirstUserVisible() {
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        this.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_share_weixin:
                ShareUtil.shareToWeixin(context, R.drawable.ic_share_img, content, title, url);
                break;
            case R.id.ll_share_pyq:
                ShareUtil.shareToPyq(context, R.drawable.ic_share_img, content, title, url);
                break;
            case R.id.ll_share_weibo:
                ShareUtil.shareToWeibo(context, R.drawable.ic_share_img, content, title, url);
                break;
            case R.id.ll_copy_link:
                ShareUtil.copyLink(context, url);
                break;
            case R.id.ll_share_qq:
                ShareUtil.shareQQ(context, R.drawable.ic_share_img, content, title, url);
                break;
            case R.id.ll_share_qzone:
                ShareUtil.shareQZone(context, R.drawable.ic_share_img, content, title, url);
                break;
            case R.id.tv_forwarding_item:
                break;

        }
        dismiss();
    }
}
