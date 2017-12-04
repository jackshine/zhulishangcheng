package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 *
 */

public class UserPurchaseOrderList {
    /**
     * msg : success
     * code : 1
     * datas : {"zuoDanList":[{"deliverTime":"","city":"阜新市","zuoDanId":435,"stateValue":1,"county":"新邱区","shopName":"苹果零售店-政七街店","z_code":"Z281506501886745","shopPayMoney":"66.60","goodsOrderList":[{"goodsOrderId":40,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"规格:中","goodsId":2924,"num":1,"payOnDelivery":1,"goodsSpecificationId":11,"hasComment":false,"goodsName":"测试","picture":"http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png,http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png,http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png"}],"z_integral":333,"z_shopYingYeMoney":"6666.00","addressDetail":"通知我","province":"辽宁省","createTime":"2017-09-27 16:44:47","cancelTime":"","phone":"157699985588","name":"口味","shopId":1,"state":"失败"},{"deliverTime":"2017-09-28 11:31:46","city":"阜新市","zuoDanId":434,"stateValue":2,"county":"新邱区","shopName":"苹果零售店-政七街店","z_code":"Z281506484262605","shopPayMoney":"66.60","goodsOrderList":[{"goodsOrderId":39,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"颜色:黄,版本:128G","goodsId":1,"num":1,"payOnDelivery":1,"goodsSpecificationId":7,"hasComment":false,"goodsName":"苹果7","picture":"http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png"}],"z_integral":333,"z_shopYingYeMoney":"6666.00","addressDetail":"通知我","province":"辽宁省","createTime":"2017-09-27 11:51:03","cancelTime":"","phone":"157699985588","name":"口味","shopId":1,"state":"成功"},{"deliverTime":"","city":"郑州市","zuoDanId":433,"stateValue":4,"county":"金水区","shopName":"苹果零售店-政七街店","z_code":"Z281506483157875","shopPayMoney":"932.40","goodsOrderList":[{"goodsOrderId":35,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"颜色:白,版本:128G","goodsId":1,"num":10,"payOnDelivery":1,"goodsSpecificationId":4,"hasComment":true,"goodsName":"苹果7","picture":"http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png"},{"goodsOrderId":36,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"颜色:黄,版本:128G","goodsId":1,"num":1,"payOnDelivery":1,"goodsSpecificationId":7,"hasComment":true,"goodsName":"苹果7","picture":"http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png"},{"goodsOrderId":37,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"规格:小","goodsId":2924,"num":2,"payOnDelivery":1,"goodsSpecificationId":12,"hasComment":true,"goodsName":"测试","picture":"http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png,http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png,http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png"},{"goodsOrderId":38,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"颜色:黄,版本:258G","goodsId":1,"num":1,"payOnDelivery":1,"goodsSpecificationId":2,"hasComment":true,"goodsName":"苹果7","picture":"http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png"}],"z_integral":4662,"z_shopYingYeMoney":"93324.00","addressDetail":"空无一物","province":"河南省","createTime":"2017-09-27 11:32:38","cancelTime":"","phone":"15738961968","name":"周一","shopId":1,"state":"待发货"},{"deliverTime":"","city":"郑州市","zuoDanId":432,"stateValue":4,"county":"金水区","shopName":"叮当猫","z_code":"Z281506483157432","shopPayMoney":"80.00","goodsOrderList":[{"goodsOrderId":34,"marketPrice":"1000.00","salePrice":"800.00","goodsSpecification":"","goodsId":2928,"num":2,"payOnDelivery":0,"goodsSpecificationId":"","hasComment":false,"goodsName":"测试我的店铺商品测试-1","picture":"http://image.ddmzl.com/cd3be43144da49c090e544b9fb93150720170919.png"}],"z_integral":400,"z_shopYingYeMoney":"1600.00","addressDetail":"空无一物","province":"河南省","createTime":"2017-09-27 11:32:37","cancelTime":"","phone":"15738961968","name":"周一","shopId":230,"state":"待发货"}],"totalPage":1,"pageNo":1,"recordsTotal":4}
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
         * zuoDanList : [{"deliverTime":"","city":"阜新市","zuoDanId":435,"stateValue":1,"county":"新邱区","shopName":"苹果零售店-政七街店","z_code":"Z281506501886745","shopPayMoney":"66.60","goodsOrderList":[{"goodsOrderId":40,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"规格:中","goodsId":2924,"num":1,"payOnDelivery":1,"goodsSpecificationId":11,"hasComment":false,"goodsName":"测试","picture":"http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png,http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png,http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png"}],"z_integral":333,"z_shopYingYeMoney":"6666.00","addressDetail":"通知我","province":"辽宁省","createTime":"2017-09-27 16:44:47","cancelTime":"","phone":"157699985588","name":"口味","shopId":1,"state":"失败"},{"deliverTime":"2017-09-28 11:31:46","city":"阜新市","zuoDanId":434,"stateValue":2,"county":"新邱区","shopName":"苹果零售店-政七街店","z_code":"Z281506484262605","shopPayMoney":"66.60","goodsOrderList":[{"goodsOrderId":39,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"颜色:黄,版本:128G","goodsId":1,"num":1,"payOnDelivery":1,"goodsSpecificationId":7,"hasComment":false,"goodsName":"苹果7","picture":"http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png"}],"z_integral":333,"z_shopYingYeMoney":"6666.00","addressDetail":"通知我","province":"辽宁省","createTime":"2017-09-27 11:51:03","cancelTime":"","phone":"157699985588","name":"口味","shopId":1,"state":"成功"},{"deliverTime":"","city":"郑州市","zuoDanId":433,"stateValue":4,"county":"金水区","shopName":"苹果零售店-政七街店","z_code":"Z281506483157875","shopPayMoney":"932.40","goodsOrderList":[{"goodsOrderId":35,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"颜色:白,版本:128G","goodsId":1,"num":10,"payOnDelivery":1,"goodsSpecificationId":4,"hasComment":true,"goodsName":"苹果7","picture":"http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png"},{"goodsOrderId":36,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"颜色:黄,版本:128G","goodsId":1,"num":1,"payOnDelivery":1,"goodsSpecificationId":7,"hasComment":true,"goodsName":"苹果7","picture":"http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png"},
         * {"goodsOrderId":37,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"规格:小","goodsId":2924,"num":2,"payOnDelivery":1,"goodsSpecificationId":12,"hasComment":true,"goodsName":"测试","picture":"http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png,http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png,http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png"},
         * {"goodsOrderId":38,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"颜色:黄,版本:258G","goodsId":1,"num":1,"payOnDelivery":1,"goodsSpecificationId":2,"hasComment":true,"goodsName":"苹果7","picture":"http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png"}],"z_integral":4662,"z_shopYingYeMoney":"93324.00","addressDetail":"空无一物","province":"河南省","createTime":"2017-09-27 11:32:38","cancelTime":"","phone":"15738961968","name":"周一","shopId":1,"state":"待发货"},{"deliverTime":"","city":"郑州市","zuoDanId":432,"stateValue":4,"county":"金水区","shopName":"叮当猫","z_code":"Z281506483157432","shopPayMoney":"80.00","goodsOrderList":[{"goodsOrderId":34,"marketPrice":"1000.00","salePrice":"800.00","goodsSpecification":"","goodsId":2928,"num":2,"payOnDelivery":0,"goodsSpecificationId":"","hasComment":false,"goodsName":"测试我的店铺商品测试-1","picture":"http://image.ddmzl.com/cd3be43144da49c090e544b9fb93150720170919.png"}],"z_integral":400,"z_shopYingYeMoney":"1600.00","addressDetail":"空无一物","province":"河南省","createTime":"2017-09-27 11:32:37","cancelTime":"","phone":"15738961968","name":"周一","shopId":230,"state":"待发货"}]
         * totalPage : 1
         * pageNo : 1
         * recordsTotal : 4
         */

