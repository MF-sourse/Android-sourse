package com.example.sharecontactstable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, "ContactsDatabase.db", null, 1);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                // 开始组装第一条数据
                values.put("name", "张三");
                values.put("number", "13660922769");
                values.put("sex", "男");
                db.insert("Contacts", null, values); // 插入第一条数据
                values.clear();
                // 开始组装第一条数据
                values.put("name", "凤姐");
                values.put("number", "18660922788");
                values.put("sex", "女");
                db.insert("Contacts", null, values); // 插入第一条数据
                values.clear();
            }
        });
        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("sex", "女");
                db.update("Contacts", values, "name = ?", new String[] { "张三" });
            }
        });
        Button deleteButton = (Button) findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Contacts", "sex == ?", new String[] { "男" });
            }
        });
        Button queryButton = (Button) findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // 查询Contacts表中所有的数据
                Cursor cursor = db.query("Contacts", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        // 遍历Cursor对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String number = cursor.getString(cursor.getColumnIndex("number"));
                        String sex = cursor.getString(cursor.getColumnIndex("sex"));
                        Log.d("MainActivity", "姓名： " + name);
                        Log.d("MainActivity", "电话号码： " + number);
                        Log.d("MainActivity", "性别： " + sex);
                    } while (cursor.moveToNext());
                }
                cursor.close();

            }
        });
    }
}
