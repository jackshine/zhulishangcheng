package com.example.ddm.appui.summary.detail;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.StoreDetail;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * 商品图文详情
 */
public class GoodsPictureFragment extends BaseFragment {
    private WebView mWebView;
    public GoodsPictureFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goods_picture, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mWebView = mFindViewUtils.findViewById(R.id.webview);
    }

    @Override
    protected void setListener() {
        super.setListener();
    }
    @Override
    protected void setData() {
        super.setData();
        getDetail("1");
    }
    private void getDetail(String id){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);
        HttpHelper.getInstance().post(Url.GOODSC, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                StoreDetail storeDetail = JsonUtils.parse(response, StoreDetail.class);
                if (storeDetail.getCode() == 1) {
//                    mWebView.loadUrl(storeDetail.getDatas().getDetails());
                    Document doc= Jsoup.parse( storeDetail.getDatas().getDetails());
                    Elements elements=doc.getElementsByTag("img");
                    for (Element element : elements) {
                        element.attr("style","width:100%").attr("height","auto").attr("alt","img");
                    }
                    LogUtils.d("aaaaaaaaaaaaa"+doc.toString());
                    mWebView.loadDataWithBaseURL(null,doc.toString(),"text/html","utf-8", null);
//                    mWebView.loadUrl(doc.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                StringUtils.showToast(getActivity(),error_msg);
            }
        });
    }
}
