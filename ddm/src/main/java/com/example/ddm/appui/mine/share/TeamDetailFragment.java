package com.example.ddm.appui.mine.share;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
/**
 * A simple {@link Fragment} subclass.
 * 团队成员
 */
public class TeamDetailFragment extends BaseFragment {
    public TeamDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_detail, container, false);
    }

}
