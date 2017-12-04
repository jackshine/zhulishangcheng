package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class TiXianBean {
    /**
     * msg : success
     * serviceCharge : 0.08
     * code : 1
     * remark : ["1、每周一次申请积分兑换的机会。（申请时间段为每周一至周五，周六日不可申请）","2、兑换周期为一周时间。（即本周的兑换申请，系统会在下周一审核并兑换，节假日顺延）","3、兑换额大于等于100且必须为整百可兑换","4、兑换额转入银行卡收取8.0%服务费","5、如要更换银行卡，请到个人中心更改"]
     */
    private String msg;
    private String serviceCharge;
    private int code;
    private List<String> remark;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getServiceCharge() {
        return serviceCharge;
    }
    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getRemark() {
        return remark;
    }

    public void setRemark(List<String> remark) {
        this.remark = remark;
    }
}
