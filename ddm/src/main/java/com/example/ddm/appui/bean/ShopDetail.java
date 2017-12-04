package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class ShopDetail {

    /**
     * msg : success
     * code : 1
     * datas : {"shopGraded":"5.0","goodsDatas":[{"mainImage":"http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png","marketPrice":"2000","goodsState":"有效","memberPrice":"1800","name":"测试","goodsStateValue":1,"id":2924},{"mainImage":"http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png","marketPrice":"7777","goodsState":"有效","memberPrice":"7500","name":"iphone7s","goodsStateValue":1,"id":2920}],"mainImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/c7873bfeb46e466384fbc9c39f397fe420170705.png","address":"34.80573593002055,113.67098014093058","shopName":"苹果零售店-政七街店","shopAddress":"郑州市金水区东风路政七街东南角","customersdatas":[{"imgUrl":"http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png","customerPhone":"张三丰","createTime":"2017-09-05 16:43","replyMessage":"亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！","id":18,"gradedValue":2,"customerAvotorr":"","content":"商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的"},{"imgUrl":"http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png","customerPhone":"王新冠","createTime":"2017-09-05 16:43","replyMessage":"","id":287,"gradedValue":4,"customerAvotorr":"","content":"商品很好的"}],"realName":"王超","phone":"13838378247","detailImg":"<img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png\" style=\"width: 90%; -webkit-touch-callout: none; -webkit-user-select: none;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/56b6e5a41638421cbf6590bd3098245e20170705.png\" style=\"width: 90%; -webkit-touch-callout: none; -webkit-user-select: none;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/ade332e7f7434590aa895ed3c63e128320170705.png\" style=\"width: 90%; -webkit-touch-callout: none; -webkit-user-select: none;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/69edfca463e04b1087a4b46ae32a572e20170705.png\" style=\"width: 90%; -webkit-touch-callout: none; -webkit-user-select: none;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/6f2b164fbc684f3d8d46c221c3f8550420170705.png\" style=\"width: 90%; -webkit-touch-callout: none; -webkit-user-select: none;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/e0656231eef4429eaed87b303256834b20170705.png\" style=\"width: 90%; -webkit-touch-callout: none; -webkit-user-select: none;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/211316d3960140efbc9eee5a137566de20170705.png\" style=\"width:90%;\">","userCustomersSum":22,"id":1,"commentsSumdatas":[{"commentsTypeValue":1,"commentsSum":22,"commentsType":"最新评价"},{"commentsTypeValue":2,"commentsSum":22,"commentsType":"晒图"},{"commentsTypeValue":3,"commentsSum":16,"commentsType":"好评"},{"commentsTypeValue":4,"commentsSum":6,"commentsType":"差评"},{"commentsTypeValue":5,"commentsSum":5,"commentsType":"五星"}]}
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
         * goodsDatas : [{"mainImage":"http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png","marketPrice":"2000","goodsState":"有效","memberPrice":"1800","name":"测试","goodsStateValue":1,"id":2924},{"mainImage":"http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png","marketPrice":"7777","goodsState":"有效","memberPrice":"7500","name":"iphone7s","goodsStateValue":1,"id":2920}]
         * mainImg : http://ddm-image.oss-cn-beijing.aliyuncs.com/c7873bfeb46e466384fbc9c39f397fe420170705.png
         * address : 34.80573593002055,113.67098014093058
         * shopName : 苹果零售店-政七街店
         * shopAddress : 郑州市金水区东风路政七街东南角
         * customersdatas : [{"imgUrl":"http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png","customerPhone":"张三丰","createTime":"2017-09-05 16:43","replyMessage":"亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！亲！求好评！","id":18,"gradedValue":2,"customerAvotorr":"","content":"商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的商品很好的"},{"imgUrl":"http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png","customerPhone":"王新冠","createTime":"2017-09-05 16:43","replyMessage":"","id":287,"gradedValue":4,"customerAvotorr":"","content":"商品很好的"}]
         * realName : 王超
         * phone : 13838378247
         * detailImg : <img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png" style="width: 90%; -webkit-touch-callout: none; -webkit-user-select: none;"><img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/56b6e5a41638421cbf6590bd3098245e20170705.png" style="width: 90%; -webkit-touch-callout: none; -webkit-user-select: none;"><img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/ade332e7f7434590aa895ed3c63e128320170705.png" style="width: 90%; -webkit-touch-callout: none; -webkit-user-select: none;"><img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/69edfca463e04b1087a4b46ae32a572e20170705.png" style="width: 90%; -webkit-touch-callout: none; -webkit-user-select: none;"><img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/6f2b164fbc684f3d8d46c221c3f8550420170705.png" style="width: 90%; -webkit-touch-callout: none; -webkit-user-select: none;"><img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/e0656231eef4429eaed87b303256834b20170705.png" style="width: 90%; -webkit-touch-callout: none; -webkit-user-select: none;"><img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/211316d3960140efbc9eee5a137566de20170705.png" style="width:90%;">
         * userCustomersSum : 22
         * id : 1
         * commentsSumdatas : [{"commentsTypeValue":1,"commentsSum":22,"commentsType":"最新评价"},{"commentsTypeValue":2,"commentsSum":22,"commentsType":"晒图"},{"commentsTypeValue":3,"commentsSum":16,"commentsType":"好评"},{"commentsTypeValue":4,"commentsSum":6,"commentsType":"差评"},{"commentsTypeValue":5,"commentsSum":5,"commentsType":"五星"}]
         */

        private String shopGraded;
        private String mainImg;
        private String address;
        private String shopName;
        private String shopAddress;
        private String realName;
        private String phone;
        private String detailImg;
        private int userCustomersSum;
        private int id;
        private List<GoodsDatasBean> goodsDatas;
        private List<CustomersdatasBean> customersdatas;
        private List<CommentsSumdatasBean> commentsSumdatas;

        public String getShopGraded() {
            return shopGraded;
        }

        public void setShopGraded(String shopGraded) {
            this.shopGraded = shopGraded;
        }

        public String getMainImg() {
            return mainImg;
        }

        public void setMainImg(String mainImg) {
            this.mainImg = mainImg;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDetailImg() {
            return detailImg;
        }

        public void setDetailImg(String detailImg) {
            this.detailImg = detailImg;
        }

        public int getUserCustomersSum() {
            return userCustomersSum;
        }

        public void setUserCustomersSum(int userCustomersSum) {
            this.userCustomersSum = userCustomersSum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<GoodsDatasBean> getGoodsDatas() {
            return goodsDatas;
        }

        public void setGoodsDatas(List<GoodsDatasBean> goodsDatas) {
            this.goodsDatas = goodsDatas;
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

        public static class GoodsDatasBean {
            /**
             * mainImage : http://image.ddmzl.com/a186cdf8714948b8b230f505e9100ebb20170821.png
             * marketPrice : 2000
             * goodsState : 有效
             * memberPrice : 1800
             * name : 测试
             * goodsStateValue : 1
             * id : 2924
             */

            private String mainImage;
            private String marketPrice;
            private String goodsState;
            private String memberPrice;
            private String name;
            private int goodsStateValue;
            private int id;

            public String getMainImage() {
                return mainImage;
            }

            public void setMainImage(String mainImage) {
                this.mainImage = mainImage;
            }

            public String getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(String marketPrice) {
                this.marketPrice = marketPrice;
            }

            public String getGoodsState() {
                return goodsState;
            }

            public void setGoodsState(String goodsState) {
                this.goodsState = goodsState;
            }

            public String getMemberPrice() {
                return memberPrice;
            }

            public void setMemberPrice(String memberPrice) {
                this.memberPrice = memberPrice;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getGoodsStateValue() {
                return goodsStateValue;
            }

            public void setGoodsStateValue(int goodsStateValue) {
                this.goodsStateValue = goodsStateValue;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class CustomersdatasBean {
            /**
             * imgUrl : http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png,http://ddm-image.oss-cn-beijing.aliyuncs.com/202832e83fa54800b21624b5755a69d920170705.png
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
             * commentsSum : 22
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
