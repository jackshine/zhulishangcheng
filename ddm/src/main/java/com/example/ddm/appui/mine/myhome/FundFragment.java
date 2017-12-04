package com.example.ddm.appui.mine.myhome;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.WaterBean;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.view.PullToRefreshView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
/**
 * 资金流水
 */
public class FundFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{
    private TextView mBack;
    private ListView mListView;
    private int page = 1;
    private boolean hasMoreData = true;
    private PullToRefreshView mPullToRefreshView;
    private RelativeLayout mRelativeLayout;
    private List<WaterBean.DatasBean> mList = new ArrayList<>();
    private CommonAdapter<WaterBean.DatasBean> mAdapter;
    public FundFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fund, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mPullToRefreshView = mFindViewUtils.findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());
        mRelativeLayout = mFindViewUtils.findViewById(R.id.Relative);
        mListView = mFindViewUtils.findViewById(R.id.list_view);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);

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
        mAdapter = new CommonAdapter<WaterBean.DatasBean>(getContext(),null,R.layout.account_item) {
            @Override
            public void setViewData(ViewHolder holder, WaterBean.DatasBean item, int position) {
                if (position != 0) {
                    String a = (position - 1) >= 0 ? mList.get(position - 1).getCreateDate() : "";
                    if (!a.equals(mList.get(position).getCreateDate())) {
                        holder.getView(R.id.Relative1).setVisibility(View.VISIBLE);
                        holder.setText(item.getCreateDate(), R.id.Relative1);
                    } else {
                        holder.getView(R.id.Relative1).setVisibility(View.GONE);
                    }
                } else {
                    holder.getView(R.id.Relative1).setVisibility(View.VISIBLE);
                    holder.setText(item.getCreateDate(), R.id.Relative1);
                }
                holder.setText(item.getCreateTime(), R.id.txt_time).
                        setText(item.getReferCode(), R.id.content)
                        .setText(item.getChange_num(), R.id.money).setText(item.getSource(), R.id.send);
            }
        };
        mListView.setAdapter(mAdapter);
    }
    @Override
    protected void setData() {
        super.setData();
        post(page);
    }

    private void post(int page){
        showLoading();
        mPullToRefreshView.onFooterRefreshComplete();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.PAGE, String.valueOf(page));
        HttpHelper.getInstance().post(Url.FUNDWATER, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final WaterBean bean = JsonUtils.parse(response, WaterBean.class);
                if (bean.getCode()==1&&bean.getDatas().size()>0) {
                    mList.addAll(bean.getDatas());
                    if (bean.getDatas().size()<20) {
                        hasMoreData = false;
                    }
                    mAdapter.update(mList);
                    mRelativeLayout.setVisibility(View.GONE);
                } else if (bean.getCode() == 0) {
                    Toast.makeText(getContext(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                if (Net()) {
                }
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
                    post(page);
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
