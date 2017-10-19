package com.example.android.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

/**
 * Created by husky on 17-10-18.
 */

public class CallActivity extends BaseActivity {
    private EditText nameEdit;
    private EditText numberEdit;

    private String preName;
    private String preNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        final Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        final String number = intent.getStringExtra("number");
         preName = name;
        preNumber = number;
        nameEdit = (EditText)findViewById(R.id.call_person_name);
        numberEdit = (EditText)findViewById(R.id.call_person_number);
        nameEdit.setText(name);
        numberEdit.setText(number);

        //实现打电话
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.call);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                String callNumber = "tel:"+number;
                callIntent.setData(Uri.parse(callNumber));
                startActivity(callIntent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_call,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_refresh:
                refresh();
                finish();

                return true;

            case R.id.action_deletePerson:
                deletePerson();
                Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_LONG).show();
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh(){
        Person person = new Person();
        String name = nameEdit.getText().toString();
        String number = numberEdit.getText().toString();
        person.setName(name);
        person.setNumber(number);
        person.updateAll("name = ? and number = ?",preName,preNumber);
    }

    private void deletePerson(){
        String name = nameEdit.getText().toString();
        String number = numberEdit.getText().toString();
        DataSupport.deleteAll(Person.class,"name = ? and number = ?",preName,preNumber);
    }

}
