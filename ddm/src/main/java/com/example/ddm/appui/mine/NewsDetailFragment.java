package com.example.ddm.appui.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.InformDetailBean;
import com.example.ddm.appui.bean.NewsDetailBean;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailFragment extends BaseFragment {
    private TextView mTitle,mTime, mContent,mBack;
    public NewsDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mTitle = mFindViewUtils.findViewById(R.id.title);
        mTime = mFindViewUtils.findViewById(R.id.time);
        mContent = mFindViewUtils.findViewById(R.id.content);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        post();
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
    //消息详情
    private void post(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
//        hashMap.put(IAppKey.MESSAGEID, PreferenceManager.instance().getNewsId());
        hashMap.put(IAppKey.MESSAGEID, String.valueOf(getArguments().getInt("id")));
        HttpHelper.getInstance().post(Url.GETONENEWS, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final NewsDetailBean bean = JsonUtils.parse(response, NewsDetailBean.class);
                if (bean.getCode() == 1) {
                    mTitle.setText(bean.getDatas().getMessage().getTitle());
                    mTime.setText(bean.getDatas().getMessage().getCreateTime());
                    mContent.setText(bean.getDatas().getMessage().getContent());
                    newRead();
                } else if (bean.getCode()==0) {
                    StringUtils.showToast(getActivity(),bean.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //修改消息为已读
    private void newRead(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
//        hashMap.put(IAppKey.MESSAGEID, PreferenceManager.instance().getNewsId());
        hashMap.put(IAppKey.MESSAGEID, String.valueOf(getArguments().getInt("id")));
        HttpHelper.getInstance().post(Url.READNEWS, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final NewsDetailBean bean = JsonUtils.parse(response, NewsDetailBean.class);
                if (bean.getCode() == 1) {
                    EventBus.getDefault().post(new LoginSuccess());
                } else if (bean.getCode()==0) {
                    StringUtils.showToast(getActivity(),bean.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
