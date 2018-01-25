package net.doyouhike.app.bbs.ui.home.tag.activity;


import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;

import java.util.List;

/**
 * 功能：
 *
 * @author：曾江 日期：16-2-25.
 */
public interface IViewHomeTag {
    /**
     * @param tags 获取标签回调
     */
    void onGetTags(List<BaseTag> tags);

    void onGetTagErr(Response response);

    /**
     * @param tags 订阅标签
     */
    void onGetSubcribeTags(List<BaseTag> tags);

    void onGetSubcribeTagsErr(Response response);

    void onSaveTagErr(Response response);

    void onSaveTagSuc();

}
