package com.code.independentutilsfream.androidutils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class BitmapUtils {
	
	public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
	
	public static Bitmap decodeResource(Resources res, int resId,int reqWidth, int reqHeight) {   
	    // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小   
	    final BitmapFactory.Options options = new BitmapFactory.Options();   
	    options.inJustDecodeBounds = true;   
	    BitmapFactory.decodeResource(res, resId, options);   
	    // 调用上面定义的方法计算inSampleSize值   
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);   
	    // 使用获取到的inSampleSize值再次解析图片   
	    options.inJustDecodeBounds = false;   
	    return BitmapFactory.decodeResource(res, resId, options);   
	} 
	
	/**
	 * 根据源图片产生一张灰色的图片
	 */
	public static Bitmap toGrey(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(faceIconGreyBitmap);
		Paint paint = new Paint();
		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0);
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
		paint.setColorFilter(colorMatrixFilter);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return faceIconGreyBitmap;
	}
}
