package com.example.ddm.appui.mine.myhome;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.PopBaseAdapter;
import com.example.ddm.appui.bean.GetCardBean;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * 添加银行卡
 */
public class BindCardFragment extends BaseFragment {
    private TextView mBack,mTxtSex;
    private RelativeLayout mImageView;//选择银行卡
    private ListView mListView;
    private ImageView mPic;
    private EditText mEditPerson,mEditCardH,mEditCard, mEditCardSure;
    private TextWatcher mWatcher_Person,mWatcher_CardH,mWatcher_Card, mWatcher_CardSure;
    private PopupWindow mPopupWindow;
    private String mSex,mPerson,mCardH,mCard,mCardSure,mPosition;
    private Button mSure,mClear1,mClear2,mClear3,mClear4;
    private List<String> card = new ArrayList<>();//银行的集合
    public BindCardFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bind_card, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mPic = mFindViewUtils.findViewById(R.id.pic);
        mEditPerson = mFindViewUtils.findViewById(R.id.text_card);
        mEditCardH = mFindViewUtils.findViewById(R.id.text_card_hang);
        mEditCard = mFindViewUtils.findViewById(R.id.text_card_num);
        mEditCardSure = mFindViewUtils.findViewById(R.id.text_card_num_sure);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mImageView = mFindViewUtils.findViewById(R.id.Relative);
        mTxtSex = mFindViewUtils.findViewById(R.id.text_sex);
        mSure = mFindViewUtils.findViewById(R.id.registerInBtn);
        mClear1 = mFindViewUtils.findViewById(R.id.but1);
        mClear2 = mFindViewUtils.findViewById(R.id.account_clear);
        mClear3 = mFindViewUtils.findViewById(R.id.btn2);
        mClear4 = mFindViewUtils.findViewById(R.id.btn3);
        mClear1.setOnClickListener(this);
        mClear2.setOnClickListener(this);
        mClear3.setOnClickListener(this);
        mClear4.setOnClickListener(this);
        initList();
        initWatcher();
        mEditPerson.addTextChangedListener(mWatcher_Person);
        mEditCardH.addTextChangedListener(mWatcher_CardH);
        mEditCard.addTextChangedListener(mWatcher_Card);
        mEditCardSure.addTextChangedListener(mWatcher_CardSure);
    }
    private void initList(){
        card.add("工商银行");
        card.add("交通银行");
        card.add("建设银行");
        card.add("中国银行");
        card.add("农业银行");
        card.add("招商银行");
        card.add("中信银行");
        card.add("浦发银行");
        card.add("广发银行");
        card.add("民生银行");
        card.add("兴业银行");
        card.add("邮政储蓄银行");
        card.add("光大银行");
        mSex = mTxtSex.getText().toString().trim();
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
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopWindow(card);
                 mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mTxtSex.setText(card.get(position));
                        mPosition = String.valueOf(position+1);
                        LogUtils.d("dddddddddddddddddsfhhfhfhjfhf"+mPosition);
                        mPopupWindow.dismiss();
                    }
                });
            }
        });
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNull()) {
                    post();//添加银行卡
                }
            }
        });
    }
    /**
     * 初始化poupwindow
     */
    private void initPopWindow(List<String> datas) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
//		// 引入窗口配置文件
        View view = inflater.inflate(R.layout.poup_window_small, null,false);
        mListView = (ListView) view.findViewById(R.id.listview_pop);
        mPopupWindow = new PopupWindow(view, DisplayUtils.dip2px(52f)*2, WindowManager.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.popupAnimation);
        mListView.setAdapter(new PopBaseAdapter(getContext(),datas));
        // 设置此参数获得焦点，否则无法点击
        mPopupWindow.setFocusable(true);
