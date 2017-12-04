package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class GetSpecification {


    /**
     * msg : success
     * code : 1
     * datas : [{"data":[{"id":1,"content":"白"},
     * {"id":2,"content":"黑"},{
     * "id":3,"content":"黄"}],
     * "name":"颜色","id":2},
     * {"data":[{"id":4,"content":"32G"},
     * {"id":5,"content":"128G"},
     * {"id":6,"content":"258G"}],
     * "name":"版本","id":3}]
     */

    private String msg;
    private int code;
    private List<DatasBean> datas;

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

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * data : [{"id":1,"content":"白"},{"id":2,"content":"黑"},{"id":3,"content":"黄"}]
         * name : 颜色
         * id : 2
         */

        private String name;
        private int id;
        private List<DataBean> data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 1
             * content : 白
             */

            private int id;
            private String content;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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
