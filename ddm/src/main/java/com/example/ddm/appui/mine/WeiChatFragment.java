package com.example.ddm.appui.mine;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.eventbus.Card;
import com.example.ddm.appui.bean.eventbus.WeChat;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
/**
 * 修改微信界面
 */
public class WeiChatFragment extends BaseFragment {
    private TextView mBack,mKeep;
    private EditText mEditText;
    private Button mClear;
    private String mWeChat;
    private TextWatcher mAccountWatcher;//账号输入框监听
    public WeiChatFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wei_chat, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mKeep = mFindViewUtils.findViewById(R.id.app_title_action);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mEditText = mFindViewUtils.findViewById(R.id.text_card);
        mClear = mFindViewUtils.findViewById(R.id.account_clear);
        mClear.setOnClickListener(this);
        initWatcher();
        mEditText.addTextChangedListener(mAccountWatcher);
        mEditText.setText(PreferenceManager.instance().getWeixin());
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
        mKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNull()) {
                    PreferenceManager.instance().saveWeixin(mWeChat);
                    EventBus.getDefault().post(new WeChat(mWeChat));
                    WeChat();
                }
            }
        });
    }
    /*清空数据*/
    private void initWatcher() {
        mAccountWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {//设置删除键
//                mEditTextPwd.setText("");
                if (s.toString().length() > 0) {//当输入框有内容时，显示删除按钮
                    mClear.setVisibility(View.VISIBLE);
                } else {
                    mClear.setVisibility(View.INVISIBLE);
                }
            }
        };
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.account_clear:
                mEditText.setText("");
                break;
        }
    }
    private boolean isNull(){
        mWeChat = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(mWeChat)) {
            Toast.makeText(getContext(), "请输入微信号", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    //修改微信号
    private void WeChat(){
        showLoading("正在提交");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.WEIXIN, mWeChat);
        HttpHelper.getInstance().post(Url.Alter_WeChat, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                LogUtils.d("ssssssss"+response);
                final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                if (bean.getCode() == 1) {
                  popSelf();
                } else if (bean.getCode() == 0) {

                    StringUtils.showToast(getActivity(), bean.getMsg());
                } else {
                    StringUtils.showToast(getActivity(),bean.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
