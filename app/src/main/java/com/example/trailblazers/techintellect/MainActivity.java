package com.example.trailblazers.techintellect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

    private TextView btn_forgoPwd; //Added by Yash
    private LinearLayout linearLayout;
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

        //Added by Yash Starts
        btn_forgoPwd = findViewById(R.id.btn_forgoPwd);

        btn_forgoPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(intent);
            }
        });
        //Added by Yash Ends

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                intent.putExtra("Guest",1);
                startActivity(intent);

            }
        });

        btnLogin = findViewById(R.id.btn_signIn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnectedToInternet()){ //Added by Aravind - checking if device is connected to internet
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
                //Added by Aravind - Starts
                else{
                    //Alerting when there is no active internet connection
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
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
                //Added by Aravind - Ends

            }
        });
        //Added by Ravi ends

        // Added by Smit to hide the keyboard
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hide(v);
                return false;
            }
        });
    }
protected void hide(View view){
    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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

    //Added by Aravind - Starts
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
    //Added by Aravind - Ends


}
