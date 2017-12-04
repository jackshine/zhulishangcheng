package com.example.ddm.appui.utils;
import android.util.Log;
import android.view.View;
public class FindViewUtils {
    //获取该类的名字
    private final String mTag = getClass().getName();
    private View mView;

    public FindViewUtils(View view) {
        mView = view;
    }

    /**
     * @param viewId ：控件的id
     * @param <T> ：泛型
     * @return ： viewId代表的view
     */
    public <T extends View> T findViewById(int viewId) {
        if (mView == null) {
            Log.e(mTag, "findViewById is Null !!!");
            return null;
        }
        View view = mView.findViewById(viewId);
        return (T) view;
    }
}
