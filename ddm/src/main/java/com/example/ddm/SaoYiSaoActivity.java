package com.example.ddm;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.BaseActivity;
import com.example.ddm.appui.bean.CommonBean;
import com.example.ddm.appui.bean.MerchantBean;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.home.SaoYiSaoFragment;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.MD5;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.view.CircleImageView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import java.util.HashMap;
/**
 *转账界面
 */
public class SaoYiSaoActivity extends BaseActivity {
    private CircleImageView mPic;
    private TextView mName,mBack;
    private Button mSure;
    private EditText mMoney;
    private TextWatcher mWatcher;
    private String mUser;
    private String mNum;
    private String mPwd;
    private String Id;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sao_yi_sao);
        PokonyanApplication.getInstance().addActivity(this);
    }
    @Override
    protected void initView() {
        super.initView();
        mPic = (CircleImageView) findViewById(R.id.pic);
        mName = (TextView) findViewById(R.id.name);
        mBack = (TextView) findViewById(R.id.app_title_back);
        mSure = (Button) findViewById(R.id.registerInBtn);
        mMoney = (EditText) findViewById(R.id.edt);
        initWatch();
        mMoney.addTextChangedListener(mWatcher);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNull()) {
                    final Dialog dialog = new Dialog(SaoYiSaoActivity.this, R.style.translate_dialog);
                    View contentView =getLayoutInflater().inflate(R.layout.dialog_intput_pwd, null);
                    TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                    final EditText etContent = (EditText) contentView.findViewById(R.id.et_dialog_content);
                    Button btnCancel = (Button) contentView.findViewById(R.id.btn_dialog_cancel);
                    Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
                    mPwd = etContent.getText().toString().trim();
                    tvTitle.setText("请输入交易密码");
                    LogUtils.d("aaaaaaaaaaaaaaaaaaa"+mPwd);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(etContent.getText().toString().trim())) {
                                Toast.makeText(SaoYiSaoActivity.this, "交易密码不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                postMoney(mNum,etContent.getText().toString().trim(),Id);
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog.setContentView(contentView);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.width = DisplayUtils.getWidthPx() /4*3;
                    window.setAttributes(params);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        Intent intent = getIntent();
        LogUtils.d("wode"+intent.getStringExtra("codedContent"));
        Id = intent.getStringExtra("codedContent");
        getType(intent.getStringExtra("codedContent"));
    }
//    监听输入框
    private void initWatch(){
        mWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        mMoney.setText(s);
                        mMoney.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    mMoney.setText(s);
                    mMoney.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mMoney.setText(s.subSequence(0, 1));
                        mMoney.setSelection(1);
                        return;
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }
    /**
     * @param id
     * 判断用户的等级
     */
    private void getType(String id){
        LogUtils.d("我的走了");
    HashMap<String, String> hashMap = new HashMap<>();
    HttpHelper.getInstance().post(Url.GETSHOP+id, hashMap, new RawResponseHandler() {
        @Override
        public void onSuccess(int statusCode, String response) {
            LogUtils.d("我的数据"+response);
            CommonBean personBean = JsonUtils.parse(response, CommonBean.class);
            MerchantBean merchantBean = JsonUtils.parse(response, MerchantBean.class);
            if (personBean.getDatas().getUserTypeValue()==1) {
                Glide.with(SaoYiSaoActivity.this).load(personBean.getDatas().getAvotorr())
                        .placeholder(R.drawable.default_img).crossFade()
                        .into(mPic);
                mUser = personBean.getDatas().getRealName();
                mName.setText("向“"+personBean.getDatas().getRealName()+"”转账");
            } else if (merchantBean.getDatas().getUserTypeValue()==2) {
                Glide.with(SaoYiSaoActivity.this).load(merchantBean.getDatas().getShopImage())
                        .placeholder(R.drawable.ic_launcher).crossFade()
                        .into(mPic);
                mUser = merchantBean.getDatas().getShopName();
                mName.setText("向“"+merchantBean.getDatas().getShopName()+"”转账");
            }
        }
        @Override
        public void onFailure(int statusCode, String error_msg) {
            StringUtils.showToast(SaoYiSaoActivity.this, "失败");
        }
    });
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                LogUtils.d("我的等级"+content);

            }
        }
    }
    /**
     * @param id 用户id
     * @param num 金钱
     * 转账
     */
    private void postMoney(final String num, String pwd,String id){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.MONEY, num);
        hashMap.put("tradePassword", MD5.getMD5(new StringBuffer(pwd).append(PreferenceManager.instance().getKey()).toString()));
        HttpHelper.getInstance().post(Url.TRANSFER+id, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                if (bean.getCode()==1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("money",num);
                    bundle.putString("name",mUser);
                    ShowFragmentUtils.showFragment(SaoYiSaoActivity.this, SaoYiSaoFragment.class, FragmentTag.SAOYISAOFRAGMENT,bundle,true);
                } else if (bean.getCode()==0) {
                    Toast.makeText(SaoYiSaoActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                } else if (bean.getCode()==-1) {
                    Toast.makeText(SaoYiSaoActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                    PreferenceManager.instance().removeToken();
                    showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                Toast.makeText(SaoYiSaoActivity.this, "请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    判断金钱是否为空
    private boolean isNull(){
        mNum = mMoney.getText().toString().trim();
        if (TextUtils.isEmpty(mNum)) {
            StringUtils.showToast(this, "请输入转账金额");
            return false;
        }else {
            return true;
        }
    }
}
