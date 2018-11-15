package com.example.trailblazers.techintellect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    //Added by Ravi Ends

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

        //Added by Ravi Ends


        //Added by Ravi starts
        btnLogin = findViewById(R.id.btn_signIn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog  = ProgressDialog.show(MainActivity.this,"Please wait....","Logging in.....",true);

                (authentication.signInWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {

                            Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
                            startActivity(intent);
                            //Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Log.e("Error",task.getException().toString());
                            Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }


                    }
                });
            }
        });
        //Added by Ravi ends
    }

    public void forgotPassword(View view) {
    }

    public void signUp(View view) {

    }






}
