package com.example.ddm.appui.mine.myhome;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.utils.ShowFragmentUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class BindSuccessFragment extends BaseFragment {
    private TextView mAction;
    public BindSuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bind_success, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        EventBus.getDefault().post(new UpdateCardList("更换默认银行卡"));
        mAction = mFindViewUtils.findViewById(R.id.app_title_action);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mBaseActivity.finish();
            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
    }
}
