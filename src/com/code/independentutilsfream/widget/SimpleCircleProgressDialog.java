package com.code.independentutilsfream.widget;

import com.code.independentutilsfream.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.LinearLayout;

public class SimpleCircleProgressDialog extends Dialog {
    private static final String TAG = SimpleCircleProgressDialog.class.getSimpleName();

    public SimpleCircleProgressDialog(Context context) {
        super(context, R.style.Theme_CustomDialog);
        init(context);
    }

    public SimpleCircleProgressDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    public SimpleCircleProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public void init(Context context){
    	
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.progress_simple_circle,null);
        this.setCanceledOnTouchOutside(false);
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        this.getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.setContentView(layout);
    }
}