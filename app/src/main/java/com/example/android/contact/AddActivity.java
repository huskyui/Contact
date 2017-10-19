package com.example.android.contact;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by husky on 17-10-18.
 */

public class AddActivity extends BaseActivity {

    private EditText nameText;
    private EditText numberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Log.d("addActivity","found");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_save:
                addData();
                finish();
                return true;
            //清空数据
            case R.id.action_clear:
                clearData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addData(){
        nameText = (EditText)findViewById(R.id.name);
        numberText = (EditText)findViewById(R.id.number);
        String name = nameText.getText().toString();
        String number = numberText.getText().toString();
        Log.d("database","name:"+name);
        Log.d("database","number"+number);
        Person person = new Person();
        person.setName(name);
        person.setNumber(number);
        person.save();
        Log.d("database","success");
    }

    private void clearData(){
        nameText = (EditText)findViewById(R.id.name);
        numberText = (EditText)findViewById(R.id.number);
        nameText.setText("");
        numberText.setText("");
    }
}
