package com.example.ddm.appui.mine.share;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.recycle.CommenRecycleAdapter;
import com.example.ddm.appui.adapter.recycle.SuperViewHolder;
import com.example.ddm.appui.bean.GetCommission;
import com.example.ddm.appui.bean.SearchUserCoupon;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
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
 * 分享流水
 */
public class ShareWaterFragment extends BaseFragment {
    private LRecyclerView mLRecyclerView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int mPage = 1;
    private CommenRecycleAdapter<GetCommission.DatasBean.DataBean> mAdapter;
    private List<GetCommission.DatasBean.DataBean> mList = new ArrayList<>();
    public ShareWaterFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_water, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mLRecyclerView = mFindViewUtils.findViewById(R.id.lrv_public_recyclerview);
        mAdapter = new CommenRecycleAdapter<GetCommission.DatasBean.DataBean>(getContext(), R.layout.order_item) {
            @Override
            public void setViewData(SuperViewHolder holder, GetCommission.DatasBean.DataBean item, int position) {
                holder.getView(R.id.success).setVisibility(View.GONE);
                holder.getView(R.id.check).setVisibility(View.GONE);
                holder.setText(item.getDate(), R.id.txt_time).
                        setText(item.getMoney(), R.id.money).
                        setText(item.getCode(), R.id.order);
//                        .setText(item.getShopState(), R.id.success);
            }
            @Override
            public void setListener(SuperViewHolder holder, View view) {
                holder.setOnClickListener(R.id.watch);
            }
            @Override
            public void onClickBack(SuperViewHolder holder, int position, View view) {
                mArguments.putString("code",mList.get(position).getCode());
                ShowFragmentUtils.showFragment(getActivity(),ShareDetailFragment.class, FragmentTag.SHAREDETAILFRAGMENT,mArguments,true);
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
        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetCommission(mPage);
            }
        });
        mLRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPage++;
                GetCommission(mPage);
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        GetCommission(mPage);
    }
    private void GetCommission(int page){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.PAGE, String.valueOf(page));
        HttpHelper.getInstance().post(Url.getCommission, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                GetCommission getCommission = JsonUtils.parse(response, GetCommission.class);
                if (getCommission.getCode()==1) {
                    mList.addAll(getCommission.getDatas().getData());
                    mAdapter.setDataList(mList);
                    mLRecyclerView.refreshComplete(1000);
                } else if (getCommission.getCode()==0) {
                    StringUtils.showToast(getActivity(),getCommission.getMsg());
                } else if (getCommission.getCode()==-1) {
                    StringUtils.showToast(getActivity(),getCommission.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                mLRecyclerView.refreshComplete(1000);
                StringUtils.showToast(getActivity(),error_msg);
            }
        });
    }
}
