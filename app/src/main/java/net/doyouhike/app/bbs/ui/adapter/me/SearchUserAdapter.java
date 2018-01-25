package net.doyouhike.app.bbs.ui.adapter.me;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.RecommendUser;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.response.users.SearchUserResp;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.util.FollowButtonHelper;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/8
 * 描述：
 */
public class SearchUserAdapter extends CommonAdapter<SearchUserResp.ItemsBean> {

    public SearchUserAdapter(Context context, List<SearchUserResp.ItemsBean> datas) {
        super(context, datas, R.layout.item_recommend_user_info);
    }

    @Override
    public void convert(ViewHolder holder, SearchUserResp.ItemsBean itemsBean) {


        ImageView iv_avatar = holder.getView(R.id.iv_avatar);
        TextView tv_nickname = holder.getView(R.id.tv_nick_name);
        TextView tv_user_desc = holder.getView(R.id.tv_user_signature);
        View line_bottom = holder.getView(R.id.v_bottom_side);
        View v_item = holder.getView(R.id.llyt_user_info);
        TextView tv_follow = holder.getView(R.id.iv_attend_btn);

        RecommendUser user = itemsBean.getUser();


        // 设置头像
        if (iv_avatar != null) {
            String avatar_url = Constant.PHOTO_DOMAIN_PATH + user.getAvatar();
            GlideHelper.displayHeader(mContext, iv_avatar, avatar_url);
            UserHeadNickClickHelper.getInstance().setClickListener(iv_avatar,
                    user.getNick_name(),
                    user.getUser_id(),
                    user.getUser_id(),
                    avatar_url);
        }
        //整栏可点
        setParentView(v_item, user.getUser_id());

        //整栏可点


        // 设置昵称
        if (user.getNick_name() != null) {
            tv_nickname.setText(user.getNick_name());
        }

        // 设置签名
        if (tv_user_desc != null && user.getUser_desc() != null) {
            tv_user_desc.setText(user.getUser_desc());
        }

        // 设置关注按钮

        int isFollowed = UsersHelper.getSingleTon().getFollowStateBySocial(itemsBean.getSocial());

        FollowButtonHelper.setTextState(tv_follow, isFollowed, user.getUser_id());

        // 设置边线
        // 最后一个
        UIUtils.showView(line_bottom, holder.getmPosition() != mDatas.size() - 1);

    }

    private void setParentView(View vParent, final String userID) {

        if (null == userID) {
            vParent.setOnClickListener(null);
            vParent.setBackgroundColor(Color.WHITE);
        } else {
            vParent.setBackgroundResource(R.drawable.selector_list_item_bg);
            vParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityRouter.openOtherPageActivity(v.getContext(), userID);
                }
            });
        }
    }
}
