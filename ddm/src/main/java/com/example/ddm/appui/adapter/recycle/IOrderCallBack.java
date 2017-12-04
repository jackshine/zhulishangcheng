package com.example.ddm.appui.adapter.recycle;


import com.example.ddm.appui.bean.UserPurchaseOrderList;

/**
 * Created by Nianner Wang on 2017/9/27.
 * wytaper495@qq.com
 * 类注释：
 */

public interface IOrderCallBack {
    /**
     * 确认收货
     */
    void onReceive(UserPurchaseOrderList.DatasBean.ZuoDanListBean item);

    /**
     * 评价
     */
    void onEvaluate(UserPurchaseOrderList.DatasBean.ZuoDanListBean item);

    /**
     * 取消订单
     */
    void onCancel(UserPurchaseOrderList.DatasBean.ZuoDanListBean item);

    /**
     * 删除订单
     */
    void onDelete(UserPurchaseOrderList.DatasBean.ZuoDanListBean item,int position);

}
