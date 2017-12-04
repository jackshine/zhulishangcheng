package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/6.
 *得到key
 */

public class GetKeyBean {

    /**
     * msg : success
     * code : 1
     * datas : {"userKey":"8f6130a0-e5dd-4244-bd7f-016b2433f165"}
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
         * userKey : 8f6130a0-e5dd-4244-bd7f-016b2433f165
         */
        private String userKey;

        public String getUserKey() {
            return userKey;
        }
        public void setUserKey(String userKey) {
            this.userKey = userKey;
        }
    }
}
