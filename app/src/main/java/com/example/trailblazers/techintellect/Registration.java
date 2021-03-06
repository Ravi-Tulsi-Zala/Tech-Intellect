package com.example.trailblazers.techintellect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

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
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        initializeComponents();
    }

    private void initializeComponents() {
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        full_name = findViewById(R.id.full_name_value);
        email = findViewById(R.id.email_address_value);
        password = findViewById(R.id.create_new_password);
        confirm_password = findViewById(R.id.confirm_password_value);
        signUp = findViewById(R.id.Sign_up_btn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnectedToInternet()){
                    if(validateEntries()){
                        progressDialog.setMessage("Registering..Please wait!");
                        progressDialog.show();
                        Thread mthread = new Thread(){  //Creating a separate thread for progress dialog
                            @Override
                            public void run() {
                                auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            progressDialog.hide();
                                            saveDisplayName();
                                            Toast.makeText(getApplicationContext(), "Success! Kindly login to proceed. ", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                        }
                                        if(!task.isSuccessful()){
                                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                            Toast.makeText(getApplicationContext(), "Failed Registration: "+e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        };
                        mthread.start();
                    }
                }
                else{
                    //Alerting when there is no active internet connection
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Registration.this);
                    dlgAlert.setMessage("Please check your internet connection and try again!");
                    dlgAlert.setTitle("Alert");
                    dlgAlert.setPositiveButton("Ok", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    dlgAlert.create().show();
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
    //Checks if the field is empty or not
    boolean isEmpty(EditText text){
       CharSequence input = text.getText().toString();
       return TextUtils.isEmpty(input);
    }
   //Checks if the email is valid or not
    boolean isEmail(EditText text){
        CharSequence input = text.getText().toString();
        return (!TextUtils.isEmpty(input) && Patterns.EMAIL_ADDRESS.matcher(input).matches());
    }
    //Method for validating user inputs
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
        if(password.getText().toString().length() < 6 || confirm_password.getText().toString().length() < 6){
            Toast.makeText(getApplicationContext(), "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.getText().toString().equals(confirm_password.getText().toString())){
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    //Saving display name to firebase
    private void saveDisplayName(){
        FirebaseUser user = auth.getCurrentUser();
        if(user!= null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(full_name.getText().toString()).build();

            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d("saving display name","successful");
                    }
                }
            });
        }
    }

    //Method to check if there is active internet connection
    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}
