package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/18.
 * 账户概况数据
 */

public class AccountBean {


    /**
     * datas : {"sdd":189,"accountMoney":"2169.40","order":5580,"userTypeValue":2,"cdd":5,"tiXianMoney":0,"shopOrder":"106097.60","userType":"商户"}
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
         * sdd : 189
         * accountMoney : 2169.40
         * order : 5580
         * userTypeValue : 2
         * cdd : 5
         * tiXianMoney : 0
         * shopOrder : 106097.60
         * userType : 商户
         */

        private int sdd;
        private String accountMoney;
        private int order;
        private int userTypeValue;
        private int cdd;
        private int tiXianMoney;
        private String shopOrder;
        private String userType;

        public int getSdd() {
            return sdd;
        }

        public void setSdd(int sdd) {
            this.sdd = sdd;
        }

        public String getAccountMoney() {
            return accountMoney;
        }

        public void setAccountMoney(String accountMoney) {
            this.accountMoney = accountMoney;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getUserTypeValue() {
            return userTypeValue;
        }

        public void setUserTypeValue(int userTypeValue) {
            this.userTypeValue = userTypeValue;
        }

        public int getCdd() {
            return cdd;
        }

        public void setCdd(int cdd) {
            this.cdd = cdd;
        }

        public int getTiXianMoney() {
            return tiXianMoney;
        }

        public void setTiXianMoney(int tiXianMoney) {
            this.tiXianMoney = tiXianMoney;
        }

        public String getShopOrder() {
            return shopOrder;
        }

        public void setShopOrder(String shopOrder) {
            this.shopOrder = shopOrder;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }
    }
}
