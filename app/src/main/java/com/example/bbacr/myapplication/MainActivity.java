package com.example.bbacr.myapplication;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton mImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageButton = (ImageButton) findViewById(R.id.head_img);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuckeyDialog.Builder builder = new LuckeyDialog.Builder(MainActivity.this,R.style.Dialog);//调用style中的Diaog
                builder.setName("系统");
                builder.setOpenButton("", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this,Open.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                builder.setCloseButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });


//                <span style="color:#ff0000;">
                        Dialog dialog = builder.create();
                Window dialogWindow = dialog.getWindow();


                WindowManager m = getWindowManager();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                p.height = (int) (d.getHeight() * 0.7); // 高度设置为屏幕的0.6
                p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕的0.65
                dialogWindow.setAttributes(p);
//</span>
                        dialog.show();
            }
        });
    }
}
