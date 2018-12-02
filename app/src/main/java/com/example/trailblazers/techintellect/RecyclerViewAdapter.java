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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>
{

    Context context;
    List<FirebaseDataModel> list;
    private Firebase ref;
    private int number=0;
    private int noneed;



    public  FirebaseDataModel mylist;

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

         mylist = list.get(i);

        recyclerViewHolder.topic.setText(mylist.getTopic()+" \n \n "+mylist.getLevel());


        recyclerViewHolder.linearRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Hi",Toast.LENGTH_SHORT).show();
                //ArrayList<QuestionAnswerModel> questionAnswerModelArrayList = new ArrayList<QuestionAnswerModel>();
                initialise();
                Intent intent = new Intent(context,Dashboard_Intent.class);

                context.startActivity(intent);
            }
        });
    }


    String topic = mylist.getTopic();
    String level = mylist.getLevel();
    public String firebaseurl = makeUrl(topic,level);

    public void initialise()
    {
        ref = new Firebase(firebaseurl+""+number+"/Question");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<QuestionAnswerModel> questionAnswerModelArrayList = new ArrayList<QuestionAnswerModel>();



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public String makeUrl(String topic, String level){
        String Url;
        if(topic!=null && topic.equalsIgnoreCase("Google Go")){
            if(level!=null && level.equalsIgnoreCase("Easy")){
                Url = "https://tech-intellect-3dd39.firebaseio.com/easy_go/";
                return Url;
            }
            if(level!=null && level.equalsIgnoreCase("Medium")){
                Url = "https://tech-intellect-3dd39.firebaseio.com/medium_go/";
                return Url;
            }
            if(level!=null && level.equalsIgnoreCase("Hard")){

            }
        }
        return "Success";
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

