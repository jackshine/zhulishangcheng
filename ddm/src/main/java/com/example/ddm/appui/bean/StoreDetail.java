package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class StoreDetail {


    /**
     * msg : success
     * code : 1
     * datas : {"shopGraded":"5.0","marketPrice":"8888-8888","deliveryTime":"","payOnDelivery":1,"shopName":"苹果零售店-政七街店","goodsStateValue":1,"remark":["此商品在商城可抢红包102-333每天可抢一次，红包方式为随机红包。","抢红包时间为每天早上八点，用户点击领取红包即可获得。"],"shopAddress":"郑州市金水区东风路政七街东南角","inventory":88,"customersdatas":[{"imgUrl":"","customerPhone":"张三丰","createTime":"2017-09-05 16:43","replyMessage":"亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！","id":18,"gradedValue":2,"customerAvotorr":"","content":"商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的"},{"imgUrl":"","customerPhone":"王新冠","createTime":"2017-09-05 16:43","replyMessage":"","id":287,"gradedValue":4,"customerAvotorr":"","content":"商品很好的"}],"goodsState":"有效","integral":"102-333","details":"<img src=\"http://image.ddmzl.com/0964428c7ea04debb1f24216ce83655920170918.jpg\" alt=\"img\" style=\"width: 90%\"><img src=\"http://image.ddmzl.com/fadbd869c2594f8989db9dc61d4e889d20170918.jpg\" alt=\"img\" style=\"width: 90%\"><img src=\"http://image.ddmzl.com/710ed2461d244c82b7fef2fd0259e97020170918.png\" alt=\"img\" style=\"width: 90%\"><img src=\"http://image.ddmzl.com/a931337662ee41809a3e999072ec756520170918.jpg\" alt=\"img\" style=\"width: 90%\">","id":1,"goodsName":"苹果7","summary":"iphone8 126G 红色","memberPrice":"6666-6666","specification":true,"mainImage":"http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png","phone":"13838378247","userCustomersSum":0,"name":"苹果7","location":"113.687791,34.795833","goodsCode":"","deliveryArea":"","commentsSumdatas":[{"commentsTypeValue":1,"commentsSum":0,"commentsType":"最新评价"},{"commentsTypeValue":2,"commentsSum":0,"commentsType":"晒图"},{"commentsTypeValue":3,"commentsSum":0,"commentsType":"好评"},{"commentsTypeValue":4,"commentsSum":0,"commentsType":"差评"},{"commentsTypeValue":5,"commentsSum":0,"commentsType":"五星"}],"remark2":["红包可在合作商家直接消费。","红包累计满100可进行提现。"]}
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
         * shopGraded : 5.0
         * marketPrice : 8888-8888
         * deliveryTime :
         * payOnDelivery : 1
         * shopName : 苹果零售店-政七街店
         * goodsStateValue : 1
         * remark : ["此商品在商城可抢红包102-333每天可抢一次，红包方式为随机红包。","抢红包时间为每天早上八点，用户点击领取红包即可获得。"]
         * shopAddress : 郑州市金水区东风路政七街东南角
         * inventory : 88
         * customersdatas : [{"imgUrl":"","customerPhone":"张三丰","createTime":"2017-09-05 16:43","replyMessage":"亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！","id":18,"gradedValue":2,"customerAvotorr":"","content":"商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的"},{"imgUrl":"","customerPhone":"王新冠","createTime":"2017-09-05 16:43","replyMessage":"","id":287,"gradedValue":4,"customerAvotorr":"","content":"商品很好的"}]
         * goodsState : 有效
         * integral : 102-333
         * details : <img src="http://image.ddmzl.com/0964428c7ea04debb1f24216ce83655920170918.jpg" alt="img" style="width: 90%"><img src="http://image.ddmzl.com/fadbd869c2594f8989db9dc61d4e889d20170918.jpg" alt="img" style="width: 90%"><img src="http://image.ddmzl.com/710ed2461d244c82b7fef2fd0259e97020170918.png" alt="img" style="width: 90%"><img src="http://image.ddmzl.com/a931337662ee41809a3e999072ec756520170918.jpg" alt="img" style="width: 90%">
         * id : 1
         * goodsName : 苹果7
         * summary : iphone8 126G 红色
         * memberPrice : 6666-6666
         * specification : true
         * mainImage : http://image.ddmzl.com/9b4c9c36b14b460091289d2222bb102b20170918.png
         * phone : 13838378247
         * userCustomersSum : 0
         * name : 苹果7
         * location : 113.687791,34.795833
         * goodsCode :
         * deliveryArea :
         * commentsSumdatas : [{"commentsTypeValue":1,"commentsSum":0,"commentsType":"最新评价"},{"commentsTypeValue":2,"commentsSum":0,"commentsType":"晒图"},{"commentsTypeValue":3,"commentsSum":0,"commentsType":"好评"},{"commentsTypeValue":4,"commentsSum":0,"commentsType":"差评"},{"commentsTypeValue":5,"commentsSum":0,"commentsType":"五星"}]
         * remark2 : ["红包可在合作商家直接消费。","红包累计满100可进行提现。"]
         */

        private String shopGraded;
        private String marketPrice;
        private String deliveryTime;
        private int payOnDelivery;
        private String shopName;
        private int goodsStateValue;
        private String shopAddress;
        private int inventory;
        private String goodsState;
        private String integral;
        private String details;
        private int id;
        private String goodsName;
        private String summary;
        private String memberPrice;
        private boolean specification;
        private String mainImage;
        private String phone;
        private int userCustomersSum;
        private String name;
        private String location;
        private String goodsCode;
        private String deliveryArea;
        private List<String> remark;
        private List<CustomersdatasBean> customersdatas;
        private List<CommentsSumdatasBean> commentsSumdatas;
        private List<String> remark2;

        public String getShopGraded() {
            return shopGraded;
        }

        public void setShopGraded(String shopGraded) {
            this.shopGraded = shopGraded;
        }

        public String getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(String marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public int getPayOnDelivery() {
            return payOnDelivery;
        }

        public void setPayOnDelivery(int payOnDelivery) {
            this.payOnDelivery = payOnDelivery;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getGoodsStateValue() {
            return goodsStateValue;
        }

        public void setGoodsStateValue(int goodsStateValue) {
            this.goodsStateValue = goodsStateValue;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }

        public int getInventory() {
            return inventory;
        }

        public void setInventory(int inventory) {
            this.inventory = inventory;
        }

        public String getGoodsState() {
            return goodsState;
        }

        public void setGoodsState(String goodsState) {
            this.goodsState = goodsState;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getMemberPrice() {
            return memberPrice;
        }

        public void setMemberPrice(String memberPrice) {
            this.memberPrice = memberPrice;
        }

        public boolean isSpecification() {
            return specification;
        }

        public void setSpecification(boolean specification) {
            this.specification = specification;
        }

        public String getMainImage() {
            return mainImage;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getUserCustomersSum() {
            return userCustomersSum;
        }

        public void setUserCustomersSum(int userCustomersSum) {
            this.userCustomersSum = userCustomersSum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getGoodsCode() {
            return goodsCode;
        }

        public void setGoodsCode(String goodsCode) {
            this.goodsCode = goodsCode;
        }

        public String getDeliveryArea() {
            return deliveryArea;
        }

        public void setDeliveryArea(String deliveryArea) {
            this.deliveryArea = deliveryArea;
        }

        public List<String> getRemark() {
            return remark;
        }

        public void setRemark(List<String> remark) {
            this.remark = remark;
        }

        public List<CustomersdatasBean> getCustomersdatas() {
            return customersdatas;
        }

        public void setCustomersdatas(List<CustomersdatasBean> customersdatas) {
            this.customersdatas = customersdatas;
        }

        public List<CommentsSumdatasBean> getCommentsSumdatas() {
            return commentsSumdatas;
        }

        public void setCommentsSumdatas(List<CommentsSumdatasBean> commentsSumdatas) {
            this.commentsSumdatas = commentsSumdatas;
        }

        public List<String> getRemark2() {
            return remark2;
        }

        public void setRemark2(List<String> remark2) {
            this.remark2 = remark2;
        }

        public static class CustomersdatasBean {
            /**
             * imgUrl :
             * customerPhone : 张三丰
             * createTime : 2017-09-05 16:43
             * replyMessage : 亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！
             * id : 18
             * gradedValue : 2
             * customerAvotorr :
             * content : 商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的
             */

            private String imgUrl;
            private String customerPhone;
            private String createTime;
            private String replyMessage;
            private int id;
            private int gradedValue;
            private String customerAvotorr;
            private String content;

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getCustomerPhone() {
                return customerPhone;
            }

            public void setCustomerPhone(String customerPhone) {
                this.customerPhone = customerPhone;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getReplyMessage() {
                return replyMessage;
            }

            public void setReplyMessage(String replyMessage) {
                this.replyMessage = replyMessage;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getGradedValue() {
                return gradedValue;
            }

            public void setGradedValue(int gradedValue) {
                this.gradedValue = gradedValue;
            }

            public String getCustomerAvotorr() {
                return customerAvotorr;
            }

            public void setCustomerAvotorr(String customerAvotorr) {
                this.customerAvotorr = customerAvotorr;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class CommentsSumdatasBean {
            /**
             * commentsTypeValue : 1
             * commentsSum : 0
             * commentsType : 最新评价
             */

            private int commentsTypeValue;
            private int commentsSum;
            private String commentsType;

            public int getCommentsTypeValue() {
                return commentsTypeValue;
            }

            public void setCommentsTypeValue(int commentsTypeValue) {
                this.commentsTypeValue = commentsTypeValue;
            }

            public int getCommentsSum() {
                return commentsSum;
            }

            public void setCommentsSum(int commentsSum) {
                this.commentsSum = commentsSum;
            }

            public String getCommentsType() {
                return commentsType;
            }

            public void setCommentsType(String commentsType) {
                this.commentsType = commentsType;
            }
        }
    }
}
