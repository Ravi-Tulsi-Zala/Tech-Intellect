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


        cp_submitBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = user.getEmail();

                String password = cp_old_password.getText().toString();
                final String newPassword = cp_newPassword.getText().toString();
                String confirmPassword = cp_confirmPassword.getText().toString();

                if (isConnectedToInternet()) {

                    if (validateCredentials()) {

                        if (newPassword.equals(confirmPassword)) {

                            AuthCredential credential = EmailAuthProvider.getCredential(email, password);
                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override

                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Password updated", Toast.LENGTH_SHORT).show();
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

    public boolean isConnectedToInternet(){

        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            if (info != null)
                for(int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    boolean isEmpty(EditText text){
        CharSequence input = text.getText().toString();
        return TextUtils.isEmpty(input);
    }

    private boolean validateCredentials()
    {

        if(isEmpty(cp_old_password)){
            Toast.makeText(getApplicationContext(), "Please provide your old password....", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(isEmpty(cp_newPassword)){
            Toast.makeText(getApplicationContext(), "Please provide your new password....", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(isEmpty(cp_confirmPassword)){
            Toast.makeText(getApplicationContext(), "Please confirm your new password....", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
