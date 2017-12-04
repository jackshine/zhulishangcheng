package com.example.ddm.appui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.ddm.R;

/**
 * 作者：Yzp on 2017-04-13 15:41
 * 邮箱：15111424807@163.com
 * QQ: 486492302
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
        this.show();
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
        this.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emption_dialog);
    }
}
