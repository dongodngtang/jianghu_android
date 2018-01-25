package net.doyouhike.app.bbs.ui.widget.me;

import android.view.View;
import android.widget.ImageView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;
import net.doyouhike.app.library.ui.uistate.SimpleUiHandler;
import net.doyouhike.app.library.ui.uistate.VaryViewHelper;

/**
 * 功能：
 * 作者：曾江
 * 日期：16-1-12.
 */
public class MeUiHandle extends SimpleUiHandler {

    private ImageView iv_temp;
    private String tempUrl;

    public MeUiHandle(View view) {
        setHelper(new VaryViewHelper(view));
    }

    public void setTempUrl(String tempUrl){
        this.tempUrl = tempUrl;
    }


    @Override
    public void onLoadingView() {
        try {
            if (null == loadingView) {
                loadingView = helper.inflate(R.layout.layout_loading_picture);
            }
            helper.showLayout(loadingView);
            if(StringUtil.isNotEmpty(tempUrl)){
                iv_temp = (ImageView) loadingView.findViewById(R.id.iv_temp);
                GlideHelper.displayNetTempImage(iv_temp.getContext(),iv_temp,tempUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
