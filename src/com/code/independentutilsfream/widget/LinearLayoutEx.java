package com.code.independentutilsfream.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 用于监测软键盘是否显示的一个布局，其activity需要在Manifest中设置windowSoftInputMode属性为android:windowSoftInputMode="adjustResize"
 * @author Al_Ways
 *
 */
public class LinearLayoutEx extends LinearLayout {

	private Context mContext;
	
	public LinearLayoutEx(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public LinearLayoutEx(Context context) {
		super(context);
		mContext = context;
	}
	
	private OnSoftKeyboardListener onSoftKeyboardListener;

	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		if (onSoftKeyboardListener != null) {
			final int newSpec = MeasureSpec.getSize(heightMeasureSpec);
			final int oldSpec = getMeasuredHeight();
			
			if (newSpec > oldSpec) {
				onSoftKeyboardListener.onHidden();
			} else if (newSpec < oldSpec) {
				onSoftKeyboardListener.onShown();
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public final void setOnSoftKeyboardListener(
			final OnSoftKeyboardListener listener) {
		this.onSoftKeyboardListener = listener;
	}

	public interface OnSoftKeyboardListener {
		public void onShown();
		public void onHidden();
	}

}
