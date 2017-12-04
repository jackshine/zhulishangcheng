package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/28.
 * 版本号
 */

public class VersionBean {

    /**
     * msg : success
     * code : 1
     * datas : {"iosVersionCode":"1.0","isoMemo":"b","androidVersionCode":"1.0","androidMemo":"a"}
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
         * iosVersionCode : 1.0
         * isoMemo : b
         * androidVersionCode : 1.0
         * androidMemo : a
         */

        private String iosVersionCode;
        private String isoMemo;
        private String androidVersionCode;
        private String androidMemo;

        public String getIosVersionCode() {
            return iosVersionCode;
        }

        public void setIosVersionCode(String iosVersionCode) {
            this.iosVersionCode = iosVersionCode;
        }

        public String getIsoMemo() {
            return isoMemo;
        }

        public void setIsoMemo(String isoMemo) {
            this.isoMemo = isoMemo;
        }

        public String getAndroidVersionCode() {
            return androidVersionCode;
        }

        public void setAndroidVersionCode(String androidVersionCode) {
            this.androidVersionCode = androidVersionCode;
        }

        public String getAndroidMemo() {
            return androidMemo;
        }

        public void setAndroidMemo(String androidMemo) {
            this.androidMemo = androidMemo;
        }
    }
}
