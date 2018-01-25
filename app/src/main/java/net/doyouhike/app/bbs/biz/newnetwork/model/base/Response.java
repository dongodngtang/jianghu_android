package net.doyouhike.app.bbs.biz.newnetwork.model.base;

/**
 *
 * Created by zaitu on 15-11-18.
 *
 *
 * 网络响应的通用格式
 * { code: 0, msg: 'msg', data:{} }
 * 网络响应基本类,泛型DATA 对应json的data部分
 *
 */
public class Response<DATA> {

    /**
     * 响应的版本信息
     * version : OpenAPI 2.0
     * build : v0.1.5.0
     * copyright : copyright(c) 2016 @zaitu.cn
     */
    private MetaBean meta;
    /**
     * 成功失败
     */
    private boolean isSuccess=false;
    /**
     * 网络响应的json字符串
     */
    private String strJSON;
    /**
     * 响应的code
     */
    private int code;
    /**
     * 消息
     */
    private String msg;
    /**
     * data部分字符串形式
     */
    private String strData;
    /**
     * 标签,用于携带额外信息
     */
    private Object extraTag;
    /**
     * 对应json data部分
     */
    private DATA mData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getStrJSON() {
        return strJSON;
    }

    public void setStrJSON(String strJSON) {
        this.strJSON = strJSON;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getExtraTag() {
        return extraTag;
    }

    public void setExtraTag(Object tag) {
        this.extraTag = tag;
    }

    public String getStrData() {
        return strData;
    }

    public void setStrData(String data) {
        this.strData = data;
    }

    public DATA getData() {
        return mData;
    }

    public void setData(DATA data) {
        this.mData = data;
    }

    public static class MetaBean {
        private String version;
        private String build;
        private String copyright;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getBuild() {
            return build;
        }

        public void setBuild(String build) {
            this.build = build;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }
    }
}
