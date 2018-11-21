package com.example.trailblazers.techintellect;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Dashboard_Intent extends AppCompatActivity {
    TextView question,answer;
    private QuestionAnswerAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard__intent);

        question = (TextView)findViewById(R.id.question);
        answer = (TextView)findViewById(R.id.answer);

        ArrayList<QuestionAnswerModel> qalist = RecyclerViewAdapter.QAlist();

        recyclerView = (RecyclerView) findViewById(R.id.RecycleQA);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);


        //Toast.makeText(getApplicationContext(),qalist.size(),Toast.LENGTH_SHORT).show();
       // System.out.println(qalist.size());

        recyclerViewAdapter = new QuestionAnswerAdapter(getApplicationContext(),qalist);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(layoutManager);
    }



}
