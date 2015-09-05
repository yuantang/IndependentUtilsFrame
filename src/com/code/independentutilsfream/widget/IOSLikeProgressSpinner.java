package com.code.independentutilsfream.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
public class IOSLikeProgressSpinner extends SurfaceView implements SurfaceHolder.Callback, Runnable{
	private static final String TAG = IOSLikeProgressSpinner.class.getSimpleName();
	private SurfaceHolder mSurfaceHolder;
	private Thread thread = null;
	private int canvasWidth, canvasHeight;
	private int pinWidth = 90, pinHeight = 20 , pinMargin=70;
	private int sleepTime = 90;
	private int counter = 1;
	private Paint animationPaint;
	private RectF piece;
	private Long initTime;

	public IOSLikeProgressSpinner(Context context) {
		super(context);
		init();
	}

	public IOSLikeProgressSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public IOSLikeProgressSpinner(Context context, AttributeSet attrs, int defStyle, SurfaceHolder mSurfaceHolder) {
		super(context, attrs, defStyle);
		this.mSurfaceHolder = mSurfaceHolder;
		init();
	}

	public void init(){
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
		setZOrderOnTop(true);
		animationPaint = new Paint();
		animationPaint.setAntiAlias(true);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int canvasWidth, int canvasHeight) {
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		pinWidth = canvasWidth / 32 * 9;
		pinHeight = pinWidth / 5;
		pinMargin = canvasWidth / 32 * 7;
		piece = new RectF(canvasWidth*.5f+pinMargin,canvasHeight*.5f-pinHeight*.5f,canvasWidth*.5f+pinMargin+pinWidth,canvasHeight*.5f+pinHeight*.5f);

		Log.v("debug", "width:" + canvasWidth + " height:" + canvasHeight);
		thread = new Thread(this);
		thread.start();
		initTime = System.currentTimeMillis();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread = null;
	}

	@Override
	public void run() {
		while(thread != null){
			tick();
			counter = counter < 12 ? counter+1 : 1;
			try {
				Thread.sleep(sleepTime);
			}
			catch (InterruptedException e) {
			}
		}
	}

	public void tick() {
		Canvas canvas = mSurfaceHolder.lockCanvas();
		if (canvas != null){
			canvas.drawColor(Color.argb(0, 255, 255, 255));
			drawLoadingAnimation(canvas);
			onDraw(canvas);
			mSurfaceHolder.unlockCanvasAndPost(canvas);
		}
	}
	public void drawLoadingAnimation(Canvas canvas){
		long nowTime = System.currentTimeMillis();
		if( nowTime - initTime < 300){
			return;
		}
		for(int i=0;i<12;i++){
			int base = 80;
			int rate = 8;
			int index = (i+counter)%12;
			animationPaint.setColor(Color.argb(200, base+rate*index, base+rate*index, base+rate*index));
			canvas.save();
			canvas.rotate(-30*i,canvasWidth*.5f,canvasHeight*.5f);
			canvas.drawRoundRect(piece, 100, 100, animationPaint);
			canvas.restore();
		}
	}
}