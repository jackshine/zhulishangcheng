package com.example.ddm.appui.bean;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class ConfirmOrder {

    /**
     * msg : success
     * code : 1
     * datas : {"totalMoney":"19998","goodsNum":1,"totalIntegral":"999"}
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
         * totalMoney : 19998
         * goodsNum : 1
         * totalIntegral : 999
         */

        private String totalMoney;
        private int goodsNum;
        private String totalIntegral;

        public String getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(String totalMoney) {
            this.totalMoney = totalMoney;
        }

        public int getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(int goodsNum) {
            this.goodsNum = goodsNum;
        }

        public String getTotalIntegral() {
            return totalIntegral;
        }

        public void setTotalIntegral(String totalIntegral) {
            this.totalIntegral = totalIntegral;
        }
    }
}
