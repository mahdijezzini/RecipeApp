package com.example.recipeapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText userEditText;
    EditText passwordEditText;
    Button registerButton,logInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initLayoutComponents();
        initLogIn();
        initRegister();
    }

    private void initLayoutComponents() {
        userEditText=findViewById(R.id.UserEdit);
        passwordEditText=findViewById(R.id.PassEdit);
        logInButton=findViewById(R.id.buttonLogin);
        registerButton=findViewById(R.id.buttonRegister);
    }
    private void initLogIn(){
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=userEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                RecipeDataSource dataSource=new RecipeDataSource(MainActivity.this);
                dataSource.open();
                if(dataSource.checkUsernameAndPassword(username,password)){
                    if(dataSource.checkIfEmptyRecipe()){
                        Intent intent=new Intent(MainActivity.this,AddRecipeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    }else {
                        Intent intent=new Intent(MainActivity.this,AllRecipeListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(MainActivity.this,"Enter valid username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initRegister(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}