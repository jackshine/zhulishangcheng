package com.example.ddm.appui.bean;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class GetCouponNumber {

    /**
     * msg : success
     * code : 1
     * datas : {"yiguoqi":1,"youxiao":1,"yishiyong":1}
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
         * yiguoqi : 1
         * youxiao : 1
         * yishiyong : 1
         */

        private int yiguoqi;
        private int youxiao;
        private int yishiyong;

        public int getYiguoqi() {
            return yiguoqi;
        }

        public void setYiguoqi(int yiguoqi) {
            this.yiguoqi = yiguoqi;
        }

        public int getYouxiao() {
            return youxiao;
        }

        public void setYouxiao(int youxiao) {
            this.youxiao = youxiao;
        }

        public int getYishiyong() {
            return yishiyong;
        }

        public void setYishiyong(int yishiyong) {
            this.yishiyong = yishiyong;
        }
    }
}
