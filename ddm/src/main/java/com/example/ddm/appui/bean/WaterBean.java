package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/20.
 */

public class WaterBean {

    /**
     * msg : success
     * code : 1
     * datas : [{"ReferCode":"CDG401499410738622","Change_num":"+14.00","CreateTime":"14:29","Id":147,"CreateDate":"2017-07-10","Source":"送积分"}]
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
         * ReferCode : CDG401499410738622
         * Change_num : +14.00
         * CreateTime : 14:29
         * Id : 147
         * CreateDate : 2017-07-10
         * Source : 送积分
         */

        private String ReferCode;
        private String Change_num;
        private String CreateTime;
        private int Id;
        private String CreateDate;
        private String Source;

        public String getReferCode() {
            return ReferCode;
        }

        public void setReferCode(String ReferCode) {
            this.ReferCode = ReferCode;
        }

        public String getChange_num() {
            return Change_num;
        }

        public void setChange_num(String Change_num) {
            this.Change_num = Change_num;
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

        public String getSource() {
            return Source;
        }

        public void setSource(String Source) {
            this.Source = Source;
        }
    }
}
