package net.doyouhike.app.bbs.ui.fragment.start;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.fragment.BaseFragment;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.AccountHelper;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventBannersResp;
import net.doyouhike.app.bbs.biz.openapi.response.events.StartAdBannerResp;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.ui.activity.action.ActionDetailActivity;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.widget.common.webview.BaseWebViewActivity;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.List;
import java.util.Random;

import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdStartFragment extends BaseFragment {


    @InjectView(R.id.iv_ad_content)
    ImageView iv_ad_content;

    public AdStartFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onFirstUserVisible() {

        setSystemBarTintDrawable(-1);
        //获取广告相关
        AccountHelper.getInstance().getAdLoading(mContext, adListener);

//        3s后跳转主页或登录页
        iv_ad_content.postDelayed(gotoActivityTask, 3000);
    }

    IOnResponseListener<Response<StartAdBannerResp>> adListener = new IOnResponseListener<Response<StartAdBannerResp>>() {
        @Override
        public void onSuccess(Response<StartAdBannerResp> response) {
            if(response.getData().getAd_result() != null)
                showAd(response.getData().getAd_result());
        }

        @Override
        public void onError(Response response) {

        }
    };

    @Override
    protected void initViewsAndEvents() {

        iv_ad_content.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EventBannersResp.ItemsBean adData = (EventBannersResp.ItemsBean) v.getTag(R.id.image_tag);

                if (null == adData) {
                    return;
                }
                gotoAdDetail(v, adData);
            }

        });


    }

    @Override
    public void onDestroyView() {
        iv_ad_content.removeCallbacks(gotoActivityTask);
        super.onDestroyView();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_ad_start;
    }


    private Runnable gotoActivityTask = new Runnable() {
        @Override
        public void run() {
            gotoActivity();
        }
    };

    /**
     * 登录,跳转主页,未登陆跳转登陆页
     */
    private void gotoActivity() {
        if (UserInfoUtil.getInstance().isLogin()) {
            // 登录,跳转主页
            readyGo(MainActivity.class);
        } else {
            //未登陆,跳转登陆页
            readyGo(LoginActivity.class);
        }
        getActivity().finish();
    }

    /**
     * 跳转广告细节
     *
     * @param v      图片
     * @param adData 广告详情
     */
    private void gotoAdDetail(View v, EventBannersResp.ItemsBean adData) {
        if ("mf_link".equals(adData.getLink_type()) || "link".equals(adData.getLink_type())) {
            //链接类型
            v.removeCallbacks(gotoActivityTask);

            //链接类型
            Intent webIntent = new Intent(getActivity(), BaseWebViewActivity.class);
            webIntent.putExtra(BaseWebViewActivity.I_URL, adData.getLink_url());
            getActivity().startActivities(new Intent[]{getGotoIntent(), webIntent});
            getActivity().finish();
        } else if ("4".equals(adData.getNode_type()) || "node".equals(adData.getLink_type())) {
            //活动类型
            v.removeCallbacks(gotoActivityTask);

            Intent actionIntent = new Intent(getActivity(), ActionDetailActivity.class);
            actionIntent.putExtra("nodeId", adData.getLink_url());
            getActivity().startActivities(new Intent[]{getGotoIntent(), actionIntent});
            getActivity().finish();
        }
    }


    private Intent getGotoIntent() {
        if (UserInfoUtil.getInstance().isLogin()) {
            // 登录,主页
            return new Intent(getActivity(), MainActivity.class);
        } else {
            //未登陆,登陆页
            return new Intent(getActivity(), LoginActivity.class);
        }
    }

    /**
     * 展示广告
     *
     * @param ads 广告列表
     */
    private void showAd(List<EventBannersResp.ItemsBean> ads) {

        if (ads == null || ads.isEmpty()) {
            return;
        }


        if (ads.size() == 1) {
            setImageContent(ads.get(0));
            return;
        }
        try{
            //随机播放广告
            int randomIndex = new Random().nextInt(ads.size());

            if (Glide.getPhotoCacheDir(getActivity(), ads.get(randomIndex).getImage_url()) != null) {
                //有无缓存
                setImageContent(ads.get(randomIndex));
            } else {
                //移除无缓存广告
                ads.remove(randomIndex);
                //展示下一张
                showAd(ads);
            }
        }catch (Exception e){
            e.printStackTrace();
        }




    }

    /**
     * 设置图片内容
     *
     * @param adDataResp 广告内容
     */
    private void setImageContent(EventBannersResp.ItemsBean adDataResp) {
        iv_ad_content.setTag(R.id.image_tag, adDataResp);
        GlideHelper.displayIndexAdImage(getActivity(), iv_ad_content, adDataResp.getImage_url());
    }
}
