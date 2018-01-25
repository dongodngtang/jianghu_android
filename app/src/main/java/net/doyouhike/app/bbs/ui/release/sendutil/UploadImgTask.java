package net.doyouhike.app.bbs.ui.release.sendutil;

import android.content.Context;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.biz.openapi.presenter.UploaderHelper;
import net.doyouhike.app.bbs.biz.openapi.request.upload.UploaderPhotoPost;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventDetailResp;
import net.doyouhike.app.bbs.biz.openapi.response.uploader.UploaderPhotoResp;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.StringUtil;

/**
 * 功能：
 *
 * @author：曾江 日期：16-2-29.
 */
public class UploadImgTask implements Runnable {

    private String TAG = SendUtil.class.getSimpleName();

    BaseSendEntity liveEntity;
    Context context;

    public UploadImgTask(Context context, BaseSendEntity liveEntity) {
        this.liveEntity = liveEntity;
        this.context = context;
    }

    @Override
    public void run() {


        while (true) {

            if (liveEntity.getHasCancel()) {
                return;
            }

            if (liveEntity.getHasSend()) {
                return;
            }

            //获取需要上传的图片
            final EventDetailResp.ContentBean item = liveEntity.getWaitUploadImg();

            if (null == item) {
                //无上传图片,则发送直播数据
                liveEntity.onUploadSuc();
                return;
            }
            if (StringUtil.isNotEmpty(item.getPhoto_id())) {
                //无上传图片,则发送直播数据
                liveEntity.onUploadSuc();
                return;
            }

            item.setStatus(Timeline.ImagesEntity.UPLOADING);

            LogUtil.d(TAG, "正在上传：" + item.getWhole_photo_path());

            UploaderHelper.getInstance().uploaderPhoto(context,
                    item.getWhole_photo_path(), "temporary",
                    new IOnResponseListener<Response<UploaderPhotoResp>>() {

                        @Override
                        public void onSuccess(Response<UploaderPhotoResp> response) {

                            if (response.getData() != null) {
                                UploaderPhotoPost post = (UploaderPhotoPost) response.getExtraTag();
                                if (post != null && post.getPhotoPath() != null)
                                    for (EventDetailResp.ContentBean bean : liveEntity.getImgs()) {
                                        if (bean.getWhole_photo_path() != null &&
                                                bean.getWhole_photo_path().equals(post.getPhotoPath())) {
                                            LogUtil.d("图片上传:" + post.getPhotoPath() +
                                                    " 队列里的:" + bean.getWhole_photo_path());
                                            bean.setStatus(EventDetailResp.ContentBean.UPLOAD_OK);
                                            bean.setPhoto_id(response.getData().getPhoto_id());
                                            liveEntity.onUploadSuc();
                                            break;
                                        }
                                    }
                            }
                        }

                        @Override
                        public void onError(Response response) {
                            UploaderPhotoPost post = (UploaderPhotoPost) response.getExtraTag();
                            if (post != null && post.getPhotoPath() != null)
                                for (EventDetailResp.ContentBean bean : liveEntity.getImgs()) {
                                    if (bean.getWhole_photo_path().equals(post.getPhotoPath())) {
                                        bean.setPhoto_id(null);
                                        bean.setStatus(EventDetailResp.ContentBean.UPLOAD_FAIL);
                                        liveEntity.onUploadFail();
                                        break;
                                    }
                                }
                        }
                    });

        }

    }


    public interface UploadImgTaskListener {
        void onUploadSuc(BaseSendEntity liveEntity);

        void onUploadFail(BaseSendEntity liveEntity);
    }
}
