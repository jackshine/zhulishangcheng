package com.example.ddm.appui.mine.area;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.DownTownBean;
import com.example.ddm.appui.bean.ShengFengBean;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownTownFragment extends BaseFragment {
    private ListView mListView;
    private List<DownTownBean.DatasBean> mList = new ArrayList<>();
    private CommonAdapter<DownTownBean.DatasBean> mAdapter;

    public DownTownFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_down_town, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mListView = mFindViewUtils.findViewById(R.id.list_view);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mAdapter = new CommonAdapter<DownTownBean.DatasBean>(getContext(), null, R.layout.area_item) {
            @Override
            public void setViewData(ViewHolder holder, DownTownBean.DatasBean item, int position) {
                holder.setText(item.getCityName(),R.id.area);
            }
        };
        mListView.setAdapter(mAdapter);
       mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               PreferenceManager.instance().saveCity(mList.get(position).getCityName());
               mArguments.putString("downid", String.valueOf(mList.get(position).getId()));
               ShowFragmentUtils.showFragment(getActivity(),CountyFragment.class, FragmentTag.COUNTYFRAGMENT,mArguments,true);
           }
       });
    }
    @Override
    protected void setData() {
        super.setData();
        GetDown();
    }
    private void GetDown(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("provinceId", mArguments.getString("area"));
        HttpHelper.getInstance().post(Url.DOWNTOWN, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final DownTownBean bean = JsonUtils.parse(response, DownTownBean.class);
                if (bean.getCode()==1) {
                    mList.addAll(bean.getDatas());
                    mAdapter.update(mList);
                }else if (bean.getCode() == 0) {
                    Toast.makeText(getContext(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    PreferenceManager.instance().removeToken();
                    Toast.makeText(getContext(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                    mBaseActivity.showActivity(LoginActivity.class, null);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
}
