package com.example.ddm.appui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * 新人注册红包详情界面
 */
public class HongBaoDetailFragment extends BaseFragment {
    private TextView mMoney,mSure,mBack;
    public HongBaoDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hong_bao_detail, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mBack = mFindViewUtils.findViewById(R.id.back);
        mMoney = mFindViewUtils.findViewById(R.id.num);
        mSure = mFindViewUtils.findViewById(R.id.sure);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
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
        mMoney.setText(mArguments.getString("hongbaonum"));
    }
}
