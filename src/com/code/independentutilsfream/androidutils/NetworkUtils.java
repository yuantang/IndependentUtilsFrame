package com.code.independentutilsfream.androidutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

	/**
	 * 当前网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		if (SystemUtils.checkPermissions(context, "android.permission.INTERNET")) {
			ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cManager.getActiveNetworkInfo();
			if (info != null && info.isAvailable()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 当前网络是否为WIFI
	 * @param context
	 * @return
	 */
	public static boolean isNetworkTypeWifi(Context context) {

		if (SystemUtils.checkPermissions(context, "android.permission.INTERNET")) {
			ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cManager.getActiveNetworkInfo();

			if (info != null && info.isAvailable() && info.getTypeName().equals("WIFI")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 当前网络类型
	 * @param paramContext
	 * @return
	 */
    public static String getNetworkType(Context paramContext) {
    	String type = "unknow";
    	ConnectivityManager connectivityManager = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    	if (networkInfo == null) {
    		return type;
    	}
    	
    	int nType = networkInfo.getType();
    	if (nType == ConnectivityManager.TYPE_MOBILE) {
    		String extraInfo = networkInfo.getExtraInfo();
    		type = extraInfo;
    	} else if (nType == ConnectivityManager.TYPE_WIFI) {
    		type = "wifi";
    	}
    	return type;
	}

}
