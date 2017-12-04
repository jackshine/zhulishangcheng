package com.example.ddm.appui.home;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.InformBean;
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
 * 通知的界面
 */
public class InformFragment extends BaseFragment {
    private TextView mBack;
    private ListView mListView;
    private CommonAdapter<InformBean.DatasBean> mAdapter;
    private List<InformBean.DatasBean> mList = new ArrayList<>();
    public InformFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inform, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
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
        mAdapter = new CommonAdapter<InformBean.DatasBean>(getContext(),null,R.layout.infrom_item) {
            @Override
            public void setViewData(ViewHolder holder, InformBean.DatasBean item, int position) {
                ImageView view = holder.getView(R.id.pic);
                Glide.with(getContext()).load(item.getImgUrl()).placeholder(R.drawable.ic_launcher).crossFade().into(view);
                holder.setText(item.getCreateTime(), R.id.time).setText(item.getTitle(), R.id.news);
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PreferenceManager.instance().saveNewsId(String.valueOf(mList.get(position).getId()));
                ShowFragmentUtils.showFragment(getActivity(),InformDetailFragment.class, FragmentTag.INFORMDETAILFRAGMENT,null,true);
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        News();
    }
    private void News(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.PAGE,"1");
        HttpHelper.getInstance().post(Url.INFORM, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final InformBean bean = JsonUtils.parse(response, InformBean.class);
                if (bean.getCode()==1) {
                    if (mList!=null) {
                        mList.clear();
                        mList.addAll(bean.getDatas());
                        mAdapter.update(mList);
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
