package com.example.ddm.appui.bean;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class MerchantBean {

    /**
     * msg : success
     * code : 1
     * datas : {"shopBoss":"测试","shopImage":"http://ddm-image.oss-cn-beijing.aliyuncs.com/24ce0c21026f4588a6fff80836db933020170707.png","shopName":"测试店铺主店铺","shopPhone":"15093112512","id":15,"userTypeValue":1,"shopAddress":"郑州市二七区郑州市金水区东风路政七街交叉口恒美商务"}
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
         * shopBoss : 测试
         * shopImage : http://ddm-image.oss-cn-beijing.aliyuncs.com/24ce0c21026f4588a6fff80836db933020170707.png
         * shopName : 测试店铺主店铺
         * shopPhone : 15093112512
         * id : 15
         * userTypeValue : 1
         * shopAddress : 郑州市二七区郑州市金水区东风路政七街交叉口恒美商务
         */

        private String shopBoss;
        private String shopImage;
        private String shopName;
        private String shopPhone;
        private int id;
        private int userTypeValue;
        private String shopAddress;

        public String getShopBoss() {
            return shopBoss;
        }

        public void setShopBoss(String shopBoss) {
            this.shopBoss = shopBoss;
        }

        public String getShopImage() {
            return shopImage;
        }

        public void setShopImage(String shopImage) {
            this.shopImage = shopImage;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopPhone() {
            return shopPhone;
        }

        public void setShopPhone(String shopPhone) {
            this.shopPhone = shopPhone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserTypeValue() {
            return userTypeValue;
        }

        public void setUserTypeValue(int userTypeValue) {
            this.userTypeValue = userTypeValue;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }
    }
}
