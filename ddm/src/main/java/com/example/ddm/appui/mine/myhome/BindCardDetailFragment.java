package com.example.ddm.appui.mine.myhome;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.CardDetailBean;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.eventbus.Update;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 * 银行卡详情
 */
public class BindCardDetailFragment extends BaseFragment {
    private TextView mTextName,mTextCard,mTextBank, mTextAddress,mName,mCard;
    private ImageView mImageView;
    private TextView mBack;
    private RelativeLayout mRelativeLayout;
    private Button mButton;
    public BindCardDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bind_card_detail, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mRelativeLayout = mFindViewUtils.findViewById(R.id.Relative);
        mButton = mFindViewUtils.findViewById(R.id.btn_unlock);
        mImageView = mFindViewUtils.findViewById(R.id.iv_bank_icon);
        mName = mFindViewUtils.findViewById(R.id.tv_card_user);
        mCard = mFindViewUtils.findViewById(R.id.tv_card_num);
        mTextName = mFindViewUtils.findViewById(R.id.tv_bank_user);
        mTextCard = mFindViewUtils.findViewById(R.id.tv_xing_four);
        mTextBank = mFindViewUtils.findViewById(R.id.tv_bank_name);
        mTextAddress = mFindViewUtils.findViewById(R.id.tv_card_address);
        bankDetail(mArguments.getInt("cardId"));
    }

    @Override
    protected void setListener() {
        super.setListener();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Relieve(mArguments.getInt("cardId"));
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
    }
    /**
     * 银行卡详情
     */
    private void bankDetail(int cardId) {
        showLoading();
        HashMap<String, String> param = new HashMap<>();
        param.put("token", PreferenceManager.instance().getToken());
        param.put("cardId", String.valueOf(cardId));
        HttpHelper.getInstance().post(Url.BANKCARD_DETAIL, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                LogUtils.d("获取银行卡详情成功：" + response);
                hiddenLoading();
                final CardDetailBean bean = JsonUtils.parse(response, CardDetailBean.class);
                if (bean.getCode()==1) {
                    if (bean.getDatas().getBankId()==1) {
                        mRelativeLayout.setBackgroundResource(R.drawable.gradient_ramp);
                    } else if (bean.getDatas().getBankId()==2) {
                        mRelativeLayout.setBackgroundResource(R.drawable.gradient_ramp_1);
                    }else if (bean.getDatas().getBankId()==3) {
                        mRelativeLayout.setBackgroundResource(R.drawable.gradient_ramp_2);
                    }else if (bean.getDatas().getBankId()==4) {
                        mRelativeLayout.setBackgroundResource(R.drawable.gradient_ramp_3);
                    }else if (bean.getDatas().getBankId()==5) {
                        mRelativeLayout.setBackgroundResource(R.drawable.gradient_ramp_4);
                    }else if (bean.getDatas().getBankId()==6) {
                        mRelativeLayout.setBackgroundResource(R.drawable.gradient_ramp_5);
                    }else if (bean.getDatas().getBankId()==7) {
                        mRelativeLayout.setBackgroundResource(R.drawable.gradient_ramp_6);
                    }else if (bean.getDatas().getBankId()==8) {
                        mRelativeLayout.setBackgroundResource(R.drawable.gradient_ramp_7);
                    }else if (bean.getDatas().getBankId()==9) {
                        mRelativeLayout.setBackgroundResource(R.drawable.gradient_ramp_8);
                    }else if (bean.getDatas().getBankId()==10) {
                        mRelativeLayout.setBackgroundResource(R.drawable.gradient_ramp_9);
                    }else if (bean.getDatas().getBankId()==11) {
                        mRelativeLayout.setBackgroundResource(R.drawable.gradient_ramp_10);
                    }else if (bean.getDatas().getBankId()==12) {
                        mRelativeLayout.setBackgroundResource(R.drawable.gradient_ramp_11);
                    }else if (bean.getDatas().getBankId()==13) {
                        mRelativeLayout.setBackgroundResource(R.drawable.gradient_ramp_12);
                    }
                    mTextName.setText(bean.getDatas().getAccountName());
                    String i = bean.getDatas().getCardNum().substring(bean.getDatas().getCardNum().length() - 4, bean.getDatas().getCardNum().length());
                    mTextCard.setText(i);
                    mTextAddress.setText(bean.getDatas().getKaihuhang());
                    mTextBank.setText(bean.getDatas().getBank());
                    mName.setText(bean.getDatas().getAccountName());
                    mCard.setText(bean.getDatas().getCardNum());
                    Glide.with(getContext()).load(mArguments.getString("bankImg")).placeholder(R.drawable.ic_launcher).crossFade().into(mImageView);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                LogUtils.d("获取银行卡详情失败：" + error_msg);
                hiddenLoading();
            }
        });
    }
    //解绑银行卡
    private void Relieve(int cardId){
        showLoading();
        HashMap<String, String> param = new HashMap<>();
        param.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        param.put(IAppKey.CARDID, String.valueOf(cardId));
        HttpHelper.getInstance().post(Url.UNBINDTHISCARD, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                if (bean.getCode() == 1) {
                    EventBus.getDefault().post(new Update("再次刷新银行卡列表"));
                    final Dialog dialog = new Dialog(getActivity(), R.style.translate_dialog);
                    View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_sure, null);
                    TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                    TextView tvContent = (TextView) contentView.findViewById(R.id.tv_dialog_content);
                    Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
                    btnOk.setText("确认");
                    tvTitle.setText("提示");
                    tvContent.setText("已经成功解绑");
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            popSelf();
                        }
                    });
                    dialog.setContentView(contentView);
                    Window window = dialog.getWindow();
//                    window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.width = DisplayUtils.getWidthPx()/2;
                    window.setAttributes(params);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                } else {
                    Toast.makeText(getContext(), "解绑失败", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
            }
        });
    }
}
