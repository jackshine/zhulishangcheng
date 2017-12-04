package com.example.ddm.appui.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.recycle.AdapterOrderAll;
import com.example.ddm.appui.adapter.recycle.IOrderCallBack;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.UserPurchaseOrderList;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 待收货
 */
public class MyOrderTakeGoodsFragment extends BaseFragment {
    private LRecyclerView mLRecyclerView;
    private LRecyclerViewAdapter mAdapterOrder;
    private AdapterOrderAll mAdapterOrderAll;
    private int mPage = 1;
    private List<UserPurchaseOrderList.DatasBean.ZuoDanListBean> mDatasOrder = new ArrayList<>();

    public MyOrderTakeGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_order_take_goods, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mLRecyclerView = mFindViewUtils.findViewById(R.id.lrv_public_recyclerview);
        mAdapterOrderAll = new AdapterOrderAll(getContext(), mDatasOrder, new IOrderCallBack() {
            @Override
            public void onReceive(UserPurchaseOrderList.DatasBean.ZuoDanListBean item) {/*确认收货*/
                receiptOrder(item.getZuoDanId());
            }

            @Override
            public void onEvaluate(UserPurchaseOrderList.DatasBean.ZuoDanListBean item) {/*立即评价*/

            }

            @Override
            public void onCancel(UserPurchaseOrderList.DatasBean.ZuoDanListBean item) {/*取消订单*/
//                cancel(item.getZuoDanId());

            }

            @Override
            public void onDelete(UserPurchaseOrderList.DatasBean.ZuoDanListBean item, int position) {
//                delete(item.getZuoDanId(),position);

            }
        });
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
        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestOrderAllRefresh(mPage);
            }
        });
        mLRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPage++;
                requestOrderAllRefresh(mPage);
            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
        requestOrderAllRefresh(mPage);
    }
    /**
     * @param page
     *待收货
     */
    private void requestOrderAllRefresh(int page){
        showLoading();
        HashMap<String, String> param = new HashMap<>();
        param.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        param.put("pageNu", String.valueOf(page));
        param.put("zuoDanStateValue", "5");
        HttpHelper.getInstance().post(Url.userPurchaseOrderList, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                LogUtils.d("全部订单"+response);
                UserPurchaseOrderList json = JsonUtils.parse(response, UserPurchaseOrderList.class);
                if (json.getCode()==1) {
                    if (json.getDatas().getZuoDanList().size()>0) {
                        if (mDatasOrder!=null) {
                            mDatasOrder.clear();
                        }
                        mDatasOrder.addAll(json.getDatas().getZuoDanList());
                    }
                } else if (json.getCode()==0) {
                    StringUtils.showToast(getActivity(),json.getMsg());
                } else if (json.getCode()==-1) {
                    StringUtils.showToast(getActivity(),json.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
                mAdapterOrderAll.setDataList(mDatasOrder);
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
    /**
     * @param zuoDanId
     * 确认收货
     */
    private void receiptOrder(int zuoDanId){
        showLoading();
        HashMap<String, String> param = new HashMap<>();
        param.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        param.put("zuoDanId", String.valueOf(zuoDanId));
        HttpHelper.getInstance().post(Url.delZuoDan, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                SendCodeBean json = JsonUtils.parse(response, SendCodeBean.class);
                if (json.getCode()==1) {
                    StringUtils.showToast(getActivity(),"确认收货");
                    EventBus.getDefault().post(new LoginSuccess());
                } else if (json.getCode()==0) {
                    StringUtils.showToast(getActivity(),json.getMsg());
                } else if (json.getCode()==-1) {
                    StringUtils.showToast(getActivity(),json.getMsg());
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
}
