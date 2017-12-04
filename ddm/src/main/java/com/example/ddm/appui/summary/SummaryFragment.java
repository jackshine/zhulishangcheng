package com.example.ddm.appui.summary;
import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import com.example.ddm.LoginActivity;
import com.example.ddm.ProvinceActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.PopBaseAdapter;
import com.example.ddm.appui.bean.Category;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.eventbus.Address;
import com.example.ddm.appui.bean.eventbus.AddressId;
import com.example.ddm.appui.bean.eventbus.AddressTwo;
import com.example.ddm.appui.bean.eventbus.AddressTwoId;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.mine.myhome.AcceptOrdersFragment;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * 入驻
 */
public class SummaryFragment extends BaseFragment {
    private List<String> mList = new ArrayList<>();//店铺类型的集合
    private ListView mListView;
    private TextView mShopping;
    private List<Category.DataBeans> mCategoryList = new ArrayList<>();//分类的集合
    private RelativeLayout mRelativeLayout;
    private ImageView mImageView;//选择店铺
    private PopupWindow mPopupWindow;
    private String provinceId,cityId, townId;
    private Button mSure,mClear1,mClear2,mClear3,mClear4,mClear5;
    private EditText mEditText_Name,mEditText_Phone,mEditText_Shopping,
            mEditText_Address, mEditText_WeChat,mEditText_Remark,mEditArea;
   private TextWatcher mWatcher_Name,mWatcher_Phone,mWatcher_Shopping,mWatcher_Address, mWatcher_WeChat;
    private String mShopClass,mName,mPhone,mShop,mAddress, mWeChat,mPosition;
    public SummaryFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }
    //首页分类接口
    @Override
    protected void initView(View view) {
        super.initView(view);
        mEditArea = mFindViewUtils.findViewById(R.id.text_address);
        mRelativeLayout = mFindViewUtils.findViewById(R.id.Relative);
        mImageView = mFindViewUtils.findViewById(R.id.pic);
        mShopping = mFindViewUtils.findViewById(R.id.text_sex);
        mEditText_Name = mFindViewUtils.findViewById(R.id.text_card);
        mEditText_Phone = mFindViewUtils.findViewById(R.id.text_card_hang);
        mEditText_Shopping = mFindViewUtils.findViewById(R.id.text_card_num);
        mEditText_Address = mFindViewUtils.findViewById(R.id.text_card_num_sure);
        mEditText_WeChat = mFindViewUtils.findViewById(R.id.text_we_chat);
        mEditText_Remark = mFindViewUtils.findViewById(R.id.remark);
        mSure = mFindViewUtils.findViewById(R.id.registerInBtn);
        mClear1 = mFindViewUtils.findViewById(R.id.but1);
        mClear2 = mFindViewUtils.findViewById(R.id.account_clear);
        mClear3 = mFindViewUtils.findViewById(R.id.btn2);
        mClear4 = mFindViewUtils.findViewById(R.id.btn3);
        mClear5 = mFindViewUtils.findViewById(R.id.btn4);
        mClear1.setOnClickListener(this);
        mClear2.setOnClickListener(this);
        mClear3.setOnClickListener(this);
        mClear4.setOnClickListener(this);
        mClear5.setOnClickListener(this);
        getCategory();
//        initList();
        initWatcher();
        mEditText_Name.addTextChangedListener(mWatcher_Name);
        mEditText_Phone.addTextChangedListener(mWatcher_Phone);
        mEditText_Shopping.addTextChangedListener(mWatcher_Shopping);
        mEditText_Address.addTextChangedListener(mWatcher_Address);
        mEditText_WeChat.addTextChangedListener(mWatcher_WeChat);
    }
    private void getCategory() {
        Map<String, String> param = new HashMap<>();
        HttpHelper.getInstance().post(Url.GET_CATEGORY, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "获取首页分类成功：" + response);
                Category category = JsonUtils.parse(response, Category.class);
                if (category.getCode()==1&&category.getDatas().size()>0) {
                    mCategoryList.addAll(category.getDatas());
                    for (int i = 0; i <category.getDatas().size()-1; i++) {
                        mList.add(category.getDatas().get(i).getName());
                    }
                    LogUtils.d("ddahfhfh"+mList);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获取首页分类失败：" + error_msg);
            }
        });
    }
    @Subscribe
    public void getAddress(AddressTwo addresstwo) {
        LogUtils.d("收到地址::::::");
        mEditArea.setText(addresstwo.getProvince()+addresstwo.getCity()+addresstwo.getTown());//接受地址消息
    }
    @Subscribe
    public void getaddressid(AddressTwoId addresstwoid) {
        LogUtils.d("收到地址::::::");
        provinceId = addresstwoid.getProvinceId();
        cityId = addresstwoid.getCityId();
        townId = addresstwoid.getTownId();
    }
    @Override
    protected void setListener() {
        super.setListener();
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopWindow( mList);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mPosition= String.valueOf(mCategoryList.get(position).getID());
                        LogUtils.d("我的id"+mPosition);
                        mShopping.setText(mList.get(position));
                        mPopupWindow.dismiss();
                    }
                });
            }
        });
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNull()) {
                    Enter();
                }
            }
        });
        mEditArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.showActivity(ProvinceActivity.class,null);
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
    }
    /**
     * 初始化poupwindow
     */
    private void initPopWindow(List<String> datas) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
