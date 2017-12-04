package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/7.
 *
 */

public class ShopDetailBean {

    /**
     * datas : {"Phone":"18538220002",
     * "Detail":"<img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/b8491d61b1a94d15aff29cf25e29310c20170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/103800b464f84c9e93553b1f7f44f05c20170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/0381cd21263c4bb09466c37bfdd58dd820170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/d5deefd3cce047b89fe74a94ea30952a20170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/38fd1a87fb8f4faaba9bfaa19ae3d96c20170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/4b58eaf473784b83965d8d35dc25af7420170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/c1719d863a414627b8844a2b0c18659b20170706.png\" style=\"width:90%;\">","address":"34.76820775732243,113.71336708796145","MainImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/ee5a7e0caea4422b9a21b3fdf9d4b4cd20170706.png","DetailImg":["<img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/b8491d61b1a94d15aff29cf25e29310c20170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/103800b464f84c9e93553b1f7f44f05c20170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/0381cd21263c4bb09466c37bfdd58dd820170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/d5deefd3cce047b89fe74a94ea30952a20170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/38fd1a87fb8f4faaba9bfaa19ae3d96c20170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/4b58eaf473784b83965d8d35dc25af7420170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/c1719d863a414627b8844a2b0c18659b20170706.png\" style=\"width:90%;\"><br><br><br><br><br><br><br>"],"ShopName":"安妮美甲","ID":5,"RealName":"张辰","ShopAddress":"郑州市金水区曼哈顿商业广场A座一层啊1209号"}
     * code : 1
     * msg : success
     */
    private DatasBean datas;
    private int code;
    private String msg;
    public DatasBean getDatas() {
        return datas;
    }
    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }
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

    public static class DatasBean {
        /**
         * Phone : 18538220002
         * Detail : <img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/b8491d61b1a94d15aff29cf25e29310c20170706.png" style="width:90%;"><img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/103800b464f84c9e93553b1f7f44f05c20170706.png" style="width:90%;"><img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/0381cd21263c4bb09466c37bfdd58dd820170706.png" style="width:90%;"><img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/d5deefd3cce047b89fe74a94ea30952a20170706.png" style="width:90%;"><img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/38fd1a87fb8f4faaba9bfaa19ae3d96c20170706.png" style="width:90%;"><img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/4b58eaf473784b83965d8d35dc25af7420170706.png" style="width:90%;"><img src="http://ddm-image.oss-cn-beijing.aliyuncs.com/c1719d863a414627b8844a2b0c18659b20170706.png" style="width:90%;">
         * address : 34.76820775732243,113.71336708796145
         * MainImg : http://ddm-image.oss-cn-beijing.aliyuncs.com/ee5a7e0caea4422b9a21b3fdf9d4b4cd20170706.png
         * DetailImg : ["<img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/b8491d61b1a94d15aff29cf25e29310c20170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/103800b464f84c9e93553b1f7f44f05c20170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/0381cd21263c4bb09466c37bfdd58dd820170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/d5deefd3cce047b89fe74a94ea30952a20170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/38fd1a87fb8f4faaba9bfaa19ae3d96c20170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/4b58eaf473784b83965d8d35dc25af7420170706.png\" style=\"width:90%;\"><img src=\"http://ddm-image.oss-cn-beijing.aliyuncs.com/c1719d863a414627b8844a2b0c18659b20170706.png\" style=\"width:90%;\"><br><br><br><br><br><br><br>"]
         * ShopName : 安妮美甲
         * ID : 5
         * RealName : 张辰
         * ShopAddress : 郑州市金水区曼哈顿商业广场A座一层啊1209号
         */
        private String Phone;
        private String Detail;
        private String address;
        private String MainImg;
        private String ShopName;
        private int ID;
        private String RealName;
        private String ShopAddress;
        private List<String> DetailImg;

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getDetail() {
            return Detail;
        }

        public void setDetail(String Detail) {
            this.Detail = Detail;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMainImg() {
            return MainImg;
        }

        public void setMainImg(String MainImg) {
            this.MainImg = MainImg;
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

        public List<String> getDetailImg() {
            return DetailImg;
        }

        public void setDetailImg(List<String> DetailImg) {
            this.DetailImg = DetailImg;
        }
    }
}
