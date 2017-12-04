package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/19.
 * 订单明细
 */

public class FundBean {

    /**
     * msg : success
     * code : 1
     * datas : [{"DingDangCode":"SDG31500004114130","dingdangTypeValue":2,"Change_num":"+4","ShopName":"樊店铺测试子店","CreateTime":"11:48","Id":74,"CreateDate":"2017-07-14","dingdangType":"叮当","Source":"做单"},
     * {"DingDangCode":"SDG31500002599478","dingdangTypeValue":2,"Change_num":"+2","ShopName":"樊店铺测试子店","CreateTime":"11:23","Id":72,"CreateDate":"2017-07-14","dingdangType":"叮当","Source":"做单"},
     * {"DingDangCode":"SDG41499935645422","dingdangTypeValue":2,"Change_num":"+1","ShopName":"测试店铺主店铺","CreateTime":"16:47","Id":70,"CreateDate":"2017-07-13","dingdangType":"叮当","Source":"做单"},
     * {"DingDangCode":"SDG31499909686240","dingdangTypeValue":2,"Change_num":"+2","ShopName":"测试店铺主店铺","CreateTime":"09:34","Id":69,"CreateDate":"2017-07-13","dingdangType":"叮当","Source":"做单"},
     * "DingDangCode":"SDG31499909501078","dingdangTypeValue":2,"Change_num":"+13","ShopName":"测试店铺主店铺","CreateTime":"09:31","Id":67,"CreateDate":"2017-07-13","dingdangType":"叮当","Source":"做单"},{
     * "DingDangCode":"SDG11499849483660","dingdangTypeValue":2,"Change_num":"+108","ShopName":"测试店铺主店铺","CreateTime":"16:51","Id":65,"CreateDate":"2017-07-12","dingdangType":"叮当","Source":"做单"},
     * {"DingDangCode":"SDG11499849441575","dingdangTypeValue":2,"Change_num":"+9","ShopName":"测试店铺主店铺","CreateTime":"16:50","Id":63,"CreateDate":"2017-07-12","dingdangType":"叮当","Source":"做单"},{"DingDangCode":"SDG11499848392373","dingdangTypeValue":2,"Change_num":"+7","ShopName":"测试店铺主店铺","CreateTime":"16:33","Id":61,"CreateDate":"2017-07-12","dingdangType":"叮当","Source":"做单"},{"DingDangCode":"SDG11499848318131","dingdangTypeValue":2,"Change_num":"+2","ShopName":"测试店铺主店铺","CreateTime":"16:31","Id":59,"CreateDate":"2017-07-12","dingdangType":"叮当","Source":"做单"},{"DingDangCode":"SDG71499410862654","dingdangTypeValue":2,"Change_num":"+3","ShopName":"测试店铺主店铺","CreateTime":"15:01","Id":55,"CreateDate":"2017-07-07","dingdangType":"叮当","Source":"做单"},{"DingDangCode":"SDG401499410738628","dingdangTypeValue":2,"Change_num":"+2","ShopName":"测试店铺主店铺","CreateTime":"14:58","Id":53,"CreateDate":"2017-07-07","dingdangType":"叮当","Source":"做单"}]
     */

    private String msg;
    private int code;
    private List<DatasBean> datas;

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

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * DingDangCode : SDG31500004114130
         * dingdangTypeValue : 2
         * Change_num : +4
         * ShopName : 樊店铺测试子店
         * CreateTime : 11:48
         * Id : 74
         * CreateDate : 2017-07-14
         * dingdangType : 叮当
         * Source : 做单
         */

        private String DingDangCode;
        private int dingdangTypeValue;
        private String Change_num;
        private String ShopName;
        private String CreateTime;
        private int Id;
        private String CreateDate;
        private String dingdangType;
        private String Source;

        public String getDingDangCode() {
            return DingDangCode;
        }

        public void setDingDangCode(String DingDangCode) {
            this.DingDangCode = DingDangCode;
        }

        public int getDingdangTypeValue() {
            return dingdangTypeValue;
        }

        public void setDingdangTypeValue(int dingdangTypeValue) {
            this.dingdangTypeValue = dingdangTypeValue;
        }

        public String getChange_num() {
            return Change_num;
        }

        public void setChange_num(String Change_num) {
            this.Change_num = Change_num;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public String getDingdangType() {
            return dingdangType;
        }

        public void setDingdangType(String dingdangType) {
            this.dingdangType = dingdangType;
        }

        public String getSource() {
            return Source;
        }

        public void setSource(String Source) {
            this.Source = Source;
        }
    }
}
