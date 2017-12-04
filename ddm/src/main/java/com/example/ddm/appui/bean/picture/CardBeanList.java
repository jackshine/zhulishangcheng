package com.example.ddm.appui.bean.picture;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/21.
 * 获取银行卡
 *
 */

public class CardBeanList {


    /**
     * msg : success
     * code : 1
     * datas : {"cardList":[{"cardNum":"1111111111111","bankId":2,"bank":"交通银行","isDefault":false,"accountName":"莫文蔚","kaihuhang":"莫言我","cardNumShort":"1111","id":72,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%BA%A4%E9%80%9A%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/2.png"},{"cardNum":"9999999999999","bankId":2,"bank":"交通银行","isDefault":false,"accountName":"莫文蔚","kaihuhang":"去医院","cardNumShort":"9999","id":71,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%BA%A4%E9%80%9A%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/2.png"},{"cardNum":"6666666666666","bankId":3,"bank":"建设银行","isDefault":false,"accountName":"6669999","kaihuhang":"know","cardNumShort":"6666","id":70,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%B8%AD%E5%9B%BD%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/3.png"},{"cardNum":"555555555555555","bankId":4,"bank":"中国银行","isDefault":false,"accountName":"默默","kaihuhang":"6666666","cardNumShort":"5555","id":69,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%B8%AD%E5%9B%BD%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/4.png"},{"cardNum":"66666666666","bankId":4,"bank":"中国银行","isDefault":false,"accountName":"我","kaihuhang":"色魔","cardNumShort":"6666","id":68,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%B8%AD%E5%9B%BD%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/4.png"},{"cardNum":"7777777777","bankId":1,"bank":"工商银行","isDefault":true,"accountName":"我的的","kaihuhang":"冷漠","cardNumShort":"7777","id":67,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%B8%AD%E5%9B%BD%E5%B7%A5%E5%95%86%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/1.png"},{"cardNum":"9999999999","bankId":2,"bank":"交通银行","isDefault":false,"accountName":"6666","kaihuhang":"来看看","cardNumShort":"9999","id":66,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%BA%A4%E9%80%9A%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/2.png"},{"cardNum":"8888888888888","bankId":1,"bank":"工商银行","isDefault":false,"accountName":"回眸一笑","kaihuhang":"哈哈","cardNumShort":"8888","id":57,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%B8%AD%E5%9B%BD%E5%B7%A5%E5%95%86%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/1.png"},{"cardNum":"6666666666","bankId":7,"bank":"中信银行","isDefault":false,"accountName":"模样","kaihuhang":"我妈我","cardNumShort":"6666","id":56,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%B8%AD%E4%BF%A1%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/7.png"},{"cardNum":"6666666666","bankId":2,"bank":"交通银行","isDefault":false,"accountName":"569","kaihuhang":"默默无闻","cardNumShort":"6666","id":55,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%BA%A4%E9%80%9A%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/2.png"}],"totalPage":2,"recordsTotal":13}
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
         * cardList : [{"cardNum":"1111111111111","bankId":2,"bank":"交通银行","isDefault":false,"accountName":"莫文蔚","kaihuhang":"莫言我","cardNumShort":"1111","id":72,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%BA%A4%E9%80%9A%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/2.png"},{"cardNum":"9999999999999","bankId":2,"bank":"交通银行","isDefault":false,"accountName":"莫文蔚","kaihuhang":"去医院","cardNumShort":"9999","id":71,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%BA%A4%E9%80%9A%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/2.png"},{"cardNum":"6666666666666","bankId":3,"bank":"建设银行","isDefault":false,"accountName":"6669999","kaihuhang":"know","cardNumShort":"6666","id":70,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%B8%AD%E5%9B%BD%E5%BB%BA%E8%AE%BE%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/3.png"},{"cardNum":"555555555555555","bankId":4,"bank":"中国银行","isDefault":false,"accountName":"默默","kaihuhang":"6666666","cardNumShort":"5555","id":69,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%B8%AD%E5%9B%BD%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/4.png"},{"cardNum":"66666666666","bankId":4,"bank":"中国银行","isDefault":false,"accountName":"我","kaihuhang":"色魔","cardNumShort":"6666","id":68,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%B8%AD%E5%9B%BD%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/4.png"},{"cardNum":"7777777777","bankId":1,"bank":"工商银行","isDefault":true,"accountName":"我的的","kaihuhang":"冷漠","cardNumShort":"7777","id":67,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%B8%AD%E5%9B%BD%E5%B7%A5%E5%95%86%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/1.png"},{"cardNum":"9999999999","bankId":2,"bank":"交通银行","isDefault":false,"accountName":"6666","kaihuhang":"来看看","cardNumShort":"9999","id":66,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%BA%A4%E9%80%9A%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/2.png"},{"cardNum":"8888888888888","bankId":1,"bank":"工商银行","isDefault":false,"accountName":"回眸一笑","kaihuhang":"哈哈","cardNumShort":"8888","id":57,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%B8%AD%E5%9B%BD%E5%B7%A5%E5%95%86%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/1.png"},{"cardNum":"6666666666","bankId":7,"bank":"中信银行","isDefault":false,"accountName":"模样","kaihuhang":"我妈我","cardNumShort":"6666","id":56,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%B8%AD%E4%BF%A1%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/7.png"},{"cardNum":"6666666666","bankId":2,"bank":"交通银行","isDefault":false,"accountName":"569","kaihuhang":"默默无闻","cardNumShort":"6666","id":55,"state":true,"bankPicture":"http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%BA%A4%E9%80%9A%E9%93%B6%E8%A1%8C%402x.png","userKey":"d04d57c2-5903-45fa-99a4-2b5af2edf410","bankImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/2.png"}]
         * totalPage : 2
         * recordsTotal : 13
         */

