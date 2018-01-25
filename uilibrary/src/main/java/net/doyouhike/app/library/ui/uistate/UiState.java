package net.doyouhike.app.library.ui.uistate;

/**
 * 主要实现功能：ui状态：错误、提示、正常、加载中
 * 作者：zaitu
 * 日期：15-12-23.
 */
public enum UiState {
    ERROR,TIP,NORMAL, LOADING, EMPTY,NETERR,
    CUSTOM,//自定义图片样式,需设置imgId来显示不同图片,若不设置,不显示图片.显示纯文本
    SHOW_DIALOG;//显示对话框
    private String[] msg;
    /**
     * 默认为-1，取值判断，若小于0，则不设置图片
     */
    private int imgId=-1;

    /**
     * 额外信息码
     */
    private int code=-1;

    public String[] getMsg() {
        return msg;
    }

    public int getImgId() {
        return imgId;
    }

    /**
     * @param msgs 长度约定，第一个为提示词1（粗体） 第二个为提示词2 线灰色，第三个为按钮文字
     * @return
     */
    public UiState setMsg(String ...msgs) {
        this.msg = msgs;
        return this;
    }

    /**
     * @param imgId 图片ID
     * @return
     */
    public UiState setImgId(int imgId){
        this.imgId=imgId;
        return this;
    }

    public UiState  setCode(int code) {
        this.code = code;
        return this;
    }
}
