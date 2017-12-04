package com.example.ddm.appui.mine;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ddm.LoginActivity;
import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.PopBaseAdapter;
import com.example.ddm.appui.bean.Address;
import com.example.ddm.appui.bean.PersonBean;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.eventbus.Card;
import com.example.ddm.appui.bean.eventbus.Commonality;
import com.example.ddm.appui.bean.eventbus.Email;
import com.example.ddm.appui.bean.eventbus.Name;
import com.example.ddm.appui.bean.eventbus.Update;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.bean.eventbus.WeChat;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.mine.myhome.CompileAddressFragment;
import com.example.ddm.appui.order.ManageAddressFragment;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.FileUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.utils.UploadUtil;
import com.example.ddm.appui.view.CircleImageView;
import com.example.ddm.appui.view.PullToRefreshView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

import static android.app.Activity.RESULT_OK;
/**
 * 设置界面
 */
public class SetFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener,UploadUtil.OnUploadProcessListener {
    private CircleImageView mCircleImageView;//个人图像
    /** 使用相册中的图片 */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /** 使用照相机拍照获取图片 */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    private byte[] mContent;
    private LinearLayout mTextViewName,mTextViewCard,mTextViewSex,mTextViewArea,mTextViewPhone,mTextViewWeChat,
    mTextViewQq,mTextViewMailBox,mTextViewChangePwd, mTextViewDealPwd;
    private TextView mBack,mTxtName,mTxtCard,mTxtSex,
            mTxtArea,mTxtPhone,mTxtWeChat,mTxtQq,mTxtMailBox;
    private Dialog photoDialog;//显示手机相册的弹窗
    private Button mButtonExitLogin;//退出登录按钮
    private ListView mListView;
    private PullToRefreshView mPullToRefreshView;
    private PopupWindow mPopupWindow;
    private String mPath;
    private int mInt;
    private List<String> sex = new ArrayList<>();
    private String mName,mCard,mSex,mArea,mPhone,mWeChat,mQq, mMail;//个人信息
    /** 获取到的图片路径 */
    private String picPath;
    private Intent lastIntent;
    private Uri photoUri;
    /** 从Intent获取图片路径的KEY */
    public static final String KEY_PHOTO_PATH = "photo_path";
    public SetFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        //点击监听，id
        lastIntent = getActivity().getIntent();
        mPullToRefreshView = mFindViewUtils.findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());
        mCircleImageView = mFindViewUtils.findViewById(R.id.iv_photo_person_ziliao);
        mCircleImageView.setType(CircleImageView.TYPE_CIRCLE);
        mTextViewName = mFindViewUtils.findViewById(R.id.user_name);
        mTextViewCard = mFindViewUtils.findViewById(R.id.identity_card);
        mTextViewArea = mFindViewUtils.findViewById(R.id.area);
        mTextViewSex = mFindViewUtils.findViewById(R.id.sex_person);
        mTextViewPhone = mFindViewUtils.findViewById(R.id.phone);
        mTextViewWeChat = mFindViewUtils.findViewById(R.id.WeChat);
        mTextViewQq = mFindViewUtils.findViewById(R.id.qq);
        mTextViewMailBox = mFindViewUtils.findViewById(R.id.mail_box);
        mTextViewChangePwd = mFindViewUtils.findViewById(R.id.change_pwd);
        mTextViewDealPwd = mFindViewUtils.findViewById(R.id.change_deal_pwd);
        mButtonExitLogin = mFindViewUtils.findViewById(R.id.btn_exit_login);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        //属性id
        mTxtName = mFindViewUtils.findViewById(R.id.text_name);
        mTxtCard = mFindViewUtils.findViewById(R.id.text_card);
        mTxtSex = mFindViewUtils.findViewById(R.id.text_sex);
        mTxtArea = mFindViewUtils.findViewById(R.id.text_area);
        mTxtPhone = mFindViewUtils.findViewById(R.id.text_phone);
        mTxtWeChat = mFindViewUtils.findViewById(R.id.text_we_chat);
        mTxtQq = mFindViewUtils.findViewById(R.id.text_qq);
        mTxtMailBox = mFindViewUtils.findViewById(R.id.text_mail);
        initList();

    }
