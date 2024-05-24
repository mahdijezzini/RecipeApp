package com.example.recipeapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

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

    public boolean checkUsernameAndPassword(String username,String password){

        try {
            String query = "SELECT * FROM user WHERE password = '"+password+"' and username='"+username+"'";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            if (cursor.getCount()!=0){
                cursor.close();
                return true;
            }
            cursor.close();
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean checkIfEmptyRecipe(){
        try {
            String query = "SELECT * FROM recipe" ;
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            if (cursor.getCount()==0){
                cursor.close();
                return true;
            }
            cursor.close();
            return false;
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

    public boolean insertRecipe(Recipe recipe){
        boolean didSucceed=false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("name", recipe.getRecipeName());
            initialValues.put("ingredients", recipe.getIngredients());
            initialValues.put("steps", recipe.getSteps());
            if (recipe.getPhoto() != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                recipe.getPhoto().compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] photo = baos.toByteArray();
                initialValues.put("contactphoto", photo);
            }
            initialValues.put("username", recipe.getUsername());
            didSucceed = database.insert("recipe", null, initialValues) > 0;
        }catch (Exception e) {
            Log.d("My Database", "Something went wrong!");
        }
        return didSucceed;
    }


    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipes=new ArrayList<>();
        try{
            String query="select * from recipe ";
            Cursor cursor=database.rawQuery(query,null);
            Recipe newRecipe;
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                newRecipe=new Recipe();
                newRecipe.setRecipeId(cursor.getInt(0));
                newRecipe.setRecipeName(cursor.getString(1));
                newRecipe.setIngredients(cursor.getString(2));
                newRecipe.setSteps(cursor.getString(3));
                newRecipe.setUsername(cursor.getString(5));
                byte[] photo = cursor.getBlob(4);
                if(photo!=null){
                    ByteArrayInputStream bais = new ByteArrayInputStream(photo);
                    Bitmap recipePhoto = BitmapFactory.decodeStream(bais);
                    newRecipe.setPhoto(recipePhoto);
                }
                recipes.add(newRecipe);
                cursor.moveToNext();
            }
            cursor.close();
        }catch (Exception e){
            recipes=new ArrayList<>();
        }
        return recipes;
    }
}
