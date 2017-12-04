package com.example.ddm;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.BaseActivity;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.FragAdapter;
import com.example.ddm.appui.fragment.AccountFragment;
import com.example.ddm.appui.fragment.PhoneFragment;
import java.util.ArrayList;
import java.util.List;
public class LoginActivity extends BaseActivity {
    private TextView mBack;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<String> mTitles = new ArrayList<>();//标题集合
    private List<BaseFragment> mfragments = new ArrayList<>();//视图集合
    private AccountFragment mAccountFragment;
    private PhoneFragment mPhoneFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PokonyanApplication.getInstance().addActivity(this);
    }
    @Override
    protected void initView() {
        super.initView();
        mAccountFragment = new AccountFragment();
        mPhoneFragment = new PhoneFragment();
        mfragments.add(mAccountFragment);
        mfragments.add(mPhoneFragment);
        mTitles.add("账号密码登录");
        mTitles.add("手机号快捷登录");
        mTabLayout = (TabLayout) findViewById(R.id.tab_title);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mBack = (TextView) findViewById(R.id.app_title_back);
    }
    @Override
    protected void setListener() {
        super.setListener();
//给TabLayout添加内容
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(0)));
        //初始化适配器
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), mfragments,mTitles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);//让TabLayout随着ViewPager的变换而变换
//        mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                showActivity(MainActivity.class,null);
//            }
//        });
    }
    @Override
    protected void setData() {
        super.setData();
    }

//    @Override
//    public void onBackPressed() {
//
//       showActivity(MainActivity.class,null);
//
//    }
        @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
          dialog();
        }
        return false;
    }
    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        LoginActivity.this.finish();
                        PokonyanApplication.getInstance().exit();
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

}
