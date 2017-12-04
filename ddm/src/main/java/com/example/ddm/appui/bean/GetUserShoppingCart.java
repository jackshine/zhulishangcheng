package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class GetUserShoppingCart {

    /**
     * msg : success
     * shoppingCartCount : 5
     * code : 1
     * datas : [{"shopState":"有效","shopName":"叮当猫","shopSelect":true,"goods":[{"specificationId":"","shoppingCartStateValue":1,"code":"G230460048","goodsMainImage":"http://image.ddmzl.com/cd3be43144da49c090e544b9fb93150720170919.png","memberPrice":"800","priceDifference":"0","goodsId":2928,"payOnDelivery":0,"count":2,"goodsStateValue":1,"shoppingCartId":35,"inventory":200,"propertiesName":"","isSpecification":false,"shoppingCartState":"已选","propertiesId":"","goodsState":"有效","goodsName":"测试我的店铺商品测试-1"}],"shopSelectCount":1,"shopSelectMoney":"1600.00","shopId":230,"shopStateValue":1},{"shopState":"有效","shopName":"苹果零售店-政七街店","shopSelect":true,"goods":[{"specificationId":2,"shoppingCartStateValue":1,"code":"G166277588","goodsMainImage":"http://gw.alicdn.com/bao/uploaded/i4/TB1mSlAegMPMeJjy1Xdc89srXXa_1136x1136Q75.jpg_.webp","memberPrice":"6666","priceDifference":"0","goodsId":1,"payOnDelivery":1,"count":1,"goodsStateValue":1,"shoppingCartId":59,"inventory":36,"propertiesName":"颜色:黄,版本:258G","isSpecification":true,"shoppingCartState":"已选","propertiesId":"2:3,3:6","goodsState":"有效","goodsName":"苹果7"},{"specificationId":12,"shoppingCartStateValue":1,"code":"G1662775r5","goodsMainImage":"http://gw.alicdn.com/bao/uploaded/i4/TB1mSlAegMPMeJjy1Xdc89srXXa_1136x1136Q75.jpg_.webp","memberPrice":"6666","priceDifference":"0","goodsId":2924,"payOnDelivery":1,"count":2,"goodsStateValue":1,"shoppingCartId":58,"inventory":17,"propertiesName":"规格:小","isSpecification":true,"shoppingCartState":"已选","propertiesId":"28:14","goodsState":"有效","goodsName":"测试"},{"specificationId":7,"shoppingCartStateValue":1,"code":"G166277588","goodsMainImage":"http://gw.alicdn.com/bao/uploaded/i4/TB1mSlAegMPMeJjy1Xdc89srXXa_1136x1136Q75.jpg_.webp","memberPrice":"6666","priceDifference":"0","goodsId":1,"payOnDelivery":1,"count":1,"goodsStateValue":1,"shoppingCartId":57,"inventory":36,"propertiesName":"颜色:黄,版本:128G","isSpecification":true,"shoppingCartState":"已选","propertiesId":"2:3,3:5","goodsState":"有效","goodsName":"苹果7"},{"specificationId":4,"shoppingCartStateValue":1,"code":"G166277588","goodsMainImage":"http://gw.alicdn.com/bao/uploaded/i4/TB1mSlAegMPMeJjy1Xdc89srXXa_1136x1136Q75.jpg_.webp","memberPrice":"6666","priceDifference":"0","goodsId":1,"payOnDelivery":1,"count":10,"goodsStateValue":1,"shoppingCartId":41,"inventory":36,"propertiesName":"颜色:白,版本:128G","isSpecification":true,"shoppingCartState":"已选","propertiesId":"2:1,3:5","goodsState":"有效","goodsName":"苹果7"}],"shopSelectCount":4,"shopSelectMoney":"93324.00","shopId":1,"shopStateValue":1}]
     * totalMoney : 94924.00
     * selectCount : 5
     */

    private String msg;
    private int shoppingCartCount;
    private int code;
    private String totalMoney;
    private int selectCount;
    private List<DatasBean> datas;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getShoppingCartCount() {
        return shoppingCartCount;
    }

    public void setShoppingCartCount(int shoppingCartCount) {
        this.shoppingCartCount = shoppingCartCount;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * shopState : 有效
         * shopName : 叮当猫
         * shopSelect : true
         * goods : [{"specificationId":"","shoppingCartStateValue":1,"code":"G230460048","goodsMainImage":"http://image.ddmzl.com/cd3be43144da49c090e544b9fb93150720170919.png","memberPrice":"800","priceDifference":"0","goodsId":2928,"payOnDelivery":0,"count":2,"goodsStateValue":1,"shoppingCartId":35,"inventory":200,"propertiesName":"","isSpecification":false,"shoppingCartState":"已选","propertiesId":"","goodsState":"有效","goodsName":"测试我的店铺商品测试-1"}]
         * shopSelectCount : 1
         * shopSelectMoney : 1600.00
         * shopId : 230
         * shopStateValue : 1
         */

        private String shopState;
        private String shopName;
        private boolean shopSelect;
        private int shopSelectCount;
        private String shopSelectMoney;
        private int shopId;
        private int shopStateValue;
        private List<GoodsBean> goods;

        public String getShopState() {
            return shopState;
        }

        public void setShopState(String shopState) {
            this.shopState = shopState;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public boolean isShopSelect() {
            return shopSelect;
        }

        public void setShopSelect(boolean shopSelect) {
            this.shopSelect = shopSelect;
        }

        public int getShopSelectCount() {
            return shopSelectCount;
        }

        public void setShopSelectCount(int shopSelectCount) {
            this.shopSelectCount = shopSelectCount;
        }

        public String getShopSelectMoney() {
            return shopSelectMoney;
        }

        public void setShopSelectMoney(String shopSelectMoney) {
            this.shopSelectMoney = shopSelectMoney;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getShopStateValue() {
            return shopStateValue;
        }

        public void setShopStateValue(int shopStateValue) {
            this.shopStateValue = shopStateValue;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * specificationId :
             * shoppingCartStateValue : 1
             * code : G230460048
             * goodsMainImage : http://image.ddmzl.com/cd3be43144da49c090e544b9fb93150720170919.png
             * memberPrice : 800
             * priceDifference : 0
             * goodsId : 2928
             * payOnDelivery : 0
             * count : 2
             * goodsStateValue : 1
             * shoppingCartId : 35
             * inventory : 200
             * propertiesName :
             * isSpecification : false
             * shoppingCartState : 已选
             * propertiesId :
             * goodsState : 有效
             * goodsName : 测试我的店铺商品测试-1
             */

            private String specificationId;
            private int shoppingCartStateValue;
            private String code;
            private String goodsMainImage;
            private String memberPrice;
            private String priceDifference;
            private int goodsId;
            private int payOnDelivery;
            private int count;
            private int goodsStateValue;
            private int shoppingCartId;
            private int inventory;
            private String propertiesName;
            private boolean isSpecification;
            private String shoppingCartState;
            private String propertiesId;
            private String goodsState;
            private String goodsName;

            public String getSpecificationId() {
                return specificationId;
            }

            public void setSpecificationId(String specificationId) {
                this.specificationId = specificationId;
            }

            public int getShoppingCartStateValue() {
                return shoppingCartStateValue;
            }

            public void setShoppingCartStateValue(int shoppingCartStateValue) {
                this.shoppingCartStateValue = shoppingCartStateValue;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getGoodsMainImage() {
                return goodsMainImage;
            }

            public void setGoodsMainImage(String goodsMainImage) {
                this.goodsMainImage = goodsMainImage;
            }

            public String getMemberPrice() {
                return memberPrice;
            }

            public void setMemberPrice(String memberPrice) {
                this.memberPrice = memberPrice;
            }

            public String getPriceDifference() {
                return priceDifference;
            }

            public void setPriceDifference(String priceDifference) {
                this.priceDifference = priceDifference;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getPayOnDelivery() {
                return payOnDelivery;
            }

            public void setPayOnDelivery(int payOnDelivery) {
                this.payOnDelivery = payOnDelivery;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getGoodsStateValue() {
                return goodsStateValue;
            }

            public void setGoodsStateValue(int goodsStateValue) {
                this.goodsStateValue = goodsStateValue;
            }

            public int getShoppingCartId() {
                return shoppingCartId;
            }

            public void setShoppingCartId(int shoppingCartId) {
                this.shoppingCartId = shoppingCartId;
            }

            public int getInventory() {
                return inventory;
            }

            public void setInventory(int inventory) {
                this.inventory = inventory;
            }

            public String getPropertiesName() {
                return propertiesName;
            }

            public void setPropertiesName(String propertiesName) {
                this.propertiesName = propertiesName;
            }

            public boolean isIsSpecification() {
                return isSpecification;
            }

            public void setIsSpecification(boolean isSpecification) {
                this.isSpecification = isSpecification;
            }

            public String getShoppingCartState() {
                return shoppingCartState;
            }

            public void setShoppingCartState(String shoppingCartState) {
                this.shoppingCartState = shoppingCartState;
            }

            public String getPropertiesId() {
                return propertiesId;
            }

            public void setPropertiesId(String propertiesId) {
                this.propertiesId = propertiesId;
            }

            public String getGoodsState() {
                return goodsState;
            }

            public void setGoodsState(String goodsState) {
                this.goodsState = goodsState;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }
        }
    }
}
