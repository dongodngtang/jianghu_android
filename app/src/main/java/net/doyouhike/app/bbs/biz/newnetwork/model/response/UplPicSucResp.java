package net.doyouhike.app.bbs.biz.newnetwork.model.response;

/**
 * Created by zaitu on 15-12-3.
 */
public class UplPicSucResp {

    /**
     * imgName : 45daab234c447f24f3321dc43da76da3
     * imgSize : 51063
     * imgURL : http://static.test.doyouhike.net/files/2015/12/03/9/9dccc33201140e1c5ca39721c9e8a1e9_m.jpg
     * photoID : 1704563
     */

    private String imgName;
    private int imgSize;
    private String imgURL;
    private int photoID;

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public void setImgSize(int imgSize) {
        this.imgSize = imgSize;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public String getImgName() {
        return imgName;
    }

    public int getImgSize() {
        return imgSize;
    }

    public String getImgURL() {
        return imgURL;
    }

    public int getPhotoID() {
        return photoID;
    }
}
