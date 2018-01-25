package net.doyouhike.app.bbs.ui.home.tag.activity;

import android.content.Context;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.MySubscibesRespons;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.SearchTagsResp;
import net.doyouhike.app.bbs.biz.openapi.presenter.NodesHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 *
 * @author：曾江 日期：16-2-25.
 */
public class PresenterHomeTag implements IPresenterHomeTag {

    IViewHomeTag iView;
    boolean isDestory = false;
    private Context mContext;

    public PresenterHomeTag(IViewHomeTag IView,Context context) {
        this.iView = IView;
        mContext = context;
    }


    @Override
    public void getTagData() {
        NodesHelper.getInstance().getBaseMfTypes(mContext,getEventTypeListener);

    }

    @Override
    public void getSubscribTag() {

        //获取我订阅的标签
        NodesHelper.getInstance().getUserSubscriptions(mContext,UserInfoUtil.getInstance().getUserId()
                ,getMySubscriListener);
    }

    @Override
    public void onSubscribeTag(List<BaseTag> tags) {
        UsersHelper.getSingleTon().putUserTags(mContext,UserInfoUtil.getInstance().getUserId(),tags,saveTagListener);
    }

    @Override
    public void onDestory() {
        isDestory = true;
    }


    //#######start#########
    //标签相关

    IOnResponseListener getEventTypeListener = new IOnResponseListener<Response<SearchTagsResp>>() {

        @Override
        public void onSuccess(Response<SearchTagsResp> response) {
            if (isDestory) {
                return;
            }
            ArrayList<BaseTag> tags = new ArrayList<>();
            tags.addAll(response.getData().getMinilog_tags());
            iView.onGetTags(tags);
        }

        @Override
        public void onError(Response response) {
            if (isDestory) {
                return;
            }
            iView.onGetTagErr(response);
        }
    };

    //获取订阅标签
    IOnResponseListener getMySubscriListener = new IOnResponseListener<Response<MySubscibesRespons>>() {


        @Override
        public void onSuccess(Response<MySubscibesRespons> response) {

            if (isDestory) {
                return;
            }
            iView.onGetSubcribeTags(response.getData().getMy_subscribes());

        }

        @Override
        public void onError(Response response) {
            if (isDestory) {
                return;
            }
            iView.onGetSubcribeTagsErr(response);
        }
    };


    //保存订阅标签
    IOnResponseListener saveTagListener = new IOnResponseListener<Response>() {


        @Override
        public void onSuccess(Response response) {
            if (isDestory) {
                return;
            }
            iView.onSaveTagSuc();
        }

        @Override
        public void onError(Response response) {
            if (isDestory) {
                return;
            }
            iView.onSaveTagErr(response);
        }
    };

    //标签相关
    //#######end#########


}
