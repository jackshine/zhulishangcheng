package com.example.ddm.appui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.WelcomeActivity;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.bean.VersionBean;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.AppInnerDownLoder;
import com.example.ddm.appui.utils.DownLoadUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.PackageUtils;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class UpdateDialog extends Dialog{
    private TextView tvUpdateTitle,tv_update_now, tvUpdateContent;
    private Button btnUpdateIdOk,btnUpdateId_Cancel;
    private boolean isWelcomeActivity;
    private Context context;
    private VersionBean vsersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ShowCenter();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

    /**
     * @param ctx 上下文
     */
    public UpdateDialog(Context ctx, VersionBean vsersion) {
        super(ctx, R.style.dialog_custom);
        this.context = ctx;
        this.vsersion = vsersion;
        isWelcomeActivity = this.context instanceof WelcomeActivity;
    }
    private void ShowCenter() {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        window.setWindowAnimations(R.style.center_menu_animation); // 添加动画效果
        setContentView(R.layout.layout_mine_version);
        tvUpdateTitle = (TextView) findViewById(R.id.tv_update_title);
        tv_update_now = (TextView) findViewById(R.id.tv_update_now);
        tvUpdateContent = (TextView) findViewById(R.id.tv_update_content);
        btnUpdateIdOk = (Button) findViewById(R.id.btn_update_id_ok);
        btnUpdateId_Cancel = (Button) findViewById(R.id.btn_update_id_cancel);
        // 宽度全屏
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 4 / 5; // 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(false);
        InitData();
    }
    private void InitData() {
        String versionName = PackageUtils.getVersionName(context);
        tv_update_now.setText("当前版本: "+versionName);
        tvUpdateTitle.setText("更新版本："+vsersion.getDatas().getAndroidVersionCode());
        tvUpdateContent.setText(vsersion.getDatas().getAndroidMemo());
        btnUpdateIdOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DownLoadUtils.getInstance(context).canDownload()) {
                    LogUtils.d("可以下载..........");
//                    DownloadApk.downloadApk(context,resultBean.getUrl(),resultBean.getTitle(), "jzlapp");
                    AppInnerDownLoder.downLoadApk(context, Url.UPDAPE,"助利商城");
                } else {
                    LogUtils.d("没有下载..........");
                    DownLoadUtils.getInstance(context).skipToDownloadManager();
                }
                UpdateDialog.this.dismiss();
            }
        });
        btnUpdateId_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDialog.this.dismiss();
                if (isWelcomeActivity) {
                    ((WelcomeActivity) context).finish();
                    context.startActivity(new Intent(PokonyanApplication.getInstance(), MainActivity.class));
                }
            }
        });
    }
}
