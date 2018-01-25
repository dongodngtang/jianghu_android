package net.doyouhike.app.bbs.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.event.PostLiveEvent;
import net.doyouhike.app.bbs.biz.event.ResendLiveEvent;
import net.doyouhike.app.bbs.biz.event.SendLive;
import net.doyouhike.app.bbs.biz.event.action.ActionEditRefreshEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.SendingTimeline;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.ui.adapter.NodeTimelineAdapter;
import net.doyouhike.app.bbs.ui.home.topic.RefreshAttenLive;
import net.doyouhike.app.bbs.ui.home.topic.TimelineRequestType;
import net.doyouhike.app.bbs.ui.release.yueban.EditEventActivity;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StatisticsEventUtil;
import net.doyouhike.app.bbs.util.StringUtil;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 功能：
 *
 * @author：曾江 日期：16-3-4.
 */
public class AttendLiveFragment extends BaseLiveFragment {


    public AttendLiveFragment() {
    }

    public static AttendLiveFragment newInstance(BaseTag tag) {
        AttendLiveFragment fragment = new AttendLiveFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TAG, tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onFirstUserVisible() {
        super.onFirstUserVisible();

        NodeTimeline.ItemsBean.NodeBean timeline = SharedPreferencesManager.getPostLive();
        if (null != timeline) {
            timelineView.addTempTimeline(timeline);
        }

        SendingTimeline sendingEvent = SharedPreferencesManager.getPostEvent();

        if (null != sendingEvent) {
            timelineView.addTempTimeline(sendingEvent);
        }
    }


    public void onEventMainThread(PostLiveEvent eventCenter) {

        if (mTag.getLiveType() == TimelineRequestType.ATTEND) {

            if (null != timelineView) {
                if (StringUtil.isEmpty(eventCenter.getData().getTempNoteId())) {
                    onPostLiveEvent(eventCenter);
                } else {
                    onPostEidtLiveEvent(eventCenter);
                }

            }
        }
    }

    private void onPostEidtLiveEvent(PostLiveEvent eventCenter) {
        switch (eventCenter.getEventCode()) {
            case Content.RELEASE_LIVE_SENDING:
                LogUtil.d("约伴编辑 发送中");
                break;
            case Content.RELEASE_LIVE_SUCCUSS:
                LogUtil.d("SendReleaseUtil编辑 发送成功");
                SharedPreferencesManager.setPostEventEdit(null);
                StringUtil.showSnack(getActivity(), R.string.snack_release_edit_event_ok);
                EventBus.getDefault().post(new ActionEditRefreshEvent());

                break;
            case Content.RELEASE_LIVE_FAIL:
                //发送直播失败
//                TipUtil.alert(getActivity(),mContext.getString(R.string.snack_release_edit_event_fail));
                showEditEventUpdateFail(eventCenter);
                break;
        }
    }

    private void onPostLiveEvent(PostLiveEvent eventCenter) {
        LogUtil.d("SendReleaseUtil", "EventCode:" + eventCenter.getEventCode());
        switch (eventCenter.getEventCode()) {

            case Content.RELEASE_LIVE_SENDING:
                notifyDataChange(eventCenter.getData());
                //滚动到顶行
                getLvHomeList().smoothScrollToPositionFromTop(0, 0);
                break;
            case Content.RELEASE_LIVE_SUCCUSS:
                //发送成功
                LogUtil.d("SendReleaseUtil", "EventCode:RELEASE_LIVE_SUCCUSS");

                StringUtil.showSnack(getContext(), getString(R.string.snack_release_ok));

                //统计事件
                if (null != eventCenter.getData()) {

                    if (eventCenter.getData().getMinilog_type().equals(NodeTimelineAdapter.NODE_TEXT_PHOTO)) {
                        //图文统计
                        StatisticsEventUtil.PostSuccess(getActivity());

                    } else if (eventCenter.getData().getMinilog_type().equals(NodeTimelineAdapter.NODE_EVENT)) {
                        //活动统计
                        StatisticsEventUtil.PostAction(getActivity());
                    }
                }
                timelineView.refreshData();
                break;
            case Content.RELEASE_LIVE_FAIL:
                //发送直播失败
//                TipUtil.alert(getActivity(),mContext.getString(R.string.check_network));
                notifyDataChange(eventCenter.getData());

                break;
            case Content.RELEASE_LIVE_REPOST:
                //重新发送直播
                EventBus.getDefault().post(new ResendLiveEvent(eventCenter.getData()));
                notifyDataChange(eventCenter.getData());
                break;
            case Content.RELEASE_LIVE_DELETE:
                //删除发送失败的直播

                SendingTimeline sendingTimeline = eventCenter.getData();

//                timelineView.getLiveAdapter().getDatas().remove(0);
                NodeTimeline.ItemsBean.NodeBean node = eventCenter.getData();
                NodeTimeline.ItemsBean itemsBean = new NodeTimeline.ItemsBean();
                itemsBean.setNode(node);
                if (timelineView.getLiveAdapter().getDatas().contains(itemsBean)) {
                    timelineView.getLiveAdapter().getDatas().remove(itemsBean);
                }

                timelineView.getLiveAdapter().notifyDataSetChanged();

                if (sendingTimeline.getSendingId() == SendingTimeline.ID_SENDING_LIVE) {
                    SharedPreferencesManager.setPostLive(null);
                    SharedPreferencesManager.clearToReleaseLiveInfo(mContext);
                } else {
                    SharedPreferencesManager.setPostEvent(null);
                    SharedPreferencesManager.clearToEventLiveInfo(getContext());
                }

                if (timelineView.getLiveAdapter() != null && timelineView.getLiveAdapter().getDatas().isEmpty()) {
                    timelineView.initData();
                }

                break;
            case Content.RELEASE_TAG:
                NodeTimeline.ItemsBean.NodeBean timeline = eventCenter.getData();
                if (timeline != null) {
                    timelineView.addTempTimeline(timeline);
                }
                break;
            case Content.RELEASE_LIVE_CANCEL:
                break;
            case Content.RELEASE_LIVE_FORBEDING:

                //被禁用户,发布约伴
                notifyDataChange(eventCenter.getData());
                break;
            case Content.RELEASE_LIVE_OVER_MORE:

                //发布次数超限制
                notifyDataChange(eventCenter.getData());
                break;
        }

    }

    private void showEditEventUpdateFail(final PostLiveEvent eventCenter) {

        MaterialDialog dialog = TipUtil.alert(mContext
                , mContext.getString(R.string.live_send_fail)
                , mContext.getString(R.string.try_again_or_delete)
                , new String[]{mContext.getString(R.string.delete), mContext.getString(R.string.try_again)}
                , new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        SharedPreferencesManager.setPostEventEdit(null);
                    }
                }
                ,
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        SharedPreferencesManager.setPostEventEdit(null);
                        String nodeId = eventCenter.getData().getTempNoteId();
                        if (!StringUtil.isEmpty(nodeId))
                            eventCenter.getData().setNode_id(nodeId);

                        String json = new Gson().toJson(eventCenter.getData());
                        Intent intent = new Intent(mContext, EditEventActivity.class);
                        intent.putExtra(Content.TIMELINE, json);
                        mContext.startActivity(intent);
                    }
                }
        );
        dialog.setCanceledOnTouchOutside(false);

    }


    public void onEventMainThread(RefreshAttenLive response) {

        if (null != timelineView) {
            timelineView.refreshData();
        }

    }

    /**
     * 由于从SharedPreferences中获取的对象与发送中的可能不是同一个对象,所以notifyDataSetChanged,列表可能不会刷新,
     * 需要删除列表中的直播,再添加回去
     *
     * @param nodeBean
     */
    private synchronized void notifyDataChange(final SendingTimeline nodeBean) {

        if (null == lvHomeList) {
            //有可能在onDestroyView的时候接手到消息
            return;
        }


        List<NodeTimeline.ItemsBean> datas = timelineView.getLiveAdapter().getDatas();
        NodeTimeline.ItemsBean timeline = new NodeTimeline.ItemsBean();
        timeline.setNode(nodeBean);
        if (timeline.getNode().getReleaseStatus() == Content.SEND_OK) {
            //发送成功,改变发送id
            nodeBean.setSendingId(SendingTimeline.ID_SENDING_SUCCESS);
        }

        String nodeId = timeline.getNode().getNode_id();
        //临时设置为空,否则发送成功后有nodeid,contains方法识别不了
        timeline.getNode().setNode_id(null);

        if (datas.contains(timeline)) {
            //删除发送中的直播
            datas.remove(timeline);
        }

        timeline.getNode().setNode_id(nodeId);
        datas.add(0, timeline);
        timelineView.getLiveAdapter().notifyDataSetChanged();

    }

}
