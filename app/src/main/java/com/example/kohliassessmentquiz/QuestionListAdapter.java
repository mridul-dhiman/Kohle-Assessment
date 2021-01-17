package com.example.kohliassessmentquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kohliassessmentquiz.utils.Questions;

import java.util.ArrayList;

import static com.example.kohliassessmentquiz.utils.Constants.ANSWERED_CORRECTLY;
import static com.example.kohliassessmentquiz.utils.Constants.ANSWERED_INCORRECTLY;
import static com.example.kohliassessmentquiz.utils.Constants.VIEWED;

public class QuestionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int QUESTION_TYPE = 1;
    private static final int RESULT_TYPE = 2;

    private boolean displayingQuestions = true;

    private ArrayList<Questions> questionsArrayList;
    private ArrayList<Integer> answeredList;
    private ArrayList<Integer> responseList;
    private OnQuestionClickListener clickListener;

    public QuestionListAdapter(ArrayList<Questions> questionsArrayList, OnQuestionClickListener clickListener, ArrayList<Integer> answeredList, ArrayList<Integer> responseList) {
        this.questionsArrayList = questionsArrayList;
        this.clickListener = clickListener;
        this.answeredList = answeredList;
        this.responseList = responseList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == QUESTION_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_list_card, parent, false);
            return new QuestionViewHolder(view, clickListener);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_list_card, parent, false);
            return new ResultViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (displayingQuestions) {
            return QUESTION_TYPE;
        } else {
            return RESULT_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == QUESTION_TYPE) {
            ((QuestionViewHolder) holder).question.setText(questionsArrayList.get(position).getQuestion());

            if (answeredList != null && answeredList.get(position) != null) {
                if (answeredList.get(position) == ANSWERED_CORRECTLY) {
                    ((QuestionViewHolder) holder).cardView.setBackgroundResource(R.color.correct);
                    ((QuestionViewHolder) holder).question.setTextColor(holder.itemView.getResources().getColor(R.color.white, null));
                } else if (answeredList.get(position) == ANSWERED_INCORRECTLY) {
                    ((QuestionViewHolder) holder).cardView.setBackgroundResource(R.color.incorrect);
                    ((QuestionViewHolder) holder).question.setTextColor(holder.itemView.getResources().getColor(R.color.white, null));
                } else if (answeredList.get(position) == VIEWED) {
                    ((QuestionViewHolder) holder).cardView.setBackgroundResource(R.color.viewed);
                    ((QuestionViewHolder) holder).question.setTextColor(holder.itemView.getResources().getColor(R.color.white, null));
                }
            }
        } else {
            ((ResultViewHolder) holder).question.setText(questionsArrayList.get(position).getQuestion());

            if (responseList.get(position) == null) {
                ((ResultViewHolder) holder).UAnswer.setText(holder.itemView.getResources().getString(R.string.not_answered));
                ((ResultViewHolder) holder).UAnswer.setTextColor(holder.itemView.getResources().getColor(R.color.incorrect, null));

                ((ResultViewHolder) holder).CAnswer.setVisibility(View.VISIBLE);
                String correctAns = null;
                switch (questionsArrayList.get(position).getCorrect_option()) {
                    case 1:
                        correctAns = questionsArrayList.get(position).getOption1();
                        break;
                    case 2:
                        correctAns = questionsArrayList.get(position).getOption2();
                        break;
                    case 3:
                        correctAns = questionsArrayList.get(position).getOption3();
                        break;
                    case 4:
                        correctAns = questionsArrayList.get(position).getOption4();
                        break;
                }
                ((ResultViewHolder) holder).CAnswer.setText(correctAns);


            } else if (responseList.get(position) == questionsArrayList.get(position).getCorrect_option()) {
                String correctAns = null;
                switch (questionsArrayList.get(position).getCorrect_option()) {
                    case 1:
                        correctAns = questionsArrayList.get(position).getOption1();
                        break;
                    case 2:
                        correctAns = questionsArrayList.get(position).getOption2();
                        break;
                    case 3:
                        correctAns = questionsArrayList.get(position).getOption3();
                        break;
                    case 4:
                        correctAns = questionsArrayList.get(position).getOption4();
                        break;
                }
                ((ResultViewHolder) holder).UAnswer.setText(correctAns);
                ((ResultViewHolder) holder).UAnswer.setTextColor(holder.itemView.getResources().getColor(R.color.correct, null));

            } else {
                ((ResultViewHolder) holder).CAnswer.setVisibility(View.VISIBLE);
                String correctAns = null;
                switch (questionsArrayList.get(position).getCorrect_option()) {
                    case 1:
                        correctAns = questionsArrayList.get(position).getOption1();
                        break;
                    case 2:
                        correctAns = questionsArrayList.get(position).getOption2();
                        break;
                    case 3:
                        correctAns = questionsArrayList.get(position).getOption3();
                        break;
                    case 4:
                        correctAns = questionsArrayList.get(position).getOption4();
                        break;
                }
                ((ResultViewHolder) holder).CAnswer.setText(correctAns);

                String uAns = null;
                switch (responseList.get(position)) {
                    case 1:
                        uAns = questionsArrayList.get(position).getOption1();
                        break;
                    case 2:
                        uAns = questionsArrayList.get(position).getOption2();
                        break;
                    case 3:
                        uAns = questionsArrayList.get(position).getOption3();
                        break;
                    case 4:
                        uAns = questionsArrayList.get(position).getOption4();
                        break;
                }
                ((ResultViewHolder) holder).UAnswer.setText(uAns);
                ((ResultViewHolder) holder).UAnswer.setTextColor(holder.itemView.getResources().getColor(R.color.incorrect, null));
            }
        }
    }

    @Override
    public int getItemCount() {
        return questionsArrayList.size();
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView question;
        OnQuestionClickListener onQuestionClickListener;

        public QuestionViewHolder(@NonNull View itemView, OnQuestionClickListener listener) {
            super(itemView);
            cardView = itemView.findViewById(R.id.questionCard);
            question = itemView.findViewById(R.id.tvQuestionCard);
            onQuestionClickListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onQuestionClickListener.OnClick(getAdapterPosition());
        }
    }

    public interface OnQuestionClickListener {
        void OnClick(int position);
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView question, UAnswer, CAnswer;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.tvResultCard);
            UAnswer = itemView.findViewById(R.id.tVUAnswer);
            CAnswer = itemView.findViewById(R.id.tVCorrect);
        }
    }

    public void setDisplayingQuestions(boolean displayingQuestions) {
        this.displayingQuestions = displayingQuestions;
        this.notifyDataSetChanged();
    }
}