//		// 引入窗口配置文件
        View view = inflater.inflate(R.layout.poup_window_small, null,false);
        mListView = (ListView) view.findViewById(R.id.listview_pop);
        mPopupWindow = new PopupWindow(view,DisplayUtils.dip2px(52f)*2, WindowManager.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.popupAnimation);
        mListView.setAdapter(new PopBaseAdapter(getContext(),datas));
        // 设置此参数获得焦点，否则无法点击
        mPopupWindow.setFocusable(true);
//        Toast.makeText(getContext(),"gg",Toast.LENGTH_SHORT).show();
        mPopupWindow.showAsDropDown(mImageView, 0, 25);
    }
    private boolean isNull(){
        mShopClass = mShopping.getText().toString().trim();
        mName = mEditText_Name.getText().toString().trim();
        mPhone = mEditText_Phone.getText().toString().trim();
        mShop = mEditText_Shopping.getText().toString().trim();
        mAddress = mEditText_Address.getText().toString().trim();
        mWeChat = mEditText_WeChat.getText().toString().trim();
        if (TextUtils.isEmpty(mName)) {
            Toast.makeText(getContext(), "姓名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mShopClass)) {
            Toast.makeText(getContext(), "店铺类别不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (TextUtils.isEmpty(mShop)) {
                Toast.makeText(getContext(), "店铺名不能为空", Toast.LENGTH_SHORT).show();
                return false;
            } else if (TextUtils.isEmpty(mAddress)) {
                Toast.makeText(getContext(), "店铺地址不能为空", Toast.LENGTH_SHORT).show();
                return false;
            } else if (TextUtils.isEmpty(mPhone)) {
                Toast.makeText(getContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                return false;
            } else if (StringUtils.isMobileNO(mPhone)) {
                return true;
            } else {
                Toast.makeText(getContext(), "手机号不合法", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
    /*清空数据*/
    private void initWatcher() {
        mWatcher_Name = new TextWatcher() {
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
        mWatcher_Phone = new TextWatcher() {
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
        mWatcher_Shopping = new TextWatcher() {
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
        mWatcher_Address = new TextWatcher() {
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
        mWatcher_WeChat = new TextWatcher() {
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
                    mClear5.setVisibility(View.VISIBLE);
                } else {
                    mClear5.setVisibility(View.INVISIBLE);
                }
            }
        };
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.but1:
                mEditText_Name.setText("");
                break;
            case R.id.account_clear:
                mEditText_Phone.setText("");
                break;
            case R.id.btn2:
                mEditText_Shopping.setText("");
                break;
            case R.id.btn3:
                mEditText_Address.setText("");
                break;
            case R.id.btn4:
                mEditText_WeChat.setText("");
                break;
        }
    }
    private void Enter(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.CUSTOMERNAME,mName);
        hashMap.put(IAppKey.CUSTOMERPHONE,mPhone);
        hashMap.put(IAppKey.CATEGORYID,mPosition);
        hashMap.put(IAppKey.ADDRESS,mAddress);
        hashMap.put(IAppKey.SHOPNAME,mShop);
        hashMap.put("provinceId",provinceId);
        hashMap.put("cityId",cityId);
        hashMap.put("areaId",townId);
        hashMap.put(IAppKey.CUSTOMERNOTE,mEditText_Remark.getText().toString().trim());
        hashMap.put(IAppKey.WEIXIN,mWeChat);
        HttpHelper.getInstance().post(Url.ENTER, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                if (bean.getCode() == 1) {
                    final Dialog dialog = new Dialog(getActivity(), R.style.translate_dialog);
                    View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_sure, null);
                    TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                    TextView tvContent = (TextView) contentView.findViewById(R.id.tv_dialog_content);
                    Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
                    btnOk.setText("确认");
                    tvTitle.setText("提示");
                    tvContent.setText("入驻申请以提交");
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
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
                } else if (bean.getCode() == 0) {
                    StringUtils.showToast(getActivity(), bean.getMsg());
                } else {
                    Toast.makeText(getContext(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Toast.makeText(mBaseActivity, "请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
