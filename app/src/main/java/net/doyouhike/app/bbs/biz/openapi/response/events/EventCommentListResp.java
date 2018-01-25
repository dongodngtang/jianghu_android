package net.doyouhike.app.bbs.biz.openapi.response.events;

import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodesCommentPost;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.util.StringUtil;

import java.util.List;

/**
 * 作者：luochangdong on 16/9/14
 * 描述：
 */
public class EventCommentListResp {

    /**
     * items : [{"comment_id":3442497,"created_at":1468460840,"source_type":"web","user":{"user_id":"4409900c88537ce0985756d009801a44","nick_name":"mtv433","avatar":"files/faces/4/0/4062ff528.jpg"},"reply_to":{"comment_id":432440,"user_id":"c3422e119493b6f711e656d0094a45a5","nick_name":"暑假有约"},"content":[{"type":"text","content":"好吃~好吃~~ "},{"type":"image","photo_path":"2016/07/14/9/93db3e17e49f28a1878a29486dbf08f4","photo_ext":"jpg"}]}]
     * photo_domain_path : http://c1.zdb.io/
     * page : {"page_index":1,"page_size":1,"page_count":8,"total_records":8}
     */

    private String photo_domain_path;
    /**
     * page_index : 1
     * page_size : 1
     * page_count : 8
     * total_records : 8
     */

    private PageBean page;
    /**
     * comment_id : 3442497
     * created_at : 1468460840
     * source_type : web
     * user : {"user_id":"4409900c88537ce0985756d009801a44","nick_name":"mtv433","avatar":"files/faces/4/0/4062ff528.jpg"}
     * reply_to : {"comment_id":432440,"user_id":"c3422e119493b6f711e656d0094a45a5","nick_name":"暑假有约"}
     * content : [{"type":"text","content":"好吃~好吃~~ "},{"type":"image","photo_path":"2016/07/14/9/93db3e17e49f28a1878a29486dbf08f4","photo_ext":"jpg"}]
     */

    private List<ItemsBean> items;

    public String getPhoto_domain_path() {
        return photo_domain_path;
    }

    public void setPhoto_domain_path(String photo_domain_path) {
        this.photo_domain_path = photo_domain_path;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class PageBean {
        private int page_index;
        private int page_size;
        private int page_count;
        private int total_records;

        public int getPage_index() {
            return page_index;
        }

        public void setPage_index(int page_index) {
            this.page_index = page_index;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }

        public int getTotal_records() {
            return total_records;
        }

        public void setTotal_records(int total_records) {
            this.total_records = total_records;
        }
    }

    public static class ItemsBean {
        private String comment_id;
        private long created_at;
        private String source_type;
        /**
         * user_id : 4409900c88537ce0985756d009801a44
         * nick_name : mtv433
         * avatar : files/faces/4/0/4062ff528.jpg
         */

        private UserBean user;
        /**
         * comment_id : 432440
         * user_id : c3422e119493b6f711e656d0094a45a5
         * nick_name : 暑假有约
         */

        private ReplyToBean reply_to;
        /**
         * type : text
         * content : 好吃~好吃~~
         */

        private List<NodesCommentPost.ContentBean> content;

        public String contentToString(){

            if (null==content){
                return "";
            }

            StringBuilder stringBuilder=new StringBuilder();

            for (NodesCommentPost.ContentBean bean :content){
                if (bean.getType().equals(Constant.TXET)){
                    stringBuilder.append(bean.getContent()).append("\n");
                }
            }

            String strComment= StringUtil.removeEnd(stringBuilder.toString(),"\n");
            return strComment;
        }

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public String getSource_type() {
            return source_type;
        }

        public void setSource_type(String source_type) {
            this.source_type = source_type;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public ReplyToBean getReply_to() {
            return reply_to;
        }

        public void setReply_to(ReplyToBean reply_to) {
            this.reply_to = reply_to;
        }

        public List<NodesCommentPost.ContentBean> getContent() {
            return content;
        }

        public void setContent(List<NodesCommentPost.ContentBean> content) {
            this.content = content;
        }

        public static class UserBean {
            private String user_id;
            private String nick_name;
            private String avatar;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }

        public static class ReplyToBean {
            private String comment_id;
            private String user_id;
            private String nick_name;

            public String getComment_id() {
                return comment_id;
            }

            public void setComment_id(String comment_id) {
                this.comment_id = comment_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }
        }


    }
}
