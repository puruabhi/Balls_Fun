package com.example.android.balls_fun;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.TranslateAnimation;

/**
 * Created by abhishek on 03-02-2017.
 */

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final String TAG = MainGamePanel.class.getSimpleName();

    private MainThread thread;
    private Ball ball;

    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);
        // create ball and load bitmap
        ball = new Ball(BitmapFactory.decodeResource(getResources(),R.drawable.ball),50,50);
        // create the game loop thread
        thread = new MainThread(getHolder(),this);
        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        while(retry){
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // delegating event handling to the ball
            ball.handleActionDown((int)event.getX(), (int)event.getY());
            if (event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity) getContext()).finish();
            } else {
                Log.d(TAG, "X: " + getX() + ", Y: " + getY());
            }
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // the gestures
            if (ball.isTouched()) {
                // the droid was picked up and is being dragged
                ball.setX((int)event.getX());
                ball.setY((int)event.getY());
            }
        } if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            if (ball.isTouched()) {
                ball.setTouched(false);
            }
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        ball.draw(canvas);
    }
}
