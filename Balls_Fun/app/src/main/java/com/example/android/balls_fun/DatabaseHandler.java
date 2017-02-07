package com.example.android.balls_fun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Switch;

import com.example.android.balls_fun.TableData.TableInfo;

import java.util.ArrayList;

/**
 * Created by abhishek on 04-01-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE "+ TableInfo.TABLE_NAME+"("+TableInfo.EASY+" TEXT,"+TableInfo.MEDIUM+" TEXT,"+TableInfo.HARD+" TEXT);";

    public DatabaseHandler(Context context) {
        super(context, TableInfo.DATABASE_NAME, null, database_version);
        Log.d("DatabaseHandler","Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        Log.d("DatabaseHandler","Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addInfo(DatabaseHandler dh, String score, int level){

        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        switch(level){
            case 0:
                cv.put(TableInfo.EASY,score);
                cv.put(TableInfo.MEDIUM,"");
                cv.put(TableInfo.HARD,"");
                break;
            case 1:
                cv.put(TableInfo.EASY,"");
                cv.put(TableInfo.MEDIUM,score);
                cv.put(TableInfo.HARD,"");
                break;
            case 2:
                cv.put(TableInfo.EASY,"");
                cv.put(TableInfo.MEDIUM,"");
                cv.put(TableInfo.HARD,score);
                break;
        }
        db.insert(TableInfo.TABLE_NAME,null,cv);
        Log.d("DatabaseHandler","One row inserted");

    }

    public Cursor getInfo(DatabaseHandler dh){
        SQLiteDatabase db = dh.getReadableDatabase();
        String [] columns = {TableInfo.EASY,TableInfo.MEDIUM,TableInfo.HARD};
        Cursor cr = db.query(TableInfo.TABLE_NAME,columns,null,null,null,null,null);
        return cr;
    }
    public Cursor getInfo(DatabaseHandler dh, int level){
        SQLiteDatabase db = dh.getReadableDatabase();
        ArrayList<String> columns = new ArrayList<>();
        switch(level){
            case 0:
                columns.add(TableInfo.EASY);
                break;
            case 1:
                columns.add(TableInfo.MEDIUM);
                break;
            case 2:
                columns.add(TableInfo.HARD);
                break;
        }
        String[] column = {columns.get(0)};
        Cursor cr = db.query(TableInfo.TABLE_NAME,column,null,null,null,null,null);
        return cr;
    }
}
