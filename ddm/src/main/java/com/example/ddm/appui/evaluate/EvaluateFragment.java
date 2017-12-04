package com.example.ddm.appui.evaluate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 立即评价
 */
public class EvaluateFragment extends BaseFragment implements TextWatcher {
    private EditText et_comment_content;
    private TextView tv_submit;
    private ImageView iv_choose_goods_pic;
    private HorizontalScrollView hsv_comment_imgs;
    private ImageView iv_comment_star_1, iv_comment_star_2, iv_comment_star_3, iv_comment_star_4, iv_comment_star_5;
    private List<ImageView> starList;
    private List<String> imageUrls;//所有晒图图片路径
    private int currentStarCount;
    private InputMethodManager manager;

    public static final String KEY_IMAGE_LIST = "imageList";
    public static final String KEY_CURRENT_INDEX = "currentIndex";
    private final int REQUEST_CODE_PICTURE = 1;
    private final int RESULT_CODE_LARGE_IMAGE = 1;
    //晒单图片最多选择四张
    private final int MAX_PIC = 5;

    public EvaluateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evaluate, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
//
    }

    @Override
    protected void setListener() {
        super.setListener();
        tv_submit.setOnClickListener(this);
        iv_choose_goods_pic.setOnClickListener(this);
        iv_comment_star_1.setOnClickListener(this);
        iv_comment_star_2.setOnClickListener(this);
        iv_comment_star_3.setOnClickListener(this);
        iv_comment_star_4.setOnClickListener(this);
        iv_comment_star_5.setOnClickListener(this);
        et_comment_content.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
//            case R.id.tv_submit:
//                //评价提交
//                validateComment();
//                break;
//
//            case R.id.iv_choose_goods_pic:
//                //检查是否有打开照相机和文件读写的权限
//                if (PermissionCheckUtil.checkCameraAndExternalStoragePermission(this))
//                    //权限已经开启, 调出图片选择界面
//                    MultiImageSelector.create().count(MAX_PIC - imageUrls.size()).start(this, REQUEST_CODE_PICTURE);
//                break;

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
}
