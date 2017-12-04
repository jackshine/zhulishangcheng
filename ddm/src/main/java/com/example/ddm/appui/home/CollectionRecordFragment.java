package com.example.ddm.appui.home;
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
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.RecordBean;
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
 * 收款记录
 */
public class CollectionRecordFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{
    private ListView mListView;
    private TextView mBack;
    private List<RecordBean.DatasBean.DataBean> mList = new ArrayList<>();
    private CommonAdapter<RecordBean.DatasBean.DataBean> mAdapter;
    private int page = 1;
    private RelativeLayout mLayout;
    private boolean hasMoreData = true;
    private PullToRefreshView mPullToRefreshView;
    public CollectionRecordFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection_record, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mLayout = mFindViewUtils.findViewById(R.id.Relative);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mPullToRefreshView = mFindViewUtils.findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());
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
        mAdapter = new CommonAdapter<RecordBean.DatasBean.DataBean>(getContext(),null,R.layout.item_record) {
            @Override
            public void setViewData(ViewHolder holder, RecordBean.DatasBean.DataBean item, int position) {
                    holder.setText(item.getCreatTime().substring(0, 4), R.id.text_1)
                            .setText("收到“" + item.getUserNmae() + "”的转账", R.id.text_2).
                            setText(item.getCreatTime().substring(5, 10), R.id.text_3).
                            setText("+"+item.getMoney(), R.id.text_4);
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mArguments.putString("phone",mList.get(position).getUserPhone());
                mArguments.putString("userNmae",mList.get(position).getUserNmae());
                mArguments.putString("money",mList.get(position).getMoney());
                mArguments.putString("state",mList.get(position).getState());
                mArguments.putString("creatTime",mList.get(position).getCreatTime());
                mArguments.putString("code",mList.get(position).getCode());
                ShowFragmentUtils.showFragment(getActivity(),RecordDetailFragment.class, FragmentTag.RECORDDETAILFRAGMENT,mArguments,true);
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        getRecord(page);
    }
    private void getRecord(int page){
        showLoading();
        mPullToRefreshView.onFooterRefreshComplete();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("pageNu", String.valueOf(page));
        HttpHelper.getInstance().post(Url.RECEIPT, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final RecordBean bean = JsonUtils.parse(response, RecordBean.class);
                if (bean.getCode()==1&&bean.getDatas().getData().size()>0) {
                    mLayout.setVisibility(View.GONE);
                    if (mList != null) {
                        mList.addAll(bean.getDatas().getData());
                        if (bean.getDatas().getData().size()<10) {
                            hasMoreData = false;
                        }
                        mAdapter.update(mList);

                    }
                } else if (bean.getCode() == 0) {
                    StringUtils.showToast(getActivity(), bean.getMsg());
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
                    getRecord(page);
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
