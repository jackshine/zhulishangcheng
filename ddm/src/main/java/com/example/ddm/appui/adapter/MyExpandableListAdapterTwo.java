package com.example.ddm.appui.adapter;

import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ddm.R;
import com.example.ddm.appui.adapter.clickback.OnShoppingCartChangeListener;
import com.example.ddm.appui.adapter.clickback.ShoppingCartBiz;
import com.example.ddm.appui.bean.GetSpecification;
import com.example.ddm.appui.bean.GetUserShoppingCart;
import com.example.ddm.appui.bean.ShoppingCar;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.order.ShoppingCarFragment;
import com.example.ddm.appui.summary.detail.ConfirmOrderFragment;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.view.CustomDialog;
import com.example.ddm.appui.widget.dialog.UIAlertView;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class MyExpandableListAdapterTwo extends BaseExpandableListAdapter {
    private FragmentActivity mContext;
    private List<GetUserShoppingCart.DatasBean> mListGoods = new ArrayList<GetUserShoppingCart.DatasBean>();
    public MyExpandableListAdapterTwo(FragmentActivity context) {
        mContext = context;
    }
    public void setList(List<GetUserShoppingCart.DatasBean> mListGoods) {
        this.mListGoods = mListGoods;
    }

    public int getGroupCount() {
        return mListGoods.size();
    }
    public int getChildrenCount(int groupPosition) {
        return mListGoods.get(groupPosition).getGoods().size();
    }
    public Object getGroup(int groupPosition) {
        return mListGoods.get(groupPosition);
    }
    public Object getChild(int groupPosition, int childPosition) {
        return mListGoods.get(groupPosition).getGoods().get(childPosition);
    }
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    public boolean hasStableIds() {
        return false;
    }
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
       GroupViewHolder holder = null;
        if (convertView == null) {
            holder = new GroupViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_elv_group_test, parent, false);
            holder.tvGroup = (TextView) convertView.findViewById(R.id.tvShopNameGroup);
            holder.tvEdit = (TextView) convertView.findViewById(R.id.tvEdit);
            holder.subtotal = (TextView) convertView.findViewById(R.id.subtotal);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            holder.shop_num = (TextView) convertView.findViewById(R.id.shop_num);
            holder.ivCheckGroup = (ImageView) convertView.findViewById(R.id.ivCheckGroup);
            holder.imageView4 = convertView.findViewById(R.id.imageView4);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.subtotal.setVisibility(View.VISIBLE);
        holder.shop_num.setVisibility(View.VISIBLE);
        holder.money.setText("￥:"+mListGoods.get(groupPosition).getShopSelectMoney());
        holder.shop_num.setText("共"+mListGoods.get(groupPosition).getShopSelectCount()+"件商品");
        holder.imageView4.setVisibility(View.GONE);
        holder.tvGroup.setText(mListGoods.get(groupPosition).getShopName());
        holder.ivCheckGroup.setTag(groupPosition);
        holder.ivCheckGroup.setVisibility(View.GONE);
        holder.tvEdit.setTag(groupPosition);
        holder.tvEdit.setVisibility(View.GONE);
        return convertView;
    }
    /**
     * child view
     */
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_elv_child_test, parent, false);
            holder.tvGoods = (ImageView) convertView.findViewById(R.id.ivGoods);
            holder.tvChild = (TextView) convertView.findViewById(R.id.tvItemChild);
            holder.tvDel = (TextView) convertView.findViewById(R.id.tvDel);
            holder.ivCheckGood = (ImageView) convertView.findViewById(R.id.ivCheckGood);
            holder.rlEditStatus = (RelativeLayout) convertView.findViewById(R.id.rlEditStatus);
            holder.llGoodInfo = (LinearLayout) convertView.findViewById(R.id.llGoodInfo);
            holder.ivAdd = (ImageView) convertView.findViewById(R.id.ivAdd);
            holder.ivReduce = (ImageView) convertView.findViewById(R.id.ivReduce);
            holder.tvGoodsParam = (TextView) convertView.findViewById(R.id.tvGoodsParam);
            holder.tvPriceNew = (TextView) convertView.findViewById(R.id.tvPriceNew);
            holder.tvPriceOld = (TextView) convertView.findViewById(R.id.tvPriceOld);
            holder.tvPriceOld.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//数字被划掉效果
            holder.tvNum = (TextView) convertView.findViewById(R.id.tvNum);
            holder.tvNum2 = (TextView) convertView.findViewById(R.id.tvNum2);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        GetUserShoppingCart.DatasBean.GoodsBean goods = mListGoods.get(groupPosition).getGoods().get(childPosition);
        String priceNew = "¥" + goods.getMemberPrice();
        String priceOld = "¥" + goods.getMemberPrice();
//        String d[] = goods.getGoodsMainImage().split(",");
        int num = goods.getCount();
        String pdtDesc = goods.getPropertiesName();
        LogUtils.d("购物车图片"+goods.getGoodsMainImage());
        String goodName = mListGoods.get(groupPosition).getGoods().get(childPosition).getGoodsName();
        holder.ivCheckGood.setTag(groupPosition + "," + childPosition);
        holder.ivCheckGood.setVisibility(View.GONE);
        holder.tvChild.setText(goodName);
        Glide.with(mContext).load(goods.getGoodsMainImage()).placeholder(R.drawable.ic_launcher).crossFade().into(holder.tvGoods);
        holder.tvPriceNew.setText(priceNew);
        holder.tvPriceOld.setText(priceOld);
        holder.tvNum.setText("X " + num);
        holder.tvNum2.setText(""+num);
        holder.tvGoodsParam.setText(pdtDesc);
        holder.ivAdd.setTag(goods);
        holder.ivReduce.setTag(goods);
        holder.tvDel.setTag(groupPosition + "," + childPosition);
//        holder.tvDel.setTag(groupPosition + "," + childPosition);
        return convertView;
    }
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    class GroupViewHolder {
        TextView tvGroup;
        TextView shop_num;
        TextView money;
        TextView subtotal;
        TextView tvEdit;
        ImageView ivCheckGroup;
        ImageView imageView2;
        View imageView4;

    }
    class ChildViewHolder {
        /** 商品名称 */
        TextView tvChild;
        /** 商品规格 */
        TextView tvGoodsParam;
        /** 选中 */
        ImageView ivCheckGood;
        /** 非编辑状态 */
        LinearLayout llGoodInfo;
        /** 编辑状态 */
        RelativeLayout rlEditStatus;
        /** +1 */
        ImageView ivAdd;
        /** -1 */
        ImageView ivReduce;
        /** 删除 */
        TextView tvDel;
        /** 新价格 */
        TextView tvPriceNew;
        /** 旧价格 */
        TextView tvPriceOld;
        /** 商品状态的数量 */
        TextView tvNum;
        /** 编辑状态的数量 */
        TextView tvNum2;
        ImageView tvGoods;
    }
}
