package com.example.ddm.appui.mine;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.ddm.LoginActivity;
import com.example.ddm.ProvinceActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.adapter.recycle.CommenRecycleAdapter;
import com.example.ddm.appui.adapter.recycle.SuperViewHolder;
import com.example.ddm.appui.bean.GetZongJiangRecord;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.eventbus.AddressTwo;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 没领奖
 */
public class NoneAwardFragment extends BaseFragment {
    private LRecyclerView mLRecyclerView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int mPage=1;
    private LinearLayout mSubmit;
    private PopupWindow mPopupWindow;
    private EditText chose_address;
    private int mId;
    private CommenRecycleAdapter<GetZongJiangRecord.DatasBean> mAdapter;
    private List<GetZongJiangRecord.DatasBean> mList = new ArrayList<>();
    public NoneAwardFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_none_award, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mLRecyclerView = mFindViewUtils.findViewById(R.id.lrv_public_recyclerview);
        mAdapter = new CommenRecycleAdapter<GetZongJiangRecord.DatasBean>(getContext(), R.layout.award_item) {
            @Override
            public void setViewData(SuperViewHolder holder, GetZongJiangRecord.DatasBean item, int position) {
                ImageView view = holder.getView(R.id.glide_icon);
                Glide.with(getContext()).load(item.getImg()).placeholder(R.drawable.ic_launcher).crossFade().into(view);
                holder.setText(item.getJiangPinName(),R.id.goods_name).setText("中奖编码"+item.getCode(),R.id.award_code)
                        .setText("中奖时间"+item.getCreateTime(),R.id.award_time).
                        setText(item.getJiangPinLevel(),R.id.award_grade).setText("奖品编码："+item.getJiangPinCode(),R.id.award_num);
            }
            @Override
            public void setListener(SuperViewHolder holder, View view) {
                holder.setOnClickListener(R.id.get_award);
            }
            @Override
            public void onClickBack(SuperViewHolder holder, int position, View view) {
                StringUtils.showToast(getActivity(),"立即领取");
                hongPopWindow(mList.get(position).getId());
            }
        };
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.a_5dp)
                .setColorResource(R.color.gray_background)
                .build();
        mLRecyclerView.addItemDecoration(divider);
        mLRecyclerView.setAdapter(mLRecyclerViewAdapter);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();
                mAdapter.clear();
                getAward(mPage);

            }
        });
        mLRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPage++;
                getAward(mPage);
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        getAward(mPage);
    }
    /**
     * @param page
     * 未领奖
     */
    private void getAward(int page){
        showLoading();
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
    hashMap.put("page", String.valueOf(page));
    hashMap.put("isobtain", "false");
    HttpHelper.getInstance().post(Url.getZongJiangRecord, hashMap, new RawResponseHandler() {
        @Override
        public void onSuccess(int statusCode, String response) {
            hiddenLoading();
            GetZongJiangRecord getZongJiangRecord = JsonUtils.parse(response, GetZongJiangRecord.class);
            if (getZongJiangRecord.getCode()==1) {
                mList.addAll(getZongJiangRecord.getDatas());
               mAdapter.setDataList(mList);
                mLRecyclerView.refreshComplete(1000);
            } else if (getZongJiangRecord.getCode()==0) {
                StringUtils.showToast(getActivity(),getZongJiangRecord.getMsg());
            } else if (getZongJiangRecord.getCode()==-1) {
                StringUtils.showToast(getActivity(),getZongJiangRecord.getMsg());
                PreferenceManager.instance().removeToken();
                mBaseActivity.showActivity(LoginActivity.class, null);
            }
        }
        @Override
        public void onFailure(int statusCode, String error_msg) {
            mLRecyclerView.refreshComplete(1000);
            hiddenLoading();
            StringUtils.showToast(getActivity(),error_msg);
        }
    });
}
    @Subscribe
    public void getAddress(AddressTwo addresstwo) {
        LogUtils.d("收到地址::::::");
        chose_address.setText(addresstwo.getProvince()+addresstwo.getCity()+addresstwo.getTown());//接受地址消息
    }
    private void hongPopWindow(final int id){
        LayoutInflater inflater = LayoutInflater.from(getContext());
//		// 引入窗口配置文件
        View view = inflater.inflate(R.layout.poup_window_award, null,false);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.Relative);
        mSubmit = (LinearLayout) view.findViewById(R.id.submit);
         chose_address = (EditText) view.findViewById(R.id.chose_address);
        final EditText detail = (EditText) view.findViewById(R.id.detail);
        detail.setText(PreferenceManager.instance().getDetail());
        chose_address.setText(PreferenceManager.instance().getAddress());
        mPopupWindow = new PopupWindow(view, DisplayUtils.getWidthPx()/5*4, DisplayUtils.getHeightPx()/4*3, true);
        // 需要设置一下此参数，点击外边可消失
        setBackgroundAlpha(0.5f);
        mPopupWindow.setFocusable(false);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setAnimationStyle(R.style.popupAnimation);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        // 设置此参数获得焦点，否则无法点击
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        chose_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.showActivity(ProvinceActivity.class,null);
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
                hashMap.put("id", String.valueOf(id));
                hashMap.put("location",chose_address.getText().toString()+detail.getText().toString());
                HttpHelper.getInstance().post(Url.userGetJiangPin, hashMap, new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                        if (bean.getCode()==1) {
                            PreferenceManager.instance().saveAddress(chose_address.getText().toString());
                            PreferenceManager.instance().saveDetail(detail.getText().toString());
                            EventBus.getDefault().post(new LoginSuccess());
                            mPopupWindow.dismiss();
                        } else if (bean.getCode()==0) {
                            StringUtils.showToast(getActivity(),bean.getMsg());
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                    }
                });
            }
        });
    }
}
