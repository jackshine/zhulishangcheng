package com.example.ddm.appui.home;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.ClassifyBean;
import com.example.ddm.appui.bean.HomeListViewList;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 * 分类商店
 */
public class ShoppingFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener {
    private TextView mTextView,mBack;
    private ListView mListView;
    private int page = 1;
    private boolean hasMoreData = true;
    private PullToRefreshView mPullToRefreshView;
    private RelativeLayout mRelativeLayout;
    private CommonAdapter<ClassifyBean.DatasBean> mAdapter;
    private List<ClassifyBean.DatasBean> mList=new ArrayList<>();
    public ShoppingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping, container, false);
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
        mTextView = mFindViewUtils.findViewById(R.id.app_title_text);
        mListView = mFindViewUtils.findViewById(R.id.list_view);

    }
    //商家分类解析
    private void classifyShop(int page){
        showLoading("正在努力加载");
        mPullToRefreshView.onFooterRefreshComplete();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.CATEGORYID, String.valueOf(mArguments.getInt("classid")));
        hashMap.put(IAppKey.PAGE, String.valueOf(page));
        hashMap.put("city", PreferenceManager.instance().getLocation());
        HttpHelper.getInstance().post(Url.CLASS_SHOP, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final ClassifyBean classifyBean = JsonUtils.parse(response, ClassifyBean.class);
                if (classifyBean.getCode() == 1&&classifyBean.getDatas().size()>0) {
                    mRelativeLayout.setVisibility(View.GONE);
                    if (mList != null) {
                        mList.addAll(classifyBean.getDatas());
                        if (classifyBean.getDatas().size()<1) {
                            hasMoreData = false;
                        }
                        mAdapter.update(mList);
                    }
                    mTextView.setText(PreferenceManager.instance().getClassName());
                    mTextView.setTextColor(0xFF000000);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            PreferenceManager.instance().saveShopId(String.valueOf(mList.get(position).getID()));
                            ShowFragmentUtils.showFragment(getActivity(), ShoppingDetailFragment.class, FragmentTag.SHOPPINGDETAILFRAGMENT, null, true);
                        }
                    });
                } else if (classifyBean.getCode() == 0) {
                    mPullToRefreshView.onFooterRefreshComplete();
                    Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                mPullToRefreshView.onFooterRefreshComplete();
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
        mAdapter = new CommonAdapter<ClassifyBean.DatasBean>(getContext(), null, R.layout.home_list_view_item) {
            @Override
            public void setViewData(ViewHolder holder, ClassifyBean.DatasBean item, int position) {
                if (item.getIsNewShop() == 1) {
                    holder.getView(R.id.new_shop).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.new_shop).setVisibility(View.GONE);
                }
                ImageView view = holder.getView(R.id.pic_sp);
                Glide.with(getContext()).load(item.getMinImg()).placeholder(R.drawable.ic_launcher).crossFade().into(view);
                holder.setText(item.getShopName(), R.id.txt_sp).setText(item.getPhone(), R.id.phone)
                        .setText(item.getRealName(), R.id.name).setText(item.getShopAddress(), R.id.place);
            }
        };
        mListView.setAdapter(mAdapter);
    }
    @Override
    protected void setData() {
        super.setData();
        classifyShop(page);
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtils.d("ddddddddddddddddddd");
                if (hasMoreData) {
                    page++;
                    classifyShop(page);
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
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullToRefreshView.onHeaderRefreshComplete("更新于:"
                        + Calendar.getInstance().getTime().toLocaleString());
                mPullToRefreshView.onHeaderRefreshComplete();
                Toast.makeText(getContext(), "数据刷新完成!", Toast.LENGTH_LONG).show();
            }
        }, 1000);
    }
}
