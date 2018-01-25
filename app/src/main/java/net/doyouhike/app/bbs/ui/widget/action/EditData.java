package net.doyouhike.app.bbs.ui.widget.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-4-6
 */
public class EditData implements Serializable {


    private ArrayList<String> del_photoId;

    private List<ImgText> imgTexts;

    public ArrayList<String> getDel_photoId() {
        return del_photoId;
    }

    public void setDel_photoId(ArrayList<String> del_photoId) {
        this.del_photoId = del_photoId;
    }

    public List<ImgText> getImgTexts() {
        return imgTexts;
    }

    public void setImgTexts(List<ImgText> imgTexts) {
        this.imgTexts = imgTexts;
    }

    public static class ImgText implements Serializable {
        private String inputStr;
        private String imagePath;
        private String photoId;

        /**
         * 0 旧, 1 删除原来的图再添加新图(需要上传的)
         * 不包含图片位置变化
         */
        private String isNew;


        public String getIsNew() {
            return isNew;
        }

        public void setIsNew(String isNew) {
            this.isNew = isNew;
        }

        public String getInputStr() {
            return inputStr;
        }

        public void setInputStr(String inputStr) {
            this.inputStr = inputStr;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getPhotoId() {
            return photoId;
        }

        public void setPhotoId(String photoId) {
            this.photoId = photoId;
        }
    }


}
