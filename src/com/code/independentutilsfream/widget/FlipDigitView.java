package com.code.independentutilsfream.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * 浠挎敮浠樺疂鍙樺寲鏃朵細璺冲姩鐨勬暟瀛�
 * @author Jeremy He
 */
public class FlipDigitView extends SurfaceView implements Callback{
	
	private SurfaceHolder mSurfaceHolder;
	private Paint mTextPaint;
	private float mValue = 0f;
	
	private Thread mFlipThread = null;

	public FlipDigitView(Context context) {
		super(context, null, 0);
		init();
	}
	
	public FlipDigitView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		init();
	}
	
	public FlipDigitView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init(){
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		
		mTextPaint = new Paint();
		mTextPaint.setTextAlign(Paint.Align.CENTER);
		// TODO: 浠庡睘鎬ц幏鍙�
		mTextPaint.setTextSize(21);
		mTextPaint.setColor(Color.rgb(0xcc, 0x33, 0));
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		drawValue(mValue);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
	
	public void setValue(float value){
		mValue = value;
		mFlipThread = new FlipValueThread();
		mFlipThread.start();
	}
	
	private void drawValue(float value){
		Canvas canvas = mSurfaceHolder.lockCanvas();
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawColor(Color.argb(0, 255, 255, 255));
		
		final String valueStr = String.format("%,.2f", value);
		canvas.drawText(valueStr, 0, 0, mTextPaint);
		mSurfaceHolder.unlockCanvasAndPost(canvas);
	}
	
	private class FlipValueThread extends Thread {
		private final long flipTime = 1000;
		private final long sleepTime = 5;
		
		@Override
		public void run() {
			super.run();
			float value = 0;
			float step = mValue/flipTime*sleepTime;
			while(value<=mValue){
				value += step;
				if (value>mValue) {
					value = mValue;
				}
				drawValue(value);
				
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
