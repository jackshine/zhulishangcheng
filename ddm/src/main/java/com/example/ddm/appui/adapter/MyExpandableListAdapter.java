package com.example.ddm.appui.adapter;

import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.example.ddm.R;
import com.example.ddm.appui.adapter.clickback.OnShoppingCartChangeListener;
import com.example.ddm.appui.adapter.clickback.ShoppingCartBiz;
import com.example.ddm.appui.bean.GetGoodSpecification;
import com.example.ddm.appui.bean.GetSpecification;
import com.example.ddm.appui.bean.ShoppingCar;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.order.ShoppingCarFragment;
import com.example.ddm.appui.summary.detail.ConfirmOrderFragment;
import com.example.ddm.appui.summary.detail.ConfirmOrderTwoFragment;
import com.example.ddm.appui.summary.detail.GoodsTwoFragment;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.view.CustomDialog;
import com.example.ddm.appui.widget.dialog.UIAlertView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private FragmentActivity mContext;
    private List<ShoppingCar.DatasBean> mListGoods = new ArrayList<ShoppingCar.DatasBean>();
    private OnShoppingCartChangeListener mChangeListener;
    private ShoppingCarFragment mFragment;
    private GetSpecification dateModle;
    private boolean isChildSelected;
    private CustomDialog dialog;
    private boolean isSelectAll = false;
    public MyExpandableListAdapter(FragmentActivity context) {
        mContext = context;
        mFragment = new ShoppingCarFragment();
    }
    public void setList(List<ShoppingCar.DatasBean> mListGoods) {
        this.mListGoods = mListGoods;
        setSettleInfo();
    }
    public void setOnShoppingCartChangeListener(OnShoppingCartChangeListener changeListener) {
        this.mChangeListener = changeListener;
    }
    public View.OnClickListener getAdapterListener() {
        return listener;
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
            holder.ivCheckGroup = (ImageView) convertView.findViewById(R.id.ivCheckGroup);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.tvGroup.setText(mListGoods.get(groupPosition).getShopName());

        ShoppingCartBiz.checkItem(mListGoods.get(groupPosition).isShopSelect(), holder.ivCheckGroup);
        boolean isEditing = mListGoods.get(groupPosition).isEditing();
        if (isEditing) {
            holder.tvEdit.setText("完成");
        } else {
            holder.tvEdit.setText("编辑");
        }
        holder.ivCheckGroup.setTag(groupPosition);
        holder.ivCheckGroup.setOnClickListener(listener);
        holder.tvEdit.setTag(groupPosition);
        holder.tvEdit.setOnClickListener(listener);
        holder.tvGroup.setOnClickListener(listener);
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
//            holder.show_dialog = (TextView) convertView.findViewById(R.id.show_dialog);
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
        ShoppingCar.DatasBean.GoodsBean goods = mListGoods.get(groupPosition).getGoods().get(childPosition);
        if (mListGoods.get(groupPosition).getGoods().get(childPosition).getShoppingCartStateValue() == 1) {
             isChildSelected = true;
        } else {
            isChildSelected = false;
        }
//        boolean isChildSelected = mListGoods.get(groupPosition).getGoods().get(childPosition).isChildSelected();
        boolean isEditing = goods.isEditing();
        String priceNew = "¥" + goods.getMemberPrice();
        String priceOld = "¥" + goods.getMemberPrice();
//        String d[] = goods.getGoodsMainImage().split(",");
        int num = goods.getCount();
        String pdtDesc = goods.getPropertiesName();
        LogUtils.d("购物车图片"+goods.getGoodsMainImage());
        String goodName = mListGoods.get(groupPosition).getGoods().get(childPosition).getGoodsName();
        holder.ivCheckGood.setTag(groupPosition + "," + childPosition);
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
        holder.tvDel.setTag(groupPosition + "," + childPosition);
        ShoppingCartBiz.checkItem(isChildSelected, holder.ivCheckGood);
        if (isEditing) {
            holder.llGoodInfo.setVisibility(View.GONE);
            holder.rlEditStatus.setVisibility(View.VISIBLE);
        } else {
            holder.llGoodInfo.setVisibility(View.VISIBLE);
            holder.rlEditStatus.setVisibility(View.GONE);
        }
        holder.ivCheckGood.setOnClickListener(listener);
        holder.tvDel.setOnClickListener(listener);
        holder.ivAdd.setOnClickListener(listener);
        holder.ivReduce.setOnClickListener(listener);
        holder.llGoodInfo.setOnClickListener(listener);
        return convertView;
    }
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                //main
                case R.id.ivSelectAll:
                    isSelectAll = ShoppingCartBiz.selectAll(mListGoods, isSelectAll, (ImageView) v);
                    setSettleInfo();
                    notifyDataSetChanged();
                    break;
                case R.id.tvEditAll:/*全部删除*/
                    break;
                case R.id.btnSettle:
                    if (ShoppingCartBiz.hasSelectedGoods(mListGoods)) {
                        ShowFragmentUtils.showFragment(mContext, ConfirmOrderFragment.class, FragmentTag.CONFIRMORDERFRAGMENT,null,true);
                    } else {
                        Toast.makeText(mContext, "亲，请选择商品", Toast.LENGTH_SHORT).show();
                    }
                    //group
                    break;
                case R.id.tvEdit://切换界面，属于特殊处理，假如没打算切换界面，则不需要这块代码
                    int groupPosition2 = Integer.parseInt(String.valueOf(v.getTag()));
                    boolean isEditing = !(mListGoods.get(groupPosition2).isEditing());
                    mListGoods.get(groupPosition2).setEditing(isEditing);
                    for (int i = 0; i < mListGoods.get(groupPosition2).getGoods().size(); i++) {
                        mListGoods.get(groupPosition2).getGoods().get(i).setEditing(isEditing);
                    }
                    notifyDataSetChanged();
                    break;
                case R.id.ivCheckGroup:
                    int groupPosition3 = Integer.parseInt(String.valueOf(v.getTag()));
                    isSelectAll = ShoppingCartBiz.selectGroup(mListGoods, groupPosition3);
                    selectAll();
                    setSettleInfo();
                    notifyDataSetChanged();
                    break;
                //child
                case R.id.ivCheckGood:
                    String tag = String.valueOf(v.getTag());
                    if (tag.contains(",")) {
                        String s[] = tag.split(",");
                        int groupPosition = Integer.parseInt(s[0]);
                        int childPosition = Integer.parseInt(s[1]);
                        isSelectAll = ShoppingCartBiz.selectOne(mListGoods, groupPosition, childPosition);
                        selectAll();
                        setSettleInfo();
                        notifyDataSetChanged();
                    }
                    break;
                case R.id.tvDel:
                    String tagPos = String.valueOf(v.getTag());
                    if (tagPos.contains(",")) {
                        String s[] = tagPos.split(",");
                        int groupPosition = Integer.parseInt(s[0]);
                        int childPosition = Integer.parseInt(s[1]);
                        showDelDialog(groupPosition, childPosition);
                    }
                    break;
                case R.id.ivAdd:
                    ShoppingCartBiz.addOrReduceGoodsNum(true, (ShoppingCar.DatasBean.GoodsBean) v.getTag(), ((TextView) (((View) (v.getParent())).findViewById(R.id.tvNum2))));
                    setSettleInfo();
                    break;
                case R.id.ivReduce:
                    ShoppingCartBiz.addOrReduceGoodsNum(false, (ShoppingCar.DatasBean.GoodsBean) v.getTag(), ((TextView) (((View) (v.getParent())).findViewById(R.id.tvNum2))));
                    setSettleInfo();
                    break;
                case R.id.llGoodInfo:
