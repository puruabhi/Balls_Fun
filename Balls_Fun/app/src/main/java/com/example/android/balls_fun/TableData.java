package com.example.android.balls_fun;

import android.provider.BaseColumns;

/**
 * Created by abhishek on 04-01-2017.
 */

public class TableData {

    public TableData(){

    }

    public static abstract class TableInfo implements BaseColumns{
        public static final String EASY = "easy";
        public static final String MEDIUM = "medium";
        public static final String HARD = "hard";
        public static final String DATABASE_NAME = "user_info";
        public static final String TABLE_NAME = "reg_info";
    }

}
