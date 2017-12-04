package com.example.ddm.appui.mine.Charge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.eventbus.Pay;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PaySuccessFragment extends BaseFragment {
    private TextView  mSuccess;
    public PaySuccessFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pay_success, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        EventBus.getDefault().post(new Pay());
        mSuccess = mFindViewUtils.findViewById(R.id.app_title_action);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
    }
}
