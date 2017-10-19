package com.example.android.contact;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG ="MainActivity";
    //定义一个变量，来标识是否退出
    private long clickTime = 0;


    private List<Person> personList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建数据库
        Connector.getDatabase();
        //初始化数据
        initPersons();




        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart(){
        super.onStart();
        initPersons();
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_delete_all_person:
                Log.d("MainActivity","delete all person");
                deleteAllPerson();
               return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //每次返回时都重新从数据库中读取数据
    private void initPersons(){

        personList = DataSupport.findAll(Person.class);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        PersonAdapter personAdapter = new PersonAdapter(getApplicationContext(),personList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(personAdapter);


    }

    //当按键在back进入exit()函数，对当前的时间继续计数，任何再按back'时对两个时间相减，实现3秒内按back两次，退出整个应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }


    private void exit(){
        if(System.currentTimeMillis()-clickTime > 2000){
            Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        }else{
            Log.d("MainActivity","exit application");
            ActivityCollector.finishAll();//实现关闭所有Activity
        }
    }

    //AlertDialog
    private void deleteAllPerson(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("删除所有人信息");
        dialog.setMessage("信息无价");
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataSupport.deleteAll(Person.class);
                initPersons();
                Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();

    }





}
