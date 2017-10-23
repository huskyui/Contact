package com.example.android.contact;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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
            case R.id.action_importAllPerson:
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);

                }else{
                    importAllPerson();
                }
                finish();
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


    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResult){
        switch (requestCode){
            case 1:
                if(grantResult.length > 0&&grantResult[0] == PackageManager.PERMISSION_GRANTED){
                    importAllPerson();
                }else{

                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();

                }
                break;
            default:
        }
    }

    private void importAllPerson(){
        Cursor cursor = null;
        try{
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if(cursor != null){
                while(cursor.moveToNext()){
                    Person person = new Person();
                    person.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                    person.setNumber(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    person.save();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
