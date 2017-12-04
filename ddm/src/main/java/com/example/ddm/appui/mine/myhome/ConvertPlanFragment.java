package com.example.ddm.appui.mine.myhome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.ExchangeBean;
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
 * 兑换进度
 */
public class ConvertPlanFragment extends BaseFragment {
    private TextView mBack;
    private TextView mTextView,mTextView1,mTextView2,mTextView3,mTextView4,mTextView5,mTextView6,mTextView7, mTextView8,mSuccess;
    private ImageView mImageView;
    public ConvertPlanFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_convert_plan, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mImageView = mFindViewUtils.findViewById(R.id.pic_zfb);
        mSuccess = mFindViewUtils.findViewById(R.id.success);
        mTextView = mFindViewUtils.findViewById(R.id.money);
        mTextView1 = mFindViewUtils.findViewById(R.id.check);
        mTextView2 = mFindViewUtils.findViewById(R.id.money_free);
        mTextView3 = mFindViewUtils.findViewById(R.id.time1);
        mTextView4 = mFindViewUtils.findViewById(R.id.time2);
        mTextView5 = mFindViewUtils.findViewById(R.id.time3);
        mTextView6 = mFindViewUtils.findViewById(R.id.bank);
        mTextView7 = mFindViewUtils.findViewById(R.id.card);
        mTextView8 = mFindViewUtils.findViewById(R.id.text_sb);

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
        getList(1);
    }
    private void getList(int page) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.PAGE, String.valueOf(page));
        HttpHelper.getInstance().post(Url.EXCHANGEBEAN, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final ExchangeBean bean = JsonUtils.parse(response, ExchangeBean.class);
                LogUtils.d(response);
                if (bean.getCode()==1) {
                    int i= Integer.valueOf(PreferenceManager.instance().getID());
                    LogUtils.d("ffjffkfk"+bean.getDatas().get(i).getStateValue());
                    if (bean.getDatas().get(i).getStateValue()==3) {
                        mTextView.setText(bean.getDatas().get(i).getMoney());
                        mTextView1.setText(bean.getDatas().get(i).getState());
                        mTextView1.setTextColor(0xFF49C9BB);
                        mSuccess.setTextColor(0xFF49C9BB);
                        mTextView8.setBackgroundColor(0xFF49C9BB);
                        mImageView.setImageResource(R.mipmap.dhjdcg);
                        mTextView2.setText(bean.getDatas().get(i).getFactorage());
                        mTextView3.setText(bean.getDatas().get(i).getCreateTime());
                        mTextView4.setText(bean.getDatas().get(i).getCreateTime());
                        mTextView5.setText(bean.getDatas().get(i).getHandTime());
                        mTextView6.setText(bean.getDatas().get(i).getBankName()+"("+bean.getDatas().get(i).getCardNum()+")"+bean.getDatas().get(i).getAccountName());
                        mTextView7.setText(bean.getDatas().get(i).getCode());
                    } else if (bean.getDatas().get(i).getStateValue()==2||bean.getDatas().get(i).getStateValue()==1) {
                        mTextView.setText(bean.getDatas().get(i).getMoney());
                        mTextView1.setText(bean.getDatas().get(i).getState());
                        mTextView1.setTextColor(0xFFFF296C);
                        mTextView2.setText(bean.getDatas().get(i).getFactorage());
                        mTextView3.setText(bean.getDatas().get(i).getCreateTime());
                        mTextView4.setText(bean.getDatas().get(i).getCreateTime());
                        mTextView5.setText("");
                        mTextView6.setText(bean.getDatas().get(i).getBankName()+"("+bean.getDatas().get(i).getCardNum()+")"+bean.getDatas().get(i).getAccountName());
                        mTextView7.setText(bean.getDatas().get(i).getCode());
                    }
                } else if (bean.getCode() == 0) {
                    Toast.makeText(getContext(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
