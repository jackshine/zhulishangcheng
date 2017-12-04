package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/22.
 */

public class NewsDetailBean {

    /**
     * msg : success
     * code : 1
     * datas : {"message":{"ifRead":false,"createTime":"2017-06-13","id":1,"title":"kd","content":"kkkk"}}
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
         * message : {"ifRead":false,"createTime":"2017-06-13","id":1,"title":"kd","content":"kkkk"}
         */

        private MessageBean message;

        public MessageBean getMessage() {
            return message;
        }

        public void setMessage(MessageBean message) {
            this.message = message;
        }

        public static class MessageBean {
            /**
             * ifRead : false
             * createTime : 2017-06-13
             * id : 1
             * title : kd
             * content : kkkk
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
