package com.sds.study.arduinoproject;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {
    String TAG;
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        TAG = this.getClass().getName();

    }

    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, " db create .....  ");
        StringBuffer sb = new StringBuffer();
        sb.append("create table position(");
        sb.append("position_id integer primary key autoincrement,");
        sb.append("lat varchar(20),");
        sb.append("lng varchar(20)");
        sb.append(")");

        db.execSQL(sb.toString());


    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
