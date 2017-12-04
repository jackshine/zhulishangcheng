package com.example.ddm.appui.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;
/**
 * adapter 的基础类，没有复写 getView 方法
 */
public abstract class CommonBaseAdapter<T> extends BaseAdapter {
    protected List<T> mDataList = new ArrayList<>();
    protected LayoutInflater mInflater;
    protected Context context;

    public CommonBaseAdapter(Context context, List<T> dataList) {
        this.context = context;
        if (dataList != null) {
            mDataList.addAll(dataList);
        }
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        T data = null;
        if (position >= 0 && position < getCount()) {
            data = mDataList.get(position);
        }
        return data;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(List<T> data) {
        mDataList.clear();
        if (data != null) {
            mDataList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void add(T data){
        if(data != null){
            mDataList.add(data);
            notifyDataSetChanged();
        }
    }
    public void add(List<T> data){
        if(data != null){
            mDataList.addAll(data);
            notifyDataSetChanged();
        }
    }
    public void appendStart(T data){
        if(data != null){
            if(mDataList.contains(data)){
                return;
            }else {
                mDataList.add(0,data);
                notifyDataSetChanged();
            }
        }
    }
    public void appendStart(List<T> data){
        if(data != null){
            mDataList.removeAll(data);
            mDataList.removeAll(data);
            mDataList.addAll(0,data);
            notifyDataSetChanged();
        }
    }

    public void removeOne(T data){
        if(data != null){
            mDataList.remove(data);
            notifyDataSetChanged();
        }
    }
}
