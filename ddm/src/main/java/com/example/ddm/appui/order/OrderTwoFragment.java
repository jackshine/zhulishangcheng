package com.example.ddm.appui.order;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.OrderBean;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.view.PullToRefreshView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 我的界面中的订单
 */
public class OrderTwoFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{
    private TextView mBack;
    private ListView mListView;
    private RelativeLayout mLayout;
    private int page = 1;
    private boolean hasMoreData = true;
    private PullToRefreshView mPullToRefreshView;
    private CommonAdapter<OrderBean.DatasBean> mAdapter;
    private List<OrderBean.DatasBean> mList = new ArrayList<>();
    public OrderTwoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_two, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mPullToRefreshView = mFindViewUtils.findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());
        mLayout = mFindViewUtils.findViewById(R.id.Relative);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mListView = mFindViewUtils.findViewById(R.id.list_view);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
        mAdapter = new CommonAdapter<OrderBean.DatasBean>(getContext(), null, R.layout.order_item) {
            @Override
            public void setViewData(ViewHolder holder, OrderBean.DatasBean item, int position) {
                holder.setText(item.getCreateTime(), R.id.txt_time).
                        setText(item.getGoodOnePrice(), R.id.money).
                        setText(item.getCode(), R.id.order).setText(item.getShopState(),R.id.success);
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PreferenceManager.instance().saveOrderId(String.valueOf(position));
                ShowFragmentUtils.showFragment(getActivity(), OrderDeailFragment.class, FragmentTag.ORDERDEAILFRAGMENT, null, true);
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        List(page);
    }
    //我的订单
    //我的订单
    private void List(int page){
        showLoading();
        mPullToRefreshView.onFooterRefreshComplete();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.PAGE, String.valueOf(page));
        HttpHelper.getInstance().post(Url.My_Order, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final OrderBean bean = JsonUtils.parse(response, OrderBean.class);
                LogUtils.d("我的token====="+PreferenceManager.instance().getToken());
                if (bean.getCode()==1&&bean.getDatas().size()>0) {
                    mLayout.setVisibility(View.GONE);
                    if (mList != null) {
                        mList.addAll(bean.getDatas());
                        if (bean.getDatas().size()<20) {
                            hasMoreData = false;
                        }
                        mAdapter.update(mList);
                    }
                } else if (bean.getCode() == 0) {
                    StringUtils.showToast(getActivity(), bean.getMsg());
                }else if (bean.getCode()==-1) {
                    StringUtils.showToast(getActivity(),bean.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                Toast.makeText(mBaseActivity, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtils.d("ddddddddddddddddddd");
                if (hasMoreData) {
                    page++;
                    List(page);
                    LogUtils.d("ddddddddddddddddddd");
                    Toast.makeText(getContext(), "加载更多数据", Toast.LENGTH_LONG).show();
                } else {
                    mPullToRefreshView.onFooterRefreshComplete();
                    Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                }

            }

        }, 1000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {

    }
}
