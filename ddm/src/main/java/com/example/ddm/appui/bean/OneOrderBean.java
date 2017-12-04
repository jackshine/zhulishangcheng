package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/24.
 *
 */

public class OneOrderBean {

    /**
     * msg : success
     * code : 1
     * datas : {"ShopPayMoney":"11.10","GoodOnePrice":"111.00","ShopName":"李文亚的店面","illustrate":"","CreateTime":"2017-06-20 17:08","ShopYingYeMoney":"111.00","managerName":"樊","Code":"Z381497949721668","GoodsName":"苹果6s","CustomerUserName":"樊","State":"成功","GoodsNum":1,"ID":70,"Memo":""}
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
         * ShopPayMoney : 11.10
         * GoodOnePrice : 111.00
         * ShopName : 李文亚的店面
         * illustrate :
         * CreateTime : 2017-06-20 17:08
         * ShopYingYeMoney : 111.00
         * managerName : 樊
         * Code : Z381497949721668
         * GoodsName : 苹果6s
         * CustomerUserName : 樊
         * State : 成功
         * GoodsNum : 1
         * ID : 70
         * Memo :
         */

        private String ShopPayMoney;
        private String GoodOnePrice;
        private String ShopName;
        private String illustrate;
        private String CreateTime;
        private String ShopYingYeMoney;
        private String managerName;
        private String Code;
        private String GoodsName;
        private String CustomerUserName;
        private String State;
        private int GoodsNum;
        private int ID;
        private String Memo;

        public String getShopPayMoney() {
            return ShopPayMoney;
        }

        public void setShopPayMoney(String ShopPayMoney) {
            this.ShopPayMoney = ShopPayMoney;
        }

        public String getGoodOnePrice() {
            return GoodOnePrice;
        }

        public void setGoodOnePrice(String GoodOnePrice) {
            this.GoodOnePrice = GoodOnePrice;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public String getIllustrate() {
            return illustrate;
        }

        public void setIllustrate(String illustrate) {
            this.illustrate = illustrate;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getShopYingYeMoney() {
            return ShopYingYeMoney;
        }

        public void setShopYingYeMoney(String ShopYingYeMoney) {
            this.ShopYingYeMoney = ShopYingYeMoney;
        }

        public String getManagerName() {
            return managerName;
        }

        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }

        public String getCustomerUserName() {
            return CustomerUserName;
        }

        public void setCustomerUserName(String CustomerUserName) {
            this.CustomerUserName = CustomerUserName;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public int getGoodsNum() {
            return GoodsNum;
        }

        public void setGoodsNum(int GoodsNum) {
            this.GoodsNum = GoodsNum;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }
    }
}
