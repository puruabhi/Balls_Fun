package com.example.android.balls_fun;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set MainGamePanel as View
        setContentView(new MainGamePanel(this));
        Log.d(TAG,"View added");
    }

    @Override
    protected void onDestroy(){
        Log.d(TAG,"Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop(){
        Log.d(TAG,"Stopping..");
        super.onStop();
    }
}
