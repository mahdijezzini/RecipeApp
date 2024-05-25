package com.example.recipeapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecipeList extends AppCompatActivity {

    ArrayList<Recipe> recipes;
    RecyclerView recipesRV;
    RecipeAdapter adapter;
    ImageButton addImageButton;
    ImageButton list;
    ImageButton myList;
    ImageButton settings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recipe_list);
        initLayoutComponents();
        NavButtonsInitializer.initNavButtons( list,   myList, settings,this);
        initRecyclerView();


    }


    private void initRecyclerView() {
        RecipeDataSource ds = new RecipeDataSource(this);


        try {
            ds.open();
            recipes = ds.getAllRecipes();
            ds.close();
            if (recipes.size()>0){
                recipesRV.setLayoutManager(new LinearLayoutManager(this));
                adapter=new RecipeAdapter(recipes,this);
                recipesRV.setAdapter(adapter);
            }
            else {
                Intent intent=new Intent(this, AddRecipeActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving dishes", Toast.LENGTH_SHORT).show();
        }

    }

    private void initLayoutComponents() {
        recipesRV = findViewById(R.id.recipesRV);
        addImageButton=findViewById(R.id.addImageButton);
        list=findViewById(R.id.listImageButton);
        myList=findViewById(R.id.myListImageButton);
        settings=findViewById(R.id.settingsImageButton);
        myList.setEnabled(false);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyRecipeList.this,AddRecipeActivity.class);
                intent.putExtra("username",getIntent().getStringExtra("username"));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

}
