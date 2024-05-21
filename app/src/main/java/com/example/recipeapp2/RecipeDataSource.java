package com.example.recipeapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RecipeDataSource  {

    SQLiteDatabase database;
    RecipeDBHelper dbHelper;

    public RecipeDataSource(Context context) {
        dbHelper = new RecipeDBHelper(context);
    }

    public void open() throws SQLException {
        database =dbHelper.getWritableDatabase();
    }
    public boolean checkAvailabilityOfNewUsername(String newUsername){

        try {
            String query = "SELECT * FROM user WHERE username = '"+newUsername+"';";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            if (cursor.getCount()!=0){
                cursor.close();
                return false;
            }
            cursor.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void close(){
        dbHelper.close();
    }

    public boolean insertUser(User user) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("username", user.getUsername());
            initialValues.put("firstname", user.getFirstName());
            initialValues.put("lastname", user.getLastName());
            initialValues.put("gender", user.getGender());
            initialValues.put("dateOfBirth", String.valueOf(user.getDateOfBirth().getTimeInMillis()));
            initialValues.put("password", user.getPassword());
            didSucceed = database.insert("user", null, initialValues) > 0;
        } catch (Exception e) {
            Log.d("My Database", "Something went wrong!");
        }
        return didSucceed;
    }



}
