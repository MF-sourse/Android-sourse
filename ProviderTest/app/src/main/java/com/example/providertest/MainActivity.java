package com.example.providertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 添加数据
                Uri uri = Uri.parse("content://com.example.sharecontactstable.provider/contacts");
                ContentValues values = new ContentValues();
                values.put("name", "刘梅");
                values.put("number", "12345678901");
                values.put("sex", "女");
                Uri newUri = getContentResolver().insert(uri, values);
                newId = newUri.getPathSegments().get(1);
            }
        });
        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查询数据
                Uri uri = Uri.parse("content://com.example.sharecontactstable.provider/contacts");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor. getColumnIndex("name"));
                        String number = cursor.getString(cursor. getColumnIndex("number"));
                        String sex = cursor.getString(cursor. getColumnIndex("sex"));
                        Log.d("MainActivity", "名字： " + name);
                        Log.d("MainActivity", "电话号码： " + number);
                        Log.d("MainActivity", "性别： " + sex);
                    }
                    cursor.close();
                }
            }
        });
        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更新数据
                Uri uri = Uri.parse("content://com.example.sharecontactstable.provider/contacts/" + newId);
                ContentValues values = new ContentValues();
                values.put("name", "MF");
                values.put("number", "12211331312");
                values.put("sex", "男");
                getContentResolver().update(uri, values, null, null);
            }
        });
        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除数据
                Uri uri = Uri.parse("content://com.example.sharecontactstable.provider/contacts/" + newId);
                getContentResolver().delete(uri, null, null);
            }
        });
    }

}
