package net.doyouhike.app.bbs.ui.adapter.me;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.db.green.entities.Follow;
import net.doyouhike.app.bbs.biz.openapi.presenter.AttendState;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.util.FollowButtonHelper;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.List;

/**
 * 作者：luochangdong on 16/9/26
 * 描述：
 */
public class FollowFansAdapter extends CommonAdapter<Follow> {

    private String follow_fans;

    public FollowFansAdapter(Context context, List<Follow> datas, String follow_fans) {
        super(context, datas, R.layout.item_follow_and_follower);
        this.follow_fans = follow_fans;
    }

    @Override
    public void convert(ViewHolder holder, Follow follow) {
        ImageView iv_avatar = holder.getView(R.id.iv_itemt_portrait);
        TextView tv_nickname = holder.getView(R.id.tv_itemt_nickname);
        TextView tv_userdesc = holder.getView(R.id.tv_itemt_autograph);
        TextView tv_follow = holder.getView(R.id.btn_itemt_follow_state);

        String url = Constant.PHOTO_DOMAIN_PATH + follow.getAvatar();
        GlideHelper.displayHeader(mContext, iv_avatar, url);

        holder.setVisible(R.id.v_divider, holder.getmPosition() != mDatas.size() - 1);
        holder.setVisible(R.id.v_top, holder.getmPosition() == 0);

        tv_nickname.setText(follow.getNick_name());

        UserHeadNickClickHelper.getInstance().setClickListener(iv_avatar, follow.getNick_name(), follow.getUser_id(), null, url);

        if (follow.getUser_desc() != null)
            tv_userdesc.setText(follow.getUser_desc());
        int attend;
        if (follow.getFollow_each()) {
            attend = AttendState.ATTENTION_EACH_OTHER;
        } else {
            if (follow_fans.equals("follow")) {
                attend = AttendState.ATTENDING;
            } else {
                attend = AttendState.NOT_ATTEND;
            }
        }

        FollowButtonHelper.setTextState(tv_follow, attend, follow.getUser_id());

    }
}
