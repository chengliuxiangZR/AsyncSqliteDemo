package com.example.asus.asyncsqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="database.db";
    private static final int DB_VERSION=1;
    public static final String CREATE_USER="create table User ("
            +"id integer primary key autoincrement,"
            +"username text,"
            +"userpwd text)";
    public OpenHelper(Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL("insert into User(username,userpwd) values(?,?)",new String[]{"Mike","123"});
        db.execSQL("insert into User(username,userpwd) values(?,?)",new String[]{"Jhon","456"});
        db.execSQL("insert into User(username,userpwd) values(?,?)",new String[]{"Bill","789"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
