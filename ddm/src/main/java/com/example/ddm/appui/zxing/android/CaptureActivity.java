package com.example.ddm.appui.zxing.android;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.R;
import com.example.ddm.RegisterCodeActivity;
import com.example.ddm.SaoYiSaoActivity;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.home.SaoYiSaoFragment;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.zxing.camera.CameraManager;
import com.example.ddm.appui.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * 这个activity打开相机，在后台线程做常规的扫描；它绘制了一个结果view来帮助正确地显示条形码，在扫描的时候显示反馈信息，
 * 然后在扫描成功的时候覆盖扫描结果
 */
public final class CaptureActivity extends Activity implements
        SurfaceHolder.Callback, View.OnClickListener {
    private static final String TAG = CaptureActivity.class.getSimpleName();
    // 相机控制
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private IntentSource source;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    // 电量控制
    private InactivityTimer inactivityTimer;
    // 声音、震动控制
    private BeepManager beepManager;
    private boolean isFlash=true;
    //声明返回按钮,三个图片按钮
    private ImageButton imageButton_back;
    private Button photo_btn;
    private Button flash_btn;
    private Button qrcode_btn;
    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    /**
     * OnCreate中初始化一些辅助类，如InactivityTimer（休眠）、Beep（声音）以及AmbientLight（闪光灯）
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.capture);
        CameraManager.init(getApplication());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        imageButton_back = (ImageButton) findViewById(R.id.capture_imageview_back);
        photo_btn = (Button) findViewById(R.id.photo_btn);
        flash_btn = (Button) findViewById(R.id.flash_btn);
        qrcode_btn = (Button) findViewById(R.id.qrcode_btn);
        photo_btn.setOnClickListener(this);
        flash_btn.setOnClickListener(this);
        qrcode_btn.setOnClickListener(this);
        imageButton_back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.capture_imageview_back :
                    //退出扫描
                    finish();
                    break;
                case R.id.photo_btn :
                    //打开相册
                    break;
                case R.id.flash_btn :
                    light();
                    break;
                case R.id.qrcode_btn :
                    //生成二维码
                    break;
            }
    }
    protected void light() {
        if (isFlash == true) {
            isFlash = false;
            // 开闪光灯
            cameraManager.openLight();

        } else {
            isFlash = true;
            // 关闪光灯
            cameraManager.offLight();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // CameraManager必须在这里初始化，而不是在onCreate()中。
        // 这是必须的，因为当我们第一次进入时需要显示帮助页，我们并不想打开Camera,测量屏幕大小
        // 当扫描框的尺寸不正确时会出现bug
        cameraManager = new CameraManager(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);
        handler = null;
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；
            // surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }
        beepManager.updatePrefs();
        inactivityTimer.onResume();
        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;
    }
    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }
    /**
     * 扫描成功，处理反馈信息
     *
     * @param rawResult
     * @param barcode
     * @param scaleFactor
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();
        boolean fromLiveScan = barcode != null;
        //这里处理解码完成后的结果，此处将参数回传到Activity处理
        if (fromLiveScan) {
            beepManager.playBeepSoundAndVibrate();
            LogUtils.d("我的网站"+rawResult.getText());
            if (rawResult.getText().substring(0, 2).equals("id")) {
                Intent intent = new Intent(CaptureActivity.this, SaoYiSaoActivity.class);
                intent.putExtra("codedContent", rawResult.getText().replace(",","&"));
                intent.putExtra("codedBitmap", barcode);
                setResult(RESULT_OK, intent);
                finish();
                startActivity(intent);
            }
//            else if (rawResult.getText().substring(0, 20).equals("http://www.ddmzl.com")) {
//                Intent intent = new Intent(CaptureActivity.this, RegisterCodeActivity.class);
//                intent.putExtra("Invitation_code", rawResult.getText().substring(24));
//                setResult(RESULT_OK, intent);
//                finish();
//                startActivity(intent);
//            }
            else {
                final Dialog dialog = new Dialog(this, R.style.translate_dialog);
                View contentView = getLayoutInflater().inflate(R.layout.dialog_sure, null);
                TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                TextView tvContent = (TextView) contentView.findViewById(R.id.tv_dialog_content);
                Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
                btnOk.setText("确认");
                tvTitle.setText("扫码结果");
                tvContent.setText(rawResult.getText());
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        finish();
                    }
                });
                dialog.setContentView(contentView);
                Window window = dialog.getWindow();
//                    window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                WindowManager.LayoutParams params = window.getAttributes();
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                params.width = DisplayUtils.getWidthPx() / 2;
                window.setAttributes(params);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        }
    }
    /**
     * 初始化Camera
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats,
                        decodeHints, characterSet, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }
    /**
     * 显示底层错误信息并退出应用
     */
    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_sao));
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }
    /**
     * 判断是否是数字类型
     */
    public static boolean isValidNumber(String s)
    {
        try
        {
            double i = Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException  e)
        {
            //如果throw Java.text.NumberFormatException或者NullPointerException，就说明格式不对
            return false;
        }
    }
}
