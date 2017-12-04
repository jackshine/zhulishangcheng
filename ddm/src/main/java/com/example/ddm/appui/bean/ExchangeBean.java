package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/27.
 * 兑换明细
 */

public class ExchangeBean {
    /**
     * msg : success
     * code : 1
     * datas : [{"actualMoney":"99.00","code":"TX151500972965456","accountName":"699666","stateValue":2,"handTime":"2017-07-25 17:05","bankName":"建设银行","userName":"15093112512","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/3.png","factorage":"1.00","cardNum":"0000","money":"100.00","createTime":"2017-07-25 16:56","id":7,"state":"审核不通过"},{"actualMoney":"99.00","code":"TX151499659098650","accountName":"李","stateValue":3,"handTime":"2017-07-10 17:22","bankName":"建设银行","userName":"15093112512","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/3.png","factorage":"1.00","cardNum":"9123","money":"100.00","createTime":"2017-07-10 12:01","id":1,"state":"审核通过"}]
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
         * actualMoney : 99.00
         * code : TX151500972965456
         * accountName : 699666
         * stateValue : 2
         * handTime : 2017-07-25 17:05
         * bankName : 建设银行
         * userName : 15093112512
         * bankImg : http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/3.png
         * factorage : 1.00
         * cardNum : 0000
         * money : 100.00
         * createTime : 2017-07-25 16:56
         * id : 7
         * state : 审核不通过
         */

        private String actualMoney;
        private String code;
        private String accountName;
        private int stateValue;
        private String handTime;
        private String bankName;
        private String userName;
        private String bankImg;
        private String factorage;
        private String cardNum;
        private String money;
        private String createTime;
        private int id;
        private String state;

        public String getActualMoney() {
            return actualMoney;
        }

        public void setActualMoney(String actualMoney) {
            this.actualMoney = actualMoney;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public int getStateValue() {
            return stateValue;
        }

        public void setStateValue(int stateValue) {
            this.stateValue = stateValue;
        }

        public String getHandTime() {
            return handTime;
        }

        public void setHandTime(String handTime) {
            this.handTime = handTime;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getBankImg() {
            return bankImg;
        }

        public void setBankImg(String bankImg) {
            this.bankImg = bankImg;
        }

        public String getFactorage() {
            return factorage;
        }

        public void setFactorage(String factorage) {
            this.factorage = factorage;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