private void initList(){
    sex.add("男");
    sex.add("女");
    sex.add("隐私");
    mName = mTxtName.getText().toString().trim();
    mCard = mTxtCard.getText().toString().trim();
    mSex = mTxtSex.getText().toString().trim();
    mArea = mTxtArea.getText().toString().trim();
    mPhone = mTxtPhone.getText().toString().trim();
    mWeChat = mTxtWeChat.getText().toString().trim();
    mQq = mTxtQq.getText().toString().trim();
    mMail = mTxtMailBox.getText().toString().trim();
}
    @Subscribe
    public void getQqMsg(Update update) {
        LogUtils.d("收到QQ::::::");
        mTxtQq.setText(update.getUpdateMsg());//接受QQ消息
    }
    @Subscribe
    public void getElMsg(Email email) {
        LogUtils.d("收到邮箱::::::");
        mTxtMailBox.setText(email.getEmail());//接受邮箱消息
    }
    @Subscribe
    public void getCdMsg(Card card) {
        LogUtils.d("收到身份证::::::");
        mTxtCard.setText(card.getCard());//接受身份证消息
    }
    @Subscribe
    public void getWCMsg(WeChat weChat) {
        LogUtils.d("收到微信::::::");
        mTxtWeChat.setText(weChat.getWeChat());//接受微信消息
    }
    @Subscribe
    public void getWCMsg(Name name) {
        LogUtils.d("收到姓名::::::");
        mTxtName.setText(name.getName());//接受姓名消息
    }
    @Override
    protected void setListener() {
        super.setListener();
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoDialog();
            }
        });
        mTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),NameFragment.class, FragmentTag.NAMEFRAGMENT,null,true);
            }
        });
        mTextViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),CardFragment.class, FragmentTag.CARDFRAGMENT,null,true);
            }
        });
        mTextViewArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),ManageAddressFragment.class, FragmentTag.MANAGEADDRESSFRAGMENT,null,true);

            }
        });
        mTextViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),PhoneNumFragment.class, FragmentTag.PHONENUMFRAGMENT,null,true);
            }
        });
        mTextViewWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),WeiChatFragment.class, FragmentTag.WEICHATFRAGMENT,null,true);
            }
        });
        mTextViewQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),QqFragment.class, FragmentTag.QQFRAGMENT,null,true);
            }
        });
        mTextViewMailBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),MailFragment.class, FragmentTag.MAILFRAGMENT,null,true);
            }
        });
        mTextViewChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),LoginPwdFragment.class, FragmentTag.LOGINPWDFRAGMENT,null,true);
            }
        });
        mTextViewDealPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),DealPwdFragment.class, FragmentTag.DEALPWDFRAGMENT,null,true);
            }
        });
        mButtonExitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
        mTextViewSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopWindow(sex);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mTxtSex.setText(sex.get(position));
                        if (sex.get(position).equals("男")) {
                            mInt = 4;
                        } else if (sex.get(position).equals("女")) {
                            mInt = 7;
                        } else {
                            mInt = 1;
                        }
