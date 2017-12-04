package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class CommentBean {

    /**
     * shopGraded : 5.0
     * msg : success
     * code : 1
     * userCustomersSum : 22
     * commentsSumdatas : [{"commentsTypeValue":1,"commentsSum":0,"commentsType":"最新评价"},{"commentsTypeValue":2,"commentsSum":0,"commentsType":"晒图"},{"commentsTypeValue":3,"commentsSum":0,"commentsType":"好评"},{"commentsTypeValue":4,"commentsSum":0,"commentsType":"差评"},{"commentsTypeValue":5,"commentsSum":0,"commentsType":"五星"}]
     */

    private String shopGraded;
    private String msg;
    private int code;
    private int userCustomersSum;
    private List<CommentsSumdatasBean> commentsSumdatas;

    public String getShopGraded() {
        return shopGraded;
    }

    public void setShopGraded(String shopGraded) {
        this.shopGraded = shopGraded;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getUserCustomersSum() {
        return userCustomersSum;
    }

    public void setUserCustomersSum(int userCustomersSum) {
        this.userCustomersSum = userCustomersSum;
    }

    public List<CommentsSumdatasBean> getCommentsSumdatas() {
        return commentsSumdatas;
    }

    public void setCommentsSumdatas(List<CommentsSumdatasBean> commentsSumdatas) {
        this.commentsSumdatas = commentsSumdatas;
    }

    public static class CommentsSumdatasBean {
        /**
         * commentsTypeValue : 1
         * commentsSum : 0
         * commentsType : 最新评价
         */

        private int commentsTypeValue;
        private int commentsSum;
        private String commentsType;

        public int getCommentsTypeValue() {
            return commentsTypeValue;
        }

        public void setCommentsTypeValue(int commentsTypeValue) {
            this.commentsTypeValue = commentsTypeValue;
        }

        public int getCommentsSum() {
            return commentsSum;
        }

        public void setCommentsSum(int commentsSum) {
            this.commentsSum = commentsSum;
        }

        public String getCommentsType() {
            return commentsType;
        }

        public void setCommentsType(String commentsType) {
            this.commentsType = commentsType;
        }
    }
}
