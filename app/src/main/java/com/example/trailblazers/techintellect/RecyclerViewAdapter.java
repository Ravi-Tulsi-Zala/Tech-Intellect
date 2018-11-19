package com.example.trailblazers.techintellect;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>
{

    Context context;
    private String [] topicNames;
    public RecyclerViewAdapter(Context context,String [] topicNames)
    {
        this.topicNames=topicNames;
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
        String topicName = topicNames[i];
        recyclerViewHolder.topic.setText(topicName);

        recyclerViewHolder.linearRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Hi",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,Dashboard_Intent.class);
                intent.putExtra("New Intent","Dashboard");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topicNames.length;
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

