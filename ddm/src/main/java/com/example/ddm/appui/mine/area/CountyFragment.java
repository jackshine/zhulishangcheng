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
import com.example.ddm.ProvinceActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.CountyBean;
import com.example.ddm.appui.bean.DownTownBean;
import com.example.ddm.appui.bean.eventbus.Address;
import com.example.ddm.appui.bean.eventbus.AddressId;
import com.example.ddm.appui.bean.eventbus.AddressTwo;
import com.example.ddm.appui.bean.eventbus.AddressTwoId;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountyFragment extends BaseFragment {
    private ListView mListView;
    private List<CountyBean.DatasBean> mList = new ArrayList<>();
    private CommonAdapter<CountyBean.DatasBean> mAdapter;

    public CountyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_county, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mListView = mFindViewUtils.findViewById(R.id.list_view);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter = new CommonAdapter<CountyBean.DatasBean>(getContext(),null,R.layout.area_item) {
            @Override
            public void setViewData(ViewHolder holder, CountyBean.DatasBean item, int position) {
                holder.setText(item.getAreaName(),R.id.area);
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new AddressId(PreferenceManager.instance().getTown(), String.valueOf(mList.get(position).getCityId()), String.valueOf(mList.get(position).getId())));
                EventBus.getDefault().post(new AddressTwoId(PreferenceManager.instance().getTown(), String.valueOf(mList.get(position).getCityId()), String.valueOf(mList.get(position).getId())));
                EventBus.getDefault().post(new Address(PreferenceManager.instance().getProvince(),PreferenceManager.instance().getCity(),mList.get(position).getAreaName()));
                EventBus.getDefault().post(new AddressTwo(PreferenceManager.instance().getProvince(),PreferenceManager.instance().getCity(),mList.get(position).getAreaName()));
                PreferenceManager.instance().saveTown(mList.get(position).getAreaName());
                mBaseActivity.finish();
            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
        GetCounty();
    }
    private void GetCounty(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cityId", mArguments.getString("downid"));
        HttpHelper.getInstance().post(Url.COUNTY, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final CountyBean bean = JsonUtils.parse(response, CountyBean.class);
                if (bean.getCode()==1) {
                    mList.addAll(bean.getDatas());
                    mAdapter.update(mList);
                } else if (bean.getCode() == 0) {
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
