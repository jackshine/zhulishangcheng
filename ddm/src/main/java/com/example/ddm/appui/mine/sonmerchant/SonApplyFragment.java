package com.example.ddm.appui.mine.sonmerchant;
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
import com.example.ddm.appui.bean.WorkOrderBean;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.mine.merchant.DetailFragment;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
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
 *做单申请
 */
public class SonApplyFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{
    private ListView mListView;
    private RelativeLayout mRelativeLayout;
    private int page = 1;
    private boolean hasMoreData = true;
    private PullToRefreshView mPullToRefreshView;
    private List<WorkOrderBean.DatasBean> mList = new ArrayList<>();
    private CommonAdapter<WorkOrderBean.DatasBean> mAdapter;
    public SonApplyFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_son_apply, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mPullToRefreshView = mFindViewUtils.findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());
        mListView = mFindViewUtils.findViewById(R.id.list_view);
        mRelativeLayout = mFindViewUtils.findViewById(R.id.Relative);

    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter = new CommonAdapter<WorkOrderBean.DatasBean>(getContext(),null,R.layout.order_apply_item) {
            @Override
            public void setViewData(ViewHolder holder, WorkOrderBean.DatasBean item, int position) {
                holder.setText(item.getCreateTime(), R.id.txt_time).
                        setText(item.getShopYingYeMoney(), R.id.money).
                        setText(item.getCode(), R.id.order).setText("查看详情",R.id.txt);
            }
        };
        mListView.setAdapter(mAdapter);
    }
    @Override
    protected void setData() {
        super.setData();
        List(page);
    }
    private void List(int page){
        showLoading();
        mPullToRefreshView.onFooterRefreshComplete();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.PAGE, String.valueOf(page));//可以为空
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.STATEVALUE, "3");
        HttpHelper.getInstance().post(Url.WORK_ORDER, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final WorkOrderBean bean = JsonUtils.parse(response, WorkOrderBean.class);
                if (bean.getCode() == 1&&bean.getDatas().size()>0) {
                    mRelativeLayout.setVisibility(View.GONE);
                    if (mList!=null) {
                        mList.clear();
                        mList.addAll(bean.getDatas());
                        if (bean.getDatas().size()<20) {
                            hasMoreData = false;
                        }
                        mAdapter.update(mList);
                    }
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            PreferenceManager.instance().saveOrderId(String.valueOf(position));
                            ShowFragmentUtils.showFragment(getActivity(), DetailFragment.class, FragmentTag.DETAILFRAGMENT, null, true);
                        }
                    });
                } else if (bean.getCode()==0) {
                    Toast.makeText(getContext(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
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
