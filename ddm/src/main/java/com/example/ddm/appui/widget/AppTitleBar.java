package com.example.ddm.appui.widget;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.ddm.R;
/**
 * Created by Hcw on 2016/12/14 0014.
 *
 */

public class AppTitleBar extends LinearLayout {
    private TextView mBack;
    private TextView mTitle;
    private TextView mAction;
    private static final int DEFAULT_TITLE_COLOR = Color.BLACK;
    public AppTitleBar(Context context) {
        super(context);
        init(context, null);
    }
    public AppTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
    public AppTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }
    private void init(Context context, AttributeSet attr) {
        LayoutInflater.from(context).inflate(R.layout.app_title_bar, this);
        mBack = (TextView) findViewById(R.id.app_title_back);
        mTitle = (TextView) findViewById(R.id.app_title_text);
        mAction = (TextView) findViewById(R.id.app_title_action);
        TypedArray array = context.obtainStyledAttributes(attr,R.styleable.AppTitleBar);
        if (array != null) {
            Drawable background = array.getDrawable(R.styleable.AppTitleBar_bar_background);
            if (background != null) {
                this.setBackgroundDrawable(background);
            }
            String title = array.getString(R.styleable.AppTitleBar_bar_title);
            int color = array.getColor(R.styleable.AppTitleBar_bar_title_text_color, DEFAULT_TITLE_COLOR);
            if (!TextUtils.isEmpty(title)) {
                mTitle.setText(title);
                mTitle.setTextColor(color);
            }
        } else {
            this.setBackgroundColor(this.getResources().getColor(R.color.fragment_bg));
        }
        setTextAndImg(array, mBack, R.styleable.AppTitleBar_back_text, R.styleable.AppTitleBar_back_img);

        setTextAndImg(array, mAction, R.styleable.AppTitleBar_action_text, R.styleable.AppTitleBar_action_img);

        if (array != null) {
            array.recycle();
        }
    }
    private void setTextAndImg(TypedArray array, TextView textView, int textId, int imgId) {
        if (array != null) {
            Drawable drawable = array.getDrawable(imgId);
            String backText = array.getString(textId);
            if (!TextUtils.isEmpty(backText)) {
                textView.setText(backText);
            }
            setDrawable(drawable, textView);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.fragment_back_arrow);
            setDrawable(drawable, textView);
        }
    }
    private void setDrawable(Drawable drawable, TextView textView) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicWidth());
            textView.setCompoundDrawables(drawable, null, null, null);
        }
    }


    public TextView getAction() {
        return mAction;
    }

    public TextView getBack() {
        return mBack;
    }

    public TextView getTitle() {
        return mTitle;
    }

    public void setTitle(String text) {
        mTitle.setText(text);
    }

    public void setTitleSize(float size) {
        mTitle.setTextSize(size);
    }

    public void setTitle(int text) {
        mTitle.setText(text);
    }
    public void setBack(String text) {
        mBack.setText(text);
    }

    public void setBack(int text) {
        mBack.setText(text);
    }

}
