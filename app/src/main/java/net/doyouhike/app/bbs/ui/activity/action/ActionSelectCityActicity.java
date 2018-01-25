/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ActionSelectCityActicity.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-10-22
 *
 */
package net.doyouhike.app.bbs.ui.activity.action;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.CitySelectInfo;
import net.doyouhike.app.bbs.biz.entity.SelectCityModel;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.adapter.action.SortAdapter;
import net.doyouhike.app.bbs.ui.widget.action.SideBar;
import net.doyouhike.app.bbs.ui.widget.action.SideBar.OnTouchingLetterChangedListener;
import net.doyouhike.app.bbs.util.CharacterParser;
import net.doyouhike.app.bbs.util.ChineseFCUtil;
import net.doyouhike.app.bbs.util.GetCityIDUtils;
import net.doyouhike.app.bbs.util.PinyinComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ZouWenJie 选择城市的界面
 */
public class ActionSelectCityActicity extends BaseActivity {
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private CharacterParser characterParser;// 汉字转换成拼音的类
    private List<SelectCityModel> SourceDateList;
    private PinyinComparator pinyinComparator;// 根据拼音来排列ListView里面的数
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_select_city);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        city = intent.getStringExtra("city");// 获取定位的城市
        bindControl();
        initData();
    }

    /**
     * 联网获取热门城市数据
     */
    private void initData() {


        AsyncTask<Void,Void,List<SelectCityModel>> task=new AsyncTask<Void,Void,List<SelectCityModel>> () {

            @Override
            protected List<SelectCityModel> doInBackground(Void... params) {
                List<CitySelectInfo> citySelectInfos= GetCityIDUtils.getCityList(ActionSelectCityActicity.this);

                List<SelectCityModel> selectCityModels=new ArrayList<>();


                if (null!=citySelectInfos){
                    for (CitySelectInfo info:citySelectInfos){

                        SelectCityModel model=new SelectCityModel();

                        model.setCity_id(String.valueOf(info.getCityId()));
                        model.setName(info.getCityName());
                        model.setSortLetters(ChineseFCUtil.cn2py(model.getName()));
                        selectCityModels.add(model);
                    }
                }


                return selectCityModels;

            }

            @Override
            protected void onPostExecute(List<SelectCityModel> citySelectInfos) {
                super.onPostExecute(citySelectInfos);
                SourceDateList = citySelectInfos;
                initViews();
            }
        };

        task.execute();

    }


    IOnResponseListener<Response<List<SelectCityModel>>> getHotCityListenner =
            new IOnResponseListener<Response<List<SelectCityModel>>>() {
                @Override
                public void onSuccess(Response<List<SelectCityModel>> response) {
                    SourceDateList = response.getData();
                    initViews();
                }

                @Override
                public void onError(Response response) {

                }
            };

    private void bindControl() {
        dialog = (TextView) findViewById(R.id.tv_select_city_dialog);// 中间提示的textview
        sideBar = (SideBar) findViewById(R.id.sidrbar_select_city);// 右侧索引条
        sortListView = (ListView) findViewById(R.id.lv_select_city);
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar.setTextView(dialog);
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = -2;
                // 该字母首次出现的位置
                if (adapter != null && s != null) {
                    position = adapter.getPositionForSection(s.charAt(0));
                }
                if (position != -1) {
                    sortListView.setSelection(position + 1);
                }

            }
        });

        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象

                Intent intent = new Intent();
                String selectCity;

                if (position == 0) {

                    selectCity = adapter.getCity();

                } else {
                    selectCity = ((SelectCityModel) adapter
                            .getItem(position - 1)).getName();
                }

                if (TextUtils.isEmpty(selectCity)) {
                    selectCity = "其他";
                }

                intent.putExtra("selectCity", selectCity);
                ActionSelectCityActicity.this.setResult(RESULT_OK, intent);
                ActionSelectCityActicity.this.finish();
            }
        });
        // 讲获取到的数据设置首字符数据
        if (SourceDateList != null) {
            SourceDateList = filledData(SourceDateList);
            // 根据a-z进行排序源数据
            Collections.sort(SourceDateList, pinyinComparator);
        }
        adapter = new SortAdapter(this, SourceDateList, city);
        sortListView.setAdapter(adapter);

    }

    /**
     * 为ListView填充数据
     *
     * @param SourceDateList
     * @return
     */
    private List<SelectCityModel> filledData(
            List<SelectCityModel> SourceDateList) {
        List<SelectCityModel> sortList = new ArrayList<SelectCityModel>();

        for (int i = 0; i < SourceDateList.size(); i++) {
            SelectCityModel SelectCityModel = new SelectCityModel();
            SelectCityModel.setName(SourceDateList.get(i).getName());
            SelectCityModel.setCity_id(SourceDateList.get(i).getCity_id());
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(SourceDateList.get(i)
                    .getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                SelectCityModel.setSortLetters(sortString.toUpperCase());
            } else {
                SelectCityModel.setSortLetters("#");
            }

            sortList.add(SelectCityModel);
        }
        return sortList;

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
