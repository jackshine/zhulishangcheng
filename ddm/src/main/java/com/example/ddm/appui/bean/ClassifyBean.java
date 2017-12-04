package com.example.ddm.appui.bean;
import java.util.List;
/**
 * Created by Bbacr on 2017/7/6.
 * 分類商品
 */
public class ClassifyBean {
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
         * Phone : 18614958257
         * ShopName : ???????-??????
         * ID : 11
         * isNewShop : 1
         * RealName : ?????
         * ShopAddress : ???????????????·??????????ж???б????
         * MinImg : http://ddm-image.oss-cn-beijing.aliyuncs.com/429d51d3cd2f4ed6a9ee2cf68e9a66d220170706.png
         */

        private String Phone;
        private String ShopName;
        private int ID;
        private int isNewShop;
        private String RealName;
        private String ShopAddress;
        private String MinImg;

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getIsNewShop() {
            return isNewShop;
        }

        public void setIsNewShop(int isNewShop) {
            this.isNewShop = isNewShop;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getShopAddress() {
            return ShopAddress;
        }

        public void setShopAddress(String ShopAddress) {
            this.ShopAddress = ShopAddress;
        }

        public String getMinImg() {
            return MinImg;
        }

        public void setMinImg(String MinImg) {
            this.MinImg = MinImg;
        }
    }
}
