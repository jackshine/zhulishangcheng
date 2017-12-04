package com.example.ddm.appui.adapter.recycle;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public abstract class CommenRecycleAdapter<T> extends ListBaseAdapter<T>{
    private int mLayoutId;
    public CommenRecycleAdapter(Context context, int layoutId) {
        super(context);
        mLayoutId = layoutId;
    }
    public CommenRecycleAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        T item = getDataList().get(position);
        setListener(holder,holder.itemView);
        holder.setPosition(position);
        setViewData(holder, item, position);
    }
    public abstract void setViewData(SuperViewHolder holder, T item, int position);

    public abstract void setListener(SuperViewHolder holder,View view);
}