        private int totalPage;
        private int pageNo;
        private int recordsTotal;
        private List<ZuoDanListBean> zuoDanList;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getRecordsTotal() {
            return recordsTotal;
        }

        public void setRecordsTotal(int recordsTotal) {
            this.recordsTotal = recordsTotal;
        }

        public List<ZuoDanListBean> getZuoDanList() {
            return zuoDanList;
        }

        public void setZuoDanList(List<ZuoDanListBean> zuoDanList) {
            this.zuoDanList = zuoDanList;
        }

        public static class ZuoDanListBean {
            /**
             * deliverTime :
             * city : 阜新市
             * zuoDanId : 435
             * stateValue : 1
             * county : 新邱区
             * shopName : 苹果零售店-政七街店
             * z_code : Z281506501886745
             * shopPayMoney : 66.60
             * goodsOrderList : [{"goodsOrderId":40,"marketPrice":"8888.00","salePrice":"6666.00","goodsSpecification":"规格:中","goodsId":2924,"num":1,"payOnDelivery":1,"goodsSpecificationId":11,"hasComment":false,"goodsName":"测试","picture":"http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png,http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png,http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png"}]
             * z_integral : 333
             * z_shopYingYeMoney : 6666.00
             * addressDetail : 通知我
             * province : 辽宁省
             * createTime : 2017-09-27 16:44:47
             * cancelTime :
             * phone : 157699985588
             * name : 口味
             * shopId : 1
             * state : 失败
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
                 * goodsOrderId : 40
                 * marketPrice : 8888.00
                 * salePrice : 6666.00
                 * goodsSpecification : 规格:中
                 * goodsId : 2924
                 * num : 1
                 * payOnDelivery : 1
                 * goodsSpecificationId : 11
                 * hasComment : false
                 * goodsName : 测试
                 * picture : http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png,http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png,http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png
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