//        Toast.makeText(getContext(),"gg",Toast.LENGTH_SHORT).show();
        mPopupWindow.showAsDropDown(mPic, 0, 25);
    }
    //判断账号
    private boolean isNull(){
        mPerson = mEditPerson.getText().toString().trim();
        mCardH = mEditCardH.getText().toString().trim();
        mCard = mEditCard.getText().toString().trim();
        mCardSure = mEditCardSure.getText().toString().trim();
        if (TextUtils.isEmpty(mPerson)) {
            Toast.makeText(getActivity(), "请输入真实姓名", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mCardH)) {
            Toast.makeText(getActivity(), "请输入开户行", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mCard)) {
            Toast.makeText(getActivity(), "请输入卡号", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mCardSure.equals(mCard)) {
            Toast.makeText(getActivity(), "两次输入卡号不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    //添加
    private void post(){
        HashMap<String, String> parse = new HashMap<>();
        parse.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        parse.put(IAppKey.ACCOUNTNAME, mPerson);
        parse.put(IAppKey.CARDNUM, mCard);
        parse.put(IAppKey.KAIHUHANG, mCardH);
        parse.put(IAppKey.BANKID, mPosition);
        LogUtils.d("上传的"+mPosition);
        HttpHelper.getInstance().post(Url.Add_Card, parse, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                if (bean.getCode()==1) {
                    PreferenceManager.instance().saveAccountname(mPerson);
                    PreferenceManager.instance().saveCardnum(mCard);
                    PreferenceManager.instance().saveKaihuhang(mCardH);
                    ShowFragmentUtils.showFragment(getActivity(),BindSuccessFragment.class, FragmentTag.BINDSUCCESSFRAGMENT,null,true);
                } else if (bean.getCode()==0) {
                    LogUtils.d("dafjfajkjkk="+bean.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
    //得到请求
    private void get(){
        HashMap<String, String> parse = new HashMap<>();
        parse.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        parse.put(IAppKey.PAGE, "1");
        HttpHelper.getInstance().post(Url.CARDLIST, parse, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final GetCardBean cardbean = JsonUtils.parse(response, GetCardBean.class);
                if (cardbean.getCode()==1) {
                    if (cardbean.getDatas().isState()) {
                        PreferenceManager.instance().saveAccountname(mPerson);
                        PreferenceManager.instance().saveCardnum(mCard);
                        PreferenceManager.instance().saveKaihuhang(mCardH);
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
    //修改请求
    private void Amend(){
        HashMap<String, String> parse = new HashMap<>();
        parse.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        parse.put(IAppKey.ACCOUNTNAME, mPerson);
        parse.put(IAppKey.CARDNUM, mCard);
        parse.put(IAppKey.KAIHUHANG, mCardH);
        parse.put(IAppKey.BANKID, mPosition);
        HttpHelper.getInstance().post(Url.Compile_Card, parse, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                if (bean.getCode()==1) {
                    PreferenceManager.instance().saveAccountname(mPerson);
                    PreferenceManager.instance().saveCardnum(mCard);
                    PreferenceManager.instance().saveKaihuhang(mCardH);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
    /*清空数据*/
    private void initWatcher() {
        mWatcher_Person = new TextWatcher() {
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
                    mClear1.setVisibility(View.VISIBLE);
                } else {
                    mClear1.setVisibility(View.INVISIBLE);
                }
            }
        };
        mWatcher_CardH = new TextWatcher() {
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
                    mClear2.setVisibility(View.VISIBLE);
                } else {
                    mClear2.setVisibility(View.INVISIBLE);
                }
            }
        };
        mWatcher_Card = new TextWatcher() {
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
                    mClear3.setVisibility(View.VISIBLE);
                } else {
                    mClear3.setVisibility(View.INVISIBLE);
                }
            }
        };
        mWatcher_CardSure = new TextWatcher() {
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
                    mClear4.setVisibility(View.VISIBLE);
                } else {
                    mClear4.setVisibility(View.INVISIBLE);
                }
            }
        };
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.but1:
                mEditPerson.setText("");
                break;
            case R.id.account_clear:
                mEditCardH.setText("");
                break;
            case R.id.btn2:
                mEditCard.setText("");
                break;
            case R.id.btn3:
                mEditCardSure.setText("");
                break;
        }
    }
}
