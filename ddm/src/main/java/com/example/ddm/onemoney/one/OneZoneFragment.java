package com.example.ddm.onemoney.one;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 一元专区
 */
public class OneZoneFragment extends BaseFragment {
    private GridView mGridView;
//    private CommonAdapter<> mAdapter;
//    private List<> mList = new ArrayList();
    public OneZoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one_zone, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mGridView = mFindViewUtils.findViewById(R.id.grid_view);
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    protected void setData() {
        super.setData();
    }
}
