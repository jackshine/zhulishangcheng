package com.example.ddm.appui.mine.myhome;
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
import com.example.ddm.LoginActivity;
import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.bean.picture.CardBeanList;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 * 选择银行卡界面
 */
public class SelectCardFragment extends BaseFragment {
    private TextView mBack;
    private ListView mListView;
    private List<CardBeanList.DatasBean.CardListBean> mList = new ArrayList<>();
    private CommonAdapter<CardBeanList.DatasBean.CardListBean> mAdapter;
    public SelectCardFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_card, container, false);
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

    }

    @Override
    protected void setData() {
        super.setData();
        getBankCard(1);
    }

    /**
     * 获取银行卡列表
     */
    private void getBankCard(final int pageNum) {
        showLoading();
        mAdapter = new CommonAdapter<CardBeanList.DatasBean.CardListBean>(getActivity(), null, R.layout.card_list_item) {
            @Override
            public void setViewData(ViewHolder holder, CardBeanList.DatasBean.CardListBean item, int position) {
                ImageView view = holder.getView(R.id.pic);
                Glide.with(context).load(item.getBankImg()).placeholder(R.drawable.ic_launcher).crossFade().into(view);
                holder.setText(item.getAccountName(), R.id.integral).setText("尾号"+item.getCardNumShort(), R.id.content);
                if (item.isIsDefault()) {
                    holder.getView(R.id.time).setVisibility(View.VISIBLE);
                }
            }
        };
        mListView.setAdapter(mAdapter);
        HashMap<String, String> param = new HashMap<>();
        param.put("token", PreferenceManager.instance().getToken());
        param.put("pageNu", String.valueOf(pageNum));
        HttpHelper.getInstance().post(Url.CARDLIST, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                LogUtils.d( "获取银行卡列表成功：" + response);
                final CardBeanList beanList = JsonUtils.parse(response, CardBeanList.class);
                if (beanList.getCode()==1&&beanList.getDatas().getRecordsTotal()>0) {
                    if (mList != null) {
                        mList.clear();
                        mList.addAll(beanList.getDatas().getCardList());
                        mAdapter.update(mList);
                    }
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, String> param = new HashMap<>();
                            param.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
                            param.put(IAppKey.CARDID, String.valueOf(beanList.getDatas().getCardList().get(position).getId()));
                            HttpHelper.getInstance().post(Url.TOBEDEFAULT, param, new RawResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, String response) {
                                    final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                                    if (bean.getCode() == 1) {
                                        EventBus.getDefault().post(new UpdateCardList("更换默认银行卡"));
                                        popSelf();
                                    } else if (bean.getCode() == 0) {
                                        Toast.makeText(getActivity(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                                        PreferenceManager.instance().removeToken();
                                        mBaseActivity.showActivity(LoginActivity.class,null);
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, String error_msg) {

                                }
                            });
                        }
                    });
                } else if (beanList.getCode()==0){
                    Toast.makeText(getContext(), beanList.getMsg(), Toast.LENGTH_SHORT).show();
                } else if (beanList.getCode() == -1) {
                    Toast.makeText(getContext(), beanList.getMsg(), Toast.LENGTH_SHORT).show();
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(MainActivity.class, null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                LogUtils.d( "获取银行卡列表失败：" + error_msg);
                hiddenLoading();
            }
        });
    }
}
