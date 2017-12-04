package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/18.
 * 订单
 */

public class OrderBean {
    /**
     * msg : success
     * code : 1
     * datas : [{"code":"Z151499737035439","createTime":"2017-07-11 09:37","goodOnePrice":"100.00","illustrate":"","jifen":50,"shopState":"成功","shopName":"苹果零售店-政七街店","id":13,"goodsName":"手机","goodsNum":1,"shopYingYeMoney":"100.00"},{"code":"Z151499736921682","createTime":"2017-07-11 09:35","goodOnePrice":"100.00","illustrate":"","jifen":50,"shopState":"成功","shopName":"苹果零售店-政七街店","id":12,"goodsName":"手机","goodsNum":1,"shopYingYeMoney":"100.00"},{"code":"Z151499409068095","createTime":"2017-07-07 14:31","goodOnePrice":"500.00","illustrate":"","jifen":250,"shopState":"成功","shopName":"苹果零售店-政七街店","id":8,"goodsName":"手机","goodsNum":1,"shopYingYeMoney":"500.00"}]
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
         * code : Z151499737035439
         * createTime : 2017-07-11 09:37
         * goodOnePrice : 100.00
         * illustrate :
         * jifen : 50
         * shopState : 成功
         * shopName : 苹果零售店-政七街店
         * id : 13
         * goodsName : 手机
         * goodsNum : 1
         * shopYingYeMoney : 100.00
         */
        private String code;
        private String createTime;
        private String goodOnePrice;
        private String illustrate;
        private int jifen;
        private String shopState;
        private String shopName;
        private int id;
        private String goodsName;
        private int goodsNum;
        private String shopYingYeMoney;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getGoodOnePrice() {
            return goodOnePrice;
        }

        public void setGoodOnePrice(String goodOnePrice) {
            this.goodOnePrice = goodOnePrice;
        }

        public String getIllustrate() {
            return illustrate;
        }

        public void setIllustrate(String illustrate) {
            this.illustrate = illustrate;
        }

        public int getJifen() {
            return jifen;
        }

        public void setJifen(int jifen) {
            this.jifen = jifen;
        }

        public String getShopState() {
            return shopState;
        }

        public void setShopState(String shopState) {
            this.shopState = shopState;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(int goodsNum) {
            this.goodsNum = goodsNum;
        }

        public String getShopYingYeMoney() {
            return shopYingYeMoney;
        }

        public void setShopYingYeMoney(String shopYingYeMoney) {
            this.shopYingYeMoney = shopYingYeMoney;
        }
    }
}
