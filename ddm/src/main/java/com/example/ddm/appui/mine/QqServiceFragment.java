package com.example.ddm.appui.mine;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
/**
 * A simple {@link Fragment} subclass.
 */
public class QqServiceFragment extends BaseFragment {
    private WebView mWebView;
    public QqServiceFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qq2, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mWebView = mFindViewUtils.findViewById(R.id.webview);
    }
    @Override
    protected void setListener(){
        super.setListener();
        mWebView.loadUrl("http://www.ddmzl.com/m/html/system.html");
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
// TODO Auto-generated method stub
// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                System.out.println("-------------"+url);
                return true;
            }
        });
    }
}
