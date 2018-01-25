package net.doyouhike.app.bbs.ui.release.yueban.destination;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.entity.LocationInfo;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetDestByKeywordResp;
import net.doyouhike.app.bbs.ui.release.PresenterEditLive;
import net.doyouhike.app.bbs.ui.release.map.SearchInMapActivity;
import net.doyouhike.app.bbs.ui.widget.action.XEditText;
import net.doyouhike.app.bbs.util.InputTools;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-3-22
 */
public class SelectDestActivity extends BaseActivity {
    @InjectView(R.id.et_activityKeyWord)
    XEditText etActivityKeyWord;
    @InjectView(R.id.tv_cancel)
    TextView tvCancel;
    @InjectView(R.id.lv_dest)
    ListView lvDest;
    @InjectView(R.id.tv_yueban_select_dest_keyword)
    TextView tvYuebanSelectDestKeyword;
    @InjectView(R.id.rl_yueban_select_dest_live)
    RelativeLayout rlYuebanSelectDestLive;


    public static SelectDestActivity Instance = null;
    SelectDestAdapter adapter;
    PresenterSelectDest presenter;

    public static final String SEARCH_WORD = "SEARCH_WORD";
    @InjectView(R.id.ll_act_select_dest)
    LinearLayout llActSelectDest;

    private List<GetDestByKeywordResp> selectDatas = new ArrayList<>();
    private String keyword;

    /**
     * 搜索没有数据
     */
    private GetDestByKeywordResp noData = new GetDestByKeywordResp();

    /**
     * 区分 直播-约伴
     */
    private boolean isEditLive = false;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_yueban_select_dest;
    }

    @Override
    protected void initViewsAndEvents() {
        Instance = this;
        presenter = new PresenterSelectDest(this);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new SelectDestAdapter(this, selectDatas);
        lvDest.setAdapter(adapter);

        initEditText();
        lvDest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GetDestByKeywordResp entity = adapter.getItem(position);
                if (isEditLive) {
                    LocationInfo info = new LocationInfo();
                    info.setLocation(entity.getNode_name());
                    info.setDest_id(entity.getNode_id());
                    EventBus.getDefault().post(info);
                } else
                    EventBus.getDefault().post(entity);
                finish();

            }
        });
        etActivityKeyWord.clearFocus();

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null && extras.get(PresenterEditLive.TAG).equals(PresenterEditLive.TAG))
            isEditLive = true;
    }

    @Override
    protected View getLoadingTargetView() {
        return llActSelectDest;
    }

    /**
     * 没有关键字的数据
     */
    private void initEditText() {
        etActivityKeyWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputTools.HideKeyboard(etActivityKeyWord);
                    keyword = etActivityKeyWord.getText().toString();
                    if (!keyword.isEmpty()) {
                        adapter.setKeyword(keyword);
                        presenter.getDestByKeyword(keyword);
                        updateView(UiState.LOADING);
                        if (!isEditLive) {

                            noData.setNode_name(keyword);
                            noData.setNode_cat(SelectDestAdapter.TYPE_NULL);
                        }


                    }
                    return true;
                }

                return false;
            }
        });

        if (isEditLive) {
            etActivityKeyWord.setHint(R.string.live_search_destination);
        } else
            etActivityKeyWord.setHint(R.string.event_search_destination);


    }

    private void editLiveHandle() {
        if (rlYuebanSelectDestLive == null)
            return;
        if (isEditLive) {
            rlYuebanSelectDestLive.setVisibility(View.VISIBLE);
            StringBuilder search = new StringBuilder();
            search.append("在地图搜索“");
            search.append(keyword);
            search.append("”");
            UIUtils.setKeyword(tvYuebanSelectDestKeyword, search.toString(), keyword);
            rlYuebanSelectDestLive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(SEARCH_WORD, keyword);
                    readyGo(SearchInMapActivity.class, bundle);
                }
            });
        } else {
            rlYuebanSelectDestLive.setVisibility(View.GONE);
        }
    }

    public void setSelectDatas(List<GetDestByKeywordResp> datas) {
        updateView(UiState.NORMAL);
        selectDatas.clear();
        if (datas == null || datas.isEmpty()) {
            if (!isEditLive) {
                selectDatas.add(noData);
            }
        } else {
            selectDatas.addAll(datas);
        }
        editLiveHandle();
        adapter.notifyDataSetChanged();
    }

    public void loadError(String error) {
        updateView(UiState.ERROR.setMsg(getString(R.string.common_error_msg),
                getString(R.string.try_to_click_refresh)),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateView(UiState.LOADING);
                        adapter.setKeyword(keyword);
                        presenter.getDestByKeyword(keyword);
                    }
                });

    }

    @Override
    protected void onDestroy() {
        presenter.cancelPost();
        super.onDestroy();
    }
}
