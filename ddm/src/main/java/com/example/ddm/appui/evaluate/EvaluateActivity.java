package com.example.ddm.appui.evaluate;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.R;
import com.example.ddm.appui.BaseActivity;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.FileUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.manager.PreferenceManager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;

public class EvaluateActivity extends BaseActivity implements View.OnClickListener,TextWatcher {
    private EditText et_comment_content;
    private TextView tv_submit;
    private TextView mBack;
    private ImageView iv_choose_goods_pic;
    private HorizontalScrollView hsv_comment_imgs;
    private ImageView iv_comment_star_1, iv_comment_star_2, iv_comment_star_3, iv_comment_star_4, iv_comment_star_5;
    private List<ImageView> starList;
    private List<String> imageUrls;//所有晒图图片路径
    private int currentStarCount;
    private List<String> mPath = new ArrayList<>();
    private InputMethodManager manager;
    public static final String KEY_IMAGE_LIST = "imageList";
    public static final String KEY_CURRENT_INDEX = "currentIndex";
    private final int REQUEST_CODE_PICTURE = 1;
    private final int RESULT_CODE_LARGE_IMAGE = 1;
    //晒单图片最多选择四张
    private final int MAX_PIC = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        initData();
        inView();
        initListener();
    }
    private void initListener() {
        mBack.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        iv_choose_goods_pic.setOnClickListener(this);
        iv_comment_star_1.setOnClickListener(this);
        iv_comment_star_2.setOnClickListener(this);
        iv_comment_star_3.setOnClickListener(this);
        iv_comment_star_4.setOnClickListener(this);
        iv_comment_star_5.setOnClickListener(this);
        et_comment_content.addTextChangedListener(this);
    }
    private void inView() {
        mBack = (TextView) findViewById(R.id.app_title_action);
        tv_submit = (TextView) findViewById(R.id.app_title_back);
        et_comment_content = (EditText) findViewById(R.id.et_comment_content);
        iv_choose_goods_pic = (ImageView) findViewById(R.id.iv_choose_goods_pic);
        hsv_comment_imgs = (HorizontalScrollView) findViewById(R.id.hsv_comment_imgs);
        starList.add(iv_comment_star_1 = (ImageView) findViewById(R.id.iv_comment_star_1));
        starList.add(iv_comment_star_2 = (ImageView) findViewById(R.id.iv_comment_star_2));
        starList.add(iv_comment_star_3 = (ImageView) findViewById(R.id.iv_comment_star_3));
        starList.add(iv_comment_star_4 = (ImageView) findViewById(R.id.iv_comment_star_4));
        starList.add(iv_comment_star_5 = (ImageView) findViewById(R.id.iv_comment_star_5));
    }
    private void initData() {
        starList = new ArrayList<>();
        imageUrls = new ArrayList<>();
        currentStarCount = 5;//默认为五星好评
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KeyBoardManager.isShouldHideInput(v, ev)) {
                if (manager != null) {
                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //清除临时压缩图片文件
        CleanCacheManager.cleanExternalCache(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_title_action:
                if (validateComment()) {

                }
                break;
            case R.id.app_title_back:
                //评价提交
                finish();
                break;
            case R.id.iv_choose_goods_pic:
                //检查是否有打开照相机和文件读写的权限
                if (PermissionCheckUtil.checkCameraAndExternalStoragePermission(this))
                    //权限已经开启, 调出图片选择界面
                    MultiImageSelector.create().count(MAX_PIC - imageUrls.size()).start(this, REQUEST_CODE_PICTURE);
                break;
            case R.id.iv_comment_star_1:
                currentStarCount = 1;
                break;
            case R.id.iv_comment_star_2:
                currentStarCount = 2;
                break;
            case R.id.iv_comment_star_3:
                currentStarCount = 3;
                break;
            case R.id.iv_comment_star_4:
                currentStarCount = 4;
                break;
            case R.id.iv_comment_star_5:
                currentStarCount = 5;
                break;
            default:
                break;
        }
        for (int i = 0, len = starList.size(); i < len; i++) {
            starList.get(i).setImageResource(i < currentStarCount ? R.mipmap.icon_comment_star_red : R.mipmap.icon_comment_star_gray);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限允许
            } else {
                openSettingActivity(this, "您没有打开相机或文件存储权限，请在设置中打开授权");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_PICTURE) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
//                LogUtils.d("图片集合"+path);
                for (int i = 0; i <path.size() ; i++) {
                    File file = new File(path.get(i));
                    OkHttpUtils.post().tag(this)
                            .url(Url.POSTPIC).addFile("file",file.getName(),file).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                        }
                        @Override
                        public void onResponse(String response, int id) {
                            LogUtils.d("图片集合"+response);
                        }
                    });
                }
                imageUrls.addAll(path);
                handleCommentPicList(imageUrls, false);
            }
        } else if (resultCode == RESULT_CODE_LARGE_IMAGE) {
            //晒单大图页返回, 重新设置晒单图片
            handleCommentPicList(imageUrls = data.getStringArrayListExtra(KEY_IMAGE_LIST), true);
        }
    }
    /**
     * 处理选择的评价图片
     *
     * @param paths      图片的路径集合
     * @param isFromBack 是否来自LargeImageActivity返回
     */
    private void handleCommentPicList(final List<String> paths, boolean isFromBack) {
        LinearLayout rootview = new LinearLayout(this);
        View commentView;
        SimpleDraweeView sdv_pic;
        for (int i = 0, len = paths.size(); i < len; i++) {
            commentView = getLayoutInflater().inflate(R.layout.order_comment_pic_item, null);
            sdv_pic = (SimpleDraweeView) commentView.findViewById(R.id.sdv_pic);
            File file = new File(paths.get(i));
            if (isFromBack) {
                //来自LargeImageActivity
                OkHttpUtils.post().tag(this)
                        .url(Url.POSTPIC).addFile("file",file.getName(),file).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d("图片集合"+response);
                        if (mPath!=null) {
                            mPath.clear();
                        }
                        mPath.add(response.substring(8, response.length()));
                    }
                });
                sdv_pic.setImageURI(Uri.parse("file://" + paths.get(i)));
            } else {
                //来自图片选择器
                String path = FileUtils.getCachePath(this);//获取app缓存路径来存放临时图片
                BitmapUtils.compressImage(paths.get(i), path, 95);
                sdv_pic.setImageURI(Uri.parse("file://" + path));
                imageUrls.set(i, path);
            }
            final int finalI = i;
            sdv_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击HorizontalScrollView里的晒单图进入图片详情页
                    Intent intent = new Intent(EvaluateActivity.this,CommentImageActivity.class);
                    intent.putExtra(KEY_CURRENT_INDEX, finalI);
                    intent.putStringArrayListExtra(KEY_IMAGE_LIST, (ArrayList<String>) paths);
                    startActivityForResult(intent, REQUEST_CODE_PICTURE);
                }
            });
            AutoUtils.auto(commentView);
            rootview.addView(commentView);
        }
        hsv_comment_imgs.removeAllViews();
        hsv_comment_imgs.addView(rootview);
    }
    /**
     * 评价内容验证
     */
    private boolean validateComment() {
        String content = et_comment_content.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(EvaluateActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String content = et_comment_content.getText().toString();
        if (content.length() >= 255) {
            Toast.makeText(EvaluateActivity.this, "评论内容不能多于255个字符", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
    }
    private void openSettingActivity(final Activity activity, String message) {
        showMessageOKCancel(activity, message, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //打开权限设置页面
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                startActivity(intent);
            }
        });
    }
    private void showMessageOKCancel(Activity context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    private void post(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("shopId", "");
        hashMap.put("content", "");
        hashMap.put("zuodanId", "");
        hashMap.put("imgUrl", "");
        hashMap.put("gradedValue", "");
    }
}
