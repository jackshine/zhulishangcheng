package com.example.ddm.appui.adapter.recycle;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.ddm.R;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.UserOrderDetail;
import com.example.ddm.appui.bean.UserPurchaseOrderList;
import com.example.ddm.appui.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class AdapterOrderDetail extends ListBaseAdapter<UserOrderDetail.DatasBean.OrderDetailBean> {
    private List<UserOrderDetail.DatasBean.OrderDetailBean> mDatas = new ArrayList<>();
    private Context mContext;
    private List<CommonAdapter<UserOrderDetail.DatasBean.OrderDetailBean.GoodsOrderListBean>> pAdapterList = new ArrayList<>();
    public AdapterOrderDetail(Context context, List<UserOrderDetail.DatasBean.OrderDetailBean> datas) {
        super(context);
        mDatas = datas;
        mContext = context;
    }
    @Override
    public void setDataList(List<UserOrderDetail.DatasBean.OrderDetailBean> list) {
        for (int i = 0; i < list.size(); i++) {
            CommonAdapter<UserOrderDetail.DatasBean.OrderDetailBean.GoodsOrderListBean> pAdapter = new CommonAdapter<UserOrderDetail.DatasBean.OrderDetailBean.GoodsOrderListBean>(mContext,list.get(i).getGoodsOrderList(), R.layout.recycle_order_detail) {
                @Override
                public void setViewData(ViewHolder holder, UserOrderDetail.DatasBean.OrderDetailBean.GoodsOrderListBean item, int position) {
                    if (item.getPayOnDelivery() == 1) {
                        holder.setText("货到付款", R.id.detail);
                    } else {
                        holder.setText("暂不支持货到付款", R.id.detail);
                    }
                    holder.setText(item.getGoodsName(), R.id.content).setText("X"+item.getNum(), R.id.num);
                }
            };
            pAdapterList.add(pAdapter);
        }
        super.setDataList(list);
    }
    @Override
    public int getLayoutId() {
        return R.layout.item_pending_detail;
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        final UserOrderDetail.DatasBean.OrderDetailBean item =  getDataList().get(position);
        TextView tvStatus = holder.getView(R.id.tv_order_ddmj);
        TextView tvOne = holder.getView(R.id.tv_pending_pay);
        TextView tvTwo = holder.getView(R.id.tv_pending_cancel_order);
        TextView tvThree = holder.getView(R.id.tv_pending_seller);
        TextView tvFour = holder.getView(R.id.tv_pending_time);
        TextView tvShopName = holder.getView(R.id.tv_order_dm);
        TextView tvCountAll = holder.getView(R.id.tv_count_all);
        TextView tvPriceAll = holder.getView(R.id.tv_order_hj);
        if (item != null) {
            tvOne.setText("发货时间："+item.getDeliverTime());
            tvTwo.setText("创建时间："+item.getCreateTime());
            tvThree.setText("订单编号："+item.getZ_code());
            tvFour.setText("取消时间："+item.getCancelTime());
            tvShopName.setText(item.getShopName());
            tvPriceAll.setText("合计：￥"+item.getShopPayMoney()+"（含运费￥0.00）");
            tvCountAll.setText("共计" + item.getCounty() + "件商品");
//            tvThree.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mIOrderCallBack.onSeller();
//                }
//            });
            //list view
            ListViewForScrollView listView = holder.getView(R.id.list_view);
            if (pAdapterList.size() > 0) {
                listView.setAdapter(pAdapterList.get(position));
            }
        }
    }
    @Override
    public void onClickBack(SuperViewHolder holder, int position, View view) {

    }
}
