package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class DaZhuangPanMessage {

    /**
     * msg : success
     * img : http://ddm-image.oss-cn-beijing.aliyuncs.com/dzp/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20170926175345.jpg
     * code : 1
     * invitationCount : 0
     * remark : ["2红包积分可抽奖一次","邀请十个好友，并注册成功，额外获取一次抽奖机会","被邀请的好友也可获取一次抽奖机会","被邀请的好友，注册成功后直接成为自己的创业合伙人，可在我的分享团队中查看"]
     * userId :
     * islogin : false
     * userCouponCount : 0
     */

    private String msg;
    private String img;
    private int code;
    private int invitationCount;
    private String userId;
    private boolean islogin;
    private int userCouponCount;
    private List<String> remark;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getInvitationCount() {
        return invitationCount;
    }

    public void setInvitationCount(int invitationCount) {
        this.invitationCount = invitationCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isIslogin() {
        return islogin;
    }

    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }

    public int getUserCouponCount() {
        return userCouponCount;
    }

    public void setUserCouponCount(int userCouponCount) {
        this.userCouponCount = userCouponCount;
    }

    public List<String> getRemark() {
        return remark;
    }

    public void setRemark(List<String> remark) {
        this.remark = remark;
    }
}
