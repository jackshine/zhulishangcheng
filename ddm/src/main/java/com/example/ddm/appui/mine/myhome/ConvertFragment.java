package com.example.ddm.appui.mine.myhome;
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
import com.example.ddm.appui.bean.ExchangeBean;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
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
 * 兑换明细
 */
public class ConvertFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{
    private ListView mListView;
    private RelativeLayout mRelativeLayout;
    private int page = 1;
    private boolean hasMoreData = true;
    private PullToRefreshView mPullToRefreshView;
    private List<ExchangeBean.DatasBean> mList = new ArrayList<>();
    private CommonAdapter<ExchangeBean.DatasBean> mAdapter;
    private TextView mBack;
    public ConvertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_convert, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mPullToRefreshView = mFindViewUtils.findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());
        mRelativeLayout = mFindViewUtils.findViewById(R.id.Relative);
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
        mAdapter = new CommonAdapter<ExchangeBean.DatasBean>(getContext(),null,R.layout.change_pay_item) {
            @Override
            public void setViewData(ViewHolder holder, ExchangeBean.DatasBean item, int position) {
                holder.setText("订单号："+item.getCode(), R.id.integral).
                        setText("-"+item.getMoney(), R.id.check).
                        setText(item.getCreateTime(),R.id.content).setText(item.getState(),R.id.money);
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PreferenceManager.instance().saveID(String.valueOf(position));
                LogUtils.d("dddddd"+PreferenceManager.instance().getID());
                ShowFragmentUtils.showFragment(getActivity(),ConvertPlanFragment.class, FragmentTag.CONVERTPLANFRAGMENT,null,true);
            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
        getList(page);
    }

    private void getList(int page) {
        showLoading();
        mPullToRefreshView.onFooterRefreshComplete();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.PAGE, String.valueOf(page));
        HttpHelper.getInstance().post(Url.EXCHANGEBEAN, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final ExchangeBean bean = JsonUtils.parse(response, ExchangeBean.class);
                if (bean.getCode()==1&&bean.getDatas().size()>0) {
                    mRelativeLayout.setVisibility(View.GONE);
                    mList.addAll(bean.getDatas());
                    if (bean.getDatas().size()<20) {
                        hasMoreData = false;
                    }
                    mAdapter.update(mList);
                } else if (bean.getCode() == 0) {
                    Toast.makeText(getContext(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
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
                    getList(page);
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

