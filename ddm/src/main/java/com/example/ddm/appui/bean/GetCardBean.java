package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/14.
 *
 */

public class GetCardBean {

    /**
     * msg : success
     * code : 1
     * datas : {"cardNum":"666666666666666","bankId":1,"accountName":"飞龙哥","kaihuhang":"时也命也","id":1,"state":true}
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
         * cardNum : 666666666666666
         * bankId : 1
         * accountName : 飞龙哥
         * kaihuhang : 时也命也
         * id : 1
         * state : true
         */
        private String cardNum;
        private int bankId;
        private String accountName;
        private String kaihuhang;
        private int id;
        private boolean state;

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
    }
}
