package com.example.recipeapp2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AllRecipeListActivity extends AppCompatActivity {

    ArrayList<Recipe> recipes;
    RecyclerView recipesRV;
    RecipeAdapter adapter;
    ImageButton addImageButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_recipe_list);
        initLayoutComponents();
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
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AllRecipeListActivity.this,AddRecipeActivity.class);
                intent.putExtra("username",getIntent().getStringExtra("username"));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
}
