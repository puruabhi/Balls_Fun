package com.example.android.balls_fun;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class StartGameActivity extends Activity {

    private static final String TAG = StartGameActivity.class.getSimpleName();
    private MainGamePanel mainGamePanel;

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
        setContentView(mainGamePanel);
        Log.d(TAG,"View added");
    }

    @Override
    protected void onDestroy(){
        Log.d(TAG,"Destroying...");
        super.onDestroy();
        //finish();
    }

    @Override
    public void onBackPressed(){
        mainGamePanel.destroyThread();
    }

    @Override
    protected void onStop(){
        Log.d(TAG,"Stopping..");
        super.onStop();
        //finish();
    }
}
