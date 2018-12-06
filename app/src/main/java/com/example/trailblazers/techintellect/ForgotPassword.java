/**
 * @author  Yash Modi
 * @version 1.0
 * Created on 21 November 2018
 */

package com.example.trailblazers.techintellect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText fp_email;
    private Button fp_submitBtn;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);

        fp_email = findViewById(R.id.fp_email);
        fp_submitBtn = findViewById(R.id.fp_submitBtn);

        fp_submitBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String email = fp_email.getText().toString();

                if (isConnectedToInternet()) {

                    if (validateCredentials()) {

                        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override

                            public void onComplete(@NonNull Task<Void> task) {          // Sends email on given email address to reset password

                                if (task.isSuccessful()) {

                                    Toast.makeText(getApplicationContext(), "Email sent successfully", Toast.LENGTH_SHORT).show();
                                    fp_email.setText("");
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Sorry! email could not be sent, please check the email address", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    // Adds back button to the screen
    public boolean onOptionsItemSelected(MenuItem mItem){

        switch (mItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(mItem);
    }

    // Checks if application is connected to internet
    public boolean isConnectedToInternet(){

        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            if (info != null)

                for(int i = 0; i < info.length; i++)

                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }

        Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    // Checks if edit text is empty or not
    boolean isEmpty(EditText text){

        CharSequence input = text.getText().toString();
        return TextUtils.isEmpty(input);
    }

    // Validates edit text with email address pattern
    boolean isEmail(EditText text){

        CharSequence input = text.getText().toString();
        return (!TextUtils.isEmpty(input) && Patterns.EMAIL_ADDRESS.matcher(input).matches());
    }

    // Validates email address with appropriate messages
    private boolean validateCredentials()
    {
        if(isEmpty(fp_email)){

            Toast.makeText(getApplicationContext(), "Please provide your email address...", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(!isEmail(fp_email)){

            Toast.makeText(getApplicationContext(), "Please enter a valid email address...", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
