package com.example.android.balls_fun;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView balls_funTextView,fun;
    Button easyButton, mediumButton, difficultButton, highScoresButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setIDs();
        setOnClickListeners();
    }

    public void setIDs(){
        balls_funTextView = (TextView)findViewById(R.id.balls_funTextView);
        easyButton = (Button)findViewById(R.id.easyButton);
        mediumButton= (Button)findViewById(R.id.mediumButton);
        difficultButton = (Button)findViewById(R.id.difficultButton);
        highScoresButton = (Button)findViewById(R.id.highScoreButton);
    }

    public void setOnClickListeners(){
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),StartGameActivity.class);
                intent.putExtra("numberOfBalls","2");
                startActivity(intent);
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),StartGameActivity.class);
                intent.putExtra("numberOfBalls","3");
                startActivity(intent);
            }
        });

        difficultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),StartGameActivity.class);
                intent.putExtra("numberOfBalls","4");
                startActivity(intent);
            }
        });

        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),HighScoreActivity.class);
                startActivity(intent);
            }
        });
    }
}
