package com.example.ddm.appui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 *
 */
public class MyLetterView extends View {


	private Paint mPaint;
	private boolean isShowBg = false;
	private OnSlidingListener mOnSlidingListener;
	private int choose = -1;
	private TextView mTvDialog;

	private String[] letter = {"", "", "", "#", "A", "B", "C", "D",
			"E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q",
			"R", "S", "T", "W", "X", "Y", "Z"};

	public MyLetterView(Context context) {
		super(context);
	}

	public MyLetterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	public MyLetterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	private void initPaint() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(26);
		mPaint.setColor(Color.parseColor("#8c8c8c"));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (isShowBg) {
			canvas.drawColor(Color.parseColor("#40000000"));
		}

		float singleHeight = getHeight() / letter.length;
		int width = getWidth();
		for (int i = 0; i < letter.length; i++) {
			String text = letter[i];

			float xPosition = width / 2 - mPaint.measureText(text) / 2;
			float yPosition = singleHeight * i + singleHeight;

			canvas.drawText(text, xPosition, yPosition, mPaint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();
		int position = (int) (event.getY() / getHeight() * letter.length);
		int oldChoose = choose;
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				isShowBg = true;
				if (oldChoose != position && mOnSlidingListener != null) {
					if (position > 0 && position < letter.length) {

						mOnSlidingListener.sliding(position,letter[position]);
						choose = position;
						if (mTvDialog != null) {
							mTvDialog.setVisibility(View.VISIBLE);
							mTvDialog.setText(letter[position]);
						}
					}
					invalidate();
				}
				break;

			case MotionEvent.ACTION_MOVE:

				isShowBg = true;
				if (oldChoose != position && mOnSlidingListener != null) {
					if (position >= 0 && position < letter.length) {
						mOnSlidingListener.sliding(position,letter[position]);
						choose = position;
						if (mTvDialog != null) {
							mTvDialog.setVisibility(View.VISIBLE);
							mTvDialog.setText(letter[position]);
						}
					}
					invalidate();
				}
				break;

			case MotionEvent.ACTION_UP:
				isShowBg = false;
				choose = -1;
				if (mTvDialog != null) {
					mTvDialog.setVisibility(View.GONE);
				}
				invalidate();
				break;
		}

		return true;
	}

	public void setOnSlidingListener(OnSlidingListener mOnSlidingListener) {
		this.mOnSlidingListener = mOnSlidingListener;
	}

	public interface OnSlidingListener {
		void sliding(int position,String s);
	}

	public void setTextView(TextView tvDialog) {
		mTvDialog = tvDialog;
	}
}
