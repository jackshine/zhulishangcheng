package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class RecordBean {

    /**
     * msg : success
     * code : 1
     * datas : {"pageNu":1,"data":[{"code":"063915864054371","creatTime":"2017-08-08 11:31:32","money":"100.00","userNmae":"李","userPhone":"15093112512","toUserPhone":"15528196521","toUserName":"WE","state":"已取消"}],"totalPage":1,"recordsTotal":1}
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
         * pageNu : 1
         * data : [{"code":"063915864054371","creatTime":"2017-08-08 11:31:32","money":"100.00","userNmae":"李","userPhone":"15093112512","toUserPhone":"15528196521","toUserName":"WE","state":"已取消"}]
         * totalPage : 1
         * recordsTotal : 1
         */

        private int pageNu;
        private int totalPage;
        private int recordsTotal;
        private List<DataBean> data;

        public int getPageNu() {
            return pageNu;
        }

        public void setPageNu(int pageNu) {
            this.pageNu = pageNu;
        }

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

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * code : 063915864054371
             * creatTime : 2017-08-08 11:31:32
             * money : 100.00
             * userNmae : 李
             * userPhone : 15093112512
             * toUserPhone : 15528196521
             * toUserName : WE
             * state : 已取消
             */

            private String code;
            private String creatTime;
            private String money;
            private String userNmae;
            private String userPhone;
            private String toUserPhone;
            private String toUserName;
            private String state;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCreatTime() {
                return creatTime;
            }

            public void setCreatTime(String creatTime) {
                this.creatTime = creatTime;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getUserNmae() {
                return userNmae;
            }

            public void setUserNmae(String userNmae) {
                this.userNmae = userNmae;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }

            public String getToUserPhone() {
                return toUserPhone;
            }

            public void setToUserPhone(String toUserPhone) {
                this.toUserPhone = toUserPhone;
            }

            public String getToUserName() {
                return toUserName;
            }
            public void setToUserName(String toUserName) {
                this.toUserName = toUserName;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
    }
}
