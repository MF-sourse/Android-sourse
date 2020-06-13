package com.example.sharecontactstable;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    public static final int CONTACTS_DIR = 0;

    public static final int CONTACTS_ITEM = 1;

    public static final String AUTHORITY = "com.example.sharecontactstable.provider";

    private static UriMatcher uriMatcher;

    private MyDatabaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "contacts", CONTACTS_DIR);
        uriMatcher.addURI(AUTHORITY, "contacts/#", CONTACTS_ITEM);
    }

//    public MyContentProvider() {
//    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // 删除数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)) {
            case CONTACTS_DIR:
                deletedRows = db.delete("Contacts", selection, selectionArgs);
                break;
            case CONTACTS_ITEM:
                String contactsId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Contacts", "id = ?", new String[] { contactsId });
                break;
            default:
                break;
        }
        return deletedRows;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CONTACTS_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.sharecontactstable.provider.contacts";
            case CONTACTS_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.sharecontactstable.provider.contacts";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // 添加数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case CONTACTS_DIR:
            case CONTACTS_ITEM:
                long newContactsId = db.insert("Contacts", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/contacts/" + newContactsId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext(), "ContactsDatabase.db", null, 1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // 查询数据
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case CONTACTS_DIR:
                cursor = db.query("Contacts", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CONTACTS_ITEM:
                String contactsId = uri.getPathSegments().get(1);
                cursor = db.query("Contacts", projection, "id = ?", new String[] { contactsId }, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // 更新数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)) {
            case CONTACTS_DIR:
                updatedRows = db.update("Contacts", values, selection, selectionArgs);
                break;
            case CONTACTS_ITEM:
                String contactsId = uri.getPathSegments().get(1);
                updatedRows = db.update("Contacts", values, "id = ?", new String[] { contactsId });
                break;
            default:
                break;
        }
        return updatedRows;
    }
}
