package net.doyouhike.app.bbs.ui.home;

import android.widget.ImageView;
import android.widget.TextView;

import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.ui.home.topic.TimelineRequestType;

/**
 * Filework: LiveAdapter业务
 * Author: luochangdong
 * Date:16-1-8
 */
public interface IPresenterLiveAdapter {


    void isHot(TimelineRequestType isHot);

    void clickLike(ImageView likeImage, TextView likeNum, Timeline timeline);

    void clickComment(Timeline timeline);

    void clickMore(Timeline timeline);

    void clickAuthor(String userId);

    void lookDetail(Timeline timeline);


}

