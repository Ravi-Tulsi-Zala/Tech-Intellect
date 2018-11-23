/**
 * @author  Yash Modi
 * @version 1.0
 * Created on 16 November 2018
 */
package com.example.trailblazers.techintellect;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;

public class QuizScreen extends AppCompatActivity {
    //Added by Aravind - Starts
    private TextView questionTextView;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;
    private Button submit;
    private Button quit;
    private TextView correct_answer;
    private Firebase questionRef,option1Ref,option2Ref,option3Ref,option4ref,answerRef;
    private int questionNumber = 0;
    private int dummyQuestionNumber;
    private int maxQuestions = 10;
    private String correctAnswerValue;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView levelView;
    private TextView topicView;
    private String topic;
    private String level;
    private String mode;
    private String firebaseUrl;
    private Vibrator vibe;
    List<Integer> wrongly_answered = new ArrayList<Integer>();
    private TextView timerView;
    private CountDownTimer timer;
    private long millisInFuture,minutes, seconds = 0;
    private boolean isQuizCompleted = false;
    //Added by Aravind - Ends
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this); //Added by Aravind
        setContentView(R.layout.quiz_screen);

        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);
        //Added by Aravind - Starts
        initializeComponents();
        firebaseUrl = buildUrl(topic,level);
        Log.e("fire base url built",""+firebaseUrl);
        performQuiz();
        //Added by Aravind - Ends
    }

    public boolean onOptionsItemSelected(MenuItem mItem){
        switch (mItem.getItemId()) {
            case android.R.id.home:
                //Added by Aravind - Starts
                //Ending the quiz when the back button on the task bar is pressed
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizScreen.this);
                builder.setMessage("Going back would end the quiz. Are you sure you want to continue?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Intent homeActivity = new Intent(getApplicationContext(),HomeScreen.class);
                                startActivity(homeActivity);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                //Added by Aravind - Ends
                return true;
        }
        return super.onOptionsItemSelected(mItem);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    //Added by Aravind - Starts
    private void initializeComponents(){

        Bundle bundle = getIntent().getExtras();
        topic = bundle.getString("topic");
        level = bundle.getString("level");
        mode = bundle.getString("mode");

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        questionTextView = findViewById(R.id.questionMain);
        option1 = findViewById(R.id.optionA);
        option2 = findViewById(R.id.optionB);
        option3 = findViewById(R.id.optionC);
        option4 = findViewById(R.id.optionD);
        submit = findViewById(R.id.submitAnswerBtn);
        quit = findViewById(R.id.quitBtn);
        correct_answer = findViewById(R.id.correctAnswer);
        radioGroup = findViewById(R.id.radioOptions);
        levelView = findViewById(R.id.difficultyModeText);
        topicView = findViewById(R.id.topicNameText);
        timerView = findViewById(R.id.timerTextView);

        levelView.setText(level);
        topicView.setText(topic);

        if(mode !=null && mode.equalsIgnoreCase("Timed")){
            if(level !=null && level.equalsIgnoreCase("Easy")){
                minutes = 7;
                seconds = 30;
                millisInFuture = minutes * 60000 + seconds * 1000;
                startCountDownTimer();
            }
            else if(level !=null && level.equalsIgnoreCase("Medium")){
                minutes = 5;
                seconds = 30;
                millisInFuture = minutes * 60000 + seconds * 1000;
                startCountDownTimer();
            }
            else if(level !=null && level.equalsIgnoreCase("Hard")){
                minutes = 3;
                seconds = 30;
                millisInFuture = minutes * 60000 + seconds * 1000;
                startCountDownTimer();
            }

        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1)
                {
                    // no radio buttons are checked
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(QuizScreen.this);
                    dlgAlert.setMessage("Please select your answer!");
                    dlgAlert.setTitle("Alert");
                    dlgAlert.setPositiveButton("Ok", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss the dialog
                                    dialog.cancel();
                                }
                            });
                    dlgAlert.create().show();
                }
                else{
                    submit.setEnabled(false);
                    // get selected radio button from radioGroup
                    int selectedId = radioGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioButton = (RadioButton) findViewById(selectedId);

                //when the user answer is wrong
                if(!radioButton.getText().toString().equalsIgnoreCase(correctAnswerValue)){
                    //Added by Haritha - starts
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wronganswer);
                    mediaPlayer.start();
                    //Added by Haritha - ends
                    boolean isVibrate = SettingsActivity.isVibrateSwitchOn;
                    if(isVibrate)
                     vibe.vibrate(500);
                    correct_answer.setTextColor(0xFFFF0000);
                    correct_answer.setText("Answer: "+correctAnswerValue);
                    wrongly_answered.add(dummyQuestionNumber); //adding wrong question to the list - yet to implement the logic
                    radioGroup.clearCheck();
                    Runnable r = new Runnable() {
                        @Override
                        public void run(){
                            correct_answer.setText(null);
                            if(questionNumber < maxQuestions)
                               performQuiz(); //loading the next question
                            else{
                                //Added by Haritha for jumping to quiz completion screen upon completion of quiz - starts
                                //Toast.makeText(getApplicationContext(), "Quiz has been completed! ", Toast.LENGTH_LONG).show();
                                isQuizCompleted = true;
                                Intent intent = new Intent(getApplicationContext(),QuizCompletionScreen.class);
                                startActivity(intent);
                                //Added by Haritha for jumping to quiz completion screen upon completion of quiz - ends
                            }

                        }
                    };
                    Handler h = new Handler();
                    h.postDelayed(r, 4000); // <-- the "4000" is the delay time in miliseconds.

                }
                //when the answer is correct
                else{
                    //Added by Haritha - starts
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.correctanswer);
                    mediaPlayer.start();
                    //Added by Haritha - ends
                    correct_answer.setTextColor(0xFF00FF00);
                    correct_answer.setText("You got it correct!");
                    radioGroup.clearCheck();
                    Runnable r = new Runnable() {
                        @Override
                        public void run(){
                            correct_answer.setText(null);
                            if(questionNumber < maxQuestions)
                                performQuiz();  //loading the next question
                            else{
                                //Added by Haritha for jumping to quiz completion screen upon completion of quiz - starts
                                //Toast.makeText(getApplicationContext(), "Quiz has been completed! ", Toast.LENGTH_LONG).show();
                                isQuizCompleted = true;
                                Intent intent = new Intent(getApplicationContext(),QuizCompletionScreen.class);
                                startActivity(intent);
                                //Added by Haritha for jumping to quiz completion screen upon completion of quiz - ends
                            }
                        }
                    };
                    Handler h = new Handler();
                    h.postDelayed(r, 4000); // <-- the "4000" is the delay time in miliseconds.
                }
      }

            }
        });
        //Ending the quiz when the quit button is pressed
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizScreen.this);
                builder.setMessage("Are you sure you want to quit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Intent homeActivity = new Intent(getApplicationContext(),HomeScreen.class);
                                startActivity(homeActivity);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    //Method for fetching the JSON values from firebase and displaying it in the UI
    public void performQuiz(){
        submit.setEnabled(true);
        if(isConnectedToInternet()){
            questionRef = new Firebase(firebaseUrl+""+questionNumber+"/Question");
            questionRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String question = dataSnapshot.getValue(String.class);
                    questionTextView.setText(question);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            option1Ref = new Firebase(firebaseUrl+""+questionNumber+"/Answer choice 1");
            option1Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String data = dataSnapshot.getValue(String.class);
                    option1.setText(data);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            option2Ref = new Firebase(firebaseUrl+""+questionNumber+"/Answer choice 2");
            option2Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String data = dataSnapshot.getValue(String.class);
                    option2.setText(data);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            option3Ref = new Firebase(firebaseUrl+""+questionNumber+"/Answer choice 3");
            option3Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String data = dataSnapshot.getValue(String.class);
                    option3.setText(data);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            option4ref = new Firebase(firebaseUrl+""+questionNumber+"/Answer choice 4");
            option4ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String data = dataSnapshot.getValue(String.class);
                    option4.setText(data);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            answerRef = new Firebase(firebaseUrl+""+questionNumber+"/Correct answer");
            answerRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String data = dataSnapshot.getValue(String.class);
                    correctAnswerValue = data;
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            dummyQuestionNumber = questionNumber;
            questionNumber++;
        }
        else{
            //Alerting when there is no active internet connection
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(QuizScreen.this);
            dlgAlert.setMessage("Please check your internet connection and try again!");
            dlgAlert.setTitle("Alert");
            dlgAlert.setPositiveButton("Ok", null);
            dlgAlert.setCancelable(true);
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent homeActivity = new Intent(getApplicationContext(),HomeScreen.class);
                            startActivity(homeActivity);
                            dialog.cancel();
                        }
                    });
            dlgAlert.create().show();
        }

    }

    //This method builds the firebase URL based on the user's selection in the home screen
    public String buildUrl(String topic, String level){
        //Acronyms url
        String Url;
        if(topic !=null && topic.equalsIgnoreCase("Computer Science Acronyms")){
            if(level !=null && level.equalsIgnoreCase("Easy")){
                Url = "https://tech-intellect-3dd39.firebaseio.com/acronyms_easy/";
                return Url;
            }
            if(level !=null && level.equalsIgnoreCase("Medium")){
                Url = "https://tech-intellect-3dd39.firebaseio.com/acronyms_medium/";
                return Url;
            }
            //Added by Haritha - starts
            if(level !=null && level.equalsIgnoreCase("Hard")){
                Url = "https://tech-intellect-3dd39.firebaseio.com/acronyms_hard/";
                return Url;
            }
            //Added by Haritha - ends
        }
        // Google Go Url
        else if( topic !=null && topic.equalsIgnoreCase("Google Go")){
            if(level !=null && level.equalsIgnoreCase("Easy")){
                Url = "https://tech-intellect-3dd39.firebaseio.com/easy_go/";
                return Url;
            }
            if(level !=null && level.equalsIgnoreCase("Medium")){
                Url = "https://tech-intellect-3dd39.firebaseio.com/medium_go/";
                return Url;
            }
            if(level !=null && level.equalsIgnoreCase("Hard")){

            }
        }
        //NLP url
        else if(topic !=null && topic.equalsIgnoreCase("Natural Language Processing")){
            if(level !=null && level.equalsIgnoreCase("Easy")){
                Url = "https://tech-intellect-3dd39.firebaseio.com/NLP_easy/";
                return Url;
            }
            if(level !=null && level.equalsIgnoreCase("Medium")){
                Url = "https://tech-intellect-3dd39.firebaseio.com/NLP_medium/";
                return Url;
            }
            if(level !=null && level.equalsIgnoreCase("Hard")){
                Url = "https://tech-intellect-3dd39.firebaseio.com/NLP_hard/";
                return Url;
            }
        }
        return "success";

    }

    //ending the quiz when the bottom back button is pressed
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizScreen.this);
        builder.setMessage("Going back would end the quiz. Are you sure you want to continue?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent homeActivity = new Intent(getApplicationContext(),HomeScreen.class);
                        startActivity(homeActivity);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //for displaying timer in timed mode
    private void startCountDownTimer(){

        long startFrom = millisInFuture;
        timer = new CountDownTimer(startFrom, 1000) {
            @Override
            public void onTick(long millisLeft) {
                setTxtTimerValue(millisLeft);
            }

            @Override
            public void onFinish() {
                setTxtTimerValue(0);
                if(!isQuizCompleted){
                    Toast.makeText(getApplicationContext(),"Oops..Time out!",Toast.LENGTH_LONG).show();
                    Intent homeActivity = new Intent(getApplicationContext(),HomeScreen.class);
                    startActivity(homeActivity);
                }

            }
        }.start();
    }

    public void setTxtTimerValue(long millis){
        minutes = millis / 60000;
        seconds = (millis / 1000) % 60;

        String minutesVal = String.valueOf(minutes);
        String secondsVal = String.valueOf(seconds);

        secondsVal = secondsVal.replaceAll("(?<!\\d)\\d(?!\\d)", "0$0");

        timerView.setText(getString(R.string.txtTimer, minutesVal, secondsVal));
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


    //Added by Aravind - Ends


}
