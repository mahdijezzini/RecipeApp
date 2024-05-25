package com.example.recipeapp2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class AddRecipeActivity extends AppCompatActivity {


    final int PERMISSION_REQUEST_CAMERA = 103;
    EditText recipeNameEditText,ingredientsEditText,stepsEditText;
    ImageButton saveButton,cameraImageButton;
    ToggleButton editToggleButton;
    Recipe currentRecipe;
    ImageView recipeImage;
    ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK) {
                                Intent data = result.getData();
                                Bitmap photo = (Bitmap) data.getExtras().get("data");
                                float density = AddRecipeActivity.this.getResources().getDisplayMetrics().density;
                                int dp = 140;
                                int pixels = (int) ((dp * density) + 0.5);
                                Bitmap scaledPhoto = Bitmap.createScaledBitmap(
                                        photo, pixels, pixels, true);
                                recipeImage.setImageBitmap(scaledPhoto);
                                currentRecipe.setPhoto(scaledPhoto);
                            }
                        }
                    });
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayoutComponents();
        initCameraButton();
    }


    private void initLayoutComponents() {
        recipeNameEditText=findViewById(R.id.recipeNameEditText);
        ingredientsEditText=findViewById(R.id.ingredientsEditText);
        stepsEditText=findViewById(R.id.editTextRecipeSteps);
        saveButton=findViewById(R.id.imageButtonSave);
        editToggleButton=findViewById(R.id.toggleButtonEdit);
        editToggleButton.setVisibility(View.INVISIBLE);
        cameraImageButton=findViewById(R.id.cameraImageButton);

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
    private void initCameraButton() {
        cameraImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddRecipeActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            AddRecipeActivity.this, Manifest.permission.CAMERA)) {
                        Snackbar.make(findViewById(R.id.activity_main),
                                        "The app needs permission to take photo",
                                        Snackbar.LENGTH_INDEFINITE)
                                .setAction("Ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("MainActivity Camera permission", "");
                                        ActivityCompat.requestPermissions(AddRecipeActivity.this,
                                                new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                                    }
                                }).show();
                    } else {
                        Log.d("MainActivity Camera permission", "");
                        ActivityCompat.requestPermissions(AddRecipeActivity.this,
                                new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    }
                }
            }
        });
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncher.launch(intent);
    }


}
