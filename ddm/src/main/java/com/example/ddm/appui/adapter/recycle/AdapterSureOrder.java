package com.example.ddm.appui.adapter.recycle;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ddm.R;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.GetUserShoppingCart;
import com.example.ddm.appui.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class AdapterSureOrder extends ListBaseAdapter<GetUserShoppingCart.DatasBean> {
    private List<GetUserShoppingCart.DatasBean> mDatas = new ArrayList<>();
    private Context mContext;
    private List<CommonAdapter<GetUserShoppingCart.DatasBean.GoodsBean>> pAdapterList = new ArrayList<>();
    public AdapterSureOrder(Context context, List<GetUserShoppingCart.DatasBean> datas) {
        super(context);
        mDatas = datas;
        mContext = context;
    }
    @Override
    public void setDataList(List<GetUserShoppingCart.DatasBean> list) {
        for (int i = 0; i < list.size(); i++) {
            CommonAdapter<GetUserShoppingCart.DatasBean.GoodsBean> pAdapter = new CommonAdapter<GetUserShoppingCart.DatasBean.GoodsBean>(mContext,list.get(i).getGoods(), R.layout.recycle_order) {
                @Override
                public void setViewData(ViewHolder holder, GetUserShoppingCart.DatasBean.GoodsBean item, int position) {
                    ImageView view = holder.getView(R.id.icon);
                    Glide.with(mContext).load(item.getGoodsMainImage()).placeholder(R.drawable.ic_launcher).crossFade().into(view);
                    if (item.getPayOnDelivery() == 1) {
                        holder.setText("货到付款", R.id.detail);
                    } else {
                        holder.setText("暂不支持货到付款", R.id.detail);
                    }
                    holder.setText(item.getGoodsName(), R.id.content).setText("X"+item.getCount(), R.id.tvNum).setText("¥"+item.getMemberPrice(),R.id.tvPriceNew).setText(item.getPropertiesName(),R.id.specification);
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
        final GetUserShoppingCart.DatasBean item =  getDataList().get(position);
        TextView tvStatus = holder.getView(R.id.tv_order_ddmj);
        TextView tvOne = holder.getView(R.id.tv_pending_pay);
        TextView tvTwo = holder.getView(R.id.tv_pending_cancel_order);
        TextView tvThree = holder.getView(R.id.tv_pending_seller);
        TextView tvShopName = holder.getView(R.id.tv_order_dm);
        TextView tvCountAll = holder.getView(R.id.tv_count_all);
        TextView tvPriceAll = holder.getView(R.id.tv_order_hj);
        if (item != null) {
            tvStatus.setVisibility(View.GONE);
            tvOne.setVisibility(View.GONE);
            tvTwo.setVisibility(View.GONE);
            tvThree.setVisibility(View.GONE);
            tvShopName.setText(item.getShopName());
            tvPriceAll.setText("合计：￥"+item.getShopSelectMoney()+"（含运费￥0.00）");
            tvCountAll.setText("共计" + item.getShopSelectCount() + "件商品");
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
