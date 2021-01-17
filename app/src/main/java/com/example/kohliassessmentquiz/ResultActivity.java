package com.example.kohliassessmentquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.kohliassessmentquiz.database.QuestionsRepository;
import com.example.kohliassessmentquiz.utils.Questions;
import com.example.kohliassessmentquiz.utils.VerticalSpacing;
import com.google.android.material.transition.platform.MaterialFadeThrough;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private static final String TAG = "ResultActivity";

    private QuestionListAdapter adapter;
    private ArrayList<Questions> questionsList = new ArrayList<>();
    private ArrayList<Integer> responseList = new ArrayList<>();

    private QuestionsRepository questionsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new MaterialFadeThrough().addTarget(findViewById(R.id.rAContainer)));
        getWindow().setExitTransition(new MaterialFadeThrough().addTarget(findViewById(R.id.rAContainer)));
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);
        setContentView(R.layout.activity_result);

        RecyclerView recyclerView = findViewById(R.id.rvResult);

        TextView total = findViewById(R.id.tVTotal);
        TextView corr = findViewById(R.id.tVCorrect);
        TextView incorr = findViewById(R.id.tVIncorrect);

        SharedPreferences sharedPreferences = getSharedPreferences("Assessment", MODE_PRIVATE);

        questionsRepository = new QuestionsRepository(this);

        Intent intent = getIntent();
        responseList = intent.getIntegerArrayListExtra("ANSWERED");
        int correct = intent.getIntExtra("CORR", 0);
        int incorrect = intent.getIntExtra("INCORR", 0);

        total.setText(String.valueOf(correct));
        corr.setText(String.valueOf(correct));
        incorr.setText(String.valueOf(incorrect));

        int saved = sharedPreferences.getInt("SAVE", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (saved < correct) {
            editor.putInt("SAVE", correct);
            editor.apply();
            editor.commit();

            Log.d(TAG, "onCreate: saved");
        }


        retrieveQuestions();
        setupRecyclerView(recyclerView);
    }

    private void retrieveQuestions() {
        questionsRepository.retrieveQuestions().observe(this, questionsList1 -> {
            if (!questionsList.isEmpty()) {
                questionsList.clear();
            }
            if (!questionsList1.isEmpty()) {
                questionsList.addAll(questionsList1);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        adapter = new QuestionListAdapter(questionsList, null, null, responseList);
        adapter.setDisplayingQuestions(false);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new VerticalSpacing());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}