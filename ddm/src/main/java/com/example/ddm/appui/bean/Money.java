package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/26.
 */

public class Money {

    /**
     * datas : {"code":"017216830982431"}
     * code : 1
     * msg : success
     */

    private DatasBean datas;
    private int code;
    private String msg;

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
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

    public static class DatasBean {
        /**
         * code : 017216830982431
         */

        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
