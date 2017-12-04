package com.example.ddm.appui.summary.detail;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ddm.LoginActivity;
import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.MyExpandableListAdapter;
import com.example.ddm.appui.adapter.MyExpandableListAdapterTwo;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.adapter.clickback.OnShoppingCartChangeListener;
import com.example.ddm.appui.adapter.clickback.ShoppingCartBiz;
import com.example.ddm.appui.adapter.recycle.AdapterOrderAll;
import com.example.ddm.appui.adapter.recycle.AdapterSureOrder;
import com.example.ddm.appui.bean.ConfirmOrder;
import com.example.ddm.appui.bean.GetDefault;
import com.example.ddm.appui.bean.GetUserShoppingCart;
import com.example.ddm.appui.bean.ShoppingCar;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.order.ManageAddressFragment;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 确认订单
 */
public class ConfirmOrderFragment extends BaseFragment {
    private LRecyclerView mLRecyclerView;
    private LRecyclerViewAdapter mAdapterOrder;
    private AdapterSureOrder mAdapterOrderAll;
    private TextView mTextViewAllShopping, mTextViewSubmitOrder;
    private TextView mTextViewName, mTextViewPhone;
    private TextView mAddress;
    private String mAddressId="";
    private boolean isSelect=false;
    private List<GetUserShoppingCart.DatasBean> mDatasBeen = new ArrayList<>();
    public ConfirmOrderFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_order, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mTextViewAllShopping = mFindViewUtils.findViewById(R.id.all_shopping);
        mTextViewSubmitOrder = mFindViewUtils.findViewById(R.id.Submit_Order);
//        mListViewForScrollView = mFindViewUtils.findViewById(R.id.list_view);
        mTextViewName = mFindViewUtils.findViewById(R.id.name);
        mTextViewPhone = mFindViewUtils.findViewById(R.id.user_phone);
        mAddress = mFindViewUtils.findViewById(R.id.chose_address);
        mLRecyclerView = mFindViewUtils.findViewById(R.id.lrv_public_recyclerview);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mAdapterOrderAll=new AdapterSureOrder(getContext(),mDatasBeen);
        mAdapterOrder = new LRecyclerViewAdapter(mAdapterOrderAll);
        mLRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.a_1dp)
                .setColorResource(R.color.white)
                .build();
        mLRecyclerView.addItemDecoration(divider);
        mLRecyclerView.setPullRefreshEnabled(false);
        mLRecyclerView.setAdapter(mAdapterOrder);
        mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),ManageAddressFragment.class, FragmentTag.MANAGEADDRESSFRAGMENT,null,true);
            }
        });
        mTextViewSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelect) {
//                    StringUtils.showToast(getActivity(),"发起支付");
                    Submit(mAddressId);
                }
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        getDefault();
        getGetuserShoppingCart();
    }
    /**
     * 获取确认订单列表
     */
    private void getGetuserShoppingCart(){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("isSettlement", "true");
        HttpHelper.getInstance().post(Url.GETUSERSHOPPINGCART, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                LogUtils.d("商品详情"+response);
                hiddenLoading();
                GetUserShoppingCart shoppingCar=JsonUtils.parse(response, GetUserShoppingCart.class);
                mTextViewAllShopping.setText("合计："+shoppingCar.getTotalMoney());
                if (shoppingCar.getCode()==1) {
                    if (mDatasBeen!=null) {
                        mDatasBeen.clear();
                        mDatasBeen.addAll(shoppingCar.getDatas());
                    }
                    mAdapterOrderAll.setDataList(mDatasBeen);
                } else if (shoppingCar.getCode()==0) {
                    StringUtils.showToast(getActivity(),shoppingCar.getMsg());
                } else if (shoppingCar.getCode()==-1) {
                    StringUtils.showToast(getActivity(),shoppingCar.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                StringUtils.showToast(getActivity(),error_msg);
            }
        });
    }
    /**
     * 获取用户收货地址
     */
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
    /**
     * 提交订单
     */
    private void Submit(String addressId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
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
