package com.example.ddm.appui.mine.myhome;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.AccountBean;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import java.util.HashMap;
/**
 * 账号概况
 */
public class BillSummaryFragment extends BaseFragment {
    private TextView mBack,mTextView1,mTextView2,mTextView3,mTextView4,mTextView5,mTextView6;
    private LinearLayout mLayout,mLayout1;
    public BillSummaryFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill_summary, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mLayout1 = mFindViewUtils.findViewById(R.id.layout3);
        mLayout = mFindViewUtils.findViewById(R.id.dingdang);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mTextView1 = mFindViewUtils.findViewById(R.id.txt_1);
        mTextView2 = mFindViewUtils.findViewById(R.id.txt_2);
        mTextView3 = mFindViewUtils.findViewById(R.id.txt_3);
        mTextView4 = mFindViewUtils.findViewById(R.id.txt_4);
        mTextView5 = mFindViewUtils.findViewById(R.id.txt_5);
        mTextView6 = mFindViewUtils.findViewById(R.id.txt_6);

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
        post();
    }

    private void post(){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.ACCOUNTBEAN, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final AccountBean bean = JsonUtils.parse(response, AccountBean.class);
                if (bean.getCode()==1) {
                    mTextView1.setText(bean.getDatas().getOrder() + "元");
                    mTextView2.setText(bean.getDatas().getAccountMoney() + " 积分");
                    mTextView5.setText(bean.getDatas().getCdd() + "个");
                    mTextView6.setText(bean.getDatas().getTiXianMoney() + "元");
                    if (bean.getDatas().getUserTypeValue() == 2) {
                        mTextView3.setText(bean.getDatas().getShopOrder() + "元");
                        mTextView4.setText(bean.getDatas().getSdd() + "个");
                        mLayout.setVisibility(View.GONE);
                    } else if (bean.getDatas().getUserTypeValue()==1) {
                        mLayout1.setVisibility(View.GONE);
                        mLayout.setVisibility(View.GONE);
                    } else if (bean.getDatas().getUserTypeValue()==5) {
                        mTextView3.setText(bean.getDatas().getShopOrder() + "元");
                        mLayout.setVisibility(View.GONE);}
                } else if (bean.getCode()==0) {
                    Toast.makeText(getContext(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                } else if (bean.getCode()==-1) {
                    Toast.makeText(mBaseActivity, "请登录", Toast.LENGTH_SHORT).show();
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
