package net.doyouhike.app.bbs.biz.entity;

/**
 * 上传图片后返回的json中，data节点下的对应bean
 * 
 * @author wu-yoline
 *
 */
public class UpdPicFinishInfo {

	private String imgName;
	private long imgSize;
	private String imgURL;
	private String photoID;

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public long getImgSize() {
		return imgSize;
	}

	public void setImgSize(long imgSize) {
		this.imgSize = imgSize;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public String getPhotoID() {
		return photoID;
	}

	public void setPhotoID(String photoID) {
		this.photoID = photoID;
	}

	@Override
	public String toString() {
		return "UpdPicFinishInfo{" +
				"imgName='" + imgName + '\'' +
				", imgSize=" + imgSize +
				", imgURL='" + imgURL + '\'' +
				", photoID='" + photoID + '\'' +
				'}';
	}
}
