package com.code.independentutilsfream.widget;


import com.code.independentutilsfream.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;


public class IOS7LikeProgressDialog extends Dialog {
	private static final String TAG = IOS7LikeProgressDialog.class.getSimpleName();

	public static final int THEME_BLACK = 0;
	public static final int THEME_WHITE = 1;

	private LinearLayout layout;
	private TextView textView;
	private int ios7Color;
	private int iosColor;

	public IOS7LikeProgressDialog(Context context) {
		super(context, R.style.Theme_CustomDialog);
		init(context);
	}

	public IOS7LikeProgressDialog(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	public IOS7LikeProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context);
	}

	public void setText(String text){
		textView.setText(text);
	}

	public void setTextSize(float size){
		textView.setTextSize(size);
	}

	public void setTextVisible(boolean visible){
		if (visible) {
			textView.setVisibility(View.VISIBLE);
		} else {
			textView.setVisibility(View.GONE);
		}
	}

	public void setTheme(int theme){
		if (theme == THEME_BLACK) {
			layout.setBackgroundResource(R.drawable.round);
			textView.setTextColor(iosColor);
		} else {
			layout.setBackgroundResource(R.drawable.ios7round);
			textView.setTextColor(ios7Color);
		}
	}

	public void init(Context context){
		ios7Color = context.getResources().getColor(R.color.ios7_text_color);
		iosColor = context.getResources().getColor(R.color.ios_text_color);

		LayoutInflater inflater = LayoutInflater.from(context);
		layout = (LinearLayout)inflater.inflate(R.layout.ios7progress,null);
		textView = (TextView)layout.findViewById(R.id.textView);
		this.setCanceledOnTouchOutside(false);
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		//        this.getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		this.setContentView(layout);
	}
}