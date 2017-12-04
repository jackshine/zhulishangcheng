package com.example.ddm.appui.view;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;
import com.example.ddm.R;
import java.util.Date;
/**
 * 带滚动监听的scrollview
 *
 */
public class ObservableScrollView extends ScrollView  {
	public interface ScrollViewListener {
		void onScrollChanged(ObservableScrollView scrollView, int x, int y,
                             int oldx, int oldy);
		void onScroll(int scrollY);
	}
	private ScrollViewListener scrollViewListener = null;

	public ObservableScrollView(Context context) {
		super(context);
	}
	public ObservableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev);
}
	class YScrollDetector extends GestureDetector.SimpleOnGestureListener
	{
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
								float distanceX, float distanceY)
		{
			if (Math.abs(distanceY) > Math.abs(distanceX))
			{
				return true;
			}
			return false;
		}
	}
	public void setScrollViewListener(ScrollViewListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}
	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (scrollViewListener != null) {
			scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
			scrollViewListener.onScroll(y);
		}
	}


}