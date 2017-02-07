package com.example.android.balls_fun;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class StartGameActivity extends Activity {

    private static final String TAG = StartGameActivity.class.getSimpleName();
    private MainGamePanel mainGamePanel;
    private MainThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set MainGamePanel as View
        int numberOfBalls = 5;
        String intentData = getIntent().getStringExtra("numberOfBalls");
        numberOfBalls = Integer.parseInt(intentData);
        mainGamePanel = new MainGamePanel(this,numberOfBalls);
        thread = mainGamePanel.getThread();
        setContentView(mainGamePanel);
        Log.d(TAG,"View added");
    }

    @Override
    protected void onDestroy(){
        Log.d(TAG,"Destroying...");
        super.onDestroy();
        if(!thread.isInterrupted()){
            thread.interrupt();
        }
        //finish();
    }

    @Override
    public void onBackPressed(){
        mainGamePanel.stopThread();
        finish();
    }

    @Override
    protected void onStop(){
        Log.d(TAG,"Stopping..");
        super.onStop();
        //finish();
    }
}
