package com.gmail.blackbull8810.noiseapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jun on 2017. 4. 28..
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context,"Noise.db",null,1);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table noisevalue(num integer primary key autoincrement, value text, day text, valuek text);");
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }

}
