package net.doyouhike.app.bbs.biz.openapi.request.users.ims;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetUserImResponse;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

import java.util.List;

/**
 * 作者：luochangdong on 16/9/21
 * 描述：获取用户im信息接口
 */
public class UsersImsGet extends BaseGetRequest {
    List<String> user_ids;
    String user_ids_str;


    @Override
    public String getSubUrl() {
        return OpenApiUrl.ACCOUNT_USER_IMS+user_ids_str;
    }

    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<GetUserImResponse>(GetUserImResponse.class);
    }

    @Override
    protected void setMapValue() {

    }

    public List<String> getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(List<String> user_ids) {
        this.user_ids = user_ids;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < user_ids.size(); i++) {
            sb.append(user_ids.get(i));
            if (i != user_ids.size() - 1)
                sb.append(",");
        }
        user_ids_str = sb.toString();
    }
}
