package com.example.recipeapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {

    EditText recipeNameEditText,ingredientsEditText,stepsEditText;
    ImageButton saveButton;
    ToggleButton editToggleButton;
    Recipe currentRecipe;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayoutComponents();

    }

    private void initLayoutComponents() {
        recipeNameEditText=findViewById(R.id.recipeNameEditText);
        ingredientsEditText=findViewById(R.id.ingredientsEditText);
        stepsEditText=findViewById(R.id.editTextRecipeSteps);
        saveButton=findViewById(R.id.imageButtonSave);
        editToggleButton=findViewById(R.id.toggleButtonEdit);
        editToggleButton.setVisibility(View.INVISIBLE);
        currentRecipe=new Recipe();
        currentRecipe.setUsername(getIntent().getStringExtra("username"));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRecipe.setRecipeName(recipeNameEditText.getText().toString());
                currentRecipe.setIngredients(ingredientsEditText.getText().toString());
                currentRecipe.setSteps(stepsEditText.getText().toString());
            }
        });
    }
}
