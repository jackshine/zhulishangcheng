package com.example.ddm.appui.mine;
import android.os.Bundle;
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
import com.example.ddm.appui.bean.eventbus.Update;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
/**
 * 身份证号修改界面
 */
public class CardFragment extends BaseFragment {
    private TextView mBack,mKeep;
    private EditText mEditText;//身份证输入框
    private Button mClear;
    private String mCard;
    private TextWatcher mAccountWatcher;//账号输入框监听
    public CardFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false);
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
            mEditText.setText(PreferenceManager.instance().getIdentity());
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
                if (isCardNumber()) {//验证身份证是否合法
                    PreferenceManager.instance().saveIdentity(mCard);
                    EventBus.getDefault().post(new Card(mCard));
                    postCard();
                    popSelf();
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
    //验证身份证号的合法性
    private boolean isCardNumber(){
        mCard = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(mCard)) {
            Toast.makeText(getContext(), "请输入身份证号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.personIdValidation(mCard)) {
            return true;
        }else {
            Toast.makeText(getContext(), "身份证号不合法，请重新输入", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    //提交身份证到服务器
    private void postCard(){
        showLoading("正在提交");
        HashMap<String, String> parse = new HashMap<>();
        parse.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        parse.put(IAppKey.IDENTITY, mCard);
        HttpHelper.getInstance().post(Url.Alter_Status, parse, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                if (bean.getCode()==1) {
                    hiddenLoading();
                } else if (bean.getCode() == 0) {
                    StringUtils.showToast(getActivity(),bean.getMsg());
                } else {
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
            }
        });
    }
}
