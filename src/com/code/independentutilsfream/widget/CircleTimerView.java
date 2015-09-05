package com.code.independentutilsfream.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CircleTimerView extends SurfaceView implements SurfaceHolder.Callback{
    private static final String TAG = CircleTimerView.class.getSimpleName();
    private SurfaceHolder mSurfaceHolder;
    private float canvasWidth, canvasHeight, circleRadius;
    private int centerX , centerY;
    private RectF arc;
    
    private float strokeWidthRate = 0.03f;
    private Paint bgCirclePaint;
    private Paint frontCirclePaint;
    
    private Thread prepareThread = null;
    private TimerThread timerThread = null;
    private int sleepTime = 5;
    
    private long totalTime = 30*60*1000;
    private long startTime = 0;
    private String title = "宸ヤ綔涓�..";
    private float titleSizeRate = 0.0714f;
    private Paint titlePaint;
    private float timeTextSizeRate = 0.2143f;
    private Paint timeTextPaint;
    private float timeTextHeigth;
    

    public CircleTimerView(Context context) {
        super(context);
        init();
    }

    public CircleTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleTimerView(Context context, AttributeSet attrs, int defStyle, SurfaceHolder mSurfaceHolder) {
        super(context, attrs, defStyle);
        this.mSurfaceHolder = mSurfaceHolder;
        init();
    }

    public void init(){
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);
        
        bgCirclePaint = new Paint();
        bgCirclePaint.setStyle(Paint.Style.STROKE);
        bgCirclePaint.setColor(Color.argb(128, 255, 255, 255));
        bgCirclePaint.setAntiAlias(true);
        
        frontCirclePaint = new Paint();
        frontCirclePaint.setStyle(Paint.Style.STROKE);
        frontCirclePaint.setColor(Color.rgb(255, 255, 255));
        frontCirclePaint.setAntiAlias(true);
        
        titlePaint = new Paint();
        titlePaint.setColor(Color.argb(128, 255, 255, 255));
        titlePaint.setTextAlign(Paint.Align.CENTER);
        
        timeTextPaint = new Paint();
        timeTextPaint.setColor(Color.rgb(255, 255, 255));
        timeTextPaint.setTextAlign(Paint.Align.CENTER);
    }
    
    public void setTime(long time){
    	if (timerThread != null) {
			timerThread = null;
		}
    	
    	this.totalTime = time;
    	prepareThread = new PrepareThread();
    	prepareThread.start();
    }
    
    public void setTitle(String title){
    	this.title = title;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    	Log.d(TAG, "CircleView-->surfaceCreated");
    	canvasWidth = getWidth();
    	canvasHeight = getHeight();
        centerX = (int) (canvasWidth / 2);
        centerY = (int) (canvasHeight / 2);
        circleRadius =canvasWidth*.45f;
        arc = new RectF(centerX-circleRadius, centerY-circleRadius, centerX+circleRadius, centerY+circleRadius);
        final float min = canvasWidth<canvasHeight?canvasWidth:canvasHeight;
        bgCirclePaint.setStrokeWidth(strokeWidthRate*min);
        frontCirclePaint.setStrokeWidth(strokeWidthRate*min);
        titlePaint.setTextSize(titleSizeRate*min);
        timeTextPaint.setTextSize(timeTextSizeRate*min);
        Rect bounds = new Rect();
        timeTextPaint.getTextBounds("30:00", 0, 1, bounds);
        timeTextHeigth = bounds.bottom-bounds.top;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int canvasWidth, int canvasHeight) {
    	Log.d(TAG, "CircleView-->surfaceChanged");
    	if (startTime != 0) {
    		timerThread = new TimerThread();
        	timerThread.start();
		}
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
		timerThread = null;
    	Log.d(TAG, "CircleView-->surfaceDestroyed");
    }
    
    public void startTimer(){
    	while(prepareThread != null);
    	startTime = System.currentTimeMillis();
    	timerThread = new TimerThread();
    	timerThread.start();
    }
    
    public void startTimerForce(){
    	prepareThread = null;
    	startTime = System.currentTimeMillis();
    	timerThread = new TimerThread();
    	timerThread.start();
    }
    
    public void continueTimer(long lastStartTime){
    	startTime = lastStartTime;
    }
    
    public long getStartTime(){
    	return startTime;
    }
    
    /**
     * 灏嗘椂闂磋浆鎹㈡垚鍒嗭細绉掑舰寮�
     * @param timeInMills
     * @return
     */
    private String formatTimeInMills(long timeInMills){
    	timeInMills = timeInMills/1000;
    	final int second = (int) (timeInMills%60);
    	final int minute = (int) (timeInMills/60);
    	
    	return String.format("%02d:%02d", minute,second);
    }

    /**
     * 缁樺埗鍑轰竴涓畬鏁寸殑鍦�
     * @author Al_Ways
     */
    private class PrepareThread extends Thread {
    	float sweepAngle = 0;
    	
    	@Override
    	public void run() {
    		super.run();
    		sleepThread(300);
    		
    		while(sweepAngle<360 && prepareThread == Thread.currentThread()){
    			drawPrepareCircle();
    			sleepThread(sleepTime);
    		}
    		
    		prepareThread = null;
    	}
    	
    	private void sleepThread(long time) {
    		try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    	
    	private void drawPrepareCircle(){
    		Canvas canvas = mSurfaceHolder.lockCanvas();
            if (canvas != null){
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                canvas.drawColor(Color.argb(0, 255, 255, 255));
                
                canvas.drawArc(arc, -90, 360, false, bgCirclePaint);
                canvas.drawText(title, centerX, centerY-canvasHeight/7, titlePaint);
                final String timeText = formatTimeInMills(totalTime);
                canvas.drawText(timeText, centerX, centerY+timeTextHeigth/2, timeTextPaint);
                
                sweepAngle = sweepAngle+2.5f*sleepTime;
                canvas.drawArc(arc, -90, sweepAngle, false, frontCirclePaint);
                
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
    	}
    }
    
    /**
     * 缁樺埗褰撳墠璁℃椂鎯呭喌
     */
    private class TimerThread extends Thread{
    	
    	private boolean stop = false;
    	private float angleStep;
    	
    	@Override
    	public void run() {
    		super.run();
    		angleStep = 360f/totalTime;
    		
    		while(!stop && timerThread == Thread.currentThread()){
    			drawTimer();
    			try {
    				Thread.sleep(sleepTime);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	
    	private void drawTimer(){
    		Canvas canvas = mSurfaceHolder.lockCanvas();
            if (canvas != null){
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                canvas.drawColor(Color.argb(0, 255, 255, 255));
                
                canvas.drawArc(arc, -90, 360, false, bgCirclePaint);
        		canvas.drawText(title, centerX, centerY-canvasHeight/7, titlePaint);
                
                drawTimerCircleAndText(canvas);
                
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            } else {
            	stop = true;
            }
    	}
    	
    	private void drawTimerCircleAndText(Canvas canvas){
    		final long now = System.currentTimeMillis();
            final long passTime = now-startTime;
            final float passAngle = passTime*angleStep;
            canvas.drawArc(arc, -90+passAngle, 360-passAngle, false, frontCirclePaint);
            
            final long restTime = totalTime-passTime;
            final String timeText = formatTimeInMills(restTime);
    		canvas.drawText(timeText, centerX, centerY+timeTextHeigth/2, timeTextPaint);
            
            if (passAngle>=360) {
				stop = true;
			}
    	}
    }
 }