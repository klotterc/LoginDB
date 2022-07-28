package com.example.logindblibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginDBLibrary extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "credentials.db";
    public static final String CREDENTIALS_COLUMN_ID = "id";
    public static final String CREDENTIALS_TABLE_NAME = "credentials";
    public static final String CREDENTIALS_COLUMN_NAME = "name";
    public static final String CREDENTIALS_COLUMN_EMAIL = "email";
    public static final String CREDENTIALS_COLUMN_PASSWORD = "password";
    public static final String CREDENTIALS_COLUMN_DOB = "dob";
    public static final String CREDENTIALS_COLUMN_GENDER = "gender";
    public static final int DATABASE_VERSION = 1;

    public LoginDBLibrary(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table credentials " +
                        "(id integer primary key,name text,email text,password text,dob text,gender text)"
        );
    }
    public boolean insertCredentials (String name, String email, String password, String dob, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CREDENTIALS_COLUMN_NAME, name);
        contentValues.put(CREDENTIALS_COLUMN_EMAIL, email);
        contentValues.put(CREDENTIALS_COLUMN_PASSWORD, password);
        contentValues.put(CREDENTIALS_COLUMN_DOB, dob);
        contentValues.put(CREDENTIALS_COLUMN_GENDER, gender);

        db.insert(CREDENTIALS_TABLE_NAME, null, contentValues);
        return true;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from credentials where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CREDENTIALS_COLUMN_ID);
        return numRows;
    }
    public Integer deleteCredentials (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CREDENTIALS_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Integer> getAllCredentials() {
        ArrayList<Integer> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from events", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            int index = res.getColumnIndex(CREDENTIALS_COLUMN_ID);
            if(index >= 0) {
                array_list.add(res.getInt(index));
                res.moveToNext();
            }
        }
        return array_list;
    }
    public boolean updateCredentials (Integer id, String name, String email, String password, String dob, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CREDENTIALS_COLUMN_NAME, name);
        contentValues.put(CREDENTIALS_COLUMN_EMAIL, email);
        contentValues.put(CREDENTIALS_COLUMN_PASSWORD, password);
        contentValues.put(CREDENTIALS_COLUMN_DOB, dob);
        contentValues.put(CREDENTIALS_COLUMN_GENDER, gender);
        db.update("events", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS events");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
