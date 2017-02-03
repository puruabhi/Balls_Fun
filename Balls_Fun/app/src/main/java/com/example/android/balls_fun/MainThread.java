package com.example.android.balls_fun;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by abhishek on 03-02-2017.
 */

public class MainThread extends Thread {

    public static final String TAG = MainThread.class.getSimpleName();

    private SurfaceHolder surfaceHolder;
    private MainGamePanel gamePanel;

    public MainThread(SurfaceHolder surfaceHolder,MainGamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    // flag to hold game state
    public boolean running;
    public void setRunning(boolean running){
        this.running = running;
    }

    @Override
    public void run(){
        Canvas canvas;
        //long tickCount = 0;
        Log.d(TAG,"Starting game loop");
        while(running){
            canvas = null;
            // try locking the canvas for exclusive pixel editing on the surface
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    // update game state
                    // draws the canvas on the panel
                    this.gamePanel.onDraw(canvas);
                }
            }finally {
                if(canvas!=null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            //tickCount++;
            // update game state
            // render state to the screen
        }
        //Log.d(TAG,"Executed "+tickCount+" times.");
    }
}
