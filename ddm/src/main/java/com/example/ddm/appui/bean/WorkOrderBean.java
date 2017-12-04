package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/19.
 * 做单明细
 */

public class WorkOrderBean {

    /**
     * msg : success
     * code : 1
     * datas : [{"ShopPayMoney":"10.00","GoodOnePrice":"100.00","ShopName":"樊店铺测试子店","illustrate":"","CreateTime":"2017-07-18 16:53","ShopYingYeMoney":"100.00","managerName":"18037673875","Code":"Z11500367997191","GoodsName":"123456","CustomerUserName":"付春雨","State":"申请","GoodsNum":1,"ID":29,"Memo":""},{"ShopPayMoney":"5600.00","GoodOnePrice":"8000.00","ShopName":"樊店铺测试子店","illustrate":"没货了","CreateTime":"2017-07-14 11:28","ShopYingYeMoney":"56000.00","managerName":"18037673875","Code":"Z31500002918941","GoodsName":"iPhone","CustomerUserName":"we","State":"失败","GoodsNum":7,"ID":28,"Memo":""},{"ShopPayMoney":"1200.00","GoodOnePrice":"12.00","ShopName":"樊店铺测试子店","illustrate":"","CreateTime":"2017-07-14 11:28","ShopYingYeMoney":"12000.00","managerName":"18037673875","Code":"Z31500002881812","GoodsName":"Apple","CustomerUserName":"we","State":"失败","GoodsNum":1000,"ID":27,"Memo":""},{"ShopPayMoney":"2560.00","GoodOnePrice":"12800.00","ShopName":"樊店铺测试子店","illustrate":"","CreateTime":"2017-07-14 11:26","ShopYingYeMoney":"25600.00","managerName":"18037673875","Code":"Z31500002790282","GoodsName":"iMac","CustomerUserName":"we","State":"成功","GoodsNum":2,"ID":26,"Memo":""},{"ShopPayMoney":"5000.00","GoodOnePrice":"10000.00","ShopName":"樊店铺测试子店","illustrate":"MMP","CreateTime":"2017-07-14 09:42","ShopYingYeMoney":"50000.00","managerName":"18037673875","Code":"Z31499996528828","GoodsName":"iMac","CustomerUserName":"we","State":"失败","GoodsNum":5,"ID":25,"Memo":""},{"ShopPayMoney":"1200.00","GoodOnePrice":"12000.00","ShopName":"樊店铺测试子店","illustrate":"","CreateTime":"2017-07-14 09:37","ShopYingYeMoney":"12000.00","managerName":"18037673875","Code":"Z31499996253386","GoodsName":"iMac","CustomerUserName":"we","State":"成功","GoodsNum":1,"ID":24,"Memo":"æ\u0083 "},{"ShopPayMoney":"12.30","GoodOnePrice":"123.00","ShopName":"测试店铺主店铺","illustrate":"","CreateTime":"2017-07-13 16:47","ShopYingYeMoney":"123.00","managerName":"樊飞龙","Code":"Z41499935645251","GoodsName":"12","CustomerUserName":"樊飞龙","State":"成功","GoodsNum":1,"ID":23,"Memo":""},{"ShopPayMoney":"1160.00","GoodOnePrice":"5800.00","ShopName":"测试店铺主店铺","illustrate":"","CreateTime":"2017-07-13 09:34","ShopYingYeMoney":"11600.00","managerName":"we","Code":"Z31499909686082","GoodsName":"apple","CustomerUserName":"we","State":"成功","GoodsNum":2,"ID":22,"Memo":"ð\u009f\u0098\u008fð\u009f\u0091\u008c"},{"ShopPayMoney":"7200.00","GoodOnePrice":"9000.00","ShopName":"测试店铺主店铺","illustrate":"","CreateTime":"2017-07-13 09:31","ShopYingYeMoney":"72000.00","managerName":"we","Code":"Z31499909500682","GoodsName":"iMac ","CustomerUserName":"we","State":"成功","GoodsNum":8,"ID":21,"Memo":""},{"ShopPayMoney":"60000.00","GoodOnePrice":"600000.00","ShopName":"测试店铺主店铺","illustrate":"","CreateTime":"2017-07-12 16:51","ShopYingYeMoney":"600000.00","managerName":"付春雨","Code":"Z11499849483511","GoodsName":"123","CustomerUserName":"付春雨","State":"成功","GoodsNum":1,"ID":20,"Memo":""},{"ShopPayMoney":"5000.00","GoodOnePrice":"50000.00","ShopName":"测试店铺主店铺","illustrate":"","CreateTime":"2017-07-12 16:50","ShopYingYeMoney":"50000.00","managerName":"付春雨","Code":"Z11499849441410","GoodsName":"123456","CustomerUserName":"付春雨","State":"成功","GoodsNum":1,"ID":19,"Memo":""},{"ShopPayMoney":"4000.00","GoodOnePrice":"10000.00","ShopName":"测试店铺主店铺","illustrate":"","CreateTime":"2017-07-12 16:33","ShopYingYeMoney":"40000.00","managerName":"付春雨","Code":"Z11499848392207","GoodsName":"123456","CustomerUserName":"付春雨","State":"成功","GoodsNum":4,"ID":18,"Memo":""},{"ShopPayMoney":"1000.00","GoodOnePrice":"5000.00","ShopName":"测试店铺主店铺","illustrate":"","CreateTime":"2017-07-12 16:31","ShopYingYeMoney":"10000.00","managerName":"付春雨","Code":"Z11499848317972","GoodsName":"213","CustomerUserName":"付春雨","State":"成功","GoodsNum":2,"ID":17,"Memo":""},{"ShopPayMoney":"10.00","GoodOnePrice":"100.00","ShopName":"测试店铺主店铺","illustrate":"","CreateTime":"2017-07-12 16:27","ShopYingYeMoney":"100.00","managerName":"付春雨","Code":"Z11499848060146","GoodsName":"120","CustomerUserName":"付春雨","State":"成功","GoodsNum":1,"ID":16,"Memo":"111"},{"ShopPayMoney":"5.00","GoodOnePrice":"50.00","ShopName":"樊店铺测试子店","illustrate":"","CreateTime":"2017-07-12 14:20","ShopYingYeMoney":"50.00","managerName":"18037673875","Code":"Z41499840406705","GoodsName":"手机","CustomerUserName":"樊飞龙","State":"成功","GoodsNum":1,"ID":15,"Memo":""},{"ShopPayMoney":"12.30","GoodOnePrice":"123.00","ShopName":"测试店铺主店铺","illustrate":"","CreateTime":"2017-07-12 12:04","ShopYingYeMoney":"123.00","managerName":"樊飞龙","Code":"Z41499832256661","GoodsName":"手机","CustomerUserName":"樊飞龙","State":"成功","GoodsNum":1,"ID":14,"Memo":""},{"ShopPayMoney":"1599.90","GoodOnePrice":"15999.00","ShopName":"测试店铺主店铺","illustrate":"","CreateTime":"2017-07-07 15:01","ShopYingYeMoney":"15999.00","managerName":"周静","Code":"Z71499410862487","GoodsName":"ipad","CustomerUserName":"周静","State":"成功","GoodsNum":1,"ID":10,"Memo":""},{"ShopPayMoney":"1599.90","GoodOnePrice":"15999.00","ShopName":"测试店铺主店铺","illustrate":"","CreateTime":"2017-07-07 14:58","ShopYingYeMoney":"15999.00","managerName":"18037673875","Code":"Z401499410738466","GoodsName":"ipad","CustomerUserName":"18037673875","State":"成功","GoodsNum":1,"ID":9,"Memo":""}]
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
         * ShopPayMoney : 10.00
         * GoodOnePrice : 100.00
         * ShopName : 樊店铺测试子店
         * illustrate :
         * CreateTime : 2017-07-18 16:53
         * ShopYingYeMoney : 100.00
         * managerName : 18037673875
         * Code : Z11500367997191
         * GoodsName : 123456
         * CustomerUserName : 付春雨
         * State : 申请
         * GoodsNum : 1
         * ID : 29
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
