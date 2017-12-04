package com.example.ddm;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.BaseActivity;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.ShengFengBean;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.mine.area.DownTownFragment;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//所在地区的
public class ProvinceActivity extends BaseActivity {
    private ListView mListView;
    private List<ShengFengBean.DatasBean> mList = new ArrayList<>();
    private CommonAdapter<ShengFengBean.DatasBean> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        PokonyanApplication.getInstance().addActivity(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mListView = (ListView) findViewById(R.id.list_view);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter = new CommonAdapter<ShengFengBean.DatasBean>(this, null, R.layout.area_item) {
            @Override
            public void setViewData(ViewHolder holder, ShengFengBean.DatasBean item, int position) {
                holder.setText(item.getProvinceName(), R.id.area);
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PreferenceManager.instance().saveTown(String.valueOf(mList.get(position).getId()));
                PreferenceManager.instance().saveProvince(mList.get(position).getProvinceName());
                Bundle bundle = new Bundle();
                bundle.putString("area", String.valueOf(mList.get(position).getId()));
                ShowFragmentUtils.showFragment(ProvinceActivity.this, DownTownFragment.class, FragmentTag.DOWNTOWNFRAGMENT, bundle, true);
            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
        GetSheng();
    }
    private void GetSheng(){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        HttpHelper.getInstance().post(Url.SHENGFENG, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final ShengFengBean bean = JsonUtils.parse(response, ShengFengBean.class);
                if (bean.getCode()==1) {
                    mList.addAll(bean.getDatas());
                    mAdapter.update(mList);
                } else if (bean.getCode() == 0) {
                    Toast.makeText(ProvinceActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProvinceActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                    PreferenceManager.instance().removeToken();
                    showActivity(LoginActivity.class,null);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
            }
        });
    }
}
