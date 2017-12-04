package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/26.
 * 待支付
 */
public class PayOrderBean {

    /**
     * datas : [{"id":207,"orderStateValue":1,"createTime":"Wed Jul 26 22:22:02 CST 2017","orderState":{"intValue":1},"userId":15,"money":"10000","code":"017192036432685"},{"id":205,"orderStateValue":1,"createTime":"Wed Jul 26 22:21:59 CST 2017","orderState":{"intValue":1},"userId":15,"money":"10000","code":"012563198716225"},{"id":206,"orderStateValue":1,"createTime":"Wed Jul 26 22:21:59 CST 2017","orderState":{"intValue":1},"userId":15,"money":"10000","code":"019745802676548"},{"id":204,"orderStateValue":1,"createTime":"Wed Jul 26 22:12:21 CST 2017","orderState":{"intValue":1},"userId":15,"money":"10000","code":"017246098366662"},{"id":203,"orderStateValue":1,"createTime":"Wed Jul 26 22:12:10 CST 2017","orderState":{"intValue":1},"userId":15,"money":"10000","code":"014673182510289"}]
     * code : 1
     * msg : success
     */
    private int code;
    private String msg;
    private List<DatasBean> datas;
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

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * id : 207
         * orderStateValue : 1
         * createTime : Wed Jul 26 22:22:02 CST 2017
         * orderState : {"intValue":1}
         * userId : 15
         * money : 10000
         * code : 017192036432685
         */

        private int id;
        private int orderStateValue;
        private String createTime;
        private OrderStateBean orderState;
        private int userId;
        private String money;
        private String code;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderStateValue() {
            return orderStateValue;
        }

        public void setOrderStateValue(int orderStateValue) {
            this.orderStateValue = orderStateValue;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public OrderStateBean getOrderState() {
            return orderState;
        }

        public void setOrderState(OrderStateBean orderState) {
            this.orderState = orderState;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public static class OrderStateBean {
            /**
             * intValue : 1
             */

            private int intValue;

            public int getIntValue() {
                return intValue;
            }

            public void setIntValue(int intValue) {
                this.intValue = intValue;
            }
        }
    }
}
