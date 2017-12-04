package com.example.ddm.appui.summary.detail;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseActivity;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.ConfirmOrder;
import com.example.ddm.appui.bean.GetDefault;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.order.ManageAddressFragment;
import com.example.ddm.appui.utils.DecimalUtil;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * 确认订单
 */
public class ConfirmOrderTwoFragment extends BaseFragment {
    private boolean isSelect=false;
    private TextView mTextViewName, mTextViewPhone;
    private TextView mAddress;
    private TextView mAll_Shopping;
    private TextView mSubmit_Order;
    private TextView mState;
    private TextView mAll;
    private String mAddressId="";
    private TextView mMoney;
    private ImageView mGoodPic;
    private TextView mTextViewShopName,mTextViewGoodsName, mTextViewGoodsDetail,mTextViewGoodsPrise,mTextViewGoodsMarket, mTextViewGoodsNum;
    public ConfirmOrderTwoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_order_two, container, false);
    }
/* mArguments.putString("goodsNum",tv_item_number_comm_detail.getText().toString());
                    mArguments.putString("goodsState",mTextView.getText().toString());
                    mArguments.putString("shopName",mShopName);
                    mArguments.putString("goodsDetail",mGoodsDetail.getText().toString());
                    mArguments.putString("goodsPrise",memberPrice.getText().toString());
                    mArguments.putString("goodsMarket",marketPrice.getText().toString());*/
    @Override
    protected void initView(View view) {
        super.initView(view);
        mAll = mFindViewUtils.findViewById(R.id.shop_num);
        mMoney = mFindViewUtils.findViewById(R.id.all);
        mGoodPic = mFindViewUtils.findViewById(R.id.ivGoods);
        mState = mFindViewUtils.findViewById(R.id.state);
        mSubmit_Order = mFindViewUtils.findViewById(R.id.Submit_Order);
        mAll_Shopping = mFindViewUtils.findViewById(R.id.all_shopping);
        mTextViewShopName = mFindViewUtils.findViewById(R.id.Shop_name);
        mTextViewGoodsName = mFindViewUtils.findViewById(R.id.tvItemChild);
        mTextViewGoodsDetail = mFindViewUtils.findViewById(R.id.tvGoodsParam);
        mTextViewGoodsPrise = mFindViewUtils.findViewById(R.id.tvPriceNew);
        mTextViewGoodsMarket = mFindViewUtils.findViewById(R.id.tvPriceOld);
        mTextViewGoodsNum = mFindViewUtils.findViewById(R.id.tvNum);
        mTextViewName = mFindViewUtils.findViewById(R.id.name);
        mTextViewPhone = mFindViewUtils.findViewById(R.id.user_phone);
        mAddress = mFindViewUtils.findViewById(R.id.chose_address);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mState.setText(mArguments.getString("goodsState"));
        mTextViewShopName.setText(mArguments.getString("shopName"));
        mTextViewGoodsName.setText(mArguments.getString("goodsName"));
        mTextViewGoodsDetail.setText(mArguments.getString("goodsDetail"));
        mTextViewGoodsPrise.setText("￥:"+mArguments.getString("goodsPrise"));
        mTextViewGoodsMarket.setText(mArguments.getString("goodsMarket"));
        mTextViewGoodsMarket.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        mTextViewGoodsNum.setText("X"+mArguments.getString("goodsNum"));
//        mAll_Shopping.setText("合计:"+mArguments.getString("goodsNum")+"件");
//        mAll.setText("总共"+mArguments.getString("goodsNum")+"件商品");
//        mMoney.setText("小计"+ (Double.valueOf(mArguments.getString("goodsPrise"))*Integer.valueOf(mArguments.getString("goodsNum"))));
        Glide.with(getContext())
                .load(mArguments.getString("goodsUrl"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(mGoodPic);
        mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),ManageAddressFragment.class, FragmentTag.MANAGEADDRESSFRAGMENT,null,true);
            }
        });
        mSubmit_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit(mAddressId);
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        getDefault();
        getMoney();
    }
    private void getDefault(){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.getDefault, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                GetDefault getDefault = JsonUtils.parse(response, GetDefault.class);
                if (getDefault.getCode()==1) {
                        mAddressId = String.valueOf(getDefault.getDatas().getId());
                        isSelect=getDefault.getDatas().isIfDefault();
                        mTextViewName.setText(getDefault.getDatas().getName());
                        mTextViewPhone.setText(getDefault.getDatas().getPhone());
                        mAddress.setText( getDefault.getDatas().getProvince()+getDefault.getDatas().getCity()+getDefault.getDatas().getCounty()+"\n"+getDefault.getDatas().getDetail());
                } else if (getDefault.getCode()==0) {
                    mAddress.setText(getDefault.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                StringUtils.showToast(getActivity(), error_msg);
            }
        });
    }
    private void getMoney(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("goodsId", mArguments.getString("goodsId"));
        hashMap.put("num", mArguments.getString("goodsNum"));
        hashMap.put("goodsSpecificationId", mArguments.getString("specificationId"));
        HttpHelper.getInstance().post(Url.confirmOrder, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                ConfirmOrder order = JsonUtils.parse(response, ConfirmOrder.class);
                if (order.getCode()==1) {
                    mAll_Shopping.setText("合计："+order.getDatas().getTotalMoney());
                    mMoney.setText("小计："+order.getDatas().getTotalMoney());
                    mAll.setText("总共"+order.getDatas().getGoodsNum()+"件商品");
//                    mTextViewGoodsNum.setText("X"+order.getDatas().getGoodsNum());
                } else if (order.getCode()==0) {
                    StringUtils.showToast(getActivity(),order.getMsg());
                } else if (order.getCode()==-1) {
                    StringUtils.showToast(getActivity(),order.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                StringUtils.showToast(getActivity(), error_msg);
            }
        });
    }

    /**
     * 提交订单
     */
    private void Submit(String addressId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("goodsId", mArguments.getString("goodsId"));
        hashMap.put("num", mArguments.getString("goodsNum"));
        hashMap.put("goodsSpecificationId", mArguments.getString("specificationId"));
        hashMap.put("addressId", addressId);
        HttpHelper.getInstance().post(Url.submitOrder, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                ConfirmOrder order = JsonUtils.parse(response, ConfirmOrder.class);
                if (order.getCode()==1) {
                    /*订单成功跳转界面*/
                    StringUtils.showToast(getActivity(),order.getMsg());
                } else if (order.getCode()==0) {
                    StringUtils.showToast(getActivity(),order.getMsg());
                } else if (order.getCode()==-1) {
                    StringUtils.showToast(getActivity(),order.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                StringUtils.showToast(getActivity(), error_msg);
            }
        });
    }
}
