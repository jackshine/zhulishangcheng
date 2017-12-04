package com.example.ddm.appui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.manager.PreferenceManager;

/**
 * A simple {@link Fragment} subclass.
 * 收款详情
 */
public class RecordDetailFragment extends BaseFragment {
    private TextView mName,mMoney,mState,mUser,mTime,mOrder,mBack;


    public RecordDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record_detail, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mName = mFindViewUtils.findViewById(R.id.user_name);
        mMoney = mFindViewUtils.findViewById(R.id.money);
        mState = mFindViewUtils.findViewById(R.id.state);
        mUser = mFindViewUtils.findViewById(R.id.news);
        mTime = mFindViewUtils.findViewById(R.id.time);
        mOrder = mFindViewUtils.findViewById(R.id.order);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
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
        if (PreferenceManager.instance().getPhoneNum().equals(mArguments.getString("phone"))) {
            mName.setText("向“"+mArguments.getString("userNmae")+"”转账");
            mMoney.setText("-"+mArguments.getString("money"));
            mUser.setText(mArguments.getString("userNmae"));
            mTime.setText(mArguments.getString("creatTime"));
            mOrder.setText(mArguments.getString("code"));
            mState.setText(mArguments.getString("state"));
        } else {
            mName.setText("收到“"+mArguments.getString("userNmae")+"”的转账");
            mMoney.setText("+"+mArguments.getString("money"));
            mUser.setText(mArguments.getString("userNmae"));
            mTime.setText(mArguments.getString("creatTime"));
            mOrder.setText(mArguments.getString("code"));
            mState.setText(mArguments.getString("state"));
        }


    }
}
