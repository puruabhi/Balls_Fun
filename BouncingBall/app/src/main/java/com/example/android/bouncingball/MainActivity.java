package com.example.android.bouncingball;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity{

    BouncingBallView bouncing_ball_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bouncing_ball_view = new BouncingBallView(this);
        setContentView(bouncing_ball_view);
    }

    @Override
    public void onStart(){
        super.onStart();
        bouncing_ball_view.start_animation_thread();
    }

    @Override
    public void onStop(){
        super.onStop();
        bouncing_ball_view.stop_animation_thread();
    }
}
