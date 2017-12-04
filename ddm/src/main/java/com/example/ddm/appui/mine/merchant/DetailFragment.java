package com.example.ddm.appui.mine.merchant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.OrderBean;
import com.example.ddm.appui.bean.WorkOrderBean;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * 子做单详情
 */
public class DetailFragment extends BaseFragment {
    private TextView mName,mOrder,mShopping,mNum,mPrice,mAll, mIntegral,mFail,mRemark,mBack;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mName = mFindViewUtils.findViewById(R.id.name);
        mOrder = mFindViewUtils.findViewById(R.id.order);
        mShopping = mFindViewUtils.findViewById(R.id.shop_name);
        mNum = mFindViewUtils.findViewById(R.id.num);
        mPrice = mFindViewUtils.findViewById(R.id.money);
        mAll = mFindViewUtils.findViewById(R.id.all);
        mIntegral = mFindViewUtils.findViewById(R.id.integral);
        mFail = mFindViewUtils.findViewById(R.id.fail);
        mRemark = mFindViewUtils.findViewById(R.id.remark);
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
    private void post(){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.STATEVALUE, "1");
        HttpHelper.getInstance().post(Url.WORK_ORDER, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final WorkOrderBean bean = JsonUtils.parse(response, WorkOrderBean.class);
                LogUtils.d("ddddd"+response);
                if (bean.getCode()==1) {
                            int i = Integer.parseInt(PreferenceManager.instance().getOrderId());
                            mName.setText(bean.getDatas().get(i).getShopName());
                            mOrder.setText(bean.getDatas().get(i).getCode());
                            mShopping.setText(bean.getDatas().get(i).getGoodsName());
                            mNum.setText(bean.getDatas().get(i).getGoodsNum()+"个");
                            mPrice.setText(bean.getDatas().get(i).getGoodOnePrice());
                            mAll.setText(bean.getDatas().get(i).getShopYingYeMoney());
                            mIntegral.setText(bean.getDatas().get(i).getShopPayMoney());
                            mFail.setText(bean.getDatas().get(i).getIllustrate());
                            mRemark.setText(bean.getDatas().get(i).getMemo());
                } else if (bean.getCode() == 0) {
                    Toast.makeText(mBaseActivity, "请求失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mBaseActivity, "请登录", Toast.LENGTH_SHORT).show();
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
            }
        });
    }
}
