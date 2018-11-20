package com.example.trailblazers.techintellect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private TextView signUp; //Added by Aravind
    private Button btnLogin; //Added by Haritha

    //Added by Ravi
    private EditText etEmail;
    private EditText etPassword;
    private FirebaseAuth authentication;
    private Button btnGuest;
    //Added by Ravi Ends
    int backButtonCount =0; //Added by Haritha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Added by Aravind starts
        signUp = findViewById(R.id.btn_signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Registration.class);
                startActivity(intent);
            }
        });
        //Added by Aravind ends

        //Added by Ravi Starts
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        authentication = FirebaseAuth.getInstance();

        btnGuest = findViewById(R.id.btn_guest);


        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity(intent);

            }
        });

        btnLogin = findViewById(R.id.btn_signIn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(validateCredentials()) {

                  final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Please wait....", "Logging in.....", true);

                  (authentication.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()))
                          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                              @Override
                              public void onComplete(@NonNull Task<AuthResult> task) {

                                  progressDialog.dismiss();
                                  if (task.isSuccessful()) {

                                      Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                                      startActivity(intent);
                                      //Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                                  } else {
                                      Log.e("Error", task.getException().toString());
                                      Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                  }


                              }
                          });
              }
            }
        });
        //Added by Ravi ends

    }

    boolean isEmail(EditText text){
        CharSequence input = text.getText().toString();
        return (!TextUtils.isEmpty(input) && Patterns.EMAIL_ADDRESS.matcher(input).matches());
    }

    boolean isEmpty(EditText text){
        CharSequence input = text.getText().toString();
        return TextUtils.isEmpty(input);
    }


    private boolean validateCredentials()
    {

        if(!isEmail(etEmail)){
            etEmail.setError("Please enter a valid email address ...");
            return false;
        }
        if(isEmpty(etPassword)){
            Toast.makeText(getApplicationContext(), "Please provide a password....", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;

    }

    //Added by Haritha - Starts
    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }

    }
    //Added by Haritha - Ends
}
