package com.example.ddm.appui.adapter;
import android.view.View;
/**
 *
 */
public interface IAdapterClickBack {
    void onClickBack(View v, int position, ViewHolder holder);
    void onBack(String name);
}
