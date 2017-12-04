package com.example.ddm.appui.bean;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class GetGoodSpecification {

    /**
     * msg : success
     * code : 1
     * datas : {"propertiesId":"2:1,3:4","image":"http://ddm-image.oss-cn-beijing.aliyuncs.com/3bf4cdaaede9462a81bb7f1b84c104c220170705.png","createUserId":1,"maketPirce":"8888","createTime":"Fri Sep 08 16:05:57 CST 2017","memberPrice":"6666","integral":1111,"id":1,"goodId":1,"totalInventory":200,"inventory":100,"propertiesName":"颜色:白,版本:32G"}
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
         * propertiesId : 2:1,3:4
         * image : http://ddm-image.oss-cn-beijing.aliyuncs.com/3bf4cdaaede9462a81bb7f1b84c104c220170705.png
         * createUserId : 1
         * maketPirce : 8888
         * createTime : Fri Sep 08 16:05:57 CST 2017
         * memberPrice : 6666
         * integral : 1111
         * id : 1
         * goodId : 1
         * totalInventory : 200
         * inventory : 100
         * propertiesName : 颜色:白,版本:32G
         */

        private String propertiesId;
        private String image;
        private int createUserId;
        private String maketPirce;
        private String createTime;
        private String memberPrice;
        private int integral;
        private int id;
        private int goodId;
        private int totalInventory;
        private int inventory;
        private String propertiesName;

        public String getPropertiesId() {
            return propertiesId;
        }

        public void setPropertiesId(String propertiesId) {
            this.propertiesId = propertiesId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(int createUserId) {
            this.createUserId = createUserId;
        }

        public String getMaketPirce() {
            return maketPirce;
        }

        public void setMaketPirce(String maketPirce) {
            this.maketPirce = maketPirce;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getMemberPrice() {
            return memberPrice;
        }

        public void setMemberPrice(String memberPrice) {
            this.memberPrice = memberPrice;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGoodId() {
            return goodId;
        }

        public void setGoodId(int goodId) {
            this.goodId = goodId;
        }

        public int getTotalInventory() {
            return totalInventory;
        }

        public void setTotalInventory(int totalInventory) {
            this.totalInventory = totalInventory;
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
    }
}
