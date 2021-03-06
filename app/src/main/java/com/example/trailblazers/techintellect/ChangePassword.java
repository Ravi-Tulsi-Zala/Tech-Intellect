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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    private EditText cp_old_password;
    private EditText cp_newPassword;
    private EditText cp_confirmPassword;
    private Button cp_submitBtn;
    private TextView guestError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);

        cp_old_password = findViewById(R.id.cp_old_password);
        cp_newPassword = findViewById(R.id.cp_newPassword);
        cp_confirmPassword = findViewById(R.id.cp_confirmPassword);
        cp_submitBtn = findViewById(R.id.cp_submitBtn);

        guestError = findViewById(R.id.guestError);

        // Gets current user's data
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // checks for guest user and make necessary changes if found
        if(user != null){
            guestError.setText("");
        }
        else {
            cp_submitBtn.setVisibility(View.GONE);
            cp_old_password.setFocusable(false);
            cp_old_password.setCursorVisible(false);
            cp_old_password.setEnabled(false);
            cp_old_password.setKeyListener(null);
            cp_newPassword.setFocusable(false);
            cp_newPassword.setCursorVisible(false);
            cp_newPassword.setEnabled(false);
            cp_newPassword.setKeyListener(null);
            cp_confirmPassword.setEnabled(false);
            cp_confirmPassword.setCursorVisible(false);
            cp_confirmPassword.setFocusable(false);
            cp_confirmPassword.setKeyListener(null);
            guestError.setText("Sorry, This feature does not work with a guest user.");
        }


        cp_submitBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String email = user.getEmail();

                String password = cp_old_password.getText().toString();
                final String newPassword = cp_newPassword.getText().toString();
                String confirmPassword = cp_confirmPassword.getText().toString();

                if (isConnectedToInternet()) {

                    if (validateCredentials()) {

                        if (newPassword.equals(confirmPassword)) {                  // Checks if new password and confirm password fields have same value

                            AuthCredential credential = EmailAuthProvider.getCredential(email, password);           // Re-authentic user with existing password and email
                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override

                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {             // updates password after re-authenticating user

                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Password updated", Toast.LENGTH_SHORT).show();
                                                    cp_old_password.setText("");
                                                    cp_newPassword.setText("");
                                                    cp_confirmPassword.setText("");
                                                    cp_confirmPassword.clearFocus();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Sorry! Password could not be updated", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Sorry! User authentication failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Confirm password does not match with new password", Toast.LENGTH_SHORT).show();
                        }
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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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

    // Validates given edit text fields if empty or not with appropriate messages
    private boolean validateCredentials()
    {

        if(isEmpty(cp_old_password)){
            Toast.makeText(getApplicationContext(), "Please provide your old Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(isEmpty(cp_newPassword)){
            Toast.makeText(getApplicationContext(), "Please provide your new Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(isEmpty(cp_confirmPassword)){
            Toast.makeText(getApplicationContext(), "Please confirm your new Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Checks edit text is empty or not
    boolean isEmpty(EditText text){
        CharSequence input = text.getText().toString();
        return TextUtils.isEmpty(input);
    }

}
