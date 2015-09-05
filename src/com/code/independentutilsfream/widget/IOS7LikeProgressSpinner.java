package com.code.independentutilsfream.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
public class IOS7LikeProgressSpinner extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    private static final String TAG = IOS7LikeProgressSpinner.class.getSimpleName();
    private SurfaceHolder mSurfaceHolder;
    private Thread thread = null;
    private float canvasWidth, canvasHeight, circleRadius;
    private int centerX , centerY;
    private int sleepTime = 5;
    private float counter = 1;
    private Paint animationPaint;
    private RectF arc;
    private Long initTime;

    public IOS7LikeProgressSpinner(Context context) {
        super(context);
        init();
    }

    public IOS7LikeProgressSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IOS7LikeProgressSpinner(Context context, AttributeSet attrs, int defStyle, SurfaceHolder mSurfaceHolder) {
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
        animationPaint.setStyle(Paint.Style.STROKE);
//        animationPaint.setStrokeWidth(9);
        animationPaint.setStrokeWidth(7);
//        animationPaint.setColor(Color.rgb(21,122,251));
        animationPaint.setColor(Color.LTGRAY);
        animationPaint.setAntiAlias(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int canvasWidth, int canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        centerX = canvasWidth / 2;
        centerY = canvasHeight / 2;
        circleRadius =canvasWidth*.45f;
        arc = new RectF(centerX-circleRadius, centerY-circleRadius, centerX+circleRadius, centerY+circleRadius);
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
            counter = counter < 360 ? counter+3.0f : 1.5f;
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
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
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
        canvas.save();
        canvas.rotate(counter, canvasWidth * .5f, canvasHeight * .5f);
        canvas.drawArc(arc, -90, 320, false, animationPaint);
        canvas.restore();
    }
}