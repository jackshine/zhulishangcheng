package com.example.ddm.appui.mine.share;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.GetDetailCommission;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * 分享流水详情
 */
public class ShareDetailFragment extends BaseFragment {
private TextView mTextView1,mTextView2,mTextView3,mTextView4,mTextView5, mTextView6;
    public ShareDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_detail, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
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
    }
    @Override
    protected void setData() {
        super.setData();
        getDetailCommission(mArguments.getString("code"));
    }
    private void getDetailCommission(String code){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("code", code);
        HttpHelper.getInstance().post(Url.getDetailCommission, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                LogUtils.d("集合"+response);
                GetDetailCommission getDetailCommission = JsonUtils.parse(response, GetDetailCommission.class);
                if (getDetailCommission.getCode()==1) {
                    if (getDetailCommission.getDatas().size()>2) {
                        mTextView1.setText(getDetailCommission.getDatas().get(0).getName());
                        mTextView2.setText(getDetailCommission.getDatas().get(0).getMoney());
                        mTextView3.setText(getDetailCommission.getDatas().get(1).getName());
                        mTextView4.setText(getDetailCommission.getDatas().get(1).getMoney());
                        mTextView5.setText(getDetailCommission.getDatas().get(2).getName());
                        mTextView6.setText(getDetailCommission.getDatas().get(2).getMoney());
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                StringUtils.showToast(getActivity(),error_msg);
            }
        });
    }
}