//                    ToastHelper.getInstance()._toast("商品详情，暂未实现");
                    break;
                case R.id.tvShopNameGroup:
//                    ToastHelper.getInstance()._toast("商铺详情，暂未实现");
                    break;
//                case R.id.show_dialog:
//
//                    break;
            }
        }
    };
    private void getSpecification(String id){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("goodid", id);
        HttpHelper.getInstance().post(Url.GETSPECIFICATION, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                LogUtils.d("集合"+response);
                dateModle = JsonUtils.parse(response, GetSpecification.class);
                GetSpecification.DatasBean datasBean = JsonUtils.parse(response, GetSpecification.DatasBean.class);
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
    private void selectAll() {
        if (mChangeListener != null) {
            mChangeListener.onSelectItem(isSelectAll);
        }
    }

    private void setSettleInfo() {
        String[] infos = ShoppingCartBiz.getShoppingCount(mListGoods);
        //删除或者选择商品之后，需要通知结算按钮，更新自己的数据；
        if (mChangeListener != null && infos != null) {
            mChangeListener.onDataChange(infos[0], infos[1]);
        }
    }

    private void showDelDialog(final int groupPosition, final int childPosition) {
        final UIAlertView delDialog = new UIAlertView(mContext, "温馨提示", "确认删除该商品吗?",
                "取消", "确定");
        delDialog.show();
        delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
                                       public void doLeft() {
                                           delDialog.dismiss();
                                       }
                                       public void doRight() {
                                           String productID = mListGoods.get(groupPosition).getGoods().get(childPosition).getShoppingCartId();
                                           ShoppingCartBiz.delGood(productID);
                                           delGoods(groupPosition, childPosition);
                                           setSettleInfo();
                                           notifyDataSetChanged();
                                           delDialog.dismiss();
                                       }
                                   }
        );
    }
    private void delGoods(int groupPosition, int childPosition) {
        mListGoods.get(groupPosition).getGoods().remove(childPosition);
        if (mListGoods.get(groupPosition).getGoods().size() == 0) {
            mListGoods.remove(groupPosition);
        }
        notifyDataSetChanged();
    }
    class GroupViewHolder {
        TextView tvGroup;
        TextView tvEdit;
        ImageView ivCheckGroup;
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
        TextView show_dialog;
    }
}
