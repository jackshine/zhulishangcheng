package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/21.
 * 获得用户消息
 */

public class NewsBean {

    /**
     * msg : success
     * code : 1
     * datas : {"totalPage":1,"pageNo":1,"messages":[{"ifRead":false,"createTime":"2017-06-13","id":2,"title":"king","content":"k4"},{"ifRead":false,"createTime":"2017-06-13","id":1,"title":"kd","content":"kkkk"}],"recordsTotal":2}
     */

    private String msg;
    private int code;
    private DatasBean datas;

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

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * totalPage : 1
         * pageNo : 1
         * messages : [{"ifRead":false,"createTime":"2017-06-13","id":2,"title":"king","content":"k4"},{"ifRead":false,"createTime":"2017-06-13","id":1,"title":"kd","content":"kkkk"}]
         * recordsTotal : 2
         */

        private int totalPage;
        private int pageNo;
        private int recordsTotal;
        private List<MessagesBean> messages;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getRecordsTotal() {
            return recordsTotal;
        }

        public void setRecordsTotal(int recordsTotal) {
            this.recordsTotal = recordsTotal;
        }

        public List<MessagesBean> getMessages() {
            return messages;
        }

        public void setMessages(List<MessagesBean> messages) {
            this.messages = messages;
        }

        public static class MessagesBean {
            /**
             * ifRead : false
             * createTime : 2017-06-13
             * id : 2
             * title : king
             * content : k4
             */
            private boolean ifRead;
            private String createTime;
            private int id;
            private String title;
            private String content;
            public boolean isIfRead() {
                return ifRead;
            }

            public void setIfRead(boolean ifRead) {
                this.ifRead = ifRead;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
