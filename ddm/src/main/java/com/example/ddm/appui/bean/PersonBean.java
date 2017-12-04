package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/17.
 *
 */

public class PersonBean {
    /**
     * msg : success
     * code : 1
     * datas : {"user":{"sexValue":4,"qq":"","realName":"king","userTypeValue":1,"weixin":"19971225","identity":"410221199101023456","sex":"男","name":"18838998708","id":38,"userType":"普通用户","email":"11504348872@qq.com","avotorr":""}}
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
         * user : {"sexValue":4,"qq":"","realName":"king","userTypeValue":1,"weixin":"19971225","identity":"410221199101023456","sex":"男","name":"18838998708","id":38,"userType":"普通用户","email":"11504348872@qq.com","avotorr":""}
         */
        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * sexValue : 4
             * qq :
             * realName : king
             * userTypeValue : 1
             * weixin : 19971225
             * identity : 410221199101023456
             * sex : 男
             * name : 18838998708
             * id : 38
             * userType : 普通用户
             * email : 11504348872@qq.com
             * avotorr :
             */
            private int sexValue;
            private String qq;
            private String realName;
            private int userTypeValue;
            private String weixin;
            private String identity;
            private String sex;
            private String name;
            private int id;
            private String userType;
            private String email;
            private String avotorr;

            public int getSexValue() {
                return sexValue;
            }

            public void setSexValue(int sexValue) {
                this.sexValue = sexValue;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public int getUserTypeValue() {
                return userTypeValue;
            }

            public void setUserTypeValue(int userTypeValue) {
                this.userTypeValue = userTypeValue;
            }

            public String getWeixin() {
                return weixin;
            }

            public void setWeixin(String weixin) {
                this.weixin = weixin;
            }

            public String getIdentity() {
                return identity;
            }

            public void setIdentity(String identity) {
                this.identity = identity;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String   getAvotorr() {
                return avotorr;
            }

            public void setAvotorr(String avotorr) {
                this.avotorr = avotorr;
            }
        }
    }

}
