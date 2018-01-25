package net.doyouhike.app.bbs.ui.home.tag.activity;


import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;

import java.util.List;

/**
 * 功能：
 *
 * @author：曾江 日期：16-2-25.
 */
public interface IPresenterHomeTag {

    /**
     * 初始化标签数据
     */
    void getTagData();

    void getSubscribTag();

    void onSubscribeTag(List<BaseTag> tags);

    void onDestory();
}