//                        PreferenceManager.instance().saveSex(sex.get(position));
                        sex();
                        mPopupWindow.dismiss();
                    }
                });
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        postPerson();
    }
   //选择图片dialog
    private void showPhotoDialog() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_photo_choose, null);
        //初始化三个按钮
        Button btnPic = (Button) view.findViewById(R.id.btn_pic);
        Button btnCamera = (Button) view.findViewById(R.id.btn_camera);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        //响应点击事件
        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "选择照片", Toast.LENGTH_LONG).show();
                choseHeadImageFromGallery();
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"拍照",Toast.LENGTH_LONG).show();
                choseHeadImageFromCameraCapture();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoDialog.dismiss();
            }
        });
        photoDialog = new Dialog(getContext(), R.style.transparentFrameWindowStyle);
        photoDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = photoDialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        photoDialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        photoDialog.setCanceledOnTouchOutside(true);
        photoDialog.show();
    }
    //调用系统相册
    private void choseHeadImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
    }
    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
        } else {
            Toast.makeText(getContext(), "内存卡不存在",
                    Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 初始化poupwindow
     */
    private void initPopWindow(List<String> datas) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
//		// 引入窗口配置文件
        View view = inflater.inflate(R.layout.poup_window_small, null,false);
        mListView = (ListView) view.findViewById(R.id.listview_pop);
        mPopupWindow = new PopupWindow(view,  DisplayUtils.dip2px(52f)*2, WindowManager.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.popupAnimation);
        mListView.setAdapter(new PopBaseAdapter(getContext(),datas));
        // 设置此参数获得焦点，否则无法点击
        mPopupWindow.setFocusable(true);
//        Toast.makeText(getContext(),"gg",Toast.LENGTH_SHORT).show();
        mPopupWindow.showAsDropDown(mTxtSex, 0, 25);
    }
    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确定要退出吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        PreferenceManager.instance().removeToken();
                        if (!JPushInterface.isPushStopped(getContext())) {
                            JPushInterface.stopPush(getContext());
                        }
                        PokonyanApplication.getInstance().exit();
                    }
                });
        builder.setNeutralButton("切换账号", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!JPushInterface.isPushStopped(getContext())) {
                    JPushInterface.stopPush(getContext());
                }
                PreferenceManager.instance().removeToken();
                mBaseActivity.showActivity(LoginActivity.class,null);
            }
        });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullToRefreshView.onHeaderRefreshComplete("更新于:"
                        + Calendar.getInstance().getTime().toLocaleString());
                mPullToRefreshView.onHeaderRefreshComplete();
                StringUtils.showToast(getActivity(),"数据刷新完成!");
                postPerson();
