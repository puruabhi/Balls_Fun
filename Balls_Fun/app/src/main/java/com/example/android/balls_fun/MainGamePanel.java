package com.example.android.balls_fun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.CursorAnchorInfo;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by abhishek on 03-02-2017.
 */

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final String TAG = MainGamePanel.class.getSimpleName();
    private String avgFPS;

    public void setAvgFPS(String avgFPS) {
        this.avgFPS = avgFPS;
    }

    private MainThread thread;
    private Ball ball;
    private ArrayList<MovingBall> movingBallArrayList;

    private int numberOfBalls;

    Context context;

    public MainGamePanel(Context context, int numberOfBalls) {
        super(context);
        this.context = context;
        this.numberOfBalls = numberOfBalls;
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);
        // create ball and load bitmap
        // create the game loop thread
        thread = new MainThread(getHolder(),this);
        // make the GamePanel focusable so it can handle events
        setFocusable(true);
        movingBallArrayList = new ArrayList<>();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ball = new Ball(getBitmap(R.drawable.ball4),50,50);
        for(int i=0;i<numberOfBalls;i++){
            MovingBall movingBall = new MovingBall(getBitmap(R.drawable.ball5),20,getHeight()/2,getWidth(),getHeight());
            movingBallArrayList.add(movingBall);
        }
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
        int x,y;
        // set x so that image does not go out of screen
        if(event.getX()-(ball.getBitmap().getWidth()/2)<0)x = ball.getBitmap().getWidth()/2;
        else if(event.getX()+(ball.getBitmap().getWidth()/2)>getWidth())x = getWidth()-(ball.getBitmap().getWidth()/2);
        else x = (int)event.getX();

        // set x so that image does not go out of screen
        if(event.getY()-(ball.getBitmap().getHeight()/2)<0)y = ball.getBitmap().getHeight()/2;
        else if(event.getY()+(ball.getBitmap().getHeight()/2)>getHeight())y = getHeight()-(ball.getBitmap().getHeight()/2);
        else y = (int)event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // delegating event handling to the ball
            ball.handleActionDown(x, y);
            if (event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity) getContext()).finish();
            } else {
                Log.d(TAG, "X: " + getX() + ", Y: " + getY());
            }
            //Toast.makeText(context,"Width: "+movingBallArrayList.get(0).getWIDTH()+" Height: "
            //        +movingBallArrayList.get(0).getHEIGHT(),Toast.LENGTH_SHORT).show();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // the gestures
            if (ball.isTouched()) {
                // the droid was picked up and is being dragged
                ball.setX(x);
                ball.setY(y);
            }
        } if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            if (ball.isTouched()) {
                ball.setTouched(false);
            }
        }

        return true;
    }

    public void render(Canvas canvas){
        if(canvas!=null) {
            canvas.drawColor(Color.BLACK);
            ball.draw(canvas);
            //System.out.println(getWidth());
            for(int i=0;i<movingBallArrayList.size();i++) {
                if (!checkCollision(movingBallArrayList.get(i))) {
                    movingBallArrayList.get(i).move();
                }
                movingBallArrayList.get(i).draw(canvas);
            }
            displayFps(canvas, avgFPS);
        }
    }

    private void displayFps(Canvas canvas, String fps){
        if(canvas != null && fps != null){
            Paint paint = new Paint();
            paint.setARGB(255,255,255,255);
            canvas.drawText(fps, this.getWidth() - 100, 20, paint);
        }
    }

    public void update(){

    }

    private Bitmap getBitmap(int drawableRes) {
        Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private boolean checkCollision(MovingBall movingBall){
        boolean check = false;
        float distance = getDistance(movingBall.getX(),movingBall.getY(),ball.getX(),ball.getY());
        if(distance <= movingBall.getRadius()+ball.getRadius()) check = true;
        return check;
    }

    private float getDistance(int x1,int y1, int x2, int y2){
        return (float)Math.sqrt(Math.pow(x1-x2,2)+ Math.pow(y1-y2,2));
    }

    public void destroyThread(){
        Activity StartGameActivity = (Activity)context;
        StartGameActivity.finish();
        boolean retry = true;
        while(retry){
            try {
                thread.stop();
                retry = false;
            }catch (Exception e){}
        }
    }
}
