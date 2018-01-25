package net.doyouhike.app.bbs.biz.openapi.request.users.ims;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.MyImInfo;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.account.AccountUserSelfImResp;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;

/**
 * 作者：luochangdong on 16/9/21
 * 描述：获取自己im信息接口
 */
public class UsersSelfImGet extends BaseGetRequest {
    private String user_id;

    public UsersSelfImGet(String user_id) {
        this.user_id = user_id;
    }

    @Override
    protected void setMapValue() {

    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS+user_id+"/im";
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<AccountUserSelfImResp>(AccountUserSelfImResp.class){
            @Override
            protected void doNext() {
                if (result.getData()!=null&&result.getData().getIm()!=null&&!result.getData().getIm().isEmpty()){

                    MyImInfo myImInfo =result.getData().getIm().get(0);
                    //保存本地IM信息
                    SharedPreferencesManager.saveImUserInfo(myImInfo);

                }
            }
        };
    }
}
