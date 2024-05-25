package com.example.recipeapp2;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

public class NavButtonsInitializer {

    public static void initNavButtons(ImageButton list,  ImageButton myList,ImageButton settings, Context context,String username) {
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        context, AllRecipeListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("username",username);
                context.startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        context, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("username",username);
                context.startActivity(intent);
            }
        });

        myList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        context, MyRecipeList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("username",username);
                context.startActivity(intent);
            }
        });
    }
}

