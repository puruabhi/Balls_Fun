package com.example.android.balls_fun;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class HighScoreActivity extends Activity {

    TextView easyScore, mediumScore, hardScore;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.high_score_activity);
        setIDs();
        putScore();
    }

    public void putScore(){
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        Cursor cursor = databaseHandler.getInfo(databaseHandler);
        if(cursor.moveToFirst()) {
            String easy,medium,hard;
            easy = cursor.getString(0);
            medium = cursor.getString(1);
            hard = cursor.getString(2);
            if(!easy.equals(""))
                easyScore.setText(easy);
            else
                easyScore.setText("NA");
            if(!medium.equals(""))
                mediumScore.setText(medium);
            else
                mediumScore.setText("NA");
            if(!hard.equals(""))
                hardScore.setText(hard);
            else
                hardScore.setText("NA");
        }
        else{
            easyScore.setText("NA");
            mediumScore.setText("NA");
            hardScore.setText("NA");
        }
    }
    public void setIDs(){
        easyScore = (TextView)findViewById(R.id.easy_value);
        mediumScore = (TextView)findViewById(R.id.medium_value);
        hardScore = (TextView)findViewById(R.id.difficult_value);
    }
}
