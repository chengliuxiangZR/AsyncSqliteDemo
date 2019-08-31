package com.example.asus.asyncsqlitedemo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public abstract class DbCommand<T> {
    //数据库执行引擎，只有一个线程的线程池
    private static ExecutorService sDbEngine= Executors.newSingleThreadExecutor();
    //主线程消息队列的Handler
    private final static Handler sUIHandler=new Handler(Looper.getMainLooper());
    //执行数据库操作
    public final void execute(){
        Log.i("数据库","开始异步操作");
        sDbEngine.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    T data=doInBackground();
                    Log.i("数据库",data.toString());
                    postResult(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //将结果投递到UI线程
    private void postResult(final T result){
        Log.i("数据库","开始投递结果到UI线程");
        sUIHandler.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(result);
            }
        });
    }
    //在后台执行数据库操作
    protected abstract T doInBackground();
    //将结果投递到UI线程
    protected void onPostExecute(T result){}
}
