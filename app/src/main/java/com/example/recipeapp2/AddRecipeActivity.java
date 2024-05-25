package com.example.recipeapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {

    EditText recipeNameEditText,ingredientsEditText,stepsEditText;
    ImageButton saveButton,camera;
    ToggleButton editToggleButton;
    Recipe currentRecipe;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayoutComponents();

    }
    private void initCamera(){

    }

    private void initLayoutComponents() {
        recipeNameEditText=findViewById(R.id.recipeNameEditText);
        ingredientsEditText=findViewById(R.id.ingredientsEditText);
        stepsEditText=findViewById(R.id.editTextRecipeSteps);
        saveButton=findViewById(R.id.imageButtonSave);
        editToggleButton=findViewById(R.id.toggleButtonEdit);
        editToggleButton.setVisibility(View.INVISIBLE);
        camera=findViewById(R.id.cameraImageButton);

        currentRecipe=new Recipe();
        currentRecipe.setUsername(getIntent().getStringExtra("username"));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentRecipe.setRecipeName(recipeNameEditText.getText().toString());
                currentRecipe.setIngredients(ingredientsEditText.getText().toString());
                currentRecipe.setSteps(stepsEditText.getText().toString());

                if(     currentRecipe.getRecipeName().length()>1
                        && currentRecipe.getIngredients().length()>1
                        && currentRecipe.getSteps().length()>1
                ){
                    RecipeDataSource dataSource=new RecipeDataSource(AddRecipeActivity.this);
                    dataSource.open();
                    if(dataSource.insertRecipe(currentRecipe)){
                        Toast.makeText(AddRecipeActivity.this,"Recipe added", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(AddRecipeActivity.this,AllRecipeListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("username",currentRecipe.getUsername());
                        startActivity(intent);
                    }else {
                        Toast.makeText(AddRecipeActivity.this,"Error saving recipe", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AddRecipeActivity.this,"Fill empty fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
