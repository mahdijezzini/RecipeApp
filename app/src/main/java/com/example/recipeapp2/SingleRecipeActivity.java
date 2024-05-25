package com.example.recipeapp2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SingleRecipeActivity extends AppCompatActivity {
    TextView name,ingredients,steps,username;
    Recipe currentRecipe;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
        initLayoutComponents();

    }

    private void initLayoutComponents() {
        name=findViewById(R.id.recipeNameTextView);
        steps=findViewById(R.id.recipeStepsTextView);
        ingredients=findViewById(R.id.recipeIngredientsTextView);
        username=findViewById(R.id.usernameTextView);
        int recipeId=getIntent().getIntExtra("recipeId",-1);
        RecipeDataSource dataSource=new RecipeDataSource(this);
        dataSource.open();
        currentRecipe=dataSource.getSpecificRecipe(recipeId);
        name.setText(currentRecipe.getRecipeName());
        steps.setText(currentRecipe.getSteps());
        ingredients.setText(currentRecipe.getIngredients());
        username.setText(currentRecipe.getUsername());
//continue getting data of the current recipe and setting text to text views

    }

}
