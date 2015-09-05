package com.code.independentutilsfream.androidutils;

import android.app.AlertDialog;
import android.content.Context;

public class AlertDialogUtils {

	public static void showSimpleAlertDialog(Context context,String title,String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title)
		.setMessage(msg)
		.show();
	}

}
