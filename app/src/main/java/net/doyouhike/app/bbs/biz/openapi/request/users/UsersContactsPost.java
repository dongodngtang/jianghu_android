package net.doyouhike.app.bbs.biz.openapi.request.users;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.event.open.UsersContactsEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.ResponseEventAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.users.UsersContactsResp;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/8
 * 描述：
 */
@ResponseEventAnnotation(UsersContactsEvent.class)
public class UsersContactsPost extends BasePostRequest {
    private String user_id;

    public UsersContactsPost(String user_id) {
        this.user_id = user_id;
    }

    @Expose
    private List<String> mobiles;

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<UsersContactsResp>(UsersContactsResp.class);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS + user_id + "/contacts";
    }
}
