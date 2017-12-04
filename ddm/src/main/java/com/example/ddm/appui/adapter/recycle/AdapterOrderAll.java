package com.example.ddm.appui.adapter.recycle;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ddm.R;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.UserPurchaseOrderList;
import com.example.ddm.appui.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class AdapterOrderAll extends ListBaseAdapter<UserPurchaseOrderList.DatasBean.ZuoDanListBean> {
    private List<UserPurchaseOrderList.DatasBean.ZuoDanListBean> mDatas = new ArrayList<>();
    private Context mContext;
    private List<CommonAdapter<UserPurchaseOrderList.DatasBean.ZuoDanListBean.GoodsOrderListBean>> pAdapterList = new ArrayList<>();

    private IOrderCallBack mIOrderCallBack;

    public AdapterOrderAll(Context context, List<UserPurchaseOrderList.DatasBean.ZuoDanListBean> datas, IOrderCallBack iOrderCallBack) {
        super(context);
        mDatas = datas;
        mContext = context;
        mIOrderCallBack = iOrderCallBack;
    }

    @Override
    public void setDataList(List<UserPurchaseOrderList.DatasBean.ZuoDanListBean> list) {
        for (int i = 0; i < list.size(); i++) {
            CommonAdapter<UserPurchaseOrderList.DatasBean.ZuoDanListBean.GoodsOrderListBean> pAdapter = new CommonAdapter<UserPurchaseOrderList.DatasBean.ZuoDanListBean.GoodsOrderListBean>
                    (mContext,list.get(i).getGoodsOrderList(), R.layout.recycle_order_detail) {
                @Override
                public void setViewData(ViewHolder holder, UserPurchaseOrderList.DatasBean.ZuoDanListBean.GoodsOrderListBean item, int position) {
                    ImageView view = holder.getView(R.id.icon);
                    Glide.with(mContext).load(item.getPicture()).placeholder(R.drawable.ic_launcher).crossFade().into(view);
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
        return R.layout.item_pending_pay;
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        final UserPurchaseOrderList.DatasBean.ZuoDanListBean item =  getDataList().get(position);
        TextView tvStatus = holder.getView(R.id.tv_order_ddmj);
        TextView tvOne = holder.getView(R.id.tv_pending_pay);
        TextView tvTwo = holder.getView(R.id.tv_pending_cancel_order);
        TextView tvThree = holder.getView(R.id.tv_pending_seller);
        TextView tvShopName = holder.getView(R.id.tv_order_dm);
        TextView tvCountAll = holder.getView(R.id.tv_count_all);
        TextView tvPriceAll = holder.getView(R.id.tv_order_hj);
        if (item != null) {
            if (item.getStateValue() == 1) {
                tvStatus.setText(item.getState());
                tvOne.setVisibility(View.GONE);
                tvTwo.setVisibility(View.GONE);
                tvThree.setVisibility(View.GONE);
            } else if (item.getStateValue() == 2) {
                tvStatus.setText(item.getState());
                tvOne.setText("立即评价");
                tvTwo.setText("删除订单");
                tvTwo.setVisibility(View.VISIBLE);
                tvOne.setVisibility(View.VISIBLE);
                tvThree.setVisibility(View.GONE);
            } else if (item.getStateValue() == 3) {
                tvStatus.setText(item.getState());
                tvOne.setVisibility(View.GONE);
                tvTwo.setVisibility(View.GONE);
                tvThree.setVisibility(View.GONE);
            } else if (item.getStateValue() == 4) {
                tvStatus.setText(item.getState());
                tvOne.setVisibility(View.GONE);
                tvTwo.setVisibility(View.VISIBLE);
                tvThree.setVisibility(View.GONE);
                tvTwo.setText("取消订单");
            } else if (item.getStateValue() == 5) {
                tvStatus.setText(item.getState());
                tvOne.setVisibility(View.VISIBLE);
                tvTwo.setVisibility(View.GONE);
                tvThree.setVisibility(View.GONE);
                tvOne.setText("确认收购");
            } else if (item.getStateValue() == 6) {
                tvStatus.setText(item.getState());
                tvOne.setVisibility(View.GONE);
                tvTwo.setVisibility(View.GONE);
                tvThree.setVisibility(View.GONE);
            } else if (item.getStateValue() == 8) {
                tvStatus.setText(item.getState());
                tvOne.setVisibility(View.GONE);
                tvTwo.setVisibility(View.GONE);
                tvThree.setVisibility(View.GONE);
            }
            tvShopName.setText(item.getShopName());
            tvPriceAll.setText("合计：￥"+item.getZ_shopYingYeMoney()+"（含运费￥0.00）");
            tvCountAll.setText("共计" + item.getGoodsOrderList().size() + "件商品");
            tvOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view instanceof TextView) {
                        switch (((TextView) view).getText().toString()) {
                            case "确认收货":
                                mIOrderCallBack.onReceive(item);
                                break;
                            case "立即评价":
                                mIOrderCallBack.onEvaluate(item);
                                break;
                        }
                    }
                }
            });
            tvTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view instanceof TextView) {
                        switch (((TextView) view).getText().toString()) {
                            case "取消订单":
                                mIOrderCallBack.onCancel(item);
                                break;
                            case "删除订单":
                                mIOrderCallBack.onDelete(item,position);
                                remove(position);
                                break;
                        }
                    }
                }
            });
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
