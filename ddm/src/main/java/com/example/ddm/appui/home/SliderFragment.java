package com.example.ddm.appui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * 轮播跳转界面
 */
public class SliderFragment extends BaseFragment {
    private WebView mWebView;
    private TextView mBack;
    private TextView mTitle;
    public SliderFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slider, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mTitle = mFindViewUtils.findViewById(R.id.app_title_text);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mWebView = mFindViewUtils.findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
    @Override
    protected void setListener() {
        super.setListener();
    }
    @Override
    protected void setData() {
        super.setData();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
        mWebView.loadUrl(mArguments.getString("banurl"));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
            mTitle.setText(view.getTitle());
                mTitle.setTextColor(0xFF000000);
            }
        });
    }
}
