package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class UserOrderDetail {


    /**
     * msg : success
     * code : 1
     * datas : {"orderDetail":{"deliverTime":"","city":"阜新市","zuoDanId":444,"stateValue":8,"county":"新邱区","shopName":"苹果零售店-政七街店","z_code":"Z281506673560391","shopPayMoney":"66.60","goodsOrderList":[{"goodsOrderId":51,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"颜色:黄,版本:258G","goodsId":1,"num":1,"payOnDelivery":1,"goodsSpecificationId":2,"hasComment":false,"goodsName":"苹果7","picture":"http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png"}],"z_integral":333,"z_shopYingYeMoney":"6666.00","addressDetail":"通知我","province":"辽宁省","createTime":"2017-09-29 16:26:00","cancelTime":"2017-09-29 17:03:13","phone":"157699985588","name":"口味","shopId":1,"state":"已取消"}}
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
         * orderDetail : {"deliverTime":"","city":"阜新市","zuoDanId":444,"stateValue":8,"county":"新邱区","shopName":"苹果零售店-政七街店","z_code":"Z281506673560391","shopPayMoney":"66.60","goodsOrderList":[{"goodsOrderId":51,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"颜色:黄,版本:258G","goodsId":1,"num":1,"payOnDelivery":1,"goodsSpecificationId":2,"hasComment":false,"goodsName":"苹果7","picture":"http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png"}],"z_integral":333,"z_shopYingYeMoney":"6666.00","addressDetail":"通知我","province":"辽宁省","createTime":"2017-09-29 16:26:00","cancelTime":"2017-09-29 17:03:13","phone":"157699985588","name":"口味","shopId":1,"state":"已取消"}
         */

        private OrderDetailBean orderDetail;

        public OrderDetailBean getOrderDetail() {
            return orderDetail;
        }

        public void setOrderDetail(OrderDetailBean orderDetail) {
            this.orderDetail = orderDetail;
        }

        public static class OrderDetailBean {
            /**
             * deliverTime :
             * city : 阜新市
             * zuoDanId : 444
             * stateValue : 8
             * county : 新邱区
             * shopName : 苹果零售店-政七街店
             * z_code : Z281506673560391
             * shopPayMoney : 66.60
             * goodsOrderList : [{"goodsOrderId":51,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"颜色:黄,版本:258G","goodsId":1,"num":1,"payOnDelivery":1,"goodsSpecificationId":2,"hasComment":false,"goodsName":"苹果7","picture":"http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png"}]
             * z_integral : 333
             * z_shopYingYeMoney : 6666.00
             * addressDetail : 通知我
             * province : 辽宁省
             * createTime : 2017-09-29 16:26:00
             * cancelTime : 2017-09-29 17:03:13
             * phone : 157699985588
             * name : 口味
             * shopId : 1
             * state : 已取消
             */

            private String deliverTime;
            private String city;
            private int zuoDanId;
            private int stateValue;
            private String county;
            private String shopName;
            private String z_code;
            private String shopPayMoney;
            private int z_integral;
            private String z_shopYingYeMoney;
            private String addressDetail;
            private String province;
            private String createTime;
            private String cancelTime;
            private String phone;
            private String name;
            private int shopId;
            private String state;
            private List<GoodsOrderListBean> goodsOrderList;

            public String getDeliverTime() {
                return deliverTime;
            }

            public void setDeliverTime(String deliverTime) {
                this.deliverTime = deliverTime;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public int getZuoDanId() {
                return zuoDanId;
            }

            public void setZuoDanId(int zuoDanId) {
                this.zuoDanId = zuoDanId;
            }

            public int getStateValue() {
                return stateValue;
            }

            public void setStateValue(int stateValue) {
                this.stateValue = stateValue;
            }

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getZ_code() {
                return z_code;
            }

            public void setZ_code(String z_code) {
                this.z_code = z_code;
            }

            public String getShopPayMoney() {
                return shopPayMoney;
            }

            public void setShopPayMoney(String shopPayMoney) {
                this.shopPayMoney = shopPayMoney;
            }

            public int getZ_integral() {
                return z_integral;
            }

            public void setZ_integral(int z_integral) {
                this.z_integral = z_integral;
            }

            public String getZ_shopYingYeMoney() {
                return z_shopYingYeMoney;
            }

            public void setZ_shopYingYeMoney(String z_shopYingYeMoney) {
                this.z_shopYingYeMoney = z_shopYingYeMoney;
            }

            public String getAddressDetail() {
                return addressDetail;
            }

            public void setAddressDetail(String addressDetail) {
                this.addressDetail = addressDetail;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCancelTime() {
                return cancelTime;
            }

            public void setCancelTime(String cancelTime) {
                this.cancelTime = cancelTime;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public List<GoodsOrderListBean> getGoodsOrderList() {
                return goodsOrderList;
            }

            public void setGoodsOrderList(List<GoodsOrderListBean> goodsOrderList) {
                this.goodsOrderList = goodsOrderList;
            }

            public static class GoodsOrderListBean {
                /**
                 * goodsOrderId : 51
                 * marketPrice : 8888.00
                 * salePrice : 6666.00
                 * goodsSpecification : 颜色:黄,版本:258G
                 * goodsId : 1
                 * num : 1
                 * payOnDelivery : 1
                 * goodsSpecificationId : 2
                 * hasComment : false
                 * goodsName : 苹果7
                 * picture : http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png
                 */

                private int goodsOrderId;
                private String marketPrice;
                private String salePrice;
                private String goodsSpecification;
                private int goodsId;
                private int num;
                private int payOnDelivery;
                private String goodsSpecificationId;
                private boolean hasComment;
                private String goodsName;
                private String picture;

                public int getGoodsOrderId() {
                    return goodsOrderId;
                }

                public void setGoodsOrderId(int goodsOrderId) {
                    this.goodsOrderId = goodsOrderId;
                }

                public String getMarketPrice() {
                    return marketPrice;
                }

                public void setMarketPrice(String marketPrice) {
                    this.marketPrice = marketPrice;
                }

                public String getSalePrice() {
                    return salePrice;
                }

                public void setSalePrice(String salePrice) {
                    this.salePrice = salePrice;
                }

                public String getGoodsSpecification() {
                    return goodsSpecification;
                }

                public void setGoodsSpecification(String goodsSpecification) {
                    this.goodsSpecification = goodsSpecification;
                }

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public int getPayOnDelivery() {
                    return payOnDelivery;
                }

                public void setPayOnDelivery(int payOnDelivery) {
                    this.payOnDelivery = payOnDelivery;
                }

                public String getGoodsSpecificationId() {
                    return goodsSpecificationId;
                }

                public void setGoodsSpecificationId(String goodsSpecificationId) {
                    this.goodsSpecificationId = goodsSpecificationId;
                }

                public boolean isHasComment() {
                    return hasComment;
                }

                public void setHasComment(boolean hasComment) {
                    this.hasComment = hasComment;
                }

                public String getGoodsName() {
                    return goodsName;
                }

                public void setGoodsName(String goodsName) {
                    this.goodsName = goodsName;
                }

                public String getPicture() {
                    return picture;
                }

                public void setPicture(String picture) {
                    this.picture = picture;
                }
            }
        }
    }
}
