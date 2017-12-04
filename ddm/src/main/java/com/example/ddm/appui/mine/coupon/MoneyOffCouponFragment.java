package com.example.ddm.appui.mine.coupon;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.recycle.CommenRecycleAdapter;
import com.example.ddm.appui.adapter.recycle.SuperViewHolder;
import com.example.ddm.appui.bean.GetCouponNumber;
import com.example.ddm.appui.bean.GetZongJiangRecord;
import com.example.ddm.appui.bean.SearchUserCoupon;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 * 满减优惠券
 */
public class MoneyOffCouponFragment extends BaseFragment {
    private LRecyclerView mLRecyclerView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int mPage=1;
    private int mState=1;
    private int mOverdue=1;
    private RadioButton mRadioButton1,mRadioButton2, mRadioButton3;
    private CommenRecycleAdapter<SearchUserCoupon.DatasBeanX.DatasBean> mAdapter;
    private List<SearchUserCoupon.DatasBeanX.DatasBean> mList = new ArrayList<>();
    public MoneyOffCouponFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_money_off_coupon, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mRadioButton1 = mFindViewUtils.findViewById(R.id.btn1);
        mRadioButton2 = mFindViewUtils.findViewById(R.id.btn2);
        mRadioButton3 = mFindViewUtils.findViewById(R.id.btn3);
        mLRecyclerView = mFindViewUtils.findViewById(R.id.lrv_public_recyclerview);
        mAdapter = new CommenRecycleAdapter<SearchUserCoupon.DatasBeanX.DatasBean>(getContext(), R.layout.coupon_item) {
            @Override
            public void setViewData(SuperViewHolder holder, SearchUserCoupon.DatasBeanX.DatasBean item, int position) {
                if (!item.getState().equals("有效")) {
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.coupon_no);
                }
                ImageView view = holder.getView(R.id.glide_icon);
                Glide.with(getContext()).load(Url.encode+item.getId()+".jpeg").placeholder(R.drawable.ic_launcher).crossFade().into(view);
                holder.setText(""+item.getMinusMoney(),R.id.money).setText("满减"+item.getOrderMoney()+"可用",R.id.all)
                        .setText(item.getStarTime()+"_"+item.getEndTime(),R.id.time1).
                        setText(item.getShopName(),R.id.shop_name);
            }
            @Override
            public void setListener(SuperViewHolder holder, View view) {
              view.setOnClickListener(holder);
            }
            @Override
            public void onClickBack(SuperViewHolder holder, int position, View view) {
                StringUtils.showToast(getActivity(),"立即领取");
            }
        };
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.a_5dp)
                .setColorResource(R.color.gray_background)
                .build();
        mLRecyclerView.addItemDecoration(divider);
        mLRecyclerView.setAdapter(mLRecyclerViewAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        getCouponNumber(1);
        mRadioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mState = 1;
                mOverdue = 1;
                mRadioButton2.setChecked(false);
                mRadioButton3.setChecked(false);
                mList.clear();
                SearchUserCoupon(mPage,1,1,1);
            }
        });
        mRadioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mState = 0;
                mRadioButton1.setChecked(false);
                mRadioButton3.setChecked(false);
                mList.clear();
                SearchUserCoupon(mPage,1,0,1);
            }
        });
        mRadioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mState = 1;
                mOverdue = 0;
                mRadioButton2.setChecked(false);
                mRadioButton1.setChecked(false);
                mList.clear();
                SearchUserCoupon(mPage,1,1,0);
            }
        });
        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();
//                mAdapter.clear();
                SearchUserCoupon(mPage,1,mState,mOverdue);
            }
        });
        mLRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPage++;
                SearchUserCoupon(mPage,1,mState,mOverdue);
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        SearchUserCoupon(mPage,1,mState,mOverdue);
    }
    /**
     * 满减优惠券couponId=1
     * @param page
     */
    private void SearchUserCoupon(int page,int couponId,int state,int overdue){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.PAGE, String.valueOf(page));
        hashMap.put("couponId", String.valueOf(couponId));
        hashMap.put("state", String.valueOf(state));
        hashMap.put("overdue", String.valueOf(overdue));
        HttpHelper.getInstance().post(Url.searchUserCoupon, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                LogUtils.d("集合"+response);
                hiddenLoading();
                SearchUserCoupon searchUserCoupon = JsonUtils.parse(response, SearchUserCoupon.class);
                mList.addAll(searchUserCoupon.getDatas().getDatas());
                mAdapter.setDataList(mList);
                mLRecyclerView.refreshComplete(1000);
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mLRecyclerView.refreshComplete(1000);
                hiddenLoading();
                StringUtils.showToast(getActivity(),error_msg);
            }
        });

    }
    private void getCouponNumber(int couponId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("couponId", String.valueOf(couponId));
        HttpHelper.getInstance().post(Url.getCouponNumber, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                GetCouponNumber getCouponNumber = JsonUtils.parse(response, GetCouponNumber.class);
                if (getCouponNumber.getCode()==1) {
                    mRadioButton1.setText("未使用("+getCouponNumber.getDatas().getYouxiao()+")");
                    mRadioButton2.setText("已使用("+getCouponNumber.getDatas().getYishiyong()+")");
                    mRadioButton3.setText("已过期("+getCouponNumber.getDatas().getYiguoqi()+")");
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                StringUtils.showToast(getActivity(), null);
            }
        });
    }
}
