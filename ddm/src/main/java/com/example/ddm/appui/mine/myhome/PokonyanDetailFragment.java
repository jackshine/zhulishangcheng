package com.example.ddm.appui.mine.myhome;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.ddm.appui.bean.FundBean;
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
 * 叮当明细
 */
public class PokonyanDetailFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{
    private ListView mListView;
    private RelativeLayout mRelativeLayout;
    private int page = 1;
    private boolean hasMoreData = true;
    private PullToRefreshView mPullToRefreshView;
    private List<FundBean.DatasBean> mList = new ArrayList<>();
    private CommonAdapter<FundBean.DatasBean> mAdapter;
    private TextView mBack;
    public PokonyanDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokonyan_detail, container, false);
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
        mAdapter = new CommonAdapter<FundBean.DatasBean>(getContext(),null,R.layout.detail_item) {
            @Override
            public void setViewData(ViewHolder holder, FundBean.DatasBean item, int position) {
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
                        setText(item.getChange_num(), R.id.money).
                        setText(item.getDingdangType(), R.id.send_code).
                        setText(item.getSource(), R.id.send).
                        setText(item.getDingDangCode(),R.id.content);
            }
        };
        mListView.setAdapter(mAdapter);
    }
    @Override
    protected void setData() {
        super.setData();
        List(page);
    }
    //获取订单明细数据
    private void List(int page){
       showLoading();
        mPullToRefreshView.onFooterRefreshComplete();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.PAGE, String.valueOf(page));
        HttpHelper.getInstance().post(Url.DDETAIL, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final FundBean bean = JsonUtils.parse(response, FundBean.class);
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
