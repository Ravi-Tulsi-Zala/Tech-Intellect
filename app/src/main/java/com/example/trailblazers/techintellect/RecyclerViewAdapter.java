package com.example.trailblazers.techintellect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    Context context;
    List<FirebaseDataModel> list;
public  static String final_topic;
public static ArrayList<QuestionAnswerModel> qalist = new ArrayList<QuestionAnswerModel>();
    public RecyclerViewAdapter(Context context, List<FirebaseDataModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.linear_recycler_layout, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {
        // String topicName = topicNames[i];

        FirebaseDataModel mylist = list.get(i);

        recyclerViewHolder.topic.setText(mylist.getTopic() + " " + mylist.getLevel());
        if(mylist.getTopic().contains("Go"))
        {
            final_topic = mylist.getLevel().toLowerCase() + "_" + "go";

        }
        else if(mylist.getTopic().contains("Natural"))
        {
            final_topic = mylist.getLevel().toLowerCase() + "_" + "nlp";

        }
        else if(mylist.getTopic().contains("R"))
        {
            final_topic = mylist.getLevel().toLowerCase() + "_" + "r";

        }
        else if(mylist.getTopic().contains("Acronyms"))
        {
            final_topic = mylist.getLevel().toLowerCase() + "_" + "acronyms";

        }
        else
        {
            ;
        }
        recyclerViewHolder.linearRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show();
                ArrayList<QuestionAnswerModel> questionAnswerModelArrayList = new ArrayList<QuestionAnswerModel>();
                QAlist().clear();
                Intent intent = new Intent(context, Dashboard_Intent.class);

                context.startActivity(intent);
            }
        });
    }

    public static ArrayList<QuestionAnswerModel> QAlist() {
        FirebaseDatabase database;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(final_topic);

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                   QuestionAnswerModel questionAnswerModel = dataSnapshot1.getValue(QuestionAnswerModel.class);
                   String ques = questionAnswerModel.getQuestion();
                   String ans = questionAnswerModel.getAnswer();
                   qalist.add(questionAnswerModel);
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
//        qalist.add(new QuestionAnswerModel("Acronyms", "Acronyms answer"));
//        qalist.add(new QuestionAnswerModel("R", "R answer"));
//        qalist.add(new QuestionAnswerModel("NLP", "NLP answer"));
//        qalist.add(new QuestionAnswerModel("Google Go", "Google Go answer"));
//        qalist.add(new QuestionAnswerModel("Acronyms", "Acronyms answer"));
//        qalist.add(new QuestionAnswerModel("R", "R answer"));
//        qalist.add(new QuestionAnswerModel("NLP", "NLP answer"));
        return qalist;
    }

    @Override
    public int getItemCount() {
        int arr = 0;
        try {
            if (list.size() == 0) {

                arr = 0;

            } else {

                arr = list.size();
            }


        } catch (Exception e) {


        }

        return arr;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView topic;
        RelativeLayout linearRecycler;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            topic = (TextView) itemView.findViewById(R.id.topic);
            linearRecycler = (RelativeLayout) itemView.findViewById(R.id.linearRecycler);
        }
    }
}
