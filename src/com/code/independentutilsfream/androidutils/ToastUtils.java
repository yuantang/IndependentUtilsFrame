package com.code.independentutilsfream.androidutils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	public static void showShortToast(Context c,String msg){
		Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
	}
	
	public static void showLongToast(Context c,String msg){
		Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
	}
	
	
}
