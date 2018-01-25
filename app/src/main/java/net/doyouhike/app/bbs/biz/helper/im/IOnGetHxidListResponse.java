package net.doyouhike.app.bbs.biz.helper.im;

import java.util.List;
import java.util.Map;

/**
 * Created by zengjiang on 16/7/30.
 */

public interface IOnGetHxidListResponse {

    void onSuccess(Map<String, String> list);

    void onError(List<String> list);
}
