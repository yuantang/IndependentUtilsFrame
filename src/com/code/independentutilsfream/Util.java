package com.code.independentutilsfream;
import java.io.File;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

/**
 * 常用工具类
 *  
 */
public class Util {
	
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
	
	/**
	 * 以最省内存的方式读取本地资源的图片 
	 * @param context
	 * @param resId 图片资源id
	 * @return Bitmap
	 */
	public static Bitmap readBitMap(Context context, int resId){ 
		BitmapFactory.Options options = new BitmapFactory.Options(); 
		options.inPreferredConfig = Bitmap.Config.RGB_565; 
		options.inPurgeable = true; 
		options.inInputShareable = true; 
		InputStream is = context.getResources().openRawResource(resId); //获取资源图片的输入流
		return BitmapFactory.decodeStream(is,null, options); 
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
	 * 检查是否有能上网的网络连接存在
	 * @param mContext 当前Activity
	 * @return
	 */
	public static boolean isNetWorkConnected(Context mContext){
	    //connectivity [,k?nek'tiv?ti]n.[数][电][计]连通性
		ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo info = manager.getActiveNetworkInfo();
		return info!=null&&info.isConnected();
	}
	
	/** 获取显示器的密度 */
	public static float getDisplayDensity(Context context) {  
        return context.getResources().getDisplayMetrics().density;  
    }
	
	 /** 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }    
    
    /** 根据手机的分辨率从 px(像素) 的单位 转成为 dp */
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }
	
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}
}
