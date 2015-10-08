package com.code.independentutilsfream.utils;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
/**
 * 
 * @author tom
 *
 */
public class AppUtils {

	private AppUtils() {
		throw new AssertionError();
	}
	/**
	 * 安装一个apk
	 * @param file apk文件
	 * @param mActivity 当前Activity
	 */
	public static void installApk(File file, Activity mActivity) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		mActivity.startActivity(intent);
		mActivity.finish();
	}
	/** 获取当前应用程序的版本号 */
	public static String getVersion(Context context) {
		// 获取系统的包管理的服务
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);// 得到当前包的相关信息
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";// 不可能发生
		}
	}

	/**
	 * 应用是否在后台运行
	 * @param context
	 * @return
	 */
	public static boolean isApplicationInBackground(Context context) {
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> taskList = am.getRunningTasks(1);
		if (taskList != null && !taskList.isEmpty()) {
			ComponentName topActivity = taskList.get(0).topActivity;
			if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
	}
}
