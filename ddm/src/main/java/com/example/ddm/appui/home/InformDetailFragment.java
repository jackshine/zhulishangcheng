package com.example.ddm.appui.home;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.InformDetailBean;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import java.util.HashMap;
/**
 * A simple {@link Fragment} subclass.
 * 通知详情
 */
public class InformDetailFragment extends BaseFragment {
private TextView mTitle,mTime,mBack;
    private WebView mWebView;
    public InformDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inform_detail, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTitle = mFindViewUtils.findViewById(R.id.title);
        mTime = mFindViewUtils.findViewById(R.id.time);
        mWebView = mFindViewUtils.findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        post();
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
    }
    private void post(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.ID, PreferenceManager.instance().getNewsId());
        HttpHelper.getInstance().post(Url.INFORM_DETAIL, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final InformDetailBean bean = JsonUtils.parse(response, InformDetailBean.class);
                if (bean.getCode()==1) {
                    mTitle.setText(bean.getDatas().getTitle());
                    mTime.setText(bean.getDatas().getCreateTime());
                    mWebView.loadDataWithBaseURL(null,bean.getDatas().getContent(),"text/html","utf-8", null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
}
