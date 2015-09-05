package com.code.independentutilsfream.bitmap;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

 
public class BitmapUtils {

	public static Bitmap	drawableToBitmap1(Drawable drawable){
		Bitmap bitmap=Bitmap.createBitmap(drawable.getMinimumWidth(), 
				drawable.getMinimumHeight(),  
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
						: Bitmap.Config.RGB_565);

		return bitmap;
	}
}

