/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ReportActivity.java
 * Author: wu-yoline(伍建鹏)
 * Version: 1.0
 * Create: 2015-10-5
 *
 * Changes (from 2015-10-6)
 * -----------------------------------------------------------------
 * 2015-10-1 : 	1、创建 本类 ，实现父类onCreate()方法(wu-yoline);
 * 				2、设置contentView为activity_report.xml(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-10-6 : 	1、把父类改为BaseActivity(wu-yoline);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.NodesHelper;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeAccusationResp;
import net.doyouhike.app.bbs.ui.adapter.live.ReportAdapter;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class ReportActivity extends net.doyouhike.app.bbs.base.activity.BaseActivity {
    @InjectView(R.id.title_act_report)
    TitleView titleActReport;
    @InjectView(R.id.lv_report)
    ListView lvReport;
    private int accusationType = -1;
    public static final String NODEID = "nodeID";
    public static final String MYUSERID = "myUserID";
    public static final String ISEVENT = "REPORT_EVEN";
    private String nodeID;
    private String myUserID;
    ReportAdapter adapter;
    List<NodeAccusationResp.ItemsBean> reportDatas;
    private boolean isEvent;

    public boolean isEvent() {
        return isEvent;
    }

    public int getAccusationType() {
        return accusationType;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_report;
    }

    @Override
    protected void initViewsAndEvents() {
        initData();
        setListener();
        titleActReport.getRight_text().setEnabled(false);
    }

    @Override
    protected View getLoadingTargetView() {
        return lvReport;
    }

    private void setListener() {
        titleActReport.setListener(new TitleView.ClickListener() {
            @Override
            public void clickLeft() {
                finish();
            }

            @Override
            public void clickRight() {
                if (UserInfoUtil.getInstance().isLogin()) {
                    if (accusationType == -1) {
                        showAlertDialog();
                    } else {
                        titleActReport.getRight_text().setEnabled(false);
                        NodesHelper.getInstance().postNodeAccusation(mContext,nodeID,accusationType,accusationListener);
                    }
                } else {
                    ActivityRouter.openLoginActivity(ReportActivity.this);
                    finish();
                }

            }
        });

        lvReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                accusationType = adapter.getItem(position).getType_id();
                titleActReport.getRight_text().setEnabled(true);
                adapter.notifyDataSetChanged();
            }
        });


    }

    private void showAlertDialog() {
        TipUtil.alert(ReportActivity.this, "请选择举报内容");
    }


    private void initData() {
        reportDatas = new ArrayList<>();
        nodeID = getIntent().getStringExtra(NODEID);
        myUserID = getIntent().getStringExtra(MYUSERID);
        isEvent = getIntent().getBooleanExtra(ISEVENT, false);
        adapter = new ReportAdapter(this, reportDatas);
        lvReport.setAdapter(adapter);
        updateView(UiState.LOADING);
        NodesHelper.getInstance().getNodeAccusationList(mContext,typelistListener);
    }


    public void error(String error) {
        updateView(UiState.NORMAL);
        if (reportDatas.size() > 0)
            return;
        updateView(UiState.ERROR.setMsg("出错了", error), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateView(UiState.LOADING);
                NodesHelper.getInstance().getNodeAccusationList(mContext,typelistListener);
            }
        });
    }




    IOnResponseListener typelistListener = new IOnResponseListener<Response<NodeAccusationResp>>() {
        @Override
        public void onSuccess(Response<NodeAccusationResp> response) {
            setTypeList(response.getData().getItems());
        }

        @Override
        public void onError(Response response) {
           error(response.getMsg());
        }
    };
    public void setTypeList(List<NodeAccusationResp.ItemsBean> data) {
        updateView(UiState.NORMAL);
        if (data.size() > reportDatas.size()) {
            reportDatas.clear();
            reportDatas.addAll(data);
            adapter.notifyDataSetChanged();
        }

    }

    IOnResponseListener accusationListener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            StringUtil.showSnack(mContext, getString(R.string.report_act_succuss));
            titleActReport.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ReportActivity.this.finish();
                }
            }, 1200);
        }

        @Override
        public void onError(Response response) {
            StringUtil.showSnack(mContext, response.getMsg());
            titleActReport.getRight_text().setEnabled(true);
        }
    };


}
