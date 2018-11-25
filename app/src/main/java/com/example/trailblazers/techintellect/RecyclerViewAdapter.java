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

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>
{

    Context context;
    List<FirebaseDataModel> list;

    public RecyclerViewAdapter(Context context, List<FirebaseDataModel> list)
    {
        this.list=list;
        this.context=context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.linear_recycler_layout,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder( RecyclerViewHolder recyclerViewHolder, int i) {
       // String topicName = topicNames[i];

        FirebaseDataModel mylist = list.get(i);

        recyclerViewHolder.topic.setText(mylist.getTopic()+" "+mylist.getLevel());


        recyclerViewHolder.linearRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Hi",Toast.LENGTH_SHORT).show();
                ArrayList<QuestionAnswerModel> questionAnswerModelArrayList = new ArrayList<QuestionAnswerModel>();

                Intent intent = new Intent(context,Dashboard_Intent.class);

                context.startActivity(intent);
            }
        });
    }
    public static ArrayList<QuestionAnswerModel> QAlist(){
        ArrayList<QuestionAnswerModel> qalist = new ArrayList<>();
        qalist.add(new QuestionAnswerModel("Google Go","Google Go answer"));
        qalist.add(new QuestionAnswerModel("Acronyms","Acronyms answer"));
        qalist.add(new QuestionAnswerModel("R","R answer"));
        qalist.add(new QuestionAnswerModel("NLP","NLP answer"));
        qalist.add(new QuestionAnswerModel("Google Go","Google Go answer"));
        qalist.add(new QuestionAnswerModel("Acronyms","Acronyms answer"));
        qalist.add(new QuestionAnswerModel("R","R answer"));
        qalist.add(new QuestionAnswerModel("NLP","NLP answer"));
        qalist.add(new QuestionAnswerModel("Google Go","Google Go answer"));
        qalist.add(new QuestionAnswerModel("Acronyms","Acronyms answer"));
        qalist.add(new QuestionAnswerModel("R","R answer"));
        qalist.add(new QuestionAnswerModel("NLP","NLP answer"));
        qalist.add(new QuestionAnswerModel("Google Go","Google Go answer"));
        qalist.add(new QuestionAnswerModel("Acronyms","Acronyms answer"));
        qalist.add(new QuestionAnswerModel("R","R answer"));
        qalist.add(new QuestionAnswerModel("NLP","NLP answer"));
        return qalist;
    }
    @Override
    public int getItemCount() {
        int arr=0;
        try{
            if(list.size()==0){

                arr = 0;

            }
            else{

                arr=list.size();
            }



        }catch (Exception e){



        }

        return arr;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView topic;
        RelativeLayout linearRecycler;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            topic = (TextView) itemView.findViewById(R.id.topic);
            linearRecycler = (RelativeLayout)itemView.findViewById(R.id.linearRecycler);
        }
    }
}

