package com.example.recipeapp2;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {

    Button registerButton,changeDateButton;
    EditText firstNameEditText,lastNameEditText,userNameEditText,passwordEditText;
    TextView birthdayTextView;
    RadioButton maleRadioButton,femaleRadioButton;
    User currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initLayoutComponents();
        initChangeDate();
        initRegistering();
    }

    private void initChangeDate() {
        changeDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                DatePickerDialog dialog = new DatePickerDialog();
                dialog.show(fm, "DatePicker");
            }
        });
    }

    private void initLayoutComponents() {
        registerButton=findViewById(R.id.saveregister);
        changeDateButton=findViewById(R.id.changeDateButton);
        firstNameEditText=findViewById(R.id.editTextFirstName);
        lastNameEditText=findViewById(R.id.editTextLastName);
        userNameEditText=findViewById(R.id.usernameEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        femaleRadioButton=findViewById(R.id.radioFemale);
        maleRadioButton=findViewById(R.id.radioMale);
        maleRadioButton.setChecked(true);
        birthdayTextView=findViewById(R.id.textViewBirthday);
        currentUser=new User();
    }

    public void didFinishDatePickerDialog(Calendar selectedDate) {
        currentUser.setDateOfBirth(selectedDate);
        birthdayTextView.setText(DateFormat.format("dd/MM/yyyy", selectedDate));
    }
    private void initRegistering(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.setGender(initGender());
                currentUser.setPassword(passwordEditText.getText().toString());
                currentUser.setUsername(userNameEditText.getText().toString());
                currentUser.setLastName(lastNameEditText.getText().toString());
                currentUser.setFirstName(firstNameEditText.getText().toString());
                RecipeDataSource dataSource=new RecipeDataSource(RegisterActivity.this);
                try {
                    dataSource.open();
                    if(dataSource.checkAvailabilityOfNewUsername(currentUser.getUsername())){
                        if(currentUser.getPassword().length()>5){
                            if(currentUser.getLastName().length()!=0 && currentUser.getFirstName().length()!=0){
                                dataSource.insertUser(currentUser);
                                dataSource.close();
                                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }else {
                                Toast.makeText(RegisterActivity.this,"Fill empty fields", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(RegisterActivity.this,"Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this,"Choose another username", Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    dataSource.close();
                    Toast.makeText(RegisterActivity.this,"Error in creating new account", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private String initGender(){
        if(maleRadioButton.isChecked()){
            return "m";
        }
        return "f";
    }

}
