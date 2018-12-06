package com.example.trailblazers.techintellect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class QuestionAnswerAdapter extends RecyclerView.Adapter<QuestionAnswerAdapter.RecyclerViewHolder> {
   ArrayList<QuestionAnswerModel> questionAnswerModelArrayList;
   Context context;

    public QuestionAnswerAdapter(Context context, ArrayList<QuestionAnswerModel> questionAnswerModelArrayList ){
        this.context=context;
        this.questionAnswerModelArrayList = questionAnswerModelArrayList;

    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        View view = layoutInflater.inflate(R.layout.activity_questionanswer,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {

        QuestionAnswerModel questionAnswerModel = questionAnswerModelArrayList.get(i);
        recyclerViewHolder.question.setText(questionAnswerModel.getQuestion());
        recyclerViewHolder.answer.setText(questionAnswerModel.getAnswer());
    }

    public void insert(int i, QuestionAnswerModel questionAnswerModel){
        questionAnswerModelArrayList.add(i,questionAnswerModel);
        notifyItemInserted(i);
    }


    @Override
    public int getItemCount() {

        return questionAnswerModelArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
         TextView question, answer;
         LinearLayout linearLayout;
         CardView cardView;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
           // cardView = (CardView) itemView.findViewById(R.id.QAcardview) ;
            question = (TextView) itemView.findViewById(R.id.question);
            answer = (TextView) itemView.findViewById(R.id.answer);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.RelativeLayout);

        }
    }
}
