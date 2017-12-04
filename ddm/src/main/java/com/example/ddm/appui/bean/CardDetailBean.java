package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/21.
 * 银行卡详情
 */

public class CardDetailBean {
    /**
     * msg : success
     * code : 1
     * datas : {"cardNum":"66778899556633","bankId":1,"bank":"工商银行","accountName":"king","kaihuhang":"二七支行","id":16,"state":false,"userKey":"93328a05-aee0-434a-a65d-9dba06845dd4"}
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
         * cardNum : 66778899556633
         * bankId : 1
         * bank : 工商银行
         * accountName : king
         * kaihuhang : 二七支行
         * id : 16
         * state : false
         * userKey : 93328a05-aee0-434a-a65d-9dba06845dd4
         */

        private String cardNum;
        private int bankId;
        private String bank;
        private String accountName;
        private String kaihuhang;
        private int id;
        private boolean state;
        private String userKey;

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public int getBankId() {
            return bankId;
        }

        public void setBankId(int bankId) {
            this.bankId = bankId;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getKaihuhang() {
            return kaihuhang;
        }

        public void setKaihuhang(String kaihuhang) {
            this.kaihuhang = kaihuhang;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }

        public String getUserKey() {
            return userKey;
        }

        public void setUserKey(String userKey) {
            this.userKey = userKey;
        }
    }
}
