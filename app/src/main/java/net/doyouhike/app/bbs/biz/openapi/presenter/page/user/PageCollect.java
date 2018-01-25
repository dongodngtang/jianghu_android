package net.doyouhike.app.bbs.biz.openapi.presenter.page.user;

import android.content.Context;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines.UserFavoritesTimelineGet;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserNodeStateResp;
import net.doyouhike.app.bbs.ui.adapter.me.UserLiveAdapter;
import net.doyouhike.app.bbs.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 功能：
 * 作者：曾江
 * 日期：16-1-12.
 */
public class PageCollect extends PageBase<NodeTimeline.ItemsBean> {


    private Context mContext;

    public PageCollect(Context context, String user_id) {

        items = new ArrayList<>();
        adapter = new UserLiveAdapter(context, items);
        mRequest = new UserFavoritesTimelineGet(user_id);
        mContext = context;
    }

    @Override
    public void updateItem(final List<NodeTimeline.ItemsBean> items, boolean isRefresh) {
        super.updateItem(items, isRefresh);
        Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                List<String> nodes = new ArrayList<>();
                for (NodeTimeline.ItemsBean bean : items) {
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
                        UsersHelper.getSingleTon().getNodeStates(mContext, nodes, nodeStateListener);

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
                            for (NodeTimeline.ItemsBean node : getItems()) {
                                if (node.getNode().getForward() != null && node.getNode().getForward().getNode() != null
                                        && StringUtil.isNotEmpty(node.getNode().getForward().getNode().getNode_id())) {
                                    if (nodeState.getNode_id().equals(node.getNode().getForward().getNode().getNode_id())) {
                                        setNodeCounter(nodeState, node);
                                    }
                                } else if (nodeState.getNode_id().equals(node.getNode().getNode_id())) {
                                    setNodeCounter(nodeState, node);
                                }
                            }
                            return getItems();
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
                            adapter.notifyDataSetChanged();
                        }
                    });

        }

        @Override
        public void onError(Response response) {

        }
    };

    @Override
    public String[] getEmptyTip() {
        return new String[]{"", "暂无收藏"};
    }

    @Override
    public UserFavoritesTimelineGet getRequestParam() {
        if (getItems().size() > 0 &&
                getItems().get(getItems().size() - 1).getNode() != null
                && getItems().get(getItems().size() - 1).getNode().getNode_id() != null) {
            String lastId = getItems().get(getItems().size() - 1).getNode().getNode_id();
            mRequest.setLast_id(lastId);
        }
        return (UserFavoritesTimelineGet) mRequest;
    }


    @Override
    public UserLiveAdapter getAdapter() {
        return (UserLiveAdapter) super.getAdapter();
    }
}