//                if (TextUtils.isEmpty(PreferenceManager.instance().getName())) {
//                    mTxtName.setText("");
//                } else {
//                    mTxtName.setText(PreferenceManager.instance().getName());
//                }
//                if (TextUtils.isEmpty(PreferenceManager.instance().getPhoneNum())) {
//                    mTxtPhone.setText("");
//                } else {
//                    mTxtPhone.setText(PreferenceManager.instance().getPhoneNum());
//                }
//                if (TextUtils.isEmpty(PreferenceManager.instance().getQq())) {
//                    mTxtQq.setText("");
//                } else {
//                    mTxtQq.setText(PreferenceManager.instance().getQq());
//                }
//                if (TextUtils.isEmpty(PreferenceManager.instance().getIdentity())) {
//                    mTxtCard.setText("");
//                } else {
//                    mTxtCard.setText(PreferenceManager.instance().getIdentity());
//                }
//                if (TextUtils.isEmpty(PreferenceManager.instance().getWeixin())) {
//                    mTxtWeChat.setText("");
//                } else {
//                    mTxtWeChat.setText(PreferenceManager.instance().getWeixin());
//                }
//                if (TextUtils.isEmpty(PreferenceManager.instance().getEmail())) {
//                    mTxtMailBox.setText("");
//                } else {
//                    mTxtMailBox.setText(PreferenceManager.instance().getEmail());
//                }

            }
        }, 3000);
    }
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullToRefreshView.onFooterRefreshComplete();
                LogUtils.d("ddddddddddddddddddd");
                StringUtils.showToast(getActivity(),"加载更多数据");

            }

        }, 3000);
    }
    //请求用户地址
    private void postAddress(){
        HashMap<String, String> parse = new HashMap<>();
        parse.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.Get_Address, parse, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final Address bean = JsonUtils.parse(response, Address.class);
                if (bean.getCode() == 1) {

                } else {
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
    //修改性别
    private void sex(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.SEXVALUE, String.valueOf(mInt));
        HttpHelper.getInstance().post(Url.Alter_Sex, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                if (bean.getCode() == 1) {

                } else if (bean.getCode()==0) {
                    StringUtils.showToast(getActivity(), bean.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver contentResolver = getActivity().getContentResolver();
        switch (requestCode) {
            case SELECT_PIC_BY_TACK_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        try {
                            //获得图片的uri
//                            Uri orginalUri = data.getData();
                            Uri orginalUri = geturi(data);
                            String[] pojo = { MediaStore.Images.Media.DATA };
                            Cursor cursor = getActivity().managedQuery(orginalUri, pojo, null, null,
                                    null);
                            if (cursor != null) {
                                ContentResolver cr = getContext().getContentResolver();
                                int colunm_index = cursor
                                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                cursor.moveToFirst();
                                mPath = cursor.getString(colunm_index);
                                LogUtils.d("图片"+mPath);
                                File file = new File(mPath);
                                if(!file.exists()){
                                    showLoading();
                                    return;
                                }
                                showLoading("正在上传");
                                OkHttpUtils.post().tag(this)
                                        .url(Url.POSTPIC)
//                                        .addParams(IAppKey.TOKEN,PreferenceManager.instance().getToken())
                                        .addFile("file",file.getName(),file)
                                        .build().execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        hiddenLoading();
                                    }
                                    @Override
                                    public void onResponse(String response, int id) {
                                        LogUtils.d("fshjfjjhfjjkajkjkajka===="+response.substring(8, response.length()));
                                        OkHttpUtils.post().tag(this).url(Url.PIC).addParams("avtor", response.substring(8, response.length())).
                                                addParams(IAppKey.TOKEN,PreferenceManager.instance().getToken()).build()
                                                .execute(new StringCallback() {
                                            @Override
                                            public void onError(Call call, Exception e, int id) {
                                                hiddenLoading();
                                            }
                                            @Override
                                            public void onResponse(String response, int id) {
                                               LogUtils.d("dhjahhffahhhfhf"+response);
                                                hiddenLoading();
                                                EventBus.getDefault().post(new UpdateCardList("图片更新"));
                                            }
                                        });
                                    }
                                });
                                LogUtils.d("地址========"+mPath);
                                //将图片内容解析成字节数组
                                InputStream is = null;
                                is = contentResolver.openInputStream(Uri.parse(orginalUri.toString()));
                                mContent = FileUtils.readStream(contentResolver.openInputStream(Uri.parse(orginalUri.toString())));
                                Bitmap myBitmap = FileUtils.getPicFromBytes(mContent, null);
//                                Glide.with(this).load(mPath).into(mCircleImageView);
                                mCircleImageView.setImageBitmap(myBitmap);
//                                UploadUtil uploadUtil = UploadUtil.getInstance(getActivity());
//                                uploadUtil.setOnUploadProcessListener(this);
//                                HashMap<String, String> hashMap = new HashMap<>();
//                                hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
//                                String result = uploadUtil.uploadFile(mPath, "pic", Url.POSTPIC,hashMap);
//                                StringUtils.showToast(getActivity(),"上传成功"+result);
//
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                photoDialog.dismiss();
                break;
            case SELECT_PIC_BY_PICK_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (data != null ) {
                        Uri uri = data.getData();
                        LogUtils.d("地址===="+uri);
                        if (uri != null) {
                            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                            if (cursor.moveToFirst()) {
                                String videoPath = cursor.getString(cursor.getColumnIndex("_data"));// 获取绝对路径
                                LogUtils.d("地址====" + videoPath);
                                File file = new File(videoPath);
                                if (!file.exists()) {
                                    showLoading();
                                    return;
                                }
                                showLoading("正在上传");
                                OkHttpUtils.post().tag(this)
                                        .url(Url.POSTPIC)
                                        .addParams(IAppKey.TOKEN, PreferenceManager.instance().getToken())
                                        .addFile("file", file.getName(), file)
                                        .build().execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        hiddenLoading();
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        LogUtils.d("qqqqqqq====" + response.substring(8, response.length()));
                                        OkHttpUtils.post().tag(this).url(Url.PIC).addParams("avtor", response.substring(8, response.length())).
                                                addParams(IAppKey.TOKEN, PreferenceManager.instance().getToken()).build()
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onError(Call call, Exception e, int id) {
                                                        hiddenLoading();
                                                    }

                                                    @Override
                                                    public void onResponse(String response, int id) {
                                                        hiddenLoading();
                                                        LogUtils.d("dhjahhffahhhfhf" + response);
                                                        EventBus.getDefault().post(new UpdateCardList("图片更新"));
                                                    }
                                                });
                                    }
                                });
                            }
                        } else {
                            Bitmap bm = (Bitmap) data.getExtras().get("data");
                            File file = getFile(bm);
                             String videoPath = file.getPath();
                            showLoading("正在上传");
                            OkHttpUtils.post().tag(this)
                                    .url(Url.POSTPIC)
                                    .addParams(IAppKey.TOKEN, PreferenceManager.instance().getToken())
                                    .addFile("file", file.getName(), file)
                                    .build().execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    hiddenLoading();
                                }
                                @Override
                                public void onResponse(String response, int id) {
                                    LogUtils.d("qqqqqqq====" + response.substring(8, response.length()));
                                    OkHttpUtils.post().tag(this).url(Url.PIC).addParams("avtor", response.substring(8, response.length())).
                                            addParams(IAppKey.TOKEN, PreferenceManager.instance().getToken()).build()
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onError(Call call, Exception e, int id) {
                                                    hiddenLoading();
                                                }
                                                @Override
                                                public void onResponse(String response, int id) {
                                                    LogUtils.d("dhjahhffahhhfhf" + response);
                                                    hiddenLoading();
                                                    EventBus.getDefault().post(new UpdateCardList("图片更新"));
                                                }
                                            });
                                }
                            });
                        }
                    }
                    try {
                        Bundle extras = data.getExtras();
                        Bitmap myBitmap = (Bitmap) extras.get("data");
                        mCircleImageView.setImageBitmap(myBitmap);
//                        map.clear();
//                        map.put("user_id", "32");
//                        UploadUtil uploadUtil = UploadUtil.getInstance(PersonCenterActivity.this);
//                        uploadUtil.setOnUploadProcessListener(PersonCenterActivity.this);
//                        String result = uploadUtil.uploadFile(, "pic", NetUtils.UPLOAD_PIC, map);
//                        StringUtils.showToast(PersonCenterActivity.this, "上传成功" + result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                photoDialog.dismiss();
                break;
        }
    }

    /**
     * 解决小米手机上获取图片路径为null的情况
     * @param intent
     * @return
     */
    public Uri geturi(android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = getActivity().getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID },
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }
    private File getFile(Bitmap bitmap){
        String pictureDir = "";
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ByteArrayOutputStream baos = null;
        File file = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArray = baos.toByteArray();
            String saveDir = Environment.getExternalStorageDirectory()
                    + "/dreamtownImage";
            File dir = new File(saveDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            file = new File(saveDir,".jpg");
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(byteArray);
            pictureDir = file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return file;
    }
    @Override
    public void onUploadDone(int responseCode, String message) {

    }

    @Override
    public void onUploadProcess(int uploadSize) {

    }

    @Override
    public void initUpload(int fileSize) {

    }
    //获得个人信息
    private void postPerson(){
        showLoading("正在加载。。。");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.Personal_Data, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final PersonBean bean = JsonUtils.parse(response, PersonBean.class);
                hiddenLoading();
                if (bean.getCode()==1) {
                    PreferenceManager.instance().saveName(bean.getDatas().getUser().getRealName());
                    PreferenceManager.instance().saveIdentity(bean.getDatas().getUser().getIdentity());
                    PreferenceManager.instance().saveWeixin(bean.getDatas().getUser().getWeixin());
                    PreferenceManager.instance().saveQq(bean.getDatas().getUser().getQq());
                    PreferenceManager.instance().saveEmail(bean.getDatas().getUser().getEmail());
                    PreferenceManager.instance().savePhoneNum(bean.getDatas().getUser().getName());
                    LogUtils.d("imageurl"+bean.getDatas().getUser().getAvotorr());
                    Glide.with(getContext()).load(bean.getDatas().getUser().getAvotorr())
                            .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .placeholder(R.drawable.ic_launcher).crossFade().into(mCircleImageView);
                    mTxtName.setText(bean.getDatas().getUser().getRealName());
                    mTxtCard.setText(bean.getDatas().getUser().getIdentity());
                    mTxtSex.setText(bean.getDatas().getUser().getSex());
                    mTxtPhone.setText(bean.getDatas().getUser().getName());
                    mTxtWeChat.setText(bean.getDatas().getUser().getWeixin());
                    mTxtQq.setText(bean.getDatas().getUser().getQq());
                    mTxtMailBox.setText(bean.getDatas().getUser().getEmail());
                } else if (bean.getCode()==0) {
                    StringUtils.showToast(getActivity(),bean.getMsg());
                } else if (bean.getCode()==-1) {
                    PreferenceManager.instance().removeToken();
                    StringUtils.showToast(getActivity(),bean.getMsg());
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
