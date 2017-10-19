package com.example.android.contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by husky on 17-10-17.
 */

public class LaunchActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        //实现隐藏menu
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        //这儿好像是用了线程，将时间推迟3秒
        final Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run(){
                startActivity(intent);
            }
        };
        timer.schedule(task,1000*3);


    }
}
