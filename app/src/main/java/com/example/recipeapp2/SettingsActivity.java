package com.example.recipeapp2;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {
    ImageButton list;
    ImageButton myList;
    ImageButton settings;
    Button saveButton,changeDateButton;
    EditText firstNameEditText,lastNameEditText,userNameEditText,passwordEditText;
    TextView birthdayTextView;
    RadioButton maleRadioButton,femaleRadioButton;
    User currentUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        initLayoutComponents();
        NavButtonsInitializer.initNavButtons( list,   myList, settings,this,getIntent().getStringExtra("username"));
        initChangeDate();
        initSaving();
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
        list=findViewById(R.id.listImageButton);
        myList=findViewById(R.id.myListImageButton);
        settings=findViewById(R.id.settingsImageButton);
        settings.setEnabled(false);
        saveButton=findViewById(R.id.saveregister);
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
        RecipeDataSource dataSource=new RecipeDataSource(SettingsActivity.this);
        dataSource.open();
        currentUser=dataSource.getSpecificUser(getIntent().getStringExtra("username"));
        firstNameEditText.setText(currentUser.getFirstName());
        lastNameEditText.setText(currentUser.getLastName());
        userNameEditText.setText(currentUser.getUsername());
        birthdayTextView.setText(DateFormat.format("dd/MM/yyyy",
                currentUser.getDateOfBirth().getTimeInMillis()).toString());
        passwordEditText.setText(currentUser.getPassword());
        String gender=currentUser.getGender();
        switch (gender){
            case "m":
                maleRadioButton.setChecked(true);
                break;
            case "f":
                femaleRadioButton.setChecked(true);
        }
    }

    public void didFinishDatePickerDialog(Calendar selectedDate) {
        currentUser.setDateOfBirth(selectedDate);
        birthdayTextView.setText(DateFormat.format("dd/MM/yyyy", selectedDate));
    }
    private void initSaving(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldUsername=getIntent().getStringExtra("username");
                currentUser.setGender(initGender());
                currentUser.setPassword(passwordEditText.getText().toString());
                currentUser.setUsername(userNameEditText.getText().toString());
                currentUser.setLastName(lastNameEditText.getText().toString());
                currentUser.setFirstName(firstNameEditText.getText().toString());
                RecipeDataSource dataSource=new RecipeDataSource(SettingsActivity.this);
                try {
                    dataSource.open();

                        if(currentUser.getPassword().length()>5){
                            if(currentUser.getLastName().length()!=0 && currentUser.getFirstName().length()!=0){
                                boolean saved=dataSource.updateUser(currentUser,oldUsername);
                                dataSource.close();
                                if(saved){
                                    Toast.makeText(SettingsActivity.this,"Saved", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(SettingsActivity.this,AllRecipeListActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("username",currentUser.getUsername());
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(SettingsActivity.this,"Didn't save", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(SettingsActivity.this,"Fill empty fields", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(SettingsActivity.this,"Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                        }

                }catch (Exception e){
                    dataSource.close();
                    Toast.makeText(SettingsActivity.this,"Error in creating new account", Toast.LENGTH_SHORT).show();
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
