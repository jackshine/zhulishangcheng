package com.example.ddm.appui.mine;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.recycle.AdapterOrderAll;
import com.example.ddm.appui.adapter.recycle.AdapterOrderDetail;
import com.example.ddm.appui.bean.UserOrderDetail;
import com.example.ddm.appui.bean.UserPurchaseOrderList;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
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
 * 订单详情
 */
public class MyOrderDetailFragment extends BaseFragment {
    private LRecyclerView mLRecyclerView;
    private LRecyclerViewAdapter mAdapterOrder;
    private AdapterOrderDetail mAdapterOrderAll;
    private List<UserOrderDetail.DatasBean.OrderDetailBean> mDatasOrder = new ArrayList<>();
    public MyOrderDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_order_detail, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mLRecyclerView = mFindViewUtils.findViewById(R.id.lrv_public_recyclerview);
        mAdapterOrderAll = new AdapterOrderDetail(getContext(), mDatasOrder);
        mAdapterOrder = new LRecyclerViewAdapter(mAdapterOrderAll);
        mLRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.a_1dp)
                .setColorResource(R.color.white)
                .build();
        mLRecyclerView.addItemDecoration(divider);
        mLRecyclerView.setAdapter(mAdapterOrder);
    }

    @Override
    protected void setListener() {
        super.setListener();

    }

    @Override
    protected void setData() {
        super.setData();
        userOrderDetail(mArguments.getString("zuoDanId"));
    }

    private void userOrderDetail(String zuoDanId){
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
    hashMap.put("zuoDanId", zuoDanId);
    HttpHelper.getInstance().post(Url.userOrderDetail, hashMap, new RawResponseHandler() {
        @Override
        public void onSuccess(int statusCode, String response) {
            UserOrderDetail bean = JsonUtils.parse(response, UserOrderDetail.class);
            if (bean.getCode()==1) {
                mDatasOrder.add(bean.getDatas().getOrderDetail());
                mAdapterOrderAll.setDataList(mDatasOrder);
            } else if (bean.getCode()==0) {
                StringUtils.showToast(getActivity(),bean.getMsg());
            }
        }

        @Override
        public void onFailure(int statusCode, String error_msg) {

        }
    });
}
}
