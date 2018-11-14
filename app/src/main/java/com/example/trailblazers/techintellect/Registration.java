package com.example.trailblazers.techintellect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*************************************************************************************************************************************************************
 Program: Registration class handling validations and registration operations.
 Author: Aravind
 Date of creation: 10-Oct-2018
 *************************************************************************************************************************************************************/


public class Registration extends AppCompatActivity {

    private TextView linkLogin;
    private Button signUp;
    private EditText full_name;
    private EditText email;
    private EditText password;
    private EditText confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        initializeComponents();
    }

    private void initializeComponents() {

        full_name = findViewById(R.id.full_name_value);
        email = findViewById(R.id.email_address_value);
        password = findViewById(R.id.create_new_password);
        confirm_password = findViewById(R.id.confirm_password_value);
        signUp = findViewById(R.id.Sign_up_btn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    Toast.makeText(getApplicationContext(), "Success! Kindly login to proceed.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        linkLogin = findViewById(R.id.login_link);
        String htmlData="<u>Login</u>";
        linkLogin.setText(Html.fromHtml(htmlData));

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    boolean isEmpty(EditText text){
       CharSequence input = text.getText().toString();
       return TextUtils.isEmpty(input);
    }

    boolean isEmail(EditText text){
        CharSequence input = text.getText().toString();
        return (!TextUtils.isEmpty(input) && Patterns.EMAIL_ADDRESS.matcher(input).matches());
    }

    private boolean validateEntries(){
        if(isEmpty(full_name)){
            full_name.setError("Full name is required");
            return false;
        }
        if(!isEmail(email)){
            email.setError("Please enter a valid email");
            return false;
        }
        if(isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please provide a password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(isEmpty(confirm_password)){
            Toast.makeText(getApplicationContext(), "Please confirm your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.getText().toString().equals(confirm_password.getText().toString())){
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
