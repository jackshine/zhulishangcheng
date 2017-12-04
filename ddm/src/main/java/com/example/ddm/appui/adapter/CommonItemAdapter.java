package com.example.ddm.appui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用的Adapter
 */
public abstract class CommonItemAdapter<T> extends CommonBaseAdapter<T>  {
    private int mLayoutId;
    public CommonItemAdapter(Context context, List<T> dataList) {
        super(context,dataList);
    }

    public CommonItemAdapter(Context context, List<T> dataList, int layoutId) {
        super(context,dataList);
        mLayoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(mLayoutId, parent, false);
            holder = getViewHolder(convertView);
            setEvent(holder,convertView);
            setViewDimen(convertView);
        }
        holder.setPosition(position); // very important
        setViewData(holder,getItem(position),position);
        return convertView;
    }

    public void setViewDimen(View convertView) {

    }

    public abstract void setViewData(ViewHolder holder, T item, int position);

    /**设置事件（点击事件，触摸事件，滑动事件等）
     * @param holder ：
     * @param convertView ：
     */
    protected void setEvent(ViewHolder holder, View convertView) {

    }
    /**子类和父类的ViewHolder的区别化处理，子类需要处理点击事件
     * 而父类不需要，从而提高代码的逻辑合理性
     * @param convertView ：
     * @return ：ViewHolder的对象
     */
    protected ViewHolder getViewHolder(View convertView) {
        return new ViewHolder(null, convertView);
    }



}
