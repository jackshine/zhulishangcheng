package com.example.ddm.onemoney.three;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * 一元夺宝3
 */
public class ThreePersonFragment extends BaseFragment {
    public ThreePersonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three_person, container, false);
    }

}
