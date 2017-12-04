package com.example.ddm.appui.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by Hcw on 2016/12/16.
 *
 */
public class ViewHolder implements View.OnClickListener {
    private final String mTag = getClass().getName();
    private SparseArray<View> mSparseArray = new SparseArray<>();
    private View mConvertView;
    private int mPosition;
    private IAdapterClickBack mAdapterClickBack;

    public View getConvertView() {
        return mConvertView;
    }

    public ViewHolder(IAdapterClickBack onClickBack, View convertView) {
        mAdapterClickBack = onClickBack;
        mConvertView = convertView;
        if (mConvertView != null) {
            mConvertView.setTag(this);
        }
    }

    public ViewHolder setAdapter(int viewId, BaseAdapter adapter) {
        AdapterView adapterView = getView(viewId);
        if (adapterView != null) {
            adapterView.setAdapter(adapter);
        }
        return this;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    private <T extends View> T findViewById(int viewId) {
        if (mConvertView == null) {
            Log.e(mTag, "Error, mConvertView is null !!!");
            return null;
        }
        View view = mConvertView.findViewById(viewId);
        if (view == null) {
            Log.e(mTag, "Error, viewId is not found !!!");
            return null;
        }
        mSparseArray.put(viewId, view);
        return (T) view;
    }

    public <T extends View> T getView(int viewId) {
        View view = mSparseArray.get(viewId);
        if (view != null) {
            return (T) view;
        }
        findViewById(viewId);
        return (T) mSparseArray.get(viewId);
    }

    public ViewHolder setText(int text, int viewId) {
        View view = getView(viewId);
        if (view != null && view instanceof TextView) {
            ((TextView) view).setText(text);
        }
        return this;
    }

    public ViewHolder setText(int viewId, CharSequence text) {
        View view = getView(viewId);
        if (view != null && view instanceof TextView) {
            ((TextView) view).setText(text);
        }
        return this;
    }

    public ViewHolder setText(CharSequence text, int viewId) {
        View view = getView(viewId);
        if (view != null && view instanceof TextView) {
            ((TextView) view).setText(text);
        }
        return this;
    }

    public ViewHolder setText(String text, int viewId) {
        View view = getView(viewId);
        if (view != null && view instanceof TextView) {
            ((TextView) view).setText(text);
        }
        return this;
    }

    public ViewHolder setBackgroundRes(int resId, int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundColor(resId);
        }
        return this;
    }

    public ViewHolder setChecked(boolean isCheck, int viewId) {
//        View view = getView(viewId);
//        if (isCheck) {
//            view.setBackgroundResource(R.mipmap.checked);
//        } else {
//            view.setBackgroundResource(R.mipmap.nochecked);
//        }

        return this;
    }

    public ViewHolder setColorChecked(boolean isCheck, int viewId) {
//        View view = getView(viewId);
//        if (isCheck) {
//            view.setBackgroundResource(R.drawable.tv_checked_shape);
//        } else {
//            view.setBackgroundResource(R.drawable.tv_no_checked_shape);
//        }

        return this;
    }

    public ViewHolder setImageBitmap(Bitmap bitmap, int viewId) {
        if (bitmap == null) {
            Log.e(mTag, "Error, bitmap is null !!!");
            return this;
        }
        View view = getView(viewId);
        if (view != null && view instanceof ImageView) {
            ((ImageView) view).setImageBitmap(bitmap);
        }

        return this;
    }

    public ViewHolder setImageRes(int imgResId, int viewId) {
        View view = getView(viewId);
        if (view != null && view instanceof ImageView) {
            ((ImageView) view).setImageResource(imgResId);
        }
        return this;
    }
    public ViewHolder setImage(ImageView view, int viewId) {
        view.setImageResource(viewId);
        return this;
    }

    public ViewHolder setOnclickListener(int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(this);
        }
        return this;
    }

    /**
     * 处理控件的显示与隐藏
     *
     * @param visible true 则显示，false 则 Gone
     * @param viewId  ：
     * @return ：
     */
    public ViewHolder setVisible(boolean visible, int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }

        return this;
    }


    @Override
    public void onClick(View v) {
        if (mAdapterClickBack != null) {
            mAdapterClickBack.onClickBack(v, mPosition, this);
        }
    }

    ViewHolder mHolder;

    public void playVideo(String path, int Id) {
        View view = getView(Id);
        if (view != null && view instanceof VideoView) {
            ((VideoView) view).setVideoPath(path);
            ((VideoView) view).seekTo(0);
            ((VideoView) view).start();
        }

    }
}
