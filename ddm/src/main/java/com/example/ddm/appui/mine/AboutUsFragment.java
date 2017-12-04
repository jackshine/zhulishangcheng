package com.example.ddm.appui.mine;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.utils.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 * 关于我们
 */
public class AboutUsFragment extends BaseFragment {
    private TextView mBack;
    private String mVersion;
    private TextView mTextVersion;
    public AboutUsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        getLocalVersion(getContext());
        mTextVersion = mFindViewUtils.findViewById(R.id.Version);
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
        mTextVersion.setText("版本号V"+mVersion);
    }
    private void getLocalVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            mVersion = "0.0";
        }
        LogUtils.d("版本号"+info.versionName);
        mVersion = info.versionName;
    }
}
