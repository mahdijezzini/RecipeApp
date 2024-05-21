package com.example.recipeapp2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RecipeDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipeApp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_USER = "create table user(" +
            "username text primary key, "+
            "firstname text, "+
            "lastname text,"+
            "gender char,"+
            "dateofbirth date, "+
            "password text);";
    private static final String CREATE_TABLE_RECIPE = "create table recipe(" +
            "recipeId integer primary key autoincrement, " +
            "name text,"+
            "ingredients text, "+
            "steps text, "+
            "recipephoto blob,"+
            "username text, "+
            "foreign key (username)  references user(username) );";

    public RecipeDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_RECIPE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
