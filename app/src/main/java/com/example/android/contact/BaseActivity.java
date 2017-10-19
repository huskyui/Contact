package com.example.android.contact;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by husky on 17-10-18.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
