package net.doyouhike.app.bbs.ui.release;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-1-26
 */
public class UploadImage {

    public static final int UPLOAD_OK = 1;
    public static final int UPLOADING = 2;
    public static final int UPLOAD_WAIT = 0;
    /**
     * 0 等待上传 1 上传成功 2正在发送
     */
    private int status = UPLOAD_WAIT;

    private String imgPath;

    private String uploadOkId;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getUploadOkId() {
        return uploadOkId;
    }

    public void setUploadOkId(String uploadOkId) {
        this.uploadOkId = uploadOkId;
    }
}
