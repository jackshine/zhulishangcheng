package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/22.
 * 做单验证手机号
 */

public class CheckBean {

    /**
     * msg : success
     * code : 1
     * datas : {"realName":"zlq","name":"18037350011","id":42}
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
         * realName : zlq
         * name : 18037350011
         * id : 42
         */

        private String realName;
        private String name;
        private int id;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

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
    }
}
