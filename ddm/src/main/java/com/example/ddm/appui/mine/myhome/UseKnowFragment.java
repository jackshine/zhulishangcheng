package com.example.ddm.appui.mine.myhome;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.manager.PreferenceManager;

import java.io.InputStream;

/**
 * 用户需知
 */
public class UseKnowFragment extends BaseFragment {
    private WebView mWebView;
    private TextView mBack;
    private TextView mTextView;
    public UseKnowFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_use_know, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mTextView = mFindViewUtils.findViewById(R.id.app_title_text);
        mWebView = mFindViewUtils.findViewById(R.id.webview);
    }
    @Override
    protected void setListener() {
        super.setListener();
        if (PreferenceManager.instance().getUserId().equals("1")) {
            mTextView.setText("用户需知");
            mWebView.loadUrl("http://www.ddmzl.com/m/html/app/user-infomation.html");
        } else {
            mTextView.setText("商户需知");
            mWebView.loadUrl("http://www.ddmzl.com/m/html/app/business-information.html");
        }
mBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        popSelf();
    }
});
    }
}