        private int totalPage;
        private int recordsTotal;
        private List<CardListBean> cardList;

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

        public List<CardListBean> getCardList() {
            return cardList;
        }

        public void setCardList(List<CardListBean> cardList) {
            this.cardList = cardList;
        }

        public static class CardListBean {
            /**
             * cardNum : 1111111111111
             * bankId : 2
             * bank : 交通银行
             * isDefault : false
             * accountName : 莫文蔚
             * kaihuhang : 莫言我
             * cardNumShort : 1111
             * id : 72
             * state : true
             * bankPicture : http://ddm-image.oss-cn-beijing.aliyuncs.com/%E4%BA%A4%E9%80%9A%E9%93%B6%E8%A1%8C%402x.png
             * userKey : d04d57c2-5903-45fa-99a4-2b5af2edf410
             * bankImg : http://ddm-image.oss-cn-beijing.aliyuncs.com/bank_img/2.png
             */

            private String cardNum;
            private int bankId;
            private String bank;
            private boolean isDefault;
            private String accountName;
            private String kaihuhang;
            private String cardNumShort;
            private int id;
            private boolean state;
            private String bankPicture;
            private String userKey;
            private String bankImg;

            public String getCardNum() {
                return cardNum;
            }

            public void setCardNum(String cardNum) {
                this.cardNum = cardNum;
            }

            public int getBankId() {
                return bankId;
            }

            public void setBankId(int bankId) {
                this.bankId = bankId;
            }

            public String getBank() {
                return bank;
            }

            public void setBank(String bank) {
                this.bank = bank;
            }

            public boolean isIsDefault() {
                return isDefault;
            }

            public void setIsDefault(boolean isDefault) {
                this.isDefault = isDefault;
            }

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public String getKaihuhang() {
                return kaihuhang;
            }

            public void setKaihuhang(String kaihuhang) {
                this.kaihuhang = kaihuhang;
            }

            public String getCardNumShort() {
                return cardNumShort;
            }

            public void setCardNumShort(String cardNumShort) {
                this.cardNumShort = cardNumShort;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isState() {
                return state;
            }

            public void setState(boolean state) {
                this.state = state;
            }

            public String getBankPicture() {
                return bankPicture;
            }

            public void setBankPicture(String bankPicture) {
                this.bankPicture = bankPicture;
            }

            public String getUserKey() {
                return userKey;
            }

            public void setUserKey(String userKey) {
                this.userKey = userKey;
            }

            public String getBankImg() {
                return bankImg;
            }

            public void setBankImg(String bankImg) {
                this.bankImg = bankImg;
            }
        }
    }
}
