package com.example.ddm.appui.bean;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class GetRegisterRedPacket {

    /**
     * msg : success
     * code : 1
     * datas : {"redPacket":"1.2"}
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
         * redPacket : 1.2
         */

        private String redPacket;

        public String getRedPacket() {
            return redPacket;
        }

        public void setRedPacket(String redPacket) {
            this.redPacket = redPacket;
        }
    }
}
