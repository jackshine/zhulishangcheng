package com.example.ddm.appui.summary.nearbylist;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ddm.R;
import com.example.ddm.ShopDetailActivity;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.Category;
import com.example.ddm.appui.bean.NearbyBean;
import com.example.ddm.appui.bean.eventbus.ListViewHeight;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.home.ShopDetailFragment;
import com.example.ddm.appui.summary.detail.GoodsDetailFragment;
import com.example.ddm.appui.summary.detail.GoodsFragment;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.view.ListViewForScrollView;
import com.example.ddm.appui.view.PullToRefreshView;
import com.example.ddm.appui.view.ViewUtil;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * 数码电子
 */
public class ElectronicFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener{
    private ListView mListView;
    private RelativeLayout mLayout;
    private int page = 1;
    private int Id;
    private boolean hasMoreData = true;
    private PullToRefreshView mPullToRefreshView;
    private CommonAdapter<NearbyBean.DatasBean> mAdapter;
    private List<NearbyBean.DatasBean> mList = new ArrayList<>();
    public ElectronicFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_electronic, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mPullToRefreshView = mFindViewUtils.findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());
        mLayout = mFindViewUtils.findViewById(R.id.Relative);
        mListView = mFindViewUtils.findViewById(R.id.list_view);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mAdapter = new CommonAdapter<NearbyBean.DatasBean>(getContext(),null,R.layout.nearby_list_view) {
            @Override
            public void setViewData(ViewHolder holder, NearbyBean.DatasBean item, int position) {
//                if (item.getGraded().equals("1")) {/*星级图片*/
//                }
                if (item.getIs_delivery().equals("1")) {
                    holder.getView(R.id.new_shop).setVisibility(View.VISIBLE);
                }
                ImageView view = holder.getView(R.id.pic_sp);
                Glide.with(getContext()).load(item.getMain_img())
                        .placeholder(R.drawable.ic_launcher).crossFade()
                        .into(view);
                holder.setText(item.get_name(),R.id.txt_sp)
                        .setText("距离"+item.get_distance()+"m",R.id.distance).setText(item.getSales()+"人消费",R.id.good_sp);
            }
        };
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PreferenceManager.instance().saveShopId(String.valueOf(mList.get(position).getId()));
                ShowFragmentUtils.showFragment(getActivity(), ShopDetailFragment.class, FragmentTag.SHOPDETAILFRAGMENT,null,true);
//                mBaseActivity.showActivity(ShopDetailActivity.class,null);
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
//        getNearbyList(1,105);
        getCategory();
    }
    private void getNearbyList(int page,int id){
        showLoading();
        mPullToRefreshView.onFooterRefreshComplete();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.PAGE, String.valueOf(page));
        hashMap.put("category_id", String.valueOf(id));
        hashMap.put("state", "1");
        HttpHelper.getInstance().get(Url.NEARBY, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                LogUtils.d("附近"+response);
                final NearbyBean bean = JsonUtils.parse(response, NearbyBean.class);
                if (bean.getInfo().equals("OK")) {
                    if (bean.getDatas().size()>0) {
                        mLayout.setVisibility(View.GONE);
                        if (mList!=null) {
                            mList.addAll(bean.getDatas());
                            if (bean.getDatas().size()<20) {
                                hasMoreData = false;
                            }
                            mAdapter.update(mList);
                            mListView.setAdapter(mAdapter);
                            int listViewHeight = ViewUtil.setListViewHeightBasedOnChildren1(mListView);
                            LogUtils.d("zhdhak"+ViewUtil.setListViewHeightBasedOnChildren1(mListView));
                            EventBus.getDefault().post(new ListViewHeight(listViewHeight));
                        }
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                Toast.makeText(getContext(), error_msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getCategory() {
        Map<String, String> param = new HashMap<>();
        HttpHelper.getInstance().post(Url.GET_CATEGORY, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "获取首页分类成功：" + response);
                Category category = JsonUtils.parse(response, Category.class);
                if (category.getCode()==1&&category.getDatas().size()>0) {
                  Id=category.getDatas().get(0).getID();
                  getNearbyList(page,Id);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获取首页分类失败：" + error_msg);
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
                  getNearbyList(page,Id);
                    LogUtils.d("我的Id"+Id);
                    LogUtils.d("我的page"+page);

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
