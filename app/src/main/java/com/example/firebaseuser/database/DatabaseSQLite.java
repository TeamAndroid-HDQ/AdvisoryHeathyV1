package com.example.firebaseuser.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firebaseuser.Model.UserRequest;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class DatabaseSQLite extends SQLiteOpenHelper {
    final static String KEY_DB_NAME = "database";
    final static int KEY_DB_VERSION = 1;
    //table database
    final static String KEY_TABLE_NAME = "REQUEST";
    final static String KEY_COL_ID = "id";
    final static String KEY_COL_VALUE = "value";

    public DatabaseSQLite(@Nullable Context context) {
        super(context, KEY_DB_NAME, null, KEY_DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create TABLE " + KEY_TABLE_NAME + "(" + KEY_COL_ID + " INTERGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_COL_VALUE + " TEXT)";
        db.execSQL(query);
    }
    //API for the database
    public void save(DataSnapshot data) {
        SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_COL_ID, data.getKey()+"");
            values.put(KEY_COL_VALUE,data.getValue()+"");
            db.insert(KEY_TABLE_NAME,null,values);
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
