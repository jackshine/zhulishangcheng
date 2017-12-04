package com.example.ddm.appui.mine;
import android.os.Bundle;
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
import com.example.ddm.appui.bean.NewsBean;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
/**
 * 消息界面
 */
public class NewsFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{
    private TextView mBack;
    private ListView mListView;
    private RelativeLayout mRelativeLayout;
    private int page = 1;
    private boolean hasMoreData = true;
    private PullToRefreshView mPullToRefreshView;
    private List<NewsBean.DatasBean.MessagesBean> mList = new ArrayList<>();
    private CommonAdapter<NewsBean.DatasBean.MessagesBean> mAdapter;
    public NewsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
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
        mAdapter = new CommonAdapter<NewsBean.DatasBean.MessagesBean>(getContext(), null, R.layout.new_item) {
            @Override
            public void setViewData(ViewHolder holder, NewsBean.DatasBean.MessagesBean item, int position) {
                if (item.isIfRead()) {
                    holder.getView(R.id.pic).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.pic).setVisibility(View.VISIBLE);
                }
                holder.setText(item.getTitle(), R.id.integral).setText(item.getContent(), R.id.content).setText(item.getCreateTime(), R.id.time);
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            PreferenceManager.instance().saveNewsId(String.valueOf(bean.getDatas().getMessages().get(position).getId()));
                mArguments.putInt("id", mList.get(position).getId());
                ShowFragmentUtils.showFragment(getActivity(), NewsDetailFragment.class, FragmentTag.NEWSDETAILFRAGMENT, mArguments, true);
            }
        });

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
    }
    @Override
    protected void setData() {
        super.setData();
        getNews(page);
    }
    private void getNews(int page){
        showLoading();
        mPullToRefreshView.onFooterRefreshComplete();
        HashMap<String, String> parse = new HashMap<>();
        parse.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        parse.put(IAppKey.PAGENO, String.valueOf(page));
        HttpHelper.getInstance().post(Url.GETNEWS, parse, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final NewsBean bean = JsonUtils.parse(response, NewsBean.class);
                if (bean.getCode() == 1 && bean.getDatas().getMessages().size()>0) {
                    mRelativeLayout.setVisibility(View.GONE);
                    if (mList != null) {
                        mList.clear();
                        mList.addAll(bean.getDatas().getMessages());
                        mAdapter.update(mList);
                        if (bean.getDatas().getMessages().size()<20) {
                            hasMoreData = false;
                        }
                    }

                } else if (bean.getCode() == 0) {
                    StringUtils.showToast(getActivity(), bean.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
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
                    getNews(page);
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
