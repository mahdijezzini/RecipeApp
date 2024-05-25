package com.example.recipeapp2;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {
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
                initGender();

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
