package com.example.ddm.appui.mine.myhome;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.AccountBean;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.TiXianBean;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.bean.eventbus.Update;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.bean.picture.CardBeanList;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.MD5;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.view.ListViewForScrollView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 积分兑换
 */
public class CreditsExchangeFragment extends BaseFragment {
    private TextView mBack,mAction,mPay;
    private ImageView mCircleImageView;
    private TextView mMyCard,mCardNum, mName;
    private TextView mTextView1;
    private EditText mEditText,mEditPwd;//金钱输入框
    private TextWatcher mWatcher;//监听金钱
    private String mNum,mAddress,mMoney;
    private int mBankId,mId;
    private ListViewForScrollView mListViewForScrollView;
    private Button mSure;
    private String mFee;
    private List<String> mList = new ArrayList<>();
    private CommonAdapter<String> mAdapter;
    private TextView mTextView;
    private RelativeLayout mRelativeLayout;
    public CreditsExchangeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credits_exchange, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mTextView = mFindViewUtils.findViewById(R.id.txt);
        mEditPwd = mFindViewUtils.findViewById(R.id.text_pwd);
        mTextView1 = mFindViewUtils.findViewById(R.id.txt_fw);
        mSure = mFindViewUtils.findViewById(R.id.registerInBtn);
        mCircleImageView = mFindViewUtils.findViewById(R.id.pic_yhk);
        mMyCard = mFindViewUtils.findViewById(R.id.txt_yhk);
        mCardNum = mFindViewUtils.findViewById(R.id.my_num);
        mListViewForScrollView = mFindViewUtils.findViewById(R.id.list_view);
        mName = mFindViewUtils.findViewById(R.id.name);
        mRelativeLayout = mFindViewUtils.findViewById(R.id.Relative1);
        mPay = mFindViewUtils.findViewById(R.id.num);
        mEditText = mFindViewUtils.findViewById(R.id.money);
        mAction = mFindViewUtils.findViewById(R.id.app_title_action);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        initWatcher();
        mEditText.addTextChangedListener(mWatcher);
    }
    @Subscribe
    public void getCardList(UpdateCardList card) {
        LogUtils.d("收到身份证::::::");
        if (card.getMsg().equals("更换默认银行卡")) {
            LogUtils.d("qqqqqqqqqqqqqqqqqqqq");
            getCard(1);
            LogUtils.d("cccccccccccccccccccccccc");
        }
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
        mAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),ConvertFragment.class, FragmentTag.CONVERTFRAGMENT,null,true);
            }
        });
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),SelectCardFragment.class,FragmentTag.SELECTCARDFRAGMENT,null,true);
            }
        });
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckCard(1);
//                MD5.getMD5(new StringBuffer(mEditPwd.getText().toString().trim()).append(PreferenceManager.instance().getKey()).toString())

            }
        });
        mListViewForScrollView.setFocusable(false);
        mAdapter = new CommonAdapter<String>(getContext(),null,R.layout.item_search) {
            @Override
            public void setViewData(ViewHolder holder, String item, int position) {

                holder.getView(R.id.pic).setVisibility(View.GONE);
                holder.setText(mList.get(position), R.id.tv_search_content);
            }
        };
        mListViewForScrollView.setAdapter(mAdapter);
    }
    @Override
    protected void setData() {
        super.setData();
        getDetail();
        post();
        getCard(1);
    }
    private void getDetail(){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        HttpHelper.getInstance().post(Url.TIXIAN, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final TiXianBean bean = JsonUtils.parse(response, TiXianBean.class);
                if (bean.getCode()==1) {
                    mTextView.setText("兑换金额（收取"+Double.valueOf(bean.getServiceCharge())*100+"%服务费)");
                    mFee = bean.getServiceCharge();
                    mList.addAll(bean.getRemark());
                    mAdapter.update(mList);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
            }
        });
    }

    private void post(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.ACCOUNTBEAN, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final AccountBean bean = JsonUtils.parse(response, AccountBean.class);
                if (bean.getCode()==1) {
                   mPay.setText(bean.getDatas().getAccountMoney());
                    mMoney = bean.getDatas().getAccountMoney();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
    //获取用户银行卡
    private void getCard(final int pageNum){
        showLoading("正在提交");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("pageNu", String.valueOf(pageNum));
        HttpHelper.getInstance().post(Url.CARDLIST, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                LogUtils.d("ssssssss"+response);
                hiddenLoading();
                final CardBeanList beanList = JsonUtils.parse(response, CardBeanList.class);
                final CardBeanList.DatasBean bean = JsonUtils.parse(response, CardBeanList.DatasBean.class);
                if (beanList.getCode() == 1) {
                    LogUtils.d("aaaaaaaaaa"+beanList.getDatas().getRecordsTotal());
                    if (beanList.getDatas().getRecordsTotal() == 0) {
                        final Dialog dialog = new Dialog(getContext(), R.style.translate_dialog);
                        View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_change_city, null);
                        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                        TextView tvContent = (TextView) contentView.findViewById(R.id.tv_dialog_content);
                        Button btnCancel = (Button) contentView.findViewById(R.id.btn_dialog_cancel);
                        Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
                        tvTitle.setText("提示");
                        tvContent.setText("检测到您当前并没有银行卡，是否要添加银行卡？");
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                popSelf();
                            }
                        });
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
//                                ShowFragmentUtils.showFragment(getActivity(),BindCardFragment.class,FragmentTag.BINDCARDFRAGMENT,null,true);
                                mBaseActivity.showActivity(BindCardActivity.class, null);
                            }
                        });

                        dialog.setContentView(contentView);

                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.width = DisplayUtils.getWidthPx() / 4 * 3;
                        window.setAttributes(params);
                        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0)
                                {
                                    dialog.dismiss();
                                   popSelf();
                                }
                                return false;

                            }
                        });
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    } else if (beanList.getDatas().getRecordsTotal()>0) {
                        for (int i = 0; i <beanList.getDatas().getCardList().size() ; i++) {
                            if (beanList.getDatas().getCardList().get(i).isIsDefault()) {
                                Glide.with(getContext()).load(beanList.getDatas().getCardList().get(i).getBankImg()).placeholder(R.drawable.ic_launcher).crossFade().into(mCircleImageView);
                                mMyCard.setText(beanList.getDatas().getCardList().get(i).getBank());
                                mCardNum.setText(beanList.getDatas().getCardList().get(i).getCardNumShort());
                                mName.setText(beanList.getDatas().getCardList().get(i).getAccountName());
                                mNum = beanList.getDatas().getCardList().get(i).getCardNum();
                                mBankId = beanList.getDatas().getCardList().get(i).getBankId();
                                mId = beanList.getDatas().getCardList().get(i).getId();
                                mAddress = beanList.getDatas().getCardList().get(i).getKaihuhang();
                            }
                        }
                    }
                } else if (beanList.getCode() == 0) {
                    StringUtils.showToast(getActivity(), beanList.getMsg());
                } else {
                    StringUtils.showToast(getActivity(),beanList.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
            }
        });
    }
    //获取用户银行卡
    private void CheckCard(final int pageNum){
        showLoading("正在提交");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("pageNu", String.valueOf(pageNum));
        HttpHelper.getInstance().post(Url.CARDLIST, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                LogUtils.d("ssssssss"+response);
                hiddenLoading();
                final CardBeanList beanList = JsonUtils.parse(response, CardBeanList.class);
                final CardBeanList.DatasBean bean = JsonUtils.parse(response, CardBeanList.DatasBean.class);
                if (beanList.getCode() == 1) {
                    LogUtils.d("aaaaaaaaaa"+beanList.getDatas().getRecordsTotal());
                    if (beanList.getDatas().getRecordsTotal() == 0) {
                        final Dialog dialog = new Dialog(getContext(), R.style.translate_dialog);
                        View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_change_city, null);
                        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                        TextView tvContent = (TextView) contentView.findViewById(R.id.tv_dialog_content);
                        Button btnCancel = (Button) contentView.findViewById(R.id.btn_dialog_cancel);
                        Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
                        tvTitle.setText("提示");
                        tvContent.setText("检测到您当前并没有银行卡，是否要添加银行卡？");
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                popSelf();
                            }
                        });
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
//                                ShowFragmentUtils.showFragment(getActivity(),BindCardFragment.class,FragmentTag.BINDCARDFRAGMENT,null,true);
                                mBaseActivity.showActivity(BindCardActivity.class, null);
                            }
                        });

                        dialog.setContentView(contentView);

                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.width = DisplayUtils.getWidthPx() / 4 * 3;
                        window.setAttributes(params);
                        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0)
                                {
                                    dialog.dismiss();
                                    popSelf();
                                }
                                return false;

                            }
                        });
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    } else if (beanList.getDatas().getRecordsTotal()>0) {
                        add(mEditText.getText().toString().trim(),MD5.getMD5(new StringBuffer(mEditPwd.getText().toString().trim()).append(PreferenceManager.instance().getKey()).toString()),mName.getText().toString().trim(),
                                String.valueOf(mBankId),mNum,mPay.getText().toString().trim(),mAddress,String.valueOf(mId));

//                        for (int i = 0; i <beanList.getDatas().getCardList().size() ; i++) {
//                            if (beanList.getDatas().getCardList().get(i).isIsDefault()) {
//                                Glide.with(getContext()).load(beanList.getDatas().getCardList().get(i).getBankImg()).placeholder(R.drawable.ic_launcher).crossFade().into(mCircleImageView);
//                                mMyCard.setText(beanList.getDatas().getCardList().get(i).getBank());
//                                mCardNum.setText(beanList.getDatas().getCardList().get(i).getCardNumShort());
//                                mName.setText(beanList.getDatas().getCardList().get(i).getAccountName());
//                                mNum = beanList.getDatas().getCardList().get(i).getCardNum();
//                                mBankId = beanList.getDatas().getCardList().get(i).getBankId();
//                                mId = beanList.getDatas().getCardList().get(i).getId();
//                                mAddress = beanList.getDatas().getCardList().get(i).getKaihuhang();
//                            }
//                        }
                    }
                } else if (beanList.getCode() == 0) {
                    StringUtils.showToast(getActivity(), beanList.getMsg());
                } else {
                    StringUtils.showToast(getActivity(),beanList.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
            }
        });
    }
    private void initWatcher(){
        mWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length()>0) {
                    Double a = Double.valueOf(mEditText.getText().toString().trim());
                    if (a>Double.valueOf(mMoney)) {
                        mTextView1.setText("兑换金额不能超过兑换积分");
                        mPay.setVisibility(View.GONE);
                    } else if (a % 100 == 0) {
                        mTextView1.setText("服务费");
                        mPay.setVisibility(View.VISIBLE);
                        mPay.setText(a*Double.valueOf(mFee) + "");
                    } else {
                        mTextView1.setText("兑换金额只能为整百");
                        mPay.setVisibility(View.GONE);
                    }
                }else {
                    mTextView1.setText("服务费");
                    mPay.setVisibility(View.VISIBLE);
                    mPay.setText("0.00");
                }
            }
        };
    }
    private void add(String money, String pwd, String name, final String bankid, String card,String free,String address,String id){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.MONEY, money);
        hashMap.put("password",pwd);
        hashMap.put(IAppKey.ACCOUNTNAME,name);
        hashMap.put("bankId",bankid);
        hashMap.put(IAppKey.CARDNUM,card);
        hashMap.put(IAppKey.FACTORAGE,free);
        hashMap.put(IAppKey.KAIHUHANG,address);
        hashMap.put(IAppKey.CARDID,id);
        HttpHelper.getInstance().post(Url.TIXIANC, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                if (bean.getCode()==1) {
                    Toast.makeText(getContext(),"兑换成功", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new LoginSuccess());
                    ShowFragmentUtils.showFragment(getActivity(),ConvertFragment.class, FragmentTag.CONVERTFRAGMENT,null,true);
                } else if (bean.getCode() == 0) {
                    Toast.makeText(getContext(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Toast.makeText(getContext(),error_msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

