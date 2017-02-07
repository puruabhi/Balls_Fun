package com.example.android.balls_fun;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.CursorAnchorInfo;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Handler;

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
    private boolean gameOver = false;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

    int Seconds, Minutes, MilliSeconds ;
    private int timeCheck = 0;
    private int timeStop = 1;
    private boolean startGame = false;
    private int velocity= 15;
    private Context context;
    private String score;

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
        ball = new Ball(getBitmap(R.drawable.ball4),getWidth()/2,getHeight()/2);
        float movingDirection = (float)(Math.PI/4);
        for(int i=0;i<numberOfBalls;i++){
            Bitmap movingBallBitmap = getBitmap(R.drawable.ball5);
            int height,width;
            switch (i) {
                case 0:
                    height = 0+(movingBallBitmap.getHeight()/2);
                    width = 0+(movingBallBitmap.getHeight()/2);
                    movingDirection = (float)((Math.PI/(float)36)+(Math.random()*Math.PI*((float)8/(float)18)));
                    break;
                case 1:
                    height = getHeight()-(movingBallBitmap.getHeight()/2);
                    width = getWidth()-(movingBallBitmap.getHeight()/2);
                    movingDirection = (float)((Math.PI+(Math.PI/(float)36))+(Math.random()*Math.PI*((float)8/(float)18)));
                    break;
                case 2:
                    height = 0+(movingBallBitmap.getHeight()/2);
                    width = getWidth()-(movingBallBitmap.getHeight()/2);
                    movingDirection = (float)(((Math.PI/(float)2)+(Math.PI/(float)36))+(Math.random()*Math.PI*((float)8/(float)18)));
                    break;
                case 3:
                    height = getHeight()-(movingBallBitmap.getHeight()/2);
                    width = 0+(movingBallBitmap.getHeight()/2);
                    movingDirection = (float)(((3*Math.PI/(float)2)+(Math.PI/(float)36))+(Math.random()*Math.PI*((float)8/(float)18)));
                    break;
                default:
                    height = 0+(movingBallBitmap.getHeight()/2);
                    width = 0-(movingBallBitmap.getHeight()/2);
                    break;
            }

            MovingBall movingBall = new MovingBall(movingBallBitmap,width,height,getWidth(),getHeight(),movingDirection,velocity);
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

        int x = (int)event.getX(),y = (int)event.getY();
        if(ball!=null) {
            // set x so that image does not go out of screen
            if (event.getX() - (ball.getBitmap().getWidth() / 2) < 0)
                x = ball.getBitmap().getWidth() / 2;
            else if (event.getX() + (ball.getBitmap().getWidth() / 2) > getWidth())
                x = getWidth() - (ball.getBitmap().getWidth() / 2);
            else x = (int) event.getX();

            // set x so that image does not go out of screen
            if (event.getY() - (ball.getBitmap().getHeight() / 2) < 0)
                y = ball.getBitmap().getHeight() / 2;
            else if (event.getY() + (ball.getBitmap().getHeight() / 2) > getHeight())
                y = getHeight() - (ball.getBitmap().getHeight() / 2);
            else y = (int) event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ball.handleActionDown(x, y);
            if(gameOver){
                ((Activity)getContext()).finish();
            }
            if(startGame) {
                // delegating event handling to the ball
                /*if (event.getY() > getHeight() - 50) {
                    thread.setRunning(false);
                    ((Activity) getContext()).finish();
                } else {
                    Log.d(TAG, "X: " + getX() + ", Y: " + getY());
                }*/
                //Toast.makeText(context,""+startGame,Toast.LENGTH_SHORT).show();
                //System.out.println(startGame);
            }
            else {
                startGame = true;
                timeStop = 0;
                StartTime = SystemClock.uptimeMillis();
            }
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if(ball!=null) {
                // the gestures
                if (ball.isTouched()) {
                    // the droid was picked up and is being dragged
                    ball.setX(x);
                    ball.setY(y);
                }
            }

        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
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
            if(ball!=null) {
                ball.draw(canvas);
                //System.out.println(getWidth());
                for (int i = 0; i < movingBallArrayList.size(); i++) {
                    if (movingBallArrayList.get(i) != null) {
                        if (!checkCollision(movingBallArrayList.get(i))) {
                            //System.out.println(startGame);
                            if(startGame) {
                                movingBallArrayList.get(i).setVelocity(velocity);
                                movingBallArrayList.get(i).move();
                            }
                        } else {
                            timeStop = 1;
                            gameOver = true;
                            updateDatabase();
                            thread.setRunning(false);
                            //((Activity)getContext()).finish();
                        }
                        movingBallArrayList.get(i).draw(canvas);
                    }
                }
            }
            else {
                for(int i=0;i< movingBallArrayList.size();i++) {
                    movingBallArrayList.get(i).setVelocity(velocity);
                    movingBallArrayList.get(i).move();
                    movingBallArrayList.get(i).draw(canvas);
                }
            }
            //displayFps(canvas, avgFPS);
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

    public void currentTime(Canvas canvas) {
        if (canvas != null) {
            boolean change = false;
            //canvas.drawColor(Color.BLACK);
            //ball.draw(canvas);
            //System.out.println(getWidth());
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            if(timeStop==0) {
                UpdateTime = TimeBuff + MillisecondTime;
                Seconds = (int) (UpdateTime / 1000);
                if (Seconds / 5 - timeCheck != 0) velocity = velocity + 1;
                timeCheck = Seconds / 5;
                Minutes = Seconds / 60;
                Seconds = Seconds % 60;
                MilliSeconds = (int) (UpdateTime % 1000);
            }
            displayTime(canvas, "" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));
        }
    }
    private void displayTime(Canvas canvas, String time) {
        score = time;
        if (canvas != null /*&& time != null*/) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            paint.setTextSize(50);
            if(!gameOver) {
                canvas.drawText(time, this.getWidth() - 220, 70, paint);
            }
            else{
                canvas.drawText("Your Score  "+time, this.getWidth()/2 - 215, this.getHeight()/2+60, paint);
            }
        }
    }

    public void showGameOver(Canvas canvas){
        if (canvas != null && gameOver) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            paint.setTextSize(75);
            canvas.drawText("GAME OVER", this.getWidth()/2-200, this.getHeight()/2, paint);
        }
    }

    private void updateDatabase(){
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        Cursor cursor = databaseHandler.getInfo(databaseHandler);
        String [] selectionArray = {TableData.TableInfo.EASY, TableData.TableInfo.MEDIUM, TableData.TableInfo.HARD};
        if(cursor.moveToFirst()){
            System.out.println("Database present");
            String databaseScore = cursor.getString(numberOfBalls-2);
            System.out.println("Score: "+databaseScore);
            if(databaseScore.equals("")) {
                System.out.println("Database not present");
                System.out.println("Updating Database");
                String newScore = "" + Minutes + ":" + Seconds + ":" + MilliSeconds;
                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                String selection = selectionArray[numberOfBalls - 2] + " LIKE ?";
                String[] args = {databaseScore};
                ContentValues values = new ContentValues();
                values.put(selectionArray[numberOfBalls - 2], newScore);
                db.update(TableData.TableInfo.TABLE_NAME, values, selection, args);
                System.out.println("Database updated");
            }
            else{
                if (compareScore(databaseScore)) {
                    System.out.println("Updating Database");
                    String newScore = "" + Minutes + ":" + Seconds + ":" + MilliSeconds;
                    SQLiteDatabase db = databaseHandler.getWritableDatabase();
                    String selection = selectionArray[numberOfBalls - 2] + " LIKE ?";
                    String[] args = {databaseScore};
                    ContentValues values = new ContentValues();
                    values.put(selectionArray[numberOfBalls - 2], newScore);
                    db.update(TableData.TableInfo.TABLE_NAME, values, selection, args);
                    System.out.println("Database updated");
                }
            }
        }
        else{
            System.out.println("Database not present");
            databaseHandler.addInfo(databaseHandler,score,numberOfBalls-2);
            System.out.println("Database Added");
        }
    }

    private boolean compareScore(String databaseScore){
        boolean greater = false;
        int i = 0;
        String min = "", sec = "", msec = "";
        while (databaseScore.charAt(i)!=':'){
            min += databaseScore.charAt(i);
            i++;
        }
        i++;
        while (databaseScore.charAt(i)!=':'){
            sec+=databaseScore.charAt(i);
            i++;
        }
        i++;
        while(i<databaseScore.length()){
            msec += databaseScore.charAt(i);
            i++;
        }
        int Min = Integer.parseInt(min), Sec = Integer.parseInt(sec), Msec = Integer.parseInt(msec);
        if(Minutes>Min)greater = true;
        else if(Minutes==Min){
            if(Seconds>Sec)greater = true;
            else if(Seconds==Sec){
                if(MilliSeconds>Msec) greater = true;
            }
        }
        return greater;
    }
    public void stopThread(){
        thread.setRunning(false);
    }

    public MainThread getThread() {
        return thread;
    }
}
