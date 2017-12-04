package com.example.ddm.appui.adapter.recycle;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;


/**
 * ViewHolder基类
 */
public class SuperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    private SparseArray<View> views;
    private int mPosition;
    private ISuperHolderCallBack mAdapterClickBack;


    public SuperViewHolder(ISuperHolderCallBack onClickBack,View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
        mAdapterClickBack = onClickBack;
    }

    public int getmPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }



    public SuperViewHolder setOnClickListener(int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(this);
        }
        return this;
    }

    public LRecyclerView setLRecyclerViewAdapter(int viewId, LRecyclerViewAdapter adapter,
                                                 RecyclerView.LayoutManager manager,
                                                 DividerDecoration divider) {
        View view = getView(viewId);
        if (view != null && view instanceof LRecyclerView) {
            ((LRecyclerView) view).setLayoutManager(manager);
            ((LRecyclerView) view).addItemDecoration(divider);
            ((LRecyclerView) view).setAdapter(adapter);
            return (LRecyclerView) view;
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        if (mAdapterClickBack != null) {
            mAdapterClickBack.onClickBack(this, mPosition,view);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public SuperViewHolder setText(String text, int viewId) {
        View view = getView(viewId);
        if (view != null && view instanceof TextView) {
            ((TextView) view).setText(text);
        }
        return this;
    }
    public SuperViewHolder setTextColor(int color, int viewId) {
        View view = getView(viewId);
        if (view != null && view instanceof TextView) {
            ((TextView) view).setTextColor(color);
        }
        return this;
    }

    public String getText(int viewId) {
        View view = getView(viewId);
        if (view != null && view instanceof TextView) {
            return ((TextView) view).getText().toString();
        }
        return "";
    }

    public SuperViewHolder setImgRes(int imgResId, int viewId) {
        View view = getView(viewId);
        if (view != null && view instanceof ImageView) {
            ((ImageView) view).setImageResource(imgResId);
        }
        return this;
    }

    public SuperViewHolder setBackgroupDrawable(Drawable drawable, int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundDrawable(drawable);
        }
        return this;
    }

//    public SuperViewHolder setGlideImageView(String imgUrl, int viewId) {
//        View view = getView(viewId);
//        if (view != null && view instanceof GlideImageView) {
//            ((GlideImageView) view).loadImage(imgUrl, R.mipmap.ic_launcher);
//        }
//        return this;
//    }
    public SuperViewHolder setVisible(boolean visible, int viewId) {
        View view = getView(viewId);
        if (view != null) {
            if (visible) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
        return this;
    }


    public SuperViewHolder setOnClickLisener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return this;
    }
}
