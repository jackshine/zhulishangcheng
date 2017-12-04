package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/22.
 *
 */

public class MineBean {

    /**
     * msg : success
     * code : 1
     * datas : {"UserTypeValue":2,"SddNum":154,"countCard":10,"Avotorr":"http://image.ddmzl.com/0a9fcddd1b9149d6a872cf32fb1f052620170718.gif","CddNum":0,"stateValue":1,"AccountMoney":"1423584.80","RealName":"李","state":"有效","SexValue":7,"UserType":"商户","ShopId":15}
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
         * UserTypeValue : 2
         * SddNum : 154
         * countCard : 10
         * Avotorr : http://image.ddmzl.com/0a9fcddd1b9149d6a872cf32fb1f052620170718.gif
         * CddNum : 0
         * stateValue : 1
         * AccountMoney : 1423584.80
         * RealName : 李
         * state : 有效
         * SexValue : 7
         * UserType : 商户
         * ShopId : 15
         */

        private int UserTypeValue;
        private int SddNum;
        private int countCard;
        private String Avotorr;
        private int CddNum;
        private int stateValue;
        private String AccountMoney;
        private String RealName;
        private String state;
        private int SexValue;
        private String UserType;
        private int ShopId;

        public int getUserTypeValue() {
            return UserTypeValue;
        }

        public void setUserTypeValue(int UserTypeValue) {
            this.UserTypeValue = UserTypeValue;
        }

        public int getSddNum() {
            return SddNum;
        }

        public void setSddNum(int SddNum) {
            this.SddNum = SddNum;
        }

        public int getCountCard() {
            return countCard;
        }

        public void setCountCard(int countCard) {
            this.countCard = countCard;
        }

        public String getAvotorr() {
            return Avotorr;
        }

        public void setAvotorr(String Avotorr) {
            this.Avotorr = Avotorr;
        }

        public int getCddNum() {
            return CddNum;
        }

        public void setCddNum(int CddNum) {
            this.CddNum = CddNum;
        }

        public int getStateValue() {
            return stateValue;
        }

        public void setStateValue(int stateValue) {
            this.stateValue = stateValue;
        }

        public String getAccountMoney() {
            return AccountMoney;
        }

        public void setAccountMoney(String AccountMoney) {
            this.AccountMoney = AccountMoney;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getSexValue() {
            return SexValue;
        }

        public void setSexValue(int SexValue) {
            this.SexValue = SexValue;
        }

        public String getUserType() {
            return UserType;
        }

        public void setUserType(String UserType) {
            this.UserType = UserType;
        }

        public int getShopId() {
            return ShopId;
        }

        public void setShopId(int ShopId) {
            this.ShopId = ShopId;
        }
    }
}
