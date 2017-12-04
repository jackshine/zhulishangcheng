package com.example.ddm.appui.recever;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseActivity;
import com.example.ddm.manager.PreferenceManager;

/*极光指定跳转的activity*/
public class AuroraActivity extends BaseActivity {
    private TextView mName,mMoney,mState,mUser,mTime,mOrder,mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aurora);
    }

    @Override
    protected void initView() {
        super.initView();
        mName = (TextView) findViewById(R.id.user_name);
        mMoney = (TextView) findViewById(R.id.money);
        mState = (TextView) findViewById(R.id.state);
        mUser = (TextView) findViewById(R.id.news);
        mTime = (TextView) findViewById(R.id.time);
        mOrder = (TextView) findViewById(R.id.order);
        mBack = (TextView) findViewById(R.id.app_title_back);
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
    }
    @Override
    protected void setData() {
        super.setData();
        Intent intent = getIntent();
        if (PreferenceManager.instance().getPhoneNum().equals(intent.getStringExtra("phone"))) {
            mName.setText("向“"+intent.getStringExtra("userNmae")+"”转账");
            if (intent.getStringExtra("money").indexOf(".") == -1) {
                mMoney.setText("-"+intent.getStringExtra("money") + ".00");
            } else {
                mMoney.setText("-"+intent.getStringExtra("money"));
            }
            mUser.setText(intent.getStringExtra("userNmae"));
            mTime.setText(intent.getStringExtra("creatTime"));
            mOrder.setText(intent.getStringExtra("code"));
            mState.setText(intent.getStringExtra("state"));
        } else {
            mName.setText("收到“"+intent.getStringExtra("userNmae")+"”的转账");
            if (intent.getStringExtra("money").indexOf(".") == -1) {
                mMoney.setText("+"+intent.getStringExtra("money") + ".00");
            } else {
                mMoney.setText("+"+intent.getStringExtra("money"));
            }
            mUser.setText(intent.getStringExtra("userNmae"));
            mTime.setText(intent.getStringExtra("creatTime"));
            mOrder.setText(intent.getStringExtra("code"));
            mState.setText(intent.getStringExtra("state"));
        }
    }
}
