package com.example.recipeapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;

public class MyRecipeList extends AppCompatActivity {

    ArrayList<Recipe> recipes;
    RecyclerView recipesRV;
    RecipeAdapter adapter;
    ImageButton addImageButton;
    ImageButton list;
    ImageButton myList;
    ImageButton settings;
    SwitchMaterial deleteSwitch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recipe_list);
        initLayoutComponents();
        NavButtonsInitializer.initNavButtons( list,   myList, settings,this,getIntent().getStringExtra("username"));
        initRecyclerView();
        initDeleteSwitch();

    }


    private void initRecyclerView() {
        RecipeDataSource ds = new RecipeDataSource(this);


        try {
            ds.open();
            String username=getIntent().getStringExtra("username");
            recipes = ds.getMyRecipes(username);
            ds.close();
            if (recipes.size()>0){
                recipesRV.setLayoutManager(new LinearLayoutManager(this));
                adapter=new RecipeAdapter(recipes,this);
                recipesRV.setAdapter(adapter);
            }
            else {
                Intent intent=new Intent(this, AddRecipeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving dishes", Toast.LENGTH_SHORT).show();
        }

    }
    private void initDeleteSwitch() {
        deleteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapter.setDeleting(isChecked);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initLayoutComponents() {
        recipesRV = findViewById(R.id.myRecipesRV);
        addImageButton=findViewById(R.id.addImageButton);
        list=findViewById(R.id.listImageButton);
        myList=findViewById(R.id.myListImageButton);
        settings=findViewById(R.id.settingsImageButton);
        myList.setEnabled(false);
        deleteSwitch=findViewById(R.id.switchDelete);

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
