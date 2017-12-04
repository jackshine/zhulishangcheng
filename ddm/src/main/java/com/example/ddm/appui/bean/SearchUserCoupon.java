package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class SearchUserCoupon {

    /**
     * msg : success
     * code : 1
     * datas : {"datas":[{"starTime":"2017-09-21 09:56:16","orderMoney":888,"name":"满减优惠券","shopName":"苹果零售-百邦店","id":1,"endTime":"2017-09-21 09:56:16","state":"有效","minusMoney":111}],"totalPage":1,"recordsTotal":1,"pageNow":1}
     */

    private String msg;
    private int code;
    private DatasBeanX datas;

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

    public DatasBeanX getDatas() {
        return datas;
    }

    public void setDatas(DatasBeanX datas) {
        this.datas = datas;
    }

    public static class DatasBeanX {
        /**
         * datas : [{"starTime":"2017-09-21 09:56:16","orderMoney":888,"name":"满减优惠券","shopName":"苹果零售-百邦店","id":1,"endTime":"2017-09-21 09:56:16","state":"有效","minusMoney":111}]
         * totalPage : 1
         * recordsTotal : 1
         * pageNow : 1
         */

        private int totalPage;
        private int recordsTotal;
        private int pageNow;
        private List<DatasBean> datas;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getRecordsTotal() {
            return recordsTotal;
        }

        public void setRecordsTotal(int recordsTotal) {
            this.recordsTotal = recordsTotal;
        }

        public int getPageNow() {
            return pageNow;
        }

        public void setPageNow(int pageNow) {
            this.pageNow = pageNow;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * starTime : 2017-09-21 09:56:16
             * orderMoney : 888
             * name : 满减优惠券
             * shopName : 苹果零售-百邦店
             * id : 1
             * endTime : 2017-09-21 09:56:16
             * state : 有效
             * minusMoney : 111
             */

            private String starTime;
            private String orderMoney;
            private String name;
            private String shopName;
            private int id;
            private String endTime;
            private String state;
            private String minusMoney;

            public String getStarTime() {
                return starTime;
            }

            public void setStarTime(String starTime) {
                this.starTime = starTime;
            }

            public String getOrderMoney() {
                return orderMoney;
            }

            public void setOrderMoney(String orderMoney) {
                this.orderMoney = orderMoney;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getMinusMoney() {
                return minusMoney;
            }

            public void setMinusMoney(String minusMoney) {
                this.minusMoney = minusMoney;
            }
        }
    }
}
