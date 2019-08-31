package com.example.asus.asyncsqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db =new OpenHelper(getApplicationContext()).getWritableDatabase();
    }
    public List<User> queryAllUsers(){
        Cursor cursor=db.rawQuery("select * from User",null);
        List<User> users=new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()){
            User user=new User(cursor.getString(cursor.getColumnIndex("username")),cursor.getString(cursor.getColumnIndex("userpwd")));
            users.add(user);
        }
        cursor.close();
        return users;
    }
    public void myClick(View view){
//        List<User> users=queryAllUsers();
//        for(User user:users){
//            Log.i("数据库",user.toString());
//        }
        new DbCommand<List<User>>(){
            @Override
            protected List<User> doInBackground() {
                Log.i("数据库","###执行数据库的线程"+Thread.currentThread().getName());
                return queryAllUsers();
            }

            @Override
            protected void onPostExecute(List<User> result) {
                Log.i("数据库","###-->获取数据库操作结果的线程-->"+Thread.currentThread().getName());
                for(User user:result){
                    Log.d("数据库","###学生信息: "+user.toString());
                }
            }
        }.execute();
    }
}
