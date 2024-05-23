package com.example.recipeapp2;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {

    EditText recipeNameEditText,ingredientsEditText,stepsEditText;
    ImageButton saveButton;
    ToggleButton editToggleButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayoutComponents();

    }

    private void initLayoutComponents() {
    }
}
