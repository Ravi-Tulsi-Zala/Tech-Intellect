package com.example.trailblazers.techintellect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

/*************************************************************************************************************************************************************
 Program: Quiz completion screen which would pop up upon the completion of a quiz
 Author: Haritha
 Date of creation: 16-Nov-2018
 **************************************************************************************************************************************************************/

public class QuizCompletionScreen extends AppCompatActivity {

private Button main_menu_button;
private ImageView trophy_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_completion_screen);

        trophy_image = findViewById(R.id.trophy_img);
        main_menu_button = findViewById(R.id.take_me_home_btn);

        main_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
                startActivity(intent);
            }
        });

        //Animating the image view
        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        trophy_image.startAnimation(animation);

    }

    @Override
    //Handling default android back button
    public void onBackPressed() {
        this.finish();
        Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem mItem){
        switch (mItem.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(mItem);
    }
}
