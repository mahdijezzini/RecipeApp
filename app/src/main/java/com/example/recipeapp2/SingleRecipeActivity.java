package com.example.recipeapp2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SingleRecipeActivity extends AppCompatActivity {
    TextView name,ingredients,steps;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
        initLayoutComponents();

    }

    private void initLayoutComponents() {
        name=findViewById(R.id.recipeNameTextView);
        steps=findViewById(R.id.recipeStepsTextView);
        ingredients=findViewById(R.id.recipeIngredientsTextView);

    }

}
