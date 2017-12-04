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
 * 扫码支付详情
 */
public class SaoYiSaoFragment extends BaseFragment {
    private TextView mComplete;
    private TextView mMoney, mName;
    public SaoYiSaoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sao_yi_sao, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mMoney = mFindViewUtils.findViewById(R.id.money);
        mName = mFindViewUtils.findViewById(R.id.name);
        mComplete = mFindViewUtils.findViewById(R.id.app_title_action);
        mComplete.setTextColor(0xFF49C9BB);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.finish();
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        if (mArguments.getString("money").indexOf(".") == -1) {
            mMoney.setText(mArguments.getString("money") + ".00");
        } else {
            mMoney.setText(mArguments.getString("money"));
        }
        mName.setText(mArguments.getString("name"));
    }

}
