package com.example.ddm.appui.mine.myhome;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.eventbus.Update;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.bean.picture.CardBeanList;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.view.PullToRefreshView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 * 银行卡列表
 */
public class BindCardListFragment extends BaseFragment {
    private ListView mListView;
    private TextView mAdd,mBack;
    private RelativeLayout mRelativeLayout;
    private List<CardBeanList.DatasBean.CardListBean> mList = new ArrayList<>();
    private CommonAdapter<CardBeanList.DatasBean.CardListBean> mAdapter;
    public BindCardListFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bind_card_list, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mRelativeLayout = mFindViewUtils.findViewById(R.id.Relative);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mAdd = mFindViewUtils.findViewById(R.id.app_title_action);
        mListView = mFindViewUtils.findViewById(R.id.list_view);
        getBankCard(1);
    }
    @Subscribe
    public void getCardList(UpdateCardList card) {
        LogUtils.d("收到身份证::::::");
        if (card.getMsg().equals("刷新银行卡列表")) {
            LogUtils.d("qqqqqqqqqqqqqqqqqqqq");
            getBankCard(1);
            mListView.setAdapter(mAdapter);
            LogUtils.d("cccccccccccccccccccccccc");
        }
    }
    @Subscribe
    public void getCard(Update list) {
        LogUtils.d("收到身份证::::::");
        if (list.getUpdateMsg().equals("再次刷新银行卡列表")) {
            LogUtils.d("成功");
            getBankCard(1);
            mListView.setAdapter(mAdapter);
            LogUtils.d("失败");
        }
    }
    @Override
    protected void setListener() {
        super.setListener();
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShowFragmentUtils.showFragment(getActivity(),BindCardFragment.class, FragmentTag.BINDCARDFRAGMENT,null,true);
                mBaseActivity.showActivity(BindCardActivity.class,null);
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
        mAdapter = new CommonAdapter<CardBeanList.DatasBean.CardListBean>(getActivity(), null, R.layout.item_my_bank_card_lv) {
            @Override
            public void setViewData(ViewHolder holder, CardBeanList.DatasBean.CardListBean item, int position) {
                ImageView view = holder.getView(R.id.iv_bank_icon);
                Glide.with(context).load(item.getBankImg()).placeholder(R.drawable.ic_launcher).crossFade().into(view);
//                String i = item.getCardNum().substring(item.getCardNum().length() - 4, item.getCardNum().length());
                holder.setText(item.getAccountName(), R.id.tv_bank_user).setText(item.getCardNumShort(), R.id.tv_xing_four).setText(item.getBank(),R.id.tv_bank_name);
                if (item.getBankId() == 1) {
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.gradient_ramp);
                } else if (item.getBankId()==2){
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.gradient_ramp_1);
                }else if (item.getBankId()==3){
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.gradient_ramp_2);
                }else if (item.getBankId()==4){
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.gradient_ramp_3);
                }else if (item.getBankId()==5){
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.gradient_ramp_4);
                }else if (item.getBankId()==6){
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.gradient_ramp_5);
                }else if (item.getBankId()==7){
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.gradient_ramp_6);
                }else if (item.getBankId()==8){
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.gradient_ramp_7);
                }else if (item.getBankId()==9){
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.gradient_ramp_8);
                }else if (item.getBankId()==10){
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.gradient_ramp_9);
                }else if (item.getBankId()==11){
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.gradient_ramp_10);
                }else if (item.getBankId()==12){
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.gradient_ramp_11);
                }else if (item.getBankId()==13){
                    holder.getView(R.id.Relative).setBackgroundResource(R.drawable.gradient_ramp_12);
                }
            }
        };
        mListView.setAdapter(mAdapter);
    }
    @Override
    protected void setData() {
        super.setData();
    }
    /**
     * 获取银行卡
     */
    private void getBankCard(final int pageNum) {
        showLoading();
        HashMap<String, String> param = new HashMap<>();
        param.put("token", PreferenceManager.instance().getToken());
        param.put("pageNu", String.valueOf(pageNum));
        HttpHelper.getInstance().post(Url.CARDLIST, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                LogUtils.d( "获取银行卡列表成功：" + response);
                final CardBeanList beanList = JsonUtils.parse(response, CardBeanList.class);
                if (beanList.getCode()==1&&beanList.getDatas().getCardList().size()>0) {
                    if (mList != null) {
                        mList.clear();
                        mList.addAll(beanList.getDatas().getCardList());
                        mAdapter.update(mList);
                        mRelativeLayout.setVisibility(View.GONE);
                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                mArguments.putString("bankImg", beanList.getDatas().getCardList().get(position).getBankImg());
                                mArguments.putInt("cardId", beanList.getDatas().getCardList().get(position).getId());
                                ShowFragmentUtils.showFragment(getActivity(), BindCardDetailFragment.class, FragmentTag.BINDCARDDETAILFRAGMENT, mArguments, true);
                            }
                        });
                    }
                } else if (beanList.getCode()==1&&beanList.getDatas().getCardList().size()==0){
                    mRelativeLayout.setVisibility(View.VISIBLE);
                } else if (beanList.getCode() == 0) {
                    Toast.makeText(getContext(), "获取银行卡失败", Toast.LENGTH_SHORT).show();
                } else {

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
