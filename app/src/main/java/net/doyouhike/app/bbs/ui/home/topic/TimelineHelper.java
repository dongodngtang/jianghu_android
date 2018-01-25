package net.doyouhike.app.bbs.ui.home.topic;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines.TimelineFollowsGet;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines.TimelineHotsGet;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines.TimelineTagsGet;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserNodeStateResp;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.util.StringUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Filework:
 * Author: luochangdong
 * Date:16-2-25
 */
public class TimelineHelper {
    TimelineView timelineView;
    private int mCurrentPage;

    public TimelineHelper(TimelineView timelineView) {
        this.timelineView = timelineView;
    }


    public void onlineLiveRequest(int pageNum, int isLoadMore, int lastId, BaseTag tag) {
        TimelineTagsGet param = getTopicRequestParam(pageNum, isLoadMore, lastId, tag);
        ApiReq.doGet(param, onlineTimelineListener);

    }

    public TimelineTagsGet getTopicRequestParam(int pageNum, int isLoadMore, int lastId, BaseTag tag) {

        TimelineTagsGet param = new TimelineTagsGet(tag.getTag_id());
        param.setPage_index(pageNum);
        param.setPage_size(TimelineView.PAGE_LIMIT);
        param.setLast_id(lastId + "");
        param.setExtraInfo(new LoadPageMore(pageNum, isLoadMore));
        return param;
    }


    public void onlineHotRequest(int pageNum, int pageSize, int isLoadMore) {
        TimelineHotsGet hotTimelineReq = new TimelineHotsGet();

        hotTimelineReq.setPage_index(pageNum);
        hotTimelineReq.setPage_size(pageSize);
        hotTimelineReq.setExtraInfo(new LoadPageMore(pageNum, isLoadMore));

        ApiReq.doGet(hotTimelineReq, onlineTimelineListener);
    }


    public void onlineFollowRequest(int pageNum, int pageSize, int isLoadMore) {
        TimelineFollowsGet followTimelineGetParam = new TimelineFollowsGet();

        followTimelineGetParam.setPage_index(pageNum);
        followTimelineGetParam.setPage_size(pageSize);
        followTimelineGetParam.setExtraInfo(new LoadPageMore(pageNum, isLoadMore));
        ApiReq.doGet(followTimelineGetParam, onlineTimelineListener);
    }

    public int getmCurrentPage() {
        return mCurrentPage;
    }

    IOnResponseListener onlineTimelineListener = new IOnResponseListener<Response<NodeTimeline>>() {
        @Override
        public void onSuccess(Response<NodeTimeline> response) {
            timelineView.hideLoading();
            if (null == response.getData())
                return;
            if (response.getData().getPhoto_domain_path() != null)
                Constant.PHOTO_DOMAIN_PATH = response.getData().getPhoto_domain_path();
            if (response.getData().getDoyouhike_domain_path() != null)
                Constant.DOYOUHIKE_DOMAIN_PATH = response.getData().getDoyouhike_domain_path();
            //二层去重 数据源去重、历史去重
            final List<NodeTimeline.ItemsBean> newList = new ArrayList<>();
            Set set = new LinkedHashSet<>();
            set.addAll(response.getData().getItems());
            newList.addAll(set);


            LoadPageMore pageMore = (LoadPageMore) response.getExtraTag();
            if (response.getData().getItems().size() > 0)
                mCurrentPage = pageMore.getPage();
            if (pageMore.getLoadMore() == TimelineView.REFRESH) {
                timelineView.refreshListData(newList);
            } else if (pageMore.getLoadMore() == TimelineView.LOADMORE) {
                timelineView.addMoreListData(newList);
            }

            Observable.create(new Observable.OnSubscribe<List<String>>() {
                @Override
                public void call(Subscriber<? super List<String>> subscriber) {
                    List<String> nodes = new ArrayList<>();
                    for (NodeTimeline.ItemsBean bean : newList) {
                        if (bean.getNode() != null && StringUtil.isNotEmpty(bean.getNode().getNode_id())) {
                            if (bean.getNode().getForward() != null && bean.getNode().getForward().getNode() != null
                                    && StringUtil.isNotEmpty(bean.getNode().getForward().getNode().getNode_id())) {
                                nodes.add(bean.getNode().getForward().getNode().getNode_id());
                            } else {
                                nodes.add(bean.getNode().getNode_id());
                            }
                        }

                    }
                    subscriber.onNext(nodes);
                    subscriber.onCompleted();

                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Action1<List<String>>() {
                        @Override
                        public void call(List<String> nodes) {
                            UsersHelper.getSingleTon().getNodeStates(timelineView.getContext(), nodes, nodeStateListener);

                        }
                    });

        }

        IOnResponseListener<Response<UserNodeStateResp>> nodeStateListener = new IOnResponseListener<Response<UserNodeStateResp>>() {
            @Override
            public void onSuccess(Response<UserNodeStateResp> response) {

                Observable.from(response.getData().getItems())
                        .subscribeOn(Schedulers.newThread())
                        .map(new Func1<UserNodeStateResp.ItemsBean, List<NodeTimeline.ItemsBean>>() {
                            @Override
                            public List<NodeTimeline.ItemsBean> call(UserNodeStateResp.ItemsBean nodeState) {
                                for (NodeTimeline.ItemsBean node : timelineView.getItems()) {
                                    if (node.getNode().getForward() != null && node.getNode().getForward().getNode() != null
                                            && StringUtil.isNotEmpty(node.getNode().getForward().getNode().getNode_id())) {
                                        if (nodeState.getNode_id().equals(node.getNode().getForward().getNode().getNode_id())) {
                                            setNodeCounter(nodeState, node);
                                        }
                                    } else if (nodeState.getNode_id().equals(node.getNode().getNode_id())) {
                                        setNodeCounter(nodeState, node);
                                    }

                                }
                                return timelineView.getItems();
                            }

                            private void setNodeCounter(UserNodeStateResp.ItemsBean nodeState, NodeTimeline.ItemsBean node) {
                                node.getNode_counter().setLiked(nodeState.isLiked());
                                node.getNode_counter().setFavorited(nodeState.isFavorited());
                                node.getNode_counter().setSocial(nodeState.getSocial());
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<NodeTimeline.ItemsBean>>() {
                            @Override
                            public void call(List<NodeTimeline.ItemsBean> itemsBeen) {
                                timelineView.getLiveAdapter().notifyDataSetChanged();
                            }
                        });

            }

            @Override
            public void onError(Response response) {

            }
        };

        @Override
        public void onError(Response response) {
            timelineView.hideLoading();
            timelineView.loadDataError(response.getMsg());
        }
    };


}
